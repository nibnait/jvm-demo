package cc.tianbin.demo.jvm.instructions.references.invoke;

import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.instructions.base.MethodInvokeLogic;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.InterfaceMethodRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.MethodLookup;

/**
 * Created by nibnait on 2022/12/12
 */
public class INVOKE_INTERFACE extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb9;
    }

    /**
     * 在字节码中， invokeinterface指令的操作码后面跟着4字节而非2字节。
     * 前两字节的含义和其他指令相同，是个uint16运行时常量池索引。
     * 第3字节的值是给方法传递参数需要的slot数，其含义和给Method结构体定义的 argSlotCount 字段相同。正如我们所知，这个数是可以根据方法描 述符计算出来的，它的存在仅仅是因为历史原因。
     * 第4字节是留给 Oracle 的某些 Java虚拟机实现用的，它的值必须是0。该字节的存在是为了保证Java虚拟机可以向后兼容。
     */
    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readU2ToInt();

        // argSlotCount
        reader.readU1ToInt();
        // must be 0
        reader.readU1ToInt();
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) runtimeConstantPool.getConstants(this.index);
        Method resolvedMethod = methodRef.resolvedInterfaceMethod();

        if (resolvedMethod.getAccessFlag().isStatic() || resolvedMethod.getAccessFlag().isPrivate()) {
            throw new IncompatibleClassChangeError("接口方法 不允许是 private static 修饰");
        }

        JObject ref = frame.operandStack.getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new NullPointerException();
        }

        if (!ref.getClazz().isImplements(methodRef.resolvedClass())) {
            throw new IncompatibleClassChangeError(String.format("引用所指对象的类(%s) 还没有实现当前接口(%s)",
                    ref.getClazz().getName(), methodRef.resolvedClass().getName()));
        }

        Method methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        if (methodToBeInvoked == null) {
            throw new NoSuchMethodError(String.format("%s %s", methodRef.getName(), methodRef.getDescriptor()));
        }

        if (methodToBeInvoked.getAccessFlag().isAbstract()) {
            throw new AbstractMethodError("父类的抽象方法 待后续完善特殊处理");
        }

        if (!methodToBeInvoked.getAccessFlag().isPublic()) {
            throw new IllegalAccessError();
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
