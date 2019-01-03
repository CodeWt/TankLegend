from history.python.sendServerTankCode import Tank, MyTank, Direction
from history.python.userTankCodePY import myCodeHP

if __name__=="__main__":
    mytank=MyTank(50,[5,1],"left",[True,[10,8]])
    code=tankCode()
    code.myCodeHP(mytank)
