package cc.tianbin.demo.jvm.instructions.extended;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class MULTIANEWARRAY implements Instruction {
    @Override
    public int opcode() {
        return 0xc5;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        // todo 8.3.6
    }

    @Override
    public void execute(Frame frame) {
        // todo 8.3.6
    }
}
