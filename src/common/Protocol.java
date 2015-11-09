package common;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @author Holy 사용법은 이 클래스의 main 부분 의 하단부분 참고하세요
 */
public class Protocol {

	public static final int	P상위구분	= 0xf000;	//
	public static final int	P하위구분	= 0x0ff0;	//
	public static final int	P상태구분	= 0x000f;	//
	//static final int 이유구분 = 0xf0000; //

	public static final int	P상위_서버		= 0x1000;	// 회원관련 코드
	public static final int	P하위_회원가입	= 0x0010;	// 아이디|비번|닉네임|
	public static final int	P하위_로그인		= 0x0020;	// 아이디|비번|
	public static final int	P하위_닉네임변경	= 0x0030;	// 닉네임|
	public static final int	P하위_채팅방생성	= 0x0040;	// 방이름|
	public static final int	P하위_채팅방접속	= 0x0050;	// 채팅방번호|
	public static final int	P하위_채팅방리스트	= 0x0060;	// 방번호|방이름|방장|~

	public static final int	P상위_채팅		= 0x2000;	// 채팅 관련 코드
	public static final int	P하위_방이름변경	= 0x0050;	// 방번호|채팅내용|
	public static final int	P하위_채팅내용	= 0x0060;	// 방번호|채팅내용|
	public static final int	P하위_강퇴요청	= 0x0070;	// 방번호|아이디|
	public static final int	P하위_귓말		= 0x0080;	// 방번호|상대아이디|내용|
	public static final int	P하위_나가기		= 0x0090;	// 
	public static final int	P하위_방종료		= 0x00a0;	// 

	public static final int P하위_접속자들 = 0x0100;// 채팅방번호|id|nick|id|nick|~

	public static final int	P상태_성공	= 0x0001;	// 성공|
	public static final int	P상태_없음	= 0x000c;	// 
	public static final int	P상태_오류	= 0x000d;	//
	public static final int	P상태_중복	= 0x000e;	//
	public static final int	P상태_실패	= 0x000f;	// 실패|

	//	static final int 중복 = 0x10000; //

	public ArrayList<String> 분할내용 = new ArrayList<String>();

	public Protocol() {
		// TODO Auto-generated constructor stub
	}

	public Protocol(String text) {
		// TODO Auto-generated constructor stub
		col메세지분할(text);
	}

	public Protocol(String text, int 분할갯수) {
		// TODO Auto-generated constructor stub
		col메세지분할(text, 분할갯수);
	}

	/**
	 * "|" 기호를 기준으로 한번 분할시켜버립니다
	 * 이 클래스의 "분할내용"에 저장됨
	 * @param text
	 * @return 분할된 내용들. 첫째는 프로토콜 번호로 처리 가능
	 */
	public ArrayList<String> col메세지분할(String text) {
		return col메세지분할(text, 1);
	}

	/**새로 리스트를 만들고 "|" 기호를 기준으로 전부 분할시켜버립니다.
	 * 이 클래스의 "분할내용"에 저장됨
	 * @param text
	 *            프로토콜이 포함된 메세지 전체를 넣어주세요
	 * @param 분할횟수
	 *            "|"로 몇번 자를지? 나머지는 맨 마지막에 전부 들어갑니다
	 * @return 분할된 내용들. 첫째는 프로토콜 번호로 처리 가능
	 */
	public ArrayList<String> col메세지분할(String text, int 분할횟수) {
		분할내용 = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(text.trim());
		분할내용.clear();
		for (int i = 0; i < 분할횟수; i++) {
			if (st.hasMoreTokens()) {
				분할내용.add(st.nextToken("|").trim());
			} else {
				break;
			}
		}
		if (st.hasMoreTokens()) {
			분할내용.add(st.nextToken("").trim().substring(1));
		}
		return 분할내용;
	}

	/**
	 * 데이터 확인용
	 */
	public void col분할내용출력() {
		int i = 0;
		for (String string : 분할내용) {
			System.out.println(i++ + "\t:" + string);
		}
	};

	/**"분할내용"에서 추출
	 * @param i
	 * @return
	 */
	public String colGetIndex(int i) {
		return 분할내용.get(i);
	}

	/**핵스값으로 변환 출력
	 * @return
	 */
	public String colGetHex() {
		return String.format("%04X", colGet());
	}

	/**"분할내용"의 첫부분인 프로토콜만 숫자로 받아옴
	 * @return 숫자가 아닐시 -2 반환
	 */
	public int colGet() {
		try {
			return Integer.parseInt(colGetIndex(0));
		} catch (Exception e) {
			// TODO: handle exception
			return -2;
		}

	}

	/**"분할내용"에서 분할된 갯수
	 * @return
	 */
	public int colSize() {
		return 분할내용.size();
	}

	/**상태값은 자동으로 0 입력
	 * @param 대상 
	 * 0x0~0xf(0~15)
	 * 1:서버
	 * 2:채팅
	 * @param 종류 
	 * 0x00~0xff(0~255)
	 * (서버)
	 * 1:회원가입
	 * 2:로그인
	 * 3:닉네임변경
	 * 4:채팅방생성
	 * 5:채팅방접속
	 * 6:채팅방리스트
	 * 16:접속자리스트
	 * (채팅)
	 * 6:채팅내용
	 * 7:강퇴요청
	 * 8:귓말
	 * 9:나가기
	 * 16:접속자리스트
	 * @param 상태 
	 * 0x0~0xf(0~15)
	 * 0:일반
	 * 1:성공
	 * 12:없음(상대방 아이디가 없드는 등등)
	 * 13:오류
	 * 14:중복
	 * 15:실패
	 * @return 조합된값
	 * 만약 조합 불가능한 것일경우 Protocol.P상태_없음=49152(0xc000) 반환
	 */
	public String col만들기(int 대상, int 종류) {
		
		//		switch (대상) {
		//		case 1://서버
		//			switch (종류) {
		//			case 1://회원가입
		//				break;
		//			case 2://로그인
		//				break;
		//			case 3://닉네임변경
		//				break;
		//			case 4://채팅방생성
		//				break;
		//			case 5://채팅방접속
		//				break;
		//			case 6://채팅방리스트
		//				break;
		//			default:
		//				System.out.println("P상태_없음");
		//				return P상태_없음+ "|";
		//			}
		//			break;
		//		case 2://채팅
		//			switch (종류) {
		//			case 6://채팅내용
		//				break;
		//			case 7://강퇴요청
		//				break;
		//			case 8://귓말
		//				break;
		//			case 9://나가기
		//				break;
		//			case 10://방 종료
		//				break;
		//			case 16://방 종료
		//				break;
		//			default:
		//				System.out.println("P상태_없음");
		//				return P상태_없음+ "|";
		//			}
		//			break;
		//		default:
		//			System.out.println("P상태_없음");
		//			return ((대상 | (종류 * 0x10) | (상태 * 0x1000))+ "|";
		//		}
		//		switch (상태) {
		//		case 0://일반
		//			break;
		//		case 1://팅내용
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
		//			System.out.println("P상태_없음");
		//			return ((대상 | (종류 * 0x10) | (상태 * 0x1000))+ "|";
		//		}
		//		//		System.out.println((대상 | (종류 * 0x10) | (상태 * 0x1000)));
		//		//		System.out.println((대상 | (종류 * 0x10) | (상태 * 0x1000))+"|");
		return (((대상 * 0x1000) | (종류 * 0x10) | (0)) + "|");
	}
	/**
	 * @param 대상 
	 * 0x0~0xf(0~15)
	 * 1:서버
	 * 2:채팅
	 * @param 종류 
	 * 0x00~0xff(0~255)
	 * (서버)
	 * 1:회원가입
	 * 2:로그인
	 * 3:닉네임변경
	 * 4:채팅방생성
	 * 5:채팅방접속
	 * 6:채팅방리스트
	 * 16:접속자리스트
	 * (채팅)
	 * 6:채팅내용
	 * 7:강퇴요청
	 * 8:귓말
	 * 9:나가기
	 * 16:접속자리스트
	 * @param 상태
	 * 0x0~0xf(0~15)
	 * 0:일반
	 * 1:성공
	 * 12:없음(상대방 아이디가 없드는 등등)
	 * 13:오류
	 * 14:중복
	 * 15:실패
	 * @return 조합된값
	 * 만약 조합 불가능한 것일경우 "Protocol.P상태_없음" 반환
	 */
	public String col만들기(int 대상, int 종류, int 상태) {

		//		switch (대상) {
		//		case 1://서버
		//			switch (종류) {
		//			case 1://회원가입
		//				break;
		//			case 2://로그인
		//				break;
		//			case 3://닉네임변경
		//				break;
		//			case 4://채팅방생성
		//				break;
		//			case 5://채팅방접속
		//				break;
		//			case 6://채팅방리스트
		//				break;
		//			default:
		//				System.out.println("P상태_없음");
		//				return P상태_없음+ "|";
		//			}
		//			break;
		//		case 2://채팅
		//			switch (종류) {
		//			case 6://채팅내용
		//				break;
		//			case 7://강퇴요청
		//				break;
		//			case 8://귓말
		//				break;
		//			case 9://나가기
		//				break;
		//			case 10://방 종료
		//				break;
		//			case 16://방 종료
		//				break;
		//			default:
		//				System.out.println("P상태_없음");
		//				return P상태_없음+ "|";
		//			}
		//			break;
		//		default:
		//			System.out.println("P상태_없음");
		//			return ((대상 | (종류 * 0x10) | (상태 * 0x1000))+ "|";
		//		}
		//		switch (상태) {
		//		case 0://일반
		//			break;
		//		case 1://팅내용
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
		//			System.out.println("P상태_없음");
		//			return ((대상 | (종류 * 0x10) | (상태 * 0x1000))+ "|";
		//		}
		//		//		System.out.println((대상 | (종류 * 0x10) | (상태 * 0x1000)));
		//		//		System.out.println((대상 | (종류 * 0x10) | (상태 * 0x1000))+"|");
		return (((대상 * 0x1000) | (종류 * 0x10) | (상태)) + "|");
	}

	/**입력한 값
	 * @param 값 : 프로토콜 대상값을 입력
	 * @param 구분종류 1:상위구분 , 2:하위구분 , 3:상태구분
	 * @return 구분 종류 잘못 입력시 -1 반환. 일반 반환값은 "col만들기" 참조하기
	 */
	public int col값추출(int 값, int 구분종류) {
		int 결과;

		switch (구분종류) {
		case 1:
			//			System.out.println((값 & P상위구분));
			//			System.out.println((P하위구분/0xf));
			결과 = (값 & P상위구분) / (0x1000);
			break;
		case 2:
			//			System.out.println((값 & P하위구분));
			//			System.out.println((P하위구분/0xf));
			결과 = (값 & P하위구분) / (0x10);
			break;
		case 3:
			결과 = (값 & P상태구분) / (0x1);
			break;
		default:
			결과 = -1;
			break;
		}
		return 결과;
	}

	/**현재 분할된 "분할내용"의 0번 인덱스를 기준으로 프로토콜값 추출
	 * @param  구분종류 1:상위구분 , 2:하위구분 , 3:상태구분
	 * @return 구분 종류 잘못 입력시-1,숫자가 아닐경우 -2 반환. 일반 반환값은 "col만들기" 참조하기
	 */
	public int col값추출(int 구분종류) {
		int 값;
		if ((값 = colGet()) != -1) {
			return col값추출(값, 구분종류);
		} else {
			return -1;
		}
	}

	/**
	 * @param 값
	 * @param 구분종류
	 * @return
	 */
	public int col판정hex(int 값, int 구분종류) {
		int 결과;

		switch (구분종류) {
		case 1:
			//			System.out.println((값 & P상위구분));
			//			System.out.println((P하위구분/0xf));
			결과 = (값 & P상위구분);
			break;
		case 2:
			//			System.out.println((값 & P하위구분));
			//			System.out.println((P하위구분/0xf));
			결과 = (값 & P하위구분);
			break;
		case 3:
			결과 = (값 & P상태구분);
			break;
		default:
			결과 = -1;
			break;
		}
		return 결과;
	}

	/**현재 분할된 "분할내용"의 0번 인덱스를 기준으로 판정
	 * @param  구분종류 1:상위구분 , 2:하위구분 , 3:상태구분
	 * @return
	 */
	public int col판정hex(int 구분종류) {
		int 값;
		if ((값 = colGet()) != -1) {
			return col판정hex(값, 구분종류);
		} else {
			return -1;
		}
	}

	/**테스트용
	 * @param args
	 */
	public static void main(String[] args) {

		Protocol pro = new Protocol();

		int i = 0;

		// ================토큰 사용 예제====================
		// 클라이언트에게서 받을시
		// 클라이언트에서받은내용
		String text = (P상위_채팅 | P하위_채팅내용) + "|내용 테스트|||ㅅㄷ ㄴㅅ|ㅗㅇ 놐|ㅎㅁㅎㅁㅎ ㄶㅁㅁㅎㅁ";

		// 받은 데이터를 토큰 처리.기본적으로 \t\n\r\f 으로 분할
		StringTokenizer st = new StringTokenizer(text.trim());

		// "|" 으로 첫번째 토큰을 가려낸다. 그것은 무조건 숫자다. 프로토콜만 걸러내기 위해서임.
		int protocol = Integer.parseInt(st.nextToken("|"));
		System.out.println(i++ + ":" + protocol);// 프로토콜 결과값

		// 프로토콜 뒤의 내용을 저장
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + ":" + text);// 뒷내용 결과값

		// 다음 토큰 꺼내기. ""으로 하면 뒤의 전부출력
		text = (String) st.nextElement().toString();
		System.out.println(i++ + ":" + text);// 뒷내용 결과값

		text = st.nextToken("");// 다음 토큰 꺼내기. ""으로 하면 뒤의 전부 출력.
		System.out.println(i++ + ":" + text);// 뒷내용 결과값

		text = (P상위_채팅 | P하위_채팅내용) + "|내용 테스트|||ㅅㄷ ㄴㅅ|ㅗㅇ 놐|ㅎㅁㅎㅁㅎ ㄶㅁㅁㅎㅁ";

		st = new StringTokenizer(text.trim(), "|");
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + "::" + text);// 뒷내용 결과값
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + "::" + text);// 뒷내용 결과값
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + "::" + text);// 뒷내용 결과값
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + "::" + text);// 뒷내용 결과값
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + "::" + text);// 뒷내용 결과값
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + "::" + text);// 뒷내용 결과값
		text = st.nextToken();// 다음 토큰 꺼내기. 이전에 줬던 "|" 으로 잘라냄
		System.out.println(i++ + "::" + text);// 뒷내용 결과값

		///// ============== 편하게 사용하는 방법 ============
		// 클라이언트에게 전송시
		System.out.println((pro.P하위_채팅방접속 | pro.P상태_실패) + "|적을 내용");

		//프로토콜만 빼올시
		text = (pro.P하위_채팅내용 | pro.P상태_성공) + "|내용 테스트|ㅅㄷ ㄴㅅ|ㅗㅇ 놐|ㅎㅁㅎㅁㅎ ㄶㅁㅁㅎㅁ";
		System.out.println(i++ + ":" + text);
		// ArrayList<String> 분할내용 에 저장되있음
		pro.col메세지분할(text);
		pro.col분할내용출력();// 내용 확인용
		System.out.println(i++ + ":" + pro.분할내용.get(0));
		System.out.println(i++ + ":" + pro.colGetIndex(0));
		ArrayList<String> ㅎ = pro.분할내용;
		System.out.println(i++ + ":" + ㅎ.get(1));

		//
		text = (pro.P하위_채팅내용 | pro.P상태_성공) + "|내용 테스트|ㅅㄷ ㄴㅅ|ㅗㅇ 놐|ㅎㅁㅎㅁㅎ ㄶㅁㅁㅎㅁ";
		System.out.println(i++ + ":" + text);
		// ArrayList<String> 분할내용 에 저장되있음
		pro.col메세지분할(text, 3);
		pro.col분할내용출력();// 내용 확인용
		System.out.println(i++ + ":" + pro.분할내용.get(0));
		System.out.println(i++ + ":" + pro.colGetIndex(0));
		ㅎ = pro.분할내용;
		System.out.println(i++ + ":" + ㅎ.get(1));

	}
}
