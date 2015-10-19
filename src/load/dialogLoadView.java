package load;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JProgressBar;

import edit.panelEditView;
import main.MainApp.MainAppModel;
import main.dataImma;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class dialogLoadView extends JDialog 
{
	private dialogLoadController 	controller;
	private dialogLoadModel			model;
	private JProgressBar progressBarData;
	
	public dialogLoadView(Frame owner, String title, boolean modal) 
	{
		super(owner, title, modal);
		setUndecorated(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) 
			{
				try {
					model.doLoading();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBorder(new LineBorder(SystemColor.textHighlight, 4, true));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel labelInfo = new JLabel("Chargement des donn\u00E9es...");
		labelInfo.setBounds(223, 24, 284, 31);
		panel.add(labelInfo);
		labelInfo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		progressBarData = new JProgressBar();
		progressBarData.setBounds(10, 80, 620, 31);
		panel.add(progressBarData);
		
		// création du controller et du model
		controller = new dialogLoadController();
		try 
		{
			model = new dialogLoadModel();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Probleme lors du chargement des données");
		}
		
		this.setSize(640,200);
		this.setLocationRelativeTo(owner);
		
		
		
	}
	
	

	public JProgressBar getProgressBarData() {
		return progressBarData;
	}



	public class dialogLoadController
	{
		private dialogLoadView view;

		public dialogLoadController()
		{
			super();
			// lien vers la vue
			view = dialogLoadView.this;
		}
		
		
	}
	
	public  class dialogLoadModel
	{
		private int nbReportingLoaded;
		private  dialogLoadView view;
		private String[] splitReporting;
		
		
		public dialogLoadModel() throws IOException 
		{
			super();
			// lien vers la vue
			view = dialogLoadView.this;
			
		}
		
		public void doLoading() throws IOException
		{
			// CHARGEMENT DES DATA
						String pathApp = System.getProperty("user.dir");
						String fs  = System.getProperty("file.separator");
						// path du fichier reporting.pro
						String pathData = pathApp + fs + "bngdata" + fs + "bng" + fs + "reporting.pro";
						
						FileInputStream fis = new FileInputStream(pathData);
						byte[] buffer = new byte[fis.available()];
						fis.read(buffer);
						
						// chargement dans un String
						String reporting = new String(buffer);
						// split
						splitReporting = reporting.split(System.getProperty("line.separator"));
						// nombre de ligne
						nbReportingLoaded = splitReporting.length;
						
						// intialise le progressbar
						view.getProgressBarData().setMinimum(0);
						view.getProgressBarData().setMaximum(nbReportingLoaded);
						view.getProgressBarData().setValue(0);
						view.getProgressBarData().setStringPainted(true);
			// chargement 
			doLoad load = new doLoad(splitReporting,view);
			load.start();
		}

		public int getNbReportingLoaded() {
			return nbReportingLoaded;
		}
		
		
	}
	
	public class doLoad extends Thread
	{
		private dialogLoadView view;
		private String[] split;
		
		public doLoad(String[] splitReporting,dialogLoadView view)
		{
			this.view = view;
			this.split = splitReporting;
		}
		
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			super.run();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// chargement
			int cpt = 0;
			for(String s : split)
			{
				cpt++;
				
				// on découpe l'immatriculation et le type de vol par une tabulation
				String[] row = s.split("\t");
				// ajout d'une immatriculation
				dataImma data = new dataImma();
				data.immatriculation = row[0].trim().toUpperCase();
				data.info = row[1].trim();
				MainAppModel.immatriculations.add(data);
				// modif du progressbar
				view.getProgressBarData().setValue(cpt);
				//view.getProgressBarData().updateUI();
			}
			
			// attente 3 sec et fermeture de la dialog
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			view.setVisible(false);
			
		}
		
	}
}
