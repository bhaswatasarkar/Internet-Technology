import java.sql.*;
import java.util.ArrayList;

import details.flight.Flight;


public class Flights{
	
	ArrayList<Flight> availableflights = new ArrayList<Flight>();
	Flights(String arrivalCity,String departureCity,String departureDate,Connection con){
		String getflights = "SELECT * FROM allflights where DepartureCity=\""+departureCity+"\" AND ArrivalCity=\""+arrivalCity+"\" AND CAST(Time As time)>time(current_timestamp())";
		//String
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery(getflights);
			//Integer cost = rs.getInt(6);
			while(rs.next())  {
				System.out.println(rs.getString(7));
				availableflights.add(new Flight(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		   
	ArrayList<Flight> returnFlightDetails(){
		return availableflights;
	}
		 
}
