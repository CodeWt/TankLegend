package history.toJavacode;


import java.io.*;

/**
 * @Created by  qiao
 * @date 18-4-24 下午6:49
 */

public class testToJava {

    public static void main(String[] args) {
//        testToJava a = new testToJava();
//        a.createFile();

        String content = "if (tank.HP > 50) {\n" +
                "            tank.dirextion=up;\n" +
                "        }\r\n";
//        String codeName = "userName" + "TankCode.java";
//        File tankCode =new File("toJavaCode1.txt");
//        System.out.println(tankCode.toURI());

        try {
            String path = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/java/history/toJavacode";
            String str = "Tankcode12.java";
            insert("Tankcode1234.java", 1, content);
            System.out.println("插入成功");
        } catch (IOException e) {
            System.out.println("代码插入失败");
            e.printStackTrace();
        }

    }

    private static void insert(String fileName, int position, String insertContent) throws IOException {
//        String path = "/home/qiao/xm";
//
//        File f = new File(path);
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//        String fileName1 = "Tankcode2.java";
//        File file = new File(f, fileName1);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
        File tmp = File.createTempFile("tmp1111", null);
        /*将临时文件在结束时删除*/
        tmp.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        FileOutputStream tmpOut = new FileOutputStream(tmp);
        FileInputStream tmpIn = new FileInputStream(tmp);
        raf.seek(position);
        byte[] bbuf = new byte[64];
        int hasRead = 0;
        /*写入临时文件*/
        while ((hasRead = raf.read(bbuf)) > 0) {
            tmpOut.write(bbuf, 0, hasRead);
        }
        raf.seek(position);
        /*写到指定文件*/
        raf.write(insertContent.getBytes());
        while ((hasRead = tmpIn.read(bbuf)) > 0) {
            raf.write(bbuf, 0, hasRead);
        }
        tmpIn.close();
        tmpOut.close();
        raf.close();
    }
}
