package dao;


public class UserRepertoryInfo {
    private int UserRepertoryInfoId;
    private int UserRepertoryEqupmentId;
    private int UserRepertoryCollectorId;

    public int getUserRepertoryInfoId() {
        return UserRepertoryInfoId;
    }

    public void setUserRepertoryInfoId(int userRepertoryInfoId) {
        UserRepertoryInfoId = userRepertoryInfoId;
    }

    public int getUserRepertoryEqupmentId() {
        return UserRepertoryEqupmentId;
    }

    public void setUserRepertoryEqupmentId(int userRepertoryEqupmentId) {
        UserRepertoryEqupmentId = userRepertoryEqupmentId;
    }

    public int getUserRepertoryCollectorId() {
        return UserRepertoryCollectorId;
    }

    public void setUserRepertoryCollectorId(int userRepertoryCollectorId) {
        UserRepertoryCollectorId = userRepertoryCollectorId;
    }

    @Override
    public String toString() {
        return "UserRepertoryInfo{" +
                "UserRepertoryInfoId=" + UserRepertoryInfoId +
                ", UserRepertoryEqupmentId=" + UserRepertoryEqupmentId +
                ", UserRepertoryCollectorId=" + UserRepertoryCollectorId +
                '}';
    }
}
