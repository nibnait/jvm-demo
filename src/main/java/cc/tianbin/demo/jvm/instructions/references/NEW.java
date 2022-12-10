package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.ClassRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.MethodAreaObject;

/**
 * Created by nibnait on 2022/12/09
 */
public class NEW extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xbb;
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool cp = frame.method.getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstants(this.index);

        Class clazz = classRef.resolvedClass();
        if (clazz.getAccessFlag().isInterface() || clazz.getAccessFlag().isAbstract()) {
            throw new InstantiationError("接口和抽象类 不能实例化");
        }
        MethodAreaObject ref = clazz.newObject();
        frame.operandStack.pushRef(ref);
    }
}
