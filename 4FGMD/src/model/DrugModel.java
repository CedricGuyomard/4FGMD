package model;

public class DrugModel {

	private String DrugName;
	
	private String indication;
	
	private String toxicity;

	public String getDrugName() {
		return DrugName;
	}

	public void setDrugName(String drugName) {
		DrugName = drugName;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getToxicity() {
		return toxicity;
	}

	public void setToxicity(String toxicity) {
		this.toxicity = toxicity;
	}
	
	
}
