package cc.tianbin.demo.jvm.common;

import cc.tianbin.demo.jvm.exception.IllegalArgumentException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by nibnait on 2022/12/13
 */
@Getter
@AllArgsConstructor
public enum ArrayType {

    AT_BOOLEAN(4, "[Z"),
    AT_CHAR(5, "[C"),
    AT_FLOAT(6, "[F"),
    AT_DOUBLE(7, "[D"),
    AT_BYTE(8, "[B"),
    AT_SHORT(9, "[S"),
    AT_INT(10, "[I"),
    AT_LONG(11, "[J"),
    ;

    private final int code;
    private final String descriptor;

    public static ArrayType getByCode(int code) {
        for (ArrayType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("{} invalid array type", code);
    }

}
