package cz.pokus.calcal.frontend.component;

import java.util.logging.Logger;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class YesNoDlg extends Window {
    private final transient Logger LOG = Logger.getLogger(getClass().getSimpleName());

    public YesNoDlg(final String msg, final DlgResponse response) {
        this(null, msg, response);
    }

    public YesNoDlg(final String msg, final DlgResponse response, final boolean modal) {
        this(null, msg, response, modal);
    }

    public Button bYes;
    public Button bNo;

    private Boolean result = null;

    public YesNoDlg(final String caption, final String msg, final DlgResponse response) {
        this(caption, msg, response, true);
    }

    public YesNoDlg(final String caption, final String msg, final DlgResponse response, final boolean modal) {
        if (caption == null) {
            this.setCaption("Potvrzení");
        } else if (!"".equals(caption)) {
            this.setCaption(caption);
        }

        setModal(modal);
        VerticalLayout root = new VerticalLayout();
        setContent(root);
        root.setMargin(true);
        root.setSpacing(true);

        if (msg != null && !"".equals(msg)) {
            root.addComponent(new Label(msg, ContentMode.HTML));
            root.addComponent(new Hr());
        }

        bYes = new Button("Ano");
        bYes.setClickShortcut(KeyCode.ENTER, null);
        bYes.setWidth("80px");
        bYes.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (response != null) {
                    result = new Boolean(true);
                    response.response(true);
                }
                closeImpl(true);
            }
        });

        bNo = new Button("Ne");
        bNo.setClickShortcut(KeyCode.ESCAPE, null);
        bNo.setWidth("80px");
        bNo.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (response != null) {
                    result = new Boolean(false);
                    response.response(false);
                }
                closeImpl(false);
            }
        });

        HorizontalLayout bottom = new HorizontalLayout();
        bottom.setSpacing(true);
        bottom.setWidth("100%");
        bottom.addComponents(bYes, bNo);
        bottom.setComponentAlignment(bYes, Alignment.MIDDLE_CENTER);
        bottom.setComponentAlignment(bNo, Alignment.MIDDLE_CENTER);
        root.addComponent(bottom);

        // zavreni formulare krizkem
        if (response != null) {
            this.addCloseListener(new CloseListener() {

                @Override
                public void windowClose(CloseEvent e) {
                    // bez result == null by se zavolalo po kazdem zavreni formulare (tzn. i
                    // tlacitkem)
                    if (result == null) {
                        response.response(false);
                    }
                }

            });
        }

        bYes.focus();
    }

    protected void closeImpl(boolean delay) {
        if (isModal() || !delay) {
            close();
        }
        // obcasny problem u downloadu, kde je navazano tlacitko Yes na
        // FileDownloader
        // pekna prasarna :-)

        // [A]
        // ale zda se, ze je to OK ?
        // ale v MOZILLE to nechodi :-(, jinak OK
        // setVisible(false);

        // [B]
        // holt si to budou muset zavirat rucne
        bYes.setEnabled(false);
        bNo.setCaption("Zavřít dialog");

    }

    public Button getButtonYes() {
        return bYes;
    }

    public Button getButtonNo() {
        return bNo;
    }

    public static interface DlgResponse {
        public void response(boolean b);
    }

}
