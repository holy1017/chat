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

	protected String	id		= "#";	//토크나이저 문제로 빈값 자르기가 안됨.
	protected String	nick	= "#";	//토크나이저 문제로 빈값 자르기가 안됨.

	private List<ServerClient>	clients	= null;				//서버 접속자들
	private List<ServerChat>	chats	= new ArrayList<>();//현재 접속한 방들
	//	private Map<String, ServerChat>	chats	= new HashMap();
	//	private Map<String, ServerClient>	clients	= null;	//서버 접속자들
	//	private Map<String, ServerChat>		chats	= null;	//현재 접속한 방들

	private boolean	flag	= false;
	private boolean	login	= false;

	private Protocol p = new Protocol();

	public static void serverSet(Server server) {
		ServerClient.server = server;
	}

	/**
	 * 서버 전용. 일반 클라에서는 쓰지 말것
	 */
	public ServerClient(Server server, String bossName) {
		this.server = server;
		id = bossName;
		nick = bossName;
	}

	/** 클라 연결시 방법1
	 * @param server
	 */
	public ServerClient(Server server) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.socket = server.getSocket();
		this.clients = server.getClients();
		setting();
	}

	/**클라 연결시 방법2
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
	 * 생성자 공용 세팅 부분
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
		while (flag) {//접속 상태일시
			try {//로그인만 받기
				inLogin((String) in.readObject());//클라이언트로부터 내용 받기
				while (login) {//로그인됬을경우
					in((String) in.readObject());//클라이언트로부터 내용 받기					
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

	//================ 스트림 처리 ====================

	/** 접속창에서의 처리
	 * @param obj
	 * @return
	 */
	public int inLogin(Object obj) {

		if (!login) {//비로그인상태일경우

			String msg = (String) obj;

//			log("login" + msg);
			p.col메세지분할(msg);//프로토콜과 내용 분리
			log(msg + ":hex:" + p.colGetHex(),"magenta");
			log("상위:" + p.col값추출(1) + ":하위:" + p.col값추출(2) + ":판정:" + p.col값추출(3) ,"blue");

			//			System.out.println(p.col판정(1));
			//			System.out.println(Protocol.P상위_서버);

			switch (p.col값추출(1)) {//서버인지 채팅인지 구분
			case 1://서버

				//				System.out.println(p.col판정(2));
				//				System.out.println(Protocol.P하위_로그인);

				switch (p.col값추출(2)) {//종류 구분
				case 1://회원가입
					p.col메세지분할(msg, 4);
					userCreate(p.colGetIndex(1), p.colGetIndex(2), p.colGetIndex(3));
					return 0;
				case 2://로그인
					if (p.col메세지분할(msg, 2).size() == 3) {// 인수 갯수가 맞는지 검사
						userLogin(p.colGetIndex(1), p.colGetIndex(2));
						return 0;
					} else {
						out(p.col만들기(1, 2, 13) + "인수 맞지 않음!");
						return -1;
					}
				default:
					log(p.col만들기(1, 2, 12) + "로그인 해주세요. 없는 하위구분 서버 프로토콜입니다1");
					out(p.col만들기(1, 2, 12) + "로그인 해주세요. 없는 하위구분 서버  프로토콜입니다2");
					return -1;

				}
				//			case 2://채팅
				//				switch (p.col값추출(2)) {//하위 프로토콜
				//				case 6://채팅내용전달
				//					p.col메세지분할(msg, 2);
				//					log("ServerClient_in2:" + p.colGetIndex(1));
				//					log("ServerClient_in3:" + p.colGetIndex(2));
				//					chatMsgToRoom(p.colGetIndex(1), p.colGetIndex(2));
				//					return 0;
				//				case 7://강퇴요청
				//					p.col메세지분할(msg, 3);
				//					chatRomeKick(p.colGetIndex(1), p.colGetIndex(2));
				//					return 0;
				//				case 8://귓말
				//					p.col메세지분할(msg, 3);
				//					chatMsgToClient(p.colGetIndex(1), p.colGetIndex(2), p.colGetIndex(3));
				//					return 0;
				//				case 9://나가기
				//					p.col메세지분할(msg, 2);
				//					chatRoomOut(p.colGetIndex(1));
				//					return 0;
				//				default:
				//					out((p.colGet() | p.P상태_없음) + "|없는 하위구분 채팅 프로토콜입니다");
				//					return 0;
				//				}
			default:
				log(p.col만들기(1, 2, 12) + "로그인 해주세요. 없는 상위구분 서버 프로토콜입니다3");
				out(p.col만들기(1, 2, 12) + "로그인 해주세요. 없는 상위구분 서버 프로토콜입니다4");
				return -1;
			}
		} else {
			out((p.colGet() | p.P상태_실패) + "|로그아웃을 해주세요");
			return -1;
		}
	}

	/**받은 메세지에서 프로토콜 처리. 로그인 후에만 작동. 결과는 각 메소드에서 처리
	 * @param obj
	 * @return
	 */
	private int in(Object obj) {
		// TODO Auto-generated method stub
		String msg = (String) obj;
		p.col메세지분할(msg);//프로토콜과 내용 분리
		log(obj + ":hex:" + p.colGetHex());
		log("상위:" + p.col값추출(1) + ":하위:" + p.col값추출(2) + ":판정:" + p.col값추출(3) ,"blue");
//		log("ServerClient_in1:" + msg + "|p.col값추출(1):" + p.col값추출(1) + "|p.col값추출(2):" + p.col값추출(2));
		//		logServer("ServerClient_in:" + p.col값추출(1));
		//		logServer("ServerClient_in:" + p.col값추출(2));
		try {
			switch (p.col값추출(1)) {//상위 프로토콜
			case 1://서버
				switch (p.col값추출(2)) {//하위 프로토콜
				case 3://닉네임변경
					p.col메세지분할(msg, 2);
					userNickChange(p.colGetIndex(1));
					return 0;
				case 4://채팅방생성
					p.col메세지분할(msg, 2);
					chatRoomCreate(p.colGetIndex(1));
					return 0;
				case 5://채팅방접속
					p.col메세지분할(msg, 2);
					chatRoomJoin(p.colGetIndex(1));
					return 0;
				default:
					out((p.colGet() | p.P상태_없음) + "|없는 하위구분 서버 프로토콜입니다");
					return 0;
				}

			case 2://채팅

				switch (p.col값추출(2)) {//하위 프로토콜
				case 6://채팅내용전달
					p.col메세지분할(msg, 2);
					log("ServerClient_in2:" + p.colGetIndex(1));
					log("ServerClient_in3:" + p.colGetIndex(2));
					chatMsgToRoom(p.colGetIndex(1), p.colGetIndex(2));
					return 0;
				case 7://강퇴요청
					p.col메세지분할(msg, 3);
					chatRomeKick(p.colGetIndex(1), p.colGetIndex(2));
					return 0;
				case 8://귓말
					p.col메세지분할(msg, 3);
					chatMsgToClient(p.colGetIndex(1), p.colGetIndex(2), p.colGetIndex(3));
					return 0;
				case 9://나가기
					p.col메세지분할(msg, 2);
					chatRoomOut(p.colGetIndex(1));
					return 0;
				default:
					out((p.colGet() | p.P상태_없음) + "|없는 하위구분 채팅 프로토콜입니다");
					return 0;
				}

			default:
				out((p.colGet() | p.P상태_없음) + "|없는 상위구분 프로토콜입니다");
				return 0;
			}
		} finally {

		}
	}

	/** 클라이언트로 메세지 보내기
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

	//===================== 회원 관련 메소드 =====================
	/** 회원가입, 정규식 검사하기
	 * @param id
	 * @param pw
	 * @return
	 */
	private int userCreate(String id, String pw, String Nick) {
		// TODO Auto-generated method stub

		if (idChack(id)) {
			switch (server.userCreate(id, pw, Nick)) {
			case 1:
				out(p.col만들기(1, 1, 1) + "회원가입 성공");
				break;
			case 0:
				out(p.col만들기(1, 1, 15) + "회원가입 실패");
				break;
			case -3:
				out(p.col만들기(1, 1, 14) + "회원가입 실패:아뒤중복");
				break;
			case -5:
				out(p.col만들기(1, 1, 14) + "회원가입 실패:닉 중복");
				break;
//			case -1:
//				out(p.col만들기(1, 1, 13) + "회원가입 성공");
//				break;
			default:
				out(p.col만들기(1, 1, 1) + "회원가입 성공");
				break;
			}
			//			server.userCreate(id, pw, Nick);
			//			out(p.col만들기(1, 1, 1) + "회원가입 성공");
		} else {
			out(p.col만들기(1, 1, 13) + "회원가입 아이디 정규식 문제");
		}
		return 0;
	}

	/**서버에 회원로그인 요청, 정규식 검사하기
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
				out(p.col만들기(1, 2, 1) + id + "|" + nick + "|로그인 성공");
				out(p.col만들기(1, 6, 0) + server.getChatList() + "채팅방 리스트");//채팅방 리스트 받기
				//				out(p.col만들기(1, 16, 0) + server.getClientList()+"서버 접속자 리스트");//채팅방 리스트 받기
				server.clientListReflash();//로그인 성공시 모든 클라이언트에게 알림
				break;
			case 0:
				out(p.col만들기(1, 2, 15) + "로그인 실패");
				break;
			default:
				out(p.col만들기(1, 2, 13) + "로그인 실패");
				break;
			}
		} else {
			out(p.col만들기(1, 2, 13) + "로그인 아이디 정규식 문제");
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
		server.clientListReflash();//로그인 성공시 모든 클라이언트에게 알림
	}

	/**닉네임 변경, 정규식 검사하기
	 * @param nick
	 * @return
	 */
	private int userNickChange(String nick) {
		// TODO Auto-generated method stub
		String t = server.userNickChange(this, nick);
		if (t != null) {
			//			gui.clientListSet(clients);
			this.nick = nick;
			out(p.col만들기(1, 3, 1) + "|성공");
			server.clientListReflash();//로그인 성공시 모든 클라이언트에게 알림		
		} else {
			out(p.col만들기(1, 3, 15) + "|실패");
		}
		return 0;
	}

	/**닉네임 변경, 정규식 검사하기
	 * @param nick
	 * @return
	 */
	int userNickChangeOk(String nick) {
		// TODO Auto-generated method stub
		this.nick = nick;
		//		server.userNickChange(this, nick);
		return 0;
	}

	/** 아이디 정규식 검사
	 * @param id
	 * @return
	 */
	public boolean idChack(String id) {
		// TODO Auto-generated method stub
		StringChack sc = new StringChack();
		return sc.sc_idChack(id);
	}

	//===================== 채팅 생성 접속 관련 메소드 =====================
	/**채팅방 생성
	 * @param roomName 방이름
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

	/** 필요 없는 메소드?
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

	/**채팅방 접속
	 * @param chatnum 방번호
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

	/** 방 나오기
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
				chat.chatRoomOut(this);//해당 채팅방에 나가기 처리
				chats.remove(chat);
				out(p.col만들기(2, 9, 1) + roomKey + "|방나가기 성공:" + chats.size());
				return 0;
			}
		}
		return 0;
	}

	/**강퇴요청
	 * @param roomNum
	 * @param id
	 * @return
	 */
	public int chatRomeKick(String roomKey, String id) {
		for (ServerChat chat : chats) {
			log("chatMsgToRoom:" + chat.key);
			if (chat.key.equals(roomKey)) {//접속한 방일시
				//				log("chatMsgToRoom:" + chat);
				//				log("chatMsgToRoom:" + nick);
				//				log("chatMsgToRoom:" + msg);
				System.out.println("chat.boss.id:" + chat.boss);
				System.out.println("id:" + id);
				if (chat.boss.equals(id)) {//강퇴대상이 방장인경우
					System.out.println("chat.boss.id==id");
					out(p.col만들기(2, 7, 15) + "자기자신은 강퇴 불가능");
				} else {
					System.out.println(chat.boss + ":" + this.id);
					if (chat.boss.equals(this.id)) {//강퇴요청자가 방장인경우						
						if (chat.chatRoomKick(id) == 0) {
							out(p.col만들기(2, 7, 2) + id + "|강퇴성공");
						} else {
							out(p.col만들기(2, 7, 15) + id + "|강퇴실패");
						}
					} else {
						chat.chatRoomKickVote(id);
					}
				}
				return 0;
			}
		}
		out(p.col만들기(2, 6, 12) + "접속하지 않은방입니다");
		//접속하지 않은방입니다

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
		out(p.col만들기(2, 9, 1) + chat.key + "|강퇴됨:" + chats.size());
		chats.remove(chat);
		//		for (ServerChat chat : chats) {
		//
		//			//		}
		//			//		for (Iterator iterator = chats.iterator(); iterator.hasNext();) {
		//			//			ServerChat chat = (ServerChat) iterator.next();
		//			if (chat.key.equals(roomKey)) {
		//				//				chat.chatRoomOut(this);//해당 채팅방에 나가기 처리
		//				chats.remove(chat);
		//				out(p.col만들기(2, 9, 1) + roomKey + "|강퇴됨:" + chats.size());
		//				return 0;
		//			}
		//		}
		return 0;
	}

	public int chatRoomDelete(ServerChat chat) {
		// TODO Auto-generated method stub
		chats.remove(chat);
		out((p.col만들기(2, 10, 1)) + "|방폭발!");
		return 0;
	}

	public int chatRoomDeleteOk(ServerChat chat) {
		// TODO Auto-generated method stub
		chats.remove(chat);
		out((p.col만들기(2, 10, 1)) + "|방폭발!");
		return 0;
	}

	/** 채팅방에 메세지 보내기
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
			if (chat.key.equals(roomKey)) {//접속한 방일시
				//				log("chatMsgToRoom:" + chat);
				//				log("chatMsgToRoom:" + nick);
				//				log("chatMsgToRoom:" + msg);
				chat.chatMsgSendAll(nick + "#" + msg);
				return 0;
			}
		}
		out(p.col만들기(2, 6, 12) + "접속하지 않은방입니다");
		//접속하지 않은방입니다

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

	/** 채팅방의 특정인에게 귓 보내기
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

	/** 서버에 로그를 남김
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
