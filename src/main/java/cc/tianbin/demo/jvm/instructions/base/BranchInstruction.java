package cc.tianbin.demo.jvm.instructions.base;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/05
 */
public abstract class BranchInstruction implements Instruction {

    protected int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.offset = reader.readU2ToInt();
    }

    @Override
    public String operateNum() {
        return offset + "";
    }

    @Override
    public abstract void execute(Frame frame);

    public void branch(Frame frame, int offset) {
        int pc = frame.thread.getPc();
        frame.nextPC = pc + offset;
    }
}
