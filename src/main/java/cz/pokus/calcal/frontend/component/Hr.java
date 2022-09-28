package cz.pokus.calcal.frontend.component;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class Hr extends Label {
    public Hr(String styleName) {
        this();
        this.setStyleName(styleName);
    }

    public Hr() {
        super("<hr/>", ContentMode.HTML);
        this.setWidth("100%");
    }
}
