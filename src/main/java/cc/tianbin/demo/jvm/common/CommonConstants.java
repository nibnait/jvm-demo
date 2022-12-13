package cc.tianbin.demo.jvm.common;

/**
 * Created by nibnait on 2022/12/10
 */
public class CommonConstants {

    private CommonConstants() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static final String JAVA_LANG_CLASS = "java/lang/Class";
    public static final String JAVA_LANG_OBJECT = "java/lang/Object";
    public static final String JAVA_LANG_CLONEABLE = "java/lang/Cloneable";
    public static final String JAVA_IO_SERIALIZABLE = "java/io/Serializable";

    public static final String INIT = "<init>";
    public static final String CLINIT = "<clinit>";

}
