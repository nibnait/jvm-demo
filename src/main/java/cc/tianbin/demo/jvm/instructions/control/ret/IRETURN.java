package cc.tianbin.demo.jvm.instructions.control.ret;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;

/**
 * Created by nibnait on 2022/12/07
 */
public class IRETURN extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0xac;
    }

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.thread;
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        int retVal = currentFrame.operandStack.popInt();
        invokerFrame.operandStack.pushInt(retVal);
    }
}
