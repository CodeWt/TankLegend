package history.toJavacode.dy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @Created by  qiao
 * @date 18-4-24 下午7:35
 */

public class PostmanImpl implements Postman{
    private PrintStream output;

//    public PostmanImpl() {
//        output = System.out;
//    }
//
//    public void deliverMessage(String msg) {
//        output.println("   " + msg);
//        output.flush();
//    }

    // Start of modification
    public PostmanImpl() throws IOException {
        output = new PrintStream(new FileOutputStream("msg.txt"));
    }
    // End of modification

    public void deliverMessage(String msg) {
        output.println("   " + msg);

        output.flush();
    }
}
