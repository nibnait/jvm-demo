package cc.tianbin.demo.jvm.instructions.extended;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class GOTO_W extends BranchInstruction {
    @Override
    public int opcode() {
        return 0xc8;
    }

    /**
     * @see cc.tianbin.demo.jvm.instructions.control.GOTO
     * 与 goto 指令唯一的区别是 索引从2字节 -> 4字节
     */
    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readU2ToInt();
    }

    @Override
    public void execute(Frame frame) {
        branch(frame, this.offset);
    }
}
