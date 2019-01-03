#from enum import IntEnum, unique
#from math import sqrt


@unique
class Direction(IntEnum):
    up=1
    down=2
    left=3
    right=4

@unique
class MoveStatus(IntEnum):
    NormalDriving=1
    Attack=2
    Escape=3

class Environment:
    def __init__(self,isExistTank,localspot):
        self.__isExistTank=isExistTank
        self.__localSpot=localspot

    def __str__(self):
        return "isExistTank:"+str(self.__isExistTank)+" localspot:"+str(self.__localSpot)

    def getIsExistTank(self):
        return self.__isExistTank

    def getLocalSpot(self):
        return self.__localSpot


class Tank:
    def __init__(self,HP):
        self.__HP=HP

    def __str__(self):
        return "HP:"+str(self.__HP)

    def getHP(self):
        return self.__HP


class MyTank(Tank):
    def __init__(self,HP,localspot,direction,environment):
        super().__init__(HP)
        self.__localspot=localspot
        self.__direction=direction
        self.__environment=Environment(environment[0],environment[1])

    def __str__(self):
        return super().__str__()+" direction: "+str(self.__direction)+"   environment: "+\
               self.__environment.__str__()+"   localspot: "+str(self.__localspot)

    def getLocalspot(self):
        return self.__localspot

    def getDirection(self):
        return self.__direction

    def setDirection(self,direction):
        self.__direction=direction

    def getEnvironment(self):
        return self.__environment

    def setEnvironment(self,environment):
        self.__environment=Environment(environment[0],environment[1])

    def getDistance(self):
        return TankUtils.Distance(self.getLocalspot(),self.getEnvironment().getLocalSpot())

class TankUtils:
    @staticmethod
    def Distance(myTankLocalspot,otherTankLocalspot):
        x=abs(myTankLocalspot[0]-otherTankLocalspot[0])
        y=abs(myTankLocalspot[1]-otherTankLocalspot[1])
        return sqrt(x*x+y*y)












