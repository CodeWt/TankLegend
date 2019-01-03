package dao.CommonMap;

/**
 * @Created by  qiao
 * @date 18-3-6 下午3:01
 */
/*每块地 的属性*/
public class MapLoad {
    private String room_id;
    private int id;
    private String type;
    private String map_id;

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

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "MapLoad{" +
                "room_id='" + room_id + '\'' +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", map_id='" + map_id + '\'' +
                '}';
    }
}
