package clientservermodel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Server implements Runnable{
	
	//Containing all key value pair wrt their username HashMap<username,HashMap<String,String>>[inner hasmap is hm]
	public static HashMap<String,HashMap<String,String>> database = new HashMap<String,HashMap<String,String>>();
	
	//list of clients who have manager access and who haven't [HashMap<username,1/0>] 1 = manager, 0 = guest
	public static HashMap<String,Integer> accesslist = new HashMap<String,Integer>();
	
	
	//HashMap for the particular user
	public HashMap<String,String> hm = new HashMap<String,String>();
	
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
	
	
	
	private void handleManagerAccess(String username,String password) {
		Scanner sc = new Scanner(System.in);
		if(password.equals(PASSWORD)) {
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
						System.out.println("Welcome back "+username);
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
		else {
			out.println("wrong password");
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
	
	
	
	private void handleDatabase(String username,String[] arr) {
		int i = 0;
		String temp = "";
		while(i<arr.length) {
			
			if(arr[i].equals("put")) {
				hm.put(arr[i+1], arr[i+2]);
				i = i + 3;	
			}
			
			else if (arr[i].equals("get")){
				if(accesslist.get(username) == 1) {
					for(String key : database.keySet()) {
						if(database.get(key).containsKey(arr[i+1])) {
							temp = temp + new String(database.get(key).get(arr[i+1]))+" ";
						}	
					}	
				}
				else {
					if(database.get(username).containsKey(arr[i+1])) {
						temp = temp + database.get(username).get(arr[i+1])+" ";
					}
				}
				i = i + 2;
			}
			
			else {
				System.out.println("Invalid Input!");
				out.println("Invalid input!");
				return;
			}
			
		}
			database.put(username, hm);
			out.println(temp.stripTrailing());
			out.flush();
		
	}
	
	

	@Override
	public void run() {
				
			String str = null;
			String clientusername = null;
			try {
				while((str = in.readLine())!=null) {
					String[] arr = str.split(" ");
					switch(arr[0]){
						case "rqstpassword":
							clientusername = arr[1];
							handleManagerAccess(clientusername,arr[3]);
							break;
						case "guestentry":
							clientusername = arr[1];
							handleGuestAccess(clientusername);
							break;
						case "get": case "put":
							handleDatabase(clientusername,arr);
							break;
						case "#####maintain#####":
							maintain();
							break;
						default:
							System.out.println("Invalid input!");
							out.println("Invalid input!");
							break;
					
					}
				}
			} catch (IOException e) {
				
				System.out.println("Client Disconnected!");
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
	
	public static void maintain() {
		Scanner sc = new Scanner(System.in);
		String str = new String();
		System.out.println("Want to remove everyone from manager access?");
		str = sc.nextLine();
		if(str.equals("y")||str.equals("Y")) {
			for(String key : database.keySet()) {
				accesslist.put(key, 0);
			}
			return;
		}
		while(true) {
			System.out.println("Want to remove someone from manager access?");
			str = sc.nextLine();
			if(accesslist.containsKey(str)) {
				accesslist.put(str, 0);
			}
			else
				break;
		}
		System.out.println("Maintainence completed");
	}

	
	public static void main(String[] args) throws IOException {
		
		 s = new ServerSocket(5555);
		 System.out.println("Server is live");
		 while(true) {
				 Socket socket =  s.accept();
				 System.out.println("Client accepted (at port : "+socket.getPort()+")");
				 Server server = new Server(socket);
		         Thread thread = new Thread(server);
		         thread.start();
		 	}
	}
}
