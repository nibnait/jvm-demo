package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classpath.Classpath;
import cc.tianbin.demo.jvm.exception.JvmException;
import cc.tianbin.demo.jvm.rtda.heap.classloader.JClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import lombok.extern.slf4j.Slf4j;

import static cc.tianbin.demo.jvm.utils.LogUtil.log;

/**
 * Created by nibnait on 2022/11/23
 */
@Slf4j
public class MainStartJVM {

    /**
     * ## 指令集和解释器
     * cd /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/instructions
     * javac GaussTest.java
     * javap -v GaussTest
     * --
     * -cp /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/instructions GaussTest
     * ---
     * ## 方法调用和返回
     * cd /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/ch07
     * javac InvokeDemo.java
     * javap -v InvokeDemo
     * -verbose:class -verbose:inst -cp /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/ch07 InvokeDemo
     * ---
     * ## 字符串和数组
     * cd /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/ch08
     * javac PrintArgs.java
     * -cp /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/ch08 PrintArgs 你好，世界！
     * ---
     * ## 本地方法调用
     * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-09\target\test-classes\org\itstack\demo\test\HelloWorld -verbose true -args 你好，java版虚拟机v1.0，欢迎你的到来。
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

        JClassLoader classLoader = new JClassLoader(classpath, args.verboseClassFlag);
        JClass clazz = classLoader.loadClass(className);

        // 查找类的 main() 方法
        Method mainMethod = clazz.getMainMethod();
        if (mainMethod == null) {
            throw new JvmException("Main method not found in class {}", args.classpath);
        }

        // 解释执行 main() 方法
        Interpreter.execute(mainMethod, args.verboseInstFlag, args.getAppArgs());
    }

}