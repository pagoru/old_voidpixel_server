package voidpixelServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader bufferedReader;
	
	private static PrintWriter out = null;
	
	private static String inputLine;
	private static StringBuilder string= new StringBuilder();

	
	public static void main(String[] args) throws Exception {
		
		try {
			
			System.out.println("Bienvenido a los servidores de voidpixel! ");
			
			serverSocket = new ServerSocket(15051);
			
			while(true){				
				
				clientSocket = serverSocket.accept();
						
				bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				if((inputLine = (bufferedReader.readLine())) != null){
					
					String IP = clientSocket.getRemoteSocketAddress().toString().substring(1, clientSocket.getRemoteSocketAddress().toString().length() - 6);
					
					System.out.print(Util.getTime() + " - " + IP + " >: ");
					string.append(inputLine);
					
					System.out.println(string);
					
					if(!inputLine.equals("exit")) {
						
						if(inputLine.contains("uuid") && inputLine.length() > 5){
							
							if((inputLine.substring(4, 5).equals(" "))){
								
								String user = inputLine.substring(5);
								String UUID = read.readNick(user);
								
								Socket socket = new Socket(IP, 15051);
					    		out = new PrintWriter(socket.getOutputStream(), true);
								out.print(UUID);
								System.out.println(" " + UUID);
								out.flush();
								socket.close();
								
							}							
							
						} else if(inputLine.contains("verify") && inputLine.contains("@") && inputLine.length() > 8) {
							
							
							inputLine = inputLine.substring(7);
							
							String newInput = "";
							
							String mail = "";
							boolean mpb = true;
							
							String password = "";
							
							for (int i = 0; i < inputLine.length(); i++) {
								
								newInput = inputLine.substring(i, i + 1);
								
								if(!mpb){
									
									password = password + newInput;
									
								}
								
								if(!newInput.equals(" ") && mpb){
									
									mail = mail + newInput;
									
								} else {
									
									mpb = false;
									
								}
								
								
							}
							
							boolean verify = read.getVerify(mail, password);
							 
							Socket socket = new Socket(IP, 15051);
					    	out = new PrintWriter(socket.getOutputStream(), true);
							out.print(verify);
							System.out.println(" " + verify);
							out.flush();
							socket.close();
							
						} else {
							
							Socket socket = new Socket(IP, 15051);
				    		out = new PrintWriter(socket.getOutputStream(), true);
							out.print("Nothing to do!");
							out.flush();
							socket.close();
							
							
						}
						
					} else {
						
						break;
						
					}
					
				}
				
				string.setLength(0);
//				clientSocket.close();
				
			}
			
		} catch(IOException e) {
			
			System.out.println(e);
			
		}
	}
}
