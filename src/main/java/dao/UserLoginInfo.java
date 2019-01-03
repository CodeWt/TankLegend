package dao;

/**
 * @Created by  qiao
 * @date 18-2-25 下午4:34
 */

public class UserLoginInfo extends User{
    private int uesr_game_info_id;
    private String user_login_tel;
    private String user_login_email;
    private String uesr_login_game_info;

    public int getUesr_game_info_id() {
        return uesr_game_info_id;
    }

    public void setUesr_game_info_id(int uesr_game_info_id) {
        this.uesr_game_info_id = uesr_game_info_id;
    }

    public String getUser_login_tel() {
        return user_login_tel;
    }

    public void setUser_login_tel(String user_login_tel) {
        this.user_login_tel = user_login_tel;
    }

    public String getUser_login_email() {
        return user_login_email;
    }

    public void setUser_login_email(String user_login_email) {
        this.user_login_email = user_login_email;
    }

    public String getUesr_login_game_info() {
        return uesr_login_game_info;
    }

    public void setUesr_login_game_info(String uesr_login_game_info) {
        this.uesr_login_game_info = uesr_login_game_info;
    }
}
