package cc.tianbin.demo.jvm.instructions.stores.astore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.stores.Stores;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class ASTORE_2 extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x4d;
    }

    @Override
    public void execute(Frame frame) {
        Stores.astore(frame, 2);
    }
}
