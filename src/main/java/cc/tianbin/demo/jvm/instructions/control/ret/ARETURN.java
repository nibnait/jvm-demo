package cc.tianbin.demo.jvm.instructions.control.ret;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class ARETURN extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0xb0;
    }

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.thread;
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        JObject retVal = currentFrame.operandStack.popRef();
        invokerFrame.operandStack.pushRef(retVal);
    }
}