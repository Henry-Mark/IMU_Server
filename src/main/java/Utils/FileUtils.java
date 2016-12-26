package Utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wangbl on 2016/12/26.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/26. 17:00
 * description: 文件辅助类
 */
public class FileUtils {
    /* 项目作用路径根目录 */
    public static final String ROOTPATH = "D:" + File.separator + "IMU";
    /* 图片路径地址 */
    public static final String IMGPATH = ROOTPATH + File.separator + "img";

    /**
     * 创建文件
     *
     * @param fileName
     * @return
     */
    public static boolean createFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            LogUtils.d("create file " + fileName + "failed,because it existed!");
            return false;
        }
        if (fileName.endsWith(File.separator)) {
            LogUtils.d("create file " + fileName + "failed，becase it is a direction");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            LogUtils.d("The directory that contains the destination file does not exist, ready to create it!");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("Failed to create the target directory where the file resides!");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                LogUtils.d("create file " + fileName + "success!");
                return true;
            } else {
                LogUtils.d("create file " + fileName + "failed!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d("create file " + fileName + "failed! " + e.getMessage());
            return false;
        }

    }

    /**
     * 创建文件目录
     *
     * @param dirName
     * @return 已存在或创建成功返回true，否则返回false
     */
    public static boolean createDir(String dirName) {
        File dir = new File(dirName);
        if (dir.exists()) {
            LogUtils.d("the dir " + dirName + " existed!");
            return true;
        }

        /*以文件分隔符结尾*/
        if (!dirName.endsWith(File.separator)) {
            dirName += File.separator;
        }

        if (dir.mkdir()) {
            LogUtils.d("dir " + dirName + " mkdir success!");
            return true;
        } else {
            LogUtils.d("dir " + dirName + " mkdir failed!");
            return false;
        }

    }


}
