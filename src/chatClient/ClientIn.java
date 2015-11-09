package chatClient;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientIn implements Runnable {

	private boolean flag = false;

	private Client		client;

	ObjectInputStream in = null;

	public ClientIn(boolean flag, Client client, ObjectInputStream in) {
		// TODO Auto-generated constructor stub
		this.flag = flag;
		this.client = client;
		this.in = in;
	}

	public void run() {
		while (flag) {
			try {
				client.in((String)in.readObject());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ClassNotFoundException");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException");
				e.printStackTrace();
				flag = false;
				client.log("connect out");
			}
		}
	}
	
	

}