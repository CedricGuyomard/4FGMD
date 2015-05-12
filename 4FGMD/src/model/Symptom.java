package model;

public class Symptom extends SuperClass{

	/// Nom du symptome
	private String name;
	
	/// Description du symptome
	private String description;
	
	/// Subjectif - Objectif
	private String observation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String d) {
		this.description = d;
	}

	
	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
}
