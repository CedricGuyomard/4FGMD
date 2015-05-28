package text;

import model.Disease;

public class TextItem {
	private String search;
	private String NO;
	private String TI;
	private String TX;
	private String CS;
	public TextItem() {
	}
	public TextItem(String search) {
		this.search=search;
	}
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getNO() {
		return NO;
	}
	public void setNO(String nO) {
		NO = nO;
	}
	public String getTI() {
		return TI;
	}
	public void setTI(String tI) {
		TI = tI;
	}
	public String getTX() {
		return TX;
	}
	public void setTX(String tX) {
		TX = tX;
	}
	public String getCS() {
		return CS;
	}
	public void setCS(String cS) {
		CS = cS;
	}
	
}
