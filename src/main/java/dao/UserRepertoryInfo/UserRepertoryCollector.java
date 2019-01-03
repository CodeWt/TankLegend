package dao;


public class UserRepertoryCollector {
    private int userRepertoryCollectorId;
    private int userRepertoryCollectorType;
    private int getUserRepertoryCollectorTypeId;

    public int getUserRepertoryCollectorId() {
        return userRepertoryCollectorId;
    }

    public void setUserRepertoryCollectorId(int userRepertoryCollectorId) {
        this.userRepertoryCollectorId = userRepertoryCollectorId;
    }

    public int getUserRepertoryCollectorType() {
        return userRepertoryCollectorType;
    }

    public void setUserRepertoryCollectorType(int userRepertoryCollectorType) {
        this.userRepertoryCollectorType = userRepertoryCollectorType;
    }

    public int getGetUserRepertoryCollectorTypeId() {
        return getUserRepertoryCollectorTypeId;
    }

    public void setGetUserRepertoryCollectorTypeId(int getUserRepertoryCollectorTypeId) {
        this.getUserRepertoryCollectorTypeId = getUserRepertoryCollectorTypeId;
    }

    @Override
    public String toString() {
        return "UserRepertoryCollector{" +
                "userRepertoryCollectorId=" + userRepertoryCollectorId +
                ", userRepertoryCollectorType=" + userRepertoryCollectorType +
                ", getUserRepertoryCollectorTypeId=" + getUserRepertoryCollectorTypeId +
                '}';
    }
}
