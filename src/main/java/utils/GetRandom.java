package utils;

import java.util.UUID;

public class GetRandom {

    public synchronized static String getUserId(){
        String id= UUID.randomUUID().toString().replace("-","");
        return id;
    }

}
