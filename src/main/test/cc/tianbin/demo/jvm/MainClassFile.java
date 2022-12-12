package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.classpath.Classpath;
import cc.tianbin.demo.jvm.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static cc.tianbin.demo.jvm.utils.LogUtil.printf;

/**
 * Created by nibnait on 2022/11/23
 */
@Slf4j
public class MainClassFile {

    /**
     * Edit configurations -> program arguments
     * -help
     * -version
     * -cp foo/bar MyApp arg1 arg2
     * -XJre /Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre java.lang.Object
     * ----
     * 解析 class 文件
     * cd /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/classfile
     * javac ClassFileTest.java
     * javap -v ClassFileTest
     * --
     * -cp /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/classfile ClassFileTest
     * -cp /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/instructions GaussTest
     * -cp /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/ch07 InvokeDemo
     */
    public static void main(String[] argv) {
        Args args = Args.parse(argv);
        if (!args.ok || args.helpFlag) {
            log.info("Usage: <main class> [-options] class [args...]");
        } else if (args.versionFlag) {
            log.info("java version \"1.8.0\"");
        } else {
            startJVM(args);
        }
    }

    private static void startJVM(Args args) {
        Classpath classpath = new Classpath(args.jre, args.classpath);
        log.info("bootstrapClasspath: {}", classpath.getBootstrapClasspath());
        log.info("extensionClasspath: {}", classpath.getExtensionClasspath());
        log.info("userClasspath: {}", classpath.getUserClasspath());
        log.info("class: {}, args: {}", args.getMainClass(), args.getAppArgs());

        //获取className
        String className = args.getMainClass().replace(".", "/");
        try {
            byte[] classData = classpath.readClass(className);
            printf("class data 十进制: " + Arrays.toString(classData));

            printf("class data 十六进制: ");
            for (byte b : classData) {
                //16进制输出
                System.out.print((String.format("%02x", b & 0xff) + " "));
            }
            printf("");

            ClassFile classFile = new ClassFile(classData);
            LogUtil.printClassInfo(classFile);
        } catch (Exception e) {
            log.error("Could not find or load main class {} ", args.getMainClass(), e);
        }
    }

}