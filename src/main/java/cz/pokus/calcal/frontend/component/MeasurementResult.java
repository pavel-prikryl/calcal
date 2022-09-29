package cz.pokus.calcal.frontend.component;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.pokus.calcal.backend.jpa.entity.Measurement;
import cz.pokus.calcal.backend.jpa.service.CalCalService;
import cz.pokus.calcal.backend.utils.BodyCalculator;

public class MeasurementResult extends VerticalLayout {
    private Logger logger = LoggerFactory.getLogger(MeasurementResult.class);

    private final CalCalService ccService;

    private Label basal;
    private Label bmi;
    private Label intake;

    private TextField name;
    private Button bSave;

    private Measurement bean;

    private Callback<Measurement> onSaveCallback;

    public MeasurementResult(CalCalService ccService) {
        super();
        this.ccService = ccService;
        this.setCaption("Vypočítané hodnoty");

        basal = new Label();
        bmi = new Label();
        intake = new Label();

        GridLayout grid = new GridLayout(2, 3);
        grid.setSpacing(true);

        grid.addComponent(new Label("Bazální metabolismus"), 0, 0);
        grid.addComponent(basal, 1, 0);

        grid.addComponent(new Label("BMI"), 0, 1);
        grid.addComponent(bmi, 1, 1);

        grid.addComponent(new Label("Doporučený denní příjem"), 0, 2);
        grid.addComponent(intake, 1, 2);

        this.addComponent(grid);

        Component saveControls = buildSaveControls();
        this.addComponent(new Hr());
        this.addComponent(saveControls);

    }

    public Callback<Measurement> getOnSaveCallback() {
        return onSaveCallback;
    }

    public void setOnSaveCallback(Callback<Measurement> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
    }

    private Component buildSaveControls() {
        HorizontalLayout ret = new HorizontalLayout();
        name = new TextField("Název uloženého měření");
        bSave = new Button("Uložit");
        bSave.addClickListener(e -> {
            save();
        });
        bSave.addStyleName("success primary");

        ret.addComponents(name, bSave);
        ret.setComponentAlignment(bSave, Alignment.BOTTOM_RIGHT);
        return ret;
    }

    private void save() {
        
        if(name.getValue()==null || "".equals(name.getValue().trim())) {
            String err = "Zadejte název záznamu pro uložení";
            name.setComponentError(new UserError(err, ContentMode.TEXT, ErrorLevel.ERROR));
            Notification.show(err, Notification.Type.ERROR_MESSAGE);
            return;
        } else {
            name.setComponentError(null);
        }
        
        this.bean.setName(name.getValue().trim());

        List<Measurement> duplicity = ccService.findByNameEquals(bean.getName());
        Integer idOverwrite = null;
        if (!duplicity.isEmpty()) {
            for (Measurement m : duplicity) {
                if (!m.getId().equals(this.bean.getId())) {
                    idOverwrite = m.getId();
                }
            }
        }
        if(idOverwrite!=null) {
            final Integer idNew = idOverwrite;
            String msg = "Záznam se jménem <b>"+bean.getName()+ "</b> již existuje. Přejete si ho přepsat ?";
            YesNoDlg dlg = new YesNoDlg(msg, new YesNoDlg.DlgResponse() {
                @Override
                public void response(boolean b) {
                    if (b) {
                        bean.setId(idNew);
                        saveImpl(bean);
                    }
                }
            });
            UI.getCurrent().addWindow(dlg);

        } else {
            saveImpl(bean);
        }
    }

    private void saveImpl(Measurement m) {
        logger.info("bef save ccService=" + this.ccService);
        this.bean = ccService.save(bean);
        logger.info("saved: " + this.bean);
        name.setValue("");
        bean.setId(null);
        bean.setName(null);

        if (onSaveCallback != null) {
            onSaveCallback.onCallback(bean);
        }
        Notification.show("Záznam uložen", Notification.Type.TRAY_NOTIFICATION);
    }

    public void setMeasurement(Measurement bean) {
        this.bean = bean;

        String sBasal = bean.getBasal() + " KCAL (" + (int)BodyCalculator.kcalToKj( bean.getBasal()) + " KJ)";
        String sBmi = bean.getBmi() + " - "+BodyCalculator.getBmiCaption(bean.getBmi().doubleValue());        
        String sIntake = bean.getIntake() + " KCAL (" + (int)BodyCalculator.kcalToKj(bean.getIntake()) + " KJ)";
        
        basal.setValue(sBasal);
        bmi.setValue(sBmi);
        intake.setValue(sIntake);
    }

}
