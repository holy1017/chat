package common;

// import java.awt.BorderLayout;
import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

public class StyledDoc {

	public final static String	nomal	= "nomal";
	public final static String	red		= "red";
	public final static String	blue	= "blue";
	public final static String	green	= "green";
	public final static String	gray	= "gray";
	public final static String	orange	= "orange";
	public final static String	pink	= "pink";
	public final static String	yellow	= "yellow";
	public final static String	white	= "white";
	public final static String	magenta	= "magenta";

	private StyleContext			styles;	//스타일 맵 저장되있음
	private DefaultStyledDocument	ddoc;

	private AbstractDocument				adoc;
	private Map<String, SimpleAttributeSet>	atts;	//= new HashMap<String, SimpleAttributeSet>();

	private JTextPane jTextPane;

	//	private Map<String, Style>		styles;
	{
		styles = new StyleContext();
		atts = new HashMap<String, SimpleAttributeSet>();
		setting(0);
	}

	public StyledDoc(StyledDocument styledDocument) {
		//		sd = new StyledDoc((AbstractDocument) jTextPane1.getStyledDocument());
		adoc = (AbstractDocument) styledDocument;
		//		setting(0);
	}

	public StyledDoc(JTextPane jTextPane) {
		//		sd = new StyledDoc((AbstractDocument) jTextPane1.getStyledDocument());
		this.jTextPane = jTextPane;
		adoc = (AbstractDocument) jTextPane.getStyledDocument();
		//		jTextPane.setEditorKit(new WrapEditorKit());
		//		setting(0);
	}

	public StyledDoc(AbstractDocument doc) {
		adoc = doc;
		//		setting(0);
	}

	public StyledDoc() {
		// TODO Auto-generated constructor stub
		//		styles = new StyleContext();
		ddoc = new DefaultStyledDocument(styles);
		//		styles = new HashMap<String, Style>();
		//		setting(0);
	}

	public StyledDoc(int fontSize) {
		// TODO Auto-generated constructor stub
		//		styles = new StyleContext();
		ddoc = new DefaultStyledDocument(styles);
		//		styles = new HashMap<String, Style>();
		setting(fontSize);
	}

	private void setting(int fontSize) {
		// TODO Auto-generated method stub
		//		doc.

		addStyle("nomal", null, fontSize, false, null);
		addStyle("red", Color.red, 0, false, "nomal");
		addStyle("blue", Color.BLUE, 0, false, "nomal");
		addStyle("green", Color.green, 0, false, "nomal");
		addStyle("gray", Color.gray, 0, false, "nomal");
		addStyle("orange", Color.orange, 0, false, "nomal");
		addStyle("pink", Color.pink, 0, false, "nomal");
		addStyle("yellow", Color.yellow, 0, false, "nomal");
		addStyle("white", Color.white, 0, false, "nomal");
		addStyle("magenta", Color.magenta, 0, false, "nomal");

		addAttSet("nomal", null, fontSize, false, null);
		addAttSet("red", Color.red, 0, false, "nomal");
		addAttSet("blue", Color.BLUE, 0, false, "nomal");
		addAttSet("green", Color.green, 0, false, "nomal");
		addAttSet("gray", Color.gray, 0, false, "nomal");
		addAttSet("orange", Color.orange, 0, false, "nomal");
		addAttSet("pink", Color.pink, 0, false, "nomal");
		addAttSet("yellow", Color.yellow, 0, false, "nomal");
		addAttSet("white", Color.white, 0, false, "nomal");
		addAttSet("magenta", Color.magenta, 0, false, "nomal");
	}

	/**
	 * @param name 저장할 스타일 이름
	 * @param ForeGroundColor 색. Color 클래스 이용.
	 * @param FontSize
	 * @param Bold 굵게
	 * @param parent 부모스타일 복사해서 수정후 추가
	 */
	public void addAttSet(String name, Color ForeGroundColor, int FontSize, boolean Bold, String parent) {

		SimpleAttributeSet att;
		if (parent == null) {
			att = new SimpleAttributeSet();
		} else {
			att = new SimpleAttributeSet(atts.get(parent));
		}
		if (ForeGroundColor != null)
			StyleConstants.setForeground(att, ForeGroundColor);
		if (FontSize > 0)
			StyleConstants.setFontSize(att, FontSize);
		//			if (Bold)
		StyleConstants.setBold(att, Bold);
		atts.put(name, att);
	}

	public void addStyle(String name, Object ForeGroundColor, int FontSize, boolean Bold, String parent) {
		//		if (!styles.containsKey(key)) {
		Style style;

		if (parent != null) {
			style = styles.addStyle(name, styles.getStyle(parent));
		} else {
			style = styles.addStyle(name, null);
			//			style.addAttribute(StyleConstants., ForeGroundColor);
		}

		if (ForeGroundColor != null)
			style.addAttribute(StyleConstants.Foreground, ForeGroundColor);
		if (FontSize > 0)
			style.addAttribute(StyleConstants.FontSize, new Integer(FontSize));
		//			if (Bold)
		style.addAttribute(StyleConstants.Bold, new Boolean(Bold));
		//	    style.addAttribute(StyleConstants.FontFamily, "serif");
		//		styles.put(key, style);
		//		}
	}

	/**
	 * @param offs 추가할 위치
	 * @param str 내용
	 * @param styleName 스타일이름
	 */
	public void addDDoc(int offs, String str, String styleName) {

		try {
			ddoc.insertString(offs, str, styles.getStyle(styleName));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
		} //doc.getLength()

	}

	/**
	 * @param str 내용
	 * @param styleName 스타일이름
	 */
	public void addDDocEnd(String str, String styleName) {
		//		System.out.println("addDDocEnd1:" + str + ":" + styles.getStyle(styleName));
		addDDoc(ddoc.getLength(), str, styleName);
	}

	public void addDDocEnd(String str) {
		//		System.out.println("addDDocEnd2:" + str);
		addDDoc(ddoc.getLength(), str, null);
	}

	public void addADoc(int offs, String str, String styleName) {

		try {
			adoc.insertString(offs, str, atts.get(styleName));
			//			adoc.
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
		}
	}

	public void addADoc(int offs, String str) {

		try {
			adoc.insertString(offs, str, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
		}
	}

	public void addADocEnd(String str, String styleName) {
		//		System.out.println("addADocEnd3:" + str + ":" + atts.get(styleName));
		addADoc(adoc.getLength(), str, styleName);
	}

	public void addADocEnd(String str) {
		//		System.out.println("addADocEnd4:" + str);
		addADoc(adoc.getLength(), str);
	}

	/**
	 * @param str
	 * @param style
	 */
	public void addTextPaneEnd(String str, String style) {
		AbstractDocument doc = new DefaultStyledDocument();
		try {
			doc.insertString(0, str, atts.get(style));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//				jTextPane.
	}

	/**
	 * @param name
	 * @return 없을시 null반환
	 */
	public Style getStyle(String name) {
		return styles.getStyle(name);
	}

	/** 기존에 저장된 스타일의 요소를 변경
	 * @param name 변경할 클래스 이름
	 * @param StyleConstants 변경할 요소. StyleConstants 클래스 참조
	 * @param value 변경할 값
	 * @return
	 */
	public Style ChangeStyle(String name, Object StyleConstants, Object value) {
		Style style = styles.getStyle(name);
		if (style != null) {
			style.addAttribute(StyleConstants, value);
		}
		return style;
	}

	public DefaultStyledDocument getDDoc() {
		return ddoc;
	}

	public AbstractDocument getADoc() {
		return adoc;
	}

	public void setADoc(AbstractDocument doc) {
		adoc = doc;
	}
	//	public void StyleList() {
	//		styles.getEmptySet();
	//	}

	public static void main(String[] args) {

		//		StyledDoc doc = new StyledDoc();

		JFrame jf = new JFrame("test");
		jf.setLayout(new BorderLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane jScrollPane1 = new JScrollPane();
		jf.add(jScrollPane1, BorderLayout.CENTER);

		//		JTextPane jTextPane1 = new JTextPane(doc.getDDoc());
		JTextPane jTextPane1 = new JTextPane();
		jTextPane1.setBackground(Color.black);
		jTextPane1.setEditable(false);
		//		jTextPane1.s
		jScrollPane1.setViewportView(jTextPane1);

		StyledDoc doc = new StyledDoc(jTextPane1);

		jf.setSize(600, 400);
		jf.setVisible(true);

		doc.addDDocEnd("test1\n", "red");
		System.out.println(1);
		doc.addDDocEnd("test2", "green");
		System.out.println(2);
		doc.addDDocEnd("test3", "blue");
		System.out.println(3);
		doc.addStyle("green12", null, 22, true, "green");//부모 스타일을 카피 수정후 적용
		System.out.println(4);
		doc.addDDocEnd("test4", "green12");
		System.out.println(5);
		doc.addDDocEnd("test5", "green");
		System.out.println(6);
		doc.addDDocEnd("test6", "green3");//없는 스타일
		System.out.println(7);
		doc.addDDocEnd("test7", "nomal");
		System.out.println(8);
		doc.addStyle("green12", null, 42, true, "green");//기존스타일 수정. 이미 출력된건 수정 안됨.
		System.out.println(9);
		doc.addDDocEnd("test4", "green12");
		System.out.println(10);
		System.out.println(doc.getStyle("test"));
		doc.ChangeStyle("blue", StyleConstants.Foreground, Color.white);//기존 스타일 변경
		System.out.println(11);
		doc.addDDocEnd("test11", "blue");
		doc.addDDocEnd("test1dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd1",
				"green12");

		//		try {
		//			doc.getDoc().replace(10, 10, "text", null);
		////			doc.getDoc().
		//		} catch (BadLocationException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

	}
}
