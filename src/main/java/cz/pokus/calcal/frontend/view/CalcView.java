package cz.pokus.calcal.frontend.view;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import cz.pokus.calcal.backend.jpa.entity.EntityFactory;
import cz.pokus.calcal.backend.jpa.entity.Measurement;
import cz.pokus.calcal.backend.jpa.service.CalCalService;
import cz.pokus.calcal.backend.utils.BodyCalculator;
import cz.pokus.calcal.frontend.component.Callback;
import cz.pokus.calcal.frontend.component.Hr;
import cz.pokus.calcal.frontend.component.MeasurementEditor;
import cz.pokus.calcal.frontend.component.MeasurementResult;
import cz.pokus.calcal.frontend.component.Spacer;

@UIScope
@SpringView(name = CalcView.VIEW_NAME)
public class CalcView extends CustomComponent implements View {
    public static final String VIEW_NAME = "calcview";

    private Logger logger = LoggerFactory.getLogger(CalcView.class);

    @Autowired
    private CalCalService ccService;

    @Autowired
    private EntityFactory entityFactory;

    private MeasurementEditor meaEditor;
    private MeasurementResult meaResult;
    private Grid<Measurement> dataGrid;
    private TabSheet rootTab;

    @PostConstruct
    private void init() {

        Component editorLayout = buildEditorLayout();
        Component tableLayout = buildTableLayout();
        tableLayout.setSizeFull();

        rootTab = new TabSheet();
        rootTab.addStyleName(ValoTheme.TABSHEET_FRAMED);
        rootTab.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        TabSheet.Tab aTab = rootTab.addTab(editorLayout, "Výpočet");
        aTab.setId("tab1");
        TabSheet.Tab bTab = rootTab.addTab(tableLayout, "Uložené výsledky");
        bTab.setId("tab2");
        rootTab.setSizeFull();
        rootTab.addSelectedTabChangeListener( e-> {
            //not works ?
            //if (e.getTabSheet().getSelectedTab().getId().equals("tab2")) {
                refreshDataGrid();
            
        });
        
        this.setCompositionRoot(rootTab);
        this.setSizeFull();
        
        //to refresh meaResult
        meaEditor.onInputValueChange();
    }

    private Component buildEditorLayout() {
        VerticalLayout root = new VerticalLayout();
        Label titulek = new Label("Kalorická kalkulačka");
        root.addComponent(titulek);
        root.addComponent(new Hr());

        HorizontalLayout twoParts = new HorizontalLayout();
        meaResult = new MeasurementResult(ccService);

        meaEditor = new MeasurementEditor(getModel());
        meaEditor.setCallback(new Callback<Measurement>() {

            @Override
            public void onCallback(Measurement param) {
                param = BodyCalculator.recalcAll(param);
                logger.info("onCallback(): " + param);
                //update render result
                meaResult.setMeasurement(param);
            }

        });

        meaResult.setOnSaveCallback(new Callback<Measurement>() {
            @Override
            public void onCallback(Measurement param) {
                //reset na vychozi hodnoty - matouci pro uzivatele
                //meaEditor.setBean(entityFactory.getDefaultMeasurement());
                Measurement beanCopy = new Measurement(param);
                beanCopy.setId(null);
                beanCopy.setName(null);
                meaEditor.setBean(beanCopy);
            }
        });
        twoParts.addComponent(meaEditor);
        twoParts.addComponent(new Spacer("50px"));
        twoParts.addComponent(meaResult);

        twoParts.setComponentAlignment(meaResult, Alignment.MIDDLE_CENTER);

        // root.addComponent(meaEditor);
        root.addComponent(twoParts);
        Panel ret = new Panel(root);
        ret.addStyleName(ValoTheme.PANEL_BORDERLESS);
        ret.addStyleName(ValoTheme.PANEL_WELL);
        return ret;
    }

    private Component buildTableLayout() {
        VerticalLayout root = new VerticalLayout();
        HorizontalLayout controls  = new HorizontalLayout();
        Button bRefresh = new Button("Obnovit data", e-> {
           refreshDataGrid(); 
           Notification.show("Data obnoveny", Notification.Type.TRAY_NOTIFICATION);
        });
        controls.addComponent(bRefresh);
        
        root.addComponent(controls);
        
        root.addComponent(new Hr());
        
        dataGrid =  new Grid<>(Measurement.class);
        dataGrid.setColumns("id", "name", "sex", "birthYear", "weight", "height", "targetWeight", "fat", "activityWithLevel", "targetBody", "bmi", "basal", "intake");
        dataGrid.setItems(ccService.findAll());
        dataGrid.setSizeFull();
        root.addComponent(dataGrid);
        root.setExpandRatio(dataGrid, 1);

        return root;
    }
    
    private void refreshDataGrid() {
        dataGrid.setItems(ccService.findAll());
        logger.info("data refreshed");
    }
    
    
    private Measurement getModel() {
        return entityFactory.getDefaultMeasurement();
    }

}
