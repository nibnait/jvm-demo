package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.InstructionFactory;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;

import static cc.tianbin.demo.jvm.utils.LogUtil.log;
import static cc.tianbin.demo.jvm.utils.LogUtil.printf;

/**
 * 指令集解释器
 * Created by nibnait on 2022/12/07
 */
public class Interpreter {

    private Interpreter() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static void execute(MemberInfo memberInfo) {
        CodeAttribute codeAttr = memberInfo.getCodeAttribute();

        int maxLocals = codeAttr.maxLocals();
        int maxStack = codeAttr.maxStack();
        log("maxLocals: {}, maxStack: {} \n", maxLocals, maxStack);
        byte[] byteCode = codeAttr.data();

        Thread thread = new Thread();
        Frame frame = thread.newFrame(maxLocals, maxStack);
        thread.pushFrame(frame);
        loop(thread, byteCode);
    }

    private static void loop(Thread thread, byte[] byteCode) {
        Frame frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader();

        int opcode = -1;
        int step = 0;

        while (!needExit(opcode)) {
            // 计算pc
            int pc = frame.nextPC;
            thread.setPc(pc);
            reader.reset(byteCode, pc);
            log("pc = {}", pc);

            // 解码指令
            opcode = reader.readU1ToInt();
            Instruction inst = InstructionFactory.getByOpcode(opcode);
            inst.fetchOperands(reader);

            // 打点日志
            logFrame(frame);
            printf("%d: %s %s", step, inst.operate(), inst.operateNum());

            // 执行指令
            inst.execute(frame);
            frame.nextPC = reader.pc();

            log("====================================");
            if (needExit(opcode)) {
                logFrame(frame);
            }

            step++;
        }

    }

    private static boolean needExit(int opcode) {
        return opcode == 0xb1;
    }

    private static void logFrame(Frame frame) {
        log("局部变量表: {}", frame.localVariables.formatSlots());
        log("操作数栈: {}", frame.operandStack.formatSlots());
    }
}
