package voidpixelServer.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import voidpixelServer.Encrypt;
import voidpixelServer.Util;

public class Client {
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader bufferedReader;
	
	private static String inputLine;
	private static StringBuilder string = new StringBuilder();
	
	public static Scanner reader = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		
    	System.out.println("Bienvenido a los servidores de voidpixel! ");
    	
    	serverSocket = new ServerSocket(15051);
    	
    	while (true) {
    		
    		//READ INPUT OPEN
    		String word = reader.nextLine();
    		System.out.println(Util.getTime() + " >: " + word);
    		//READ INPUT CLOSE
    		
    		//SERVER CONNECTION OPEN
			Socket socket = new Socket("5.196.9.64", 15051);
    		PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
			print.println(Encrypt.encrypt(word));
    		socket.close();
    		//SERVER CONNECTION CLOSE
			
    		
    		//SERVER IN OPEN
    		clientSocket = serverSocket.accept();
			bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			if(!(inputLine = Encrypt.decrypt((bufferedReader.readLine()))).isEmpty()){
				
				System.out.print(Util.getTime() + " - " + " INFO >: "); //Desabilitar
				string.append(inputLine); //Desabilitar
				System.out.println(string); //Desabilitar
				
				if(inputLine.equals("refuse")){
					
					break;
					
				}
				
			}
			
			string.setLength(0);
			//SERVER CLOSE
			
		}    	
    	
    }
	
}