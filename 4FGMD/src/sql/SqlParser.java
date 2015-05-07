package sql;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Disease;
import model.Drug;

public class SqlParser {

	String url = "neptune.telcomnancy.univ-lorraine.fr";
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
	
	private ArrayList<Drug> getDrugIndication(Disease maladie)
	{
		String request;
		ArrayList<Drug> listDrug = new ArrayList();
		ResultSet rs;
		request = "SELECT Distinct(drug_name1)"
				+ "FROM label_mapping lm, indications_raw lr "
				+ "WHERE lm.label = lr.label AND lr.i_name = "+ maladie.getName()+";";
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
				+ "FROM label_mapping lm, indications_raw lr "
				+ "WHERE lm.label = lr.label AND lr.i_name = "+ maladie.getName()+";";
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
			return listDrug;
		
	}

}
