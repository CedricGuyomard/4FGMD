package model;

import java.util.Scanner;

import sql.SqlParser;

public class testsql {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
		Disease maladie = new Disease();
		Scanner sc = new Scanner(System.in);
		SqlParser sqp = new SqlParser();
		
		System.out.println("Ecrire le nom de la maladie : ");
		String str = sc.nextLine();
		maladie.setName(str);
		
		
		sqp.getDrugIndication(maladie);
		sqp.getDrugAdverseEffect(maladie);
		for(Drug d : maladie.getListDrugIndication())
		{
			System.out.println(d.getName());
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	
		for(Drug d : maladie.getListDrugAdverseEffect())
		{
			System.out.println(d.getName());
		}
		
		sqp.CloseDB();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Fin");
	}

}
