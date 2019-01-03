package dao.CommonRepertoryEqupment;

/**
 * @Created by  qiao
 * @date 18-3-4 下午9:16
 */

public class CommonRepertoryEqupmentEngine extends CommonRepertoryEqupment {
    private int equpment_engine_id;
    private String equpment_engine_name;
    public double uPDefend;//防护
    public double uPMaxSpeed;//最大速度
    public double uPTurnSpeed;//旋转速度
    public double uPPower;//动力

    public double getuPDefend() {
        return uPDefend;
    }

    public void setuPDefend(double uPDefend) {
        this.uPDefend = uPDefend;
    }

    public double getuPMaxSpeed() {
        return uPMaxSpeed;
    }

    public void setuPMaxSpeed(double uPMaxSpeed) {
        this.uPMaxSpeed = uPMaxSpeed;
    }

    public double getuPTurnSpeed() {
        return uPTurnSpeed;
    }

    public void setuPTurnSpeed(double uPTurnSpeed) {
        this.uPTurnSpeed = uPTurnSpeed;
    }

    public double getuPPower() {
        return uPPower;
    }

    public void setuPPower(double uPPower) {
        this.uPPower = uPPower;
    }

    public int getEqupment_engine_id() {
        return equpment_engine_id;
    }

    public void setEqupment_engine_id(int equpment_engine_id) {
        this.equpment_engine_id = equpment_engine_id;
    }

    public String getEqupment_engine_name() {
        return equpment_engine_name;
    }

    public void setEqupment_engine_name(String equpment_engine_name) {
        this.equpment_engine_name = equpment_engine_name;
    }

    @Override
    public String toString() {
        return "CommonRepertoryEqupmentEngine{" +
                "equpment_engine_id=" + equpment_engine_id + '\'' +
                ", equpment_engine_name='" + equpment_engine_name + '\'' +
                ", HP=" + super.getHP() + '\'' +
                ", fire=" + super.getFire() + '\'' +
                ", shotspeed=" + super.getShotspeed() + '\'' +
                ", tankspeed=" + super.getTankspeed() + '\'' +
                '}';
    }
}
