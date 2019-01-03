package history.toJavacode.dy;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Created by  qiao
 * @date 18-4-24 下午7:36
 */

public class PostmanApp {
    public static void main(String[] args) throws Exception {
        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));

        // Obtain a Postman instance
        Postman postman = getPostman();

        while (true) {
            System.out.print("Enter a message: ");
            String msg = sysin.readLine();
            postman.deliverMessage(msg);
        }
    }

    private static Postman getPostman() {
        // Omit for now, will come back later
        Postman postman =new Postman() {
            @Override
            public void deliverMessage(String msg) {
                System.out.println(msg);
            }
        };
        return postman;
    }
}
