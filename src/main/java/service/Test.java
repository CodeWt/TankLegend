/*
package service;

import config.pojo.Player;
import dao.UserPlayInfo;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test extends Thread{
    Lock lock;
    public static int i=0;
    CountDownLatch countDownLatch;
    public Test(Lock lock,CountDownLatch countDownLatch){
        this.lock=lock;
        this.countDownLatch=countDownLatch;
    }
    @Override
    public void run() {

        lock.lock();
        while (i<9000) {

            List<Player> players = MatchPlayer.matchProcess(MatchPlayer.playerPool);
            System.out.println("size---->>>" + players.size());
            for (Player : players) {
                System.out.println("playerId====>>" + player.getUser_id() + "  playerRank" + player.getRank());
            }
            System.out.println("matchPiars==>>"+(++i)+"  MatchPoolSize=====>>"+MatchPlayer.playerPool.size());

        }
        lock.unlock();
        countDownLatch.countDown();

    }

    public static void main(String[] args) {
        CountDownLatch count=new CountDownLatch(4);
        Lock lock=new ReentrantLock();
        for (int i = 0; i < 18000; i++) {
            //MatchPlayer.putPlayerIntoMatchPool(i,25);
            if (i<25){
                MatchPlayer.putPlayerIntoMatchPool(i, 25+i);
            }else if (i<65){
                MatchPlayer.putPlayerIntoMatchPool(i,60-i);
            }else {
                MatchPlayer.putPlayerIntoMatchPool(i,35);
            }

        }
        long startTime=System.currentTimeMillis();
        System.out.println("currentTime====>>>"+startTime);
        new Test(lock,count).start();
        new Test(lock,count).start();
        new Test(lock,count).start();
        new Test(lock,count).start();
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("spendTimeSum===>>"+(System.currentTimeMillis()-startTime)/1000);
    }
}
*/
