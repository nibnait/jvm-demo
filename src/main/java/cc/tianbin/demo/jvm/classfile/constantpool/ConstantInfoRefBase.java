package cc.tianbin.demo.jvm.classfile.constantpool;

import cc.tianbin.demo.jvm.classfile.ClassReader;

/**
 * 字面量的 常量 基类
 * Created by nibnait on 2022/12/01
 */
public class ConstantInfoRefBase implements ConstantInfo {

    private int tag;

    protected final ConstantPool constantPool;

    public ConstantInfoRefBase(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public ConstantTag tag() {
        return ConstantTag.getByCode(this.tag);
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.tag = reader.nextU1toInt();
    }
}
