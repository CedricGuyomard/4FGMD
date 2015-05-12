package model;

import java.util.Scanner;

import sql.SqlParser;

public class testsql {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Disease maladie = new Disease();
		Scanner sc = new Scanner(System.in);
		SqlParser sqp = new SqlParser();
		
		String str = sc.next();
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
	}

}
