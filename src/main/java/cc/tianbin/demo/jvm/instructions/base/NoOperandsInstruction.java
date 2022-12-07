package cc.tianbin.demo.jvm.instructions.base;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;

/**
 * Created by nibnait on 2022/12/06
 */
public abstract class NoOperandsInstruction implements Instruction {

    @Override
    public void fetchOperands(BytecodeReader reader) {
        // 不需要取操作数
    }

    @Override
    public abstract void execute(Frame frame);

    protected void fcmp(Frame frame, boolean flag) {
        OperandStack stack = frame.operandStack;
        float v2 = stack.popFloat();
        float v1 = stack.popFloat();

        if (Float.isNaN(v1) || Float.isNaN(v2)) {
            stack.pushInt(flag ? 1 : -1);
        }

        if (v1 > v2) {
            stack.pushInt(1);
        } else if (v1 < v2) {
            stack.pushInt(-1);
        } else {
            stack.pushInt(0);
        }
    }

    protected void dcmp(Frame frame, boolean flag) {
        OperandStack stack = frame.operandStack;
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();

        if (Double.isNaN(v1) || Double.isNaN(v2)) {
            stack.pushInt(flag ? 1 : -1);
        }

        if (v1 > v2) {
            stack.pushInt(1);
        } else if (v1 < v2) {
            stack.pushInt(-1);
        } else {
            stack.pushInt(0);
        }
    }

}
