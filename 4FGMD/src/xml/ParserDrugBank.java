package xml;

import org.xml.sax.*;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class ParserDrugBank {

	private static String FILE = "drugbank.xml";
		
	public ParserDrugBank(String name) throws SAXException, IOException, ParserConfigurationException{
			  SAXParserFactory spf = SAXParserFactory.newInstance();
			  spf.setNamespaceAware(true);
			  SAXParser saxParser = spf.newSAXParser();
			  XMLReader saxReader = saxParser.getXMLReader();
			  DrugBankHandler dbh = new DrugBankHandler(name);
		      saxReader.setContentHandler(dbh);
		      
		      Date deb = new Date();
		      
		      DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		    		 
		      System.out.println("Début de parsage : "  + format.format(deb));
		      saxReader.parse(FILE);
		      
		      Date fin = new Date();
		      System.out.println("Début de parsage : "  + format.format(fin));
	}
	
	/*
	public ParserDrugBank() throws JDOMException, IOException{
		
		Date today = new Date(); 
		
		SAXBuilder jdomBuilder = new SAXBuilder();
		
		DateFormat DateDeb = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		System.out.println("Début du parsage : " + DateDeb.format(today));
		
		jdomDocument = jdomBuilder.build(FILE);
		
		Element test = jdomDocument.getRootElement();
		
		DateFormat DateFin = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		System.out.println("Début du parsage : " + DateFin.format(today));
		
		System.out.println(test.getName());
	} */
	
	
	
	
	public static void main(String[] args){
	
		try {
			ParserDrugBank pdb = new ParserDrugBank("1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
