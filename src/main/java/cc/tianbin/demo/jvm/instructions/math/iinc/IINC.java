package cc.tianbin.demo.jvm.instructions.math.iinc;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.LocalVariables;
import lombok.Setter;

/**
 * Created by nibnait on 2022/12/07
 */
public class IINC implements Instruction {

    @Setter
    private int index;
    private int constVal;

    @Override
    public int opcode() {
        return 0x84;
    }

    @Override
    public String operateNum() {
        return index + ", " + constVal;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readU1ToInt();
        this.constVal = reader.readU1ToInt();
    }

    @Override
    public void execute(Frame frame) {
        LocalVariables localVars = frame.localVariables;
        int val = localVars.getInt(index);
        localVars.setInt(index, val + constVal);
    }
}
