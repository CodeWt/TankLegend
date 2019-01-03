package dao;

/**
 * @Created by  qiao
 * @date 18-2-27 上午10:38
 */

public class User {
    private int uid;
    private String uname;
    private String upassward;

    public User() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassward() {
        return upassward;
    }

    public void setUpassward(String upassward) {
        this.upassward = upassward;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upassward='" + upassward + '\'' +
                '}';
    }
}
