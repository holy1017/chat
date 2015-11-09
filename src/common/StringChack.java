package common;

import java.util.regex.Pattern;

public class StringChack {

	/**이메일 체크
	 * @param email
	 * @return 일치할경우 트루
	 */
	public boolean sc_emailChack(String email) {
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		return Pattern.matches(regex, email);
	}

	/** 명령어 체크"어쩌구/","어쩌구/ 저쩌구"
	 * @param order
	 * @return 일치할경우 트루
	 */
	public boolean sc_orderChack(String order) {
		String regex = "((^\\S+\\/$))|(^\\S+\\/\\s.*)";// ^\\D+\\/\\s*
		return Pattern.matches(regex, order);
	}

	/**정수형 체크
	 * @param i
	 * @return 일치할경우 트루
	 */
	public boolean sc_intChack(String i) {
		String regex = "^[0-9]+$";// ^\\D+\\/\\s*
		return Pattern.matches(regex, i);
	}

	/** 실수형 체크
	 * @param i
	 * @return 일치할경우 트루
	 */
	public boolean sc_flotChack(String i) {
		String regex = "^[+-]?\\d*(\\.?\\d*)$";// ^\\D+\\/\\s*
		return Pattern.matches(regex, i);
	}

	/**id 체크. 영문으로 시작,영문숫자만 가능,4~20자리
	 * @param id
	 * @return 일치할경우 트루
	 */
	public boolean sc_idChack(String id) {
		String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{3,20}$";
		return Pattern.matches(regex, id);
	}

	public static void main(String[] args) {

		StringChack t = new StringChack();

		System.out.println("1" + t.sc_emailChack("adfaf@gafg.fagaeg"));
		System.out.println("2" + t.sc_emailChack("adfaf@gafgfagaeg"));
		System.out.println("3" + t.sc_emailChack("adfafgafg.fagaeg"));

		System.out.println("1" + t.sc_orderChack("adfafgafg.fagaeg"));
		System.out.println("2" + t.sc_orderChack("adfaf/ gafg.fagaeg"));
		System.out.println("3" + t.sc_orderChack("adfa/gafg.fagaeg"));
		System.out.println("4" + t.sc_orderChack("adfa/"));

	}
}
