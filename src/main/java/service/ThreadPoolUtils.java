package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Created by  qiao
 * @date 18-5-21 下午4:16
 */

public class ThreadPoolUtils {
    public static ExecutorService MatchPool = Executors.newSingleThreadExecutor();
    public static ExecutorService PlayingPool = Executors.newFixedThreadPool(10);

    public static ExecutorService getMatchPool() {
        return MatchPool;
    }

    public static void setMatchPool(ExecutorService matchPool) {
        MatchPool = matchPool;
    }

    public static ExecutorService getPlayingPool() {
        return PlayingPool;
    }

    public static void setPlayingPool(ExecutorService playingPool) {
        PlayingPool = playingPool;
    }
}
