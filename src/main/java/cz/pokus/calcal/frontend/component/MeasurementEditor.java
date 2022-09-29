package cz.pokus.calcal.frontend.component;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSingleSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.Slider;
import com.vaadin.ui.themes.ValoTheme;

import cz.pokus.calcal.backend.converter.DoubleToIntegerConverter;
import cz.pokus.calcal.backend.jpa.entity.Measurement;
import cz.pokus.calcal.backend.jpa.enums.BodyActivity;
import cz.pokus.calcal.backend.jpa.enums.BodyTarget;

public class MeasurementEditor extends FormLayout {

    private static final long serialVersionUID = 8942983438291306710L;

    private Logger logger = LoggerFactory.getLogger(MeasurementEditor.class);

    private static final String W = "350px";

    private Measurement bean;
    private Callback<Measurement> callback;

    private BeanValidationBinder<Measurement> binder;

    private RadioButtonGroup<Boolean> male;
    private Slider birthYear;
    private Slider weight;
    private Slider height;
    private Slider targetWeight;
    private RadioButtonGroup<Integer> fat;

    private ComboBox<BodyActivity> activity;
    private ComboBox<BodyTarget> targetBody;

    public MeasurementEditor(Measurement bean) {
        super();
        this.bean = bean;
        buildGui();
        binder = createBinder(bean);
    }

    public Measurement getBean() {
        return bean;
    }

    public void setBean(Measurement bean) {
        this.bean = bean;
        binder.readBean(bean);
    }

    public void onInputValueChange() {
        if (callback != null) {
            try {
                binder.writeBean(bean);
                BodyTarget bTarget = bean.computeBodyTarget();
                if(bTarget != bean.getTargetBody()) {
                    bean.setTargetBody(bTarget);
                    targetBody.setValue(bTarget);
                }
            } catch (ValidationException e) {
                Notification.show("Chyba validace: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
            callback.onCallback(bean);
        }
    }

    public Callback<Measurement> getCallback() {
        return callback;
    }

    public void setCallback(Callback<Measurement> callback) {
        this.callback = callback;
    }

    @Override
    public void addComponent(Component c) {
        if (c instanceof AbstractField) {
            AbstractField f = (AbstractField) c;
            f.addValueChangeListener(e -> {
                logger.info("value changed: " + e.getValue().toString());
                onInputValueChange();
            });
        } else if (c instanceof ComboBox) {
            ComboBox cbx = (ComboBox) c;
            cbx.addValueChangeListener(e -> {
                logger.info("value changed: " + e.getValue().toString());
                onInputValueChange();
            });
        } else if (c instanceof AbstractSingleSelect) {
            AbstractSingleSelect a = (AbstractSingleSelect) c;
            a.addValueChangeListener(e -> {
                logger.info("value changed: " + e.getValue().toString());
                onInputValueChange();
            });
        }
        super.addComponent(c);
    }

    private void buildGui() {
        male = new RadioButtonGroup<>("Pohlaví", Arrays.asList(Boolean.FALSE, Boolean.TRUE));
        male.setItemCaptionGenerator(new ItemCaptionGenerator<Boolean>() {
            @Override
            public String apply(Boolean item) {
                return item ? "Mužské" : "Ženské";
            }
        });
        male.addValueChangeListener(e -> {
            male.setCaption("Pohlaví " + (e.getValue() ? "Mužské" : "Ženské"));
        });
        male.setStyleName("horizontal");
        male.setWidth(W);
        this.addComponent(male);

        birthYear = new Slider("Rok narození", 1923, 2022);
        addSlider(birthYear, "");
        weight = new Slider("Váha", 20, 250);
        addSlider(weight, "Kg");
        height = new Slider("Výška", 80, 220);
        addSlider(height, "cm");
        targetWeight = new Slider("Cílová váha", 20, 250);
        addSlider(targetWeight, "Kg");

        fat = new RadioButtonGroup<>("Tělesný tuk", Measurement.FAT_VALUES);
        fat.addValueChangeListener(e -> {
            fat.setCaption("Tělesný tuk " + e.getValue() + " %");
        });
        fat.setStyleName("horizontal");
        fat.setWidth(W);
        this.addComponent(fat);

        activity = new ComboBox<>("Aktivita", Arrays.asList(BodyActivity.values()));
        activity.setItemCaptionGenerator(new ItemCaptionGenerator<BodyActivity>() {
            @Override
            public String apply(BodyActivity item) {
                return item.getDesc();
            }
        });
        activity.setEmptySelectionAllowed(false);
        activity.setWidth(W);
        activity.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
        this.addComponent(activity);

        targetBody = new ComboBox<>("Cíl", Arrays.asList(BodyTarget.values()));
        targetBody.setItemCaptionGenerator(new ItemCaptionGenerator<BodyTarget>() {
            @Override
            public String apply(BodyTarget item) {
                return item.getDesc();
            }
        });
        targetBody.setEmptySelectionAllowed(false);
        targetBody.setWidth(W);
        targetBody.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
        targetBody.setReadOnly(true);
        this.addComponent(targetBody);

    }

    private BeanValidationBinder<Measurement> createBinder(Measurement bean) {
        BeanValidationBinder<Measurement> ret = new BeanValidationBinder<>(Measurement.class);

        // ret.forField(height).withConverter(new StringToIntegerConverter("Please enter
        // a number")).bind("height");
        ret.bind(male, "male");
        ret.forField(birthYear).withConverter(new DoubleToIntegerConverter()).bind("birthYear");
        ret.forField(weight).withConverter(new DoubleToIntegerConverter()).bind("weight");
        ret.forField(height).withConverter(new DoubleToIntegerConverter()).bind("height");
        ret.forField(targetWeight).withConverter(new DoubleToIntegerConverter()).bind("targetWeight");
        ret.bind(fat, "fat");
        ret.bind(activity, "activity");
        ret.bind(targetBody, "targetBody");

        ret.readBean(bean);
        return ret;
    }

    private void addSlider(Slider slider, String unit) {
        final String caption = slider.getCaption();
        slider.addValueChangeListener(e -> {
            slider.setCaption(caption + " " + e.getValue().intValue() + " " + unit);
        });

        slider.setWidth(W);
        this.addComponent(slider);
    }

}
