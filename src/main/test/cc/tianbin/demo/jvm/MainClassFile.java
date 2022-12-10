package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.ConstantValueAttribute;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.classpath.Classpath;
import io.github.nibnait.common.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static cc.tianbin.demo.jvm.utils.LogUtil.printf;
import static cc.tianbin.demo.jvm.utils.LogUtil.log;

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

    /**
     * 打印 解析 出来的 class 文件，实现 javap 命令
     */
    public static void printClassInfo(ClassFile classFile) {
        printf("\n");
        log("minor version: {}", classFile.getMinorVersion());
        log("major version: {}", classFile.getMajorVersion());
        log("flags: 0x{}, {}", classFile.getAccessFlag().getHexCodeStr(), classFile.getAccessFlag().getFlags());
        log("this_class: {}", classFile.getClassName());
        log("super_class: {}", classFile.getSuperClassName());
        printf("");

        ConstantPool constantPool = classFile.getConstantPool();
        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        printf("Constant pool: " + constantPool.getConstantPoolSize());
        for (int i = 1; i < constantPool.getConstantPoolSize(); i++) {
            if (constantInfos[i] != null) {
                printf("#%02d = %-20s\t%s", i, constantInfos[i].tag().getDescription(), constantInfos[i].printValue());
            }
        }
        printf("");

        log("interfaces_count: {}", classFile.getInterfaceNames().length);
        log("interfaces: ", DataUtils.toJsonStringArray(classFile.getInterfaceNames()));
        printf("");

        MemberInfo[] fields = classFile.getFields();
        log("fields_count: {}", fields.length);
        for (MemberInfo memberInfo : fields) {
            printf("  name: %s", memberInfo.getName());
            printf("    descriptor: %s", memberInfo.getDescriptor());
            printf("    flags: %s", memberInfo.getAccessFlag().getFlags());
            // 属性表
            for (AttributeInfo attribute : memberInfo.getAttributes()) {
                ConstantValueAttribute constantValueAttribute = (ConstantValueAttribute) attribute;
                ConstantInfo constantInfo = constantValueAttribute.getConstantValue();
                printf("    ConstantValue: %s %s", constantValueAttribute.attrName(),
                        constantInfo.tag().getDescription(),
                        constantInfo.printValue());
            }
        }
        printf("");

        MemberInfo[] methods = classFile.getMethods();
        log("methods_count: {}", methods.length);
        for (MemberInfo memberInfo : methods) {
            printf("  method: %s", memberInfo.getName());
            printf("    descriptor: %s", memberInfo.getDescriptor());
            printf("    flags: %s", memberInfo.getAccessFlag().getFlags());
            // 属性表
            for (AttributeInfo attribute : memberInfo.getAttributes()) {
                if (attribute instanceof CodeAttribute) {
                    CodeAttribute codeAttribute = (CodeAttribute) attribute;
                    printf("    Code:");
                    printf("      stack=%s, locals=%s", codeAttribute.getMaxStack(), codeAttribute.getMaxLocals());
                }
            }
        }
        printf("");

        AttributeInfo[] attributes = classFile.getAttributes();
        log("attributes_count: {}", attributes.length);
        for (int i = 0; i < attributes.length; i++) {
//            printf("%2d: %s", i, attributes[i].attrName());
        }
    }

}