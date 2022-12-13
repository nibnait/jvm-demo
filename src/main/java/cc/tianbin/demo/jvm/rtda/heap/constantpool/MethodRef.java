package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantMethodRefInfo;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.MethodLookup;

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
        JClass d = this.runtimeConstantPool.getClazz();
        JClass c = this.resolvedClass();
        if (c.getAccessFlag().isInterface()) {
            throw new IncompatibleClassChangeError();
        }

        Method method = lookupMethod(c, this.getName(), this.getDescriptor());
        if (method == null) {
            throw new NoSuchMethodError();
        }

        if (!method.isAccessibleTo(d)) {
            throw new IllegalAccessError();
        }

        this.method = method;
    }

    private Method lookupMethod(JClass clazz, String name, String descriptor) {
        Method method = MethodLookup.lookupMethodInClass(clazz, name, descriptor);
        if (method == null) {
            method = MethodLookup.lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return method;
    }

}
