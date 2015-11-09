package chatSever;

import common.ChatGui;
import common.Protocol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerChat extends ChatGui implements Runnable, ActionListener, MouseListener {

	static Server server = null;//��� �޾ƿ���???
	//	Socket	socket	= null;

	//	ObjectInputStream	in	= null;
	//	ObjectOutputStream	out	= null;
	
	static void ServerSet(Server server){
		ServerChat.server=server;
	}

	String	name	= "#";	//ä�ù��̸�
	String	key		= "#";

	String boss = null;//����
//	ServerClient boss = null;//����

	List<ServerClient> clients = new ArrayList<>();//���� �����ڵ�
	//	ChatGui			gui		= new ChatGui();

	private Protocol p = new Protocol();

	/**�Ϲ� Ŭ���̾�Ʈ ����
	 * @param client
	 * @param roomName
	 */
	public ServerChat(String key, String roomName, ServerClient client) {
		// TODO Auto-generated constructor stub
		super("ServerChat:" + roomName);

		name = roomName;
		boss = client.id;
		this.key = key;

		chatRoomJoin(client);//�����ڰ� �ٷ� ê�濡 ����
		
		//GUI
		setGuiAddActionListener(this, this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosed(e);
			}
		});
		setLocationRelativeTo(null);//ȭ�� �߾ӿ� ��ġ
		setVisible(true);
	}

	/**���� ȭ�鿡 �α׸� ����
	 * @param log
	 * @return
	 */
	public int Log(String log) {
		// TODO Auto-generated method stub	
		//		System.out.println(log);
		//clientListReflash:1|#boss|#boss
		server.serverLog(name + ":" + log);
		return 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public int setting(String roomName) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int protocol(String str) {
		// TODO Auto-generated method stub
		return 0;
	}

	//===================== ä�ù� ����, ������ ����===========
	/**
	 * @param client
	 * @return
	 */
	public int chatRoomJoin(ServerClient client) {
		// TODO Auto-generated method stub
		Log("clients.contains(client):"+clients.contains(client));
		if (clients.contains(client)) {
			client.out(p.col�����(1, 5, 14) + key + "|"+name+"|�̹� �����ϼ˽��ϴ�");
		}else{
			clients.add(client);
			client.out(p.col�����(1, 5, 1) + key +"|"+name+ "|ä�ù� ���Ӽ���");
			clientListReflash();//������ ����Ʈ�� �ٰ����Ƿ� �� Ŭ���̾�Ʈ���� ����Ʈ ������
		}
		


		return 0;
	}

	/** Ŭ�� ���� ���������
	 * @param client
	 * @return
	 */
	public int chatRoomOut(ServerClient client) {
		// TODO Auto-generated method stub
		clients.remove(client);
		//		gui.clientListSet(clients);
		clientListReflash();
		Log("clients.size():"+clients.size());
		
		if(clients.size()<=0){//�Ѹ� ������� ����
			server.chatRoomDelete( key);
			dispose();
		}
		return 0;
	}

	// ================== �޼��� ���� ===============

	public int chatMsgSendAll(String msg) {
		// TODO Auto-generated method stub

		//		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
		//			ServerClient client = (ServerClient) iterator.next();
		for (ServerClient client : clients) {
			client.out(p.col�����(2, 6, 0) + key + "|" + msg);
		}
		msgAdd(msg);//ȭ�鿡 ���
		return 0;
	}

	public int chatMsgSendClient(String id, String msg) {
		// TODO Auto-generated method stub
		return 0;
	}

	//===============���� ����================

	public int chatRoomKickVote(String nick) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int chatRoomKick(String id) {
		// TODO Auto-generated method stub
		for (ServerClient client : clients) {
			if(client.id.equals(id)){
				client.chatRoomkickMe(this);
				clients.remove(client);
				return 0;
			}
			System.out.println("id:"+id+":������ ����");
			return -1;
		}
		return 0;
	}

	//====================== ����Ʈ ���� ============

	/**
	 * ��� �����ڿ��� ������ ����Ʈ ������
	 */
	public void clientListReflash() {
		clientListSet(clients);//���� gui �� ���̺� ����

		//�� Ŭ���̾�Ʈ���� ������ ����Ʈ ����
		String t = getClientList();

		for (ServerClient client : clients) {
			client.out((String) p.col�����(2, 16, 0) + key + "|" + t + "ä�ù� ������ ���");
		}
		//		serverLog("clientListReflash:" + t);
		//		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
		//			ServerClient client = (ServerClient) iterator.next();
		//			client.out((String) p.col�����(2, 16, 0) + key + "|" + t + "ä�ù� ������ ���");
		//		}
	}

	/**
	 * @return Ŭ���̾�Ʈ ����Ʈ ���ڿ� ����
	 */
	public String getClientList() {
		StringBuffer list = new StringBuffer();
		int count = 0;
		//		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
		//			ServerClient client = (ServerClient) iterator.next();
		for (ServerClient client : clients) {
			list.append("|" + client.GetId());
			list.append("|" + client.GetNick());
			count++;
		}
		list.append("|");
		list.insert(0, count);
		return list.toString();
	}

	//================= �� ���� ���� ================

	/** �����
	 * @param boss �����θ� ���� �����ϹǷ� Ŭ�� �ޱ�
	 */
	public void chatRoomDelete(String boss) {
		// TODO Auto-generated method stub
		//		System.out.println(boss);
		//		System.out.println(this.boss);
		//		System.out.println(boss == this.boss);
		if (boss == this.boss) {
			//			for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
			//				ServerClient client = (ServerClient) iterator.next();
			for (ServerClient client : clients) {
				client.chatRoomDeleteOk(this);
			}
		}
		dispose();
	}

	public void formWindowClosing(WindowEvent evt) {
		// TODO add your handling code here:
		System.out.println("formWindowClosing");
	}

	//=====================get set ===========

	public String GetRoomName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String GetBossName() {
		// TODO Auto-generated method stub
		return boss;
	}

	public static void main(String[] args) {
		new Server();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == chatField) {//ä��
			String t = chatField.getText();
			if (t.length() > 0) {
				//				log("test:"+t);
				chatMsgSendAll(server.bossName+"#" + t);
			}
		}
		if (e.getSource() == kickbt) {

		}
		if (e.getSource() == msgbt) {//�Ӹ�

		}
		if (e.getSource() == nickbt) {

		}
		if (e.getSource() == outbt) {

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
