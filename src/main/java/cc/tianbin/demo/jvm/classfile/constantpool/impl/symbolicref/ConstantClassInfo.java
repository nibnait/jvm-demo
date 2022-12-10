package cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.base.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import lombok.Getter;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantClassInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 index;
     * -
     * 这个类的名字，是常量池中的第 index 个常量
     */
    @Getter
    private int nameIndex;

    public ConstantClassInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    public String getName() {
        return constantPool.getUTF8(this.nameIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.nameIndex = reader.readU2ToInt();
    }

    @Override
    public String printValue() {
        return String.format("#%-22d// %s", nameIndex, constantPool.getUTF8(nameIndex));
    }
}
