package cc.tianbin.demo.jvm.instructions.references.invoke;

import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.instructions.base.MethodInvokeLogic;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.MethodRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.*;

/**
 * Created by nibnait on 2022/12/12
 */
public class INVOKE_VIRTUAL extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb6;
    }

    @Override
    public void execute(Frame frame) {
        Class currentClass = frame.method.getClazz();
        RuntimeConstantPool runTimeConstantPool = currentClass.getRuntimeConstantPool();
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(this.index);
        Method resolvedMethod = methodRef.resolvedMethod();
        if (resolvedMethod.getAccessFlag().isStatic()) {
            throw new IncompatibleClassChangeError("invokevirtual 只能调用动态方法");
        }

        JVMMAObject ref = frame.operandStack.getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            if ("println".equals(methodRef.getName())) {
                println(frame.operandStack, methodRef.getDescriptor());
                return;
            }
            throw new NullPointerException();
        }

        if (resolvedMethod.getAccessFlag().isProtected() &&
                resolvedMethod.getClazz().isSubClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.getClazz() != currentClass &&
                !ref.getClazz().isSubClassOf(currentClass)) {
            throw new IllegalAccessError("protected方法只能被声明该方法的类或子类调用");
        }

        Method methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        if (methodToBeInvoked == null) {
            throw new NoSuchMethodError(String.format("%s %s", methodRef.getName(), methodRef.getDescriptor()));
        }
        if (methodToBeInvoked.getAccessFlag().isAbstract()) {
            // todo 父类的抽象方法，需要特殊处理
            throw new AbstractMethodError("父类的抽象方法 待后续完善特殊处理");
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }

    // hack
    private void println(OperandStack stack, String descriptor) {
        switch (descriptor) {
            case "(Z)V":
                System.out.println(stack.popInt() != 0);
                break;
            case "(C)V":
            case "(I)V":
            case "(B)V":
            case "(S)V":
                System.out.println(stack.popInt());
                break;
            case "(F)V":
                System.out.println(stack.popFloat());
                break;
            case "(J)V":
                System.out.println(stack.popLong());
                break;
            case "(D)V":
                System.out.println(stack.popDouble());
                break;
            case "(Ljava/lang/String;)V":
                JVMMAObject jStr = stack.popRef();
                String goStr = StringPool.goString(jStr);
                System.out.println(goStr);
                break;
            default:
                System.out.println(descriptor);
                break;
        }
        stack.popRef();
    }
}
