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

public class aaa {

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
	public static void main(String[] args) throws Exception
 {  
			
	EventQueue.invokeLater( new Runnable() {
			public void run() 
			{
				try 
				{
					aaa window = new aaa();
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
	public aaa() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setTitle("Gestion Tickets Trein");
		frame.setBounds(100, 100, 477, 300);
		frame.setFont(new Font("Georgia",Font.BOLD,5));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(1, 1, 1, 1));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblVilleDepart = new JLabel("Ville Depart :");
		lblVilleDepart.setFont(new Font("arial", Font.BOLD, 12));
		lblVilleDepart.setBounds(178, 89, 90, 16);
		panel.add(lblVilleDepart);
		
		JLabel lblVilleArrive = new JLabel("Ville Arrivée :");
		lblVilleArrive.setFont(new Font("arial", Font.BOLD, 12));
		lblVilleArrive.setBounds(178, 118, 78, 16);
		panel.add(lblVilleArrive);
		
		JTextField RTI = new JTextField("Résevation Tickets");
		RTI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				get();	
			}
		});
		RTI.setBackground(Color.WHITE);
		RTI.setEditable(false);
		RTI.setHorizontalAlignment(SwingConstants.CENTER);
		RTI.setFont(new Font("cursive", Font.BOLD, 23));
		RTI.setForeground(Color.BLUE);
		RTI.setBounds(0, 0, 460, 51);
		panel.add(RTI);
		
		JButton btnReserv = new JButton("Reserver !");
		btnReserv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		btnReserv.setFont(new Font("Georgia",Font.ITALIC, 12));
		btnReserv.setBounds(299, 156, 99, 25);
		panel.add(btnReserv);
		
		JButton btnCalculer = new JButton("Calculer");
		btnCalculer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc();
			}		 
		});
		btnCalculer.setFont(new Font("Georgia",Font.ITALIC, 12));
		btnCalculer.setBounds(180, 156, 90, 25);
		panel.add(btnCalculer);
		
		JComboBox com1 = new JComboBox();
		com1.setModel(new DefaultComboBoxModel(new String[] {"Tunis", "Nabeul", "Bizert", "Beja", "Touzeur", "Monastir", "Sfax"}));
		com1.setSelectedIndex(1);
		com1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String com = com1.getSelectedItem().toString(); 
				text.setText(com);
			}
		});
		com1.setBounds(268, 86, 130, 22);
		panel.add(com1);
		
		JComboBox com2 = new JComboBox();
		com2.setToolTipText("Tunis");
		com2.setModel(new DefaultComboBoxModel(new String[] {"Tunis", "Nabeul", "Bizert", "Beja", "Touzeur", "Monastir", "Sfax"}));
		com2.setSelectedIndex(1);
		com2.setBounds(268, 115, 130, 22);
		com2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String com = com2.getSelectedItem().toString(); 
				text_1.setText(com);
			}
		});
		panel.add(com2);
				
		JList list_1 = new JList();
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 kk= list_1.getSelectedValue().toString();
			}
		});
		list_1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setVisibleRowCount(3);
		
		DefaultListModel dlm = new DefaultListModel() ;
		dlm.addElement("Premier");
		dlm.addElement("Deuxieme");
		dlm.addElement("Confort");
		list_1.setModel(dlm);
		list_1.setBounds(23, 88, 84, 66);
		panel.add(list_1);
		
		
		JCheckBox Remise = new JCheckBox("Rmise !");
		Remise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{ 
					if (Remise.isSelected())
					{
					 abc -=  0.2f * abc;
					 System.out.println("Montant avec remise : "+(float)abc);
					 text_2.setText((float)abc+"DT");
					}			   
				} catch (Exception e1 ) 
				{
				 e1.getMessage();
				}
			}});
		Remise.setBounds(23, 156, 113, 25);
		panel.add(Remise);
		
		text = new JTextField();
		text.setEditable(false);
		text.setBounds(87, 218, 70, 22);
		panel.add(text);
		text.setColumns(10);
		
		text_1 = new JTextField();
		text_1.setEditable(false);
		text_1.setColumns(10);
		text_1.setBounds(210, 218, 70, 22);
		panel.add(text_1);
		
		text_2 = new JTextField();
		text_2.setEditable(false);
		text_2.setBackground(SystemColor.menu);
		text_2.setText("0.0 DT");
		text_2.setColumns(10);
		text_2.setBounds(357, 218, 53, 22);
		panel.add(text_2);
		
		JLabel lblVoyage = new JLabel("Voyage :");
		lblVoyage.setFont(new Font("Georgia",Font.BOLD,11));
		lblVoyage.setBounds(27, 218, 62, 22);
		panel.add(lblVoyage);
		
		JLabel lblVers = new JLabel("Vers :");
		lblVers.setFont(new Font("Georgia",Font.BOLD,11));
		lblVers.setBounds(167, 218, 41, 22);
		panel.add(lblVers);
		
		JLabel lblMontant = new JLabel("Montant :");
		lblMontant.setFont(new Font("Georgia",Font.BOLD,11));
		lblMontant.setBounds(289, 218, 70, 22);
		panel.add(lblMontant);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Classe : ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_1.setBounds(10, 64, 126, 131);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(147, 77, 32, -3);
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Destination : ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_3.setBounds(157, 64, 291, 131);
		panel.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Résumé :", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_4.setBounds(12, 198, 436, 50);
		panel.add(panel_4);

		System.out.println(kk);		
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
		} 
		return null;
	}
	
	private void calc() 
	{	
		try 
		{
			if ( (text_1.getText().equals(text.getText()))  || (text_1.getText().equals("")) || (text.getText().equals("")) )
			{
				text_2.setText("--DT");
			    JOptionPane.showMessageDialog(null, "!!! Erreur, Il y a des memes villes ou des champs vides :( !!!");
			}
			else
			{
				System.out.println(kk);
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
			System.out.println("!!! List Error !!!");
		}		
	}
	
	private void save() {
		Connection con=con();
		try {
			if ( text_1.getText().equals(text.getText()) )
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