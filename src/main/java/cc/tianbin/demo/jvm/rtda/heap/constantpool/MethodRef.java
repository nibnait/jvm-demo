package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantMethodRefInfo;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/08
 */
public class MethodRef extends MemberRef {

    private Method method;

    public static MethodRef newMethodRef(RuntimeConstantPool runtimeConstantPool, ConstantMethodRefInfo refInfo) {
        MethodRef ref = new MethodRef();
        ref.runtimeConstantPool = runtimeConstantPool;
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public Method resolvedMethod() {
        if (this.method == null) {
            this.resolvedMethodRef();
        }
        return this.method;
    }

    private void resolvedMethodRef() {

    }

}
