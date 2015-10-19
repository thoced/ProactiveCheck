package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import edit.panelEditView;
import load.dialogLoadView;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) 
			{
				// affichage de la boite de dialogue de chargement des données
				dialogLoadView dia = new dialogLoadView(frame,"Chargement des données",true);
				dia.setVisible(true);
			}
		});
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelEditView panelEditView_ = new panelEditView();
		panelEditView_.getListResult().setBounds(37, 95, 364, 435);
		frame.getContentPane().add(panelEditView_, BorderLayout.CENTER);
		
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.setBackground(Color.GRAY);
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu menuFile = new JMenu("Fichier");
		menuFile.setBackground(Color.GRAY);
		menuBar.add(menuFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Fermer");
		mntmNewMenuItem.setBackground(Color.GRAY);
		menuFile.add(mntmNewMenuItem);
		
		// changement d'icon
		frame.setIconImage(new ImageIcon(MainApp.class.getResource("/images/logo-prevac-mini.png")).getImage());
		// augmentation de la taille de la fenetre
		
	}
	
	
	
	public static class MainAppModel
	{
		public static List<dataImma> immatriculations = new ArrayList<dataImma>();
		
		
	}
}
