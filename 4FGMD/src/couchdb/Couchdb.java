package couchdb;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import model.Disease;

public class Couchdb {
	
	public static Disease get_disease_info(Disease d) throws CouchdbException, IOException
	{
		String s;
		ArrayList<Disease> ar = new ArrayList<Disease>();
		
		if (d.getName() == "")
		{
			throw new CouchdbException("Empty disease name");
		}
		
		String[] cmd = {
				"cmd.exe /c python C:\\Users\\cubito\\git\\4FGMD\\4FGMD\\couch.py",
				"-d",
				d.getName()
		};
		Process python = Runtime.getRuntime().exec("cmd.exe /c python C:\\Users\\cubito\\git\\4FGMD\\4FGMD\\couch.py -d " + d.getName() );
		BufferedReader scriptOutput = new BufferedReader(new InputStreamReader(python.getInputStream()));
		s = scriptOutput.readLine();
		System.out.println(s);
		
		// Parse the line given by the python script
		String[] tokens = s.split(";");
		d.setSynonym(Arrays.asList(tokens[0].split("\\$")));
		
		if(!tokens[1].equals("empty"))
		{
			for(String str : tokens[1].split("\\$"))
			{
				Disease tmp = new Disease();
				tmp.setName(str);
				ar.add(tmp);
			}
		}
		
		d.setListSymptom(ar);
		
		if(!tokens[2].equals("empty")){
			d.setCui(tokens[2]);
		}
		
		if(!tokens[3].equals("empty")){
			d.setOmim(tokens[3]);
		}
		
		return d;
	}
	
	public static ArrayList<Disease> get_diseases_from_clinical_sign(String cs) throws IOException
	{
		ArrayList<Disease> ar_d = new ArrayList<Disease>();
		String s;
		
		String[] cmd = {
				"cmd.exe /c python C:\\Users\\cubito\\git\\4FGMD\\4FGMD\\couch.py",
				"-c",
				cs
		};
		System.out.println(cmd);
		Process python = Runtime.getRuntime().exec("cmd.exe /c python C:\\Users\\cubito\\git\\4FGMD\\4FGMD\\couch.py -c " + cs );
		BufferedReader scriptOutput = new BufferedReader(new InputStreamReader(python.getInputStream()));
		s = scriptOutput.readLine();
		if(s != null)
		{
			if (!s.equals("empty"))
			{
				// Parse the line given by the python script
				for(String str : s.split("\\$"))
				{
					Disease tmp = new Disease();
					tmp.setName(str);
					ar_d.add(tmp);
				}
	}
		}
		return ar_d;
	}
}
