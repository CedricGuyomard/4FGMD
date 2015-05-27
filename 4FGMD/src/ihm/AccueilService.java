package ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import model.Disease;

import org.apache.lucene.queryparser.classic.ParseException;
import org.xml.sax.SAXException;

import sql.SqlParser;
import text.TextService;
import xml.ParserDrugBank;
import couchdb.Couchdb;
import couchdb.CouchdbException;

public class AccueilService {
	
	public static List<Disease> Request(List<String> listDiseaseName){
		//TODO Parser la requete pour differencier ou et or
		List<Disease> listDisease = new ArrayList<Disease>();
		
		listDisease = getSynonim(listDiseaseName);
		
		listDisease = getDrug(listDisease);
		
		return listDisease;
	}
	
	private static List<Disease> getDrug(List<Disease> listDisease){
		
		try {
			ParserDrugBank pdb = new ParserDrugBank();
			
			for(Disease d : listDisease){
				d = pdb.getDisease(d);
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SqlParser sqlP = new SqlParser();
		for(Disease d : listDisease){
			d = sqlP.getAllDrug(d);
		}
		
		return listDisease;
	}
	
	private static List<Disease> getSynonim(List<String> listDiseaseName){
		List<Disease> listDisease = new ArrayList<Disease>();

		// A threader pour couch DB
		listDisease.addAll(getCouchDBDisease(listDiseaseName));	
		
		// a Threader pour Txt
		listDisease.addAll(getStringDisease(listDiseaseName));
		
		// a Threader pour Csv 
		//listDisease.addAll(getCSVDisease(listDiseaseName));
		if(listDisease.size() > 0 ){
			for(int i = 0; i < listDisease.size(); i++){
				for(int j = 0; j < listDisease.size(); j++){
					if((i != j) && (((listDisease.get(i).getName() != "" && listDisease.get(i).getName() == listDisease.get(j).getName())
							|| (listDisease.get(i).getCui() != ""  && listDisease.get(i).getCui() == listDisease.get(j).getCui())
							|| (listDisease.get(i).getOmim() != ""  && listDisease.get(i).getOmim() == listDisease.get(j).getOmim())))){
						listDisease.remove(j);
					}
				}
			}
		}
		return listDisease;
	}
	
	private static List<Disease> getCouchDBDisease(List<String> listDiseaseName){
		List<Disease> listDisease = new ArrayList<Disease>();
		List<Disease> memoListeDisease = new ArrayList<Disease>();
		
		for(String s : listDiseaseName){
			try {
				memoListeDisease = Couchdb.get_diseases_from_clinical_sign(s);
				if(memoListeDisease != null || memoListeDisease.size() > 0){
					for(Disease d : memoListeDisease){
						try {
							d = Couchdb.get_disease_info(d);
						} catch (CouchdbException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						listDisease.add(d);
					}
				}else{
					Disease d = new Disease();
					d.setName(s);
					try {
						d = Couchdb.get_disease_info(d);
					} catch (CouchdbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listDisease.add(d);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listDisease;
	}
	
	private static List<Disease> getStringDisease(List<String> listDiseaseName){
		List<Disease> listDisease = new ArrayList<Disease>();
		for(String s : listDiseaseName){
			try {
				listDisease.addAll(TextService.getDiseases(s));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return listDisease;
	}

	private static List<Disease> getCSVDisease(List<String> listDiseaseName){
		// todo
		return null;
	}
	
}
