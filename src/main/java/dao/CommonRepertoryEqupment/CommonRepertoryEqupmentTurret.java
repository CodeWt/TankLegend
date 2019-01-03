package dao.CommonRepertoryEqupment;

import javafx.geometry.HPos;

/**
 * @Created by  qiao
 * @date 18-3-4 下午9:10
 */

public class CommonRepertoryEqupmentTurret extends CommonRepertoryEqupment {
    private int equpment_turret_id;
    private String equpment_turret_name;
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

    public int getEqupment_turret_id() {
        return equpment_turret_id;
    }

    public void setEqupment_turret_id(int equpment_turret_id) {
        this.equpment_turret_id = equpment_turret_id;
    }

    public String getEqupment_turret_name() {
        return equpment_turret_name;
    }

    public void setEqupment_turret_name(String equpment_turret_name) {
        this.equpment_turret_name = equpment_turret_name;
    }



    @Override
    public String toString() {
        return "CommonRepertoryEqupmentTurret{" +
                "equpment_turret_id=" + equpment_turret_id + '\'' +
                ", equpment_turret_name='" + equpment_turret_name + '\'' +
                ", HP=" + super.getHP() + '\'' +
                ", fire=" + super.getFire() + '\'' +
                ", shotspeed=" + super.getShotspeed() + '\'' +
                ", tankspeed=" + super.getTankspeed() + '\'' +
                '}';
    }

}
