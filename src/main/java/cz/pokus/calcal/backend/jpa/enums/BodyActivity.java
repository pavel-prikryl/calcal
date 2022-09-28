package cz.pokus.calcal.backend.jpa.enums;

/*
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
            
https://bruxy.regnet.cz/web/fitness/CZ/vypocet-bazalniho-metabolismu/
1,2 Sedavá  Žádné cvičení, sedavé zaměstnání    
1,375   Nízká   Lehké cvičení nebo sport, 1 až 3× v týdnu   
1,55    Mírná   Středně náročné cvičení nebo sport 3 až 5× v týdnu  
1,725   Vysoká  Těžké cvičení nebo sport 6 až 7× v týdnu    
1,9 Extrémní    Velmi těžké cvičení a fyzicky náročné zaměstnání            

 */

public enum BodyActivity {
    NONE(0, "Žádná aktivita (Žádný sport ani pohyb)", 0),
    SITTING(1, "Sedavá aktivita (Sed. zam. a občasná chůze)", 1.2), 
    LIGHT(2, "Lehce aktivní (Pohyb 1x týdně)", 1.3),
    MEDIUM(3, "Středně aktivní (Pohyb 2x týdně)", 1.375), 
    HIGH(4, "Aktivní (Pohyb 3x týdně)", 1.55),
    ABOVE_HIGH(5, "Nadprůměrně aktivní (Pohyb 4x týdně)", 1.6), 
    VERY(6, "Velmi aktivní (Pohyb 5x týdně)",  1.725),
    EXTREME(7, "Velmi aktivní (Pohyb 6x týdně)", 1.9);

    private final Integer code;
    private final String desc;
    private final double intakeRatio;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public double getIntakeRatio() {
        return intakeRatio;
    }

    public String getDescShort() {
        return code + "-" + this.name();
    }

    private BodyActivity(Integer code, String desc, double intakeRatio) {
        this.code = code;
        this.desc = desc;
        this.intakeRatio = intakeRatio;
    }

}
