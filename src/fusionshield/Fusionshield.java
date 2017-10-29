package fusionshield;

import java.util.Scanner;

public class Fusionshield {
	
	private Scanner input = new Scanner (System.in);
	private String version = "NVTech Edition 5.0 Prototype 1";
	public String usrid = null;
	public String usrpw = null;
	private String cached1;
	private String cached2;
	public boolean pass = false;
	public boolean exist = false;
	
	public void initialize() {
		
	}
	
	public boolean checkUserdataExistance() {
		
		return exist;
	}
	
	public void createUserdata() {
		
	}
	
	public boolean login() {
		
		return pass;
	}
	
	public void encryptionEngine() {
		
	}
	
	public boolean launch() {
		
		return pass;
	}
}
