package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Holy
 * 
 *         <pre>
 *         SHA256 :64�ڸ� �ڵ�� ��ȯ 
 *         SHA_1 :40�ڸ� �ڵ�� ��ȯ 
 *         MD5 :32�ڸ� �ڵ�� ��ȯ
 *         </pre>
 */
public class Password {

	public String SHA256(String str) {
		return pw(str, "SHA-256");
	}

	public String SHA_1(String str) {
		return pw(str, "SHA-1");
	}

	public String MD5(String str) {
		return pw(str, "MD5");
	}

	private String pw(String str, String type) {
		String SHA = "";
		try {
			MessageDigest sh = MessageDigest.getInstance(type);
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}
		return SHA;
	}

	void testAllPrint(String pw) {
		print(SHA256(pw));
		print(SHA_1(pw));
		print(MD5(pw));
	}

	private void print(String ��ȯ��) {
		System.out.println(��ȯ��.length() + ":" + ��ȯ��);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Password t = new Password();
		String ��ȣȭ��� = "asdf";
		String �����;
		
		����� = t.SHA256(��ȣȭ���);
		System.out.println(�����.length()+":"+�����);
		
		����� = t.SHA_1(��ȣȭ���);
		System.out.println(�����.length()+":"+�����);
		
		����� = t.MD5(��ȣȭ���);
		System.out.println(�����.length()+":"+�����);
		//		t.testAllPrint(���);
	}

}
