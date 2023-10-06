import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Login extends JFrame {
    public JTextField txtUsername;
    private JTextField txtPassword;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JButton btnSignUp;
    private JButton btnLogin;
    public double balance=100;
    public static String inputUsername;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
    	setTitle("WSU Cafeteria | Login");    //setting the title of the Form
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));      
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        
        lblUsername = new JLabel("Username:");  //creating a label marked as User name
        lblUsername.setBackground(new Color(238, 109, 255));      
        lblUsername.setBounds(50, 50, 100, 30);
        lblUsername.setFont(labelFont);
        getContentPane().add(lblUsername);
      
      

        lblPassword = new JLabel("Password:");  //creating a label marked as  Password
        lblPassword.setBackground(new Color(247, 105, 242));
        lblPassword.setBounds(50, 100, 100, 30); 
        lblPassword.setFont(labelFont);
        getContentPane().add(lblPassword);

        txtUsername = new JTextField();    //text field where User writes their user name
        txtUsername.setBounds(150, 50, 200, 30);
        getContentPane().add(txtUsername);       
        txtUsername.setColumns(10);

        txtPassword = new JPasswordField();  //text field where user inputs the password
        txtPassword.setBounds(150, 100, 200, 30);
        getContentPane().add(txtPassword);       
        txtPassword.setColumns(10);

        btnSignUp = new JButton("Sign Up");   //Sign up button in case the user is a new User
        btnSignUp.setBounds(50, 160, 120, 30);
        btnSignUp.setBackground(new Color(44, 62, 80));
        btnSignUp.setForeground(Color.MAGENTA);
        btnSignUp.setFont(buttonFont);
        btnSignUp.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        getContentPane().add(btnSignUp);
        

        btnLogin = new JButton("Login");  //login button to login 
        btnLogin.setBounds(230, 160, 120, 30);
        btnLogin.setBackground(new Color(34, 167, 240));
        btnLogin.setForeground(Color.MAGENTA);
        btnLogin.setFont(buttonFont);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        getContentPane().add(btnLogin);
        
        
/*
 * adding action listener to Sign up button
 */
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setBtnSignUp("loginInfo.txt");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
               JOptionPane.showInternalMessageDialog(null, "Successfully loggeed in to the System", inputUsername, JOptionPane.INFORMATION_MESSAGE);
            }
        });
                /*
         * adding action listener to Login button
         */
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setBtnLogin(); 
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        
        
        /*
         * trying to make my form look good but failed successfully because MacBook is VERY BAD at adding colors
         */
        
        btnSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSignUp.setBackground(new Color(52, 73, 94));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSignUp.setBackground(new Color(44, 62, 80));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSignUp.setBackground(new Color(34, 49, 63));
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSignUp.setBackground(new Color(52, 73, 94));
            }
        });

        
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(52, 152, 219));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(34, 167, 240));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(21, 101, 192));
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(52, 152, 219));
            }
        });
        
        
    }

    /*
     * login method
     */
    protected void setBtnLogin() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("loginInfo.txt")); 
        String username = null;
        String password = null;
       
        LinkedList<String> arr = new LinkedList<>(); //Creating a new Linked list called "arr". (Used linked list instead of Array list because it's fatser in this case)
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",\\s*");
            arr.addAll(Arrays.asList(tokens));  //adds all of the elements in the tokens array to the end of the arr list.
        }

        inputUsername = txtUsername.getText();
        String inputPassword = txtPassword.getText();
        String initialBalance = null; //initial balance are set at $100 when a user signs up for the first time
        
        /*
         * checking if the provided Username and Password match
         */

        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or password could not be empty!", "Login Form", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean found = false;
            for (int i = 0, j = 1, k=2; k < arr.size(); i += 3, j += 3,k+=3) {
            	initialBalance= String.valueOf(arr.get(k));
                if (Objects.equals(arr.get(i), inputUsername) && Objects.equals(arr.get(j), inputPassword)) {
                    found = true;
                    break;
             /*
             * this if statement checks if there is already someone with the same username as the current user so that dublicate users aren't created.
             */  
                  
                }else if(Objects.equals(arr.get(i), inputUsername) && !Objects.equals(arr.get(j), inputPassword)) {
                	
                     	btnSignUp.setEnabled(false); 
            }
        }

            if (found) {  //directing to main form when Username and Password match
                Main mainForm = new Main(inputUsername,initialBalance);            
                mainForm.setVisible(true);
               this.dispose(); //closing the Login form
                } else {
                JOptionPane.showMessageDialog(this, "Username or password do not match!", "Login Form", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
/*
 * Creating a sign up functionality so that new users can be enrolled in the system
 */
    protected void setBtnSignUp(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true)); //stores username and password in loginInfo.txt file    
        bw.write(txtUsername.getText()+",\s"+ txtPassword.getText()+",\s"+balance+",\s");      
        bw.flush();
        bw.close();
        
    }
    
    
  

}



