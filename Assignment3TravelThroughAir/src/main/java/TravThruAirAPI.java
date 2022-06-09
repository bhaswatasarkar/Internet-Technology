
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import deal.manager.SpecialDeals;
import details.flight.Flight;

/**
 * Servlet implementation class TravThruAirAPI
 */
@WebServlet("/getflightdetails")
public class TravThruAirAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public TravThruAirAPI() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String arrivalCity = request.getParameter("arrivalCity");
		String departureCity = request.getParameter("departureCity");
		//String departureDate = request.getParameter("departureDate");
		ServletContext sc = getServletContext();
		Flights fs = new Flights(arrivalCity,departureCity,(Connection)sc.getAttribute("dbcon"));

		ArrayList<Flight> flightDetails = fs.returnFlightDetails();
		System.out.println(flightDetails);
		request.setAttribute("flightDetails", flightDetails);
		
		 SpecialDeals sd = new SpecialDeals();
		 ArrayList<Flight> al = sd.returnSpecialDeals((Connection)sc.getAttribute("dbcon"));
//		 System.out.println(al);
		 sc.setAttribute("spd", al);
		 
		RequestDispatcher rd = request.getRequestDispatcher("resultpage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
