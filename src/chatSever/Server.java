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

	private List<ServerClient>		clients	= new ArrayList<>();// 클라이언트에서 접속 받을시 여기에 넣기
	private Map<String, ServerChat>	chats	= new HashMap();	// 채팅방 생성시 여기에 넣기
	//	private Map<String, ServerClient>	clients	= new HashMap<>();	//서버 접속자들
	//	private Map<String, ServerChat>		chats	= new HashMap<>();		//현재 접속한 방들

	String mainRoomKey = null;
	String bossName="#boss";

	private ServerGui gui = null;

	private boolean flag = false;

	private Protocol	p	= new Protocol();
	private ServerOrcl	o	= new ServerOrcl("localhost:1521/orcl", "chat", "chat");

	private int roomNum = 0;

	// 생성자 부분
	public Server() {
		// TODO Auto-generated constructor stub		
		setting(7777);
	}

	public Server(int port) {
		// TODO Auto-generated constructor stub		
		setting(port);
	}

	//===================== 서버 세팅 관련 =======================

	public int setting(int port) {
		// TODO Auto-generated method stub
		setDisplay();

		portOpen(port);//소캣 열기

		//동기화
		Collections.synchronizedList(clients);
		Collections.synchronizedMap(chats);

		ServerChat.ServerSet(this);
		ServerClient.serverSet(this);
		//		ServerClientServer.serverSet(this);
		//서버용 채팅창
		ServerClientServer scs = new ServerClientServer(this, bossName);//서버용 클라이언트 생성
		ServerChat sc = chatRoomCreate(scs, "#All");//서버용 메인 채팅방
		mainRoomKey = sc.key;
		addClient(scs);//클라 리스트에 등록

		//클라 연결 받기
		while (flag) {
			try {//클라이언트 받을대마다

				serverLog("wait client");
				socket = server.accept();
				ServerClient client = new ServerClient(this, socket);
				new Thread(client).start();//스레드 시작
				addClient(client);//서버 접속자에 추가
				client.out((String) p.col만들기(1, 6, 0) + getChatList());//클라에게 리스트 전송

				//				client.chatRoomJoin( mainRoomKey);//서버 메인 채팅방에 추가

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
			server = new ServerSocket(port);//소캣 열기
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

	//===================== 클라 접속 관련 =======================

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

		chats.get(mainRoomKey).chatRoomOut(client);//서버챗창용

		clients.remove(client);
		gui.clientListSet(clients);
		clientListReflash();
		//		chatListReflash();

		serverLog("clients:" + clients.size());
	}

	//===================== 회원 정보 관련 =======================

	/** 회원가입 db 체크후  등록
	 * @param id
	 * @param pw
	 * @param nick
	 * @return 1:성공, 0:실패, -1:에러, 
	 * -2/-4:id/nick 조회실패
	 * -3/-5:id/nick 존재함
	 * */
	public synchronized int userCreate(String id, String pw, String nick) {
		// TODO Auto-generated method stub
		return o.createUser(id, pw, nick);
	}

	/**로그인 db 체크
	 * @param ID
	 * @param Pw
	 * @return 1:성공, 0:실패, -1:에러, -2:id 암호틀림 -3:id 존재 안함
	 */
	public synchronized int userLogin(String id, String pw) {
		// TODO Auto-generated method stub
		//		o.userLogin(id,pw);
		//		if(v==1){
		//			
		//			gui.clientListSet(clients);
		//		}
		//		server.clientListReflash();//로그인 성공시 모든 클라이언트에게 알림//
		return o.userLogin(id, pw);
	}

	/** 닉네임 존재 확인
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
		//		clientListReflash();//로그인 성공시 모든 클라이언트에게 알림
		return 1;
	}

	/**닉네임 db 체크 변경
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
//			clientListReflash();//로그인 성공시 모든 클라이언트에게 알림		
			return nick;
		}else{			
			return null;
		}
	}

	//===================== 채팅방 관련 =======================

	/** 채팅방 생성
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

	/** 채팅방 접속
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

	/** 채팅방 삭제. 방장만 가능.
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

	/** 방인원이 없을때 사용하는 메소드
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

	//===================== gui 출력 관련 =======================

	/** 서버gui에 보내기 위한 메소드
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

	//======================리스트 전송 관련 ================

	/**
	 * 클라이언트 아이디 등의 정보가 바뀔시 재갱신
	 * 각 클라이언트에게 접속자 리스트 전달
	 */
	public synchronized void clientListReflash() {
		// TODO Auto-generated method stub
		gui.clientListSet(clients);

		chats.get(mainRoomKey).clientListReflash();//서버 채팅창용.

		//각 클라이언트에게 접속자 리스트 전달
		String t = getClientList();

		//		serverLog("clientListReflash:" + t);
		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
			ServerClient client = (ServerClient) iterator.next();
			client.out((String) p.col만들기(1, 16, 0) + t + "서버 접속자 목록");
		}

	}

	/**clientListReflash() 메소드용
	 * @return 클라이언트 리스트 문자열 생성
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
	 * 각 클라이언트에게 채팅 리스트 전달
	 */
	public synchronized void chatListReflash() {
		// TODO Auto-generated method stub		
		gui.chatListSet(chats);

		//각 클라이언트에게 채팅 리스트 전달
		String t = getChatList();

		//		serverLog("chatListReflash:" + t);

		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
			ServerClient client = (ServerClient) iterator.next();
			client.out((String) p.col만들기(1, 6, 0) + t);
			//			client.out((String) p.col만들기(1, 6, 0) + getChatList());
		}
	}

	/**
	 * @return 채팅방 리스트 문자열 생성
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
