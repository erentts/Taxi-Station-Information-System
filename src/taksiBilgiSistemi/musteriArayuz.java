package taksiBilgiSistemi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
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

public class musteriArayuz extends JFrame {

	private JPanel contentPane;
	private JTextField txtMusteri;
	private JTable table_1;
	private DefaultTableModel person;
	public static String tc;

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
					musteriArayuz frame = new musteriArayuz(conn);
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
	public musteriArayuz(Connection conn) {
		setTitle("M\u00DC\u015ETER\u0130 \u0130\u015ELEMLER\u0130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1059, 570);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 134, 994, 273);
		contentPane.add(scrollPane);
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		table_1.setBorder(UIManager.getBorder("EditorPane.border"));
		table_1.setRowHeight(25);
		table_1.setBackground(new Color(255, 165, 0));
		table_1.setModel(new DefaultTableModel(new Object[][] {},new String[] {"tc","ad","soyad","adres","cinsiyet","Toplam Yolculuk Sayýsý"}) {
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
				arayuz backArayuz = new arayuz(conn);
				backArayuz.setVisible(true);
			}
		});
		btnGeriDon.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnGeriDon.setBounds(12, 438, 146, 46);
		contentPane.add(btnGeriDon);
		
		JButton btnMusteriEkle = new JButton("M\u00FC\u015Fteri Ekle");
		btnMusteriEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				musteriEkle newMusteri = new musteriEkle(conn);
				newMusteri.setVisible(true);
			}
		});
		btnMusteriEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnMusteriEkle.setBounds(454, 53, 163, 46);
		contentPane.add(btnMusteriEkle);
		
		JButton btnMusteriSil = new JButton("M\u00FC\u015Fteri Sil");
		btnMusteriSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String tc = txtMusteri.getText();
				
				if(Kontrol(conn)) {
					
					String query = "DELETE FROM musteriler where tc = '" + tc + "'" ;
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
			        txtMusteri.setText("");
			        person.setRowCount(0);
			        TabloGoster(conn);
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnMusteriSil.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnMusteriSil.setBounds(629, 53, 146, 46);
		contentPane.add(btnMusteriSil);
		
		JButton btnMusteriGuncelle = new JButton("M\u00FC\u015Fteri G\u00FCncelle");
		btnMusteriGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tc = txtMusteri.getText();
				
				if(Kontrol(conn)) {
					dispose();
					musteriGuncelle updateMusteri = new musteriGuncelle(conn);
					updateMusteri.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Kayýt Bulunamadý", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		btnMusteriGuncelle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnMusteriGuncelle.setBounds(787, 53, 219, 46);
		contentPane.add(btnMusteriGuncelle);
		
		txtMusteri = new JTextField();
		txtMusteri.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		txtMusteri.setColumns(10);
		txtMusteri.setBounds(12, 53, 245, 46);
		contentPane.add(txtMusteri);
		
		JLabel lblMteriAra = new JLabel("M\u00DC\u015ETER\u0130 ARA");
		lblMteriAra.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		lblMteriAra.setBounds(64, 24, 163, 16);
		contentPane.add(lblMteriAra);
		
		JButton btnMusteriAra = new JButton("M\u00FC\u015Fteri Ara");
		btnMusteriAra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ee) {
				
				String tc = txtMusteri.getText();
				
				if(Kontrol(conn)) {
					String query = "SELECT * FROM musteriler WHERE tc = '" + tc + "'";
					try {
						person = (DefaultTableModel)table_1.getModel();
						person.setRowCount(0);
						PreparedStatement s = conn.prepareStatement(query);
						ResultSet r = s.executeQuery();
						if(!r.next()) {

						}
						else {

							Object [] row = {r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6)};
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
		btnMusteriAra.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnMusteriAra.setBounds(279, 53, 163, 46);
		contentPane.add(btnMusteriAra);
		
		JButton btn_encoktercih = new JButton("En \u00C7ok Tercih Eden M\u00FC\u015Fterilerimiz");
		btn_encoktercih.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = 0;
				int j = 0;
				String Yolcular = "";
				String[] y = new String[20];
				String[] sonuc = new String[20];
				sonuc[0] = "";
				sonuc[1] = "";
				sonuc[2] = "";
				sonuc[3] = "";
				sonuc[4] = "";
				sonuc[5] = "";
				String query = "select encok_terciheden()";
				PreparedStatement s;
				try {
					s = conn.prepareStatement(query);
					ResultSet r = s.executeQuery();
					
					while(r.next()) {
						Yolcular = r.getString(1).replace("[", "").replace(":", "").replace("]", "").replace("=", "").replace("{", "").replace("}", "").replace('"', ' ').replace("(", "").replace(")", "");
						y = Yolcular.split(",");
						sonuc[0] = "ad  soyad   tutar   yolculuk sayýsý";
						for (i = 0; i < 20; i++) {
					
								sonuc[i / 4 + 1] += y[i];
								sonuc[i / 4 + 1] += "   ";
						}
						String array[] = sonuc[1].split(" ", 2);
						sonuc[1] = array[1];
					}
					JOptionPane.showMessageDialog(null, sonuc, "En Cok Tercih Eden Müsteriler", JOptionPane.INFORMATION_MESSAGE);
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		});
		btn_encoktercih.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btn_encoktercih.setBounds(685, 438, 321, 46);
		contentPane.add(btn_encoktercih);
	
	}
	
public void TabloGoster(Connection conn) {
		
		String query = "SELECT * FROM musteriler";
		try {
			person = (DefaultTableModel)table_1.getModel();
			person.setRowCount(0);
			PreparedStatement s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
	
			while(r.next()) {

				Object [] row = {r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6)};
				person.addRow(row);
			}
			
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean Kontrol(Connection conn) {
		
		String query = "SELECT * FROM yolcular";
		PreparedStatement s;
		boolean count = false;
		try {
			s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
			while(r.next()) {
	
				if(r.getString(1).compareTo(txtMusteri.getText()) == 0) {
				
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
