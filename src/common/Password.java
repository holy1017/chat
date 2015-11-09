package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Holy
 * 
 *         <pre>
 *         SHA256 :64자리 코드로 변환 
 *         SHA_1 :40자리 코드로 변환 
 *         MD5 :32자리 코드로 변환
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

	private void print(String 반환값) {
		System.out.println(반환값.length() + ":" + 반환값);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Password t = new Password();
		String 암호화대상 = "asdf";
		String 결과값;
		
		결과값 = t.SHA256(암호화대상);
		System.out.println(결과값.length()+":"+결과값);
		
		결과값 = t.SHA_1(암호화대상);
		System.out.println(결과값.length()+":"+결과값);
		
		결과값 = t.MD5(암호화대상);
		System.out.println(결과값.length()+":"+결과값);
		//		t.testAllPrint(대상값);
	}

}
