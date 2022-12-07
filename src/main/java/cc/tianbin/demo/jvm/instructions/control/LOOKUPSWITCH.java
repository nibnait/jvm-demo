package cc.tianbin.demo.jvm.instructions.control;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Access jump table by key match and jump
 * 按 key匹配跳转
 * Created by nibnait on 2022/12/07
 */
public class LOOKUPSWITCH extends BranchInstruction {

    /*
    lookupswitch
    <0-3 byte pad>
    defaultbyte1
    defaultbyte2
    defaultbyte3
    defaultbyte4
    npairs1
    npairs2
    npairs3
    npairs4
    match-offset pairs...
    */
    private int defaultOffset;
    private int npairs;
    private int[] matchOffsets;

    @Override
    public int opcode() {
        return 0xab;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readU4ToInt();
        this.npairs = reader.readU4ToInt();
        this.matchOffsets = reader.readInts(this.npairs * 2);
    }

    @Override
    public void execute(Frame frame) {
        int key = frame.operandStack.popInt();

        for (int i = 0; i < this.npairs * 2; i++) {
            if (this.matchOffsets[i] == key) {
                int offset = matchOffsets[i + 1];
                branch(frame, offset);
            }
        }

        branch(frame, defaultOffset);
    }
}
