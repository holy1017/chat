package chatSever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.Protocol;

/**
 * @author Administrator
 *	������ȭ �ʼ�
 */
public interface ServerChat_interface extends Runnable {
	//ä�� �����ڵ�
	List<ServerClient> clients = new ArrayList<>();//ä�ù渶�� ������ ������

	//���� �ĺ���. �ؽ����� Ű������ ���. �������� �����ڷ� �޾ƿ���.
	String	key		= null;
	//���̸�
	String	Name	= null;

	//�������� 
	Protocol code = new Protocol();

	/**������ �ۼ��� �� �޼ҵ带 �̿��ϱ�
	 * @param roomName ä�ù� �̸�
	 * @return �������ݰ�. �������� �������� ���� ��� 
	 */
	int setting(String roomName);

	//Ŭ���̾�Ʈ���� �޾����� �������� ó���� ���� �κ�.
	int protocol(String str);

	//ä�ù� ����
	int chatRoomJoin(String key, ServerClient client);

	//�޼����� �����ڵ鿡�� ����. ����Ŭ���̾�Ʈ�� chatMsgToRoom �̿�
	int chatMsgSendAll(String msg);

	//������ǥ
	int chatRoomKickVote(String nick);

	//����
	int chatRoomKick(String nick);

	//���� ����
	int chatRoomOut();
}
