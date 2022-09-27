package cz.pokus.calcal.backend.jpa.enums;

public enum BodyTarget {
    FIT(0, "Být fit"), REDUCE(1, "Zhubnout"), GROW(2, "Přibrat");

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

    private BodyTarget(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
