package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Windowx extends JFrame {
 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JButton btnNewButton;
	private JLabel lblVljundiFilePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Windowx frame = new Windowx();
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
	public Windowx() {
	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 193);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTranslate = new JButton("Translate");
		btnTranslate.setBounds(137, 88, 129, 43);
		contentPane.add(btnTranslate);
		
		textField_1 = new JTextField();
		textField_1.setBounds(172, 57, 189, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblFailMidaTlkida = new JLabel("Fail mida tõlkida:");
		lblFailMidaTlkida.setBounds(10, 14, 118, 14);
		contentPane.add(lblFailMidaTlkida);
		
		btnNewButton = new JButton("Vali fail");
		btnNewButton.setBounds(172, 10, 89, 23);
		contentPane.add(btnNewButton);
		
		lblVljundiFilePath = new JLabel("Väljundi file path:");
		lblVljundiFilePath.setSize(500,100);
		lblVljundiFilePath.setBounds(10, 60, 141, 14);
		contentPane.add(lblVljundiFilePath);
		btnNewButton.addActionListener( new ActionListener(){
	    public void actionPerformed(ActionEvent e){

	    	JFileChooser chooser= new JFileChooser();

			int choice = chooser.showOpenDialog(null);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			File chosenFile = chooser.getSelectedFile();
//				System.out.println(chosenFile);
		 
	    }

			
		});
	}
}
