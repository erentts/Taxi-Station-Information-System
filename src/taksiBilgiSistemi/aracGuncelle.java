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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class aracGuncelle extends JFrame {

	private JPanel contentPane;
	private JTextField txtPlaka;
	private JTextField txtMarka;
	private JTextField txtModel;
	private JTextField txtKayitTarihi;
	private JTextField txtSoforTc;

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
					aracGuncelle frame = new aracGuncelle(conn);
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
	public aracGuncelle(Connection conn) {
		setTitle("ARA\u00C7 G\u00DCNCELLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 529);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPlaka = new JTextField();
		txtPlaka.setColumns(10);
		txtPlaka.setBounds(295, 105, 204, 29);
		contentPane.add(txtPlaka);
		
		JLabel lblAraGncelle = new JLabel("ARA\u00C7 G\u00DCNCELLE");
		lblAraGncelle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		lblAraGncelle.setBounds(258, 31, 163, 27);
		contentPane.add(lblAraGncelle);
		
		JLabel label_1 = new JLabel("Ara\u00E7 Plaka");
		label_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_1.setBounds(160, 105, 126, 27);
		contentPane.add(label_1);
		
		txtMarka = new JTextField();
		txtMarka.setColumns(10);
		txtMarka.setBounds(295, 149, 204, 29);
		contentPane.add(txtMarka);
		
		JLabel label_2 = new JLabel("Ara\u00E7 Marka");
		label_2.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_2.setBounds(160, 149, 126, 27);
		contentPane.add(label_2);
		
		txtModel = new JTextField();
		txtModel.setColumns(10);
		txtModel.setBounds(295, 196, 204, 29);
		contentPane.add(txtModel);
		
		JLabel label_3 = new JLabel("Ara\u00E7 Model");
		label_3.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_3.setBounds(160, 196, 126, 27);
		contentPane.add(label_3);
		
		txtKayitTarihi = new JTextField();
		txtKayitTarihi.setColumns(10);
		txtKayitTarihi.setBounds(295, 245, 204, 29);
		contentPane.add(txtKayitTarihi);
		
		JLabel label_4 = new JLabel("Ara\u00E7 Kay\u0131t Tarihi");
		label_4.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_4.setBounds(160, 245, 126, 27);
		contentPane.add(label_4);
		
		txtSoforTc = new JTextField();
		txtSoforTc.setColumns(10);
		txtSoforTc.setBounds(295, 292, 204, 29);
		contentPane.add(txtSoforTc);
		
		JLabel label_5 = new JLabel("\u015Eof\u00F6r Tc No");
		label_5.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		label_5.setBounds(160, 292, 126, 27);
		contentPane.add(label_5);
		
		txtPlaka.setText(aracArayuz.plaka);
		txtPlaka.disable();
		
		String plaka = aracArayuz.plaka;
		
		String query = "SELECT * FROM araclar WHERE plaka_no = '" + plaka + "'";
		try {
			
			PreparedStatement s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
			
			while(r.next()) {
				
				txtMarka.setText(r.getString(2));
				txtMarka.disable();
				txtModel.setText(r.getString(3));
				txtModel.disable();
				txtKayitTarihi.setText(r.getString(4));
				txtKayitTarihi.disable();
				txtSoforTc.setText(r.getString(5));
				
			}
			
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton btnAracGuncelle = new JButton("ARA\u00C7 G\u00DCNCELLE");
		btnAracGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sofortc = txtSoforTc.getText();
				String query = "UPDATE araclar SET sofor_id = '" + sofortc + "' " + "WHERE plaka_no = '" + aracArayuz.plaka + "'";
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
				aracArayuz backArayuz = new aracArayuz(conn);
				backArayuz.setVisible(true);
			}
		});
		btnAracGuncelle.setForeground(Color.BLACK);
		btnAracGuncelle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnAracGuncelle.setBackground(Color.WHITE);
		btnAracGuncelle.setBounds(228, 360, 283, 85);
		contentPane.add(btnAracGuncelle);
		
	}

}
