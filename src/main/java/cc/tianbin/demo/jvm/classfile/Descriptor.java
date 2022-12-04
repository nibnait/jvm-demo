package cc.tianbin.demo.jvm.classfile;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by nibnait on 2022/12/04
 */
@Getter
@AllArgsConstructor
public enum Descriptor {

    /**
     * 字段或方法的 描述符
     * 1. 基本类型的描述符是单个字母:
     *    byte, short, char, int, long, float, double
     *    B,    S,     C,    I,   J,    F,     D
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
    B("byte"),
    S("short"),
    C("char"),
    I("int"),
    J("long"),
    F("float"),
    D("double"),
    ;

    private final String type;

}
