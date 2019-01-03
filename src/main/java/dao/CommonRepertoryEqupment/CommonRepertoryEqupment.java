package dao.CommonRepertoryEqupment;

/**
 * @Created by  qiao
 * @date 18-3-4 下午9:11
 */

public class CommonRepertoryEqupment {
    private int HP;
    private int fire;
    private int shotspeed;
    private int tankspeed;

    protected String name;//装备名称
    protected String description;//准备描述
    protected int diamond;//所需砖石数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getFire() {
        return fire;
    }

    public void setFire(int fire) {
        this.fire = fire;
    }

    public int getShotspeed() {
        return shotspeed;
    }

    public void setShotspeed(int shotspeed) {
        this.shotspeed = shotspeed;
    }

    public int getTankspeed() {
        return tankspeed;
    }

    public void setTankspeed(int tankspeed) {
        this.tankspeed = tankspeed;
    }

    @Override
    public String toString() {
        return "CommonRepertoryEqupment{" +
                "HP=" + HP +
                ", fire=" + fire +
                ", shotspeed=" + shotspeed +
                ", tankspeed=" + tankspeed +
                '}';
    }
}
