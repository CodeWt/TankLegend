package dao;

import handler.LoginHandler;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by  qiao
 * @date 18-4-18 下午2:57
 */

public class UserPlayRoom implements UserInfo{
    private static Logger logger = Logger.getLogger(UserPlayRoom.class.getName());

    private GameModel type;
    private int total;
    private int roomid;
    private List number =new ArrayList<>();

    public GameModel getType() {
        return type;
    }

    public void setType(int type) {
        if (this.getRoomid() > 0 && this.getRoomid() < 10) {
            this.type = GameModel.PersonalMode;
        } else if (this.getRoomid() < 20) {
            this.type = GameModel.PairMode;
        } else if (this.getRoomid() < 50) {
            this.type = GameModel.TrainingMode;
        }else {
            this.type=GameModel.Error;
            logger.error("UserPlayRoom.type 出现错误。");
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getNumber() {
        return number;
    }

    public void setNumber(List number) {
        this.number = number;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.setType(roomid);
        this.roomid = roomid;
    }

    @Override
    public String getClassName() {
        return "userPlayRoom";
    }
}
