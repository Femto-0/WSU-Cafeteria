import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CheckOut extends JFrame {

	private JPanel contentPane;
	private JLabel lblTotal;
	private JTextArea txtTotal;
	private JLabel lblCurrentBalance;
	private JTextArea txtBalance;
	private JButton btnCancel;
	private JButton btnPlaceOrder;
	private double totalCost=0;
	private String thirdValue="" ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckOut co = new CheckOut();
					co.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CheckOut() {
		setTitle(" Winona Cafeteria | Checkout ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		lblTotal = new JLabel("Total: ");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 0;
		gbc_lblTotal.gridy = 0;
		contentPane.add(lblTotal, gbc_lblTotal);

		txtTotal = new JTextArea();
		GridBagConstraints gbc_txtTotal = new GridBagConstraints();
		gbc_txtTotal.insets = new Insets(0, 0, 5, 0);
		gbc_txtTotal.fill = GridBagConstraints.BOTH;
		gbc_txtTotal.gridx = 1;
		gbc_txtTotal.gridy = 0;
		contentPane.add(txtTotal, gbc_txtTotal);

		lblCurrentBalance = new JLabel("Balance: ");
		GridBagConstraints gbc_lblCurrentBalance = new GridBagConstraints();
		gbc_lblCurrentBalance.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentBalance.gridx = 0;
		gbc_lblCurrentBalance.gridy = 1;
		contentPane.add(lblCurrentBalance, gbc_lblCurrentBalance);

		txtBalance = new JTextArea();
		GridBagConstraints gbc_txtBalance = new GridBagConstraints();
		gbc_txtBalance.insets = new Insets(0, 0, 5, 0);
		gbc_txtBalance.fill = GridBagConstraints.BOTH;
		gbc_txtBalance.gridx = 1;
		gbc_txtBalance.gridy = 1;
		contentPane.add(txtBalance, gbc_txtBalance);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
               cancelButtonpressed();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 3;
		contentPane.add(btnCancel, gbc_btnCancel);

		btnPlaceOrder = new JButton("Place Order");
		btnPlaceOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				placeorderButtonPressed();               //----------------------//
			}
		});
		GridBagConstraints gbc_btnPlaceOrder = new GridBagConstraints();
		gbc_btnPlaceOrder.insets = new Insets(0, 0, 5, 0);
		gbc_btnPlaceOrder.gridx = 1;
		gbc_btnPlaceOrder.gridy = 3;
		contentPane.add(btnPlaceOrder, gbc_btnPlaceOrder);

		totalCost("checkout.txt"); // implementing method to calculate the total cost of the order

		currentBalance("loginInfo.txt", Login.inputUsername);

	}

	protected void placeorderButtonPressed() {
		txtBalance.setText("");
		double y=0;
		if(!thirdValue.isEmpty() && !thirdValue.trim().isEmpty()) {
	      y= Double.parseDouble(thirdValue);         //--------------------------//
		}else {
			clearCheckout();
			JOptionPane.showMessageDialog(btnPlaceOrder, "Couldn't find a balance associated with this acconut");
			System.exit(ABORT);
		}
		
		if(totalCost>y) {
			JOptionPane.showMessageDialog(this, "Your order's total amount exceeds your Balance!");
			System.exit(EXIT_ON_CLOSE);
		}
		double finalBalance= y- totalCost;
		txtBalance.setText(String.valueOf(finalBalance));
		updateBalance("loginInfo.txt", Login.inputUsername, String.valueOf(finalBalance));
		clearCheckout();
		JOptionPane.showMessageDialog(this, "Your order has been successfully placed!!");
	}

	protected void cancelButtonpressed() {
		clearCheckout();
        System.exit(ABORT);
    }

	private void totalCost(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));// implementing buffered reader to read
																				// from a text file to calculate the
																				// total cost
			String line;
			totalCost = 0;
			while ((line = br.readLine()) != null) {
				String[] token = line.split("-\\s*");
				double eachCost = Double.parseDouble(String.valueOf(token[2]));
				totalCost += eachCost;
			}
			br.close();
			txtTotal.append("$ " + String.valueOf(totalCost)); // adding the total cost to the txtTotal txtArea to
																// display the total cost of order
		} catch (IOException ioe) {
			System.out.println("Io exception");
		}

	}
	
	/*
	 * Implementing a buffered reader method to find out the current balance. The buffered reader works in such a way that it reads the third value only if the first value matches a String.
	 * for example in the given txt file:
     * fe, mto, 100.0, an, ka, 100.0, 
     * the String is "an" so it will skip all 3 elements at the start and since the String mathches, it will now read read the third value from the String i.e. "100.0"
	 */

	private void currentBalance(String fileName, String inputUsername) {
		String searchString = inputUsername;
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] token = line.split(",\\s*");
				if (token.length >= 3 && token[0].trim().equals(searchString)) {
					thirdValue = token[2].trim();
					txtBalance.append(thirdValue);
				}
			
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	private void updateBalance(String fileName, String inputUsername, String newBalance) {
	    String searchString = inputUsername;
	    String line;
	    boolean lineUpdated = false;
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(fileName));
	        StringBuilder sb = new StringBuilder();
	        while ((line = br.readLine()) != null) {
	            String[] token = line.split(",\\s+");
	            if (token.length >= 3 && token[0].trim().equals(searchString)) {
	            	token[2]= newBalance;
	                line = token[0] + ",\s" + token[1] + ",\s" + newBalance + ", ";
	                lineUpdated = true;
	            }
	            sb.append(line);
	        }
	        br.close();

	        if (!lineUpdated) {
	            // If the line was not updated, we need to append a new line to the end of the file
	            sb.append("\n").append(searchString).append(",\\s").append(newBalance).append(",\\s,");
	        }

	        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
	        bw.write(sb.toString());
	        bw.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void clearCheckout() {
		String filePath = "checkout.txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();          
        } catch (IOException e) {
            System.out.println("Error while clearing file: " + e.getMessage());
        }
	}

}
