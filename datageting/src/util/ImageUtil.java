 package util;
 import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 照片工具类
 * @author VRAR
 *
 */
 public class ImageUtil {
 
     // 读取图片获取输入流
     public static FileInputStream readImage(String path) throws IOException {
         return new FileInputStream(new File(path));
     }
 
     // 读取表中图片获取输出流
     public static void readBin2Image(InputStream in, String targetPath) {
         File file = new File(targetPath);
         String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
         if (!file.exists()) {
             new File(path).mkdir();
         }
         FileOutputStream fos = null;
         try {
             fos = new FileOutputStream(file);
             int len = 0;
             byte[] buf = new byte[1024];
             while ((len = in.read(buf)) != -1) {
                 fos.write(buf, 0, len);
             }
             fos.flush();
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if (null != fos) {
                 try {
                     fos.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
     }
     
     /**
      * 根据httpurl获取图片流
      * @param httpUrl
      * @return
      * @throws Exception
      */
     public static InputStream getImageFromHttp(String httpUrl) throws Exception{
    	//new一个URL对象  
         URL url = new URL(httpUrl);  
         //打开链接  
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
         //设置请求方式为"GET"  
         conn.setRequestMethod("GET");  
         //超时响应时间为10秒  
         conn.setConnectTimeout(10 * 1000);  
         //通过输入流获取图片数据  
         return conn.getInputStream();  
     }
 }