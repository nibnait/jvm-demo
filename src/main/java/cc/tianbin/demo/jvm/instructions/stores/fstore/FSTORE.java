package cc.tianbin.demo.jvm.instructions.stores.fstore;

import cc.tianbin.demo.jvm.instructions.base.Index8Instruction;
import cc.tianbin.demo.jvm.instructions.stores.Stores;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class FSTORE extends Index8Instruction {
    @Override
    public int opcode() {
        return 0x38;
    }

    @Override
    public void execute(Frame frame) {
        Stores.fstore(frame, this.index);
    }
}
