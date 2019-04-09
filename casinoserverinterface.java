

	import java.rmi.Remote;
	import java.rmi.RemoteException;
	import java.lang.String;

	public interface casinoserverinterface extends Remote {
	public void connect(casinoclientinterface client) throws RemoteException;
      //  public void play(casinoclientinterface client) throws RemoteException;
        public void login(casinoclientinterface client,String name) throws RemoteException;
       // public void broadcastmessage(String message) throws RemoteException;
	}
