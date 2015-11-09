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

	//�����κ��� �����ڷ� �ޱ�
	Socket				socket	= null;
	//��Ĺ���� ����
	DataOutputStream	dos	= null;
	DataInputStream		dis		= null;
	ObjectInputStream	ois		= null;
	ObjectOutputStream	oos	= null;

	//�����κ��� �����ڷ� �ޱ�
	static Server				server		= null;
	//�������� ����
	static List<ServerClient>	clients		= null;	//�޼��� ���޿���
	static List<ServerChat>		chatRooms	= null;

	//���� �ĺ���. �ؽ����� Ű������ ���. �������� �����ڷ� �޾ƿ���.
	String	key		= null;
	String	id		= null;	//�α��� id. �α����� �ޱ�
	String	nick	= null;	//�г���. �г��� ������ �ޱ�

	//�������� ����� ���� 
	Protocol code = new Protocol();

	/**�����ڷ� ����� ������ �κ�
	 * @param key �ؽ��ʿ��� ����� ���� ���а�. ���������� ��������.
	 * @param socket �� Ŭ���̾�Ʈ���� �޾ƿ�
	 * @param server Sever���� this�� �Ѱ���
	 * @return
	 */
	int setting(Socket socket, Server server);

	//���� �����Ϳ��� �������� ó���� ���� �κ�
	int in(String str);

	//Ŭ���̾�Ʈ���� ������  ����
	int out(String str);

	//ȸ������. ���̵� �˻� ���
	int userCreate(String id, String pw);

	//������ ���̵� üũ ��û�� �α���.  
	int userLogin(String id, String pw);

	//id ���Խ� �˻�
	int idChack(String id);

	/** �г��� ���� üũ�� ������ �г��� �����û.
	 * server.userNickChange() �̿��ϱ�
	 * @param nick ������ �г���
	 * @return
	 */
	int userNickChange(String nick);

	//������ ä�ù� ���� ��û
	int chatRoomCreate(String roomName);

	//ä�ù� ����
	int chatRoomJoin(String roomKey, String client);

	//ä�ù濡 �޼��� ����.��ü �޼���
	int chatMsgToRoom(String roomKey, String msg);

	//�Ӹ� ������.
	int chatMsgToClient(String roomKey, String msg);

	//
	int serverLog(String log);

}
