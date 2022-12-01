package cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref;

import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantFieldRefInfo extends ConstantMemberRefInfo {

    /**
     * 字段符号引用
     */
    public ConstantFieldRefInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public String toString() {
        return "FieldRef: "
                + this.className() + "."
                + this.nameAndType();
    }
}
