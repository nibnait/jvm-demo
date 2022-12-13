package cc.tianbin.demo.jvm._native;

import cc.tianbin.demo.jvm.rtda.Frame;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Created by nibnait on 2022/12/13
 */
@Slf4j
public class NativeMethod {

    private String methodName;
    private Object obj;

    public NativeMethod(String methodName, Object obj) {
        this.methodName = methodName;
        this.obj = obj;
    }

    public void invoke(Frame frame) {
        try {
            Method method = obj.getClass().getMethod(methodName, frame.getClass());
            method.invoke(obj, frame);
        } catch (Exception e) {
            log.error("NativeMethod.invoke error ", e);
        }
    }
}
