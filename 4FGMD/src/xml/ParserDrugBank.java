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

	//private static String FILE = "drugbank.xml";
		
	private static String FILE = "BaliseDrug.xml";
	private XMLReader saxReader;
	
	public ParserDrugBank() throws SAXException, IOException, ParserConfigurationException{
			  
	}
	
	/*
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
	*/
	
	public Disease getDisease(Disease d) throws SAXException, ParserConfigurationException{
		  SAXParserFactory spf = SAXParserFactory.newInstance();
		  spf.setNamespaceAware(true);
		  SAXParser saxParser = spf.newSAXParser();
		  saxReader = saxParser.getXMLReader();    
		  
		  Disease disease_retour = null;

		  DrugBankHandler dbh = new DrugBankHandler(d);
	      saxReader.setContentHandler(dbh);
	      
	      Date deb = new Date();
	      
	      DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	    		 
	      System.out.println("Début de parsage : "  + format.format(deb));
	      try {
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
			Disease d = new Disease();
			d.setName("heparin-induced thrombocytopenia");
			Disease retour = pdb.getDisease(d);
			
			ArrayList<Drug> test = retour.getListDrugAdverseEffect();
			List<Drug> test1 =  retour.getListDrugIndication();
			
			for(int i = 0; i < test.size(); i++){
				System.out.println("Drug trouvé  : "+ test.get(i).getName());
			}
			for(int i = 0; i < test1.size(); i++){
				System.out.println("Drug trouvé  : "+ test1.get(i).getName());
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
