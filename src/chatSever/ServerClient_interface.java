package chatSever;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.Protocol;

public interface ServerClient_interface extends Runnable {

	//서버로부터 생성자로 받기
	Socket				socket	= null;
	//소캣에서 추출
	DataOutputStream	dos	= null;
	DataInputStream		dis		= null;
	ObjectInputStream	ois		= null;
	ObjectOutputStream	oos	= null;

	//서버로부터 생성자로 받기
	static Server				server		= null;
	//서버에서 추출
	static List<ServerClient>	clients		= null;	//메세지 전달용등등
	static List<ServerChat>		chatRooms	= null;

	//고유 식별값. 해쉬맵의 키값으로 사용. 서버에서 생성자로 받아오기.
	String	key		= null;
	String	id		= null;	//로그인 id. 로그인후 받기
	String	nick	= null;	//닉네임. 닉네임 변경후 받기

	//프로토콜 사용을 위해 
	Protocol code = new Protocol();

	/**생성자로 만들시 구현할 부분
	 * @param key 해쉬맵에서 사용할 고유 구분값. 서버측에서 생성해줌.
	 * @param socket 각 클라이언트마다 받아옴
	 * @param server Sever에서 this로 넘겨줌
	 * @return
	 */
	int setting(Socket socket, Server server);

	//받은 데이터에서 프로토콜 처리를 위한 부분
	int in(String str);

	//클라이언트에게 데이터  전송
	int out(String str);

	//회원가입. 아이디 검사 등등
	int userCreate(String id, String pw);

	//서버로 아이디 체크 요청후 로그인.  
	int userLogin(String id, String pw);

	//id 정규식 검사
	int idChack(String id);

	/** 닉네임 변경 체크후 서버로 닉네임 변경요청.
	 * server.userNickChange() 이용하기
	 * @param nick 변경할 닉네임
	 * @return
	 */
	int userNickChange(String nick);

	//서버에 채팅방 생성 요청
	int chatRoomCreate(String roomName);

	//채팅방 접속
	int chatRoomJoin(String roomKey, String client);

	//채팅방에 메세지 전달.전체 메세지
	int chatMsgToRoom(String roomKey, String msg);

	//귓말 보내기.
	int chatMsgToClient(String roomKey, String msg);

	//
	int serverLog(String log);

}
