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
        ret.setBirthYear(1984);
        ret.setHeight(165);
        ret.setWeight(55);
        ret.setTargetWeight(ret.getWeight()-5);
        ret.setActivity(BodyActivity.MEDIUM);
        ret.setTargetBody(BodyTarget.REDUCE);
        ret.setFat(30);
        
        return ret;
    }

}
