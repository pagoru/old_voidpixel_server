package voidpixelServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;


public class read {
	
	public static String UrlUserName = "http://127.0.0.1/TeStT/api?username=";
	public static String UrlSalt = "http://127.0.0.1/TeStT/api?salt=";
	public static String UrlMail = "http://127.0.0.1/TeStT/api?m=";

	public static String readNick(String nick) throws IOException {
		
		URL url = new URL(UrlUserName + nick);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String read = in.readLine();
        in.close();
        
        if(!read.equals("null")){
        	
        	Gson gson = new Gson();
     		Api UUID = gson.fromJson(read, Api.class);
     		
     		return UUID.uuid;
        	
        }
		return "Not found";
        
        
	}
	
	public static String getSalt() throws IOException {
		
		URL url = new URL(UrlSalt + "almendra");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String read = in.readLine();
        in.close();
        
        if(!read.equals("null")){
        	
        	Gson gson = new Gson();
     		Api SALT = gson.fromJson(read, Api.class);
     		
     		return SALT.salt;
        	
        }
		return "Not found";
        
        
	}
	
	public static boolean getVerify(String mail, String password) throws IOException {
		
		String newPassword = "";
		
		for (int i = 0; i < password.length(); i++) {
			
			if(password.substring(i, i + 1).equals(" ")){
				
				newPassword = newPassword + "%20";
				
			} else {
				
				newPassword = newPassword + password.substring(i, i + 1); 
				
			}
			
		}
		
		URL url = new URL(UrlMail + mail + "&p=" + newPassword);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String read = in.readLine();
        in.close();
        
        if(!read.equals("null")){
        	
        	Gson gson = new Gson();
     		Api SALT = gson.fromJson(read, Api.class);
     		
     		if(SALT.passVerification.equals("1")){
     			
     			return true;
     			
     		} else {
     			
     			return false;
     			
     		}
        	
        }
		return false;
        
        
	}

}
