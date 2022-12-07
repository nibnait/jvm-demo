package cc.tianbin.demo.jvm.instructions.math.sh;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class LSHL extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x79;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        long v1 = stack.popLong();

        // long 8个字节，64位，算术左位移（有符号），最多往左移63位
        //            111111 = 63 = 3f
        int s = v2 & 0x3f;
        long val = v1 << s;
        stack.pushLong(val);
    }
}
