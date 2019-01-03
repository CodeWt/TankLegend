package dao;


public class UserRepertoryEqupment {
    private int UserRepertoryEqupmentId;
    private int UserRepertoryEqupmentType;
    private int UserRepertoryEqupmenTypeId;

    public int getUserRepertoryEqupmentId() {
        return UserRepertoryEqupmentId;
    }

    public void setUserRepertoryEqupmentId(int userRepertoryEqupmentId) {
        UserRepertoryEqupmentId = userRepertoryEqupmentId;
    }

    public int getUserRepertoryEqupmentType() {
        return UserRepertoryEqupmentType;
    }

    public void setUserRepertoryEqupmentType(int userRepertoryEqupmentType) {
        UserRepertoryEqupmentType = userRepertoryEqupmentType;
    }

    public int getUserRepertoryEqupmenTypeId() {
        return UserRepertoryEqupmenTypeId;
    }

    public void setUserRepertoryEqupmenTypeId(int userRepertoryEqupmenTypeId) {
        UserRepertoryEqupmenTypeId = userRepertoryEqupmenTypeId;
    }

    @Override
    public String toString() {
        return "UserRepertoryEqupment{" +
                "UserRepertoryEqupmentId=" + UserRepertoryEqupmentId +
                ", UserRepertoryEqupmentType=" + UserRepertoryEqupmentType +
                ", UserRepertoryEqupmenTypeId=" + UserRepertoryEqupmenTypeId +
                '}';
    }
}
