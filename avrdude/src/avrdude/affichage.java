package avrdude;

import javax.swing.*;

import java.sql.*;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Font;

public class affichage {
	
	Connection con = null ;
	
	JFrame frame = new JFrame(" History of Compilation ");
	private JTable table;
	private JTextField txtNomProjet;
	private JTextField txtType;
	private JTextField txtRefMicro;
	private JTextField txtPort;
	private JTextField txtBaudRate;
	private JTextField txtMicro;
	private JTextField txtDate;
	 
	affichage()
	{
	  con = conn(); 
	  frame.getContentPane().setBackground(Color.DARK_GRAY);
	  frame.setBackground(Color.WHITE);
	  
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setSize(1001,500);
	  frame.getContentPane().setLayout(null);
	  
	  JButton btnShow = new JButton("show");
	  btnShow.setFont(new Font("Times New Roman", Font.BOLD, 20));
	  btnShow.setForeground(Color.DARK_GRAY);  
	  btnShow.setBackground(Color.WHITE);
	 
	  btnShow.addActionListener(new ActionListener() {
	  
	  public void actionPerformed(ActionEvent e) {
	  		
	  	try {  			
	  			String query ="select * from arduino" ;
				PreparedStatement past = con.prepareStatement(query); 
				ResultSet rs = past.executeQuery(); 
				table.setModel(DbUtils.resultSetToTableModel(rs));
	  			 
	  		 } catch (Exception a) 
	  		 {
	  			 a.printStackTrace();
	  		 }
	  	}
	  });
	  btnShow.setBounds(400, 10, 209, 46);
	  frame.getContentPane().add(btnShow);
	  
	  txtNomProjet = new JTextField();
	  txtNomProjet.setEditable(false);
	  txtNomProjet.setText("Nom de projet");
	  txtNomProjet.setBounds(12, 69, 138, 38);
	  frame.getContentPane().add(txtNomProjet);
	  txtNomProjet.setColumns(10);
	  
	  txtType = new JTextField();
	  txtType.setEditable(false);
	  txtType.setText("type de prog");
	  txtType.setColumns(10);
	  txtType.setBounds(153, 69, 138, 38);
	  frame.getContentPane().add(txtType);
	  
	  txtRefMicro = new JTextField();
	  txtRefMicro.setEditable(false);
	  txtRefMicro.setText("Ref Micro");
	  txtRefMicro.setColumns(10);
	  txtRefMicro.setBounds(292, 69, 129, 38);
	  frame.getContentPane().add(txtRefMicro);
	  
	  txtPort = new JTextField();
	  txtPort.setEditable(false);
	  txtPort.setText("Port");
	  txtPort.setColumns(10);
	  txtPort.setBounds(425, 69, 129, 38);
	  frame.getContentPane().add(txtPort);
	  
	  txtBaudRate = new JTextField();
	  txtBaudRate.setEditable(false);
	  txtBaudRate.setText("Baud rate");
	  txtBaudRate.setColumns(10);
	  txtBaudRate.setBounds(556, 69, 138, 38);
	  frame.getContentPane().add(txtBaudRate);
	  
	  txtMicro = new JTextField();
	  txtMicro.setEditable(false);
	  txtMicro.setText("Nom de fichier");
	  txtMicro.setColumns(10);
	  txtMicro.setBounds(697, 69, 129, 38);
	  frame.getContentPane().add(txtMicro);
	  
	  txtDate = new JTextField();
	  txtDate.setEditable(false);
	  txtDate.setText("Date");
	  txtDate.setColumns(10);
	  txtDate.setBounds(827, 69, 144, 38);
	  frame.getContentPane().add(txtDate);
	  
	  table = new JTable();
	  table.setToolTipText("");
	  table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	  table.setBounds(12, 108, 959, 332);
	  frame.getContentPane().add(table);
	  frame.setVisible(true);
	 
	 }

/**	 
	 private static ArrayList<String> get() {
			Connection con=con();
			try {
				String query ="select * from arduino" ;
				PreparedStatement past = con.prepareStatement(query); 
				ResultSet rs = past.executeQuery();
			    ArrayList<String> ar = new ArrayList<String>();
			    System.out.println("********************************************");
			    while(rs.next())
			    {
			    	System.out.print(rs.getString(1));
			    	System.out.print(" | ");
			    	System.out.print(rs.getString(2));
			    	System.out.print(" | ");
			    	System.out.print(rs.getString(3));
			    	System.out.print(" | ");
			    	System.out.print(rs.getString(4));
			    	System.out.print(" | ");
			    	System.out.print(rs.getString(5));
			    	System.out.print(" | ");
			    	System.out.print(rs.getString(6));
			    	System.out.print(" | ");
			    	System.out.print(rs.getString(7));

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
*/	 
	 static Connection conn() 
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
}
