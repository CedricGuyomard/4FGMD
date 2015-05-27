package ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Disease;

import org.apache.lucene.queryparser.classic.ParseException;

import text.TextService;
import couchdb.Couchdb;
import couchdb.CouchdbException;

public class AccueilService {
	public static List<Disease> Request(List<String> listDiseaseName){
		List<Disease> listDisease = new ArrayList<Disease>();

		// A threader pour couch DB
		listDisease.addAll(getCouchDBDisease(listDiseaseName));	
		
		// a Threader pour Txt
		listDisease.addAll(getStringDisease(listDiseaseName));
		
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

	
	
}
