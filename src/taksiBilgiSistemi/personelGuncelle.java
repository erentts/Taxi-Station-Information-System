package taksiBilgiSistemi;

import java.sql.Connection;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class personelGuncelle extends JFrame {

	private JPanel contentPane;
	private JTextField txtPersonelTc;
	private JTextField txtAd;
	private JTextField txtSoyad;
	private JTextField txtDogumTarihi;
	private JTextField txtMaas;
	private JTextField txtKanGrubu;
	private JTextField txtPlaka;
	private JTextField txtYoneticiTc;
	
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
					personelGuncelle frame = new personelGuncelle(conn);
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
	public personelGuncelle(Connection conn) {
		setTitle("PERSONEL G\u00DCNCELLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 897, 504);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPersonelTc = new JTextField();
		txtPersonelTc.setColumns(10);
		txtPersonelTc.setBounds(186, 110, 204, 29);
		contentPane.add(txtPersonelTc);
		txtPersonelTc.disable();
		
		JLabel lblPersonelGncelle = new JLabel("PERSONEL G\u00DCNCELLE");
		lblPersonelGncelle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		lblPersonelGncelle.setBounds(340, 31, 212, 27);
		contentPane.add(lblPersonelGncelle);
		
		JLabel label_1 = new JLabel("Tc Kimlik No");
		label_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_1.setBounds(51, 110, 126, 27);
		contentPane.add(label_1);
		
		txtAd = new JTextField();
		txtAd.setColumns(10);
		txtAd.setBounds(186, 154, 204, 29);
		contentPane.add(txtAd);
		
		JLabel label_2 = new JLabel("Ad");
		label_2.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_2.setBounds(51, 154, 126, 27);
		contentPane.add(label_2);
		
		txtSoyad = new JTextField();
		txtSoyad.setColumns(10);
		txtSoyad.setBounds(186, 201, 204, 29);
		contentPane.add(txtSoyad);
		
		JLabel label_3 = new JLabel("Soyad");
		label_3.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_3.setBounds(51, 201, 126, 27);
		contentPane.add(label_3);
		
		txtDogumTarihi = new JTextField();
		txtDogumTarihi.setColumns(10);
		txtDogumTarihi.setBounds(186, 250, 204, 29);
		contentPane.add(txtDogumTarihi);
		
		JLabel label_4 = new JLabel("Do\u011Fum Tarihi");
		label_4.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_4.setBounds(51, 250, 126, 27);
		contentPane.add(label_4);
		
		txtMaas = new JTextField();
		txtMaas.setColumns(10);
		txtMaas.setBounds(610, 110, 204, 29);
		contentPane.add(txtMaas);
		
		JLabel label_5 = new JLabel("Maa\u015F");
		label_5.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_5.setBounds(475, 110, 126, 27);
		contentPane.add(label_5);
		
		txtKanGrubu = new JTextField();
		txtKanGrubu.setColumns(10);
		txtKanGrubu.setBounds(610, 154, 204, 29);
		contentPane.add(txtKanGrubu);
		
		JLabel label_6 = new JLabel("Kan Grubu");
		label_6.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_6.setBounds(475, 154, 126, 27);
		contentPane.add(label_6);
		
		txtPlaka = new JTextField();
		txtPlaka.setColumns(10);
		txtPlaka.setBounds(610, 201, 204, 29);
		contentPane.add(txtPlaka);
		
		JLabel label_7 = new JLabel("Ara\u00E7 Plaka");
		label_7.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_7.setBounds(475, 201, 126, 27);
		contentPane.add(label_7);
		
		txtYoneticiTc = new JTextField();
		txtYoneticiTc.setColumns(10);
		txtYoneticiTc.setBounds(610, 250, 204, 29);
		contentPane.add(txtYoneticiTc);
		
		JLabel label_8 = new JLabel("Y\u00F6netici Tc No");
		label_8.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_8.setBounds(475, 250, 126, 27);
		contentPane.add(label_8);
		
		txtPersonelTc.setText(personelArayuz.PersonelTC);
		
		String tc = personelArayuz.PersonelTC;
		
		String query = "SELECT * FROM personel WHERE ssn = '" + tc + "'";
		try {
			
			PreparedStatement s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
			
			while(r.next()) {
				
				txtAd.setText(r.getString(2));
				txtAd.disable();
				txtSoyad.setText(r.getString(3));
				txtSoyad.disable();
				txtDogumTarihi.setText(r.getString(4));
				txtDogumTarihi.disable();
				txtMaas.setText(r.getString(5));
				txtKanGrubu.setText(r.getString(6));
				txtKanGrubu.disable();
				txtPlaka.setText(r.getString(7));
				txtYoneticiTc.setText(r.getString(8));
				txtYoneticiTc.disable();
				
			}
			
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton btnPersonelGuncelle = new JButton("PERSONEL G\u00DCNCELLE");
		btnPersonelGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String maas = txtMaas.getText();
				String tc = personelArayuz.PersonelTC;
				String query = "UPDATE personel SET maas = '" + maas + "' " + "WHERE SSN = '" + tc + "'";
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
		        String query2 = "SELECT maas from personel where ssn = '" + tc +"'";
		       
		        if( Integer.parseInt(maas) < 19000) {
		        	
		        	try {
			        	PreparedStatement s1 = conn.prepareStatement(query2);
						ResultSet r = s1.executeQuery();
						
						while(r.next()) {
							if(maas.compareTo(r.getString(1)) != 0) {
								
								JOptionPane.showMessageDialog(null, "Girdiðiniz maaþ,personelin eski maaþýnýn 1.5 katýndan daha fazla olamaz","Trigger Tetiklenmesi", JOptionPane.INFORMATION_MESSAGE);
							}
						}
						
						s1.close();
			        }catch(SQLException ee){
			            ee.printStackTrace();
			        }
		        }
		        else {
		        	
		        	JOptionPane.showMessageDialog(null, "Girdiðiniz maaþ,personelin yöneticisinin maaþýndan daha fazla olamaz","", JOptionPane.INFORMATION_MESSAGE);
		        }
		        
		        dispose();
		        personelArayuz backPersonel = new personelArayuz(conn);
				backPersonel.setVisible(true);
			}
		});
		btnPersonelGuncelle.setForeground(Color.BLACK);
		btnPersonelGuncelle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnPersonelGuncelle.setBackground(Color.WHITE);
		btnPersonelGuncelle.setBounds(293, 334, 283, 85);
		contentPane.add(btnPersonelGuncelle);
	
	}
	
}
