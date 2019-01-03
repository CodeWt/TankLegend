package history.python.jythonService;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

/**
 * @Created by  qiao
 * @date 18-5-1 上午11:16
 */

public class TankCodePYtest {
    public static void main(String[] args) {
        Object javaObject = null;
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys");
//        interpreter.exec("from enum import IntEnum, unique");
//        interpreter.exec("from math import sqrt");
        interpreter.exec("print sys");
        interpreter.execfile(pathUtils.MYAPI_PYTHON);
//        interpreter.exec("from history.python.sendServerTankCode import tankCodeImp");
//        interpreter.exec("from history.python.sendServerTankCode import MyTank, Direction");
        interpreter.execfile(pathUtils.USER_TANKCODE);

        interpreter.exec("userTankCode=tankCode()");
        PyObject pyObject = interpreter.get("userTankCode");
        String info ="{50;[5,1];left;[True,[10,8]]}";
        pyObject.invoke("myCodeHP", new PyString(info));
        Class Interface = null;
        try {
            Interface = Class.forName("history.python.jythonService.tankCodeImp");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        javaObject = pyObject.__tojava__(Interface);

        tankCodeImp tankCode = (tankCodeImp) javaObject;
        tankCode.getHPCodeLocalSpot();
    }
}
