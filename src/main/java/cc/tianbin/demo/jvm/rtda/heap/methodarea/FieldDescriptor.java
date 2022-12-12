package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by nibnait on 2022/12/04
 */
@Getter
@AllArgsConstructor
public enum FieldDescriptor {

    /**
     * 字段或方法的 描述符
     * 1. 基本类型的描述符是单个字母:
     *    bool, byte, short, char, int, long, float, double
     *    Z,    B,    S,     C,    I,   J,    F,     D
     * 2. 引用类型的描述符是: L + 类的完全限定名 + 分号
     *    eg: java.lang.Object -> Ljava.lang.Object;
     * 3. 数组类型的描述符是: [ + 数组元素类型描述符
     *    eg: int[] -> 【I
     * 4. 方法描述符是: (参数的类型描述符) + 返回值的类型描述符
     *                void 用 V 表示
     *    void run() -> ()V
     *    String toString() -> ()Ljava.lang.String;
     *    void main(String[] args) -> ([Ljava.Lang.String)V
     *    int max(float x, float y) -> (FF)I
     *    int binarySearch(long[] a, long key) -> ([JJ)I
     * --- --- ---
     * 这里可以看出，Java语言支持方法的重载（不同的方法 可以有相同的名字，只要参数列表不同即可）
     */
    Z("Z", "boolean", false),
    B("B", "byte", (byte) 0),
    S("S", "short", (short) 0),
    C("C", "char", '\u0000'),
    I("I", "int", '0'),
    J("J", "long", 0L),
    F("F", "float", 0.0f),
    D("D", "double", 0.0d),
    STR("Ljava/lang/String;", "String", ""),
    LREF("L", "引用类型", ""),
    AREF("[", "数组类型", ""),
    UNKNOWN("", "未知", ""),
    ;

    private final String code;
    private final String type;
    private final Object defaultValue;

    /**
     * long 和 double 占两个槽位
     */
    public static boolean isLongOrDouble(String descriptor) {
        return FieldDescriptor.J.getCode().equals(descriptor) || FieldDescriptor.D.getCode().equals(descriptor);
    }

    /**
     * descriptor
     */
    public static FieldDescriptor getByCode(String code) {
        for (FieldDescriptor value : values()) {
            if (value.getCode().equals(code) || code.startsWith(value.getCode())) {
                return value;
            }
        }
        return UNKNOWN;
    }

    /**
     * descriptor 的第1个字符
     */
    public static FieldDescriptor getByStartCode(char code) {
        for (FieldDescriptor value : values()) {
            if (value.getCode().equals(String.valueOf(code))) {
                return value;
            }
        }
        return UNKNOWN;
    }

}
