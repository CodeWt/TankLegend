package dao.CommonRepertoryEqupment;

/**
 * @Created by  qiao
 * @date 18-3-4 下午9:18
 */

public class CommonRepertoryEqupmentWhell extends CommonRepertoryEqupment {
    private int equpment_whell_id;
    private String equpment_whell_name;

    public int getEqupment_whell_id() {
        return equpment_whell_id;
    }

    public void setEqupment_whell_id(int equpment_whell_id) {
        this.equpment_whell_id = equpment_whell_id;
    }

    public String getEqupment_whell_name() {
        return equpment_whell_name;
    }

    public void setEqupment_whell_name(String equpment_whell_name) {
        this.equpment_whell_name = equpment_whell_name;
    }

    @Override
    public String toString() {
        return "CommonRepertoryEqupmentWhell{" +
                "equpment_whell_id=" + equpment_whell_id + '\'' +
                ", equpment_whell_name='" + equpment_whell_name + '\'' +
                ", HP=" + super.getHP() + '\'' +
                ", fire=" + super.getFire() + '\'' +
                ", shotspeed=" + super.getShotspeed() + '\'' +
                ", tankspeed=" + super.getTankspeed() + '\'' +
                '}';
    }
}
