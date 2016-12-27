package servlet.upload;

import servlet.BaseHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by wangbl on 2016/12/27.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/27. 11:40
 * description:
 */
@WebServlet("/uploadAvatar")
public class upAvatar extends BaseHttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        processEntity(AVATARPATH,req);
//        req.setCharacterEncoding("utf-8");  //设置编码
//        //获得磁盘文件条目工厂
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//        File file = new File(AVATARPATH);
//        factory.setRepository(file);
//        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
//        factory.setSizeThreshold(1024 * 1024);
//        //高水平的API文件上传处理
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        try {
//            //可以上传多个文件
//            List<FileItem> fileItems = (List<FileItem>) upload.parseRequest(req);
//            for (FileItem item : fileItems) {
//                //获取表单的属性名字
//                String name = item.getFieldName();
//                //如果获取的 表单信息是普通的 文本 信息
//                if (item.isFormField()) {
//                    //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
//                    String value = item.getString();
//
//                    req.setAttribute(name, value);
//                } else {     //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
//                    /**
//                     * 以下三步，主要获取 上传文件的名字
//                     */
//                    //获取路径名
//                    String value = item.getName();
//                    //索引到最后一个反斜杠
//                    int start = value.lastIndexOf("\\");
//                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
//                    String filename = value.substring(start + 1);
//
//                    req.setAttribute(name, filename);
//
//                    //真正写到磁盘上
//                    //它抛出的异常 用exception 捕捉
//
//                    item.write(new File(AVATARPATH, filename));//第三方提供的
//                }
//            }
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }



    private void processEntity(String dir, HttpServletRequest request) {
        try {
            File uploadFile = new File(dir +File.separator+ "a-upload.jpg");
            InputStream input = request.getInputStream();
            OutputStream output = new FileOutputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int n = -1;
            while ((n = input.read(buffer)) != -1) {
                LogD("n="+n);
                if (n > 0) {
                    output.write(buffer, 0, n);
                }
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
