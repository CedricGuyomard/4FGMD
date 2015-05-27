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
				Disease dis = new Disease();
				dis.setDescription(doc.get(IndexText.column.TI.getValue()));
				dis.setName(doc.get(IndexText.column.NO.getValue()));
				return dis;
			}).collect(Collectors.toList());
		}
		return new ArrayList<Disease>();
	}
}
