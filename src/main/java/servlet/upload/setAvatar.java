package servlet.upload;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import servlet.BaseHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creator:henry
 * email:heneymark@gmail.com
 * time:16-12-1 下午8:14
 * description:
 */
@WebServlet("/upload/avatar")
public class setAvatar extends BaseHttpServlet {

    List piclist = new ArrayList();  //放上传的图片名

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isSystemLinux()) {
            LogD("Linux");
            LogD(System.getProperties().getProperty("os.name"));
        }
        if (isSystemWindows())
            LogD(System.getProperties().getProperty("os.name"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path=req.getRealPath("/heads");

        DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload sfu=new ServletFileUpload(factory);
        sfu.setHeaderEncoding("UTF-8");  //处理中文问题
        sfu.setSizeMax(1024*1024);   //限制文件大小

        try {
            List<FileItem> fileItems= sfu.parseRequest(req);  //解码请求 得到所有表单元素
            for (FileItem fi : fileItems) {
                //有可能是 文件，也可能是普通文字
                if (fi.isFormField()) { //这个选项是 文字
                    System.out.println("表单值为："+fi.getString());
                }else{
                    // 是文件
                    String fn=fi.getName();
                    System.out.println("文件名是："+fn);  //文件名
                    // fn 是可能是这样的 c:\abc\de\tt\fish.jpg
                    fi.write(new File(path,fn));

                    if (fn.endsWith(".jpg")) {
                        piclist.add(fn);  //把图片放入集合
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //去显示上传的文件
        req.setAttribute("pics", piclist);
        req.getRequestDispatcher("show").forward(req, resp);


    }
}
