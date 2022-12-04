package cc.tianbin.demo.jvm.classfile;

import cc.tianbin.demo.jvm.rtda.frame.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nibnait on 2022/12/04
 */
@Getter
@AllArgsConstructor
public enum AccessFlagEnum {

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.6
     */
    ACC_PUBLIC("0001", "申明为 public; 可以从包外访问"),
    ACC_PRIVATE("0002", "申明为private; 仅能在定义的class中使用"),
    ACC_PROTECT("0004", "申明为protected; 可以在子类中访问"),
    ACC_STATIC("0008", "申明为static"),
    ACC_FINAL("0010", "申明为final; 不能被覆盖"),

    ACC_SYNCHRONIZED("0020", "声明 synchronized;调用时使用monitor封装"),

    ACC_VOLATILE("0040", "声明 volatile"),

    ACC_TRANSIENT("0080", "声明 transient"),
    ACC_NATIVE("0100", "声明 native;由java之外的语言实现"),
    ACC_INTERFACE("0200", "Is an interface, not a class."),
    ACC_ABSTRACT("0400", "声明 abstract; 不提供实现"),
    ACC_SYNTHETIC("1000", "Declared synthetic; not present in the source code."),
    ACC_ANNOTATION("2000", "Declared as an annotation type."),
    ACC_ENUM("4000", "Declared as an enum type."),
    ;

    private final String code;
    private final String description;

    private static AccessFlagEnum getByCode(String code) {
        if (StringUtils.isBlank(code) || "0000".equals(code)) {
            return null;
        }
        for (AccessFlagEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("%s 非法的 access flat", code));
    }

    public static List<AccessFlagEnum> getByHexString(String hexString) {
        if (StringUtils.isBlank(hexString)) {
            return null;
        }

        List<AccessFlagEnum> list = new ArrayList<>();
        int len = hexString.length();
        for (int i = 0; i < len; i++) {
            int cur = Integer.parseInt(hexString.substring(len - i - 1, len - i));

            // 0001, 0002, 0004, 0008
            if (i == 0 && cur <= 9) {
                list.addAll(splitDigit(cur));
                continue;
            }

            String curHexStr = Integer.toHexString(cur);
            // 后面补点0
            curHexStr = Util.appendZero(curHexStr, i);
            // 前面补点0
            curHexStr = Util.insertZero(curHexStr, 4);
            // 拿到那个唯一的 accessFlag
            AccessFlagEnum accessFlag = getByCode(curHexStr);
            if (accessFlag != null) {
                list.add(accessFlag);
            }
        }

        return list;
    }

    /**
     * 0001
     * 0002
     * 0004
     * 0008
     */
    private static List<AccessFlagEnum> splitDigit(int num) {
        List<AccessFlagEnum> list = new ArrayList<>();
        String s = Integer.toBinaryString(num);
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int cur = Integer.parseInt(s.substring(len - i - 1, len - i));
            if (cur > 0) {
                // 转成 string
                String strHex = Integer.toHexString(1 << i);
                // 前面补点0
                strHex = Util.insertZero(strHex, 4);
                list.add(getByCode(strHex));
            }
        }
        return list;
    }

}
