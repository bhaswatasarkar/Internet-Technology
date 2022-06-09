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
  h3{
  	color : #030b40;
  }
  input{
    margin-left: 1px;
    margin-top: 3px;
  }
  .bg{
  	  background-image: url('background.webp');
  	  height:100%;
	  background-position: center;
	  background-repeat: no-repeat;
	  background-size: cover;
  }
  </style>
  <body style="background-image: url('background.webp')">
    <h2>Hello! Welcome to our website</h2>
    <hr />
    <h3>Search Your Flight..</h3>
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
		<br>

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
    
    <%
    	out.println("Flights available : ");
   		out.println("<table class=\"table\"><thread><tr><th scope=\"col\">Flight number</th><th scope=\"col\">Departure City</th><th scope=\"col\">Arrival City</th><th scope=\"col\">Time</th><th scope=\"col\">Legs</th><th scope=\"col\">Cost(Rs.)</th><th scope=\"col\">Travel Time(mins.)</th></tr></thread>");
    	ArrayList<Flight> al= (ArrayList<Flight>)request.getAttribute("flightDetails");
    	out.println("<tbody>");
    	for(Flight f : al){
    		out.println("<tr>");
    		out.println("<td>"+f.getFlightnum()+"</td>");
    		out.println("<td>"+f.getDepartureCity()+"</td>");
    		out.println("<td>"+f.getArrivalCity()+"</td>");
    		out.println("<td>"+f.getTime()+"</td>");
    		out.println("<td>"+f.getLegs()+"</td>");
    		out.println("<td>"+f.getCost()+"</td>");
    		out.println("<td>"+f.getTraveltime()+"</td>");
    		out.println("</tr>");
    	}
    	out.println("</tbody>");
		out.println("</table>");
    %>
    
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