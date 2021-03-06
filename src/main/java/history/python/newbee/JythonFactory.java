package history.python.newbee;

import java.util.HashMap;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
/**
 * @Created by  qiao
 * @date 18-5-1 上午11:35
 */

public class JythonFactory {
    private static final JythonFactory instance = new JythonFactory();
    private static final HashMap<String, PythonInterpreter> piMap = new HashMap<String, PythonInterpreter>();
    public static JythonFactory getInstance() {
        return instance;
    }
    public Object getJavaObjectFromJythonFile(String interfaceName, String pathToJythonModule) {
        Object javaObject = null;

        PythonInterpreter interpreter = cacheInterpreter(pathToJythonModule);

        String tempName = pathToJythonModule.substring(pathToJythonModule.lastIndexOf("/") + 1);
        tempName = tempName.substring(0, tempName.indexOf("."));
        System.out.println(tempName);
        String instanceName = tempName.toLowerCase();
        String javaClassName = tempName.substring(0, 1).toUpperCase() + tempName.substring(1);
        String objectDef = "=" + javaClassName + "()";
        interpreter.exec(instanceName + objectDef);
        try {
            Class JavaInterface = Class.forName(interfaceName);
            javaObject = interpreter.get(instanceName).__tojava__(JavaInterface);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(); // Add logging here
        }
        return javaObject;
    }
    public PyObject getPyObjectFromJythonFile(String typeName, String pathToJythonModule) {
        PyObject pyObject = null;
        PythonInterpreter interpreter = cacheInterpreter(pathToJythonModule);

        String instanceName = typeName.toLowerCase();
        String objectDef = "=" + typeName + "()";
        interpreter.exec(instanceName + objectDef);
        pyObject = interpreter.get(instanceName);
        return pyObject;
    }

    public PyFunction getPyFunctionFromJythonFile(String funcName, String pathToJythonModule) {
        PyFunction pyFunction = null;
        PythonInterpreter interpreter = cacheInterpreter(pathToJythonModule);
        pyFunction = (PyFunction)interpreter.get(funcName,PyFunction.class);
        return pyFunction;
    }

    private PythonInterpreter cacheInterpreter(String pathToJythonModule) {
        PythonInterpreter interpreter = null;
        if (piMap.get(pathToJythonModule)!=null) {
            interpreter = piMap.get(pathToJythonModule);
        } else {
            interpreter = new PythonInterpreter();
            interpreter.execfile(pathToJythonModule);
        }
        return interpreter;
    }
}
