package model;

import java.util.ArrayList;
import java.util.List;

public class Disease extends SuperClass {

	///Nom de la maladie
	private String name;
	
	// CUI
	private String cui;
	
	///Description - xml
	private String description;
	
	///Liste des synonymes
	private List<String> synonym;
	
	///Liste des symptomes
	private List<Disease> listSymptom;
	
	///Liste de medicament qui la soigne
	private List<Drug> listDrugIndication;
	
	///Liste de medicament qui la cause
	private ArrayList<Drug> listDrugAdverseEffect;
	
	///Origine de la maladie : Social, psyco, physique
	private String origine;

	
	public Disease(){
		listSymptom = new ArrayList<Disease>();
		listDrugIndication = new ArrayList<Drug>();
		listDrugAdverseEffect = new ArrayList<Drug>();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSynonym() {
		return synonym;
	}

	public void setSynonym(List<String> synonym) {
		this.synonym = synonym;
	}

	public  List<Disease> getListSymptom() {
		return listSymptom;
	}

	public void setListSymptom( List<Disease> listSymptom) {
		this.listSymptom = listSymptom;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public List<Drug> getListDrugIndication() {
		return listDrugIndication;
	}

	public void setListDrugIndication(ArrayList<Drug> listDrugIndication) {
		this.listDrugIndication = listDrugIndication;
	}

	public void addListDrugIndication(Drug d){
		this.listDrugIndication.add(d);
	}
	
	public ArrayList<Drug> getListDrugAdverseEffect() {
		return listDrugAdverseEffect;
	}
	
	public void setListDrugAdverseEffect(ArrayList<Drug> listDrugAdverseEffect) {
		this.listDrugAdverseEffect = listDrugAdverseEffect;
	}
	
	public void addListDrugAdverseEffect(Drug d){
		this.listDrugAdverseEffect.add(d);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
