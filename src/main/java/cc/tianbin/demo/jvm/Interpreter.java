package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.InstructionFactory;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;
import cc.tianbin.demo.jvm.rtda.heap.classloader.JClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.StringPool;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

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

    public static void execute(Method method, boolean logInst, List<String> args) {
        log("maxLocals: {}, maxStack: {} \n", method.getMaxLocals(), method.getMaxStack());

        Thread thread = new Thread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);

        if (CollectionUtils.isNotEmpty(args)) {
            JObject jArgs = createArgsArray(method.getClazz().getLoader(), args);
            frame.localVariables.setRef(0, jArgs);
        }

        loop(thread, logInst);
    }

    /**
     * 把命令行入参 转化成数组对象
     */
    private static JObject createArgsArray(JClassLoader loader, List<String> args) {
        JClass stringClass = loader.loadClass("java/lang/String");
        JObject argsArr = stringClass.arrayClass().newArray(args.size());
        JObject[] jArgs = argsArr.refs();
        for (int i = 0; i < jArgs.length; i++) {
            jArgs[i] = StringPool.jString(loader, args.get(i));
        }
        return argsArr;
    }

    private static void loop(Thread thread, boolean logInst) {
        BytecodeReader reader = new BytecodeReader();
        int step = 0;
        while (!thread.isStackEmpty()) {
            // 计算pc
            Frame frame = thread.currentFrame();
            int pc = frame.nextPC;
            thread.setPc(pc);
            reader.reset(frame.method.getBytecode(), pc);
            log("pc = {}", pc);

            // 解码指令
            int opcode = reader.readU1ToInt();
            Instruction inst = InstructionFactory.getByOpcode(opcode);
            inst.fetchOperands(reader);

            // 打点日志
            logInstruction(step, frame, inst);
            logFrame(frame);

            // 执行指令
            inst.execute(frame);
            frame.nextPC = reader.pc();

            log("====================================");
            if (thread.isStackEmpty()) {
                logFrame(frame);
            }

            step++;
        }

    }

    private static void logInstruction(int step, Frame frame, Instruction inst) {
        Method method = frame.method;
        String className = method.getClazz().getName();
        String methodName = method.getName();

        printf("%s.%s()", className, methodName);
        printf("%d: %s %s", step, inst.operate(), inst.operateNum());
    }

    private static void logFrame(Frame frame) {
        log("局部变量表: {}", frame.localVariables.formatSlots());
        log("操作数栈: {}", frame.operandStack.formatSlots());
    }
}
