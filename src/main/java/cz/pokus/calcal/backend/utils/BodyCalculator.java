package cz.pokus.calcal.backend.utils;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.pokus.calcal.backend.jpa.entity.Measurement;
import cz.pokus.calcal.backend.jpa.enums.BodyTarget;
import cz.pokus.calcal.frontend.view.CalcView;

public class BodyCalculator {
    private static Logger logger = LoggerFactory.getLogger(BodyCalculator.class);

    /*
     https://wikijii.com/wiki/Harris%E2%80%93Benedict_equation
     
     Harris-Benediktovy rovnice revidované Rozou a Shizgala v roce 1984.[3]

            Muži    BMR = 88,362 + (13,397 × hmotnost v kg) + (4,799 × výška v cm) - (5 677 × věk v letech)
            Ženy    BMR = 447,593 + (9,247 × hmotnost v kg) + (3,098 × výška v cm) - (4,330 × věk v letech)
     
     Harris-Benediktovy rovnice revidovány Mifflin a St Jeor v roce 1990:[4]

            Muži    BMR = (10 × hmotnost v kg) + (6,25 × výška v cm) - (5 × věk v letech) + 5
            Ženy    BMR = (10 × hmotnost v kg) + (6,25 × výška v cm) - (5 × věk v letech) - 161
            
            
        Mifflin-St. Jeor
        (muž) BMR = 10W + 6.25H - 5A + 5
        (žena) BMR = 10W + 6.25H - 5A - 161
        Katch-Mcardle
        (muž, žena) BMR = 370 + 21.6(1 - F)W
        
        -------------------------------------------------------------------------
        https://zivotavyziva.cz/jak-si-vypocitat-denni-prijem-kalorii/
            nyní číslo, které vám vyšlo z BMR vzorce či kalkulačky vynásobte číslem následujícím:
            1,2
            Pokud máte sedavé zaměstnání a necvičíte, či cvičíte velice málo
            
            1,375
            Pokud cvičíte 1-3 krát týdně, považujete se za aktivnější, či máte náročnější zaměstnání
            
            1,55
            Jestliže cvičíte 3 – 5 krát týdně, nebo máte fyzicky náročné zaměstnání, či jinou každodenní činnost
            
            1,725
            Pokud sportujete každý den, nebo 6 krát do týdne, máte velmi náročné zaměstnání, nebo vykonáváte každý den velmi náročnou fyzickou aktivitu

            KalTab:
                // Dále si můžete při výpočtu nastavit vaši tělesnou aktivitu (sedavá, lehce
                // aktivní, středně aktivní, velmi aktivní, extrémně aktivní, žádná aktivita)
                 *  a
                // cíl (zhubnout - odečítá se 15% z celkových kalorií, 
                 *  nabrat svaly - připočte  se 10 % celkových kalorií, 
                 *  být fit - výpočet se nemění).

                                       
     */
    
    
    public static double kcalToKj (int kcal) {
        return kcal * 4.184;
    }
    
    public static double calcBasalOld(Measurement data) {
        if (data.isMale()) {
            double ret = 88.362 + (13.397 * data.getWeight()) + (4.799 * data.getHeight()) - (5.677 * data.getAge());
            return ret;
        } else {
            double ret = 447.593 + (9.247 * data.getWeight()) + (3.098 * data.getHeight()) - (4.330 * data.getAge());
            return ret;
        }
    }

    public static double calcBasalWithFat(Measurement data) {
        return 370 + 21.6 * (1 - (data.getFat() / 100)) * data.getWeight();
    }

    public static double calcBasal(Measurement data) {
        if (data.isMale()) {
            double ret = 10 * data.getWeight() + 6.25 * data.getHeight() - 5 * data.getAge() + 5;
            return ret;
        } else {
            double ret = 10 * data.getWeight() + 6.25 * data.getHeight() - 5 * data.getAge() - 161;
            return ret;
        }
    }

    public static double calcBasalAverage(Measurement data) {
        double d1 = calcBasalWithFat(data);
        double d2 = calcBasal(data);
        double d3 = calcBasalOld(data);
        return (d1 + d2 + d3) / 3;
    }

    public static BigDecimal calcBmi(Measurement data) {
        double ret = data.getWeight() / (data.getHeightMeters() * data.getHeightMeters());
        BigDecimal bd = new BigDecimal(ret);
        return bd.setScale(2, BigDecimal.ROUND_CEILING);
    }

    public static double calcIntake(Measurement data) {
        double ret = calcBasalAverage(data) * data.getActivity().getIntakeRatio();
        if (BodyTarget.GROW == data.getTargetBody()) {
            ret = ret + (ret * 0.1);
        } else if (BodyTarget.REDUCE == data.getTargetBody()) {
            ret = ret - (ret * 0.15);
        }
        return ret;
    }
    
    public static Measurement recalcAll(Measurement data) {
        double basal = calcBasalAverage(data);
        data.setBasal((int) basal);

        data.setBmi(calcBmi(data));
        
        double intake = calcIntake(data);
        data.setIntake((int)intake);
        
        return data;
    }
    
    public static String getBmiCaption(double bmi) {
        if (bmi < 16.5) {
            return "těžká podvýživa";
        } else if (bmi < 18.5) {
            return "podváha";
        } else if (bmi < 25) {
            return "ideální váha";
        } else if (bmi < 30) {
            return "nadváha";
        } else if (bmi < 35) {
            return "mírná obezita";
        } else if (bmi < 40) {
            return "střední obezita";
        }
        return "morbidní obezita";
    }
}
