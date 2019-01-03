package history.python;

import org.python.core.*;
import org.python.util.PythonInterpreter;

/**
 * @Created by  qiao
 * @date 18-4-23 下午4:32
 */

public class HelloPython {
    public static void main(String[] args) {
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile("/home/qiao/IdeaProjects/tanklegendnetty/src/main/java/history/python/hello.py");
//        PyFunction pyFunction = interpreter.get("hello", PyFunction.class); // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
//        int a =1;
//        int b =2;
//        PyObject pyObject = pyFunction.__call__(); // 调用函数
//
//        System.out.println(pyObject);


        /*second*/
//        PythonInterpreter interpreter=new PythonInterpreter();
//        PySystemState sys = Py.getSystemState();
//
//        // 将 Jython 库加入系统的 classpath 中或直接通过这种方式动态引入
////        sys.path.add("D:\\jython2.5.2\\Lib");
//
//        // 执行算法所在的 python 文件
//        interpreter.execfile("/home/qiao/IdeaProjects/tanklegendnetty/src/main/java/history/python/demo.py");
//
//        PyFunction pyFunction = (PyFunction)interpreter.get("getValue",PyFunction.class );
//
//        System.out.println("Result: "+pyFunction.__call__(new PyInteger(4)));


        /*three*/
        PythonInterpreter interp =new PythonInterpreter();
        System.out.println("Hello, brave new world");
        /*导入包 和 执行 命令*/
        interp.exec("import sys");
        interp.exec("print sys");
//        interp.exec("import IntEnum");
//        interp.exec("print IntEnum");
        interp.set("a", new PyInteger(42));
        interp.exec("print a");
        interp.exec("x = 2+2");
        PyObject x = interp.get("x");
        System.out.println("x: "+x);
        System.out.println("Goodbye, cruel world");

    }
}

/*
*
* 由于在项目需要执行Python，找寻相关资料，总结出以下几种方式：

直接执行Python脚本代码
　　　　引用 org.python包

1 PythonInterpreter interpreter = new PythonInterpreter();
2 interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");   ///执行python脚本
　　

　　2.  执行python .py文件

1 PythonInterpreter interpreter = new PythonInterpreter();
2 InputStream filepy = new FileInputStream("D:\\demo.py");
3 interpreter.execfile(filepy);  ///执行python py文件
4 filepy.close();
　　

　　3.  使用Runtime.getRuntime()执行脚本文件

　　　　 这种方式和.net下面调用cmd执行命令的方式类似。如果执行的python脚本有引用第三方包的，建议使用此种方式。使用上面两种方式会报错java ImportError: No module named arcpy。

1    Process proc = Runtime.getRuntime().exec("python  D:\\demo.py");
2    proc.waitFor();*/
