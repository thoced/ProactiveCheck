package edit;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Color;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.event.CaretListener;

import load.dialogLoadView.dialogLoadModel;
import main.MainApp.MainAppModel;
import main.dataImma;

import javax.swing.event.CaretEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class panelEditView extends JPanel
{
	private JTextField tEdit;
	
	private panelEditController pController;
	private panelEditModel		pModel;
	private JList listResult;
	private JPanel panelEdit;
	private JPanel panelLogo;
	private JLabel logo;
	

	/**
	 * Create the panel.
	 */
	public panelEditView()
	{
		setBackground(Color.GRAY);
		
		// création du controller
		pController = new panelEditController();
		pModel = new panelEditModel();
		
		// size
		this.setSize(600, 800);
		
		listResult = new JList();
		listResult.setBorder(new LineBorder(new Color(51, 153, 255), 4, true));
		listResult.setFont(new Font("Tahoma", Font.PLAIN, 24));
		listResult.setBackground(Color.LIGHT_GRAY);
		JScrollPane scrollPane = new JScrollPane(listResult);
		scrollPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		add(scrollPane, BorderLayout.WEST);
		
		panelEdit = new JPanel();
		panelEdit.setBorder(new LineBorder(new Color(51, 153, 255), 4, true));
		panelEdit.setBackground(Color.GRAY);
		add(panelEdit, BorderLayout.NORTH);
		panelEdit.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// création du textfield edit
		tEdit = new JTextField();
		tEdit.setForeground(Color.BLACK);
		tEdit.setDisabledTextColor(Color.BLACK);
		tEdit.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelEdit.add(tEdit);
		tEdit.setHorizontalAlignment(SwingConstants.LEFT);
		tEdit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					// effacement de l'edit
					tEdit.setText("");
					// clear de la liste des results
					((DefaultListModel)listResult.getModel()).clear();
					// couleur orange
					tEdit.setBackground(Color.ORANGE);
					listResult.setBackground(Color.LIGHT_GRAY);
				}
					
				else	
					pController.updateEdit();
			}
		});
		
		
		
		tEdit.setBackground(Color.ORANGE);
		tEdit.setFont(new Font("Tahoma", Font.PLAIN, 48));
		tEdit.setColumns(10);
		
		panelLogo = new JPanel();
		panelLogo.setBackground(Color.GRAY);
		add(panelLogo, BorderLayout.EAST);
		panelLogo.setLayout(new BorderLayout(0, 0));
		
		logo = new JLabel("");
		logo.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		logo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		logo.setIcon(new ImageIcon(panelEditView.class.getResource("/images/logo-prevac-medium.png")));
		panelLogo.add(logo, BorderLayout.SOUTH);

	}
	
	
	public JList getListResult() {
		return listResult;
	}

	public void setListResult(JList listResult) {
		this.listResult = listResult;
	}

	

	public class panelEditController
	{
		private panelEditView pView;
		

		public panelEditController() 
		{
			super(); 
			// lien vers la vue
			pView = panelEditView.this;
			
		}
		
		public void updateEdit()
		{
			
			SwingUtilities.invokeLater(doHelloWorld);
	
		}
		
		 Runnable doHelloWorld = new Runnable()
		 {
		     public void run() 
		     {
		    	 
		    	 // suppression du modellist
		    	 pModel.getModelList().clear();
		    	 // placement de la couleur 
		    	 pView.tEdit.setBackground(Color.ORANGE);
		    	 pView.getListResult().setBackground(Color.LIGHT_GRAY);
		    	 
		    	 // uppercase 
		        String text = pView.tEdit.getText().toUpperCase();
		        pView.tEdit.setText(text);
		        // si l'edit est vide, on clear la list des résult
		        if(text.length() == 0 || text.isEmpty())
		        	((DefaultListModel)pView.getListResult().getModel()).clear();
		        else
		        {
			        // recherche dans le vecteur
			        for(dataImma data : MainAppModel.immatriculations)
			        {
			        	try
			        	{
			        		String split = data.immatriculation.substring(0, text.length());
			        		if(split.equals(text))
				        	{
				        		// ajout dans la listview des résultats
				        		pModel.getModelList().addElement(data.immatriculation);
				        		// si le nombre de caractère est égal alors la plaque est complète
				        		if(text.length() == data.immatriculation.length())
				        		{
				        			// modification de la couleur de l'edit
				        			pView.tEdit.setBackground(Color.RED);
				        			// modification de la couleur de la liste
				        			pView.getListResult().setBackground(Color.RED);
				        			// affichage du dialogMatch
				        			dialogMatch diaHit = new dialogMatch(null,"",true);
				        			diaHit.getLabelInfo().setText(data.info);
				        			diaHit.setVisible(true);
				        			// on repositionne les couleurs par defauts après avoir quitté la boite de dialogMatch
				        			pView.tEdit.setBackground(Color.ORANGE);
				        			pView.getListResult().setBackground(Color.LIGHT_GRAY);
				        		}
				        		
				        		
				        			
				        	}
			        	}
			        	catch(StringIndexOutOfBoundsException sioe)
			        	{
			        		
			        	}
			        	
			        	
			        }
			        
			        // ajout du model list dans la liste
			        pView.getListResult().setModel(pModel.getModelList());
			     }
		     }
		 };
		
	}
	
	public class panelEditModel
	{
		private DefaultListModel modelList;

		
		
		public panelEditModel()
		{
			super();

			modelList = new DefaultListModel();
		}

		public DefaultListModel getModelList() {
			return modelList;
		}

		public void setModelList(DefaultListModel modelList) {
			this.modelList = modelList;
		}
		
		
	}
	
	
}
