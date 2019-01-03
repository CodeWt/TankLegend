package history.python;

import org.python.core.PyFunction;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.Properties;

/**
 * @Created by  qiao
 * @date 18-4-30 上午10:43
 */

public class demoJython {
    public static void main(String[] args) {
        PythonInterpreter interpreter=new PythonInterpreter();
//        PySystemState sys = Py.getSystemState();
//        sys.path.add("D:\\jython2.5.2\\Lib");

        //‘re’是 Python 自身提供的正则表达式库，在 Python 方法中我们需要用到这个库中的方法，所以需要提前导入进来
        interpreter.exec("import re");

        interpreter.execfile("/home/qiao/IdeaProjects/tanklegendnetty/src/main/java/history/python/demo.py");
        PyFunction pyFunction = (PyFunction)interpreter.get("getconfig",PyFunction.class );

        System.out.println("Config value: "+pyFunction.__call__(new PyString("MaxValue")));
    }



}
