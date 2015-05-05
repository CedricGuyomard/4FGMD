package model;

import java.util.ArrayList;

public class Drug extends SuperClass{

	private String name;
	
	private ArrayList<String> listSubstance;
	
	private Disease[] listDisease;
	
	private Symptom[] listEffet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getListSubstance() {
		return listSubstance;
	}

	public void setListSubstance(ArrayList<String> listSubstance) {
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
