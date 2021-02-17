package taksiBilgiSistemi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class personelEkle extends JFrame {

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
					personelEkle frame = new personelEkle(conn);
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
	public personelEkle(Connection conn) {
		setTitle("PERSONEL EKLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 915, 509);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPersonelTc = new JTextField();
		txtPersonelTc.setBounds(165, 105, 204, 29);
		contentPane.add(txtPersonelTc);
		txtPersonelTc.setColumns(10);
		
		JLabel lblPersonelEkle = new JLabel("PERSONEL EKLE");
		lblPersonelEkle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		lblPersonelEkle.setBounds(343, 25, 163, 27);
		contentPane.add(lblPersonelEkle);
		
		JLabel lblTcKimlikNo = new JLabel("Tc Kimlik No");
		lblTcKimlikNo.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblTcKimlikNo.setBounds(30, 105, 126, 27);
		contentPane.add(lblTcKimlikNo);
		
		txtAd = new JTextField();
		txtAd.setColumns(10);
		txtAd.setBounds(165, 149, 204, 29);
		contentPane.add(txtAd);
		
		JLabel lblAd = new JLabel("Ad");
		lblAd.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAd.setBounds(30, 149, 126, 27);
		contentPane.add(lblAd);
		
		txtSoyad = new JTextField();
		txtSoyad.setColumns(10);
		txtSoyad.setBounds(165, 196, 204, 29);
		contentPane.add(txtSoyad);
		
		JLabel lblSoyad = new JLabel("Soyad");
		lblSoyad.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblSoyad.setBounds(30, 196, 126, 27);
		contentPane.add(lblSoyad);
		
		txtDogumTarihi = new JTextField();
		txtDogumTarihi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				txtDogumTarihi.setText(null);
			}
		});
		txtDogumTarihi.setText("01-JAN-2020");
		txtDogumTarihi.setColumns(10);
		txtDogumTarihi.setBounds(165, 245, 204, 29);
		contentPane.add(txtDogumTarihi);
		
		JLabel lblDoumTarihi = new JLabel("Do\u011Fum Tarihi");
		lblDoumTarihi.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblDoumTarihi.setBounds(30, 245, 126, 27);
		contentPane.add(lblDoumTarihi);
		
		txtMaas = new JTextField();
		txtMaas.setColumns(10);
		txtMaas.setBounds(589, 105, 204, 29);
		contentPane.add(txtMaas);
		
		JLabel lblMaa = new JLabel("Maa\u015F");
		lblMaa.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblMaa.setBounds(454, 105, 126, 27);
		contentPane.add(lblMaa);
		
		txtKanGrubu = new JTextField();
		txtKanGrubu.setColumns(10);
		txtKanGrubu.setBounds(589, 149, 204, 29);
		contentPane.add(txtKanGrubu);
		
		JLabel lblKanGrubu = new JLabel("Kan Grubu");
		lblKanGrubu.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblKanGrubu.setBounds(454, 149, 126, 27);
		contentPane.add(lblKanGrubu);
		
		txtPlaka = new JTextField();
		txtPlaka.setColumns(10);
		txtPlaka.setBounds(589, 196, 204, 29);
		contentPane.add(txtPlaka);
		
		JLabel lblAraPlakas = new JLabel("Ara\u00E7 Plaka");
		lblAraPlakas.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAraPlakas.setBounds(454, 196, 126, 27);
		contentPane.add(lblAraPlakas);
		
		txtYoneticiTc = new JTextField();
		txtYoneticiTc.setColumns(10);
		txtYoneticiTc.setBounds(589, 245, 204, 29);
		contentPane.add(txtYoneticiTc);
		
		JLabel lblYneticiTcKimlik = new JLabel("Y\u00F6netici Tc No");
		lblYneticiTcKimlik.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblYneticiTcKimlik.setBounds(454, 245, 126, 27);
		contentPane.add(lblYneticiTcKimlik);
		
		JButton btnPersonelEkle = new JButton("PERSONEL EKLE");
		btnPersonelEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String tc = txtPersonelTc.getText();
				String ad = txtAd.getText();
				String soyad = txtSoyad.getText();
				String dtarih = txtDogumTarihi.getText();
				String maas = txtMaas.getText();
				String kangrubu = txtKanGrubu.getText();
				String plaka = txtPlaka.getText();
				String yönetici = txtYoneticiTc.getText();
				
				String query = "INSERT INTO personel(ssn, ad, soyad, dogum_tarihi, maas, kan_grubu, arac_plaka, superssn) " +
	                       "VALUES( '" + tc + "', '" + ad + "', '" + soyad + "' ,'" + dtarih + "', '" + maas + 
	                       "', '" + kangrubu + "', '" + plaka + "', '" + yönetici + "' )";
				Statement s = null;
		        try {
		            s = conn.createStatement();
		            s.executeUpdate(query);
		            conn.setAutoCommit(false);
		            conn.commit();
		            s.close();
		        }catch(SQLException ee){
		            ee.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Kayýt Eklenemedi.Yöneticiden daha fazla maaþ girilemez!", "Mesaj Kutusu", JOptionPane.INFORMATION_MESSAGE);
		        }	
				
				dispose();
				personelArayuz backPersonel = new personelArayuz(conn);
				backPersonel.setVisible(true);
			}
		});
		btnPersonelEkle.setBackground(Color.WHITE);
		btnPersonelEkle.setForeground(Color.BLACK);
		btnPersonelEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnPersonelEkle.setBounds(272, 329, 283, 85);
		contentPane.add(btnPersonelEkle);
	}
}
