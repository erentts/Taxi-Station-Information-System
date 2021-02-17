package taksiBilgiSistemi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.naming.ldap.Rdn;
import javax.swing.ButtonGroup;
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
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButton;

public class yolculukArayuz extends JFrame {

	private JPanel contentPane;
	private JTextField txtMusteritc;
	private JTable table_1;
	private DefaultTableModel yolculuk;
	public static String tc;
	private JTextField txtSofortc;
	private JRadioButton rdbtnMusteri;
	private JRadioButton rdbtnSoför;

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
					yolculukArayuz frame = new yolculukArayuz(conn);
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
	public yolculukArayuz(Connection conn) {
		
		setTitle("YOLCULUK \u0130\u015ELEMLER\u0130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1056, 575);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 183, 994, 273);
		contentPane.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		table_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		table_1.setBorder(UIManager.getBorder("EditorPane.border"));
		table_1.setRowHeight(25);
		table_1.setBackground(new Color(255, 165, 0));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {},new String[] {"id","baslangic_konum","bitis_konum","tutar","arac_plaka","musteri_tc","sofor_tc"}) {
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
		btnGeriDon.setBounds(12, 469, 146, 46);
		contentPane.add(btnGeriDon);
		
		JButton btnYolculukBilgisiEkle = new JButton("Yolculuk Bilgisi Ekle");
		btnYolculukBilgisiEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				dispose();
				yolculukEkle newYolculuk = new yolculukEkle(conn);
				newYolculuk.setVisible(true);
			}
		});
		btnYolculukBilgisiEkle.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnYolculukBilgisiEkle.setBounds(720, 78, 226, 76);
		contentPane.add(btnYolculukBilgisiEkle);
		
		txtMusteritc = new JTextField();
		txtMusteritc.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 22));
		txtMusteritc.setColumns(10);
		txtMusteritc.setBounds(116, 78, 242, 29);
		contentPane.add(txtMusteritc);
		txtMusteritc.disable();
		
		rdbtnMusteri = new JRadioButton("Musteri");
		rdbtnMusteri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtSofortc.setText(null);
				txtMusteritc.enable();
				txtSofortc.disable();
			}
		});
		rdbtnMusteri.setBounds(56, 35, 127, 25);
		contentPane.add(rdbtnMusteri);
		
		rdbtnSoför = new JRadioButton("Sof\u00F6r");
		rdbtnSoför.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtMusteritc.setText(null);
				txtSofortc.enable();
				txtMusteritc.disable();
			}
		});
		rdbtnSoför.setBounds(231, 35, 127, 25);
		contentPane.add(rdbtnSoför);
		
		ButtonGroup bG = new ButtonGroup();
		bG.add(rdbtnMusteri);
		bG.add(rdbtnSoför);
		
		JButton btnYolculukSorgula = new JButton("Yolculuk Sorgula");
		btnYolculukSorgula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ee) {
				
				String tc = null;
				String query = null;
				
				if(rdbtnMusteri.isSelected()) {
					
					tc = txtMusteritc.getText();
					query = "SELECT yolculuk_bilgisi('" + tc.toString() + "')";
				}
				else {
					
					tc = txtSofortc.getText();
					query = "SELECT * FROM yolculuk WHERE sofor_id = '" + tc + "'";
				}
				
				if(Kontrol(conn)) {
					String a = null;
					String[] b = new String[7];
					try {
						yolculuk = (DefaultTableModel)table_1.getModel();
						yolculuk.setRowCount(0);
						PreparedStatement s = conn.prepareStatement(query);
						ResultSet r = s.executeQuery();
						
						while(r.next()) {

							a = r.getString(1).replace('(', ' ');
							a = a.replace(')', ' ');
							b = a.split(",");
							Object [] row = {b[0],b[1],b[2],b[3],b[4],b[5],b[6]};
							yolculuk.addRow(row);
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
		btnYolculukSorgula.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		btnYolculukSorgula.setBounds(423, 79, 226, 75);
		contentPane.add(btnYolculukSorgula);
		
		txtSofortc = new JTextField();
		txtSofortc.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 22));
		txtSofortc.setColumns(10);
		txtSofortc.setBounds(116, 125, 242, 29);
		contentPane.add(txtSofortc);
		txtSofortc.disable();
		
		JLabel lblNewLabel = new JLabel("M\u00FC\u015Fteri TC");
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel.setBounds(27, 82, 77, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblofrTc = new JLabel("\u015Eof\u00F6r TC");
		lblofrTc.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblofrTc.setBounds(27, 125, 77, 27);
		contentPane.add(lblofrTc);
		
		JLabel lblKiminTcNumarasyla = new JLabel("Kimin TC numaras\u0131yla arama yapmak istiyorsunuz?");
		lblKiminTcNumarasyla.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblKiminTcNumarasyla.setBounds(27, 0, 354, 27);
		contentPane.add(lblKiminTcNumarasyla);
		
	}
	
	public void TabloGoster(Connection conn) {
		
		String query = "SELECT * FROM yolculuk";
	
		try {
			yolculuk = (DefaultTableModel)table_1.getModel();
			yolculuk.setRowCount(0);
			PreparedStatement s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();

			while(r.next()) {
	
				Object [] row = {r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6),r.getString(7)};
				yolculuk.addRow(row);
			}
			
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean Kontrol(Connection conn) {
		
		String query = null;
		
		if(rdbtnMusteri.isSelected()) {
				
			query = "SELECT * FROM yolcular";
		}
		else {

			query = "SELECT * FROM soforler";
		}
		
		PreparedStatement s;
		boolean count = false;
		try {
			s = conn.prepareStatement(query);
			ResultSet r = s.executeQuery();
			while(r.next()) {
	
				if(rdbtnMusteri.isSelected()) {
					
					if(r.getString(1).compareTo(txtMusteritc.getText()) == 0) {

						count = true;
					}
				}
				else {
					
					if(r.getString(1).compareTo(txtSofortc.getText()) == 0) {
					
						count = true;
					}
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
}
