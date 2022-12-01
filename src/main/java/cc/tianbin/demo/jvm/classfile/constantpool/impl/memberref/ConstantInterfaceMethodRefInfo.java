package cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref;

import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantInterfaceMethodRefInfo extends ConstantMemberRefInfo {

    /**
     * 接口方法符号引用
     */
    public ConstantInterfaceMethodRefInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public String toString() {
        return "InterfaceMethodRef: "
                + this.className() + "."
                + this.nameAndType();
    }
}
