package chatSever;

import java.io.*;
import java.net.*;
import java.util.*;

import common.*;

/** ���� ���� Ŭ���̾�Ʈ
 * @author Administrator
 *
 */
public class ServerClientServer extends ServerClient implements Runnable {

	public ServerClientServer(Server server, String bossName) {
		super(server, bossName);
		// TODO Auto-generated constructor stub
	}
	public void run() {

	}
	public int out(Object obj) {
		log("SCS:"+(String) obj);
		return 0;		
	}
	public int log(String log) {
		// TODO Auto-generated method stub		
		return server.serverLog(nick + ":" + log);
	}
	public String getAddress() {
		return ":server";
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
