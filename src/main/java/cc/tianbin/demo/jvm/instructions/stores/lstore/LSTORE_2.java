package cc.tianbin.demo.jvm.instructions.stores.lstore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.stores.Stores;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class LSTORE_2 extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x41;
    }

    @Override
    public void execute(Frame frame) {
        Stores.lstore(frame, 2);
    }
}
