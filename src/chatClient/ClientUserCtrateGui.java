package chatClient;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;

import common.Password;
import common.Protocol;
import common.userJFrame;

public class ClientUserCtrateGui extends userJFrame {
	Client client;
	Protocol p=new Protocol();
	Password pw=new Password();
	
	ClientUserCtrateGui(Client client) {
		this.client = client;
	}
	
	public void jcreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcreateActionPerformed
		// TODO add your handling code here:
		if( flagChack()){
//			System.out.println(getid()+"|"+pw.SHA256(getpw())+"|"+getnick()+"|"+"create");
			client.out(p.col만들기(1, 1)+getid()+"|"+pw.SHA256(getpw())+"|"+getnick()+"|"+"create");
//			client.out(p.col만들기(1, 1));
		}
	}//GEN-LAST:event_jcreateActionPerformed
}
