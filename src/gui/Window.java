package gui;

import jaCTranslator.JaC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
 
	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 295, 158);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblValiFail = new JLabel("Vali fail mida tõlkida projektikaustast:");
		lblValiFail.setBounds(45, 11, 224, 24);
		contentPane.add(lblValiFail);
		
		JButton btnValiTlgi = new JButton("Vali & t\u00F5lgi");
		btnValiTlgi.setBounds(90, 46, 108, 24);
		contentPane.add(btnValiTlgi);
		
		final JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(45, 81, 202, 14);
		lblNewLabel.setForeground(Color.GREEN);
		contentPane.add(lblNewLabel);
		btnValiTlgi.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){

				JFileChooser chooser= new JFileChooser();

				int choice = chooser.showOpenDialog(null);

				if (choice != JFileChooser.APPROVE_OPTION) return;

				String chosenFile = chooser.getSelectedFile().toString();
				chosenFile = chosenFile.substring(chosenFile.lastIndexOf("\\")+1);
				try {
					JaC.trun(chosenFile);
					lblNewLabel.setText("C++ fail(id) loodud projektikausta!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}



			}


		});
	}
}
