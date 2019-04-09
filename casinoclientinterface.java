

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
import java.util.ArrayList;

public interface casinoclientinterface extends Remote  {
public void saysomething(String str) throws RemoteException;
public void results(ArrayList<Integer> card_list, String winorlose) throws RemoteException;
public boolean betting() throws RemoteException;
//public void saysomethings(String str) throws RemoteException;

}
