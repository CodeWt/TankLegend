from history.python.sendServerTankCode import MyTank, Direction
from history.python.sendServerTankCode import tankCodeImp
class tankCode(tankCodeImp):
    def myCodeHP(mytank):
        print(mytank)
        if mytank.getHP() > 30:
            print(">30")
            if mytank.getEnvironment().getIsExistTank() == False:
                mytank.setDirection(Direction.up)
                print(mytank.getDirection())
                return mytank.getDirection()
            elif mytank.getEnvironment().getIsExistTank() == True:
                if (mytank.getDistance() < 10):
                    print(mytank.getDistance())
                    mytank.setDirection(Direction.left)
                    print(mytank.getDirection())
                    return mytank.getDirection()
        elif mytank.getHP() < 100:
            print("<100")
            return mytank.getDirection()
        else:
            print(">=30")
            return mytank.getDirection()