package cc.tianbin.demo.jvm.instructions;

import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/05
 */
public interface Instruction {

    // 操作码
    int opcode();
    // 指令名
    default String operate() {
        return this.getClass().getSimpleName().toLowerCase();
    }
    // 操作数
    default String operateNum() {
        return "";
    }

    /**
     * 从字节码中提取操作数
     */
    void fetchOperands(BytecodeReader reader);

    /**
     * 执行逻辑指令
     */
    void execute(Frame frame);

}
