/* To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in
 * the editor. */
package common;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class userJFrame extends javax.swing.JFrame {

	boolean	flag		= false;
	boolean	flagId		= false;
	boolean	flagPw		= false;
	boolean	flagNick	= false;

	/**
	 * Creates new form userJFrame
	 */
	public userJFrame() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jid = new javax.swing.JTextField();
        jnick = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jpw = new javax.swing.JPasswordField();
        jpwre = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jcreate = new javax.swing.JButton();
        jclose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("id");
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("pw");
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("pw re");
        jLabel3.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setText("nick");
        jLabel4.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jid.setPreferredSize(new java.awt.Dimension(100, 21));
        jid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jidActionPerformed(evt);
            }
        });
        jid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jidKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel1.add(jid, gridBagConstraints);

        jnick.setPreferredSize(new java.awt.Dimension(100, 21));
        jnick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jnickKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jnick, gridBagConstraints);

        jButton1.setText("chack");
        jButton1.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jButton1, gridBagConstraints);

        jLabel6.setText("-");
        jLabel6.setMinimumSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel5.setText("-");
        jLabel5.setMinimumSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel5, gridBagConstraints);

        jButton2.setText("chack");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jButton2, gridBagConstraints);

        jpw.setPreferredSize(new java.awt.Dimension(100, 21));
        jpw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpwActionPerformed(evt);
            }
        });
        jpw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jpwKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jpw, gridBagConstraints);

        jpwre.setPreferredSize(new java.awt.Dimension(100, 21));
        jpwre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpwreActionPerformed(evt);
            }
        });
        jpwre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jpwreKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jpwre, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jcreate.setText("create");
        jcreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcreateActionPerformed(evt);
            }
        });
        jPanel2.add(jcreate);

        jclose.setText("close");
        jclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcloseActionPerformed(evt);
            }
        });
        jPanel2.add(jclose);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jButton2ActionPerformed

	private void jcloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcloseActionPerformed
		// TODO add your handling code here:
		flag = false;
		setVisible(false);
		//        dispose();
	}//GEN-LAST:event_jcloseActionPerformed

	private void jpwreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpwreActionPerformed
		// TODO add your handling code here:.
		jpwre.getPassword();
	}//GEN-LAST:event_jpwreActionPerformed

	private void jpwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpwActionPerformed
		// TODO add your handling code here:
		//    	passwordChackLabel();
	}//GEN-LAST:event_jpwActionPerformed

	private void jpwKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpwKeyReleased
		// TODO add your handling code here:
		passwordChack();
	}//GEN-LAST:event_jpwKeyReleased

	private void jpwreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpwreKeyReleased
		// TODO add your handling code here:
		passwordChack();
	}//GEN-LAST:event_jpwreKeyReleased

	public void jcreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcreateActionPerformed
		// TODO add your handling code here:
		 flagChack();
	}//GEN-LAST:event_jcreateActionPerformed

	public boolean flagChack() {
		flag = false;
		if(!flagId){
			return flag;
		}
		if(!flagNick){
			return flag;
		}
		if(!flagPw){
			return flag;
		}
		flag=true;
		setVisible(false);
		return flag;
	}

	private void jidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jidActionPerformed
		// TODO add your handling code here:

	}//GEN-LAST:event_jidActionPerformed

	private void jidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jidKeyReleased
		// TODO add your handling code here:
		if (jid.getText().length() < 4) {
			flagId = false;
		} else {
			flagId = true;
		}
	}//GEN-LAST:event_jidKeyReleased

	private void jnickKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jnickKeyReleased
		// TODO add your handling code here:
		if (jnick.getText().length() < 1) {
			flagNick = false;
		} else {
			flagNick = true;
		}
	}//GEN-LAST:event_jnickKeyReleased


	public void passwordChack() {
		if (jpw.getPassword().length < 4) {
			jLabel6.setText("4자리이상");
			flagPw = false;
		} else {
			jLabel6.setText("");
			flagPw = true;
		}
		if (Arrays.equals(jpw.getPassword(), jpwre.getPassword())) {
			jLabel5.setText("ok");
		} else {
			jLabel5.setText("불일치!");
			flagPw = false;			
		}
//		flagPw = true;
	}

	public boolean getFlag() {
		return flag;		
	}
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
			java.util.logging.Logger.getLogger(userJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(userJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(userJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(userJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new userJFrame().setVisible(true);
			}
		});
	}

    public String getid() {
		return jid.getText();
	}

	public String getnick() {
		return jnick.getText();
	}

	public String getpw() {
		return new String(jpw.getPassword());
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jclose;
    private javax.swing.JButton jcreate;
    private javax.swing.JTextField jid;
    private javax.swing.JTextField jnick;
    private javax.swing.JPasswordField jpw;
    private javax.swing.JPasswordField jpwre;
    // End of variables declaration//GEN-END:variables
}
