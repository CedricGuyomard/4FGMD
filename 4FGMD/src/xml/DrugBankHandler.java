package xml;


import java.util.ArrayList;

import model.*;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.LocatorImpl;


public class DrugBankHandler implements ContentHandler {

	private Disease dis;
	
	private String requete;
	
	private String type;
	
	private String name;
	
	private boolean node_name;
	
	private String toxicity;
	
	private boolean node_toxicity;

	private String indication;
	
	private boolean node_indication;
	
	private boolean match;
	
	private ArrayList<Drug> listDrug;
	
	private Locator locator;
	
	public DrugBankHandler(Disease d){
		this.dis = d;
		this.requete = dis.getName();
		listDrug = new ArrayList<Drug>();
		match = false;
		locator = new LocatorImpl();
	}
	
	
	public void characters(char[] ch, int start, int length) throws SAXException {
			String chara = new String(ch,start,length);
			// C'est une node name, donc le nom d'un médicament
			if(node_name){
				String drugName = new String(ch,start,length);
				// Si c'est le type de la recherche
				if(type == "Drug" && drugName.contains(requete)){
					match = true;
				}
				this.name = drugName;
				
			}
			
			//C'est une node indication -> on peux trouver le nom de la maladie qui est un effet secondaire ducoup ...
			if(node_indication){
				String indicationCourant = new String(ch,start,length);
				if(type == "Disease" && indicationCourant.contains(requete)){
					// On sauvgarde le noeud car il est avant dans la lecture
					match = true;	
				}
				this.indication = indicationCourant;
			}
			
			//C'est un effet secondaire 
			if(node_toxicity){
				String toxicityCourant = new String(ch,start,length);
				if(type == "Disease" && toxicityCourant.contains(requete)){	
				  match = true;
				}
				if(match){
					  //On a fini de lire les informations utiles
					  //On remplit le modele
					  this.addDrug();
					  
				}
				this.toxicity = toxicityCourant;
			}
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startElement(String uri, String element, String qualif,
			Attributes attr) throws SAXException {
		// TODO Auto-generated method sub
		
		if(element.compareTo("drug") == 0){
			match = false;
		}
		if(element.compareTo("toxicity") == 0){
			node_toxicity = true;
		}
		if(element.compareTo("name") == 0){
			node_name = true;
		}
		if(element.compareTo("indication") == 0){
			node_indication = true;
		}
		
	}

	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Drug> getResult(){
		return this.listDrug;
	}
	
	public void addDrug(){
		Drug d = new Drug();
		d.setName(name);
		Symptom s = new Symptom();
		s.setDescription(toxicity);
		d.addEffet(s);
		Disease dis = new Disease();
		dis.setDescription(indication);
		d.addDisease(dis);
		this.listDrug.add(d);
	}
	
}
