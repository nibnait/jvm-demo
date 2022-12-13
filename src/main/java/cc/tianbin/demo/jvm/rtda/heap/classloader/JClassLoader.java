package cc.tianbin.demo.jvm.rtda.heap.classloader;

import cc.tianbin.demo.jvm.classpath.Classpath;
import cc.tianbin.demo.jvm.common.CommonConstants;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.FieldDescriptor;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.utils.LogUtil;
import com.sun.tools.classfile.AccessFlags;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2022/12/08
 */
@Slf4j
public class JClassLoader {

/*
class names:
    - primitive types: boolean, byte, int ...
    - primitive arrays: [Z, [B, [I ...
    - non-array classes: java/lang/Object ...
    - array classes: [Ljava/lang/Object; ...
*/

    private Classpath classpath;
    private Map<String, JClass> classMap;
    @Getter
    private boolean verboseClassFlag;

    //------------------ 构造方法 ----------------------------
    public JClassLoader(Classpath classpath) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();
        this.verboseClassFlag = false;

        this.loadBasicClasses();
        this.loadPrimitiveClasses();
    }

    public JClassLoader(Classpath classpath, boolean verboseClassFlag) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();
        this.verboseClassFlag = verboseClassFlag;

        this.loadBasicClasses();
        this.loadPrimitiveClasses();
    }

    /**
     * 加载基本类（java.lang.Class, java.lang.Object）
     */
    private void loadBasicClasses() {
        // 先加载 java.lang.Class 类，顺便触发 java.lang.Object 等类和接口的加载
        JClass jlClassClass = this.loadClass(CommonConstants.JAVA_LANG_CLASS);
        // 然后遍历 classMap，给已加载的每个类关联类对象
        for (Map.Entry<String, JClass> entry : this.classMap.entrySet()) {
            JClass clazz = entry.getValue();
            if (clazz.jClass == null) {
                clazz.jClass = jlClassClass.newObject();
                clazz.jClass.extra = clazz;
            }
        }
    }

    /**
     * 加载 void 和 8个基本数据类型
     */
    private void loadPrimitiveClasses() {
        for (Map.Entry<String, String> entry : FieldDescriptor.PRIMITIVE_TYPE.entrySet()) {
            loadPrimitiveClass(entry.getKey());
        }
    }

    private void loadPrimitiveClass(String className) {
        JClass clazz = new JClass(AccessFlags.ACC_PUBLIC,
                className,
                this,
                true);
        clazz.jClass = this.classMap.get(CommonConstants.JAVA_LANG_CLASS).newObject();
        clazz.jClass.extra = clazz;
        this.classMap.put(className, clazz);
    }

    //------------------ loadClass ----------------------------
    public JClass loadClass(String className) {
        LogUtil.log("ClassLoader.loadClass {}", className);
        JClass clazz = classMap.get(className);
        if (clazz != null) {
            return clazz;
        }

        try {
            if (className.startsWith(FieldDescriptor.A_REF.getCode())) {
                // '[' 数组标识
                clazz = this.loadArrayClass(className);
            } else {
                clazz = this.loadNonArrayClass(className);
            }
        } catch (Exception e) {
            log.error("ClassLoader loadClass error ", e);
        }

        JClass jlClass = this.classMap.get(CommonConstants.JAVA_LANG_CLASS);
        if (jlClass != null && clazz != null) {
            // 给类关联类对象
            clazz.jClass = jlClass.newObject();
            clazz.jClass.extra = clazz;
        }

        return clazz;
    }

    private JClass loadArrayClass(String className) {
        JClass clazz = new JClass(AccessFlags.ACC_PUBLIC,
                className,
                this,
                true,
                this.loadClass(CommonConstants.JAVA_LANG_OBJECT),
                new JClass[]{this.loadClass(CommonConstants.JAVA_LANG_CLONEABLE), this.loadClass(CommonConstants.JAVA_IO_SERIALIZABLE)});
        this.classMap.put(className, clazz);
        return clazz;
    }

    private JClass loadNonArrayClass(String className) throws Exception {
        // 1.2 加载字节码
        // 通过一个类的全限定名来获取定义此类的二进制字节流。
        byte[] bytecode = this.classpath.readClass(className);
        if (bytecode == null) {
            throw new ClassNotFoundException(className);
        }

        // 1.2 加载到方法区
        // 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构。
        JClass clazz = LoadingHelper.loadClass(bytecode, this);

        // 1.3 在内存中生成一个代表这个类的java.lang.Class对象，作为方法区这个类的各种数据的访问入口。
        this.classMap.put(clazz.getName(), clazz);

        // 2. 链接: 验证 + 准备 + 解析
        LinkingHelper.verify(clazz);
        LinkingHelper.prepare(clazz);

        if (this.verboseClassFlag) {
            LogUtil.printf("[Loaded %s from %s]", className, bytecode);
        }

        return clazz;
    }

}
