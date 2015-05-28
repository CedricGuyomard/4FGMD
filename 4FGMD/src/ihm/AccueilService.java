package ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

import javax.xml.parsers.ParserConfigurationException;

import model.Disease;
import model.Drug;

import org.apache.lucene.queryparser.classic.ParseException;
import org.xml.sax.SAXException;

import sql.SqlParser;
import text.TextService;
import xml.ParserDrugBank;
import couchdb.Couchdb;
import couchdb.CouchdbException;

public class AccueilService {
	
	/*
	 * Gestion des médicaments.  
	 */
	public static List<Drug> requestDrug(List<String> listDrugName){
		List<Drug> listDrug = new ArrayList<Drug>();
		
		listDrug = listDrugXML(listDrugName);
		
		listDrug = listDrugSQL(listDrug);
		
		return listDrug;
	}
	
	private static List<Drug> listDrugSQL(List<Drug> listDrug){
		
		SqlParser sqp = new SqlParser();
		for(Drug d : listDrug){
			d.setListAdverseEffectDisease(sqp.getDiseaseAdvEffect(d));
			d.setListIndicationDisease(sqp.getDiseaseIndication(d));
		}
		return listDrug;
	}
	
	private static List<Drug> listDrugXML(List<String> listDrugName){
		List<Drug> listDisease = new ArrayList<Drug>();
		
		try {
			ParserDrugBank pdb = new ParserDrugBank();
			
			for(String s : listDrugName){
				Drug dr = new Drug(s);
				pdb.getDrug(dr);
				listDisease.add(dr);
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listDisease;
	}
	
	/*
	 * Gestion des maladies 
	 */
	public static List<Disease> requestDisease(List<String> listDiseaseName){
		//TODO Parser la requete pour differencier ou et or
		List<Disease> listDisease = new ArrayList<Disease>();
		
		listDisease = getSynonim(listDiseaseName);
		
		listDisease = getDrug(listDisease);
		
		return listDisease;
	}
	
	private static List<Disease> getDrug(List<Disease> listDisease){
		
		
		Task<List<Disease>> taskXml = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				ParserDrugBank pdb = new ParserDrugBank();
				List<Disease> listpdb = listDisease;
				for(Disease d : listpdb){
					d = pdb.getDisease(d);
				}
				return listpdb;
			}
		};
		new Thread(taskXml).start();
	
		taskXml.setOnSucceeded(e->{
			try {
				listDisease.addAll(taskXml.get());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("research xml finish");
		});
		taskXml.setOnFailed(e->{
			taskXml.getException().printStackTrace();
		});
		
		Task<List<Disease>> taskSQL = new Task<List<Disease>>() {
			@Override
			protected List<Disease> call() throws Exception {
				List<Disease> listSQl = listDisease;
				SqlParser sqlP = new SqlParser();
				for(Disease d : listSQl){
					d = sqlP.getAllDrug(d);
				}
				return listSQl;
			}
		};
		new Thread(taskSQL).start();
	
		taskSQL.setOnSucceeded(e->{
			try {
				listDisease.addAll(taskSQL.get());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("research SQL finish");
		});
		taskSQL.setOnFailed(e->{
			taskSQL.getException().printStackTrace();
		});
		
		
		while(!taskSQL.isDone() || !taskXml.isDone());
		
		return listDisease;
	}
	
	private static List<Disease> getSynonim(List<String> listDiseaseName){
		List<Disease> listDisease = new ArrayList<Disease>();

		// A threader pour couch DB
		listDisease.addAll(getCouchDBDisease(listDiseaseName));	
		
		// a Threader pour Txt
		listDisease.addAll(getStringDisease(listDiseaseName));
		
		// a Threader pour Csv 
		//listDisease.addAll(getCSVDisease(listDiseaseName));
		if(listDisease.size() > 0 ){
			for(int i = 0; i < listDisease.size(); i++){
				for(int j = 0; j < listDisease.size(); j++){
					if((i != j) && (((listDisease.get(i).getName() != "" && listDisease.get(i).getName() == listDisease.get(j).getName())
							|| (listDisease.get(i).getCui() != ""  && listDisease.get(i).getCui() == listDisease.get(j).getCui())
							|| (listDisease.get(i).getOmim() != ""  && listDisease.get(i).getOmim() == listDisease.get(j).getOmim())))){
						listDisease.remove(j);
					}
				}
			}
		}
		
		return listDisease;
	}
	
	private static List<Disease> getCouchDBDisease(List<String> listDiseaseName){
		List<Disease> listDisease = new ArrayList<Disease>();
		List<Disease> memoListeDisease = new ArrayList<Disease>();
		
		for(String s : listDiseaseName){
			try {
				memoListeDisease = Couchdb.get_diseases_from_clinical_sign(s);
				if(memoListeDisease != null || memoListeDisease.size() > 0){
					for(Disease d : memoListeDisease){
						try {
							d = Couchdb.get_disease_info(d);
						} catch (CouchdbException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						listDisease.add(d);
					}
				}else{
					Disease d = new Disease();
					d.setName(s);
					try {
						d = Couchdb.get_disease_info(d);
					} catch (CouchdbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listDisease.add(d);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listDisease;
	}
	
	private static List<Disease> getStringDisease(List<String> listDiseaseName){
		List<Disease> listDisease = new ArrayList<Disease>();
		for(String s : listDiseaseName){
			try {
				listDisease.addAll(TextService.getDiseases(s));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return listDisease;
	}

	private static List<Disease> getCSVDisease(List<String> listDiseaseName){
		// todo
		return null;
	}
	
}
