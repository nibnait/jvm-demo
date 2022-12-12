package cc.tianbin.demo.jvm.instructions.control.ret;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class RETURN extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0xb1;
    }

    /**
     * return 用于没有返回值的情况
     * 只需要吧当前帧 从虚拟机栈中弹出即可
     */
    @Override
    public void execute(Frame frame) {
        frame.thread.popFrame();
    }

}
