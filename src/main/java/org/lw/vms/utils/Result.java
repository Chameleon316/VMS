package org.lw.vms.utils;
import java.io.Serializable;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
/**
 * 统一API响应结果封装类。
 * 用于规范后端接口的返回格式，包含状态码、消息和数据。
 *
 * @param <T> 数据部分的泛型类型
 */
public class Result<T> implements Serializable {
    private Integer code; // 业务状态码，例如 200 表示成功，500 表示失败
    private String msg;   // 提示信息，例如 "操作成功", "用户名已存在"
    private T data;       // 返回给前端的数据体

    // 私有构造函数，强制使用静态工厂方法创建 Result 对象
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // --- 成功响应的静态工厂方法 ---

    /**
     * 创建一个成功的 Result 对象，包含数据和消息。
     *
     * @param data 返回的数据
     * @param msg  成功消息
     * @param <T>  数据类型
     * @return 成功的 Result 对象
     */
    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(200, msg, data);
    }

    /**
     * 创建一个成功的 Result 对象，只包含数据，默认消息为 "操作成功"。
     *
     * @param data 返回的数据
     * @param <T>  数据类型
     * @return 成功的 Result 对象
     */
    public static <T> Result<T> success(T data) {
        return success(data, "操作成功");
    }

    /**
     * 创建一个成功的 Result 对象，只包含消息，数据为 null。
     *
     * @param msg  成功消息
     * @param <T>  数据类型
     * @return 成功的 Result 对象
     */
    public static <T> Result<T> success(String msg) {
        return success(null, msg);
    }

    // --- 失败响应的静态工厂方法 ---

    /**
     * 创建一个失败的 Result 对象，包含消息，默认状态码 500。
     *
     * @param msg  失败消息
     * @param <T>  数据类型
     * @return 失败的 Result 对象
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    /**
     * 创建一个失败的 Result 对象，包含自定义状态码和消息。
     *
     * @param code 失败状态码
     * @param msg  失败消息
     * @param <T>  数据类型
     * @return 失败的 Result 对象
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    // --- Getter 和 Setter 方法 ---

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

