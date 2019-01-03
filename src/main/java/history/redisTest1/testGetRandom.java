package history.redisTest1;

import utils.GetRandom;

/**
 * @Created by  qiao
 * @date 18-4-15 下午2:10
 */

public class testGetRandom {
    public static void main(String[] args) {
        for(int i=1;i<20;i++) {
            System.out.println(GetRandom.getUserId());
        }
    }
}
