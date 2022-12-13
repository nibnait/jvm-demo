package cc.tianbin.demo.jvm.instructions.loads.xaload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class SALOAD extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x35;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int index = stack.popInt();
        JObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        short[] shorts = arrRef.shorts();
        checkIndex(shorts.length, index);
        stack.pushInt(shorts[index]);
    }
}
