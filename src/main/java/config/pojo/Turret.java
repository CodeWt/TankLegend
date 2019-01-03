package config.pojo;

public class Turret extends BasedEquip{//炮塔类
    //private String turretName;//炮塔名称
    public double turretDamage;//炮塔伤害
    public double turretReload;//重新装载弹药时间
    public double turretAttackRange;//打击范围
    public double turretBurnTime;//燃烧时间


    public double getTurretDamage() {
        return turretDamage;
    }

    public void setTurretDamage(double turretDamage) {
        this.turretDamage = turretDamage;
    }

    public double getTurretReload() {
        return turretReload;
    }

    public void setTurretReload(double turretReload) {
        this.turretReload = turretReload;
    }

    public double getTurretAttackRange() {
        return turretAttackRange;
    }

    public void setTurretAttackRange(double turretAttackRange) {
        this.turretAttackRange = turretAttackRange;
    }

    public double getTurretBurnTime() {
        return turretBurnTime;
    }

    public void setTurretBurnTime(double turretBurnTime) {
        this.turretBurnTime = turretBurnTime;
    }

}
