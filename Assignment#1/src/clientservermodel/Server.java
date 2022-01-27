package clientservermodel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Server implements Runnable{
	
	//Containing all key value pair wrt their username HashMap<username,HashMap<String,String>>
	public static HashMap<String,HashMap<String,String>> database = new HashMap<String,HashMap<String,String>>();
	
	//list of clients who have manager access and who haven't [HashMap<username,1/0>] 1 = manager, 0 = guest
	public static HashMap<String,Integer> accesslist = new HashMap<String,Integer>();
	
	private static ServerSocket s;
	private Socket socket;
	protected BufferedReader in = null;
	protected PrintWriter out = null;
	
	private static String PASSWORD = "abc";
	
	public Server(Socket socket) throws IOException{
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(),true);
	}
	
	private void handleManagerAccess(String username) {
		Scanner sc = new Scanner(System.in);
		if(accesslist.containsKey(username)) {
			if(accesslist.get(username)==1) {
				System.out.println("Welcome back "+username);
				out.println("access granted");
			}
			else if(accesslist.get(username)==0) {
				System.out.println(username+" requesting manager access Y/y to access, anything else to reject");
				String temp = sc.nextLine();
				if(temp.equals("y")||temp.equals("Y")) {
					accesslist.put(username, 1);
					out.println("access granted");
				}
				else {
					out.println("access denied");
				}
			}
		}
		//creating new manager user
		else {
			System.out.println(username+" requesting manager access Y/y to access, anything else to reject");
			String temp = sc.nextLine();
			if(temp.equals("y")||temp.equals("Y")) {
				accesslist.put(username, 1);
				out.println("access granted");
			}
			else {
				out.println("access denied");
			}
		}
	}
	
	private void handleGuestAccess(String username) {
		if(!accesslist.containsKey(username)) {
			accesslist.put(username, 0);
		}
		else {
			System.out.println("Welcome back "+username);
		}
	}

	@Override
	public void run() {
				
			String str = null;
			
			try {
				while((str = in.readLine())!=null) {
					String[] arr = str.split(" ");
					switch(arr[0]){
						case "rqstpassword":
							handleManagerAccess(arr[2]);
							break;
						case "guestentry":
							handleGuestAccess(arr[1]);
							break;
						default:
							System.out.println("Something went wrong!");  
							break;
					}
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        socket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
			}
			
		
	}

	
	public static void main(String[] args) throws IOException {
		 s = new ServerSocket(5555);
		 System.out.println("Server is live");
		 while(true) {
			 Socket socket =  s.accept();
			 System.out.println("Client accepted");
			 Server server = new Server(socket);
	         Thread thread = new Thread(server);
	         thread.start();
	         
		 }
	}
}