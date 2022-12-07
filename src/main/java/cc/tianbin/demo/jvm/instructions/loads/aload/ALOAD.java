package cc.tianbin.demo.jvm.instructions.loads.aload;

import cc.tianbin.demo.jvm.instructions.base.Index8Instruction;
import cc.tianbin.demo.jvm.instructions.loads.Loads;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class ALOAD extends Index8Instruction {

    @Override
    public int opcode() {
        return 0x19;
    }

    @Override
    public void execute(Frame frame) {
        Loads.aload(frame, this.index);
    }

}
