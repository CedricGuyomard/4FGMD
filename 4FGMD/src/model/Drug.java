package model;

public class Drug {

	private String name;
	
	private String[] listSubstance;
	
	private Disease[] listDisease;
	
	private Symptom[] listEffet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getListSubstance() {
		return listSubstance;
	}

	public void setListSubstance(String[] listSubstance) {
		this.listSubstance = listSubstance;
	}

	public Disease[] getListDisease() {
		return listDisease;
	}

	public void setListDisease(Disease[] listDisease) {
		this.listDisease = listDisease;
	}

	public Symptom[] getListEffet() {
		return listEffet;
	}

	public void setListEffet(Symptom[] listEffet) {
		this.listEffet = listEffet;
	}
	
	
}
