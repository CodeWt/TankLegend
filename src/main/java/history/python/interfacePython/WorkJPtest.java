package history.python.interfacePython;


import history.python.interfacePython.Worker;
import org.python.core.*;
import org.python.util.PythonInterpreter;

/**
 * @Created by  qiao
 * @date 18-4-30 上午11:04
 */

public class WorkJPtest {

    public static void main(String[] args) {
        /*接受已经*/
        Object javaObject = null;
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.execfile("/home/qiao/IdeaProjects/tanklegendnetty/src/main/java/history/python/interfacePython/WorkerJP.py");
        interpreter.exec("worker=Worker()");
        PyObject pyObject = interpreter.get("worker");
        pyObject.invoke("setId", new PyString("8888"));
        Class Interface = null;
        try {
            Interface = Class.forName("history.python.interfacePython.Worker");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        javaObject = pyObject.__tojava__(Interface);


        Worker worker = (Worker) javaObject;
        System.out.println("Name:" + worker.getFirstName() + worker.getLastName());
        System.out.println("ID:" + worker.getId());

    }
}
