package cc.tianbin.demo.jvm.instructions.control;

import cc.tianbin.demo.jvm.instructions.base.BranchInstruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Access jump table by index and jump
 * 按 索引跳转
 * Created by nibnait on 2022/12/07
 */
public class TABLESWITCH extends BranchInstruction {

    /*
    tableswitch
    <0-3 byte pad>
    defaultbyte1
    defaultbyte2
    defaultbyte3
    defaultbyte4
    lowbyte1
    lowbyte2
    lowbyte3
    lowbyte4
    highbyte1
    highbyte2
    highbyte3
    highbyte4
    jump offsets...
     */
    // default情况下，执行跳转所需的字节码偏移量
    private int defaultOffset;
    // case 的取值范围
    //      索引的最小值
    private int low;
    //      索引的最大值
    private int high;
    // 索引表
    private int[] jumpOffsets;

    @Override
    public int opcode() {
        return 0xaa;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readU4ToInt();
        this.low = reader.readU4ToInt();
        this.high = reader.readU4ToInt();
        int jumpOffsetCount = this.high - this.low + 1;
        this.jumpOffsets = reader.readInts(jumpOffsetCount);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.operandStack.popInt();
        if (index >= this.low && index <= this.high) {
            branch(frame, index);
        } else {
            branch(frame, defaultOffset);
        }
    }
}
