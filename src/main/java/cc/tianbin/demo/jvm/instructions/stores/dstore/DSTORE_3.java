package cc.tianbin.demo.jvm.instructions.stores.dstore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.stores.Stores;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class DSTORE_3 extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x4a;
    }

    @Override
    public void execute(Frame frame) {
        Stores.dstore(frame, 3);
    }
}
