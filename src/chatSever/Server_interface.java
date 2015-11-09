package chatSever;

import java.util.*;

import javax.swing.JFrame;


/**
 * @author Administrator 
 * ������ �������� ó�� ���� ������ �� Ŭ���̾�Ʈ�� ä�ù濡���� ��û�� ó���ϱ⸸ �ϱ�. 
 * ä�ù��� �޼��� �����̳�, Ŭ���̾�Ʈ�� Ư�� ��ɾ�� �� Ŭ���̾�Ʈ���� ó��.
 */
public interface Server_interface {

	// Ŭ���̾�Ʈ���� ���� ������ ���⿡ �ֱ�
	//	HashMap<String, ServerClient> clients = new HashMap<>();
	// ä�ù� ������ ���⿡ �ֱ�
	//	HashMap<String, ServerChat> chatRooms = new HashMap<>();
	// Ŭ���̾�Ʈ���� ���� ������ ���⿡ �ֱ�
	List<ServerClient>	clients		= new ArrayList<>();
	// ä�ù� ������ ���⿡ �ֱ�
	List<ServerChat>	chatRooms	= new ArrayList<>();

	// GUI ������
	JFrame gui = null;

	// ����Ŭ���̾�Ʈ�� ����ƽ�� ����;
	int setting(int port);

	// ȸ�� ����
	int userCreate(String ID, String Pw);

	// �α���
	int userLogin(String ID, String Pw);

	/** �г��� ����
	 * @param client
	 * @param nick
	 * @return
	 */
	int userNickChange(String client, String nick);

	// ä�ù� ����
	int chatRoomCreate(String roomName);

	// ä�ù� ����
	int chatRoomJoin(String client, String room);

	// ���� �α� ���
	int serverLog(String log);
}
