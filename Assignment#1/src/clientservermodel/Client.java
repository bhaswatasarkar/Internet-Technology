package clientservermodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



public class Client{
	
	private Socket socket;
	protected BufferedReader in = null;
	protected PrintWriter out = null;
	public int clientport;
	
	//name of the client(for unique identification)
	private String username = null;
	
	//0 = guest(default),1 = manager
	private int access = 0;
	
	public Client(String hostname,int port){
		try{
			 this.socket = new Socket(hostname, port);
		     out = new PrintWriter(socket.getOutputStream(), true);
		     in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     this.clientport = socket.getLocalPort();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkAccess() throws IOException {
		String str = null;
		Scanner sc = new Scanner(System.in);
		System.out.print("$ Enter username : ");
		this.username = sc.nextLine();
		while(true) {
			System.out.println("$ Press m with password [m<blank>password] for manager access, press g for guest access : ");
			
			str = sc.nextLine();
			
			if(str.charAt(0)=='g') {
				System.out.println("Entering as guest");
				this.out.println("guestentry "+this.username+" "+str);
				this.out.flush();
				break;
			}
			
			else if(str.charAt(0)=='m') {
				this.out.println("rqstpassword "+this.username+" "+str);//manager password
				this.out.flush();
				String temp = this.in.readLine();
				if(temp.equals("access granted")) {
					System.out.println("Manager Access Granted");
					access = 1;
					break;
				}
				else if(temp.equals("access denied")) {
					System.out.println("Manager access denied, entering as guest");
					break;
				}
				else if(temp.equals("wrong password")){
						System.out.println("Wrong password try again");
					}
		}
		}
	
	}

public static void main(String[] args) throws UnknownHostException, IOException {
		
	   
		Scanner sc = new Scanner(System.in);
		String str = null;
	
		//Client cl = new Client("localhost",5555);
		Client cl = new Client(args[0],Integer.parseInt(args[1]));
		
		cl.checkAccess();
		
		while(true) {
			System.out.print("$ ");
			str = sc.nextLine();
			if("exit".equalsIgnoreCase(str)) {
				System.out.print("logging out..");
				sc.close();
				break;
			}
			cl.out.println(str);
			cl.out.flush();
			
			str = cl.in.readLine();
			if(str!=null){
				System.out.println(str);
			}//else "no data found"
		}
		
		
		

	}

}
