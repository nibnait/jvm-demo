package cc.tianbin.demo.jvm.common;

/**
 * Created by nibnait on 2022/12/10
 */
public class CommonConstants {

    private CommonConstants() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static final String INIT = "<init>";
    public static final String CLINIT = "<clinit>";
}
