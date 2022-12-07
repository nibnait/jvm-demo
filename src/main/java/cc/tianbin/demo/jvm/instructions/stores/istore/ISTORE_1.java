package cc.tianbin.demo.jvm.instructions.stores.istore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.instructions.stores.Stores;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class ISTORE_1 extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x3c;
    }

    @Override
    public void execute(Frame frame) {
        Stores.istore(frame, 1);
    }
}
