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
        ACC_PUBLIC(0x0001, "?????? public; ?????????????????????????????????"),                                                  // class field method
        ACC_PRIVATE(0x0002, "?????? private; ????????????????????????"),                                                     //       field method
        ACC_PROTECTED(0x0004, "?????? protected; ????????????????????????"),                                                 //       field method
        ACC_STATIC(0x0008, "?????? static."),                                                                     //       field method
        ACC_FINAL(0x0010, "?????? final; ???????????????????????????????????????"),                                                 // class field method
        ACC_SUPER(0x0020, "?????? invokespecial ??????????????????????????????????????????"),                                        // class
        ACC_SYNCHRONIZED(0x0020, "?????? synchronized; ????????? monitor ????????????"),                                    //             method
        ACC_VOLATILE(0x0040, "?????? volatile; ????????????"),                                                          //       field
        ACC_BRIDGE(0x0040, "????????????????????????Java?????????????????????????????????"),                                             //             method
        ACC_TRANSIENT(0x0080, "?????? transient; ??????????????????????????????????????????"),                                       //       field
        ACC_VARARGS(0x0080, "??????????????????????????????"),                                                               //             method
        ACC_NATIVE(0x0100, "?????? native; ???Java?????????????????????"),                                                   //             method
        ACC_INTERFACE(0x0200, "????????????????????????"),                                                                // class
        ACC_ABSTRACT(0x0400, "?????? abstract; ?????????????????????????????????????????????"),                                       // class       method
        ACC_STRICT(0x0800, "?????? strictfp; ???????????????FP????????????FP-strict???"),                                      //             method
        ACC_SYNTHETIC(0x1000, "?????? synthetic; ?????????????????????"),                                                  // class field method
        ACC_ANNOTATION(0x2000, "??????."),                                                                        // class
        ACC_ENUM(0x4000, "??????"),                                                                               // class field
        ;

        private final int code;
        private final String description;

        // ?????? access flag, https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1
        private static final List<Flag> CLASS_ACCESS_FLAG = Lists.newArrayList(
                ACC_PUBLIC, ACC_FINAL, ACC_SUPER, ACC_INTERFACE, ACC_ABSTRACT, ACC_SYNTHETIC, ACC_ANNOTATION
        );

        // innerClass  https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.6-300-D.1-D.1
        private static final List<Flag> INNER_CLASS_ACCESS_FLAG = Lists.newArrayList(
                ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_INTERFACE, ACC_ABSTRACT, ACC_SYNTHETIC, ACC_ANNOTATION, ACC_ENUM
        );

        // ????????? access flag, https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.5-200-A.1
        private static final List<Flag> FIELD_ACCESS_FLAG = Lists.newArrayList(
                ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_VOLATILE, ACC_TRANSIENT, ACC_SYNTHETIC, ACC_ENUM
        );

        // ????????? access flag, https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6-200-A.1
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
                    throw new IllegalArgumentException("???????????????????????????");
            }
        }

    }

}
