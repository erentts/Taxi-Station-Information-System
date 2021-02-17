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

public class yolculukEkle extends JFrame {

	private JPanel contentPane;
	private JTextField txtBaslangic;
	private JTextField txtVaris;
	private JTextField txtTutar;
	private JTextField txtPlaka;
	private JTextField txtMusteriTc;
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
					yolculukEkle frame = new yolculukEkle(conn);
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
	public yolculukEkle(Connection conn) {
		setTitle("YOLCULUK B\u0130LG\u0130S\u0130 EKLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 576, 562);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtBaslangic = new JTextField();
		txtBaslangic.setColumns(10);
		txtBaslangic.setBounds(204, 93, 204, 29);
		contentPane.add(txtBaslangic);
		
		JLabel lblYolculukBilgisiEkle = new JLabel("YOLCULUK B\u0130LG\u0130S\u0130 EKLE");
		lblYolculukBilgisiEkle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 23));
		lblYolculukBilgisiEkle.setBounds(125, 27, 272, 27);
		contentPane.add(lblYolculukBilgisiEkle);
		
		JLabel txtYolculukId = new JLabel("Ba\u015Flang\u0131\u00E7 Noktas\u0131");
		txtYolculukId.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		txtYolculukId.setBounds(69, 93, 126, 27);
		contentPane.add(txtYolculukId);
		
		txtVaris = new JTextField();
		txtVaris.setColumns(10);
		txtVaris.setBounds(204, 137, 204, 29);
		contentPane.add(txtVaris);
		
		JLabel lblVarNoktas = new JLabel("Var\u0131\u015F Noktas\u0131");
		lblVarNoktas.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblVarNoktas.setBounds(69, 137, 126, 27);
		contentPane.add(lblVarNoktas);
		
		txtTutar = new JTextField();
		txtTutar.setColumns(10);
		txtTutar.setBounds(204, 184, 204, 29);
		contentPane.add(txtTutar);
		
		JLabel lblTutar = new JLabel("Tutar");
		lblTutar.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblTutar.setBounds(69, 184, 126, 27);
		contentPane.add(lblTutar);
		
		txtPlaka = new JTextField();
		txtPlaka.setColumns(10);
		txtPlaka.setBounds(204, 226, 204, 29);
		contentPane.add(txtPlaka);
		
		JLabel lblAraPlaka = new JLabel("Ara\u00E7 Plaka");
		lblAraPlaka.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAraPlaka.setBounds(69, 226, 126, 27);
		contentPane.add(lblAraPlaka);
		
		txtMusteriTc = new JTextField();
		txtMusteriTc.setColumns(10);
		txtMusteriTc.setBounds(204, 270, 204, 29);
		contentPane.add(txtMusteriTc);
		
		JLabel lblMteriTc = new JLabel("M\u00FC\u015Fteri Tc");
		lblMteriTc.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblMteriTc.setBounds(69, 270, 126, 27);
		contentPane.add(lblMteriTc);
		
		txtSoforTc = new JTextField();
		txtSoforTc.setColumns(10);
		txtSoforTc.setBounds(204, 314, 204, 29);
		contentPane.add(txtSoforTc);
		
		JLabel lblofrTc = new JLabel("\u015Eof\u00F6r Tc");
		lblofrTc.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblofrTc.setBounds(69, 314, 126, 27);
		contentPane.add(lblofrTc);
		
		JButton btnYolculukBilgisiEkle = new JButton("YOLCULUK B\u0130LG\u0130S\u0130 EKLE");
		btnYolculukBilgisiEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String baslangic = txtBaslangic.getText();
				String bitis = txtVaris.getText();
				String tutar = txtTutar.getText();
				String plaka = txtPlaka.getText();
				String musteritc = txtMusteriTc.getText();
				String sofortc = txtSoforTc.getText();
				
				String query = "INSERT INTO yolculuk(baslangic, bitis, tutar, arac_plaka, musteri_id, sofor_id) " +
	                       "VALUES( '" + baslangic + "', '" + bitis + "' ,'" + tutar + "', '" + plaka + 
	                       "', '" + musteritc + "', '" + sofortc +"' )";
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
				yolculukArayuz backArayuz = new yolculukArayuz(conn);
				backArayuz.setVisible(true);
			}
		});
		btnYolculukBilgisiEkle.setForeground(Color.BLACK);
		btnYolculukBilgisiEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnYolculukBilgisiEkle.setBackground(Color.WHITE);
		btnYolculukBilgisiEkle.setBounds(107, 396, 305, 85);
		contentPane.add(btnYolculukBilgisiEkle);
		
	}

}
