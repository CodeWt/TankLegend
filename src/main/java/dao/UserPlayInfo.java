package dao;

/**
 * @Created by  qiao
 * @date 18-3-8 下午9:24
 */

public class UserPlayInfo implements UserInfo{
    private int user_id;
    private UserTankInfo userTankInfo;
    private UserTankCode userTankCode;
    private UserMapInfo userMapInfo;
    private int rank;
    private int startMatchTime;

    public UserPlayInfo(int user_id, int rank) {
        this.user_id = user_id;
        this.rank = rank;
    }

    public int getStartMatchTime() {
        return startMatchTime;
    }

    public void setStartMatchTime(int startMatchTime) {
        this.startMatchTime = startMatchTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public UserPlayInfo(Integer user_id) {
        this.user_id=user_id;
    }

    public UserPlayInfo() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserTankInfo getUserTankInfo() {
        return userTankInfo;
    }

    public void setUserTankInfo(UserTankInfo userTankInfo) {
        this.userTankInfo = userTankInfo;
    }

    public UserTankCode getUserTankCode() {
        return userTankCode;
    }

    public void setUserTankCode(UserTankCode userTankCode) {
        this.userTankCode = userTankCode;
    }

    public UserMapInfo getUserMapInfo() {
        return userMapInfo;
    }

    public void setUserMapInfo(UserMapInfo userMapInfo) {
        this.userMapInfo = userMapInfo;
    }

    @Override
    public String toString() {
        return "UserPlayInfo{" +
                "userTankInfo=" + userTankInfo +
                ", userTankCode=" + userTankCode +
                ", userMapInfo=" + userMapInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPlayInfo that = (UserPlayInfo) o;

        if (userTankInfo != null ? !userTankInfo.equals(that.userTankInfo) : that.userTankInfo != null) return false;
        if (userTankCode != null ? !userTankCode.equals(that.userTankCode) : that.userTankCode != null) return false;
        return userMapInfo != null ? userMapInfo.equals(that.userMapInfo) : that.userMapInfo == null;
    }

    @Override
    public int hashCode() {
        int result = userTankInfo != null ? userTankInfo.hashCode() : 0;
        result = 31 * result + (userTankCode != null ? userTankCode.hashCode() : 0);
        result = 31 * result + (userMapInfo != null ? userMapInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String getClassName() {
        return "userPlayInfo";
    }
}
