package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.classpath.Classpath;
import io.github.nibnait.common.utils.DataUtils;
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
     * -cp /Users/nibnait/github/jvm-demo/target/test-classes/cc/tianbin/demo/jvm/classfile ClassFileTest
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

            ClassFile classFile = new ClassFile(classData);
            printClassInfo(classFile);
        } catch (Exception e) {
            printf("Could not find or load main class " + args.getMainClass());
            e.printStackTrace();
        }
    }

    private static void printClassInfo(ClassFile classFile) {
        printf("\n");
        printf("version: " + classFile.getMajorVersion() + "." + classFile.getMinorVersion());

        ConstantPool constantPool = classFile.getConstantPool();
        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        printf("constants_count: " + constantPool.getConstantPoolSize());
        for (int i = 1; i < constantPool.getConstantPoolSize(); i++) {
            if (constantInfos[i] != null) {
                printf("%2d: %s", i, constantInfos[i]);
            }
        }
        printf("");

        printf("access_flags: 0x%x", classFile.getAccessFlags());
        log("this_class: {}", classFile.getClassName());
        log("super_class: {}", classFile.getSuperClassName());
        log("interfaces_count: {}", classFile.getInterfaceNames().length);
        log("interfaces: ", DataUtils.toJsonStringArray(classFile.getInterfaceNames()));
        MemberInfo[] fields = classFile.getFields();
        log("fields_count: {}" + fields.length);
        for (MemberInfo memberInfo : fields) {
            printf("  %s", memberInfo.name());
        }

        MemberInfo[] methods = classFile.getMethods();
        log("methods_count: {}", methods.length);
        for (MemberInfo memberInfo : methods) {
            printf("  %s\n", memberInfo.name());
        }

        AttributeInfo[] attributes = classFile.getAttributes();
        log("attributes_count: {}", attributes.length);
        for (int i = 0; i < attributes.length; i++) {
            printf("%2d: %s", i, attributes[i].attrName());
        }
    }

    private static void printf(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
    
    private static void log(String format, Object... args) {
        System.out.println(DataUtils.format(format, args));
    }

}