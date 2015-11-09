package common;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class WrapTestApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new WrapTestApp();
	}

	public WrapTestApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 200);
		getContentPane().setLayout(new BorderLayout());
		JTextPane jtp = new JTextPane();
		jtp.setEditorKit(new WrapEditorKit());
		JScrollPane jsp = new JScrollPane(jtp);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(jsp, BorderLayout.CENTER);
		jtp.setText("ExampleOfTheWrapLongWordWithoutSpaces");
		setVisible(true);
	}

}

class WrapEditorKit extends StyledEditorKit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ViewFactory defaultFactory = new WrapColumnFactory();

	public ViewFactory getViewFactory() {
		System.out.println("WrapEditorKit");
		return defaultFactory;
	}

}

class WrapLabelView extends LabelView {
	public WrapLabelView(Element elem) {
		super(elem);
	}

	public float getMinimumSpan(int axis) {
		switch (axis) {
		case View.X_AXIS:
			return 0;
		case View.Y_AXIS:
			return super.getMinimumSpan(axis);
		default:
			throw new IllegalArgumentException("Invalid axis: " + axis);
		}
	}

}

class WrapColumnFactory implements ViewFactory {
	public View create(Element elem) {
		String kind = elem.getName();
		System.out.println("kind:"+kind);
		if (kind != null) {
			if (kind.equals(AbstractDocument.ContentElementName)) {
				return new WrapLabelView(elem);
			} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
				return new ParagraphView(elem);
			} else if (kind.equals(AbstractDocument.SectionElementName)) {
				return new BoxView(elem, View.Y_AXIS);
			} else if (kind.equals(StyleConstants.ComponentElementName)) {
				return new ComponentView(elem);
			} else if (kind.equals(StyleConstants.IconElementName)) {
				return new IconView(elem);
			}
		}

		// default to text display
		return new LabelView(elem);
	}
}