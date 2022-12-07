package cc.tianbin.demo.jvm.instructions.loads.aload;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.loads.Loads;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class ALOAD_2 extends NoOperandsInstruction {

    @Override
    public int opcode() {
        return 0x2c;
    }

    @Override
    public void execute(Frame frame) {
        Loads.aload(frame, 2);
    }

}
