package cz.pokus.calcal.frontend.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import cz.pokus.calcal.backend.jpa.entity.EntityFactory;
import cz.pokus.calcal.backend.jpa.entity.Measurement;
import cz.pokus.calcal.backend.jpa.service.CalCalService;

@UIScope
@SpringView(name = CalcView.VIEW_NAME)
public class CalcView extends CustomComponent implements View {
    public static final String VIEW_NAME = "calcview";

    @Autowired
    private CalCalService ccService;
    
    @Autowired
    private EntityFactory entityFactory;
    
    private MeasurementEditor editor;

    @PostConstruct
    private void init() {
        VerticalLayout root = new VerticalLayout();
        Label titulek = new Label("ahoj");
        root.addComponent(titulek);

        editor = new MeasurementEditor(getModel());
        root.addComponent(editor);
        
        this.setCompositionRoot(root);
    }
    
    private Measurement getModel() {
        return entityFactory.getDefaultMeasurement();
    }

}
