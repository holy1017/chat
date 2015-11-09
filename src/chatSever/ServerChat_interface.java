package chatSever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.Protocol;

/**
 * @author Administrator
 *	스레드화 필수
 */
public interface ServerChat_interface extends Runnable {
	//채팅 접속자들
	List<ServerClient> clients = new ArrayList<>();//채팅방마다 별도의 접속자

	//고유 식별값. 해쉬맵의 키값으로 사용. 서버에서 생성자로 받아오기.
	String	key		= null;
	//방이름
	String	Name	= null;

	//프로토콜 
	Protocol code = new Protocol();

	/**생성자 작성시 이 메소드를 이용하기
	 * @param roomName 채팅방 이름
	 * @return 프로토콜값. 성공인지 실패인지 오류 등등 
	 */
	int setting(String roomName);

	//클라이언트에서 받았을시 프로토콜 처리를 위한 부분.
	int protocol(String str);

	//채팅방 접속
	int chatRoomJoin(String key, ServerClient client);

	//메세지를 접속자들에게 전달. 서버클라이언트의 chatMsgToRoom 이용
	int chatMsgSendAll(String msg);

	//강퇴투표
	int chatRoomKickVote(String nick);

	//강퇴
	int chatRoomKick(String nick);

	//접속 해제
	int chatRoomOut();
}
