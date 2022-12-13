package cc.tianbin.demo.jvm.instructions.loads.xaload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class FALOAD extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x30;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int index = stack.popInt();
        JObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        float[] floats = arrRef.floats();
        checkIndex(floats.length, index);
        stack.pushFloat(floats[index]);
    }
}
