##1. login中专门的session表:jedis.hset(JsonKeyword.SESSION, username, sessionid);
     获取sessionid： hget session qiao

##2. regist中将把用户的基本信息传入到redis中.格式为 jedis.hmset(userLoginInfo.getUname(), userLoginInfoMap);
    将整个对象的信息都存到redis中,具体的数据都存到了userLoginInfoMap中
    注:用户信息插到了

## 3. tankHandler tankCodeHander mapHandler 向redis传输哪些数据
       
###    公共部分:
    username        redis可根据这两个数据进行用户的定义
    userSession

###    不同部分:
        tankHandler:(包含了坦克的基本信息  eg:攻击力...)
            "userTankInfoId":"1"
            "equpmentArmourId":"1",
            "equpmentTurretId":"2",
            "equpmentWhellId":"3",
            "equpmentEnginId":"4"
            
            什么时候查该数据 
                    login 会查一个默认的tank值
                    编辑的时候会进行修改。
        tankCodeHander
            "code": "qqqqqqq"
        mapHandler
          (k,v)==>userMapInfo.getRoom_id(), userMaps(Map<String.valueOf(mapLoad.getId()), mapLoad.toString()> )

## 4. 


