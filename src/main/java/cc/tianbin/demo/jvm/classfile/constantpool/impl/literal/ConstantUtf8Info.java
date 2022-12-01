package cc.tianbin.demo.jvm.classfile.constantpool.impl.literal;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfoLiteralBase;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantUtf8Info extends ConstantInfoLiteralBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 length;
     * u1 bytes[length];
     * ¬
     * 具体长度靠下一个 2字节的标志位
     * 采用 MUTF-8 的编码方式
     * http://stackoverflow.com/questions/15440584/why-does-java-usemodified-utf-8-instead-of-utf-8
     * http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
     */
    private String value;

    @Override
    public String value() {
        return value;
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        // 前2个字节，标记这个字符串的长度
        int length = reader.nextU2ToInt();
        // 读出所有 字节
        byte[] bytes = reader.nextBytes(length);
        // 采用 MUTF-8 编码，转成 String
        this.value = new String(bytes);
    }

    @Override
    public String toString() {
        return "Utf8: " + value;
    }
}
