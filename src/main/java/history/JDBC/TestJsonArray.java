package history.JDBC;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import dao.CommonMap.MapLoad;
import dao.JsonKeyword;

import java.util.Arrays;
import java.util.List;

/**
 * @Created by  qiao
 * @date 18-3-6 下午9:07
 */

public class TestJsonArray {
    public static void main(String[] args) {

        String mes = "{\"type\":\"mapinfo\", \"userId\":\"1\", \"mapinfoid\":[{ \"id\":\"1\", \"type\":\"load\", \"mapid\":\"1\" },{ \"id\":\"2\", \"type\":\"load\", \"mapid\":\"2\" }]}";
        MapLoad mapLoad =new MapLoad();
          String string= "{\"id\":\"2\",\"mapid\":\"2\",\"type\":\"load\"}";

//        JSON.

        MapLoad xxxx = JSON.parseObject(string, MapLoad.class);
        System.out.println(xxxx.toString()+"------");




    }
}

class XXX {
    private String type;
    private int userId;
    private MapLoad mapLoad[];

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public MapLoad[] getMapLoad() {
        return mapLoad;
    }

    public void setMapLoad(MapLoad[] mapLoad) {
        this.mapLoad = mapLoad;
    }

    @Override
    public String toString() {
        return "XXX{" +
                "type='" + type + '\'' +
                ", userId=" + userId +
                ", mapLoad=" + Arrays.toString(mapLoad) +
                '}';
    }
}

class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
