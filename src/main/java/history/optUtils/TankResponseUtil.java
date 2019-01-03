package history.optUtils;

/**
 * @Created by  qiao
 * @date 18-4-17 下午7:51
 */

public class TankResponseUtil {
   private String myTank;
   private int[] myLocation;
   private String otherTank;
   private int[] otherLocation;

    public String getMyTank() {
        return myTank;
    }

    public void setMyTank(String myTank) {
        this.myTank = myTank;
    }

    public int[] getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(int[] myLocation) {
        this.myLocation = myLocation;
    }

    public String getOtherTank() {
        return otherTank;
    }

    public void setOtherTank(String otherTank) {
        this.otherTank = otherTank;
    }

    public int[] getOtherLocation() {
        return otherLocation;
    }

    public void setOtherLocation(int[] otherLocation) {
        this.otherLocation = otherLocation;
    }
}
