package history.python.newbee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.core.PyException;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;

/**
 * @Created by  qiao
 * @date 18-5-1 上午11:38
 */

public class SimpleEmbedded {
    public static void main(String[] args) {
        String py = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/java/history/python/newbee/Employee.py";
        // Java
        EmployeeType eType = (EmployeeType) JythonFactory.getInstance().getJavaObjectFromJythonFile("history.python.newbee.EmployeeType", py);
        System.out.println("Employee Name: " + eType.getEmployeeFirst() + " " + eType.getEmployeeLast());
        System.out.println("Employee ID: " + eType.getEmployeeId());

        // Jython
        PyObject pyObject = JythonFactory.getInstance().getPyObjectFromJythonFile("Employee", py);
        System.out.println("+++="+pyObject.invoke("getEmployeeId"));
        pyObject.invoke("setEmployeeId",new PyString("1999"));
        System.out.println("+++="+pyObject.invoke("getEmployeeId"));

        // Jython Function
        PyFunction pyFunction = JythonFactory.getInstance().getPyFunctionFromJythonFile("getNunmberValue", py);
        System.out.println("***="+pyFunction.__call__(new PyInteger(10)));




    }
}
