package taksiBilgiSistemi;

import java.sql.Connection;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class aracArayuz extends JFrame {

	private JPanel contentPane;
	private JTextField txtPlaka;
	private JTable table_1;
	private DefaultTableModel person;
	public static String plaka;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String user, pass;
			        user = "postgres";
			        pass = "537469";
			        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TaksiDuragi_BilgiSistemi", user,pass);
					aracArayuz frame = new aracArayuz(conn);
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
	public aracArayuz(Connection conn) {
		
		setTitle("ARA\u00C7 \u0130\u015ELEMLER\u0130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1051, 578);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 134, 994, 305);
		contentPane.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		table_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		table_1.setBorder(UIManager.getBorder("EditorPane.border"));
		table_1.setRowHeight(25);
		table_1.setBackground(new Color(255, 165, 0));
		table_1.setModel(new DefaultTableModel(new Object[][] {},new String[] {"plaka","marka","model","kayit_tarihi","sofor_tc"}) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[0];
			}
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		TabloGoster(conn);
		
		JButton btnAracEkle = new JButton("Ara\u00E7 Ekle");
		btnAracEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				aracEkle newArac = new aracEkle(conn);
				newArac.setVisible(true);
			}
		});
		btnAracEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnAracEkle.setBounds(469, 52, 163, 46);
		contentPane.add(btnAracEkle);
		
		JButton btnAracSil = new JButton("Ara\u00E7 Sil");
		btnAracSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
			
				String plaka = txtPlaka.getText();
				
				if(Kontrol(conn)) {
					
					String query = "DELETE FROM araclar where plaka_no = '" + plaka + "'" ;
					Statement s = null;
			        try {
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
			        }catch(SQLException ee){
			            ee.printStackTrace();
			        }
			        txtPlaka.setText("");
			        person.setRowCount(0);
			        TabloGoster(conn);
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAracSil.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnAracSil.setBounds(644, 52, 146, 46);
		contentPane.add(btnAracSil);
		
		JButton btnAracGuncelle = new JButton("Ara\u00E7 G\u00FCncelle");
		btnAracGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				plaka = txtPlaka.getText();
				if(Kontrol(conn)) {
					dispose();
					aracGuncelle newArac = new aracGuncelle(conn);
					newArac.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAracGuncelle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnAracGuncelle.setBounds(802, 52, 219, 46);
		contentPane.add(btnAracGuncelle);
		
		txtPlaka = new JTextField();
		txtPlaka.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		txtPlaka.setColumns(10);
		txtPlaka.setBounds(27, 52, 245, 46);
		contentPane.add(txtPlaka);
		
		JLabel lblAraAra = new JLabel("ARA\u00C7 ARA");
		lblAraAra.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		lblAraAra.setBounds(79, 23, 163, 16);
		contentPane.add(lblAraAra);
		
		JButton btnAracAra = new JButton("Ara\u00E7 Ara");
		btnAracAra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ee) {

				String plaka = txtPlaka.getText();
				
				if(Kontrol(conn)) {
					String query = "SELECT * FROM araclar WHERE plaka_no = '" + plaka + "'";
					try {
						person = (DefaultTableModel)table_1.getModel();
						person.setRowCount(0);
						PreparedStatement s = conn.prepareStatement(query);
						ResultSet r = s.executeQuery();
						if(!r.next()) {
							
						}
						else {
							
							Object [] row = {r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5)};
							person.addRow(row);
						}
						
						s.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAracAra.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnAracAra.setBounds(294, 52, 163, 46);
		contentPane.add(btnAracAra);
		
		JButton btnGeriDon = new JButton("<-- Geri D\u00F6n");
		btnGeriDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				arayuz backArayuz = new arayuz(conn);
				backArayuz.setVisible(true);
			}
		});
		btnGeriDon.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnGeriDon.setBounds(23, 472, 146, 46);
		contentPane.add(btnGeriDon);
	}
	
	public void TabloGoster(Connection conn) {
		
		String query = "SELECT * FROM araclar";
		try {
			person = (DefaultTableModel)table_1.getModel();
			person.setRowCount(0);
			PreparedStatement s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();

			while(r.next()) {

				Object [] row = {r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5)};
				person.addRow(row);
			}
			
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean Kontrol(Connection conn) {
		
		String query = "SELECT plaka_no FROM araclar";
		PreparedStatement s;
		boolean count = false;
		try {
			s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
			while(r.next()) {
				if(r.getString(1).compareTo(txtPlaka.getText()) == 0) {

					count = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
}
