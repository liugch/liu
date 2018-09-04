package io;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IoTest {

    /**
     *  reader  和 write
     */
    @Test
    public void cope() {	
        // 字符输入流
        File file = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        // 字符输出流
        File file2 = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            file = new File("C:/Users/Administrator/Desktop/buzhu.txt");
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            file2 = new File("C:/Users/Administrator/Desktop/buzhu2.txt");
            if (!file2.exists()) {
                file2.createNewFile();
            }
            fileWriter = new FileWriter(file2);
            bufferedWriter = new BufferedWriter(fileWriter);
            String buffer;

            while ((buffer = bufferedReader.readLine()) != null) {
                System.out.println(buffer);
                bufferedWriter.write(buffer, 0, buffer.length());
                bufferedWriter.newLine();  // 划行
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                bufferedWriter.close();
                fileWriter.close();

                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * inputsteam outputstream
     */
    @Test
    public void cope2(){
        BufferedInputStream bufferedInputStream = null;
        FileInputStream fileInputStream = null;

        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            fileInputStream  = new FileInputStream("C:/Users/Administrator/Desktop/buzhu.txt");
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            file = new File("C:/Users/Administrator/Desktop/buzhu3.txt") ;
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);


            // 从输入流读取而没有阻塞
            int len = 0;
            byte[] buff = new byte[1024];

            // 输入流中的数据输入到
            if((len=bufferedInputStream.read(buff))!=-1){
                System.out.println(new String(buff,0,buff.length));
                bufferedOutputStream.write(buff,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                fileOutputStream.close();

                bufferedInputStream.close();
                fileInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *  遍历一个目录下的所有.text 文件并输出 文件名
     * @throws Exception
     */
    public void show(File file){
        File[] files = file.listFiles();
        for (File file1 : files) {
           if(file1.isDirectory()){
                 show(file1);
           }else {
               if(file1.getName().contains(".png")){
                   System.out.println( file1.getAbsolutePath());
               }
           }
        }
    }

    @Test
    public void showname(){

        show(new File("F:/myphoto")) ;
        
    }











    @Test
    public void pagelist() throws Exception {
        String[] array = new String[2];
        List list = new ArrayList();
        list.size();
        for (int i = 0; i < list.size(); i++) {

        }
        for (Object i : list) {
            System.out.println(i);
        }

        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }
}
