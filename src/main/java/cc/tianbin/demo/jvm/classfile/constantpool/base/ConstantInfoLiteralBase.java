package cc.tianbin.demo.jvm.classfile.constantpool.base;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.common.ConstantTag;

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
        this.tag = reader.readU1ToInt();
    }
}
