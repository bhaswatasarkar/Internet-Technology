package deal.manager;

import java.sql.*;
import java.util.Scanner;

public class SpecialDeals {
	public static void main(String[] args) {
		String username=null,password=null;
		int choice=-1;
		int flightnumber,offer;
		Scanner sc = new Scanner(System.in);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.print("username : ");
			username = sc.nextLine();
			System.out.print("password : ");
			password = sc.nextLine();
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb",username,password);
			Statement stmt = con.createStatement();
			
			
			while(choice!=0) {
				System.out.print("Add deal : press 1,delete deal : press 2, exit : press 0");
				choice = sc.nextInt();
				if(choice==1) {
					System.out.println("Enter flight number and offer(%) : ");
					flightnumber = sc.nextInt();
					offer = sc.nextInt();
					stmt.execute("INSERT INTO offer VALUES("+flightnumber+","+offer+")");
					stmt.execute("UPDATE allflights SET cost=cost*"+(100-offer)+"/100 WHERE FlightNumber="+flightnumber);
				}
				else if(choice==2) {
					System.out.println("Enter flight number : ");
					flightnumber = sc.nextInt();
					ResultSet rs=stmt.executeQuery("SELECT offer FROM offer WHERE FlightNumber="+flightnumber);
					rs.next();
					offer=rs.getInt(1);
					stmt.execute("DELETE FROM offer WHERE FlightNumber="+flightnumber);
					stmt.execute("UPDATE allflights SET cost=cost*100/"+(100-offer)+" WHERE FlightNumber="+flightnumber);
					
				}
			}
			
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		

	}

}
