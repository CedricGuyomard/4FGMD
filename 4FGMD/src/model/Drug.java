package model;

import java.util.ArrayList;

public class Drug extends SuperClass{

	private String name;
	
	private String description;
	
	private ArrayList<String> synonym;
	
	public Drug(String name)
	{
		this.name = name;
		this.synonym = new ArrayList<String>();
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
	
}
