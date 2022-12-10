package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.classpath.Classpath;
import cc.tianbin.demo.jvm.exception.JvmException;
import cc.tianbin.demo.jvm.rtda.heap.classloader.ClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import lombok.extern.slf4j.Slf4j;

import static cc.tianbin.demo.jvm.utils.LogUtil.log;

/**
 * Created by nibnait on 2022/11/23
 */
@Slf4j
public class MainStartJVM {

    /**
     * 指令集和解释器
     * cd /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/instructions
     * javac GaussTest.java
     * javap -v GaussTest
     * --
     * -cp /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/instructions GaussTest
     */
    public static void main(String[] argv) {
        Args args = Args.parse(argv);

        startJVM(args);
    }

    private static void startJVM(Args args) {
        Classpath classpath = new Classpath(args.jre, args.classpath);
        log("classpath: {}, class: {}, args: {}", classpath.getUserClasspath(),
                args.getMainClass(), args.getAppArgs());
        //获取className
        String className = args.getMainClass().replace(".", "/");

        ClassLoader classLoader = new ClassLoader(classpath);
        Class clazz = classLoader.loadClass(className);

        // 查找类的 main() 方法
        Method mainMethod = clazz.getMainMethod();
        if (mainMethod == null) {
            throw new JvmException("Main method not found in class {}", args.classpath);
        }

        // 解释执行 main() 方法
        Interpreter.execute(mainMethod);
    }

    private static ClassFile loadClass(Classpath classpath, String className) {
        try {
            byte[] classData = classpath.readClass(className);
            return new ClassFile(classData);
        } catch (Exception e) {
            log.error("Could not find or load main class {} ", className, e);
            throw new JvmException();
        }
    }

}