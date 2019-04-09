

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
//import casinoserverinterface.*;
//import casinoclientinterface.*;

class casinodaemon extends Thread {
private casinoserver server;
private int interval;

     casinodaemon(casinoserver server, int interval){
             this.server=server;
             this.interval = interval;
             setDaemon(true);
             start();
     }// endlof constructor

     public void run() {
            try{
                while(true){
                    server.broadcastmessage("START BETTING");
                sleep(interval);
                    server.broadcastmessage("NO MORE BETTING");
                sleep(interval);
                    server.play();
                      } }catch(InterruptedException e ) {}

          
}// end of run function
}//endl o class


public class casinoserver extends UnicastRemoteObject implements casinoserverinterface  {

  private Hashtable broadcastlist;

	public casinoserver() throws RemoteException {
		super(2001);
                broadcastlist = new Hashtable();
                new casinodaemon(this,8000);

	}

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(2001);
			//Registry rg=LocateRegistry.getRegistry();
			System.out.println("binding server");
			casinoserver casino = new casinoserver();
			Naming.rebind("//:2001/casino", casino);
			System.out.println("casino.server done");
			} catch (Exception e) {
				System.out.println("casino error :" + e.getMessage());
				e.printStackTrace();
			}
		// TODO Auto-generated method stub

	}
	public void connect(casinoclientinterface client) throws RemoteException {
                String str ="hi from the server please login";
                client.saysomething(str);
		System.out.println(" hello for anywhere");
               }

        public void login(casinoclientinterface client, String name) throws RemoteException {
                if(!broadcastlist.containsKey(name)) {
                broadcastlist.put(name,client);
                client.saysomething("you logged in , thanks");
                 }
                 else client.saysomething("You already logged in");
               }

        public void broadcastmessage(String broadcast_string){
                casinoclientinterface casinointerface;
                Enumeration num = broadcastlist.elements();
                while(num.hasMoreElements()) {
                   casinointerface =(casinoclientinterface) num.nextElement();
                try{
                   casinointerface.saysomething(broadcast_string);
                   } catch(IOException e) {}
                 }

             // client.saysomething(broadcast_string);
              }    


    // public void play(casinoclientinterface client)
       public void play()
     {
       casinoclientinterface casinointerface;
       String winorlose;
       ArrayList<Integer> al = new ArrayList();
       ArrayList<Integer> bl = new ArrayList();
       ArrayList<Integer> cl = new ArrayList();
       int [] slotarray = new int[48]; //array to put the number whrere the index are and increade the index
       Integer[] to_integer  = new Integer[48];
       System.out.println("your ar in server play");
       Integer [] cards = {9,2,7,12,11,11,8,10,5,4,2,1,4,6,10,5,4,1,13,9,2,13,7,5,4,12,14,9,12,8,5,8,9,3,10,3,7,3,13,12,1,6,6,11,8,2,10,4,12};

        for( int i=0;i<48;i++)
            al.add(i);
        Collections.shuffle(al);

        for(int i=0;i<5;i++)
            bl.add(al.get(i));

        for (int i=0;i<5;i++) {
            int card_index = cards[bl.get(i)]; //get the card
           // System.out.println("cardindeex = " + " " + card_index);
            slotarray[card_index]++;
            }
       // for(int i=0;i<48;i++) //test
         //System.out.print("slotarra = " + slotarray[i]);
          
        for(int i=0;i<48;i++) //get the occuence of cards like 444 32 or 12333 here convert from int array to Integer array to use it in sort collection accepts only integer objects.
             to_integer[i]=Integer.valueOf(slotarray[i]);

        // for(int i=0;i<48;i++) //test
        // System.out.print("tointeger = " + to_integer[i]);

         Arrays.sort(to_integer,Collections.reverseOrder());
         if(to_integer[0] == 4)
          winorlose = "win 4 of kinds";
         else if(to_integer[0] == 3)
          winorlose = "win 3 of kinds";
         else if(to_integer[0] == 2 && to_integer[1]==2)
          winorlose = "win 2 pair kinds";
         else if(to_integer[0] == 2)
          winorlose = "win 1 pair kinds";
         else winorlose = "you_lose";
         System.out.println("winorlosse in sserver" + winorlose);

        //try{
        //client.results(bl,winorlose);
            Enumeration num = broadcastlist.elements();
                while(num.hasMoreElements()) {
                   casinointerface =(casinoclientinterface) num.nextElement();
                 try {
                    boolean value = casinointerface.betting();
                       if(!value)
                       continue;
                      }catch (IOException e) {}
                  // System.out.println("bet_amount", +(casinoclientinterface) casinointerface.bet_amount);
                try{
                   casinointerface.results(bl,winorlose);
                   } catch(IOException e) {}
                 }
         // } catch(IOException e) { System.out.println(" something woring in server funtion at client call results");}

      }
          
		
}//end of program server
