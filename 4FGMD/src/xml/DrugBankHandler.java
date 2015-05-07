package xml;


import model.*;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class DrugBankHandler implements ContentHandler {

	private String requete;
	
	private String type;
	
	private boolean node_name;
	
	private boolean node_toxicity;

	private boolean node_indication;
	
	private boolean match;
	
	public DrugBankHandler(String req){
		requete = req;
		Drug d = new Drug();
		match = false;
	}
	
	
	public void characters(char[] ch, int start, int length) throws SAXException {
			// C'est une node name, donc le nom d'un médicament
			if(node_name){
				String drugName = new String(ch,start,length);
				// Si c'est le type de la recherche
				if(type == "Drug" && drugName.contains(requete)){
					match = true;
				}
				
			}
			
			//C'est une node indication -> on peux trouver le nom de la maladie qui est un effet secondaire ducoup ...
			if(node_indication){
				String indicationCourant = new String(ch,start,length);
				if(type == "Disease" && indicationCourant.contains(requete)){
					// On sauvgarde le noeud car il est avant dans la lecture
					match = true;	
				}
			}
			
			//C'est un effet secondaire 
			if(node_toxicity){
				String toxicityCourant = new String(ch,start,length);
				if(type == "Disease" && toxicityCourant.contains(requete)){	
				  match = true;
				  //On a fini de lire les informations utiles
				  //On remplit le modele
				}
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

	
}
