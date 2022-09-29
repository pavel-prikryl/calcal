package cz.pokus.calcal.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import cz.pokus.calcal.frontend.view.CalcView;

//@Theme("valo")
@Theme("tests-valo-dark")
@SpringUI
public class MainUI extends UI {

    @Autowired
    private Environment env;
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    
        setupNavigator();
        getPage().setTitle("Kalorická kalkulačka [" + env.getProperty("env.identifier") + "]");
        //getNavigator().navigateTo(TestView.VIEW_NAME);
        getNavigator().navigateTo(CalcView.VIEW_NAME);
    }

    
    @Autowired
    SpringViewProvider viewProvider;

    private void setupNavigator() {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        this.setNavigator(navigator);
    }

    
}
