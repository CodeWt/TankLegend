package config.pojo;

public class UnderPan extends BasedEquip{//底盘
    //private String uPName;//底盘名称
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
}
