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
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class aracEkle extends JFrame {

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
					aracEkle frame = new aracEkle(conn);
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
	public aracEkle(Connection conn) {
		setTitle("ARA\u00C7 EKLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 528);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPlaka = new JTextField();
		txtPlaka.setColumns(10);
		txtPlaka.setBounds(260, 107, 204, 29);
		contentPane.add(txtPlaka);
		
		JLabel lblAraEkle = new JLabel("ARA\u00C7 EKLE");
		lblAraEkle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		lblAraEkle.setBounds(223, 33, 163, 27);
		contentPane.add(lblAraEkle);
		
		JLabel lblAraPlaka = new JLabel("Ara\u00E7 Plaka");
		lblAraPlaka.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAraPlaka.setBounds(125, 107, 126, 27);
		contentPane.add(lblAraPlaka);
		
		txtMarka = new JTextField();
		txtMarka.setColumns(10);
		txtMarka.setBounds(260, 151, 204, 29);
		contentPane.add(txtMarka);
		
		JLabel lblAraMarka = new JLabel("Ara\u00E7 Marka");
		lblAraMarka.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAraMarka.setBounds(125, 151, 126, 27);
		contentPane.add(lblAraMarka);
		
		txtModel = new JTextField();
		txtModel.setColumns(10);
		txtModel.setBounds(260, 198, 204, 29);
		contentPane.add(txtModel);
		
		JLabel lblAraModel = new JLabel("Ara\u00E7 Model");
		lblAraModel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAraModel.setBounds(125, 198, 126, 27);
		contentPane.add(lblAraModel);
		
		txtKayitTarihi = new JTextField();
		txtKayitTarihi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				txtKayitTarihi.setText(null);
			}
		});
		txtKayitTarihi.setText("01-JAN-2020");
		txtKayitTarihi.setColumns(10);
		txtKayitTarihi.setBounds(260, 247, 204, 29);
		contentPane.add(txtKayitTarihi);
		
		JLabel lblAraKaytTarihi = new JLabel("Ara\u00E7 Kay\u0131t Tarihi");
		lblAraKaytTarihi.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblAraKaytTarihi.setBounds(125, 247, 126, 27);
		contentPane.add(lblAraKaytTarihi);
		
		JButton btnAracEkle = new JButton("ARA\u00C7 EKLE");
		btnAracEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String plaka = txtPlaka.getText();
				String marka = txtMarka.getText();
				String model = txtModel.getText();
				String kayýt_tarihi = txtKayitTarihi.getText();
				String sofor = txtSoforTc.getText();
				
				String query = "INSERT INTO araclar(plaka_no, marka, model, kayit_tarihi, sofor_id) " +
	                       "VALUES( '" + plaka + "', '" + marka + "', '" + model + "' ,'" + kayýt_tarihi + "', '" + sofor + "' )";
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
		btnAracEkle.setForeground(Color.BLACK);
		btnAracEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnAracEkle.setBackground(Color.WHITE);
		btnAracEkle.setBounds(193, 362, 283, 85);
		contentPane.add(btnAracEkle);
		
		txtSoforTc = new JTextField();
		txtSoforTc.setColumns(10);
		txtSoforTc.setBounds(260, 294, 204, 29);
		contentPane.add(txtSoforTc);
		
		JLabel lblofrTcNo = new JLabel("\u015Eof\u00F6r Tc No");
		lblofrTcNo.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblofrTcNo.setBounds(125, 294, 126, 27);
		contentPane.add(lblofrTcNo);
	}

}
