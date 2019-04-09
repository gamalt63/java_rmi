

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.*;
import java.rmi.RemoteException;
import java.io.*;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;
import java.lang.Object;


public class rmiclient extends JFrame implements casinoclientinterface, Serializable,ItemListener,ActionListener {
JTextArea outputarea = new JTextArea("hello",5,30);
JScrollPane scroll = new JScrollPane(outputarea);
JButton button_bet = new JButton("bet");
JButton button_login = new JButton("LOGIN");
JButton button_logoff = new JButton("LOGOFF");
JTextField name =new JTextField(22);
JPanel panel1, panel2,panel3;
Choice betchoice = new Choice();
JLabel label1= new JLabel();
JLabel label2= new JLabel();
JLabel label3= new JLabel();
JLabel label4= new JLabel();
JLabel label5= new JLabel();
ImageIcon icons;
int bet_amount=2;
boolean is_bet = false;
String string_name;
int win=0,balance= 0;
int len =0;



casinoserverinterface casinoserver;
public rmiclient()
{
       
      // JLabel jl = new JLabel(icons);
       
          
	 setSize(800,800);
         setLayout(new BorderLayout());
         button_bet.addActionListener(this);
         outputarea.setFont(new Font("Serif",Font.BOLD, 15));
         outputarea.setLineWrap(true);
         outputarea.setWrapStyleWord(true);
         len = outputarea.getDocument().getLength();
         outputarea.setCaretPosition(len);
         //JScrollPane scrollpane = new JScrollPane(outputarea);
        // new SmartScroller(scrollpane);

         betchoice.add("$10");
         betchoice.add("$20");
         betchoice.add("$50");
         betchoice.addItemListener(this);

	 panel1 = new JPanel();
         panel1.add(button_bet);
         panel1.add(betchoice);
	// panel1.add(outputarea);
        panel1.add(scroll);

         

         panel2 = new JPanel();
         panel2.setLayout(new GridLayout(0,5));

         icons = new ImageIcon("mycards/image49.png");
         label1.setIcon(icons);
         panel2.add(label1);
         icons = new ImageIcon("mycards/image49.png");
         label2.setIcon(icons);
         panel2.add(label2);
         icons = new ImageIcon("mycards/image49.png");
         label3.setIcon(icons);
         panel2.add(label3);
         icons = new ImageIcon("mycards/image49.png");
         label4.setIcon(icons);
         panel2.add(label4);
         icons = new ImageIcon("mycards/image49.png");
         label5.setIcon(icons);
         panel2.add(label5);

         panel3 = new JPanel();
         panel3.add(button_login);
         button_login.addActionListener(this);
         panel3.add(name);
         panel3.add(button_logoff)
;
         add(panel1,BorderLayout.NORTH);
         add(panel2,BorderLayout.CENTER);
         add(panel3,BorderLayout.SOUTH);

	 //this.add(panel1);

	 panel1.setVisible(true);
         panel2.setVisible(true);
         panel3.setVisible(true);
   //  icons = new ImageIcon("mycards/image41.png");
      // label1.setIcon(icons);
	 try{
		 UnicastRemoteObject.exportObject(this,1100);
		 
		 String servername = "//:2001/casino";
		 casinoserver =(casinoserverinterface)Naming.lookup(servername);
	 } catch(Exception e) {
		 outputarea.append("error exporting client, server down");
	 }
          try {
          casinoserver.connect(this);
          } catch(Exception e) { System.out.println("can not connect to server from connect");
                                 return; }
	 setVisible(true);
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		rmiclient rm = new rmiclient();
		rm.setVisible(true);

	}

public void saysomething(String serverhi) throws RemoteException
{  // outputarea.setText("");
    outputarea.append(serverhi+"\n");
    len = outputarea.getDocument().getLength();
     outputarea.setCaretPosition(len);

}

public void itemStateChanged(ItemEvent e)
  {
   String s = e.getItem().toString();
   if (s.equals("$10")) bet_amount = 10;
      else if(s.equals("$20")) bet_amount = 20;
      else if (s.equals("$50")) bet_amount = 50;
      
  }

public void actionPerformed(ActionEvent event)
   {
     String s = event.getActionCommand();
     if(s.equals("bet"))
       {
         is_bet= true;
         outputarea.append("YOU BET  " + bet_amount +"\n");
         len = outputarea.getDocument().getLength();
         outputarea.setCaretPosition(len);
       }
    else if(s.equals("LOGIN")) {
        string_name = name.getText();
      if(string_name.length() == 0)
        return;
        else
       try{
         is_bet= true;
        casinoserver.login(this,string_name);
          } catch(IOException e) { System.out.println(" something wrong in play exception"); }
      }
   }// end of actionperformed 


public boolean betting()throws RemoteException {
           if(!is_bet)
               return false;
           else
               is_bet= false;
             return true;
         }

public void results(ArrayList<Integer> bl,String winorlose) throws RemoteException
      {
         for(int i=0;i<5;i++)
          {
          System.out.println("value of arraylist in client " + bl.get(i));
               if(i==0) {
              icons = new ImageIcon("mycards/image"+bl.get(i)+".png");
               label1.setIcon(icons);
                }
                if(i==1) {
              icons = new ImageIcon("mycards/image"+bl.get(i)+".png");
               label2.setIcon(icons);
                }
                if(i==2) {
              icons = new ImageIcon("mycards/image"+bl.get(i)+".png");
               label3.setIcon(icons);
                }
                if(i==3) {
              icons = new ImageIcon("mycards/image"+bl.get(i)+".png");
               label4.setIcon(icons);
                }
                if(i==4) {
              icons = new ImageIcon("mycards/image"+bl.get(i)+".png");
               label5.setIcon(icons);
                }
              
          }//for statement
            //outputarea.setText("");
          //  System.out.println("winorlose client " + winorlose);
            outputarea.append(" " + winorlose+ "\n");
            //outputarea.append(" " + winorlose+ "\n");
            if(!"you_lose" .equals(winorlose)) {
              win= bet_amount*2;
              balance += win;
              outputarea.append("YOU WIN $ "+ win + " AND YOUR BALANCE IS $" + balance + "\n");
              len = outputarea.getDocument().getLength();
              outputarea.setCaretPosition(len);
                }
            else {
                  win = bet_amount;
                  balance -= bet_amount;
                  outputarea.append("YOU LOSE $"+ win + " AND YOUR BALANCE IS  $" + balance +"\n");
                  len = outputarea.getDocument().getLength();
                  outputarea.setCaretPosition(len);
   
                 }
          
          
      }//end of function results

     
} // end of program
