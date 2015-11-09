package chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.ChatGui;
import common.StyledDoc;

public class ClientChat extends ChatGui implements Runnable, ActionListener, MouseListener {

	//	Socket socket = null;

	//	ObjectInputStream	in	= null;
	//	ObjectOutputStream	out	= null;

	//	boolean flag = false;

	String			key	= null;
	static Client	client;
	

	public static void clientSet(Client client) {
		// TODO Auto-generated method stub
		ClientChat.client = client;
	}

	public ClientChat(String key, String roomName) {
		// TODO Auto-generated constructor stub
		//		this.socket = socket;	
		//		this.out=out;
		//		flag = true;
		super(roomName);
		this.key = key;
		setVisible(true);
		setting();
	}

	public ClientChat(String key, String roomName, Client client) {
		// TODO Auto-generated constructor stub
		//		this.socket = socket;	
		//		this.out=out;
		//		flag = true;
		super(roomName);
		this.key = key;
		this.client = client;
		setVisible(true);
		setting();
	}

	private void setting() {
		// TODO Auto-generated method stub

		setGuiAddActionListener(this, this);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				log("windowClosing");
				client.roomOut(key);
				dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosed(e);
				log("windowClosed");

			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//		while (flag) {
		//
		//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == getChatField()) {//채팅
			String t = getChatField().getText();
			if (t.length() > 0) {
				//				log("test:" + t);
				client.roomMsgSend(key, t);
			}
			//				client.sendMsg(key, chatField.getText());
			getChatField().setText("");
		}
		if (e.getSource() == getKickbt()) {//강퇴
			String id;
			System.out.println(userList.getSelectedRow());
			System.out.println(userList.getValueAt(userList.getSelectedRow(), 0));
			id = (String) userList.getValueAt(userList.getSelectedRow(), 0);
			//			System.out.println(id);
			client.roomkick(key, id);

		}
		if (e.getSource() == getMsgbt()) {//귓말

		}
		if (e.getSource() == getNickbt()) {//닉네임변경

		}
		if (e.getSource() == getOutbt()) {
			client.roomOut(key);
			dispose();
		}

	}

	private void log(String t) {
		// TODO Auto-generated method stub
		client.log(t);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == getUserList()) {//유저선택

		}
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

	public static void main(String[] args) {
		new Client();
		//		new Client("192.168.0.2",3000);
	}

	public void msgAdd(String msg,String style) {
		// TODO Auto-generated method stub
		
		sd.addADocEnd(msg+"\n", style);
//		setEndline();
//		getjTextPane1().replaceSelection(msg + "\n");
		jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
	}
	public void msgAdd(String msg) {
		// TODO Auto-generated method stub

		sd.addADocEnd(msg+"\n", null);
//		setEndline();
//		getjTextPane1().replaceSelection(msg + "\n");
		jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
	}
}
