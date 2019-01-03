package config.pojo;

public class Prop extends BasedEquip {
    private String abilityName;//所具有的能力名称
    private double abilityValue;//能力值

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public double getAbilityValue() {
        return abilityValue;
    }

    public void setAbilityValue(double abilityValue) {
        this.abilityValue = abilityValue;
    }
}
