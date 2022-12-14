package cc.tianbin.demo.jvm.instructions.loads.xaload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class LALOAD extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x2f;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int index = stack.popInt();
        JObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        long[] longs = arrRef.longs();
        checkIndex(longs.length, index);
        stack.pushDouble(longs[index]);
    }
}
