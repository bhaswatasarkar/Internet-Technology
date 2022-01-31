package clientservermodel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerMaintenance{

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost",5555);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println("#####maintain#####");
		socket.close();
	}

}
