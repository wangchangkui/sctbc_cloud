package org.util;



/**
 * @author Myxiaowang
 */
public class ResponseResult<T> {

    private Integer code;
    
    private T data;
    
    private String message;

    private ResponseResult(){
    }

    /**
     * 正常数据返回
     *
     * @param data 数据对象
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(data);
        result.setCode(ResponseType.SUCCESS);
        return result;
    }


    /**
     * 正常数据返回
     *
     * @param data    数据对象
     * @param message 自定义成功信息
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(data);
        result.setCode(ResponseType.SUCCESS);
        result.setMessage(message);
        return result;
    }

    /**
     * 错误数据返回
     *
     * @param responseType 错误数据
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(ResponseType responseType) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(null);
        result.setCode(responseType.getCode());
        result.setMessage(ResponseType.convertTo(responseType.getCode()).getMessage());
        return result;
    }

    public static <T> ResponseResult<T> error(Integer code, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }


    /**
     * 错误数据返回
     *
     * @param responseType 错误数据
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(ResponseType responseType, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(data);
        result.setCode(responseType.getCode());
        result.setMessage(responseType.getMessage());
        return result;
    }

    public static <T> ResponseResult<T> error(ResponseType responseType, String message, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(data);
        result.setCode(responseType.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 错误数据返回
     *
     * @param responseType 错误数据
     * @param message      自定义错误信息
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(ResponseType responseType, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(null);
        result.setCode(responseType.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 判断是否是正确返回
     *
     * @return 正确返回则返回true, 否则返回false
     */
    public boolean success() {
        return ResponseType.SUCCESS.getCode().equals(this.code);
    }

    public void setCode(ResponseType responseType) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
    }

    public void setData(ResponseType responseType, T data) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
