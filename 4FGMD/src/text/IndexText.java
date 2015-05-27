package text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/** Index all text files under a directory. */
public class IndexText {

	private static final String FILE = "omim.txt";

	static final File INDEX_DIR = new File("drug_bank");
	static final File INDEX_DIR_OMIM = new File("omim");
	static enum keyWord{
		THEEND("THEEND"),RECORD("RECORD"),FIELD("FIELD");
		String value;
		private keyWord(String value) {
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	static enum column{
		NO("NO"),TI("TI"),TX("TX"),CS("CS");
		String value;
		private column(String value) {
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	private interface lamdaString{
		void execute(String line);
	}
	

	public static void index() {
		boolean create = true;

		if (INDEX_DIR.exists()) {
			INDEX_DIR.delete();
		}
		if (INDEX_DIR_OMIM.exists()) {
			INDEX_DIR_OMIM.delete();
		}

		File file;
		Date start;
		file = new File(FILE);
		if (!file.exists() || !file.canRead()) {
			System.out.println("File '" +file.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		start = new Date();
		try {
			Directory directory = FSDirectory.open(INDEX_DIR_OMIM);
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);

			if (create) {
				// Create a new index in the directory, removing any
				config.setOpenMode(OpenMode.CREATE);
			} else {
				config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}


			IndexWriter writer = new IndexWriter(directory, config);

			//IndexWriter writer = new IndexWriter(FSDirectory.open(INDEX_DIR), new IndexWriterConfig(Version.LUCENE_40, new StandardAnalyzer(Version.LUCENE_40)) );
			System.out.println("Indexing to directory '" +INDEX_DIR_OMIM+ "'...");
			indexDocOmim(writer, file);
			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void indexDocOmim(IndexWriter writer, File file) throws IOException {
		int eltCount = 0;
		if (file.canRead() && !file.isDirectory()) {
			// each line of the file is a new document
			try{
				InputStream 	  ips  = new FileInputStream(file); 
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader    br   = new BufferedReader(ipsr);
				String line;
				//initialization
				HashMap<String, String> map= new HashMap<>();
				map.put("NO","");
				map.put("TI","");
				map.put("TX","");
				map.put("CS","");
				line=br.readLine();
				do {
					// new drug
					if(line.startsWith("*RECORD*")){
						if(!StringUtils.isBlank(map.get("NO"))){
							addIndexedElement(writer, file, map);					
							clearMap(map);
							eltCount++;
						}
						line=br.readLine();
					}
					if(line.startsWith("*FIELD*")){
						switch(StringUtils.capitalize(line.replace("*FIELD*", " ").trim())){
						case "NO" :
							line=getDetails(br, line, (s)->getNO(map,br, s));
							break;
						case "TI" :
							line=getDetails(br, line, (s)->getTI(map,br, s));
							break;
						case "TX" :
							line=getDetails(br, line, (s)->getTX(map,br, s));
							break;
						case "CS" :
							line=getDetails(br, line, (s)->getCS(map,br, s));
							break;
						default : 
							System.out.println("Part Ignored "+line.trim());
							line=getDetails(br, line, (s)->{});
						}
					}
					if(line.startsWith("*THEEND*")){
						if(!StringUtils.isBlank(map.get("NO"))){
							addIndexedElement(writer, file, map);					
							clearMap(map);
							eltCount++;
						}
						System.out.println("Fin du document");
						line = null;
					}
					if(StringUtils.isBlank(line)){
						line=br.readLine();
					}
				}while(line!=null);

				br.close(); 
			}catch (Exception e){
				System.out.println(e.toString());
			}
		}
		System.out.println(eltCount+" elts have been added to the index " + System.getProperty("user.dir")+ "\\" + INDEX_DIR_OMIM);   
	}

	private static void addIndexedElement(IndexWriter writer, File file,
			HashMap<String, String> map) throws IOException {
		//write the index
		// make a new, empty document
		Document doc = new Document();
		//add 3 fields to it
		//doc.add(new StoredField("id",map.get("NO"))); // stored not indexed
		doc.add(new TextField("NO",map.get("NO"),Field.Store.YES)); // indexed and stored
		doc.add(new TextField("TI",map.get("TI"),Field.Store.YES)); // indexed and stored
		doc.add(new TextField("TX",map.get("TX"),Field.Store.YES)); // indexed
		doc.add(new TextField("CS",map.get("CS"),Field.Store.YES)); // indexed
		if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
			System.out.println("adding " + map.get("NO"));
			writer.addDocument(doc);
		}else{
			System.out.println("updating " + map.get("NO"));
			writer.updateDocument(new Term("path", file.getPath()), doc);
		}
	}

	private static void clearMap(HashMap<String, String> map) {
		//clean values
		map.forEach((key,obj)->{
			if(map.get(key)!=""){
				map.replace(key,"");
			}
		});
	}
	private static void getNO(HashMap<String, String> map,BufferedReader br,String str) {
		map.replace("NO", map.get("NO")+" "+str);
	}
	private static void getTI(HashMap<String, String> map,BufferedReader br,String str) {
		map.replace("TI", map.get("TI")+" "+str);
	}
	private static void getTX(HashMap<String, String> map,BufferedReader br,String str) {
		map.replace("TX", map.get("TX")+" "+str);
	}
	private static void getCS(HashMap<String, String> map,BufferedReader br,String str){
		map.replace("CS", map.get("CS")+" "+str);
	}
	private static String getDetails(BufferedReader br,String line,lamdaString fct) throws IOException{
		while((line=br.readLine())!=null){
			if(line.startsWith("*") && StringUtils.containsAny(line.split("\\*")[1],keyWord.FIELD.getValue(),keyWord.RECORD.getValue(),keyWord.THEEND.getValue())){
				return line;
			}
			if(!StringUtils.isBlank(line)){
				fct.execute(line.trim());
			}else{
				fct.execute("\n");
			}
		}
		return line;
	}
	public static ListProperty<Document> get(String colone,String field) throws IOException, ParseException{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(INDEX_DIR_OMIM));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		Query query= new QueryParser(colone, analyzer).parse(field);//182265
		
		System.out.println("searching for : "+query.toString());
		Collector collector = TopScoreDocCollector.create(10, true);
		searcher.search(query, collector);
		ListProperty<Document> doc = new SimpleListProperty<Document>(FXCollections.observableArrayList());
		ObjectProperty<Exception> ex = new SimpleObjectProperty<Exception>();
		Arrays.asList(((TopScoreDocCollector)collector).topDocs().scoreDocs).stream().forEach(o->{
			try {
				doc.add(searcher.doc(o.doc));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return doc;
	}
}
