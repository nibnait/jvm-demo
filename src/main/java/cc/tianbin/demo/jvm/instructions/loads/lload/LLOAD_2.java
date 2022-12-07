package cc.tianbin.demo.jvm.instructions.loads.lload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.loads.Loads;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class LLOAD_2 extends NoOperandsInstruction {

    @Override
    public int opcode() {
        return 0x20;
    }

    @Override
    public void execute(Frame frame) {
        Loads.lload(frame, 2);
    }

}
