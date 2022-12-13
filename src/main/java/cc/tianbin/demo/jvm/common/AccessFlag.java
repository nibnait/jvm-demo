package cc.tianbin.demo.jvm.common;

import cc.tianbin.demo.jvm.rtda.heap.methodarea.FieldDescriptor;
import cc.tianbin.demo.jvm.utils.NumberUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nibnait on 2022/12/04
 */
public class AccessFlag {

    @Getter
    private final int code;
    @Getter
    private final List<Flag> flags;

    public AccessFlag(int code, FlagType flagType) {
        this.code = code;
        this.flags = getByCode(code, flagType);
    }

    public String getHexCodeStr() {
        return NumberUtil.int2HexString(this.code);
    }

    //------------ accessFlags ----------------------------------
    public boolean isPublic() {
        return (this.code & Flag.ACC_PUBLIC.getCode()) != 0;
    }

    public boolean isPrivate() {
        return (this.code & Flag.ACC_PRIVATE.getCode()) != 0;
    }

    public boolean isProtected() {
        return (this.code & Flag.ACC_PROTECTED.getCode()) != 0;
    }

    public boolean isStatic() {
        return (this.code & Flag.ACC_STATIC.getCode()) != 0;
    }

    public boolean isFinal() {
        return (this.code & Flag.ACC_FINAL.getCode()) != 0;
    }

    public boolean isSuper() {
        return (this.code & Flag.ACC_SUPER.getCode()) != 0;
    }

    public boolean isNative() {
        return (this.code & Flag.ACC_NATIVE.getCode()) != 0;
    }

    public boolean isInterface() {
        return (this.code & Flag.ACC_INTERFACE.getCode()) != 0;
    }

    public boolean isAbstract() {
        return (this.code & Flag.ACC_ABSTRACT.getCode()) != 0;
    }

    public boolean isAnnotation() {
        return (this.code & Flag.ACC_ANNOTATION.getCode()) != 0;
    }

    public boolean isEnum() {
        return (this.code & Flag.ACC_ENUM.getCode()) != 0;
    }

    //------------ accessFlags ----------------------------------

    private static List<Flag> getByCode(int code, FlagType flagType) {
        List<Flag> list = new ArrayList<>();
        for (Flag flag : Flag.getByType(flagType)) {
            if ((flag.code & code) != 0) {
                list.add(flag);
            }
        }
        return list;
    }

    @Getter
    @AllArgsConstructor
    public enum FlagType {
        CLASS,
        INNER_CLASS,
        FIELD,
        METHOD
    }

    @Getter
    @AllArgsConstructor
    public enum Flag {
        ACC_PUBLIC(0x0001, "申明 public; 可以从外部的其他包访问"),                                                  // class field method
        ACC_PRIVATE(0x0002, "申明 private; 仅在定义类中可用"),                                                     //       field method
        ACC_PROTECTED(0x0004, "申明 protected; 可以在子类中访问"),                                                 //       field method
        ACC_STATIC(0x0008, "申明 static."),                                                                     //       field method
        ACC_FINAL(0x0010, "申明 final; 不允许有子类，不允许被覆盖"),                                                 // class field method
        ACC_SUPER(0x0020, "当被 invokespecial 指令调用时，特别处理超类方法"),                                        // class
        ACC_SYNCHRONIZED(0x0020, "申明 synchronized; 调用由 monitor 使用包装"),                                    //             method
        ACC_VOLATILE(0x0040, "申明 volatile; 无法缓存"),                                                          //       field
        ACC_BRIDGE(0x0040, "用于指示编译器为Java编程语言生成的桥接方法"),                                             //             method
        ACC_TRANSIENT(0x0080, "申明 transient; 不由持久对象管理器写入或读取"),                                       //       field
        ACC_VARARGS(0x0080, "用可变数量的参数声明"),                                                               //             method
        ACC_NATIVE(0x0100, "申明 native; 用Java之外的语言实现"),                                                   //             method
        ACC_INTERFACE(0x0200, "是接口，而不是类"),                                                                // class
        ACC_ABSTRACT(0x0400, "申明 abstract; 不允许被实例化，不提供任何实现"),                                       // class       method
        ACC_STRICT(0x0800, "申明 strictfp; 浮点模式是FP严格的（FP-strict）"),                                      //             method
        ACC_SYNTHETIC(0x1000, "申明 synthetic; 源代码中不存在"),                                                  // class field method
        ACC_ANNOTATION(0x2000, "注解."),                                                                        // class
        ACC_ENUM(0x4000, "枚举"),                                                                               // class field
        ;

        private final int code;
        private final String description;

        // 类的 access flag, https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1
        private static final List<Flag> CLASS_ACCESS_FLAG = Lists.newArrayList(
                ACC_PUBLIC, ACC_FINAL, ACC_SUPER, ACC_INTERFACE, ACC_ABSTRACT, ACC_SYNTHETIC, ACC_ANNOTATION
        );

        // innerClass  https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.6-300-D.1-D.1
        private static final List<Flag> INNER_CLASS_ACCESS_FLAG = Lists.newArrayList(
                ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_INTERFACE, ACC_ABSTRACT, ACC_SYNTHETIC, ACC_ANNOTATION, ACC_ENUM
        );

        // 字段的 access flag, https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.5-200-A.1
        private static final List<Flag> FIELD_ACCESS_FLAG = Lists.newArrayList(
                ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_VOLATILE, ACC_TRANSIENT, ACC_SYNTHETIC, ACC_ENUM
        );

        // 方法的 access flag, https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1
        private static final List<Flag> METHOD_ACCESS_FLAG = Lists.newArrayList(
                ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_SYNCHRONIZED, ACC_BRIDGE, ACC_VARARGS, ACC_NATIVE, ACC_ABSTRACT, ACC_STRICT, ACC_SYNTHETIC
        );

        public static List<Flag> getByType(FlagType flagType) {
            switch (flagType) {
                case CLASS:
                    return CLASS_ACCESS_FLAG;
                case INNER_CLASS:
                    return INNER_CLASS_ACCESS_FLAG;
                case FIELD:
                    return FIELD_ACCESS_FLAG;
                case METHOD:
                    return METHOD_ACCESS_FLAG;
                default:
                    throw new IllegalArgumentException("你是怎么走到这的？");
            }
        }

    }

}
