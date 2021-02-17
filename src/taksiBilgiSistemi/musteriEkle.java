package taksiBilgiSistemi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class musteriEkle extends JFrame {

	private JPanel contentPane;
	private JTextField txtTcNo;
	private JTextField txtAd;
	private JTextField txtSoyad;
	private JTextField txtCinsiyet;
	private JTextArea txtAdres;

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
					musteriEkle frame = new musteriEkle(conn);
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
	public musteriEkle(Connection conn) {
		setTitle("M\u00DC\u015ETER\u0130 EKLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 575);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtTcNo = new JTextField();
		txtTcNo.setColumns(10);
		txtTcNo.setBounds(178, 94, 204, 29);
		contentPane.add(txtTcNo);
		
		JLabel lblMteriEkle = new JLabel("M\u00DC\u015ETER\u0130 EKLE");
		lblMteriEkle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 23));
		lblMteriEkle.setBounds(120, 26, 163, 27);
		contentPane.add(lblMteriEkle);
		
		JLabel lblTcKimlikNo = new JLabel("Tc Kimlik No");
		lblTcKimlikNo.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblTcKimlikNo.setBounds(43, 94, 126, 27);
		contentPane.add(lblTcKimlikNo);
		
		txtAd = new JTextField();
		txtAd.setColumns(10);
		txtAd.setBounds(178, 138, 204, 29);
		contentPane.add(txtAd);
		
		JLabel lblAd = new JLabel("Ad");
		lblAd.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAd.setBounds(43, 138, 126, 27);
		contentPane.add(lblAd);
		
		txtSoyad = new JTextField();
		txtSoyad.setColumns(10);
		txtSoyad.setBounds(178, 185, 204, 29);
		contentPane.add(txtSoyad);
		
		JLabel lblSoyad = new JLabel("Soyad");
		lblSoyad.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblSoyad.setBounds(43, 185, 126, 27);
		contentPane.add(lblSoyad);
		
		txtCinsiyet = new JTextField();
		txtCinsiyet.setColumns(10);
		txtCinsiyet.setBounds(178, 227, 204, 29);
		contentPane.add(txtCinsiyet);
		
		JLabel lblCinsiyet = new JLabel("Cinsiyet");
		lblCinsiyet.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblCinsiyet.setBounds(43, 227, 126, 27);
		contentPane.add(lblCinsiyet);
		
		JLabel lblAdres = new JLabel("Adres");
		lblAdres.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 16));
		lblAdres.setBounds(43, 296, 82, 27);
		contentPane.add(lblAdres);
		
		JTextArea txtAdres = new JTextArea();
		txtAdres.setBounds(178, 279, 204, 69);
		contentPane.add(txtAdres);
		
		JButton btnMusteriEkle = new JButton("M\u00DC\u015ETER\u0130 EKLE");
		btnMusteriEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String tc = txtTcNo.getText();
				String ad = txtAd.getText();
				String soyad = txtSoyad.getText();
				String adres = txtAdres.getText();
				String cinsiyet = txtCinsiyet.getText();
				
				String query = "INSERT INTO musteriler(tc, ad, soyad, adres, cinsiyet,toplam_yolculuk_sayisi) " +
	                       "VALUES( '" + tc + "', '" + ad + "', '" + soyad + "' ,'" + adres + "', '" + cinsiyet +  "', '"+ "0" +"' )";
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
				
				dispose();
				musteriArayuz backArayuz = new musteriArayuz(conn);
				backArayuz.setVisible(true);
			}
		});
		btnMusteriEkle.setForeground(Color.BLACK);
		btnMusteriEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnMusteriEkle.setBackground(Color.WHITE);
		btnMusteriEkle.setBounds(86, 398, 283, 85);
		contentPane.add(btnMusteriEkle);
		
	}
}
