package cz.pokus.calcal.backend.jpa.enums;

public enum BodyActivity {
    NONE(0, "Žádná aktivita (Žádný sport ani pohyb)"),
    SITTING(1, "Sedavá aktivita (Sedavé zaměstnání a občasná chůze)"), LIGHT(2, "Lehce aktivní (Pohyb 1x týdně)"),
    MEDIUM(3, "Středně aktivní (Pohyb 2x týdně)"), HIGH(4, "Aktivní (Pohyb 3x týdně)"),
    ABOVE_HIGH(5, "Nadprůměrně aktivní (Pohyb 4x týdně)"), VERY(6, "Velmi aktivní (Pohyb 5x týdně)"),
    EXTREME(7, "Velmi aktivní (Pohyb 6x týdně)");

    private final Integer code;
    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescShort() {
        return code + "-" + this.name();
    }

    private BodyActivity(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
