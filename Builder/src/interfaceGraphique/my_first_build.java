package interfaceGraphique;

import java.awt.EventQueue; 

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollBar;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JList;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import java.awt.Choice;
import java.awt.Panel;
import javax.swing.DropMode;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.sql.*;
import java.util.ArrayList ;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;	

public class my_first_build {

	private JFrame frame;
	private static JTextField text;
	private static JTextField text_1;
	private static JTextField text_2;
	private static JCheckBox Remise;
	private static double abc;
	private static String kk ;

	Connection connection=null;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	throws Exception
 {  			
	EventQueue.invokeLater( new Runnable() {
			public void run() 
			{
				try 
				{
					my_first_build window = new my_first_build();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
}

	/**
	 * Create the application.
	 */
	public my_first_build() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setForeground(Color.WHITE);
		frame.setTitle("Gestion Tickets Trein");
		frame.setBounds(100, 100, 750, 500);
		frame.setFont(new Font("Georgia",Font.BOLD,5));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(169, 169, 169));
		panel.setBorder(new EmptyBorder(1, 1, 1, 1));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblVilleDepart = new JLabel("Ville Depart  :");
		lblVilleDepart.setFont(new Font("Bell MT", Font.BOLD, 21));
		lblVilleDepart.setBounds(24, 101, 131, 36);
		panel.add(lblVilleDepart);
		
		JLabel lblVilleArrive = new JLabel("Ville Arrivée :");
		lblVilleArrive.setFont(new Font("Bell MT", Font.BOLD, 21));
		lblVilleArrive.setBounds(24, 152, 131, 36);
		panel.add(lblVilleArrive);
		
		JTextField RTI = new JTextField(" Reservation Tickets ");
		RTI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			
			}
		});
		RTI.setBackground(new Color(105, 105, 105));
		RTI.setEditable(false);
		RTI.setHorizontalAlignment(SwingConstants.CENTER);
		RTI.setFont(new Font("HP Simplified Jpan Light", Font.ITALIC, 40));
		RTI.setForeground(new Color(255, 255, 255));
		RTI.setBounds(0, 0, 732, 71);
		panel.add(RTI);
		
		
		JButton btnReserv = new JButton("Reserver !");
		btnReserv.setForeground(new Color(255, 255, 255));
		btnReserv.setBackground(new Color(169, 169, 169));
		btnReserv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) throws NullPointerException {
				try {
				save();
				} catch (NullPointerException ea) {
					JOptionPane.showMessageDialog(null,"Connection Impossible");
				}
			}
		});
		btnReserv.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
		btnReserv.setBounds(193, 288, 142, 56);
		panel.add(btnReserv);
		
		
		JButton btnCalculer = new JButton("Calculer");
		btnCalculer.setForeground(new Color(255, 255, 255));
		btnCalculer.setBackground(new Color(169, 169, 169));
		btnCalculer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc();
			}
			 
		});
		btnCalculer.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
		btnCalculer.setBounds(192, 226, 143, 56);
		panel.add(btnCalculer);
		
		JComboBox com1 = new JComboBox();
		com1.setFont(new Font("SimSun", Font.BOLD, 20));
		com1.setModel(new DefaultComboBoxModel(new String[] {"Tunis", "Nabeul", "Bizert", "Beja", "Touzeur", "Monastir", "Sfax"}));
		com1.setSelectedIndex(1);
		com1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String com = com1.getSelectedItem().toString(); 
				text.setText(com);
			}
		});
		com1.setBounds(167, 99, 174, 38);
		panel.add(com1);
		
		JComboBox com2 = new JComboBox();
		com2.setFont(new Font("SimSun", Font.BOLD, 20));
		com2.setToolTipText("Tunis");
		com2.setModel(new DefaultComboBoxModel(new String[] {"Tunis", "Nabeul", "Bizert", "Beja", "Touzeur", "Monastir", "Sfax"}));
		com2.setSelectedIndex(1);
		com2.setBounds(167, 150, 174, 38);
		com2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String com = com2.getSelectedItem().toString(); 
				text_1.setText(com);
			}
		});
		panel.add(com2);
		
		DefaultListModel dlm = new DefaultListModel() ;
		dlm.addElement("Premier");
		dlm.addElement("Deuxieme");
		dlm.addElement("Confort");
		
		text = new JTextField();
		text.setBackground(new Color(255, 250, 250));
		text.setForeground(SystemColor.windowBorder);
		text.setFont(new Font("SimSun", Font.BOLD, 20));
		text.setEditable(false);
		text.setBounds(156, 388, 116, 36);
		panel.add(text);
		text.setColumns(10);
		
		text_1 = new JTextField();
		text_1.setBackground(new Color(255, 250, 250));
		text_1.setForeground(SystemColor.windowBorder);
		text_1.setFont(new Font("SimSun", Font.BOLD, 20));
		text_1.setEditable(false);
		text_1.setColumns(10);
		text_1.setBounds(348, 388, 116, 36);
		panel.add(text_1);
		
		text_2 = new JTextField();
		text_2.setForeground(SystemColor.windowBorder);
		text_2.setFont(new Font("SimSun", Font.BOLD, 20));
		text_2.setEditable(false);
		text_2.setBackground(new Color(255, 250, 250));
		text_2.setText("0.0DT");
		text_2.setColumns(10);
		text_2.setBounds(592, 388, 82, 36);
		panel.add(text_2);
		
		JLabel lblVers = new JLabel("Vers :");
		lblVers.setFont(new Font("Bell MT", Font.BOLD, 21));
		lblVers.setBounds(283, 388, 62, 35);
		panel.add(lblVers);
		
		JLabel lblVoyageDe = new JLabel("Voyage de :");
		lblVoyageDe.setFont(new Font("Bell MT", Font.BOLD, 21));
		lblVoyageDe.setBounds(36, 389, 107, 36);
		panel.add(lblVoyageDe);
		
		JLabel lblMontant = new JLabel("Montant :");
		lblMontant.setFont(new Font("Bell MT", Font.BOLD, 21));
		lblMontant.setBounds(490, 389, 90, 35);
		panel.add(lblMontant);
		
		JButton btnNewButton = new JButton("Afficher");
		btnNewButton.setBackground(new Color(169, 169, 169));
		btnNewButton.setBounds(390, 102, 284, 33);
		panel.add(btnNewButton);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				get();	
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(169, 169, 169));
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Classe & Remise : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(12, 213, 149, 148);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		
		JCheckBox Remise_1 = new JCheckBox("Remise !");
		Remise_1.setBackground(new Color(169, 169, 169));
		Remise_1.setBounds(8, 106, 133, 33);
		panel_1.add(Remise_1);
		Remise_1.setFont(new Font("SimSun", Font.BOLD, 20));
		
		
		JList list_1 = new JList();
		list_1.setBackground(new Color(211, 211, 211));
		list_1.setBounds(21, 21, 94, 82);
		panel_1.add(list_1);
		list_1.setBorder(new LineBorder(Color.GRAY, 2, true));
		list_1.setFont(new Font("SimSun", Font.BOLD, 20));
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 kk= list_1.getSelectedValue().toString();
			}
		});
		list_1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setVisibleRowCount(3);
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"Premier", "Deuxieme", "Confort"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		Remise_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{ 
					if (Remise_1.isSelected())
					{
					 abc -=  0.2f * abc;
					 System.out.println("Montant avec remise : "+(float)abc);
					 text_2.setText((float)abc+"DT");
					}
				   
				} catch (Exception e1 ) 
				{
				 System.out.println("!!! Remise erreur !!!");
				
				}
			}});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(169, 169, 169));
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ville : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(12, 84, 350, 125);
		panel.add(panel_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(169, 169, 169));
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Histoire", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_2.setBounds(374, 84, 336, 277);
		panel.add(panel_2);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(169, 169, 169));
		panel_4.setBorder(new TitledBorder(null, "Résumé :", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_4.setBounds(12, 369, 698, 71);
		panel.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
	}
	
	static Connection con() 
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
	
	private void calc() {	
		try 
		{
			if ( (text_1.getText().equals(text.getText()))  || (text_1.getText().equals("")) || (text.getText().equals("")) )
			{
				text_2.setText("--DT");
			    JOptionPane.showMessageDialog(null, "!!! Erreur, Il y a des memes villes ou des champs vides :( !!!");
			}
			else
			{
				if ( kk == "Premier")
					abc = 3 ;
				else if (kk =="Deuxieme")
						abc = 6 ;
					else if (kk =="Confort")
							abc = 9 ;
			}
			
			String nn=String.valueOf(abc);
			text_2.setText(nn+"DT");
			System.out.println("Montant sans remise : "+(float)abc);	
			
		} catch (Exception e3)
		{
			System.out.println("!!! Erreur de List !!!");
		    JOptionPane.showMessageDialog(null, "!!! Erreur, de calcul :( !!!");
			e3.getMessage();
		}
		
	}
	
	private void save() {
		
		try {
			Connection con=con();
			if ( (text_1.getText().equals(text.getText()))  || (text_1.getText().equals("")) || (text.getText().equals("")) )
			{
				text_2.setText("--DT");
				JOptionPane.showMessageDialog(null, "!!! Erreur, Il y a des memes villes ou des champs vides :( !!!");
			}
			else
			{
			String query ="insert into tt values(?,?,?,?)" ;
			PreparedStatement past = con.prepareStatement(query); 
		    past.setString(1, text.getText());
		    past.setString(2, text_1.getText());
		    past.setString(3, text_2.getText());
		    java.sql.Timestamp date= new java.sql.Timestamp(new java.util.Date().getTime());
		    past.setTimestamp(4, date);
		    
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
	
	private static ArrayList<String> get() {
		Connection con=con();
		try {
			String query ="select * from tt" ;
			PreparedStatement past = con.prepareStatement(query); 
			ResultSet rs = past.executeQuery();
		    ArrayList<String> ar = new ArrayList<String>();
		    System.out.println("VilleDep|VilleArr|Montant|Date");
		    while(rs.next())
		    {
		    	System.out.print(rs.getString(1));
		    	System.out.print(" | ");
		    	System.out.print(rs.getString(2));
		    	System.out.print(" | ");
		    	System.out.print(rs.getString(3));
		    	System.out.print(" | ");
		    	System.out.println(rs.getString(4));

		    	ar.add(rs.getString(1));
		    }
		    
			JOptionPane.showMessageDialog(null, "Date Showed :)");
			past.close();
			return ar;
			
			} catch (Exception e) 
			{
		        System.out.println("!!! Erreur insertion !!!");;
		    }
		return null;
	}
}