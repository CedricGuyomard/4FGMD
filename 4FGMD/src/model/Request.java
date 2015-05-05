package model;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ParserDrugBank;

public class Request {

	/*
	public SuperClass categorie;
	
	public String querry;
	
	public Request(String q, String t){
		querry = q;
		switch(t){
		case "Disease":
			categorie = new Disease();
			categorie.setType("Disease");
		break;
		case "Drug":
			categorie = new Drug();
			categorie.setType("Drug");
			break;
		case "Symptom":
			categorie = new Symptom();
			categorie.setType("Symptom");
			break;
		}
		
		ParserDrugBank pdb = new ParserDrugBank(type, querry);
		
	} */
	
	private String querry;
	
	private String type;
	
	public Request(String q, String t){
		//Étape 1 : On va vérifier si la requête est OK
		
		//Étape 2 : On parse la requête
		ArrayList<String> liste_mot = new ArrayList<String>();
		while(q.contains("ET") || q.contains("OU")){
			if(q.contains("OU")){
				liste_mot.add(q.substring(0, q.indexOf("OU")).trim());
				q = q.substring(q.indexOf("OU") + 1);
			}
			if(q.contains("ET")){
				liste_mot.add(q.substring(0, q.indexOf("ET")).trim());
				q = q.substring(q.indexOf("ET") + 1);
			}
		}
		liste_mot.add(q);
			
		
		//Étape 3 : On va consulter les différentes bases pour savoir si on trouve ce que l'on cherche
		switch(type){
		case "Disease":
			
			
		break;
		case "Drug":
		 //# XML ou BDD en premier ?
			try {
				ParserDrugBank pdb = new ParserDrugBank("coucou");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
		case "Symptom":
			
		break;
			
		}
	}
	
	public static void main(String[] args){
		
		
	}
}
