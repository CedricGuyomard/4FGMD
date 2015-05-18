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
	
	private String name;
	
	private boolean node_name;
	
	private String descr;
	
	private boolean node_descr;
	
	private String toxicity;
	
	private boolean node_toxicity;

	private String indication; 
	
	private boolean node_indication;
	
	private ArrayList<String> synonym;
	
	private boolean node_synonym;
	
	private boolean match_tox;
	
	private boolean match_ind;
	
	private ArrayList<Drug> listDrug;
	
	private int indentation;
	
	public DrugBankHandler(Disease d){
		this.dis = d;
		this.requete = dis.getName();
		synonym = new ArrayList<String>();
		match_tox = false;
		match_ind = false;
		indentation = -1;
	}
	
	
	public void characters(char[] ch, int start, int length) throws SAXException {
			String chara = new String(ch,start,length);
			// C'est une node name, donc le nom d'un médicament
			if(node_name){
				String drugName = new String(ch,start,length);
				// Si c'est le type de la recherche
				this.name = drugName;
			}
			
			if(node_descr){
				String desc = new String(ch,start,length);
				// Si c'est le type de la recherche
				this.descr = desc;
			}
			
			
			//C'est une node indication -> on peux trouver le nom de la maladie qui est un effet secondaire 
			if(node_indication){
				String indicationCourant = new String(ch,start,length);
				if(indicationCourant.contains(requete)){
					// On sauvgarde le noeud car il est avant dans la lecture
					match_ind = true;	
				}
				this.indication = indicationCourant;
			}
			
			//C'est un effet secondaire 
			if(node_toxicity){
				String toxicityCourant = new String(ch,start,length);
				if(toxicityCourant.contains(requete)){	
				  match_tox = true;
				}
				this.toxicity = toxicityCourant;
			}
			
			if(node_synonym){
				String syno = new String(ch,start,length);
				this.synonym.add(syno) ;
			}
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
		
		node_indication = false;
		node_name = false;
		node_synonym = false;
		node_toxicity = false;
		if(arg1.compareTo("drug") == 0 && indentation == 0){
			if(match_tox || match_ind){
				Drug d = new Drug(this.name);
				d.setDescription(this.descr);
				d.setSynonym(this.synonym);
				if(match_tox)
					this.dis.addListDrugAdverseEffect(d);
				if(match_ind)
					this.dis.addListDrugIndication(d);
			}
		}
		indentation--;
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
		indentation++;
	
		if(element.compareTo("description") == 0 && indentation == 1){
			node_descr = true;
		}
		
		if(element.compareTo("toxicity") == 0 && indentation == 1){
			node_toxicity = true;
		}
		
		if(element.compareTo("name") == 0 && indentation == 1){
			node_name = true;
			
		}
		if(element.compareTo("indication") == 0 && indentation == 1){
			node_indication = true;
			
		}
		if(element.compareTo("synonym") == 0 && indentation == 2){
			node_synonym = true;
		}
		
	}

	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public Disease getResult(){
		return this.dis;
	}
	

	
}
