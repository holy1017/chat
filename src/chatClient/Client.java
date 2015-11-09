package chatClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import chatSever.ServerChat;
import common.ChatGui;
import common.Password;
import common.Protocol;
import common.StyledDoc;
import common.userJFrame;

public class Client {
	Socket socket = null;

	ObjectInputStream	in	= null;
	ObjectOutputStream	out	= null;

	String	nick	= null;
	String	id		= null;
	String	ip		= null;
	int		port;

	private ClientGui	gui	= null;
	private Protocol	p	= new Protocol();
	

	private Map<String, ClientChat>		chats	= new HashMap<>();	//���� ������ ���
	private List<ClientUserCtrateGui>	users	= new ArrayList<>();//id,nick

	public Client() {
		// TODO Auto-generated constructor stub
		setting("localhost", 7777);
	}

	public Client(String ip) {
		// TODO Auto-generated constructor stub
		setting(ip, 7777);
	}

	public Client(int port) {
		// TODO Auto-generated constructor stub
		setting("localhost", port);
	}

	public Client(String ip, int port) {
		// TODO Auto-generated constructor stub
		setting(ip, port);
	}

	private void setting(String ip, int port) {
		this.ip = ip;
		this.port = port;
		setDisplay();
		ClientChat.clientSet(this);
		if (connect(ip, port) != -1) {//���� �õ�			
			login();//�α��� �õ�
		}
	}

	private void setDisplay() {
		// TODO Auto-generated method stub

//		try {
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
//			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}

		gui = new ClientGui(this);

		gui.setVisible(true);
	}

	/**������ ����
	 * @param ip
	 * @param port
	 * @return
	 */
	private int connect(String ip, int port) {
		try {
			log("Socket:" + ip + ":" + port,"blue");
			socket = new Socket(ip, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			log("sever connect ok","green");
			//������� �׻� �ޱ�
			new Thread(new ClientIn(true, this, in)).start();//��� ���� ������ ����
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
//			System.out.println("socket errer");
			log("socket errer","red");
			return -1;
		}
	}

	/**
	 * @param obj
	 */
	void in(String obj) {
		//String t=(String) obj;
		p.col�޼�������(obj);
		log(obj + ":hex:" + p.colGetHex());
		log("����:" + p.col������(1) + ":����:" + p.col������(2) + ":����:" + p.col������(3) ,"blue");

		switch (p.col������(1)) {
		case 1://����
			switch (p.col������(2)) {
			case 1://ȸ������

				break;
			case 2://�α���
				loginOk(obj);
				break;
			case 3://�г��Ӻ���
				break;
			case 4://ä�ù����
				break;
			case 5://ä�ù�����
				if (p.col������(3) == 1) {
					p.col�޼�������(obj, 3);
					roomJionOk(p.colGetIndex(1), p.colGetIndex(2));
				}
				break;
			case 6://ä�ù渮��Ʈ
				roomList(p.colGetIndex(1));
				break;
			case 16://���� ������ ����Ʈ
				userList(p.colGetIndex(1));
				break;
			default:
				System.out.println("P����_����1");
			}
			break;
		case 2://ä��
			switch (p.col������(2)) {
			case 6://ä�ó���
				if (p.col������(3) == 0) {
					p.col�޼�������(obj, 2);
					roomMsgMe(p.colGetIndex(1), p.colGetIndex(2));
				}
				break;
			case 7://�����û
				break;
			case 8://�Ӹ�
				break;
			case 9://������
				p.col�޼�������(obj, 2);
				if (p.col������(3) == 1) {//���Ӽ�����
					roomOutOk(p.colGetIndex(1));
				} else {
					log("p.col������(3):" + p.col������(3));
				}
				break;
			case 10://�� ����
				break;
			case 16://ä�ù� ������ ����Ʈ
				roomUserList(p.colGetIndex(1));
				break;
			default:
				System.out.println("P����_����2");
			}
			break;
		default:
			System.out.println("P����_����3");
		}
	}

	void out(Object obj) {
		try {
			log("log:" + obj);
			out.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void login() {
		do {
			id = JOptionPane.showInputDialog("id:");
			//			System.out.println(id);

		} while (!(id == null || id.length() >= 4));
		if (id != null) {
			//			System.out.println(id.length());
			String pw;
			do {
				pw = JOptionPane.showInputDialog("pw:");
			} while (!(pw == null || pw.length() >= 4));
			if (pw != null) {
				pw = new Password().SHA256(pw);
				//				out(p.col�����(1, 1, 0) + id + "|" + pw);
				out(p.col�����(1, 2, 0) + id + "|" + pw);
				//				out("100#" + id + "|" + pw);
			}
		}
	}

	/**���� �α��ν� ���� ó��
	 * @param obj
	 */
	private void loginOk(String obj) {
		// TODO Auto-generated method stub
		switch (p.col������(3)) {
		case Protocol.P����_����:
			break;
		case Protocol.P����_����:
			break;
		default:
			break;
		}
		//		ArrayList<String> ar = p.col�޼�������(obj, 2);

	}

	void userCreate() {
		// TODO Auto-generated method stub
		//		String id;
		//		do {
		//			id = JOptionPane.showInputDialog("id 4�ڸ�:");
		//			if (id == null) {
		//				return;
		//			}
		//		} while (id.length() < 4);
		//
		//		Password t = new Password();
		//		String pw1;
		//		String pw2;
		//		do {
		//			pw1 = t.SHA256(JOptionPane.showInputDialog("��ȣ:"));
		//			pw2 = t.SHA256(JOptionPane.showInputDialog("��ȣ ���Է�:"));
		//		} while (!pw1.equals(pw2));
		//
		//		String nick = JOptionPane.showInputDialog("�г���:");
		System.out.println("sdf");
		userJFrame user = new ClientUserCtrateGui(this);
		user.setVisible(true);

	}

	private void userList(String str) {
		// TODO Auto-generated method stub
		log("userList:" + str);

		String[][] list = userListSplit(str, 2);

		//gui�� ������
		gui.userListSet(list);
	}

	String[][] userListSplit(String str, int ������) {
		//����Ʈ ���� ����
		Protocol p = new Protocol(str);
		int ct = Integer.parseInt(p.colGetIndex(0));
		//		log("ct:" + ct);

		//���� ���̸� ���� 
		//		log("���� ���̸�:" + p.colGetIndex(1));
		p = new Protocol(p.colGetIndex(1), ct * ������);
		//		log("colSize:" + p.colSize());

		int idx = 0;
		String[][] list = new String[ct][������];

		for (int i = 0; i < ct; i++) {
			String[] strings = list[i];

			for (int j = 0; j < ������; j++) {

				strings[j] = p.colGetIndex(idx++);
			}
			//			log("strings:" + strings[0]+":"+ strings[1]+":");
		}
		return list;
	}

	void userNickChange() {
		String nick = JOptionPane.showInputDialog("nick:");
		;
		out(p.col�����(1, 3) + nick + "|�г��� �����û");
	}

	//===============ä�ù� ���� =======================

	private void roomList(String str) {
		// TODO Auto-generated method stub

		//		log("chatList:" + str);

		//����Ʈ ���� ����
		String[][] list = userListSplit(str, 3);

		//gui�� ������
		gui.chatListSet(list);
	}

	/** ������ ä�� �޼��� ����
	 * @param key
	 * @param text
	 */
	void roomMsgSend(String key, String text) {// TODO Auto-generated method stub
		out(p.col�����(2, 6, 0) + key + "|" + text);
	}

	/**ä�ó����� ����
	 * @param key
	 * @param msg
	 */
	private void roomMsgMe(String key, String msg) {
		// TODO Auto-generated method stub
		chats.get(key).msgAdd(msg ,"blue");
	}

	void roomJoin(String key) {
		// TODO Auto-generated method stub
		out(p.col�����(1, 5, 0) + key + "|�� ���� ��û");
	}

	private void roomJionOk(String key, String name) {
		// TODO Auto-generated method stub
		//		log("chatJion"+obj);
		//		String key = p.colGetIndex(1);
		//		log("key:"+key);
		//		
		////		log()
		ClientChat chat;
		if ((chat = chats.get(key)) == null) {//�����
			//			gui.getRoomName(key);
			chat = new ClientChat(key, name);
			new Thread(chat).start();
			chats.put(key, chat);
		} else {//������ ����
			//			chat
		}
	}

	private void roomUserList(String text) {
		// TODO Auto-generated method stub
		p.col�޼�������(text);

		log("chatRoomUserList:" + p.colGetIndex(0));
		log("chatRoomUserList:" + p.colGetIndex(1));

		String[][] list = userListSplit(p.colGetIndex(1), 2);
		chats.get(p.colGetIndex(0)).clientListSet(list);
		;

	}

	/**������ �����û
	 * @param key
	 * @param id
	 */
	void roomkick(String key, String id) {
		out(p.col�����(2, 7, 0) + key + "|" + id + "|���� ��û");
	}

	/** ä�ù� ������ ��û�� �������� ó��. �����ϸ� roomOutOk���� ó���ϱ�
	 * @param key
	 */
	void roomOut(String key) {
		// TODO Auto-generated method stub		
		out(p.col�����(2, 9, 0) + key + "|�泪�����û");
	}

	private void roomOutOk(String key) {
		// TODO Auto-generated method stub
		chats.remove(key);
	}

	void roomCreate() {
		// TODO Auto-generated method stub
		String name = JOptionPane.showInputDialog("���̸�:");
		if (name != null || name.length() >= 1) {
			out(p.col�����(1, 4, 0) + name + "|�� ������û");
		}
	}

	void log(Object obj,String style) {
		gui.logAdd((String) obj,style);
	}
	void log(Object obj) {
		gui.logAdd((String) obj);
	}

	//	void guiMsg(String msg) {
	//		// TODO Auto-generated method stub
	//		gui.logAdd(msg);
	//		//		System.out.println(msg);
	//	}

	public static void main(String[] args) {
		new Client("localhost");
//		new Client("192.168.0.42");
		//		new Client("192.168.0.2",3000);
	}

}
