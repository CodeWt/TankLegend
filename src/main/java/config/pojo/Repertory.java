package config.pojo;
//仓库信息
public class Repertory {
    private String[] equipType;//装备类型
    private Turret[] turrets;
    private UnderPan[] underPans;

    public String[] getEquipType() {
        return equipType;
    }

    public void setEquipType(String[] equipType) {
        this.equipType = equipType;
    }

    public Turret[] getTurrets() {
        return turrets;
    }

    public void setTurrets(Turret[] turrets) {
        this.turrets = turrets;
    }

    public UnderPan[] getUnderPans() {
        return underPans;
    }

    public void setUnderPans(UnderPan[] underPans) {
        this.underPans = underPans;
    }
}

