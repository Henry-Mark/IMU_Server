package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbl on 2016/11/23.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/23. 17:30
 * description: 返回的Json实体
 */
public class Response<T> {

    private int code;
    private T data;
    private List<T> datas = new ArrayList<T>();

    public Response(int code, T data) {
        this.code = code;
        this.data = data;
        this.datas = null;
    }

    public Response(int code, List<T> datas) {
        this.code = code;
        this.datas = datas;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<T> getDatas() {
        return datas;
    }
}
