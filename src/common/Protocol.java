package common;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @author Holy ������ �� Ŭ������ main �κ� �� �ϴܺκ� �����ϼ���
 */
public class Protocol {

	public static final int	P��������	= 0xf000;	//
	public static final int	P��������	= 0x0ff0;	//
	public static final int	P���±���	= 0x000f;	//
	//static final int �������� = 0xf0000; //

	public static final int	P����_����		= 0x1000;	// ȸ������ �ڵ�
	public static final int	P����_ȸ������	= 0x0010;	// ���̵�|���|�г���|
	public static final int	P����_�α���		= 0x0020;	// ���̵�|���|
	public static final int	P����_�г��Ӻ���	= 0x0030;	// �г���|
	public static final int	P����_ä�ù����	= 0x0040;	// ���̸�|
	public static final int	P����_ä�ù�����	= 0x0050;	// ä�ù��ȣ|
	public static final int	P����_ä�ù渮��Ʈ	= 0x0060;	// ���ȣ|���̸�|����|~

	public static final int	P����_ä��		= 0x2000;	// ä�� ���� �ڵ�
	public static final int	P����_���̸�����	= 0x0050;	// ���ȣ|ä�ó���|
	public static final int	P����_ä�ó���	= 0x0060;	// ���ȣ|ä�ó���|
	public static final int	P����_�����û	= 0x0070;	// ���ȣ|���̵�|
	public static final int	P����_�Ӹ�		= 0x0080;	// ���ȣ|�����̵�|����|
	public static final int	P����_������		= 0x0090;	// 
	public static final int	P����_������		= 0x00a0;	// 

	public static final int P����_�����ڵ� = 0x0100;// ä�ù��ȣ|id|nick|id|nick|~

	public static final int	P����_����	= 0x0001;	// ����|
	public static final int	P����_����	= 0x000c;	// 
	public static final int	P����_����	= 0x000d;	//
	public static final int	P����_�ߺ�	= 0x000e;	//
	public static final int	P����_����	= 0x000f;	// ����|

	//	static final int �ߺ� = 0x10000; //

	public ArrayList<String> ���ҳ��� = new ArrayList<String>();

	public Protocol() {
		// TODO Auto-generated constructor stub
	}

	public Protocol(String text) {
		// TODO Auto-generated constructor stub
		col�޼�������(text);
	}

	public Protocol(String text, int ���Ұ���) {
		// TODO Auto-generated constructor stub
		col�޼�������(text, ���Ұ���);
	}

	/**
	 * "|" ��ȣ�� �������� �ѹ� ���ҽ��ѹ����ϴ�
	 * �� Ŭ������ "���ҳ���"�� �����
	 * @param text
	 * @return ���ҵ� �����. ù°�� �������� ��ȣ�� ó�� ����
	 */
	public ArrayList<String> col�޼�������(String text) {
		return col�޼�������(text, 1);
	}

	/**���� ����Ʈ�� ����� "|" ��ȣ�� �������� ���� ���ҽ��ѹ����ϴ�.
	 * �� Ŭ������ "���ҳ���"�� �����
	 * @param text
	 *            ���������� ���Ե� �޼��� ��ü�� �־��ּ���
	 * @param ����Ƚ��
	 *            "|"�� ��� �ڸ���? �������� �� �������� ���� ���ϴ�
	 * @return ���ҵ� �����. ù°�� �������� ��ȣ�� ó�� ����
	 */
	public ArrayList<String> col�޼�������(String text, int ����Ƚ��) {
		���ҳ��� = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(text.trim());
		���ҳ���.clear();
		for (int i = 0; i < ����Ƚ��; i++) {
			if (st.hasMoreTokens()) {
				���ҳ���.add(st.nextToken("|").trim());
			} else {
				break;
			}
		}
		if (st.hasMoreTokens()) {
			���ҳ���.add(st.nextToken("").trim().substring(1));
		}
		return ���ҳ���;
	}

	/**
	 * ������ Ȯ�ο�
	 */
	public void col���ҳ������() {
		int i = 0;
		for (String string : ���ҳ���) {
			System.out.println(i++ + "\t:" + string);
		}
	};

	/**"���ҳ���"���� ����
	 * @param i
	 * @return
	 */
	public String colGetIndex(int i) {
		return ���ҳ���.get(i);
	}

	/**�ٽ������� ��ȯ ���
	 * @return
	 */
	public String colGetHex() {
		return String.format("%04X", colGet());
	}

	/**"���ҳ���"�� ù�κ��� �������ݸ� ���ڷ� �޾ƿ�
	 * @return ���ڰ� �ƴҽ� -2 ��ȯ
	 */
	public int colGet() {
		try {
			return Integer.parseInt(colGetIndex(0));
		} catch (Exception e) {
			// TODO: handle exception
			return -2;
		}

	}

	/**"���ҳ���"���� ���ҵ� ����
	 * @return
	 */
	public int colSize() {
		return ���ҳ���.size();
	}

	/**���°��� �ڵ����� 0 �Է�
	 * @param ��� 
	 * 0x0~0xf(0~15)
	 * 1:����
	 * 2:ä��
	 * @param ���� 
	 * 0x00~0xff(0~255)
	 * (����)
	 * 1:ȸ������
	 * 2:�α���
	 * 3:�г��Ӻ���
	 * 4:ä�ù����
	 * 5:ä�ù�����
	 * 6:ä�ù渮��Ʈ
	 * 16:�����ڸ���Ʈ
	 * (ä��)
	 * 6:ä�ó���
	 * 7:�����û
	 * 8:�Ӹ�
	 * 9:������
	 * 16:�����ڸ���Ʈ
	 * @param ���� 
	 * 0x0~0xf(0~15)
	 * 0:�Ϲ�
	 * 1:����
	 * 12:����(���� ���̵� ����� ���)
	 * 13:����
	 * 14:�ߺ�
	 * 15:����
	 * @return ���յȰ�
	 * ���� ���� �Ұ����� ���ϰ�� Protocol.P����_����=49152(0xc000) ��ȯ
	 */
	public String col�����(int ���, int ����) {
		
		//		switch (���) {
		//		case 1://����
		//			switch (����) {
		//			case 1://ȸ������
		//				break;
		//			case 2://�α���
		//				break;
		//			case 3://�г��Ӻ���
		//				break;
		//			case 4://ä�ù����
		//				break;
		//			case 5://ä�ù�����
		//				break;
		//			case 6://ä�ù渮��Ʈ
		//				break;
		//			default:
		//				System.out.println("P����_����");
		//				return P����_����+ "|";
		//			}
		//			break;
		//		case 2://ä��
		//			switch (����) {
		//			case 6://ä�ó���
		//				break;
		//			case 7://�����û
		//				break;
		//			case 8://�Ӹ�
		//				break;
		//			case 9://������
		//				break;
		//			case 10://�� ����
		//				break;
		//			case 16://�� ����
		//				break;
		//			default:
		//				System.out.println("P����_����");
		//				return P����_����+ "|";
		//			}
		//			break;
		//		default:
		//			System.out.println("P����_����");
		//			return ((��� | (���� * 0x10) | (���� * 0x1000))+ "|";
		//		}
		//		switch (����) {
		//		case 0://�Ϲ�
		//			break;
		//		case 1://�ó���
		//			break;
		//		case 12://
		//			break;
		//		case 13://
		//			break;
		//		case 14://
		//			break;
		//		case 15://
		//			break;
		//		default:
		//			System.out.println("P����_����");
		//			return ((��� | (���� * 0x10) | (���� * 0x1000))+ "|";
		//		}
		//		//		System.out.println((��� | (���� * 0x10) | (���� * 0x1000)));
		//		//		System.out.println((��� | (���� * 0x10) | (���� * 0x1000))+"|");
		return (((��� * 0x1000) | (���� * 0x10) | (0)) + "|");
	}
	/**
	 * @param ��� 
	 * 0x0~0xf(0~15)
	 * 1:����
	 * 2:ä��
	 * @param ���� 
	 * 0x00~0xff(0~255)
	 * (����)
	 * 1:ȸ������
	 * 2:�α���
	 * 3:�г��Ӻ���
	 * 4:ä�ù����
	 * 5:ä�ù�����
	 * 6:ä�ù渮��Ʈ
	 * 16:�����ڸ���Ʈ
	 * (ä��)
	 * 6:ä�ó���
	 * 7:�����û
	 * 8:�Ӹ�
	 * 9:������
	 * 16:�����ڸ���Ʈ
	 * @param ����
	 * 0x0~0xf(0~15)
	 * 0:�Ϲ�
	 * 1:����
	 * 12:����(���� ���̵� ����� ���)
	 * 13:����
	 * 14:�ߺ�
	 * 15:����
	 * @return ���յȰ�
	 * ���� ���� �Ұ����� ���ϰ�� "Protocol.P����_����" ��ȯ
	 */
	public String col�����(int ���, int ����, int ����) {

		//		switch (���) {
		//		case 1://����
		//			switch (����) {
		//			case 1://ȸ������
		//				break;
		//			case 2://�α���
		//				break;
		//			case 3://�г��Ӻ���
		//				break;
		//			case 4://ä�ù����
		//				break;
		//			case 5://ä�ù�����
		//				break;
		//			case 6://ä�ù渮��Ʈ
		//				break;
		//			default:
		//				System.out.println("P����_����");
		//				return P����_����+ "|";
		//			}
		//			break;
		//		case 2://ä��
		//			switch (����) {
		//			case 6://ä�ó���
		//				break;
		//			case 7://�����û
		//				break;
		//			case 8://�Ӹ�
		//				break;
		//			case 9://������
		//				break;
		//			case 10://�� ����
		//				break;
		//			case 16://�� ����
		//				break;
		//			default:
		//				System.out.println("P����_����");
		//				return P����_����+ "|";
		//			}
		//			break;
		//		default:
		//			System.out.println("P����_����");
		//			return ((��� | (���� * 0x10) | (���� * 0x1000))+ "|";
		//		}
		//		switch (����) {
		//		case 0://�Ϲ�
		//			break;
		//		case 1://�ó���
		//			break;
		//		case 12://
		//			break;
		//		case 13://
		//			break;
		//		case 14://
		//			break;
		//		case 15://
		//			break;
		//		default:
		//			System.out.println("P����_����");
		//			return ((��� | (���� * 0x10) | (���� * 0x1000))+ "|";
		//		}
		//		//		System.out.println((��� | (���� * 0x10) | (���� * 0x1000)));
		//		//		System.out.println((��� | (���� * 0x10) | (���� * 0x1000))+"|");
		return (((��� * 0x1000) | (���� * 0x10) | (����)) + "|");
	}

	/**�Է��� ��
	 * @param �� : �������� ����� �Է�
	 * @param �������� 1:�������� , 2:�������� , 3:���±���
	 * @return ���� ���� �߸� �Է½� -1 ��ȯ. �Ϲ� ��ȯ���� "col�����" �����ϱ�
	 */
	public int col������(int ��, int ��������) {
		int ���;

		switch (��������) {
		case 1:
			//			System.out.println((�� & P��������));
			//			System.out.println((P��������/0xf));
			��� = (�� & P��������) / (0x1000);
			break;
		case 2:
			//			System.out.println((�� & P��������));
			//			System.out.println((P��������/0xf));
			��� = (�� & P��������) / (0x10);
			break;
		case 3:
			��� = (�� & P���±���) / (0x1);
			break;
		default:
			��� = -1;
			break;
		}
		return ���;
	}

	/**���� ���ҵ� "���ҳ���"�� 0�� �ε����� �������� �������ݰ� ����
	 * @param  �������� 1:�������� , 2:�������� , 3:���±���
	 * @return ���� ���� �߸� �Է½�-1,���ڰ� �ƴҰ�� -2 ��ȯ. �Ϲ� ��ȯ���� "col�����" �����ϱ�
	 */
	public int col������(int ��������) {
		int ��;
		if ((�� = colGet()) != -1) {
			return col������(��, ��������);
		} else {
			return -1;
		}
	}

	/**
	 * @param ��
	 * @param ��������
	 * @return
	 */
	public int col����hex(int ��, int ��������) {
		int ���;

		switch (��������) {
		case 1:
			//			System.out.println((�� & P��������));
			//			System.out.println((P��������/0xf));
			��� = (�� & P��������);
			break;
		case 2:
			//			System.out.println((�� & P��������));
			//			System.out.println((P��������/0xf));
			��� = (�� & P��������);
			break;
		case 3:
			��� = (�� & P���±���);
			break;
		default:
			��� = -1;
			break;
		}
		return ���;
	}

	/**���� ���ҵ� "���ҳ���"�� 0�� �ε����� �������� ����
	 * @param  �������� 1:�������� , 2:�������� , 3:���±���
	 * @return
	 */
	public int col����hex(int ��������) {
		int ��;
		if ((�� = colGet()) != -1) {
			return col����hex(��, ��������);
		} else {
			return -1;
		}
	}

	/**�׽�Ʈ��
	 * @param args
	 */
	public static void main(String[] args) {

		Protocol pro = new Protocol();

		int i = 0;

		// ================��ū ��� ����====================
		// Ŭ���̾�Ʈ���Լ� ������
		// Ŭ���̾�Ʈ������������
		String text = (P����_ä�� | P����_ä�ó���) + "|���� �׽�Ʈ|||���� ����|�Ǥ� ��|���������� ����������";

		// ���� �����͸� ��ū ó��.�⺻������ \t\n\r\f ���� ����
		StringTokenizer st = new StringTokenizer(text.trim());

		// "|" ���� ù��° ��ū�� ��������. �װ��� ������ ���ڴ�. �������ݸ� �ɷ����� ���ؼ���.
		int protocol = Integer.parseInt(st.nextToken("|"));
		System.out.println(i++ + ":" + protocol);// �������� �����

		// �������� ���� ������ ����
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + ":" + text);// �޳��� �����

		// ���� ��ū ������. ""���� �ϸ� ���� �������
		text = (String) st.nextElement().toString();
		System.out.println(i++ + ":" + text);// �޳��� �����

		text = st.nextToken("");// ���� ��ū ������. ""���� �ϸ� ���� ���� ���.
		System.out.println(i++ + ":" + text);// �޳��� �����

		text = (P����_ä�� | P����_ä�ó���) + "|���� �׽�Ʈ|||���� ����|�Ǥ� ��|���������� ����������";

		st = new StringTokenizer(text.trim(), "|");
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + "::" + text);// �޳��� �����
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + "::" + text);// �޳��� �����
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + "::" + text);// �޳��� �����
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + "::" + text);// �޳��� �����
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + "::" + text);// �޳��� �����
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + "::" + text);// �޳��� �����
		text = st.nextToken();// ���� ��ū ������. ������ ��� "|" ���� �߶�
		System.out.println(i++ + "::" + text);// �޳��� �����

		///// ============== ���ϰ� ����ϴ� ��� ============
		// Ŭ���̾�Ʈ���� ���۽�
		System.out.println((pro.P����_ä�ù����� | pro.P����_����) + "|���� ����");

		//�������ݸ� ���ý�
		text = (pro.P����_ä�ó��� | pro.P����_����) + "|���� �׽�Ʈ|���� ����|�Ǥ� ��|���������� ����������";
		System.out.println(i++ + ":" + text);
		// ArrayList<String> ���ҳ��� �� ���������
		pro.col�޼�������(text);
		pro.col���ҳ������();// ���� Ȯ�ο�
		System.out.println(i++ + ":" + pro.���ҳ���.get(0));
		System.out.println(i++ + ":" + pro.colGetIndex(0));
		ArrayList<String> �� = pro.���ҳ���;
		System.out.println(i++ + ":" + ��.get(1));

		//
		text = (pro.P����_ä�ó��� | pro.P����_����) + "|���� �׽�Ʈ|���� ����|�Ǥ� ��|���������� ����������";
		System.out.println(i++ + ":" + text);
		// ArrayList<String> ���ҳ��� �� ���������
		pro.col�޼�������(text, 3);
		pro.col���ҳ������();// ���� Ȯ�ο�
		System.out.println(i++ + ":" + pro.���ҳ���.get(0));
		System.out.println(i++ + ":" + pro.colGetIndex(0));
		�� = pro.���ҳ���;
		System.out.println(i++ + ":" + ��.get(1));

	}
}
