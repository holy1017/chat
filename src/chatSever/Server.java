package chatSever;

import common.ChatGui;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import chatClient.Client;
import common.Protocol;

public class Server {

	private ServerSocket	server	= null;
	private Socket			socket	= null;

	private List<ServerClient>		clients	= new ArrayList<>();// Ŭ���̾�Ʈ���� ���� ������ ���⿡ �ֱ�
	private Map<String, ServerChat>	chats	= new HashMap();	// ä�ù� ������ ���⿡ �ֱ�
	//	private Map<String, ServerClient>	clients	= new HashMap<>();	//���� �����ڵ�
	//	private Map<String, ServerChat>		chats	= new HashMap<>();		//���� ������ ���

	String mainRoomKey = null;
	String bossName="#boss";

	private ServerGui gui = null;

	private boolean flag = false;

	private Protocol	p	= new Protocol();
	private ServerOrcl	o	= new ServerOrcl("localhost:1521/orcl", "chat", "chat");

	private int roomNum = 0;

	// ������ �κ�
	public Server() {
		// TODO Auto-generated constructor stub		
		setting(7777);
	}

	public Server(int port) {
		// TODO Auto-generated constructor stub		
		setting(port);
	}

	//===================== ���� ���� ���� =======================

	public int setting(int port) {
		// TODO Auto-generated method stub
		setDisplay();

		portOpen(port);//��Ĺ ����

		//����ȭ
		Collections.synchronizedList(clients);
		Collections.synchronizedMap(chats);

		ServerChat.ServerSet(this);
		ServerClient.serverSet(this);
		//		ServerClientServer.serverSet(this);
		//������ ä��â
		ServerClientServer scs = new ServerClientServer(this, bossName);//������ Ŭ���̾�Ʈ ����
		ServerChat sc = chatRoomCreate(scs, "#All");//������ ���� ä�ù�
		mainRoomKey = sc.key;
		addClient(scs);//Ŭ�� ����Ʈ�� ���

		//Ŭ�� ���� �ޱ�
		while (flag) {
			try {//Ŭ���̾�Ʈ �����븶��

				serverLog("wait client");
				socket = server.accept();
				ServerClient client = new ServerClient(this, socket);
				new Thread(client).start();//������ ����
				addClient(client);//���� �����ڿ� �߰�
				client.out((String) p.col�����(1, 6, 0) + getChatList());//Ŭ�󿡰� ����Ʈ ����

				//				client.chatRoomJoin( mainRoomKey);//���� ���� ä�ù濡 �߰�

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				serverLog("accept failed");
				flag = false;
			}
		}
		return 0;
	}

	void portOpen(int port) {
		try {
			server = new ServerSocket(port);//��Ĺ ����
			serverLog(port + " ok");
			//			clients = new Vector<ServerClient>();
			//			chats = new Vector<ServerChat>();
			flag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			serverLog("server Socket failed");
		}
	}

	int setDisplay() {
		gui = new ServerGui();
//		gui = new ServerGui(this);
		gui.clientListSet(clients);
		gui.chatListSet(chats);
		gui.setTitle("ServerGui");
		//		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		//		gui.setSize(300, 600);
		return 0;
	}

	//===================== Ŭ�� ���� ���� =======================

	public synchronized void addClient(ServerClient client) {
		serverLog("addClient:" + client.getAddress());

		clients.add(client);
		gui.clientListSet(clients);
		clientListReflash();
		//		chatListReflash();

		serverLog("clients:" + clients.size());
	}

	public synchronized void removeClient(ServerClient client) {
		serverLog("removeClient" + clients.indexOf(client));

		chats.get(mainRoomKey).chatRoomOut(client);//����êâ��

		clients.remove(client);
		gui.clientListSet(clients);
		clientListReflash();
		//		chatListReflash();

		serverLog("clients:" + clients.size());
	}

	//===================== ȸ�� ���� ���� =======================

	/** ȸ������ db üũ��  ���
	 * @param id
	 * @param pw
	 * @param nick
	 * @return 1:����, 0:����, -1:����, 
	 * -2/-4:id/nick ��ȸ����
	 * -3/-5:id/nick ������
	 * */
	public synchronized int userCreate(String id, String pw, String nick) {
		// TODO Auto-generated method stub
		return o.createUser(id, pw, nick);
	}

	/**�α��� db üũ
	 * @param ID
	 * @param Pw
	 * @return 1:����, 0:����, -1:����, -2:id ��ȣƲ�� -3:id ���� ����
	 */
	public synchronized int userLogin(String id, String pw) {
		// TODO Auto-generated method stub
		//		o.userLogin(id,pw);
		//		if(v==1){
		//			
		//			gui.clientListSet(clients);
		//		}
		//		server.clientListReflash();//�α��� ������ ��� Ŭ���̾�Ʈ���� �˸�//
		return o.userLogin(id, pw);
	}

	/** �г��� ���� Ȯ��
	 * @param id
	 * @return
	 */
	public synchronized String userNick(String id) {
		// TODO Auto-generated method stub
		return o.userNick(id);
	}

	public synchronized int userLogout(String id, String Pw) {
		// TODO Auto-generated method stub
		gui.clientListSet(clients);
		//		clientListReflash();//�α��� ������ ��� Ŭ���̾�Ʈ���� �˸�
		return 1;
	}

	/**�г��� db üũ ����
	 * @param client
	 * @param nick
	 * @return
	 */
	public synchronized String userNickChange(ServerClient client, String nick) {
		// TODO Auto-generated method stub
		if (o.chackNick(nick)==0x000c ) {
			o.nickChange(client.GetId(),nick);
//			client.userNickChangeOk(nick);
//			gui.clientListSet(clients);
//			clientListReflash();//�α��� ������ ��� Ŭ���̾�Ʈ���� �˸�		
			return nick;
		}else{			
			return null;
		}
	}

	//===================== ä�ù� ���� =======================

	/** ä�ù� ����
	 * @param client
	 * @param roomName
	 * @return
	 */
	public synchronized ServerChat chatRoomCreate(ServerClient client, String roomName) {
		// TODO Auto-generated method stub
		String num = roomNum++ + "";
		ServerChat chat = new ServerChat(num, roomName, client);
		chats.put(num, chat);
//		client.chatRoomCreateOk(chat);
		chatListReflash();
		//		gui.chatListSet(chats);
		return chat;
	}

	/** ä�ù� ����
	 * @param client
	 * @param chatnum
	 * @return
	 */
	public synchronized ServerChat chatRoomJoin(ServerClient client, String roomKey) {
		serverLog("chatRoomJoin1:" + chats.get(roomKey).key);
//		chats.get(roomKey).chatRoomJoin(client);
		//		client.chatRoomJoinOk(chats.get(roomKey));
		// TODO Auto-generated method stub

		return chats.get(roomKey);
	}

	/** ä�ù� ����. ���常 ����.
	 * @param client
	 * @param chatnum
	 * @return
	 */
	public synchronized int chatRoomDelete(String id, String key) {
		// TODO Auto-generated method stub
		chats.get(key).chatRoomDelete(id);
		chats.remove(key);
		chatListReflash();
		gui.chatListSet(chats);
		return 0;
	}

	/** ���ο��� ������ ����ϴ� �޼ҵ�
	 * @param key
	 * @return
	 */
	public synchronized int chatRoomDelete(String key) {
		// TODO Auto-generated method stub
		chats.remove(key);
		chatListReflash();
		gui.chatListSet(chats);
		return 0;
	}

	//===================== gui ��� ���� =======================

	/** ����gui�� ������ ���� �޼ҵ�
	 * @param log
	 * @return
	 */
	public int serverLog(String log) {
		// TODO Auto-generated method stub
		//		System.out.println(log);
		gui.logAdd(log);
		return 0;
	}
	public int serverLog(String log,String style) {
		// TODO Auto-generated method stub
		//		System.out.println(log);
		gui.logAdd(log, style);
		return 0;
	}

	//======================����Ʈ ���� ���� ================

	/**
	 * Ŭ���̾�Ʈ ���̵� ���� ������ �ٲ�� �簻��
	 * �� Ŭ���̾�Ʈ���� ������ ����Ʈ ����
	 */
	public synchronized void clientListReflash() {
		// TODO Auto-generated method stub
		gui.clientListSet(clients);

		chats.get(mainRoomKey).clientListReflash();//���� ä��â��.

		//�� Ŭ���̾�Ʈ���� ������ ����Ʈ ����
		String t = getClientList();

		//		serverLog("clientListReflash:" + t);
		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
			ServerClient client = (ServerClient) iterator.next();
			client.out((String) p.col�����(1, 16, 0) + t + "���� ������ ���");
		}

	}

	/**clientListReflash() �޼ҵ��
	 * @return Ŭ���̾�Ʈ ����Ʈ ���ڿ� ����
	 */
	public synchronized String getClientList() {
		StringBuffer list = new StringBuffer();
		int count = 0;
		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
			ServerClient client = (ServerClient) iterator.next();
			list.append("|" + client.GetId());
			list.append("|" + client.GetNick());
			count++;
		}
		list.append("|");
		list.insert(0, count);
		return list.toString();
	}

	/**
	 * �� Ŭ���̾�Ʈ���� ä�� ����Ʈ ����
	 */
	public synchronized void chatListReflash() {
		// TODO Auto-generated method stub		
		gui.chatListSet(chats);

		//�� Ŭ���̾�Ʈ���� ä�� ����Ʈ ����
		String t = getChatList();

		//		serverLog("chatListReflash:" + t);

		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
			ServerClient client = (ServerClient) iterator.next();
			client.out((String) p.col�����(1, 6, 0) + t);
			//			client.out((String) p.col�����(1, 6, 0) + getChatList());
		}
	}

	/**
	 * @return ä�ù� ����Ʈ ���ڿ� ����
	 */
	public synchronized String getChatList() {
		StringBuffer list = new StringBuffer();
		int count = 0;
		for (String key : chats.keySet()) {
			//		for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
			//			ServerChat chat = (ServerChat) iterator.next();
			list.append("|" + key);
			list.append("|" + chats.get(key).GetRoomName());
			list.append("|" + chats.get(key).GetBossName());
			count++;
		}
		list.append("|");
		list.insert(0, count);
		return list.toString();
	}

	//===================== get set =======================

	public Socket getSocket() {
		return socket;
	}

	public synchronized List<ServerClient> getClients() {
		return clients;
	}

	public synchronized Map<String, ServerChat> getChats() {
		return chats;
	}
	
	public void close(){
		o.close();
	}

	public static void main(String[] args) {
		new Server();
	}
}
