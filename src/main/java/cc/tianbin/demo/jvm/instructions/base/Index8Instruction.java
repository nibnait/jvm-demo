package cc.tianbin.demo.jvm.instructions.base;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import lombok.Setter;

/**
 * Created by nibnait on 2022/12/05
 */
public abstract class Index8Instruction implements Instruction {

    @Setter
    protected int index;

    @Override
    public String operateNum() {
        return index + "";
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readU1ToInt();
    }

    @Override
    public abstract void execute(Frame frame);

}
