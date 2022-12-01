package cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantNameAndTypeInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 name_index;
     * u2 descriptor_index;
     */
    // 字段名 或 方法名，的索引
    private int nameIndex;
    // 字段或方法的 描述符，的索引
    private int descriptorIndex;

    public ConstantNameAndTypeInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * 字段名 或 方法名
     */
    public String name() {
        return constantPool.getUTF8(nameIndex);
    }

    /*
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
    public String descriptor() {
        return constantPool.getUTF8(descriptorIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.nameIndex = reader.nextU2ToInt();
        this.descriptorIndex = reader.nextU2ToInt();
    }

    @Override
    public String toString() {
        return "NameAndType: "
                + name() + "&"
                + descriptor();
    }
}
