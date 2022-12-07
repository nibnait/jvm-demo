package cc.tianbin.demo.jvm.instructions.math.sh;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class LUSHR extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x7d;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        int v1 = stack.popInt();

        // long 8个字节，64位，逻辑右位移（无符号），最多往右移64位
        //            111111 = 63 = 3f
        int s = v2 & 0x3f;
        int val = v1 >> s;
        stack.pushInt(val);
    }
}
