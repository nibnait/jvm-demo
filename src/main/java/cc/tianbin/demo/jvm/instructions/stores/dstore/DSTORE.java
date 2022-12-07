package cc.tianbin.demo.jvm.instructions.stores.dstore;

import cc.tianbin.demo.jvm.instructions.base.Index8Instruction;
import cc.tianbin.demo.jvm.instructions.stores.Stores;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class DSTORE extends Index8Instruction {
    @Override
    public int opcode() {
        return 0x39;
    }

    @Override
    public void execute(Frame frame) {
        Stores.dstore(frame, this.index);
    }
}
