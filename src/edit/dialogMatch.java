package edit;

import java.awt.Frame;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class dialogMatch extends JDialog
{
	private JLabel labelInfo;

	public dialogMatch(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == arg0.VK_ESCAPE)
				{
					// on quitte le dialog
					dialogMatch.this.setVisible(false);
				}
			}
		});
		setUndecorated(true);
		// TODO Auto-generated constructor stub
		// taille
		this.setSize(640, 200);
		// placement
		this.setLocationRelativeTo(arg0);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(51, 153, 255), 4, true));
		panel.setBackground(Color.GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel labelMatch = new JLabel("!!! POSITIVE MATCH !!!");
		labelMatch.setHorizontalAlignment(SwingConstants.CENTER);
		labelMatch.setForeground(Color.RED);
		labelMatch.setFont(new Font("Tahoma", Font.PLAIN, 48));
		panel.add(labelMatch, BorderLayout.NORTH);
		
		labelInfo = new JLabel("");
		labelInfo.setForeground(Color.ORANGE);
		labelInfo.setFont(new Font("Tahoma", Font.PLAIN, 36));
		labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(labelInfo);
		
		JLabel lblNewLabel = new JLabel("Appuyer sur la touche Esc (Escape) pour quitter");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.SOUTH);
	}

	public JLabel getLabelInfo() {
		return labelInfo;
	}
	
	

}
