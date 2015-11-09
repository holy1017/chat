package chatSever;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerOrcl {
	Connection	cnt;
	Statement	st;

	public ServerOrcl(String url, String id, String pw) {
		// TODO Auto-generated constructor stub

		url = "jdbc:oracle:thin:@" + url;

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Class.forName");

			cnt = DriverManager.getConnection(url, id, pw);
			System.out.println("DriverManager.getConnection");

			st = cnt.createStatement();
			System.out.println("createStatement");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			st.close();
			cnt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectUserAll() {
		String sql = "select * from chat_user";
		try {
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				String id = rs.getString(1);
				String passwd = rs.getString(2);
				String nick = rs.getString(3);
				System.out.println(id + "\t" + nick + "\t" + passwd);
			} //while---------
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param id
	 * @return 0xf:조회실패,0x000e 존재함,0x000c 존재 안함
	 */
	public synchronized int chackId(String id) {
		// TODO Auto-generated method stub
		try {
			int rowCnt = 0;

			ResultSet rs = st.executeQuery("select COUNT(*) from chat_user where id='" + id + "'");
			if (rs.next()) {
				rowCnt = rs.getInt(1);
				System.out.println("Total rows : " + rowCnt);
				if (rowCnt > 0) {
					return 0x000e;
				} else {
					return 0x000c;
				}
			} else {
				return 0xf;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0xf;
		}
	}

	/**
	 * @param nick
	 * @return 0xf:조회실패,0x000e 존재함,0x000c 존재 안함
	 */
	public synchronized int chackNick(String nick) {
		// TODO Auto-generated method stub

		try {
			int rowCnt = 0;

			ResultSet rs = st.executeQuery("select COUNT(*) from chat_user where nick='" + nick + "'");
			if (rs.next()) {
				rowCnt = rs.getInt(1);
				System.out.println("Total rows : " + rowCnt);
				if (rowCnt > 0) {
					return 0x000e;
				} else {
					return 0x000c;
				}
				//		ResultSetMetaData rsmd =null;
				//
				//		rsmd=rs.getMetaData();
				//
				//		int rowCnt = rsmd.getColumnCount();
			} else {
				return 0xf;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0xf;
		}

	}

	synchronized int nickChange(String id, String nick) {
		System.out.println("nickChange");
		try {
			System.out.println("id+nick" + id + nick);
			String q = "UPDATE CHAT_USER SET NICK = '" + nick + "' WHERE  ID   = '" + id + "'";
			int result = st.executeUpdate(q);
			//			PreparedStatement pstmt = null;
			//			//
			//			pstmt = cnt.prepareStatement("UPDATE CHAT_USER SET "
			//					+ "NICK = ? "
			//					+ "WHERE  ID   = ?");
			//			pstmt.setString(1, nick);
			//			pstmt.setString(2, id);
			//			System.out.println("id+nick" + id + nick);
			//			int result = pstmt.executeUpdate();
			cnt.commit();
			System.out.println("result:" + result);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @param id
	 * @param pw
	 * @param nick
	 * @return 1:성공, 0:실패, -1:에러, 
	 * -2/-4:id/nick 조회실패
	 * -3/-5:id/nick 존재함
	 */
	public synchronized int createUser(String id, String pw, String nick) {
		System.out.println("chackId(id):"+chackId(id)+":"+id);
		switch (chackId(id)) {
		case 0xf:
			return -2;
		case 0x000e:
			return -3;
		case 0x000c:
			break;
		default:
			return -1;
		}
		System.out.println("chackNick(nick):"+chackNick(nick)+":"+nick);
		switch (chackNick(nick)) {
		case 0xf:
			return -4;
		case 0x000e:
			return -5;
		case 0x000c:
			break;
		default:
			return -1;
		}
		try {
			PreparedStatement pstmt = null;
			pstmt = cnt.prepareStatement("insert into chat_user " + "(id,pw,nick)" + "values(?,?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nick);
			int result = pstmt.executeUpdate();
			cnt.commit();
			System.out.println("result:" + result);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			return -1;
		}

		//		return 0;

	}

	/**
	 * @param id
	 *  @return 1:성공, 0:실패, -1:에러, 
	 * -2:id 암호틀림	 
	 * -3:id 존재 안함
	 */
	public synchronized int userLogin(String id, String pw) {
		// TODO Auto-generated method stub
		//		switch (chackId(id)) {
		//		case 0xf:
		//			return -2;
		//		case 0x000e:
		//			break;
		//		case 0x000c:
		//			return -3;
		//		default:
		//			return -1;
		//		}
		try {
			ResultSet rs = st.executeQuery("select pw from chat_user where id='" + id + "'");
			if (rs.next()) {
				String w = rs.getString(1);
				System.out.println("pw: " + w);
				if (pw.equals(w)) {
					return 1;
				} else {
					return -2;
				}
				//		ResultSetMetaData rsmd =null;
				//
				//		rsmd=rs.getMetaData();
				//
				//		int rowCnt = rsmd.getColumnCount();
			} else {
				return -3;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public synchronized String userNick(String id) {
		try {
			ResultSet rs = st.executeQuery("select nick from chat_user where id='" + id + "'");
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		ServerOrcl orc = new ServerOrcl("localhost:1521/orcl11", "chat", "chat");
		//		ServerOrcl orc=new ServerOrcl("localhost:1521/orcl11", "scott", "tiger");
		//				orc.selectUserAll();
		//		orc.chackId("boss");
		//		orc.chackNick("boss");
		//		orc.chackId("boss1");
		//		orc.chackNick("boss1");
		//		orc.createUser("asdf", "zxcv", "awer");
		//		System.out.println(":" + orc.userLogin("boss", "boss"));
		//		System.out.println(":" + orc.userLogin("boss1", "boss"));
		//		System.out.println(":" + orc.userLogin("boss", "boss1"));
		//		System.out.println(":" + orc.userNick("asdf"));
		orc.selectUserAll();
		System.out.println("1:" + orc.nickChange("asdf", "asdf"));
		orc.selectUserAll();
		orc.close();
		//		System.out.println(":" + orc.userNick("asdf"));
		//		orc.chackID("boss","test");
		//		orc.chackID("test","boss");
				new Server();
	}

}
