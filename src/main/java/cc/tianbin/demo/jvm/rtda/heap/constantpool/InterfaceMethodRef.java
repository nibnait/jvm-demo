package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantInterfaceMethodRefInfo;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.MethodLookup;

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
        JClass d = this.runtimeConstantPool.getClazz();
        JClass c = this.resolvedClass();
        if (!c.getAccessFlag().isInterface()) {
            throw new IncompatibleClassChangeError();
        }

        Method method = lookupInterfaceMethod(c, this.getName(), this.getDescriptor());
        if (method == null) {
            throw new NoSuchMethodError();
        }

        if (!method.isAccessibleTo(d)) {
            throw new IllegalAccessError();
        }

        this.method = method;
    }


    private Method lookupInterfaceMethod(JClass clazz, String name, String descriptor) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return MethodLookup.lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
    }
}
