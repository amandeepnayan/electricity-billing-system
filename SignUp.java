
package electricity.billing.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp  extends JFrame implements ActionListener{
    
    JButton create,back;
    Choice accountType;
    JTextField meter, username,name,password;
    SignUp(){
        setBounds(450,150,700,400);
        getContentPane().setBackground(Color.lightGray);
        setLayout(null);
        
        
        // BORDER AND TITTLE OF THE PAGE
        JPanel panel = new JPanel();
        panel.setBounds(30,30,650,300);
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0,0,0),2),"Create Account",TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0,0,0)));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        panel.setForeground(new Color(34,139,34));
        add(panel);
        
        //HEADING :- CREATE ACCOUNT
        JLabel heading = new JLabel("Create Account As");
        heading.setBounds(100,50,140,20);
        heading.setForeground(Color.black);
        panel.add(heading);
        
        //CHOICE 
        accountType= new Choice();
        accountType.add("Admin");
        accountType.add("Customer");
        accountType.setBounds(260,50,150,20 );
        panel.add(accountType);
        
        
        
        // METER NUMBER
        JLabel lblmeter = new JLabel("Meter Number");
        lblmeter.setBounds(100,90,140,20);
        lblmeter.setForeground(Color.black);
        lblmeter.setVisible(false);
        panel.add(lblmeter);
        
        
        meter = new JTextField();
        meter.setBounds(260,90,140,20);
        lblmeter.setVisible(false);
        panel.add(meter);
        
        meter.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {}
            
            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Conn c  = new Conn();
                    ResultSet rs = c.s.executeQuery("select * from Login where meter_number = '"+meter.getText()+"'");
                    while(rs.next()) {
                        name.setText(rs.getString("name"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
       // USERNAME
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(100,130,140,20);
        lblusername.setForeground(Color.black);
        panel.add(lblusername);
        
        
        username = new JTextField();
        username.setBounds(260,130,140,20);
        panel.add(username);  
        
        // NAME
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(100,170,140,20);
        lblname.setForeground(Color.black);
        panel.add(lblname);
        
        
        name = new JTextField();
        name.setBounds(260,170,140,20);
        panel.add(name);
        
        
        
        // PASSWORD.
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(100,210,140,20);
        lblpassword.setForeground(Color.black);
        panel.add(lblpassword);
        
        
        password = new JTextField();
        password.setBounds(260,210,140,20);
        panel.add(password);
        
        accountType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ae) {
                String user = accountType.getSelectedItem();
                if (user.equals("Customer")) {
                    lblmeter.setVisible(true);
                    meter.setVisible(true);
                    name.setEditable(false);
                } else {
                    lblmeter.setVisible(false);
                    meter.setVisible(false);
                    name.setEditable(true);
                }
            }
        });
        
        
        // CREATE BUTTON
        create = new JButton("Create");
        create.setBounds(140,250,120,30);
        //ACTION LISTENER
        create.addActionListener(this);
        panel.add(create);
        
        // BACK BUTTON
        back = new JButton("Back");
        back.setBounds(280,250,120,30);
        // ACTION LISTENER.
        back.addActionListener(this);
        panel.add(back);
        
        // ADDING IMAGE
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/signupimage.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(415,30,250,250);
        panel.add(image);
        
        
        setVisible(true);
    
 
    }
    // ACTIONS PRFORMED
      public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== create){
            String atype = accountType.getSelectedItem();
            String susername = username.getText();
            String sname = name.getText();
            String spassword = password.getText();
            String smeter = meter.getText();
            
            try{
                Conn c = new Conn();
                
                //QUERRY
                 String query = null;
                if (atype.equals("Admin")) {
                    query = "insert into Login values('"+smeter+"', '"+susername+"', '"+sname+"', '"+spassword+"', '"+atype+"')";
                } else {
                    query = "update Login set username = '"+susername+"', password = '"+spassword+"', user = '"+atype+"' where meter_number = '"+smeter+"'";
                }
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null ,"Account Created Successfully");
                
                setVisible(false);
                new Login();
            }
            
            catch(Exception e){
                e.printStackTrace();
            }
            
            
        }
        if(ae.getSource()== back){
            setVisible(false);
            
            new Login();
            }
        
        }
    
     
    
    public static void main(String[] args){
    new SignUp();
    
    } 
    
}
