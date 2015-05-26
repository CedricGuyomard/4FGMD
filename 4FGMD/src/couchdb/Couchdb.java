package couchdb;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import model.Disease;

public class Couchdb {
	
	public Disease get_disease_info(Disease d) throws CouchdbException, IOException
	{
		String s;
		ArrayList<Disease> ar = new ArrayList<Disease>();
		
		if (d.getName() == "")
		{
			throw new CouchdbException("Empty disease name");
		}
		
		String[] cmd = {
				"./couch.py",
				"-d",
				d.getName()
		};
		
		Process python = Runtime.getRuntime().exec(cmd);
		BufferedReader scriptOutput = new BufferedReader(new InputStreamReader(python.getInputStream()));
		s = scriptOutput.readLine();
		System.out.println(s);
		
		String[] tokens = s.split(";");
		d.setSynonym(Arrays.asList(tokens[0].split("\\$")));
		
		for(String str : tokens[1].split("\\$"))
		{
			System.out.println(str + " ");
			Disease tmp = new Disease();
			tmp.setName(str);
			ar.add(tmp);
		}
		
		d.setListSymptom(ar);
		return d;
	}
	
	public ArrayList<Disease> get_diseases_from_clinical_sign(String cs) throws IOException
	{
		ArrayList<Disease> ar_d = new ArrayList<Disease>();
		String s;
		
		String[] cmd = {
				"./couch.py",
				"-c",
				cs
		};
		
		Process python = Runtime.getRuntime().exec(cmd);
		BufferedReader scriptOutput = new BufferedReader(new InputStreamReader(python.getInputStream()));
		s = scriptOutput.readLine();
		System.out.println(s);
		
		String[] tokens = s.split("\\$");
		for(String str : tokens)
		{
			System.out.println(str);
			Disease tmp = new Disease();
			tmp.setName(str);
			ar_d.add(tmp);
		}
		return ar_d;
	}
}
