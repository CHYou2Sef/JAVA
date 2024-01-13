package avrdude;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import com.fazecast.jSerialComm.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.JProgressBar;
import javax.swing.JFormattedTextField;

public class ArduinoAvr extends JFrame {
	
    SerialPort port;
    private javax.swing.JComboBox<String> PortsList;  // List of port connected
    private javax.swing.JButton connectButton;     // button for connect to port
    
    private JPanel contentPane;  // Panel Principal
	private JTextField t1;     // Text field for path 
	private JTextField t2;    // Text field for command execute
	private JButton Button1 ;   // Select button
	private JButton Button2 ;   // Send button
	
	private  String MCU = "atmega328p" ;    // -p <part no> -> Required Specify AVR device.
	// private  int F_CPU = 1000000  ;      // CPU frequency on HTZ
	private  double BAUD  = 114500 ;        // -b <baud rate> -> Override RS-232 baud rate.
	private  String PROGRAMMER_TYPE = "arduino" ;     //-c <programmer> -> Specify programmer type.
	private  String TARGET = "My_First_Avrdude_Project" ;   // Name of project
	
	private static JFileChooser file ;  // File should be .hex
	private static String filepath ;    // File path
	private static String filename ;   // File name
	private static String chp ;     // Change path to string
	private static String chn ;     // Change name to string
	private static String com ;     // Port name
	
	Connection connection=null;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) // Principal main
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
					ArduinoAvr frame = new ArduinoAvr();
					frame.setVisible(true);					
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public ArduinoAvr() // Principal function
	{
		init();
		addPortNames() ;
	}
	
	private void init() // initial function 
	{	
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\YOUSSEF\\Desktop\\caisse\\application-debug\\src\\ardude\\img.jpg"));
		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Times New Roman", Font.BOLD, 19));
		setTitle("AVRDUDE COMPILER");		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 750);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		SerialPort ports[] = SerialPort.getCommPorts() ; // Port added
		for(SerialPort p : ports) { System.out.println(p.getSystemPortName());}
		
		PortsList = new javax.swing.JComboBox<>(); 
		PortsList.setFont(new Font("Times New Roman", Font.BOLD, 20));
		PortsList.setBounds(312, 88, 242, 48);
		PortsList.setBackground(Color.WHITE);
		PortsList.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Select Port" })); // List of Ports
		PortsList.setToolTipText("Select Port");
	    contentPane.add(PortsList);
	    
		JButton Button1 = new JButton("...");  // Button of selection file
		Button1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Button1.setBackground(Color.WHITE);
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
			try {
					if (e.getSource() == Button1)
					{
						file = new JFileChooser() ; 						
						file.setCurrentDirectory(new File("C:\\Users\\YOUSSEF\\Desktop")); // Default directory 
						int r = file.showOpenDialog(null);   // Select file to open
						// System.out.println(r);
						if (r == JFileChooser.APPROVE_OPTION) // if r=0 that's mean i choose a file 
						{
							File filepath = new File(file.getSelectedFile().getAbsolutePath()) ;   // Select File Path
							File filename = new File(file.getSelectedFile().getName()) ; //select file name							
							chp = filepath.toString();
							chn = filename.getName();
							//System.out.println(filename);
							//System.out.println(filepath);
							System.out.println("Name: "+ chn);
							System.out.println("Path: "+chp);
							t1.setText(chp);
						}
					}	
				} catch(Exception w) { System.out.println("Error Selection !!!"); }
			}
		});
		Button1.setBounds(498, 191, 56, 48);
		contentPane.add(Button1);
				
		JButton sendButn = new JButton("SEND"); // Button for send the file .hex to the port of arduino
		sendButn.setBackground(Color.WHITE);
		sendButn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		sendButn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				send();  //appelle de fonction send
			}
		});
		sendButn.setBounds(167, 284, 212, 48);
		contentPane.add(sendButn);
		
		t1 = new JTextField(); // text field for the path file
		t1.setBackground(Color.WHITE);
		t1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		t1.setBounds(28, 191, 471, 48);
		contentPane.add(t1);
		t1.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setForeground(Color.WHITE);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " * SELECT FILE * ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel.setBounds(12, 164, 558, 90);
		contentPane.add(panel);
		
		t2 = new JTextField(); // text field for testing command
		t2.setBackground(Color.WHITE);
		t2.setEditable(false);
		t2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		t2.setBounds(28, 345, 526, 39);
		contentPane.add(t2);
		t2.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setForeground(Color.WHITE);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " * COMPILE FILE * ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(12, 267, 558, 130);
		contentPane.add(panel_2);
		
		JLabel lblWelcomeTo = new JLabel("*** WELCOME TO AVRDUDE ARDUINO COMPILER ***");
		lblWelcomeTo.setBackground(new Color(240, 255, 255));
		lblWelcomeTo.setForeground(Color.WHITE);
		lblWelcomeTo.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblWelcomeTo.setBounds(12, 0, 558, 48);
		contentPane.add(lblWelcomeTo);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel_3.setBackground(Color.GRAY);
		panel_3.setBounds(0, 0, 582, 54);
		contentPane.add(panel_3);
		
		connectButton = new javax.swing.JButton(); // Connect Button
		connectButton.setBackground(Color.WHITE);
		connectButton.setFont(new Font("Times New Roman", Font.BOLD, 20)); 
        connectButton.setText("CONNECTER");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });
        connectButton.setBounds(43, 88, 206, 48);
        contentPane.add(connectButton);
        
        JPanel panel_1 = new JPanel();
        panel_1.setForeground(Color.WHITE);
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " * SELECT PORT * ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel_1.setBackground(Color.DARK_GRAY);
        panel_1.setBounds(12, 61, 558, 90);
        contentPane.add(panel_1);
        
        JButton SAVEbutton = new JButton("SAVE");
        SAVEbutton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg4) {
        		save(); //appelle de fonction
        	}
        });
        SAVEbutton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        SAVEbutton.setBackground(Color.WHITE);
        SAVEbutton.setBounds(43, 433, 206, 48);
        contentPane.add(SAVEbutton);
        
        JButton btnShaw = new JButton("SHOW HISTORY");
        btnShaw.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		if (e.getSource()==btnShaw)
        		{
        			affichage wind = new affichage();
        		}
        	}
        });
        btnShaw.setBackground(Color.WHITE);
        btnShaw.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnShaw.setBounds(316, 433, 206, 48);
        contentPane.add(btnShaw);
        
        JPanel panel_4 = new JPanel();
        panel_4.setForeground(Color.WHITE);
        panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " *  Save  * ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel_4.setBackground(Color.DARK_GRAY);
        panel_4.setBounds(12, 410, 558, 90);
        contentPane.add(panel_4);
        
        JLabel imglabel = new JLabel("");
        ImageIcon img = new ImageIcon(this.getClass().getResource("/555.jpg")) ;
        imglabel.setIcon(img);
        imglabel.setBounds(-51, 494, 655, 228);
        contentPane.add(imglabel);
        
      //  System.out.println(chp+"\n"+chn);	
	}	
	
	private void send() // Function for send button 
	{
		try
		{
			if (  (t1.getText().equals(""))  )
			{	
				JOptionPane.showMessageDialog(null, "!!! Erreur, Il y a des champs vides :( !!!");
			}
			else
			{	
			System.out.println("avrdude path : "+chp+" and name : "+chn);
			com = PortsList.getSelectedItem().toString(); // port list name <string>
			
			t2.setText("avrdude -c "+PROGRAMMER_TYPE+" -p "+MCU+" -P "+com+" -b"+BAUD+" -U flash:w:"+chn); // test
			cmmd(); 			
			/**	  if (com.equals("Select Port"))
				JOptionPane.showMessageDialog(null, "CHOOSE THE PORT :)");
			else
			{				
				t2.setText("avrdude -c "+PROGRAMMER_TYPE+" -p "+MCU+" -P "+com+" -U flash:w:"+chn); // test
				cmmd(); 
			}	  */				
			}
		} catch(Exception ee) 
		{
			JOptionPane.showMessageDialog(null, "!!! :( SEND Error  :( !!!");	
		}	
	}
	
	private void  cmmd() // command for execute 
	{
		String a = "ls" ;
		String b = "-li" ;
		String c = "pwd" ;
		String aa ="ping" ; String bb = "8.8.8.8" ;
		String [] cmd = {aa,bb};
		String [] cmmd = {"avrdude -C",PROGRAMMER_TYPE," -p ",MCU," -Uflash:w:"+chn } ;
		String Command;
		for(int i=0;i<cmmd.length;i++)
		{
			
		}
		ProcessBuilder probuild = new ProcessBuilder(cmmd);
		//ProcessBuilder probuild = new ProcessBuilder("C:\\Users\\YOUSSEF\\AppData\\Local\\Arduino15\\packages\\arduino\\tools\\avrdude\\6.3.0-arduino17/bin/avrdude -CC:\\Users\\YOUSSEF\\AppData\\Local\\Arduino15\\packages\\arduino\\tools\\avrdude\\6.3.0-arduino17/etc/avrdude.conf -v -V -patmega328p -carduino -PCOM3 -b115200 -D -Uflash:w:C:\\Users\\YOUSSEF\\AppData\\Local\\Temp\\arduino-sketch-1AA69784CD85EFBCB0084B7BA81808CD/sketch_feb1a.ino.hex:i") ;
		//probuild.directory(new File(System.getProperty("user.home"))) ;					
		try 
		{							
				Process pro = probuild.start() ;			
				BufferedReader lect = new BufferedReader(new InputStreamReader(pro.getInputStream())) ;				
				
				String line=null;
				while ((line= lect.readLine() ) != null)
				{
					System.out.println(line);
				}	
				
				int cd = pro.waitFor();	
				System.out.println("\nExit Error Code :"+cd);				 
		}
		/**catch(Exception xx)
		{xx.printStackTrace();}
		*/
		catch(InterruptedException e) 
	    {	JOptionPane.showMessageDialog(null, "!!! :( Error :( !!!");	 }
		catch(IOException e)
		{	JOptionPane.showMessageDialog(null, "!!! Last Error :( !!!");  }
	
	}	
	
	public List<String> getPortNames()  // get the ports name
    {    
		return Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .collect(Collectors.toList());
    }
	
    public void addPortNames() // add port names to combo box 
    {
        getPortNames().forEach( name -> {  PortsList.addItem(name);  });
    }
     
    public void connecToPort(String SelectedPort)  // this function connect to the selected port 
    {    try 
        {
            this.port = SerialPort.getCommPort(SelectedPort);
            port.setComPortParameters(250000, 8, 1, 0);
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
            
            if (!port.openPort())  // Error while connection
            {
                throw new Exception("Connection Error !!!");
            } 
            else 
            {
            	recieveDataFromBoard();
                connectButton.setEnabled(true);
                PortsList.setEnabled(true);  
            }
            JOptionPane.showMessageDialog(null, "Successful Connection :)");
            System.out.println("Successful Connection To Port");

        } catch (Exception e) // show the alert
        {
        	System.out.print("Connection probleme : ");
            System.out.println(e.getCause());
            JOptionPane.showMessageDialog(null, "Probleme de connection !!!");
        }
    }
    
    private void recieveDataFromBoard()   // this function handle the message send by the board .
    {  
    	port.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }
            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) 
                {
                    return ;
                }
                byte[] newData = new byte[port.bytesAvailable()];
                port.readBytes(newData, newData.length);
                String data = new String(newData);
                System.out.println(data); 
            }
    	});
    }
    
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) // Connecter Button function 
    {                                              
        //handle connection button click
    	try
    	{  
    		connecToPort(PortsList.getSelectedItem().toString());
        }
        catch(Exception e)
        {
        	System.out.print("Connect Button Problem !!!");
            System.out.println(e.getMessage()); 
        }       
    }
    
    static Connection con()  // Function for Connect to Data Base 
	{
		Connection con = null ;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root", "");
		
		} catch (Exception e) {
			System.out.println("!!! connection failed !!!\n");
		    JOptionPane.showMessageDialog(null, "!!! Erreur, de connection :( !!!");

		} 
		return null;
	}
    
	private void save()   // Function for save data in the table of data base 
	{
	try {
			Connection con=con();
			if (  (t1.getText().equals("")) || (t2.getText().equals("")) )
			{	
				JOptionPane.showMessageDialog(null, "!!! Erreur, Il y a des champs vides :( !!!");
			}
			else
			{
				String query ="insert into arduino values(?,?,?,?,?,?,?)" ;
				PreparedStatement past = con.prepareStatement(query); 
				past.setString(1, TARGET);
				past.setString(2, PROGRAMMER_TYPE);
				past.setString(3, MCU);
				past.setString(4, com);
				past.setDouble(5, BAUD);
				past.setString(6, chn);
				java.sql.Timestamp date= new java.sql.Timestamp(new java.util.Date().getTime());
				past.setTimestamp(7, date);
		    
				past.execute();
		    
				JOptionPane.showMessageDialog(null, "Date Saved :)");
				past.close();
			}
		} catch (Exception e) 
		{
		    e.printStackTrace();
			JOptionPane.showMessageDialog(null, "!!! Erreur, de connection :( !!!");
		}
	}		
}  // END OF avrdude PROJECT