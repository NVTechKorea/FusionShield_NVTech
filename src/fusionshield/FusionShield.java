//WITH MAIN
package fusionshield;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

import javax.swing.*;
import javax.swing.border.EmptyBorder;

//Version Info:
//Version: 5.0 Prototype 2 NVTech Edition
//Build: NVT500001P2

public class FusionShield extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JTextField pubKey;
	private JTextField priKey;
	private Scanner input = new Scanner(System.in);
	public String version = "NVTech Edition 5.0 Prototype 2";
	private String baseversion = "DRX404001SR";
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FusionShield frame = new FusionShield();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FusionShield() {
		varInit();

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		pubKey = new JTextField();
		pubKey.setBounds(6, 30, 219, 26);
		contentPane.add(pubKey);
		pubKey.setColumns(10);

		priKey = new JTextField();
		priKey.setColumns(10);
		priKey.setBounds(6, 82, 219, 26);
		contentPane.add(priKey);

		JLabel ID = new JLabel("Public key (Username)");
		ID.setBounds(6, 6, 207, 16);
		contentPane.add(ID);

		JLabel PW = new JLabel("Private key (Password)");
		PW.setBounds(6, 66, 207, 16);
		contentPane.add(PW);

		loginButton.setBounds(6, 243, 117, 29);
		contentPane.add(loginButton);

		newUserButton.setBounds(125, 243, 117, 29);
		contentPane.add(newUserButton);

		GUI_ActionListen();
	}

	public void GUI_ActionListen() {
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean empty = true;
				boolean empty1 = true;
				cached1 = pubKey.getText();
				cached2 = priKey.getText();
				if (cached1.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Public key is empty. Unable to continue.");
				} else {
					empty = false;
				}
				if (cached2.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Private key is empty. Unable to continue.");
				} else {
					empty1 = false;
				}
				if (empty == false && empty1 == false) {
					String cached3 = cached1 + version + cached2;
					String cached4 = encryptionEngine(cached3);
					loginUI(cached4);
				}

			}
		});
		newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean empty = true;
				boolean empty1 = true;
				cached1 = pubKey.getText();
				cached2 = priKey.getText();
				if (cached1.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Public key is empty. Unable to continue.");
				} else {
					empty = false;
				}
				if (cached2.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Private key is empty. Unable to continue.");
				} else {
					empty1 = false;
				}
				if (empty == false && empty1 == false) {
					createUserdata();
				}
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
		} else if (OS.contains("linux")) {
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
		if (OS.startsWith("Windows")) {
			File textdir = new File(superpath + "//textedit");
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
		} else if (OS.startsWith("Mac")) {
			File textdir = new File(superpath + "/textedit");
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
		} else {
			File textdir = new File(superpath + "/textedit");
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
	}

	public void checkUserdataExistance() {
		File checker = new File(path_userdat);
		if (checker.exists()) {
			exist = true;
			print("Encrypted data exist.");
		} else {
			exist = false;
			print("Encrypted data does not exist.");
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
			if (cached3.equals(encryptedinput)) {
				JOptionPane.showMessageDialog(frame, "Login Successful.");
				pass = true;
			} else {
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
