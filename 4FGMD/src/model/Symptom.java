package model;

public class Symptom extends SuperClass{

	/// Nom du symptome
	private String name;
	
	/// Subjectif - Objectif
	private String observation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
}
