<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="error.jsp" import="java.util.*"%>
<%@ page import="details.flight.*"%>
<%@ page import="java.sql.*"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Travel Through Air</title>
  </head>
  <style>
  h2{
  color : rgb(139, 142, 144);
  }
  input{
    margin-left: 1px;
    margin-top: 3px;
  }
 
  
  </style>
  <body style="background-image: url('background.webp')" >
    <h2>Hello! Welcome to our website</h2>
    <hr />
    <h3>Search Your Flight</h3>
	<br><br>
    
    <form id="searchForm" action="getflightdetails" method="get">
    
    	<label for="departureCity" >Departure city:</label>
		<select class="form-select" aria-label="Default select example" id="departureCity" name="departureCity">
			  <option selected>Open this select menu</option>
			  <option>Chennai</option>
			  <option>Delhi</option>
			  <option>Kolkata</option>
			  <option>Mumbai</option>
		</select>

		<label for="arrivalCity">Arrival city:</label>
		<select class="form-select" aria-label="Default select example" id="arrivalCity" name="arrivalCity">
			  <option selected>Open this select menu</option>
			  <option>Chennai</option>
			  <option>Delhi</option>
			  <option>Kolkata</option>
			  <option>Mumbai</option>
		</select>
		

        <input type="submit" value="search" class="btn btn-danger">
    </form>
    <hr />
    <div id="deal-listing">
    </div>
    
    <div id="special-deal-listing">
     <%
	    out.println("Special offers!!!");
     	out.println("<br>");
	    ArrayList<Flight> alspecial= (ArrayList<Flight>)application.getAttribute("spd");
	    for(Flight f : alspecial){
	    	out.println("Flight number : "+f.getFlightnum()+" Offer : "+f.getOffer()+"%");
	    	out.println("<br>");
    }
	%>
    </div>
    </body>
    </html>