package cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantStringInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 string_index;
     * -
     * 这个字符串，是常量池的第 index 个常量
     */
    private int index;

    public ConstantStringInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public String value() {
        return constantPool.getUTF8(this.index);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.index = reader.nextU2ToInt();
    }

    @Override
    public String toString() {
        return "String: " + value();
    }
}
