package dao.CommonRepertoryEqupment;

/**
 * @Created by  qiao
 * @date 18-3-4 下午9:20
 */

public class CommonRepertoryEqupmentArmour extends CommonRepertoryEqupment {
    private int armourequpment_armour_id;
    private String equpment_armour_name;

    public int getArmourequpment_armour_id() {
        return armourequpment_armour_id;
    }

    public void setArmourequpment_armour_id(int armourequpment_armour_id) {
        this.armourequpment_armour_id = armourequpment_armour_id;
    }

    public String getEqupment_armour_name() {
        return equpment_armour_name;
    }

    public void setEqupment_armour_name(String equpment_armour_name) {
        this.equpment_armour_name = equpment_armour_name;
    }

    @Override
    public String toString() {
        return "CommonRepertoryEqupmentArmour{" +
                "armourequpment_armour_id=" + armourequpment_armour_id + '\'' +
                ", equpment_armour_name='" + equpment_armour_name + '\'' +
                ", HP=" + super.getHP() + '\'' +
                ", fire=" + super.getFire() + '\'' +
                ", shotspeed=" + super.getShotspeed() + '\'' +
                ", tankspeed=" + super.getTankspeed() + '\'' +
                '}';
    }
}
