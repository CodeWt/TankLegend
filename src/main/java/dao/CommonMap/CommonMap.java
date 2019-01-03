package dao.CommonMap;

/**
 * @Created by  qiao
 * @date 18-3-5 上午11:20
 */

public class CommonMap {
    private int id;
    private String type;
    private int map_id;
    private int HP;
    private int ATK;
    private int down_speed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setMap_id(Integer map_id) {
        this.map_id = map_id;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDown_speed() {
        return down_speed;
    }

    public void setDown_speed(int down_speed) {
        this.down_speed = down_speed;
    }
}
