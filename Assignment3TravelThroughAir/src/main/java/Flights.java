import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import details.flight.Flight;


public class Flights{


	ArrayList<Flight> availableflights = new ArrayList<>();

	Flights(String arrivalCity,String departureCity,Connection con){
		System.out.print("I am in constructor");
		String getflights = "SELECT * FROM allflights where DepartureCity=\""+departureCity+"\" AND ArrivalCity=\""+arrivalCity+"\" AND CAST(Time As time)>time(current_timestamp())";
		//String
		Statement stmt;
		try {

			stmt = con.createStatement();

			ResultSet rs=stmt.executeQuery(getflights);
			System.out.println(getflights);
			//Integer cost = rs.getInt(6);
			while(rs.next())  {
				//System.out.println(rs.getString(7));
				availableflights.add(new Flight(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getString(7),0));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	ArrayList<Flight> returnFlightDetails(){
		System.out.print("I am here");
		return availableflights;
	}

}
