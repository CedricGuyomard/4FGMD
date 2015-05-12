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
		
	}
	
	private ResultSet SqlRequest (String request)
	{
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
			rs = st.executeQuery(request);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				cn.close();
				st.close();
			}catch(final SQLException e)
			{
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	public Disease getDrugIndication(Disease maladie)
	{
		String request;
		ArrayList<Drug> listDrug = new ArrayList<Drug>();
		ResultSet rs;
		request = "SELECT Distinct(drug_name1)"
				+ "FROM label_mapping lm, indications_raw lr "
				+ "WHERE lm.label = lr.label AND lr.i_name = \""+ maladie.getName()+"\";";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null )
			{
				while(rs.next())
				{
					listDrug.add(new Drug(rs.getString(0)));
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		request = "SELECT Distinct(drug_name2)"
				+ "FROM label_mapping lm, indications_raw lr "
				+ "WHERE lm.label = lr.label AND lr.i_name = \""+ maladie.getName()+"\";";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null)
			{
				while(rs.next())
				{
					listDrug.add(new Drug(rs.getString(0)));
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
		request = "SELECT Distinct(drug_name1)"
				+ "FROM label_mapping lm, adverse_effects_raw lr "
				+ "WHERE lm.label = lr.label AND lr.se_name = \""+ maladie.getName()+"\";";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null)
			{
				while(rs.next())
				{
					listDrug.add(new Drug(rs.getString(0)));
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		request = "SELECT Distinct(drug_name2)"
				+ "FROM label_mapping lm, adverse_effects_raw lr "
				+ "WHERE lm.label = lr.label AND lr.se_name = \""+ maladie.getName()+"\";";
		try
		{
			rs = this.SqlRequest(request);
			if(rs != null)
			{
				while(rs.next())
				{
					listDrug.add(new Drug(rs.getString(0)));
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		maladie.setListDrugAdverseEffect(listDrug);
			return maladie;
	}
	
}
