package model;

import java.util.ArrayList;

public class Disease extends SuperClass {

	///Nom de la maladie
	private String name;
	
	///Description - xml
	private String description;
	
	///Liste des synonymes
	private String[] synonym;
	
	///Liste des symptomes
	private ArrayList<Disease> listSymptom;
	
	///Liste de medicament qui la soigne
	private ArrayList<Drug> listDrugIndication;
	
	///Liste de medicament qui la soigne
	private ArrayList<Drug> listDrugAdverseEffect;
	
	///Origine de la maladie : Social, psyco, physique
	private String origine;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getSynonym() {
		return synonym;
	}

	public void setSynonym(String[] synonym) {
		this.synonym = synonym;
	}

	public  ArrayList<Disease> getListSymptom() {
		return listSymptom;
	}

	public void setListSymptom( ArrayList<Disease> listSymptom) {
		this.listSymptom = listSymptom;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public ArrayList<Drug> getListDrugIndication() {
		return listDrugIndication;
	}

	public void setListDrugIndication(ArrayList<Drug> listDrugIndication) {
		this.listDrugIndication = listDrugIndication;
	}

	public ArrayList<Drug> getListDrugAdverseEffect() {
		return listDrugAdverseEffect;
	}

	public void setListDrugAdverseEffect(ArrayList<Drug> listDrugAdverseEffect) {
		this.listDrugAdverseEffect = listDrugAdverseEffect;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
