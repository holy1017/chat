package chatClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import common.Protocol;

/**
 * @author Holy
 *
 */
public interface Client_interface {
	String	id	= null;
	String	Key	= null;

	Protocol code = new Protocol();

	List<ClientChat> chatRooms = new ArrayList<>();

	JFrame gui = null;

	ObjectInputStream	oin		= null;
	ObjectOutputStream	oout	= null;

	//회원가입 요청
	int userCreate(String ID, String Pw);

	//로그인 요청
	int userLogin(String ID, String Pw);

	//채팅방 생성 요청
	int chatRoomCreate(String roomName);

	//채팅방 리스트 요청
	int chatRoomList();

	/**채팅방 접속
	 * @param roomKey
	 * @return
	 * 접속 성공시 CilentChat 생성후 chatRooms에 추가
	 */
	int chatRoomJoin(String roomKey);

	//방 유저 리스트
	int chatUserList(String roomKey);

	//채팅 메세지 전달
	int chatMsg(String roomKey, String msg);

	//닉네임 변경
	int userNickChange(String nick);

	//강퇴 요청
	int chatUserKick(String roomKey, String nick);

}
