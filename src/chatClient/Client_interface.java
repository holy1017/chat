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

	//ȸ������ ��û
	int userCreate(String ID, String Pw);

	//�α��� ��û
	int userLogin(String ID, String Pw);

	//ä�ù� ���� ��û
	int chatRoomCreate(String roomName);

	//ä�ù� ����Ʈ ��û
	int chatRoomList();

	/**ä�ù� ����
	 * @param roomKey
	 * @return
	 * ���� ������ CilentChat ������ chatRooms�� �߰�
	 */
	int chatRoomJoin(String roomKey);

	//�� ���� ����Ʈ
	int chatUserList(String roomKey);

	//ä�� �޼��� ����
	int chatMsg(String roomKey, String msg);

	//�г��� ����
	int userNickChange(String nick);

	//���� ��û
	int chatUserKick(String roomKey, String nick);

}
