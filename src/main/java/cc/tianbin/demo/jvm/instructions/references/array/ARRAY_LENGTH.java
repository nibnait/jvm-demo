package cc.tianbin.demo.jvm.instructions.references.array;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/13
 */
public class ARRAY_LENGTH extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0xbe;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        JObject arrRef = stack.popRef();
        if (arrRef == null) {
            throw new NullPointerException();
        }

        int arrLen = arrRef.arrayLength();
        stack.pushInt(arrLen);
    }
}
