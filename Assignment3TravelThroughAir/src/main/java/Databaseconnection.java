import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import deal.manager.SpecialDeals;
import details.flight.Flight;

/**
 * Application Lifecycle Listener implementation class Databaseconnection
 *
 */
@WebListener
public class Databaseconnection implements ServletContextListener {

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
	public void contextDestroyed(ServletContextEvent sce)  {

    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */

    @Override
	public void contextInitialized(ServletContextEvent sce) {
         ServletContext sc = sce.getServletContext();
         String url = sc.getInitParameter("dbaddress");
         String driver = sc.getInitParameter("dbdriver");
         String username = sc.getInitParameter("username");
         String password = sc.getInitParameter("password");

         try {
	         Class.forName(driver);
			 Connection con=DriverManager.getConnection(url,username,password);
			 sc.setAttribute("dbcon", con);


			 SpecialDeals sd = new SpecialDeals();
			 ArrayList<Flight> al = sd.returnSpecialDeals(con);
			 System.out.println(al);
			 sc.setAttribute("spd", al);
         }
         catch (Exception e) {
			e.printStackTrace();
		}



    }

}
