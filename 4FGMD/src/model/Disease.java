package model;

public class Disease extends SuperClass {

	///Nom de la maladie
	private String name;
	
	///Description - xml
	private String description;
	
	///Liste des synonymes
	private String[] synonym;
	
	///Liste des symptomes
	private Symptom[] listSymptom;
	
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

	public Symptom[] getListSymptom() {
		return listSymptom;
	}

	public void setListSymptom(Symptom[] listSymptom) {
		this.listSymptom = listSymptom;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
