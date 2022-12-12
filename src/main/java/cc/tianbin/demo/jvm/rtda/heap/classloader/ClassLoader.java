package cc.tianbin.demo.jvm.rtda.heap.classloader;

import cc.tianbin.demo.jvm.classpath.Classpath;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.utils.LogUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2022/12/08
 */
@Slf4j
public class ClassLoader {

/*
class names:
    - primitive types: boolean, byte, int ...
    - primitive arrays: [Z, [B, [I ...
    - non-array classes: java/lang/Object ...
    - array classes: [Ljava/lang/Object; ...
*/

    private Classpath classpath;
    private Map<String, Class> classMap;
    @Getter
    private boolean verboseClassFlag;

    public ClassLoader(Classpath classpath) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();
        this.verboseClassFlag = false;
    }

    public ClassLoader(Classpath classpath, boolean verboseClassFlag) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();
        this.verboseClassFlag = verboseClassFlag;
    }

    public Class loadClass(String className) {
        LogUtil.log("ClassLoader.loadClass {}", className);
        Class clazz = classMap.get(className);
        if (clazz != null) {
            return clazz;
        }
        try {
            return loadNonArrayClass(className);
        } catch (Exception e) {
            log.error("ClassLoader loadClass error ", e);
        }
        return null;
    }

    private Class loadNonArrayClass(String className) throws Exception {
        // 1.2 加载字节码
        // 通过一个类的全限定名来获取定义此类的二进制字节流。
        byte[] bytecode = this.classpath.readClass(className);
        if (bytecode == null) {
            throw new ClassNotFoundException(className);
        }

        // 1.2 加载到方法区
        // 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构。
        Class clazz = LoadingHelper.loadClass(bytecode, this);

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
