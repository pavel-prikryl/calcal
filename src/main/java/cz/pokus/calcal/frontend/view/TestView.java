package cz.pokus.calcal.frontend.view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import cz.pokus.calcal.backend.jpa.entity.DummyTable;
import cz.pokus.calcal.backend.jpa.service.CalCalService;

@UIScope
@SpringView(name = TestView.VIEW_NAME)
public class TestView extends CustomComponent implements View {
    public static final String VIEW_NAME = "testview";

    private static final long serialVersionUID = -5785594710635916661L;

    @Autowired
    private CalCalService ccService;

    @PostConstruct
    private void init() {
        VerticalLayout root = new VerticalLayout();
        Button bTest = new Button("Test", event -> Notification.show("clicked !"));
        bTest.addStyleName("contrast primary");
        root.addComponent(bTest);

        Button bReadData = new Button("Test load data", event -> testReadData());
        root.addComponent(bReadData);

        Button bWriteData = new Button("Test insert data", event -> testWriteData());
        root.addComponent(bWriteData);

        setSizeFull();
        setCompositionRoot(root);
    }

    private void testReadData() {
        List<DummyTable> data = ccService.dummyTableFindAll();
        Notification.show("data loaded, count=" + data.size());
    }

    private void testWriteData() {
        int count = ccService.genDummyTable();
        Notification.show("data inserted, count=" + count);
    }

}
