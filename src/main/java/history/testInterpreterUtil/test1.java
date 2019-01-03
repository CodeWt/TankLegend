package history.testInterpreterUtil;

import history.InterpreterUtils.InterpreterUtil;

/**
 * @Created by  qiao
 * @date 18-4-16 下午2:41
 */

public class test1 {
    abstract class AbstractNode {
        public abstract String interpret();
    }
    class A extends AbstractNode{

        @Override
        public String interpret() {
            return "a1";
        }
    }

    class B extends AbstractNode{

        @Override
        public String interpret() {
            return "b1";
        }
    }

    class handle {
        private AbstractNode abstractNode;

        public void out() {
            String result = abstractNode.interpret();
            System.out.println(result);
        }

    }
    public static void main(String[] args) {

    }
}


