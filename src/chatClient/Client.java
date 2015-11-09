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
	

	private Map<String, ClientChat>		chats	= new HashMap<>();	//현재 접속한 방들
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
		if (connect(ip, port) != -1) {//접속 시도			
			login();//로그인 시도
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

	/**서버로 접속
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
			//스레드로 항상 받기
			new Thread(new ClientIn(true, this, in)).start();//듣기 전용 스레드 생성
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
		p.col메세지분할(obj);
		log(obj + ":hex:" + p.colGetHex());
		log("상위:" + p.col값추출(1) + ":하위:" + p.col값추출(2) + ":판정:" + p.col값추출(3) ,"blue");

		switch (p.col값추출(1)) {
		case 1://서버
			switch (p.col값추출(2)) {
			case 1://회원가입

				break;
			case 2://로그인
				loginOk(obj);
				break;
			case 3://닉네임변경
				break;
			case 4://채팅방생성
				break;
			case 5://채팅방접속
				if (p.col값추출(3) == 1) {
					p.col메세지분할(obj, 3);
					roomJionOk(p.colGetIndex(1), p.colGetIndex(2));
				}
				break;
			case 6://채팅방리스트
				roomList(p.colGetIndex(1));
				break;
			case 16://서버 접속자 리스트
				userList(p.colGetIndex(1));
				break;
			default:
				System.out.println("P상태_없음1");
			}
			break;
		case 2://채팅
			switch (p.col값추출(2)) {
			case 6://채팅내용
				if (p.col값추출(3) == 0) {
					p.col메세지분할(obj, 2);
					roomMsgMe(p.colGetIndex(1), p.colGetIndex(2));
				}
				break;
			case 7://강퇴요청
				break;
			case 8://귓말
				break;
			case 9://나가기
				p.col메세지분할(obj, 2);
				if (p.col값추출(3) == 1) {//접속성공시
					roomOutOk(p.colGetIndex(1));
				} else {
					log("p.col값추출(3):" + p.col값추출(3));
				}
				break;
			case 10://방 종료
				break;
			case 16://채팅방 접속자 리스트
				roomUserList(p.colGetIndex(1));
				break;
			default:
				System.out.println("P상태_없음2");
			}
			break;
		default:
			System.out.println("P상태_없음3");
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
				//				out(p.col만들기(1, 1, 0) + id + "|" + pw);
				out(p.col만들기(1, 2, 0) + id + "|" + pw);
				//				out("100#" + id + "|" + pw);
			}
		}
	}

	/**서버 로그인시 반응 처리
	 * @param obj
	 */
	private void loginOk(String obj) {
		// TODO Auto-generated method stub
		switch (p.col값추출(3)) {
		case Protocol.P상태_성공:
			break;
		case Protocol.P상태_실패:
			break;
		default:
			break;
		}
		//		ArrayList<String> ar = p.col메세지분할(obj, 2);

	}

	void userCreate() {
		// TODO Auto-generated method stub
		//		String id;
		//		do {
		//			id = JOptionPane.showInputDialog("id 4자리:");
		//			if (id == null) {
		//				return;
		//			}
		//		} while (id.length() < 4);
		//
		//		Password t = new Password();
		//		String pw1;
		//		String pw2;
		//		do {
		//			pw1 = t.SHA256(JOptionPane.showInputDialog("암호:"));
		//			pw2 = t.SHA256(JOptionPane.showInputDialog("암호 재입력:"));
		//		} while (!pw1.equals(pw2));
		//
		//		String nick = JOptionPane.showInputDialog("닉네임:");
		System.out.println("sdf");
		userJFrame user = new ClientUserCtrateGui(this);
		user.setVisible(true);

	}

	private void userList(String str) {
		// TODO Auto-generated method stub
		log("userList:" + str);

		String[][] list = userListSplit(str, 2);

		//gui에 보내기
		gui.userListSet(list);
	}

	String[][] userListSplit(String str, int 열갯수) {
		//리스트 갯수 추출
		Protocol p = new Protocol(str);
		int ct = Integer.parseInt(p.colGetIndex(0));
		//		log("ct:" + ct);

		//방장 방이름 분할 
		//		log("방장 방이름:" + p.colGetIndex(1));
		p = new Protocol(p.colGetIndex(1), ct * 열갯수);
		//		log("colSize:" + p.colSize());

		int idx = 0;
		String[][] list = new String[ct][열갯수];

		for (int i = 0; i < ct; i++) {
			String[] strings = list[i];

			for (int j = 0; j < 열갯수; j++) {

				strings[j] = p.colGetIndex(idx++);
			}
			//			log("strings:" + strings[0]+":"+ strings[1]+":");
		}
		return list;
	}

	void userNickChange() {
		String nick = JOptionPane.showInputDialog("nick:");
		;
		out(p.col만들기(1, 3) + nick + "|닉네임 변경요청");
	}

	//===============채팅방 관련 =======================

	private void roomList(String str) {
		// TODO Auto-generated method stub

		//		log("chatList:" + str);

		//리스트 갯수 추출
		String[][] list = userListSplit(str, 3);

		//gui에 보내기
		gui.chatListSet(list);
	}

	/** 서버로 채팅 메세지 날림
	 * @param key
	 * @param text
	 */
	void roomMsgSend(String key, String text) {// TODO Auto-generated method stub
		out(p.col만들기(2, 6, 0) + key + "|" + text);
	}

	/**채팅내용을 받음
	 * @param key
	 * @param msg
	 */
	private void roomMsgMe(String key, String msg) {
		// TODO Auto-generated method stub
		chats.get(key).msgAdd(msg ,"blue");
	}

	void roomJoin(String key) {
		// TODO Auto-generated method stub
		out(p.col만들기(1, 5, 0) + key + "|방 들어가기 요청");
	}

	private void roomJionOk(String key, String name) {
		// TODO Auto-generated method stub
		//		log("chatJion"+obj);
		//		String key = p.colGetIndex(1);
		//		log("key:"+key);
		//		
		////		log()
		ClientChat chat;
		if ((chat = chats.get(key)) == null) {//방생성
			//			gui.getRoomName(key);
			chat = new ClientChat(key, name);
			new Thread(chat).start();
			chats.put(key, chat);
		} else {//방정보 수정
			//			chat
		}
	}

	private void roomUserList(String text) {
		// TODO Auto-generated method stub
		p.col메세지분할(text);

		log("chatRoomUserList:" + p.colGetIndex(0));
		log("chatRoomUserList:" + p.colGetIndex(1));

		String[][] list = userListSplit(p.colGetIndex(1), 2);
		chats.get(p.colGetIndex(0)).clientListSet(list);
		;

	}

	/**서버로 강퇴요청
	 * @param key
	 * @param id
	 */
	void roomkick(String key, String id) {
		out(p.col만들기(2, 7, 0) + key + "|" + id + "|강퇴 요청");
	}

	/** 채팅방 나가기 요청후 서버에서 처리. 성공하면 roomOutOk에서 처리하기
	 * @param key
	 */
	void roomOut(String key) {
		// TODO Auto-generated method stub		
		out(p.col만들기(2, 9, 0) + key + "|방나가기요청");
	}

	private void roomOutOk(String key) {
		// TODO Auto-generated method stub
		chats.remove(key);
	}

	void roomCreate() {
		// TODO Auto-generated method stub
		String name = JOptionPane.showInputDialog("방이름:");
		if (name != null || name.length() >= 1) {
			out(p.col만들기(1, 4, 0) + name + "|방 생성요청");
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
