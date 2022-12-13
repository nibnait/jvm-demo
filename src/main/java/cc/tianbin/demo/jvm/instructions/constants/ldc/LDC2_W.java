package cc.tianbin.demo.jvm.instructions.constants.ldc;

import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;

/**
 * Created by nibnait on 2022/12/06
 */
public class LDC2_W extends Index16Instruction {

    @Override
    public int opcode() {
        return 0x14;
    }

    /**
     *
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        RuntimeConstantPool runTimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        Object c = runTimeConstantPool.getConstants(this.index);

        if (c instanceof Long) {
            stack.pushLong((Long) c);
            return;
        }
        if (c instanceof Double) {
            stack.pushDouble((Double) c);
            return;
        }

        throw new ClassFormatError(c.toString());
    }
}
