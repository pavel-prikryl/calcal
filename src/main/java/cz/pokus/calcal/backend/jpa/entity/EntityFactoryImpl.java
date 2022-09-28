package cz.pokus.calcal.backend.jpa.entity;

import org.springframework.stereotype.Component;

import cz.pokus.calcal.backend.jpa.enums.BodyActivity;
import cz.pokus.calcal.backend.jpa.enums.BodyTarget;

@Component
public class EntityFactoryImpl implements EntityFactory {

    @Override
    public Measurement getDefaultMeasurement() {
        Measurement ret = new Measurement();
        ret.setName("sample");
        ret.setMale(false);
        ret.setBirthYear(1993);
        ret.setHeight(170);
        ret.setWeight(80);
        ret.setTargetWeight(ret.getWeight()-5);
        ret.setActivity(BodyActivity.SITTING);
        ret.setTargetBody(BodyTarget.FIT);
        ret.setFat(20);
        
        return ret;
    }

}
