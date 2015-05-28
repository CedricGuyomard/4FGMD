package model;

import java.util.ArrayList;
import java.util.List;

public class Drug extends SuperClass{

	private String name;
	
	private String description;
	
	private ArrayList<String> synonym;
	
	private List<Disease> listIndicationDisease;
	
	private List<Disease> listAdverseEffectDisease;
	
	public Drug(String name)
	{
		this.name = name;
		this.synonym = new ArrayList<String>();
		this.listAdverseEffectDisease = new ArrayList<Disease>();
		this.listIndicationDisease = new ArrayList<Disease>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getSynonym() {
		return synonym;
	}

	public void setSynonym(ArrayList<String> synonym) {
		this.synonym = synonym;
	}

	public List<Disease> getListIndicationDisease() {
		return listIndicationDisease;
	}

	public void setListIndicationDisease(List<Disease> listIndicationDisease) {
		this.listIndicationDisease = listIndicationDisease;
	}

	public List<Disease> getListAdverseEffectDisease() {
		return listAdverseEffectDisease;
	}

	public void setListAdverseEffectDisease(List<Disease> listAdverseEffectDisease) {
		this.listAdverseEffectDisease = listAdverseEffectDisease;
	}
	
}
