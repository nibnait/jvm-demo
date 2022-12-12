package cc.tianbin.demo.jvm.classfile.constantpool.impl.invokdynamic;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.base.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantMethodHandleInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u1 reference_kind;
     * u2 reference_index;
     */
    private int referenceKind;
    private int referenceIndex;

    public ConstantMethodHandleInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    public int referenceKind() {
        return referenceKind;
    }

    public int referenceIndex() {
        return referenceIndex;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.referenceKind = reader.readU1ToInt();
        this.referenceIndex = reader.readU2ToInt();
    }
}
