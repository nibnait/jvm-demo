package cc.tianbin.demo.jvm.instructions.constants.ipush;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/06
 */
public class SIPUSH implements Instruction {

    private int val;

    @Override
    public int opcode() {
        return 0x11;
    }

    @Override
    public String operateNum() {
        return val + "";
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.val = reader.readU2ToInt();
    }

    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushInt(val);
    }
}








