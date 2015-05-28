package xml;

import org.xml.sax.*;

import model.Disease;
import model.Drug;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class ParserDrugBank {

	private static String FILE = "drugbank.xml";
		
	//private static String FILE = "BaliseDrug.xml";
	private XMLReader saxReader;
	
	public ParserDrugBank() throws SAXException, IOException, ParserConfigurationException{
			  
	}
	
	public Disease getDisease(Disease d) throws SAXException, ParserConfigurationException{
	      //Création du parser
		  SAXParserFactory spf = SAXParserFactory.newInstance();
		  spf.setNamespaceAware(true);
		  SAXParser saxParser = spf.newSAXParser();
		  saxReader = saxParser.getXMLReader();    
		  
		  Disease disease_retour = null;

		  //Création du handler
		  DrugBankHandler dbh = new DrugBankHandler(d);
	      saxReader.setContentHandler(dbh);
	      
	      Date deb = new Date();
	      
	      DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	    		 
	      System.out.println("Début de parsage : "  + format.format(deb));
	      try {
	    	//Parsage
			saxReader.parse(FILE);
			disease_retour = dbh.getResult();
	      } catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      Date fin = new Date();
	      System.out.println("Fin de parsage : "  + format.format(fin));
	      
	      return disease_retour;
	}
	
	//Méthode à appeler pour recherche un médicament dans le fichier XML
	public ArrayList<Disease> getDrug(Drug d) throws SAXException, ParserConfigurationException{
		  // Création du parser
		  SAXParserFactory spf = SAXParserFactory.newInstance();
		  spf.setNamespaceAware(true);
		  SAXParser saxParser = spf.newSAXParser();
		  saxReader = saxParser.getXMLReader();    
		  
		  ArrayList<Disease> disease_retour = new ArrayList<Disease>();
		  //Création du handler
		  DrugBankHandler dbh = new DrugBankHandler(d);
	      saxReader.setContentHandler(dbh);
	      
	      Date deb = new Date();
	      
	      DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	    		 
	      System.out.println("Début de parsage : "  + format.format(deb));
	      try {
	    	//Début de la recherche
			saxReader.parse(FILE);
			disease_retour = dbh.getListResult();
	      } catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      Date fin = new Date();
	      System.out.println("Fin de parsage : "  + format.format(fin));
	      
	      return disease_retour;
	}
	
	

}
