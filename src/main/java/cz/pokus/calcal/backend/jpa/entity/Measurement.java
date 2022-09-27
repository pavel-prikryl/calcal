package cz.pokus.calcal.backend.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import cz.pokus.calcal.backend.jpa.enums.BodyActivity;
import cz.pokus.calcal.backend.jpa.enums.BodyTarget;

@Entity
@Table(name = "MEASUREMENT")
public class Measurement implements Serializable {

    private static final long serialVersionUID = -6793004223116087652L;

    public static final List<Integer> FAT_VALUES = Arrays.asList(5, 15, 20, 30, 40);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean male;

    @Min(1923)
    @Max(2022)
    @Column(name = "birth_year", nullable = false)
    private Integer birthYear;

    @Min(20)
    @Max(250)
    @Column(nullable = false)
    private Integer weight;

    @Min(80)
    @Max(220)
    @Column(nullable = false)
    private Integer height;

    @Min(20)
    @Max(250)
    @Column(name = "target_weight", nullable = false)
    private Integer targetWeight;

    @Min(5)
    @Max(50)
    @Column(nullable = false)
    private Integer fat;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BodyActivity activity;

    @Column(name = "target_body", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BodyTarget targetBody;

    // calculated
    @Column(nullable = true)
    private BigDecimal bmi;

    // calculated
    @Column(nullable = true)
    private Integer basal;

    // calculated
    @Column(nullable = true)
    private Integer intake;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(Integer targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public BodyActivity getActivity() {
        return activity;
    }

    public void setActivity(BodyActivity activity) {
        this.activity = activity;
    }

    public BodyTarget getTargetBody() {
        return targetBody;
    }

    public void setTargetBody(BodyTarget targetBody) {
        this.targetBody = targetBody;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public Integer getBasal() {
        return basal;
    }

    public void setBasal(Integer basal) {
        this.basal = basal;
    }

    public Integer getIntake() {
        return intake;
    }

    public void setIntake(Integer intake) {
        this.intake = intake;
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, basal, birthYear, bmi, created, fat, height, id, intake, male, name, targetBody,
                targetWeight, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Measurement other = (Measurement) obj;
        return activity == other.activity && Objects.equals(basal, other.basal)
                && Objects.equals(birthYear, other.birthYear) && Objects.equals(bmi, other.bmi)
                && Objects.equals(created, other.created) && Objects.equals(fat, other.fat)
                && Objects.equals(height, other.height) && Objects.equals(id, other.id)
                && Objects.equals(intake, other.intake) && male == other.male && Objects.equals(name, other.name)
                && targetBody == other.targetBody && Objects.equals(targetWeight, other.targetWeight)
                && Objects.equals(weight, other.weight);
    }

    @Override
    public String toString() {
        return "Measurement [id=" + id + ", created=" + created + ", name=" + name + ", male=" + male + ", birthYear="
                + birthYear + ", weight=" + weight + ", height=" + height + ", targetWeight=" + targetWeight + ", fat="
                + fat + ", activity=" + activity + ", targetBody=" + targetBody + ", bmi=" + bmi + ", basal=" + basal
                + ", intake=" + intake + "]";
    }

}
