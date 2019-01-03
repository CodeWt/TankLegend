package history.redisTest1;//package history.redisTest1;
//
////import log.aaa;
//
//
///**
// * @Created by  qiao
// * @date 18-3-8 下午9:33
// */
//
//public class TestStatic {
//    public static void main(String[] args) throws Exception {
////        String value ="aaa";
////        Class clazz = Class.forName("log."+value);
////        Constructor constructor = clazz.getConstructor();
////        System.out.println(clazz.getSimpleName());
//
////        aaa a =new aaa();
////        if () {
////            System.out.println("==========");
////        }
////        a = (aaa) constructor.newInstance();
////
////        Method channelRead = clazz.getDeclaredMethod("dis", String.class);
////        channelRead.invoke(a, "aaaaa");
////        Class cl = Class.forName("log.aaa");
////        bbb b =new bbb("a","b");
////        Method method = cl.getDeclaredMethod("dis", bbb.class);
////        Object o = method.invoke(cl.newInstance(), b);
////        System.out.println(o);
//
//        Reflectiondemo r = new Reflectiondemo();
////        Object o1 = r.getProperty(cl.getName(), "a");
////        System.out.println(o1.toString());
//
////        Class cb =Class.forName("log.bbb");
//
////        bbb newb= (bbb)r.newInstance("log.bbb",arg);
////        System.out.println(newb.getB());
////        System.out.println(newb.toString());
////        System.out.println(cb.getName());
//        String uri = "log.bbb";
//        /*通过uri获取类名*/
//        /*获得对象所需的参数arg*/
//        /*执行对象的函数.*/
//        String value=uri.substring(4);
//        System.out.println("value=" + value);
//        /*获得对象所需的参数arg*/
//        String[] arg = {"1", "2"};
//        Object o2= r.newInstance("log.bbb",arg);
//        /*执行对象的函数.*/
//        if (value.equals("bbb")){
//            bbb b = (bbb) o2;
////            r.invokeMethod(b, "add");
//        } else if (value.equals("aaa")) {
//            aaa a1 = (aaa) o2;
//        }
//        System.out.println(o2.toString());
//        String redispoll = "log.aaa.bbb";
//        Object o3 =r.newInstance(redispoll,arg);
//        System.out.println(o3.toString());
//
//    }
//}
