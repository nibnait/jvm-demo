package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.instructions.base.ClassInitLogic;
import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.instructions.base.MethodInvokeLogic;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.MethodRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/12
 */
public class INVOKE_STATIC extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb8;
    }

    @Override
    public String operate() {
        return "invokestatic";
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        MethodRef methodRef = (MethodRef) runtimeConstantPool.getConstants(this.index);
        Method resolvedMethod = methodRef.resolvedMethod();

        if (!resolvedMethod.getAccessFlag().isStatic()) {
            // 必须是静态方法
            throw new IncompatibleClassChangeError("invokestatic 只能调用静态方法");
        }

        // resolvedMethod 不能是类初始化方法。
        // 类初始化方法只能由Java虚拟机调用，不能使用invokestatic指令调用。
        // 这一规则由class文件验证器保证，这里不做检查。

        // 如果声明 resolvedMethod 的类还没有被初始化，则要先初始化该类。
        Class clazz = resolvedMethod.getClazz();
        if (!clazz.isInitStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread, clazz);
            return;
        }

        // 执行 resolvedMethod
        MethodInvokeLogic.invokeMethod(frame, resolvedMethod);
    }
}
