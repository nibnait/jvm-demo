package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classpath.Classpath;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Created by nibnait on 2022/11/23
 */
@Slf4j
public class Main {

    /**
     * Edit configurations -> program arguments
     * -help
     * -version
     * -cp foo/bar MyApp arg1 arg2
     * -XJre /Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre java.lang.Object
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
        Classpath cp = new Classpath(args.jre, args.classpath);
        log.info("classpath: {}, class: {}, args: {}\n", cp, args.getMainClass(), args.getAppArgs());

        //获取className
        String className = args.getMainClass().replace(".", "/");
        try {
            byte[] classData = cp.readClass(className);
            log.info("class data 十进制: " + Arrays.toString(classData));

            log.info("class data 十六进制: ");
            for (byte b : classData) {
                //16进制输出
                System.out.print((String.format("%02x", b & 0xff) + " "));
            }
        } catch (Exception e) {
            log.info("Could not find or load main class " + args.getMainClass());
            e.printStackTrace();
        }
    }

}