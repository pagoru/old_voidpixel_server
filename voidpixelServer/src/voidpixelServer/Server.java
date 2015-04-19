package voidpixelServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import util.Encrypt;
import util.Util;
import voidpixelServer.util.Mysql;

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
							
							connection(IP, "refuse");
							
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
						
						connection(IP, connection + "");
						
					} else if(inputLine.substring(0, 4).equals("uuid")) {
						
						String uuid = Mysql.connectionSql("SELECT `UUID` FROM `voidpixel_profile` WHERE `username`='" + inputLine.substring(5, inputLine.length()) + "'");
						
						if(!uuid.isEmpty()){
							
							System.out.print(Util.getTime() + " - " + IP + " INFO >: "); //Desabilitar
							System.out.println(uuid);
							connection(IP, uuid);
							
						} else {
							
							set404(IP);
							
						}
						
					} else if(inputLine.substring(0, 4).equals("nick")) {
						
						String username = Mysql.connectionSql("SELECT `username` FROM `voidpixel_profile` WHERE `UUID`='" + inputLine.substring(5, inputLine.length()) + "'");
						
						if(!username.isEmpty()){
							
							System.out.print(Util.getTime() + " - " + IP + " INFO >: "); //Desabilitar
							System.out.println(username);
							connection(IP, username);
							
						} else {
							
							set404(IP);
							
						}
						
					} else {
						
						set404(IP);
						
					}
					
				} else if(inputLine.length() == 4){
					
					if(inputLine.substring(0, 4).equals("list")){
						
						System.out.print(Util.getTime() + " - " + IP + " INFO >: "); //Desabilitar
						System.out.println(IPConnection.toString()); //Desabilitar
						System.out.println(isConnected.toString()); //Desabilitar
						
						connection(IP, IPConnection.toString() + isConnected.toString());
						
					} else {
						
						set404(IP);
						
					}
					
				} else {
					
					set404(IP);
					
				}
				
			}
			
			string.setLength(0);
			//SERVER CLOSE
			
		}    	
    	
    }
	
	public static void connection(String ip, String prin) throws UnknownHostException, IOException{
		
		//CLIENT CONNECTION OPEN
		Socket socket = new Socket(ip, 15051);
		PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
		print.println(Encrypt.encrypt(prin));
		socket.close();
		//CLIENT CONNECTION CLOSE
		
	}
	
	public static void set404(String IP) throws IOException{
		
		connection(IP, "404");
		
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