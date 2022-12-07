package cc.tianbin.demo.jvm.instructions.stores.xastore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class LASTORE extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x50;
    }

    @Override
    public void execute(Frame frame) {
        // todo 8.3.5
    }
}
