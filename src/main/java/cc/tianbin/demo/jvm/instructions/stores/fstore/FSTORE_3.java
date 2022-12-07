package cc.tianbin.demo.jvm.instructions.stores.fstore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.stores.Stores;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class FSTORE_3 extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x46;
    }

    @Override
    public void execute(Frame frame) {
        Stores.fstore(frame, 3);
    }
}
