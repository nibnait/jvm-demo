package cc.tianbin.demo.jvm.utils;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.ConstantValueAttribute;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import io.github.nibnait.common.utils.DataUtils;

/**
 * Created by nibnait on 2022/12/07
 */
public class LogUtil {

    private LogUtil() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static void printf(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static void log(String format, Object... args) {
        System.out.println(DataUtils.format(format, args));
    }

    /**
     * 打印 解析 出来的 class 文件，实现 javap 命令
     */
    public static void printClassInfo(ClassFile classFile) {
        log("-------------- printClassInfo --------------");
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

        log("============================================");
    }
}
