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

	static Server server = null;//어디서 받아오지???
	//	Socket	socket	= null;

	//	ObjectInputStream	in	= null;
	//	ObjectOutputStream	out	= null;
	
	static void ServerSet(Server server){
		ServerChat.server=server;
	}

	String	name	= "#";	//채팅방이름
	String	key		= "#";

	String boss = null;//방장
//	ServerClient boss = null;//방장

	List<ServerClient> clients = new ArrayList<>();//서버 접속자들
	//	ChatGui			gui		= new ChatGui();

	private Protocol p = new Protocol();

	/**일반 클라이언트 접속
	 * @param client
	 * @param roomName
	 */
	public ServerChat(String key, String roomName, ServerClient client) {
		// TODO Auto-generated constructor stub
		super("ServerChat:" + roomName);

		name = roomName;
		boss = client.id;
		this.key = key;

		chatRoomJoin(client);//생성자가 바로 챗방에 접속
		
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
		setLocationRelativeTo(null);//화면 중앙에 배치
		setVisible(true);
	}

	/**서버 화면에 로그를 남김
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

	//===================== 채팅방 접속, 나가기 관련===========
	/**
	 * @param client
	 * @return
	 */
	public int chatRoomJoin(ServerClient client) {
		// TODO Auto-generated method stub
		Log("clients.contains(client):"+clients.contains(client));
		if (clients.contains(client)) {
			client.out(p.col만들기(1, 5, 14) + key + "|"+name+"|이미 접속하셧습니다");
		}else{
			clients.add(client);
			client.out(p.col만들기(1, 5, 1) + key +"|"+name+ "|채팅방 접속성공");
			clientListReflash();//접속자 리스트가 바겻으므로 각 클라이언트에게 리스트 재전달
		}
		


		return 0;
	}

	/** 클라가 방을 나갔을경우
	 * @param client
	 * @return
	 */
	public int chatRoomOut(ServerClient client) {
		// TODO Auto-generated method stub
		clients.remove(client);
		//		gui.clientListSet(clients);
		clientListReflash();
		Log("clients.size():"+clients.size());
		
		if(clients.size()<=0){//한명도 없을경우 폭파
			server.chatRoomDelete( key);
			dispose();
		}
		return 0;
	}

	// ================== 메세지 전송 ===============

	public int chatMsgSendAll(String msg) {
		// TODO Auto-generated method stub

		//		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
		//			ServerClient client = (ServerClient) iterator.next();
		for (ServerClient client : clients) {
			client.out(p.col만들기(2, 6, 0) + key + "|" + msg);
		}
		msgAdd(msg);//화면에 출력
		return 0;
	}

	public int chatMsgSendClient(String id, String msg) {
		// TODO Auto-generated method stub
		return 0;
	}

	//===============강퇴 관련================

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
			System.out.println("id:"+id+":강퇴대상 없음");
			return -1;
		}
		return 0;
	}

	//====================== 리스트 전송 ============

	/**
	 * 모든 접속자에게 접속자 리스트 재전송
	 */
	public void clientListReflash() {
		clientListSet(clients);//서버 gui 의 테이블 갱신

		//각 클라이언트에게 접속자 리스트 전달
		String t = getClientList();

		for (ServerClient client : clients) {
			client.out((String) p.col만들기(2, 16, 0) + key + "|" + t + "채팅방 접속자 목록");
		}
		//		serverLog("clientListReflash:" + t);
		//		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
		//			ServerClient client = (ServerClient) iterator.next();
		//			client.out((String) p.col만들기(2, 16, 0) + key + "|" + t + "채팅방 접속자 목록");
		//		}
	}

	/**
	 * @return 클라이언트 리스트 문자열 생성
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

	//================= 방 삭제 관련 ================

	/** 방삭제
	 * @param boss 방주인만 삭제 가능하므로 클라 받기
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
		if (e.getSource() == chatField) {//채팅
			String t = chatField.getText();
			if (t.length() > 0) {
				//				log("test:"+t);
				chatMsgSendAll(server.bossName+"#" + t);
			}
		}
		if (e.getSource() == kickbt) {

		}
		if (e.getSource() == msgbt) {//귓말

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
