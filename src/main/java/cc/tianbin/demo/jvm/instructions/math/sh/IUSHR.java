package cc.tianbin.demo.jvm.instructions.math.sh;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/07
 */
public class IUSHR extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x7c;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        int v1 = stack.popInt();

        // int 4个字节，32位，逻辑右位移（无符号），最多往右移32位
        //             11111 = 31 = 1f     没有必要用6个1
        int s = v2 & 0x1f;
        int val = v1 >> s;
        stack.pushInt(val);
    }
}
