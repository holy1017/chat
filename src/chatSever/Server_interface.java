package chatSever;

import java.util.*;

import javax.swing.JFrame;


/**
 * @author Administrator 
 * 서버는 프로토콜 처리 없이 오로지 각 클라이언트와 채팅방에서의 요청을 처리하기만 하기. 
 * 채팅방의 메세지 전달이나, 클라이언트의 특수 명령어는 각 클라이언트에서 처리.
 */
public interface Server_interface {

	// 클라이언트에서 접속 받을시 여기에 넣기
	//	HashMap<String, ServerClient> clients = new HashMap<>();
	// 채팅방 생성시 여기에 넣기
	//	HashMap<String, ServerChat> chatRooms = new HashMap<>();
	// 클라이언트에서 접속 받을시 여기에 넣기
	List<ServerClient>	clients		= new ArrayList<>();
	// 채팅방 생성시 여기에 넣기
	List<ServerChat>	chatRooms	= new ArrayList<>();

	// GUI 구현용
	JFrame gui = null;

	// 서버클라이언트의 스태틱들 설정;
	int setting(int port);

	// 회원 가입
	int userCreate(String ID, String Pw);

	// 로그인
	int userLogin(String ID, String Pw);

	/** 닉네임 변경
	 * @param client
	 * @param nick
	 * @return
	 */
	int userNickChange(String client, String nick);

	// 채팅방 생성
	int chatRoomCreate(String roomName);

	// 채팅방 접속
	int chatRoomJoin(String client, String room);

	// 서버 로그 기록
	int serverLog(String log);
}
