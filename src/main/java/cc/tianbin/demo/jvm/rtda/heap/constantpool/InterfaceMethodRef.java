package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantInterfaceMethodRefInfo;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/08
 */
public class InterfaceMethodRef extends MemberRef {

    private Method method;

    public static InterfaceMethodRef newInterfaceMethodRef(RuntimeConstantPool runtimeConstantPool, ConstantInterfaceMethodRefInfo refInfo) {
        InterfaceMethodRef ref = new InterfaceMethodRef();
        ref.runtimeConstantPool = runtimeConstantPool;
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public Method resolvedInterfaceMethod() {
        if (this.method == null) {
            this.resolvedInterfaceMethodRef();
        }
        return this.method;
    }

    private void resolvedInterfaceMethodRef() {

    }

}
