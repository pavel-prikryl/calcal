package cz.pokus.calcal.frontend.component;


import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class Spacer extends Label {

	public Spacer(String width) {
		super("&nbsp;", ContentMode.HTML);
		setWidth(width);
	}

	public Spacer() {
		this(1);
	}

	public Spacer(int count) {
		super(buildSpaces(count), ContentMode.HTML);
	}

	private static String buildSpaces(int count) {
		StringBuilder ret = new StringBuilder();
		for(int i=0; i<count; i++) {
			ret.append("&nbsp;");
		}
		return ret.toString();
	}

}
