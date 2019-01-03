package config;

public class GarageKey {
    public static final String REQUEST_TYPE_1 = "image";//请求车库信息头
    public static final String REQUEST_TYPE_2 = "data";//请求车库信息头
    public static final String REQUEST_TYPE_3 = "currentTank";//请求当前用户坦克状态
    public static final String REQUEST_TYPE_4 = "equip";//装备时判断用户是否拥有该组件
    public static final String REQUEST_TYPE_5 = "buy";//判断用户是否拥有该组件
    public static final String REQUEST_TYPE_6 = "submit";//提交编辑好的坦克

    public static final String CHASSIS = "chassis";//底盘
    public static final String FIRE = "fire";//炮塔
    public static final String PROP = "prop";//道具

    public static final String FLAME_FIRE = "flame";//火焰炮
    public static final String ION_FIRE = "ion";//离子炮
    public static final String LASER_FIRE = "laser";//激光炮

    public static final String MIDDLE_CHASSIS = "middle";//中甲
    public static final String LIGHT_CHASSIS = "light";//轻甲
    public static final String HEAVY_CHASSIS = "heavy";//重甲

    public static final String DOUBLE_DAMAGE = "doubleKill";//双倍伤害
    public static final String DOUBLE_DEFEND = "doubleDefend";
    public static final String ADD_SPEED = "add_speed";

    public static final String DOUBLE_DAMAGE_DESC = "(按键盘上的3使用)—获得1分钟对对手双倍的伤害。";
    public static final String DOUBLE_DEFEND_DESC = "(按键盘上的2使用)—获得1分钟双倍的防御。";
    public static final String ADD_SPEED_DESC = "（按键盘上的4使用）—获得一分钟30%的加速。";

    public static final String FLAME_FIRE_DESC = "火焰炮是游戏近程类型的一个典型，火焰炮是一个非常强大的武器。";
    public static final String ION_FIRE_DESC = "(按键盘上的2使用)—获得1分钟双倍的防御。";
    public static final String LASER_FIRE_DESC = "（按键盘上的4使用）—获得一分钟30%的加速。";

    public static final String MIDDLE_CHASSIS_DESC = "这是较坚硬的中级底盘，防御性和速度都不错，稳定性较强，玩家注册后就会获得。";//中甲
    public static final String LIGHT_CHASSIS_DESC = "黄蜂轻甲，基础底盘。高度的移动性和灵活性。缺点是装甲较薄，稳定性，防御性较弱，是目前为止最快的底盘。";
    public static final String HEAVY_CHASSIS_DESC = "坚硬，防御性和稳定性都很强。缺点是移动性，灵活性较弱。";

    public static final String TYPE_DEFAULT="typeDefault";
    public static final String NAME_DEFAULT="nameDefault";
}
