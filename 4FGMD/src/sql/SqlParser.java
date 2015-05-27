package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Disease;
import model.Drug;

public class SqlParser {

	String url = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr:3306/gmd";
	String login = "gmd-read";
	String passwd = "esial";
	String bdName = "gmd";
	Connection cn;
	Statement st;
	
	public SqlParser ()
	{
				try
		{
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private ResultSet SqlRequest (String request)
	{
		ResultSet rs = null;

		try {
			rs = st.executeQuery(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
	
	public Disease getAllDrug(Disease maladie)
	{
		maladie = getDrugIndication(maladie);
		maladie = getDrugAdverseEffect(maladie);
		
		return maladie;
	}
	
	public Disease getDrugIndication(Disease maladie)
	{
		String request;
		ArrayList<Drug> listDrug = new ArrayList<Drug>();
		ResultSet rs;
		
		ArrayList<String> listNameDrug = new ArrayList<String>();
		for(Drug d : maladie.getListDrugIndication()){
			listNameDrug.add(d.getName());
		}
		
		
		request = "SELECT Distinct(drug_name2), lr."
				+ "FROM label_mapping lm, indications_raw lr "
				+ "WHERE lm.label = lr.label AND lr.i_name LIKE %upper(\""+ maladie.getName()+"\")%;";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null)
			{
				while(rs.next())
				{
					if(!listNameDrug.contains(rs.getString(1))){
						listDrug.add(new Drug(rs.getString(1)));
						listNameDrug.add(rs.getString(1));
					}
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		maladie.setListDrugIndication(listDrug);
			return maladie;
		
	}

	public Disease getDrugAdverseEffect(Disease maladie)
	{
		String request;
		ArrayList<Drug> listDrug = new ArrayList<Drug>();
		ResultSet rs;
		
		ArrayList<String> listNameDrug = new ArrayList<String>();
		for(Drug d : maladie.getListDrugAdverseEffect()){
			listNameDrug.add(d.getName());
		}
		
		request = "SELECT Distinct(drug_name2)"
				+ "FROM label_mapping lm, adverse_effects_raw lr "
				+ "WHERE lm.label = lr.label AND AND lr.se_name LIKE %upper(\""+ maladie.getName()+"\")%;";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null)
			{
				while(rs.next())
				{
					if(!listNameDrug.contains(rs.getString(1))){
						listDrug.add(new Drug(rs.getString(1)));
						listNameDrug.add(rs.getString(1));
					}
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		maladie.setListDrugAdverseEffect(listDrug);
			return maladie;
	}
	
	public ArrayList<Disease> getDiseaseIndication(Drug drug)	{
		String request;
		ArrayList<Disease> listDisease = new ArrayList<Disease>();
		ResultSet rs;
		Disease ds = new Disease();
		
		request = "SELECT Distinct(i_name)"
				+ "FROM label_mapping lm, indications_raw lr "
				+ "WHERE lm.label = lr.label AND AND lm.drug_name2 LIKE %upper(\""+ drug.getName() +"\")%;";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null)
			{
				while(rs.next())
				{
					ds.setName(rs.getString(1));
					ds = this.getAllDrug(ds);
					listDisease.add(ds);
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return listDisease;
	}
	
	public ArrayList<Disease> getDiseaseAdvEffect(Drug drug)	{
		String request;
		ArrayList<Disease> listDisease = new ArrayList<Disease>();
		ResultSet rs;
		Disease ds = new Disease();
		
		request = "SELECT Distinct(se_name)"
				+ "FROM label_mapping lm, adverse_effects_raw lr "
				+ "WHERE lm.label = lr.label AND AND upper(lm.drug_name2) LIKE %upper(\""+ drug.getName() +"\")%;";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null)
			{
				while(rs.next())
				{
					ds.setName(rs.getString(1));
					ds = this.getAllDrug(ds);
					listDisease.add(ds);
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return listDisease;
	}
	
	public void CloseDB()
	{
		try {
			this.cn.close();
			this.st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
