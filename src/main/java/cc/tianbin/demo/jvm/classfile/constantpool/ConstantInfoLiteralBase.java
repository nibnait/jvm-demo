package cc.tianbin.demo.jvm.classfile.constantpool;

import cc.tianbin.demo.jvm.classfile.ClassReader;

/**
 * 引用类型的 常量 基类
 * Created by nibnait on 2022/12/01
 */
public class ConstantInfoLiteralBase implements ConstantInfo {

    private int tag;

    @Override
    public ConstantTag tag() {
        return ConstantTag.getByCode(this.tag);
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.tag = reader.readU1toInt();
    }
}
