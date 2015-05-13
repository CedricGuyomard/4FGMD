package model;

import java.util.ArrayList;

public class Drug extends SuperClass{

	private String name;
	
	private ArrayList<String> listSubstance;
	
	private Disease[] listDisease;
	
	private Symptom[] listEffet;
	
	public Drug()
	{
		
	}
	public Drug(String name)
	{
		this.name = name;
	}
	
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
	
	public void addDisease(Disease d){
		for(int i = 0; i < this.listDisease.length; i++){
			if(this.listDisease[i] == null){
				this.listDisease[i] = d;
				break;
			}
		}
	}

	public Symptom[] getListEffet() {
		return listEffet;
	}

	public void setListEffet(Symptom[] listEffet) {
		this.listEffet = listEffet;
	}
	
	public void addEffet(Symptom s){
		for(int i = 0; i < this.listEffet.length; i++){
			if(this.listEffet[i] == null){
				this.listEffet[i] = s;
				break;
			}
		}
	}
	
}
