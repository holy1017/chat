/* To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in
 * the editor. */
package common;

import chatSever.ServerClient;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author Administrator
 */
public class ChatGui extends javax.swing.JFrame {

	DefaultTableModel	clientsList	= new DefaultTableModel();
	protected StyledDoc	sd;

	/**
	 * Creates new form ServerGui
	 */
	public ChatGui(String roomName) {
		setTitle(roomName);
		initComponents();
		sd = new StyledDoc((AbstractDocument) jTextPane1.getStyledDocument());
//		jTextPane1.setEditorKit(new WrapEditorKit());
		jTextPane1.setText(roomName+":ExampleOfTheWrapLongWordWithoutSpaces");
		userList.getTableHeader().setReorderingAllowed(false);// 이동 불가 
		this.setLocationRelativeTo(null);
	}

	public void msgAdd(String msg) {
		//		setEndline();
		//		jTextPane1.replaceSelection(msg + "\n");
		sd.addADocEnd(msg + "\n");
		jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
	
	}

	public void msgAdd(String msg, String style) {
		//		setEndline();
		//		jTextPane1.replaceSelection(msg + "\n");
		sd.addADocEnd(msg + "\n", style);
		jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
	}

	public void clientListSet(List<ServerClient> List) {
		//		System.out.println("clientListSet1");
		Vector<String> columnNames = new Vector<String>();

		columnNames.add("id");
		columnNames.add("nick");

		// data of the table
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		for (ServerClient client : List) {
			//			System.out.println(client);

			Vector<String> vector = new Vector<String>();

			vector.add(client.GetId());
			vector.add(client.GetNick());

			data.add(vector);
		}
		clientsList.setDataVector(data, columnNames);

	}

	public void clientListSet(String[][] list) {
		// TODO Auto-generated method stub

		String[] columnNames = new String[2];

		columnNames[0] = "id";
		columnNames[1] = "nick";

		clientsList.setDataVector(list, columnNames);
	}

	public void setEndline() {//그냥 억지로 함수 하나 만들었어요.
		// TODO Auto-generated method stub
		jTextPane1.selectAll();
		//문장의 끝에 무조건 커서 이동하게 설정
		jTextPane1.setSelectionStart(jTextPane1.getSelectionEnd());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new JToolBar();
        kickbt = new JButton();
        nickbt = new JButton();
        msgbt = new JButton();
        outbt = new JButton();
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextPane1 = new JTextPane();
        chatField = new JTextField();
        jPanel4 = new JPanel();
        jScrollPane2 = new JScrollPane();
        userList = new JTable();
        jPanel2 = new JPanel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(300, 600));

        jToolBar1.setRollover(true);

        kickbt.setIcon(new ImageIcon(getClass().getResource("/ico/제목 없음 3.png"))); // NOI18N
        kickbt.setText("강퇴");
        kickbt.setFocusable(false);
        kickbt.setHorizontalTextPosition(SwingConstants.RIGHT);
        jToolBar1.add(kickbt);

        nickbt.setIcon(new ImageIcon(getClass().getResource("/ico/nick.png"))); // NOI18N
        nickbt.setText("닉네임변경");
        nickbt.setFocusable(false);
        nickbt.setHorizontalTextPosition(SwingConstants.RIGHT);
        jToolBar1.add(nickbt);

        msgbt.setIcon(new ImageIcon(getClass().getResource("/ico/수정됨_User With Frame.png"))); // NOI18N
        msgbt.setText("귓속말");
        msgbt.setFocusable(false);
        msgbt.setHorizontalTextPosition(SwingConstants.RIGHT);
        msgbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                msgbtActionPerformed(evt);
            }
        });
        jToolBar1.add(msgbt);

        outbt.setIcon(new ImageIcon(getClass().getResource("/ico/수정됨_Power - Shut Down.png"))); // NOI18N
        outbt.setText("나가기");
        outbt.setFocusable(false);
        outbt.setHorizontalTextPosition(SwingConstants.RIGHT);
        outbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                outbtActionPerformed(evt);
            }
        });
        jToolBar1.add(outbt);

        getContentPane().add(jToolBar1, BorderLayout.NORTH);

        jPanel1.setLayout(new GridLayout(1, 0));

        jPanel3.setLayout(new BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportView(jTextPane1);

        jPanel3.add(jScrollPane1, BorderLayout.CENTER);

        chatField.setText("jTextField1");
        chatField.setPreferredSize(new Dimension(100, 30));
        jPanel3.add(chatField, BorderLayout.PAGE_END);

        jPanel1.add(jPanel3);

        jPanel4.setLayout(new BorderLayout());

        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        userList.setModel(clientsList);
        jScrollPane2.setViewportView(userList);

        jPanel4.add(jScrollPane2, BorderLayout.CENTER);

        jPanel1.add(jPanel4);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        jPanel2.setLayout(new GridLayout(1, 0));
        getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void outbtActionPerformed(ActionEvent evt) {//GEN-FIRST:event_outbtActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_outbtActionPerformed

	private void msgbtActionPerformed(ActionEvent evt) {//GEN-FIRST:event_msgbtActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_msgbtActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ChatGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ChatGui("test").setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected JTextField chatField;
    JPanel jPanel1;
    JPanel jPanel2;
    JPanel jPanel3;
    JPanel jPanel4;
    protected JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    protected JTextPane jTextPane1;
    JToolBar jToolBar1;
    protected JButton kickbt;
    protected JButton msgbt;
    protected JButton nickbt;
    protected JButton outbt;
    protected JTable userList;
    // End of variables declaration//GEN-END:variables

	//	public void setGuiAddActionListener(ActionListener al,MouseListener ml,WindowListener wl) {
	public void setGuiAddActionListener(ActionListener al, MouseListener ml) {

		chatField.addActionListener(al);
		kickbt.addActionListener(al);
		msgbt.addActionListener(al);
		nickbt.addActionListener(al);
		outbt.addActionListener(al);

		//		userList.addKeyListener(kl);
		userList.addMouseListener(ml);

		//                addWindowListener(wl);
	}

	public DefaultTableModel getClientsList() {
		return clientsList;
	}

	public JTextField getChatField() {
		return chatField;
	}

	public JTextPane getjTextPane1() {
		return jTextPane1;
	}

	public JButton getKickbt() {
		return kickbt;
	}

	public JButton getMsgbt() {
		return msgbt;
	}

	public JButton getNickbt() {
		return nickbt;
	}

	public JButton getOutbt() {
		return outbt;
	}

	public JTable getUserList() {
		return userList;
	}

}
