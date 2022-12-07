package cc.tianbin.demo.jvm.instructions.extended;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.InstructionFactory;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.instructions.base.Index8Instruction;
import cc.tianbin.demo.jvm.instructions.math.iinc.IINC;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/07
 */
public class WIDE implements Instruction {

    private Instruction modifiedInstruction;

    @Override
    public int opcode() {
        return 0xc4;
    }

    /**
     * 加载类(Xload), 存储类(Xstore), ret, innc，需要按[索引]访问局部变量表
     * 正常局部变量表 256个索引（1个字节）就够了
     * 当256不够时，可以通过 wide 扩成2个字节
     */
    @Override
    public void fetchOperands(BytecodeReader reader) {
        int opcode = reader.readU1ToInt();

        Instruction instruction = InstructionFactory.getByOpcode(opcode);
        if (instruction instanceof Index8Instruction) {
            Index8Instruction inst = (Index8Instruction) instruction;

            int index = reader.readU2ToInt();
            inst.setIndex(index);
            modifiedInstruction = inst;
        } else if (instruction instanceof IINC) {
            IINC inst = (IINC) instruction;
            int index = reader.readU2ToInt();
            inst.setIndex(index);
            modifiedInstruction = inst;
        }
    }

    @Override
    public void execute(Frame frame) {
        modifiedInstruction.execute(frame);
    }
}
