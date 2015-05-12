package xml;

import org.xml.sax.*;

import model.Disease;
import model.Drug;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class ParserDrugBank {

	private static String FILE = "drugbank.xml";
		
	private XMLReader saxReader;
	
	public ParserDrugBank() throws SAXException, IOException, ParserConfigurationException{
			  
	}
	
	
	public ArrayList<Drug> getMedic(String name) throws SAXException, ParserConfigurationException{
		  SAXParserFactory spf = SAXParserFactory.newInstance();
		  spf.setNamespaceAware(true);
		  SAXParser saxParser = spf.newSAXParser();
		  saxReader = saxParser.getXMLReader();    
		  
		  ArrayList<Drug> listeDrug = new ArrayList<Drug>();
		  Disease disease = new Disease();
		  disease.setName("");
		  DrugBankHandler dbh = new DrugBankHandler(disease);
	      saxReader.setContentHandler(dbh);
	      
	      Date deb = new Date();
	      
	      DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	    		 
	      System.out.println("Début de parsage : "  + format.format(deb));
	      try {
			saxReader.parse(FILE);
			 listeDrug = dbh.getResult();
	      } catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      Date fin = new Date();
	      System.out.println("Fin de parsage : "  + format.format(fin));
	      
	      return listeDrug;
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
			ParserDrugBank pdb = new ParserDrugBank();
			ArrayList<Drug> test = pdb.getMedic("Lepirudin");
			for(int i = 0; i < test.size(); i++){
				System.out.println("Nom : "+ test.get(i).getName());
				System.out.println("Effet secondaire(toxicity) : "+ test.get(i).getListEffet()[0].getDescription());
				System.out.println("Indication : "+ test.get(i).getListDisease()[0].getDescription());
			}
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
