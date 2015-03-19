package xml;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class ParserDrugBank {

	private static String file = "drugbank.xml";
		
	public ParserDrugBank() throws SAXException, IOException, ParserConfigurationException{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader saxReader = saxParser.getXMLReader();
        saxReader.setContentHandler(new DrugBankHandler());
        saxReader.parse(file);
	}
	
	
	
	
	public static void main(String[] args){
	
		try {
			ParserDrugBank pdb = new ParserDrugBank();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
