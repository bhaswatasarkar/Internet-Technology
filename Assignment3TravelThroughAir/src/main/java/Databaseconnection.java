import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class Databaseconnection
 *
 */
@WebListener
public class Databaseconnection implements ServletContextListener {

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 

    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
	
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
         }
         catch (Exception e) {
			e.printStackTrace();
		}
			 
			
         
    }
	
}
