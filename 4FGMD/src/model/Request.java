package model;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ParserDrugBank;

public class Request {

	private String querry;
	
	private String type;
	
	public Request(String q, String t){
		//TODO
		//Étape 1 : On va vérifier si la requête est OK 
		
		//Étape 2 : On parse la requête
		String mot_courant;
		ArrayList<String> liste_chaine = new ArrayList<String>();
		while(q.contains("OU")){
				liste_chaine.add(q.substring(0, q.indexOf("OU") - 1));
				//System.out.println("Chaine a parser : " + q.substring(0, q.indexOf("OU") - 1));
				q = q.substring(q.indexOf("OU") + 3 , q.length());
				//System.out.println("Reste : " + q);
		}
		liste_chaine.add(q);
		
		ArrayList<String> liste_mot = new ArrayList<String>();
		String chaine;
		for(int i = 0; i < liste_chaine.size(); i++){
			chaine = liste_chaine.get(i);
			while(q.contains("ET")){
				liste_mot.add(q.substring(0, chaine.indexOf("ET") - 1));
				chaine = chaine.substring(chaine.indexOf("ET") + 3, chaine.length());
			}
			liste_mot.add(chaine);
			
			for(int j = 0; j < liste_mot.size(); j++){
				
				Disease dis = new Disease();
				if(t == "Disease"){
					dis.setName(liste_mot.get(j));
				}
				//Appel des bases
			}
			
		}
	
	}
	
}
