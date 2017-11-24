package fusionshield;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class Fail_1{

	private JFrame frame;
	private JTextField pubKey;
	private JTextField priKey;
	private Scanner input = new Scanner(System.in);
	public String version = "NVTech Edition 5.0 Prototype 1";
	private String baseversion = "DreamHybridShield 4.3-Stable";
	public String OS;
	String superpath;
	public String home;
	public static String path_userdat;
	public static String path_privateData;
	public static String path_des;
	public String usrid = null;
	public String usrpw = null;
	private String cached1;
	private String cached2;
	public boolean pass = false;
	public boolean exist = false;
	private JButton loginButton = new JButton("Login");
	JButton newUserButton = new JButton("Create User");
	
	public static void main(String[]args) {
		Fail_1 fsg = new Fail_1();
		fsg.initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {

		varInit();

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		pubKey = new JTextField();
		pubKey.setBounds(6, 30, 219, 26);
		frame.getContentPane().add(pubKey);
		pubKey.setColumns(10);

		priKey = new JTextField();
		priKey.setColumns(10);
		priKey.setBounds(6, 82, 219, 26);
		frame.getContentPane().add(priKey);

		JLabel ID = new JLabel("Public key (Username)");
		ID.setBounds(6, 6, 207, 16);
		frame.getContentPane().add(ID);

		JLabel PW = new JLabel("Private key (Password)");
		PW.setBounds(6, 66, 207, 16);
		frame.getContentPane().add(PW);

		loginButton.setBounds(6, 243, 117, 29);
		frame.getContentPane().add(loginButton);

		newUserButton.setBounds(135, 243, 117, 29);
		frame.getContentPane().add(newUserButton);
		
		GUI_ActionListen();
	}

	public void GUI_ActionListen() {
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cached1 = pubKey.getText();
				cached2 = priKey.getText();
				String cached3 = cached1 + version + cached2;
				String cached4 = encryptionEngine(cached3);
				loginUI(cached4);
			}
		});
		newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cached1 = pubKey.getText();
				cached2 = priKey.getText();
				createUserdata();
			}
		});
	}

	public void OSdetection() {
		OS = System.getProperty("os.name");
		if (OS.startsWith("Windows")) {
			superpath = "C://FusionShield";
			path_userdat = "C://FusionShield//userdat.txt";
			path_privateData = "C://FusionShield//privateData";
			path_des = "C://FusionShield//des.txt";
		} else if (OS.startsWith("Mac")) {
			home = System.getProperty("user.home");
			superpath = home + "/FusionShield";
			path_userdat = home + "/FusionShield/userdat.txt";
			path_privateData = home + "/FusionShield/privateData";
			path_des = home + "/FusionShield/des.txt";
		}else if (OS.contains("linux")) {
			home = System.getProperty("user.home");
			superpath = home + "/FusionShield";
			path_userdat = home + "/FusionShield/userdat.txt";
			path_privateData = home + "/FusionShield/privateData";
			path_des = home + "/FusionShield/des.txt";
		}
	}

	@SuppressWarnings("deprecation")
	public void varInit() {
		System.out.println("======FUSIONSHIELD SECURE LOGIN SYSTEM======");
		System.out.println("INITIALIZATION STARTED");
		System.out.println("Initialization Process 2: Display build finished.");
		System.out.println("Checking OS...");
		OSdetection();
		System.out.println("Checking FusionShieldLibrary...");
		checkFSLib();
		System.out.println("Checking encrypted data...");
		checkUserdataExistance();
		if (exist == true) {
			System.out.println("Encrypted data exist. Starting login UI...");
			newUserButton.setEnabled(false);
		} else {
			System.out.println("Encrypted data does not exist. Creating...");
			loginButton.setEnabled(false);
		}
	}

	public void checkFSLib() {
		File fs = new File(superpath);
		File textdir = new File(superpath + "textedit");
		if (fs.exists()) {
			if (textdir.exists()) {
				System.out.println("FusionShield Library already exist.");
			} else {
				textdir.mkdir();
			}
		} else {
			fs.mkdir();
			textdir.mkdir();
			System.out.println("FusionShield Library created.");
		}
	}

	public void checkUserdataExistance() {
		File checker = new File(path_userdat);
		if (checker.exists()) {
			exist = true;
		} else {
			exist = false;
		}
	}

	public void createUserdata() {
		String key = cached1 + version + cached2;
		String encryptedData = encryptionEngine(key);
		try {
			fileWriter("userdat.txt", encryptedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Encrypted data created. Please restart.");
		System.exit(0);
	}

	public void fileWriter(String filename, String contents) throws IOException {
		print("Writing file under FusionShield directory...: " + filename);
		if (OS.startsWith("Windows")) {
			String fspath = "C://FusionShield//" + filename;
			Writer writer = null;
			File newfile = new File(filename);
			if (newfile.exists()) {
				System.out.println("File already exists!");
			} else {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fspath), "utf-8"));
				writer.write(contents);
				writer.close();
			}
		} else if (OS.startsWith("Mac")) {
			String fspath = home + "/FusionShield/" + filename;
			Writer writer = null;
			File newfile = new File(fspath);
			if (newfile.exists()) {
				System.out.println("File already exists!");
			} else {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fspath), "utf-8"));
				writer.write(contents);
				writer.close();
			}
		} else if (OS.contains("linux")) {
			String fspath = home + "/FusionShield/" + filename;
			Writer writer = null;
			File newfile = new File(fspath);
			if (newfile.exists()) {
				System.out.println("File already exists!");
			} else {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fspath), "utf-8"));
				writer.write(contents);
				writer.close();
			}
		} else {
			print("Unknown OS. Unable to find FusionShieldPath.");
		}
	}

	public boolean loginUI(String encryptedinput) {
		FileReader userdat;
		try {
			userdat = new FileReader(path_userdat);
			String cached3 = null;
			BufferedReader br = new BufferedReader(userdat);
			cached3 = br.readLine();
			if(cached3.equals(encryptedinput)) {
				JOptionPane.showMessageDialog(frame, "Login Successful.");
				pass = true;
			}else {
				JOptionPane.showMessageDialog(frame, "Login Failure.");
				pass = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pass;
	}

	public String encryptionEngine(String key) {
		String res = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(key.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			res = (hexString.toString());

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return res;
	}

	public boolean launch() {

		return pass;
	}

	public static void locker() throws IOException {
		Writer writer = null;
		File target1 = new File(path_userdat);
		File target2 = new File(path_privateData);

		if (target1.delete()) {
			// No output, successfully deleted
		} else {
			System.out.println("Failed to destroy encrypted data.");
		}
		// enc.deleteOnExit();
		if (target2.isDirectory()) {
			File[] priFiles = target2.listFiles();
			for (int i = 0; i < priFiles.length; i++) {
				if (priFiles[i].delete()) {
					// No output, successfully deleted
				} else {
					System.out.println(priFiles[i].getName() + ": Erase failed");
				}
			}
		}
		if (target2.delete()) {
			// No output, successfully deleted
		} else {
			System.out.println("Failed erase textedit.");
		}
		// pri.deleteOnExit();
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path_des), "utf-8"));
		String data = "removed.hybshield.securitypolicy";
		writer.write(data);
		writer.close();
		System.exit(0);
	}

	public static void print(String a) {
		System.out.println(a);
	}
}
