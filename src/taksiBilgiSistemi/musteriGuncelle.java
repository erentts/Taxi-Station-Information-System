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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class musteriGuncelle extends JFrame {

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
					musteriGuncelle frame = new musteriGuncelle(conn);
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
	public musteriGuncelle(Connection conn) {
		setTitle("M\u00DC\u015ETER\u0130 G\u00DCNCELLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 572);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtTcNo = new JTextField();
		txtTcNo.setColumns(10);
		txtTcNo.setBounds(181, 81, 204, 29);
		contentPane.add(txtTcNo);
		
		JLabel lblMteriGncelle = new JLabel("M\u00DC\u015ETER\u0130 G\u00DCNCELLE");
		lblMteriGncelle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 23));
		lblMteriGncelle.setBounds(123, 13, 223, 27);
		contentPane.add(lblMteriGncelle);
		
		JLabel label_1 = new JLabel("Tc Kimlik No");
		label_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_1.setBounds(46, 81, 126, 27);
		contentPane.add(label_1);
		
		txtAd = new JTextField();
		txtAd.setColumns(10);
		txtAd.setBounds(181, 125, 204, 29);
		contentPane.add(txtAd);
		
		JLabel label_2 = new JLabel("Ad");
		label_2.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_2.setBounds(46, 125, 126, 27);
		contentPane.add(label_2);
		
		txtSoyad = new JTextField();
		txtSoyad.setColumns(10);
		txtSoyad.setBounds(181, 172, 204, 29);
		contentPane.add(txtSoyad);
		
		JLabel label_3 = new JLabel("Soyad");
		label_3.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_3.setBounds(46, 172, 126, 27);
		contentPane.add(label_3);
		
		txtCinsiyet = new JTextField();
		txtCinsiyet.setColumns(10);
		txtCinsiyet.setBounds(181, 214, 204, 29);
		contentPane.add(txtCinsiyet);
		
		JLabel label_4 = new JLabel("Cinsiyet");
		label_4.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_4.setBounds(46, 214, 126, 27);
		contentPane.add(label_4);
		
		JLabel label_6 = new JLabel("Adres");
		label_6.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 16));
		label_6.setBounds(46, 283, 82, 27);
		contentPane.add(label_6);
		
		JTextArea txtAdres = new JTextArea();
		txtAdres.setBounds(181, 266, 204, 69);
		contentPane.add(txtAdres);
		
		txtTcNo.setText(musteriArayuz.tc);
		txtTcNo.disable();
		String tc = musteriArayuz.tc;
		
		String query = "SELECT * FROM musteriler WHERE tc = '" + tc + "'";
		try {
			
			PreparedStatement s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
			
			while(r.next()) {
				
				txtAd.setText(r.getString(2));
				txtAd.disable();
				txtSoyad.setText(r.getString(3));
				txtSoyad.disable();
				txtAdres.setText(r.getString(4));
				txtCinsiyet.setText(r.getString(5));
				txtCinsiyet.disable();
				
			}
			
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton txtMusteriGuncelle = new JButton("M\u00DC\u015ETER\u0130 G\u00DCNCELLE");
		txtMusteriGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String adres = txtAdres.getText();
				String tc = musteriArayuz.tc;
				String query = "UPDATE musteriler SET adres = '" + adres + "' " + "WHERE tc = '" + tc + "'";
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
		txtMusteriGuncelle.setForeground(Color.BLACK);
		txtMusteriGuncelle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		txtMusteriGuncelle.setBackground(Color.WHITE);
		txtMusteriGuncelle.setBounds(82, 389, 283, 85);
		contentPane.add(txtMusteriGuncelle);
		
	}

}
