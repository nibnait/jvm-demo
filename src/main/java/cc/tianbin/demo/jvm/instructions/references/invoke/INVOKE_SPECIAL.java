package cc.tianbin.demo.jvm.instructions.references.invoke;

import cc.tianbin.demo.jvm.common.CommonConstants;
import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.instructions.base.MethodInvokeLogic;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.MethodRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.MethodLookup;

/**
 * Created by nibnait on 2022/12/12
 */
public class INVOKE_SPECIAL extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb7;
    }

    @Override
    public String operate() {
        return "invokespecial";
    }

    @Override
    public void execute(Frame frame) {
        Class currentClass = frame.method.getClazz();
        RuntimeConstantPool runtimeConstantPool = currentClass.getRuntimeConstantPool();
        MethodRef methodRef = (MethodRef) runtimeConstantPool.getConstants(this.index);
        Class resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();

        if (CommonConstants.INIT.equals(resolvedMethod.getName())
                && resolvedMethod.getClazz() != resolvedClass) {
            // 必须是当前类的构造函数
            throw new NoSuchMethodError();
        }

        if (resolvedMethod.getAccessFlag().isStatic()) {
            throw new IncompatibleClassChangeError("invokespecial 无法调用静态方法");
        }

        JVMMAObject ref = frame.operandStack.getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new NullPointerException();
        }

        if (resolvedMethod.getAccessFlag().isProtected()
                && resolvedMethod.getClazz().isSubClassOf(currentClass)
                && !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName())
                && ref.getClazz() != currentClass
                && !ref.getClazz().isSubClassOf(currentClass)) {
            throw new IllegalAccessError("protected方法只能被声明该方法的类或子类调用");
        }

        Method methodToBeInvoked = resolvedMethod;
        if (currentClass.getAccessFlag().isSuper()
                && resolvedClass.isSubClassOf(currentClass)
                && !CommonConstants.INIT.equals(resolvedMethod.getName())) {
            // 调用父类的方法 && 非父类的构造函数
            methodToBeInvoked = MethodLookup.lookupMethodInClass(currentClass.getSuperClass(), methodRef.getName(), methodRef.getDescriptor());
        }

        if (methodToBeInvoked == null) {
            throw new NoSuchMethodError(String.format("%s %s", methodRef.getName(), methodRef.getDescriptor()));
        }
        if (methodToBeInvoked.getAccessFlag().isAbstract()) {
            // todo 父类的抽象方法，需要特殊处理
            throw new AbstractMethodError("父类的抽象方法 待后续完善特殊处理");
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
