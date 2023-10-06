import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
	private JTextArea txtUsernameMain;
	private JTextArea txtBalance;
	private JComboBox<String> cbbSort;
	private JLabel lblUsername;
	private JLabel lblBalance;
	private JButton btnCheckOut;
	private JList<String> list;
	private JTextArea txtOrderSummary;
	private static DefaultListModel<String> listModel;
	ArrayList<MenuItem> menuItems = new ArrayList<>(); // create an ArrayList of MenuItem object

	public Main(String inputUsername, String initialBalance) {
		setTitle(" Winona Cafeteria | Menu ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(500, 400);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 10, 100, 20);
		panel.add(lblUsername);

		txtUsernameMain = new JTextArea(inputUsername); // text field to display user name. The user name displayed is
														// the username that the user wrote while logging in
		txtUsernameMain.setBounds(120, 10, 150, 20);
		panel.add(txtUsernameMain);

		lblBalance = new JLabel("Balance:");
		lblBalance.setBounds(10, 40, 100, 20);
		panel.add(lblBalance);

		txtBalance = new JTextArea(initialBalance);
		txtBalance.setBounds(120, 40, 150, 20);
		panel.add(txtBalance);

		/*
		 * commented out for now because of some bugs
		 */
		cbbSort = new JComboBox<String>();
		cbbSort.setBounds(300, 10, 150, 20);
		cbbSort.addItem("Sort by Name");
		cbbSort.addItem("Sort by Price");
		cbbSort.addItem("Sort by Calorie");
		cbbSort.addActionListener(e -> {
			String selectedOption = (String) cbbSort.getSelectedItem();
			sortMenuList(selectedOption);
		});
		panel.add(cbbSort);

		btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCheckOutclicked(); // if CheckOut button is clicked
			}
		});
		btnCheckOut.setBounds(344, 316, 150, 50);
		panel.add(btnCheckOut);

		txtOrderSummary = new JTextArea(); // text area that shows what food and beverages have we picked so far
		txtOrderSummary.setBounds(42, 312, 299, 54);
		panel.add(txtOrderSummary);

		/*
		 * adding Scroll Pane for Order Summary Text Area.
		 */
		JScrollPane sp = new JScrollPane(txtOrderSummary, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBounds(42, 312, 299, 54);
		panel.add(sp);
		getContentPane().add(panel);

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(42, 80, 400, 220);
		panel.add(listScrollPane);

		readMenuItemsFromFile("menu.txt"); // calling the readMenuItemsFromFile
											// method which reads menu from menu.txt file

		/*
		 * add selected item to Checkout.txt and show the order in TxtOrderSummary
		 */

		if (!listenerAdded) { // creating a checker for listener
			list.addListSelectionListener(e -> {
				if (!e.getValueIsAdjusting()) { // checking if the selection change event is caused by the user
					try {
						String selected = list.getSelectedValue();
						if (selected != null) {
							txtOrderSummary.append(selected + "\n");
							
							FileWriter fw = new FileWriter("checkout.txt", true); // writing the selected items into
																					// checkout.txt file to
																					// calculate total
							fw.write(selected + "\n");
							fw.close();
						}

					} catch (IOException ex) {
						JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
					}
				}
			});
			listenerAdded = true;
		}

	}

	/*
	 * method when Checkout button is clicked
	 */
	protected void btnCheckOutclicked() {
		this.dispose(); // gets rid of the current Main form
		CheckOut co = new CheckOut(); // goes to checkout tab
		co.setVisible(true);

	}
	/*
	 * method to go through "menu.txt" to know the name, calorie and price of the
	 * food or beverage.
	 */

	private boolean listenerAdded = false;

	private void readMenuItemsFromFile(String filename) {

		try {
			Scanner scanner = new Scanner(new File(filename)); // implementing scanner to read menu items from menu.txt
																// file
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",\\s");
				String name = parts[0];
				int calorie = Integer.parseInt(parts[1]);
				double price = Double.parseDouble(parts[2]);
				MenuItem menuItem = new MenuItem(name, calorie, price);
				menuItems.add(menuItem); // add the MenuItem object to the ArrayList
				listModel.addElement(menuItem.toString());  //populating the listModel

			}

			scanner.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Error: File not found");
		}

	}

	/*
	 * Sort the ArrayList based on the user's selection
	 */

	private void sortMenuList(String selectedOption) {
		switch (selectedOption) {
		case "Sort by Name":
			Collections.sort(menuItems, new Comparator<MenuItem>() {
				@Override
				public int compare(MenuItem o1, MenuItem o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			break;
		case "Sort by Price":
			Collections.sort(menuItems, new Comparator<MenuItem>() {
				@Override
				public int compare(MenuItem o1, MenuItem o2) {
					return Double.compare(o1.getPrice(), o2.getPrice());
				}
			});
			break;
		case "Sort by Calorie":
			Collections.sort(menuItems, new Comparator<MenuItem>() {
				@Override
				public int compare(MenuItem o1, MenuItem o2) {
					return Integer.compare(o1.getCalories(), o2.getCalories());
				}
			});
			break;
		default:
			break;
		}
		updateMenuList();
	}

	private void updateMenuList() {
		listModel.clear();
		for (MenuItem menuItem : menuItems) {
			listModel.addElement(menuItem.toString());
		}
	}

	/*
	 * main method
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			private String inputUsername;
			private String initialBalance;

			public void run() {
				try {
					Main frame = new Main(inputUsername, initialBalance);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}




	
	