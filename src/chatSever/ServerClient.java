package chatSever;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.*;

import common.*;

public class ServerClient implements Runnable {

	protected static Server	server	= null;
	private Socket			socket	= null;

	private ObjectInputStream	in	= null;
	private ObjectOutputStream	out	= null;

	protected String	id		= "#";	//��ũ������ ������ �� �ڸ��Ⱑ �ȵ�.
	protected String	nick	= "#";	//��ũ������ ������ �� �ڸ��Ⱑ �ȵ�.

	private List<ServerClient>	clients	= null;				//���� �����ڵ�
	private List<ServerChat>	chats	= new ArrayList<>();//���� ������ ���
	//	private Map<String, ServerChat>	chats	= new HashMap();
	//	private Map<String, ServerClient>	clients	= null;	//���� �����ڵ�
	//	private Map<String, ServerChat>		chats	= null;	//���� ������ ���

	private boolean	flag	= false;
	private boolean	login	= false;

	private Protocol p = new Protocol();

	public static void serverSet(Server server) {
		ServerClient.server = server;
	}

	/**
	 * ���� ����. �Ϲ� Ŭ�󿡼��� ���� ����
	 */
	public ServerClient(Server server, String bossName) {
		this.server = server;
		id = bossName;
		nick = bossName;
	}

	/** Ŭ�� ����� ���1
	 * @param server
	 */
	public ServerClient(Server server) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.socket = server.getSocket();
		this.clients = server.getClients();
		setting();
	}

	/**Ŭ�� ����� ���2
	 * @param server
	 * @param socket
	 */
	public ServerClient(Server server, Socket socket) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.socket = socket;
		this.clients = server.getClients();
		setting();
	}

	/**
	 * ������ ���� ���� �κ�
	 */
	private void setting() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			flag = true;
			log("getStream ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log("getStream errer");
		}
	}

	public String getAddress() {
		return "" + socket.getInetAddress();
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run() */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//		chats = new Vector<ServerChat>();
		while (flag) {//���� �����Ͻ�
			try {//�α��θ� �ޱ�
				inLogin((String) in.readObject());//Ŭ���̾�Ʈ�κ��� ���� �ޱ�
				while (login) {//�α��Ή������
					in((String) in.readObject());//Ŭ���̾�Ʈ�κ��� ���� �ޱ�					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				//				e.printStackTrace();
				log("readObject ClassNotFoundException errer");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//				e.printStackTrace();
				log("readObject IOException errer");
				userLogout();
				flag = false;
				server.removeClient(this);

			}
		}
	}

	//================ ��Ʈ�� ó�� ====================

	/** ����â������ ó��
	 * @param obj
	 * @return
	 */
	public int inLogin(Object obj) {

		if (!login) {//��α��λ����ϰ��

			String msg = (String) obj;

//			log("login" + msg);
			p.col�޼�������(msg);//�������ݰ� ���� �и�
			log(msg + ":hex:" + p.colGetHex(),"magenta");
			log("����:" + p.col������(1) + ":����:" + p.col������(2) + ":����:" + p.col������(3) ,"blue");

			//			System.out.println(p.col����(1));
			//			System.out.println(Protocol.P����_����);

			switch (p.col������(1)) {//�������� ä������ ����
			case 1://����

				//				System.out.println(p.col����(2));
				//				System.out.println(Protocol.P����_�α���);

				switch (p.col������(2)) {//���� ����
				case 1://ȸ������
					p.col�޼�������(msg, 4);
					userCreate(p.colGetIndex(1), p.colGetIndex(2), p.colGetIndex(3));
					return 0;
				case 2://�α���
					if (p.col�޼�������(msg, 2).size() == 3) {// �μ� ������ �´��� �˻�
						userLogin(p.colGetIndex(1), p.colGetIndex(2));
						return 0;
					} else {
						out(p.col�����(1, 2, 13) + "�μ� ���� ����!");
						return -1;
					}
				default:
					log(p.col�����(1, 2, 12) + "�α��� ���ּ���. ���� �������� ���� ���������Դϴ�1");
					out(p.col�����(1, 2, 12) + "�α��� ���ּ���. ���� �������� ����  ���������Դϴ�2");
					return -1;

				}
				//			case 2://ä��
				//				switch (p.col������(2)) {//���� ��������
				//				case 6://ä�ó�������
				//					p.col�޼�������(msg, 2);
				//					log("ServerClient_in2:" + p.colGetIndex(1));
				//					log("ServerClient_in3:" + p.colGetIndex(2));
				//					chatMsgToRoom(p.colGetIndex(1), p.colGetIndex(2));
				//					return 0;
				//				case 7://�����û
				//					p.col�޼�������(msg, 3);
				//					chatRomeKick(p.colGetIndex(1), p.colGetIndex(2));
				//					return 0;
				//				case 8://�Ӹ�
				//					p.col�޼�������(msg, 3);
				//					chatMsgToClient(p.colGetIndex(1), p.colGetIndex(2), p.colGetIndex(3));
				//					return 0;
				//				case 9://������
				//					p.col�޼�������(msg, 2);
				//					chatRoomOut(p.colGetIndex(1));
				//					return 0;
				//				default:
				//					out((p.colGet() | p.P����_����) + "|���� �������� ä�� ���������Դϴ�");
				//					return 0;
				//				}
			default:
				log(p.col�����(1, 2, 12) + "�α��� ���ּ���. ���� �������� ���� ���������Դϴ�3");
				out(p.col�����(1, 2, 12) + "�α��� ���ּ���. ���� �������� ���� ���������Դϴ�4");
				return -1;
			}
		} else {
			out((p.colGet() | p.P����_����) + "|�α׾ƿ��� ���ּ���");
			return -1;
		}
	}

	/**���� �޼������� �������� ó��. �α��� �Ŀ��� �۵�. ����� �� �޼ҵ忡�� ó��
	 * @param obj
	 * @return
	 */
	private int in(Object obj) {
		// TODO Auto-generated method stub
		String msg = (String) obj;
		p.col�޼�������(msg);//�������ݰ� ���� �и�
		log(obj + ":hex:" + p.colGetHex());
		log("����:" + p.col������(1) + ":����:" + p.col������(2) + ":����:" + p.col������(3) ,"blue");
//		log("ServerClient_in1:" + msg + "|p.col������(1):" + p.col������(1) + "|p.col������(2):" + p.col������(2));
		//		logServer("ServerClient_in:" + p.col������(1));
		//		logServer("ServerClient_in:" + p.col������(2));
		try {
			switch (p.col������(1)) {//���� ��������
			case 1://����
				switch (p.col������(2)) {//���� ��������
				case 3://�г��Ӻ���
					p.col�޼�������(msg, 2);
					userNickChange(p.colGetIndex(1));
					return 0;
				case 4://ä�ù����
					p.col�޼�������(msg, 2);
					chatRoomCreate(p.colGetIndex(1));
					return 0;
				case 5://ä�ù�����
					p.col�޼�������(msg, 2);
					chatRoomJoin(p.colGetIndex(1));
					return 0;
				default:
					out((p.colGet() | p.P����_����) + "|���� �������� ���� ���������Դϴ�");
					return 0;
				}

			case 2://ä��

				switch (p.col������(2)) {//���� ��������
				case 6://ä�ó�������
					p.col�޼�������(msg, 2);
					log("ServerClient_in2:" + p.colGetIndex(1));
					log("ServerClient_in3:" + p.colGetIndex(2));
					chatMsgToRoom(p.colGetIndex(1), p.colGetIndex(2));
					return 0;
				case 7://�����û
					p.col�޼�������(msg, 3);
					chatRomeKick(p.colGetIndex(1), p.colGetIndex(2));
					return 0;
				case 8://�Ӹ�
					p.col�޼�������(msg, 3);
					chatMsgToClient(p.colGetIndex(1), p.colGetIndex(2), p.colGetIndex(3));
					return 0;
				case 9://������
					p.col�޼�������(msg, 2);
					chatRoomOut(p.colGetIndex(1));
					return 0;
				default:
					out((p.colGet() | p.P����_����) + "|���� �������� ä�� ���������Դϴ�");
					return 0;
				}

			default:
				out((p.colGet() | p.P����_����) + "|���� �������� ���������Դϴ�");
				return 0;
			}
		} finally {

		}
	}

	/** Ŭ���̾�Ʈ�� �޼��� ������
	 * @param obj
	 * @return
	 */
	int out(Object obj) {
		// TODO Auto-generated method stub
		try {
			out.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			log("writeObject IOException errer");
		}
		return 0;
	}

	//===================== ȸ�� ���� �޼ҵ� =====================
	/** ȸ������, ���Խ� �˻��ϱ�
	 * @param id
	 * @param pw
	 * @return
	 */
	private int userCreate(String id, String pw, String Nick) {
		// TODO Auto-generated method stub

		if (idChack(id)) {
			switch (server.userCreate(id, pw, Nick)) {
			case 1:
				out(p.col�����(1, 1, 1) + "ȸ������ ����");
				break;
			case 0:
				out(p.col�����(1, 1, 15) + "ȸ������ ����");
				break;
			case -3:
				out(p.col�����(1, 1, 14) + "ȸ������ ����:�Ƶ��ߺ�");
				break;
			case -5:
				out(p.col�����(1, 1, 14) + "ȸ������ ����:�� �ߺ�");
				break;
//			case -1:
//				out(p.col�����(1, 1, 13) + "ȸ������ ����");
//				break;
			default:
				out(p.col�����(1, 1, 1) + "ȸ������ ����");
				break;
			}
			//			server.userCreate(id, pw, Nick);
			//			out(p.col�����(1, 1, 1) + "ȸ������ ����");
		} else {
			out(p.col�����(1, 1, 13) + "ȸ������ ���̵� ���Խ� ����");
		}
		return 0;
	}

	/**������ ȸ���α��� ��û, ���Խ� �˻��ϱ�
	 * @param id
	 * @param pw
	 * @return
	 */
	private void userLogin(String id, String pw) {
		// TODO Auto-generated method stub
		if (idChack(id)) {
			switch (server.userLogin(id, pw)) {
			case 1:
				login = true;
				this.id = id;
				this.nick = server.userNick(id);
				out(p.col�����(1, 2, 1) + id + "|" + nick + "|�α��� ����");
				out(p.col�����(1, 6, 0) + server.getChatList() + "ä�ù� ����Ʈ");//ä�ù� ����Ʈ �ޱ�
				//				out(p.col�����(1, 16, 0) + server.getClientList()+"���� ������ ����Ʈ");//ä�ù� ����Ʈ �ޱ�
				server.clientListReflash();//�α��� ������ ��� Ŭ���̾�Ʈ���� �˸�
				break;
			case 0:
				out(p.col�����(1, 2, 15) + "�α��� ����");
				break;
			default:
				out(p.col�����(1, 2, 13) + "�α��� ����");
				break;
			}
		} else {
			out(p.col�����(1, 2, 13) + "�α��� ���̵� ���Խ� ����");
		}
	}

	private void userLogout() {
		//		Set k=chats.keySet();
		//		for (Object o : k) {
		//			chats.get(o).chatRoomOut(this);
		//		}
		for (ServerChat chat : chats) {

			//		}
			//		for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
			//			ServerChat chat = (ServerChat) iterator.next();
			chat.chatRoomOut(this);
		}
		this.id = "#";
		this.nick = "#";
		server.clientListReflash();//�α��� ������ ��� Ŭ���̾�Ʈ���� �˸�
	}

	/**�г��� ����, ���Խ� �˻��ϱ�
	 * @param nick
	 * @return
	 */
	private int userNickChange(String nick) {
		// TODO Auto-generated method stub
		String t = server.userNickChange(this, nick);
		if (t != null) {
			//			gui.clientListSet(clients);
			this.nick = nick;
			out(p.col�����(1, 3, 1) + "|����");
			server.clientListReflash();//�α��� ������ ��� Ŭ���̾�Ʈ���� �˸�		
		} else {
			out(p.col�����(1, 3, 15) + "|����");
		}
		return 0;
	}

	/**�г��� ����, ���Խ� �˻��ϱ�
	 * @param nick
	 * @return
	 */
	int userNickChangeOk(String nick) {
		// TODO Auto-generated method stub
		this.nick = nick;
		//		server.userNickChange(this, nick);
		return 0;
	}

	/** ���̵� ���Խ� �˻�
	 * @param id
	 * @return
	 */
	public boolean idChack(String id) {
		// TODO Auto-generated method stub
		StringChack sc = new StringChack();
		return sc.sc_idChack(id);
	}

	//===================== ä�� ���� ���� ���� �޼ҵ� =====================
	/**ä�ù� ����
	 * @param roomName ���̸�
	 * @return
	 */
	public int chatRoomCreate(String roomName) {
		// TODO Auto-generated method stub
		ServerChat chat = server.chatRoomCreate(this, roomName);
		if (chat != null) {
			chats.add(chat);
		} else {

		}
		//		chats.add(chat);
		return 0;
	}

	/** �ʿ� ���� �޼ҵ�?
	 * @param chat
	 * @return
	 */
	public int chatRoomCreateOk(ServerChat chat) {
		// TODO Auto-generated method stub
		//		ServerChat chat= server.chatRoomCreate(this, roomName);
		//		chats.put(chat.key,chat);
		chats.add(chat);
		return 0;
	}

	/**ä�ù� ����
	 * @param chatnum ���ȣ
	 * @return
	 */
	public int chatRoomJoin(String roomKey) {
		// TODO Auto-generated method stub
		//		server.chatRoomJoin(this, roomKey);
		ServerChat chat = server.chatRoomJoin(this, roomKey);
		log("chatRoomJoin2:" + chat.key);
		if (chat != null) {
			chat.chatRoomJoin(this);
			chats.add(chat);
		}
		//		chats.put(chat.key,chat);
		//		chats.add();
		return 0;
	}
	//
	//	public int chatRoomJoinOk(ServerChat chat) {
	//		// TODO Auto-generated method stub
	//		//		server.chatRoomJoin(this, roomKey)
	//		chats.add(chat);
	//		return 0;
	//	}

	/** �� ������
	 * @param roomNum
	 * @return
	 */
	public int chatRoomOut(String roomKey) {
		// TODO Auto-generated method stub
		//		chats.get(roomKey).chatRoomOut(this);
		for (ServerChat chat : chats) {

			//		}
			//		for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
			//			ServerChat chat = (ServerChat) iterator.next();
			if (chat.key.equals(roomKey)) {
				chat.chatRoomOut(this);//�ش� ä�ù濡 ������ ó��
				chats.remove(chat);
				out(p.col�����(2, 9, 1) + roomKey + "|�泪���� ����:" + chats.size());
				return 0;
			}
		}
		return 0;
	}

	/**�����û
	 * @param roomNum
	 * @param id
	 * @return
	 */
	public int chatRomeKick(String roomKey, String id) {
		for (ServerChat chat : chats) {
			log("chatMsgToRoom:" + chat.key);
			if (chat.key.equals(roomKey)) {//������ ���Ͻ�
				//				log("chatMsgToRoom:" + chat);
				//				log("chatMsgToRoom:" + nick);
				//				log("chatMsgToRoom:" + msg);
				System.out.println("chat.boss.id:" + chat.boss);
				System.out.println("id:" + id);
				if (chat.boss.equals(id)) {//�������� �����ΰ��
					System.out.println("chat.boss.id==id");
					out(p.col�����(2, 7, 15) + "�ڱ��ڽ��� ���� �Ұ���");
				} else {
					System.out.println(chat.boss + ":" + this.id);
					if (chat.boss.equals(this.id)) {//�����û�ڰ� �����ΰ��						
						if (chat.chatRoomKick(id) == 0) {
							out(p.col�����(2, 7, 2) + id + "|���𼺰�");
						} else {
							out(p.col�����(2, 7, 15) + id + "|�������");
						}
					} else {
						chat.chatRoomKickVote(id);
					}
				}
				return 0;
			}
		}
		out(p.col�����(2, 6, 12) + "�������� �������Դϴ�");
		//�������� �������Դϴ�

		//		for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
		//			chat = (ServerChat) iterator.next();
		//			log("chatMsgToRoom:" + chat.key);
		//			if(chat.key.equals(roomKey)){
		//				return 0;
		//			}			
		//		}
		//		if(chat!=null){
		//		log("chatMsgToRoom:" + chat);
		//		log("chatMsgToRoom:" + nick);
		//		log("chatMsgToRoom:" + msg);
		//		chat.chatMsgSendAll(nick + "#" + msg);
		//		}
		return 0;
	}

	public int chatRoomkickMe(ServerChat chat) {
		// TODO Auto-generated method stub
		//		chats.get(roomKey).chatRoomOut(this);
		out(p.col�����(2, 9, 1) + chat.key + "|�����:" + chats.size());
		chats.remove(chat);
		//		for (ServerChat chat : chats) {
		//
		//			//		}
		//			//		for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
		//			//			ServerChat chat = (ServerChat) iterator.next();
		//			if (chat.key.equals(roomKey)) {
		//				//				chat.chatRoomOut(this);//�ش� ä�ù濡 ������ ó��
		//				chats.remove(chat);
		//				out(p.col�����(2, 9, 1) + roomKey + "|�����:" + chats.size());
		//				return 0;
		//			}
		//		}
		return 0;
	}

	public int chatRoomDelete(ServerChat chat) {
		// TODO Auto-generated method stub
		chats.remove(chat);
		out((p.col�����(2, 10, 1)) + "|������!");
		return 0;
	}

	public int chatRoomDeleteOk(ServerChat chat) {
		// TODO Auto-generated method stub
		chats.remove(chat);
		out((p.col�����(2, 10, 1)) + "|������!");
		return 0;
	}

	/** ä�ù濡 �޼��� ������
	 * @param roomNum
	 * @param msg
	 * @return
	 */
	public int chatMsgToRoom(String roomKey, String msg) {
		// TODO Auto-generated method stub
		//		ServerChat chat = null ;
		//		ServerChat chat = chats.get(Integer.parseInt(roomNum));
		//		ServerChat chat = chats.get(roomNum);

		for (ServerChat chat : chats) {
			log("chatMsgToRoom:" + chat.key);
			if (chat.key.equals(roomKey)) {//������ ���Ͻ�
				//				log("chatMsgToRoom:" + chat);
				//				log("chatMsgToRoom:" + nick);
				//				log("chatMsgToRoom:" + msg);
				chat.chatMsgSendAll(nick + "#" + msg);
				return 0;
			}
		}
		out(p.col�����(2, 6, 12) + "�������� �������Դϴ�");
		//�������� �������Դϴ�

		//		for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
		//			chat = (ServerChat) iterator.next();
		//			log("chatMsgToRoom:" + chat.key);
		//			if(chat.key.equals(roomKey)){
		//				return 0;
		//			}			
		//		}
		//		if(chat!=null){
		//		log("chatMsgToRoom:" + chat);
		//		log("chatMsgToRoom:" + nick);
		//		log("chatMsgToRoom:" + msg);
		//		chat.chatMsgSendAll(nick + "#" + msg);
		//		}
		return 0;
	}

	/** ä�ù��� Ư���ο��� �� ������
	 * @param roomNum
	 * @param msg
	 * @param id
	 * @return
	 */
	public int chatMsgToClient(String roomNum, String id, String msg) {
		// TODO Auto-generated method stub
		ServerChat chat = chats.get(Integer.parseInt(roomNum));
		chat.chatMsgSendClient(id, msg);
		return 0;
	}

	/** ������ �α׸� ����
	 * @param log
	 * @return
	 */
	public int log(String log) {
		// TODO Auto-generated method stub		
		return server.serverLog(getAddress() + ":" + log);
	}
	public int log(String log,String style) {
		// TODO Auto-generated method stub		
		return server.serverLog(getAddress() + ":" + log,style);
	}

	public String GetId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String GetNick() {
		// TODO Auto-generated method stub
		return nick;
	}

	public static void main(String[] args) {
		new Server();
	}

}
