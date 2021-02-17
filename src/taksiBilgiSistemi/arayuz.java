package taksiBilgiSistemi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class arayuz extends JFrame {

	private JPanel contentPane;
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
					arayuz frame = new arayuz(conn);
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
	public arayuz(Connection conn) {
		setTitle("TAKS\u0130 B\u0130LG\u0130 S\u0130STEM\u0130");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 903, 537);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TAKS\u0130 B\u0130LG\u0130 S\u0130STEM\u0130");
		lblNewLabel.setFont(new Font("Impact", Font.PLAIN, 30));
		lblNewLabel.setBounds(333, 29, 257, 47);
		contentPane.add(lblNewLabel);
		
		JButton btnPerson = new JButton("PERSONEL \u0130\u015ELEMLER\u0130");
		btnPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				personelArayuz personel = new personelArayuz(conn);
				personel.setVisible(true);
				
			}
		});
		btnPerson.setBackground(new Color(102, 204, 204));
		btnPerson.setForeground(new Color(0, 51, 102));
		btnPerson.setFont(new Font("Impact", Font.PLAIN, 21));
		btnPerson.setBounds(54, 121, 325, 136);
		contentPane.add(btnPerson);
		
		JButton btnAraclar = new JButton("ARA\u00C7 \u0130\u015ELEMLER\u0130");
		btnAraclar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				aracArayuz openArac = new aracArayuz(conn);
				openArac.setVisible(true);
			}
		});
		btnAraclar.setForeground(new Color(0, 51, 102));
		btnAraclar.setFont(new Font("Impact", Font.PLAIN, 21));
		btnAraclar.setBackground(new Color(102, 204, 204));
		btnAraclar.setBounds(487, 121, 325, 136);
		contentPane.add(btnAraclar);
		
		JButton btnMusteriler = new JButton("M\u00DC\u015ETER\u0130 \u0130\u015ELEMLER\u0130");
		btnMusteriler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				musteriArayuz openMusteri = new musteriArayuz(conn);
				openMusteri.setVisible(true);
			}
		});
		btnMusteriler.setForeground(new Color(0, 51, 102));
		btnMusteriler.setFont(new Font("Impact", Font.PLAIN, 21));
		btnMusteriler.setBackground(new Color(102, 204, 204));
		btnMusteriler.setBounds(54, 309, 325, 136);
		contentPane.add(btnMusteriler);
		
		JButton btnYolculuk = new JButton("YOLCULUK \u0130\u015ELEMLER\u0130");
		btnYolculuk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				yolculukArayuz openArayuz = new yolculukArayuz(conn);
				openArayuz.setVisible(true);
			}
		});
		btnYolculuk.setForeground(new Color(0, 51, 102));
		btnYolculuk.setFont(new Font("Impact", Font.PLAIN, 21));
		btnYolculuk.setBackground(new Color(102, 204, 204));
		btnYolculuk.setBounds(487, 309, 325, 136);
		contentPane.add(btnYolculuk);
		
		String query1 = "CREATE VIEW soforler AS SELECT ssn FROM personel";
		String query2 = "CREATE VIEW yolcular AS SELECT tc FROM musteriler";
		String query3 = "CREATE TYPE new_yolculuk AS (id integer, baslangic VARCHAR(20), bitis varchar(20), cost integer, plaka varchar(7), customer varchar(11), sofor varchar(11));" +
				"CREATE or REPLACE FUNCTION yolculuk_bilgisi(m_tc yolculuk.musteri_id%type)" + 
				"RETURNS new_yolculuk AS '" + 
				"DECLARE " + 
				"bilgi new_yolculuk; " +
				"BEGIN " + 
				"SELECT yolculuk_id, baslangic, bitis, tutar, arac_plaka, musteri_id, sofor_id INTO bilgi " + 
				"				FROM yolculuk " + 
				"				WHERE musteri_id = m_tc; " + 
				"return bilgi;" +
				"END;" + 
				"' LANGUAGE 'plpgsql';";
		String query4 = "CREATE or REPLACE FUNCTION toplam_kazanc(sofor_tc personel.ssn%type) " + 
				"RETURNS integer AS '" + 
				"DECLARE " + 
				"kazanc integer;" + 
				"curs CURSOR FOR SELECT tutar FROM yolculuk WHERE sofor_id = sofor_tc;" + 
				"BEGIN " + 
				"kazanc := 0;" + 
				"FOR satir IN curs LOOP " + 
				"kazanc := kazanc + satir.tutar;" + 
				"END LOOP;" + 
				"RETURN kazanc;" + 
				"END;" + 
				"' LANGUAGE 'plpgsql';";
		String query5 = "CREATE TYPE customer AS(ad varchar(20), soyad varchar(20), tutar integer, tercih_sayisi integer);"+
				"CREATE or REPLACE FUNCTION encok_terciheden() " + 
				"RETURNS customer[] AS '" + 
				"DECLARE " + 
				"c customer[];" + 
				"i integer;" +
				"curs CURSOR FOR SELECT ad, soyad, sum(tutar), count(*) FROM yolculuk, musteriler WHERE tc = musteri_id GROUP BY tc ORDER BY count(*) DESC LIMIT 5;" + 
				"BEGIN " + 
				"i := 1;" + 
				"FOR satir IN curs LOOP " + 
				"c[i] = satir;" + 
				"i := i + 1;" +
				"END LOOP;" + 
				"RETURN c;" + 
				"END;" + 
				"' LANGUAGE 'plpgsql';";
		String query6 = "CREATE OR REPLACE FUNCTION trig_fonk_maas() " + 
				"RETURNS TRIGGER AS $$ " + 
				"BEGIN " + 
				"IF(new.maas>1.5*old.maas) THEN " + 
				"RETURN old;" + 
				"ELSE " + 
				"RETURN new;" + 
				"END IF;" + 
				"END;" + 
				"$$ LANGUAGE 'plpgsql';" +
				"CREATE TRIGGER trg_maas " + 
				"BEFORE UPDATE " + 
				"ON personel " + 
				"FOR EACH ROW EXECUTE PROCEDURE trig_fonk_maas();";
		String query7 = "CREATE OR REPLACE FUNCTION trig_yolculuk() " + 
				"RETURNS TRIGGER AS $$ " + 
				"BEGIN " + 
				"update musteriler " + 
				"set toplam_yolculuk_sayisi=toplam_yolculuk_sayisi + 1 " + 
				"where tc=new.musteri_id;" + 
				"return new;" + 
				"END; " + 
				"$$ LANGUAGE 'plpgsql';" +
				"CREATE TRIGGER trg_y " + 
				"AFTER INSERT " + 
				"ON yolculuk " + 
				"FOR EACH ROW EXECUTE PROCEDURE trig_yolculuk();";
		try {
			
			Statement s1 = conn.createStatement();
			Statement s2 = conn.createStatement();
			Statement s3 = conn.createStatement();
			Statement s4 = conn.createStatement();
			Statement s5 = conn.createStatement();
			Statement s6 = conn.createStatement();
			Statement s7 = conn.createStatement();

			s7.executeUpdate(query7);
			s6.executeUpdate(query6);
			s5.executeUpdate(query5);
			s4.executeUpdate(query4);
			s3.executeUpdate(query3);
			s1.executeUpdate(query1);
			s2.executeUpdate(query2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
