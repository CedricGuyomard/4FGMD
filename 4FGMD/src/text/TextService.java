package text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.ListProperty;
import model.Disease;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

public class TextService {

	public static List<Disease> getDiseases(String name) throws IOException, ParseException{
		if(StringUtils.isNotBlank(name)){
		ListProperty<Document> list = IndexText.get(IndexText.column.TI.getValue(),name);
		return list.stream().map(doc->{
				TextItem ti = new TextItem();
				ti.setNO(doc.get(IndexText.column.NO.getValue()));
				ti.setTI(doc.get(IndexText.column.TI.getValue()));
				ti.setTX(doc.get(IndexText.column.TX.getValue()));
				ti.setCS(doc.get(IndexText.column.CS.getValue()));
				return ti;
			}).map(ti->TextService.textItemToDisease(ti)).filter(o-> o != null).collect(Collectors.toList());
		}
		return new ArrayList<Disease>();
	}
	public static List<Disease> getDiseasesBySign(String sign) throws IOException, ParseException{
		if(StringUtils.isNotBlank(sign)){
		ListProperty<Document> list = IndexText.get(IndexText.column.CS.getValue(),sign);
		return list.stream().map(doc->{
				TextItem ti = new TextItem();
				ti.setNO(doc.get(IndexText.column.NO.getValue()));
				ti.setTI(doc.get(IndexText.column.TI.getValue()));
				ti.setTX(doc.get(IndexText.column.TX.getValue()));
				ti.setCS(doc.get(IndexText.column.CS.getValue()));
				return ti;
			}).map(ti->TextService.textItemToDisease(ti)).filter(o-> o != null).collect(Collectors.toList());
		}
		return new ArrayList<Disease>();
	}

	public static List<Disease> getDiseasesBySigns(List<String> sign) throws IOException, ParseException{
		List<Disease> list = new ArrayList<Disease>();
		for(String str : sign){
			list.addAll(getDiseasesBySign(str));
		}
		return list;
	}

	public static Disease textItemToDisease(TextItem ti) {
		Disease dis = new Disease();
		dis=parseTI(ti, dis);
		dis=parseTX(ti, dis);
		dis=parseCS(ti, dis);
		return dis;
	}
	private static Disease parseTI(TextItem ti,Disease dis){
		boolean nameNotSet = true;
		String id ="";
		for(String syn :ti.getTI().split(";;")){
			String[] syns = syn.split(";");
			for(String term : syns){
				dis.getSynonym().add(term);
			}
			if(nameNotSet){
				dis.setName("");
				String[] split = syns[0].trim().split(" ");
				id=split[0];
				for(int i=1;i<split.length;i++){
					dis.setName(dis.getName()+" "+split[i]);
				}
				dis.setName(dis.getName().trim());
				nameNotSet=false;
				//Pour supprimer l'id présent dans le premier nom
				dis.getSynonym().set(0, dis.getName());
			}
		}
		if(StringUtils.containsIgnoreCase(dis.getName(), "MOVED TO")){
			dis.setName(ti.getNO().trim());
			dis.getSynonym().clear();
		}
		return dis;
	}
	private static Disease parseTX(TextItem ti,Disease dis){
		dis.setDescription(ti.getTX());
		return dis;
	}
	private static Disease parseCS(TextItem ti,Disease dis){
		//dis.setDescription(ti.getCS());
		return dis;
	}
}
