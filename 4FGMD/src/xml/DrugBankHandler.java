package xml;


import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class DrugBankHandler implements ContentHandler {

	private boolean name;
	
	private boolean toxicity;
	
	private boolean indication;
	
	private int test;
	
	public DrugBankHandler(){
		test = 0;
		
	}
	public void characters(char[] ch, int start, int length) throws SAXException {
		
			if(toxicity){
				if(test == 1){
				System.out.println("<toxicity>" + new String(ch, start, length) );
				test++;
				}
			}
			if(indication){
				if(test == 0){
				System.out.println("<indication>" + new String(ch, start, length));
				test++;
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
		// TODO Auto-generated method stub
		if(element.compareTo("toxicity") == 0){
			toxicity = true;
		}
		if(element.compareTo("name") == 0){
			name = true;
		}
		if(element.compareTo("indication") == 0){
			indication = true;
		}
		
	}

	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	
}
