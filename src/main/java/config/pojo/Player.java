package config.pojo;

public class Player{
    private String playerId;//玩家ID
    private int rank;//玩家分数
    private long startMatchTime;//开始匹配时间

    public Player(String playerId, int rank) {
        this.playerId = playerId;
        this.rank = rank;
        this.startMatchTime=System.currentTimeMillis();
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getStartMatchTime() {
        return startMatchTime;
    }

    public void setStartMatchTime(long startMatchTime) {
        this.startMatchTime = startMatchTime;
    }
}