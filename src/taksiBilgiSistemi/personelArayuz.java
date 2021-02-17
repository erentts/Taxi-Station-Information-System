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
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class personelArayuz extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table_1;
	private DefaultTableModel person;
	public static String PersonelTC;
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
					personelArayuz frame = new personelArayuz(conn);
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
	public personelArayuz(Connection conn) {
		setTitle("PERSONEL \u0130\u015ELEMLER\u0130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1036, 546);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 136, 994, 273);
		contentPane.add(scrollPane);
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		table_1.setBorder(UIManager.getBorder("EditorPane.border"));
		table_1.setRowHeight(25);
		table_1.setBackground(new Color(255, 165, 0));
		table_1.setModel(new DefaultTableModel(new Object[][] {},new String[] {"ssn","ad","soyad","dogum_tarihi","maas","kan_grubu","rac_plaka","superssn"}) {
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
		
		JButton btnGeriDon = new JButton("<-- Geri D\u00F6n");
		btnGeriDon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				arayuz newArayuz = new arayuz(conn);
				newArayuz.setVisible(true);
			}
		});
		btnGeriDon.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnGeriDon.setBounds(12, 440, 146, 46);
		contentPane.add(btnGeriDon);
		
		JButton btnPersonelEkle = new JButton("Personel Ekle");
		btnPersonelEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose(); 
				personelEkle newPersonel = new personelEkle(conn);
				newPersonel.setVisible(true);
			}
		});
		btnPersonelEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnPersonelEkle.setBounds(454, 55, 163, 46);
		contentPane.add(btnPersonelEkle);
		
		JButton btnPersonelSil = new JButton("Personel Sil");
				
		btnPersonelSil.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnPersonelSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
		
				String tc = textField.getText();
				
				if(Kontrol(conn)) {
					
					String query = "DELETE FROM personel where ssn = '" + tc + "'" ;
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
			        person.setRowCount(0);
			        TabloGoster(conn);
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
				textField.setText("");
			}
		});
		btnPersonelSil.setBounds(629, 55, 146, 46);
		contentPane.add(btnPersonelSil);
		
		JButton btnPersonelGuncelle = new JButton("Personel G\u00FCncelle");
		btnPersonelGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				PersonelTC = textField.getText();
				
				if(Kontrol(conn)) {
					dispose();
					personelGuncelle updatePersonel = new personelGuncelle(conn);
					updatePersonel.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnPersonelGuncelle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnPersonelGuncelle.setBounds(787, 55, 219, 46);
		contentPane.add(btnPersonelGuncelle);
		
		textField = new JTextField();
		textField.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		textField.setBounds(12, 55, 245, 46);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("PERSONEL ARA");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		lblNewLabel.setBounds(64, 26, 163, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnPersonelAra = new JButton("Personel Ara");
		btnPersonelAra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg2) {
	
				String tc = textField.getText();
				
				if(Kontrol(conn)) {
					String query = "SELECT * FROM personel WHERE ssn = '" + tc + "'";
					try {
						person = (DefaultTableModel)table_1.getModel();
						person.setRowCount(0);
						PreparedStatement s = conn.prepareStatement(query);
						ResultSet r = s.executeQuery();
						if(!r.next()) {
							
						}
						else {
						
							Object [] row = {r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6),r.getString(7),r.getString(8)};
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
		btnPersonelAra.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnPersonelAra.setBounds(279, 55, 163, 46);
		contentPane.add(btnPersonelAra);
				
		JButton btnEnokKazandranlar = new JButton("En \u00C7ok Kazand\u0131ranlar");
		btnEnokKazandranlar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ee) {
				String kazanan = "";
				String query = "SELECT ad, soyad, sum(tutar) FROM personel, yolculuk WHERE ssn = sofor_id GROUP BY ad, soyad HAVING sum(tutar) > 1000";
				try {
					
					PreparedStatement s = conn.prepareStatement(query);
					ResultSet r = s.executeQuery();
					while(r.next()) {
						
						kazanan += r.getString(1) + " ";
						kazanan += r.getString(2) + " ";
						kazanan += r.getString(3) + " TL\n";
					}
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, kazanan, "1000 TL'den fazla kazandýranlar", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnEnokKazandranlar.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnEnokKazandranlar.setBounds(787, 440, 219, 46);
		contentPane.add(btnEnokKazandranlar);
		
		JButton btn_toplam_kazanc = new JButton("\u015Eof\u00F6r\u00FCn Toplam Kazanc\u0131");
		btn_toplam_kazanc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(Kontrol(conn)) {
					
					String Toplam = "";
					String tc = textField.getText();
					String query = "select toplam_kazanc('" + tc +"')"; 

					try {
						
						PreparedStatement s = conn.prepareStatement(query);
						ResultSet r = s.executeQuery();
						while(r.next()) {
							
							Toplam += r.getString(1) + " TL\n";
						}
						s.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, Toplam, "Þoförün toplam kazancý", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btn_toplam_kazanc.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btn_toplam_kazanc.setBounds(193, 440, 233, 46);
		contentPane.add(btn_toplam_kazanc);
		
		JButton btn_maas_yapilacak = new JButton("Zam \u00D6nceli\u011Fine Sahip Olanlar");
		btn_maas_yapilacak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String query = "select ad, soyad " + 
						"from personel " + 
						"where maas < 5000 " + 
						"except " + 
						"select ad, soyad " + 
						"from personel " + 
						"where superssn is null";
				String person = "";
				try {
					
					PreparedStatement s = conn.prepareStatement(query);
					ResultSet r = s.executeQuery();
					
					while(r.next()) {
						
						person += r.getString(1) + " " + r.getString(2) + "\n";
					}
				}
				catch(SQLException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, person, "Zam Önceliðine Sahip Olanlar", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btn_maas_yapilacak.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btn_maas_yapilacak.setBounds(465, 440, 288, 46);
		contentPane.add(btn_maas_yapilacak);
		
		String yoneticimaas = null;
		String query1 = "SELECT maas FROM personel WHERE ssn = '11111111111'";
		try {
			
			PreparedStatement s = conn.prepareStatement(query1);
			ResultSet r = s.executeQuery();
			if(!r.next()) {
				
			}
			else {
				
				yoneticimaas = r.getString(1);
			}
			s.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		int maas = Integer.parseInt(yoneticimaas);
		String query2 = "ALTER TABLE personel ADD CONSTRAINT check_salary CHECK (maas < 19000)";
		
		try {
			
			Statement s2 = conn.createStatement();
			s2.executeUpdate(query2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void TabloGoster(Connection conn) {
		
		String query = "SELECT * FROM personel";
		try {
			person = (DefaultTableModel)table_1.getModel();
			person.setRowCount(0);
			PreparedStatement s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();

			while(r.next()) {
				
				Object [] row = {r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6),r.getString(7),r.getString(8)};
				person.addRow(row);
			}
			
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean Kontrol(Connection conn) {
		
		String query = "SELECT * FROM soforler";
		PreparedStatement s;
		boolean count = false;
		try {
			s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
			while(r.next()) {

				if(r.getString(1).compareTo(textField.getText()) == 0) {
				
					count = true;
				}
			}
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
}
