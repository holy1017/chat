package common;

import java.util.regex.Pattern;

public class StringChack {

	/**�̸��� üũ
	 * @param email
	 * @return ��ġ�Ұ�� Ʈ��
	 */
	public boolean sc_emailChack(String email) {
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		return Pattern.matches(regex, email);
	}

	/** ��ɾ� üũ"��¼��/","��¼��/ ��¼��"
	 * @param order
	 * @return ��ġ�Ұ�� Ʈ��
	 */
	public boolean sc_orderChack(String order) {
		String regex = "((^\\S+\\/$))|(^\\S+\\/\\s.*)";// ^\\D+\\/\\s*
		return Pattern.matches(regex, order);
	}

	/**������ üũ
	 * @param i
	 * @return ��ġ�Ұ�� Ʈ��
	 */
	public boolean sc_intChack(String i) {
		String regex = "^[0-9]+$";// ^\\D+\\/\\s*
		return Pattern.matches(regex, i);
	}

	/** �Ǽ��� üũ
	 * @param i
	 * @return ��ġ�Ұ�� Ʈ��
	 */
	public boolean sc_flotChack(String i) {
		String regex = "^[+-]?\\d*(\\.?\\d*)$";// ^\\D+\\/\\s*
		return Pattern.matches(regex, i);
	}

	/**id üũ. �������� ����,�������ڸ� ����,4~20�ڸ�
	 * @param id
	 * @return ��ġ�Ұ�� Ʈ��
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
