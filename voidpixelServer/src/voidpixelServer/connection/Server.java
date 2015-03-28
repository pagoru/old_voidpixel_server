package voidpixelServer.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import voidpixelServer.Encrypt;
import voidpixelServer.Util;
import voidpixelServer.read;

public class Server {
	
	public static Scanner reader = new Scanner(System.in);
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader bufferedReader;
	
	private static String inputLine;
	private static StringBuilder string = new StringBuilder();
	
	public static ArrayList<String> IPConnection = new ArrayList<String>();
	public static ArrayList<Boolean> isConnected = new ArrayList<Boolean>();
	
	public static void main(String[] args) throws Exception {
		
    	System.out.println("Bienvenido a los servidores de voidpixel! ");
    	
    	serverSocket = new ServerSocket(15051);
    	
    	while (true) {
				
			//SERVER OPEN
    		clientSocket = serverSocket.accept();
			bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String IP = clientSocket.getRemoteSocketAddress().toString().substring(1, clientSocket.getRemoteSocketAddress().toString().length() - 6);
			
			boolean accept = true;
			
			for (int i = 0; i < IPConnection.size(); i++) {
				
				if(IPConnection.get(i).contains(IP)){
					
					if(IPConnection.get(i).equals(IP)){
						
						if(!isConnected.get(i)){
							
							accept = false;
							
							//CLIENT CONNECTION OPEN
							Socket socket = new Socket(IP, 15051);
				    		PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
							print.println(Encrypt.encrypt("refuse"));
				    		socket.close();
				    		//CLIENT CONNECTION CLOSE
							
						}
						
					}
					
				}
				
			}
			
			if((inputLine = Encrypt.decrypt((bufferedReader.readLine()))) != null && accept){
				
				System.out.print(Util.getTime() + " - " + IP + " INFO >: "); //Desabilitar
				string.append(inputLine); //Desabilitar
				System.out.println(string); //Desabilitar
				
				if(inputLine.length() > 10){
					
					if(inputLine.substring(0, 7).equals("connect")){
						
						boolean connection = isConnection(inputLine, IP);
						
						System.out.print(Util.getTime() + " - " + IP + " INFO >: "); //Desabilitar
						System.out.println(connection); //Desabilitar
						
						//CLIENT CONNECTION OPEN
						Socket socket = new Socket(IP, 15051);
			    		PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
						print.println(Encrypt.encrypt(connection + ""));
			    		socket.close();
			    		//CLIENT CONNECTION CLOSE
						
					}
					
				} else if(inputLine.length() == 4){
					
					if(inputLine.substring(0, 4).equals("list")){
						
						System.out.print(Util.getTime() + " - " + IP + " INFO >: "); //Desabilitar
						System.out.println(IPConnection.toString()); //Desabilitar
						System.out.println(isConnected.toString()); //Desabilitar
						
						//CLIENT CONNECTION OPEN
						Socket socket = new Socket(IP, 15051);
			    		PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
						print.println(Encrypt.encrypt(IPConnection.toString() + isConnected.toString()));
			    		socket.close();
			    		//CLIENT CONNECTION CLOSE
						
					}
					
				} else {
					
					//CLIENT CONNECTION OPEN
					Socket socket = new Socket(IP, 15051);
		    		PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
					print.println(Encrypt.encrypt("404"));
		    		socket.close();
		    		//CLIENT CONNECTION CLOSE
					
				}
				
			}
			
			string.setLength(0);
			//SERVER CLOSE
			
		}    	
    	
    }
	
	public static boolean isConnection(String connectText, String IP) throws Exception{
		
		connectText = connectText.substring(8); //"connect " << 8
		
		String newInput = "";
		
		String mail = "";
		boolean mpb = true;
		
		String password = "";
		
		for (int i = 0; i < connectText.length(); i++) {
			
			newInput = connectText.substring(i, i + 1);
			
			if(!mpb){
				
				password = password + newInput;
				
			}
			
			if(!newInput.equals(" ") && mpb){
				
				mail = mail + newInput;
				
			} else {
				
				mpb = false;
				
			}
			
			
		}
		
		boolean verified = read.getVerify(mail, password);
		
		IPConnection.add(IP);
		isConnected.add(verified);
		
		return verified;
		
	}
	
}