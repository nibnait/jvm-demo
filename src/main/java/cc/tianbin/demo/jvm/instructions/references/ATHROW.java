package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm._native.java.lang._Throwable;
import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.StringPool;
import cc.tianbin.demo.jvm.utils.LogUtil;

/**
 * Created by nibnait on 2022/12/13
 */
public class ATHROW extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0xbf;
    }

    /**
     * @see Throwable#fillInStackTrace()
     */
    @Override
    public void execute(Frame frame) {
        JObject ex = frame.operandStack.popRef();
        if (ex == null) {
            throw new NullPointerException();
        }

        Thread thread = frame.thread;
        if (!findAndGotoExceptionHandler(thread, ex)) {
            handleUncaughtException(thread, ex);
        }
    }

    /**
     * 从当前帧开始遍历虚拟机栈，查找方法的异常处理表
     * 看是否可以找到并跳转到异常处理代码
     */
    private boolean findAndGotoExceptionHandler(Thread thread, JObject ex) {
        do {
            Frame frame = thread.currentFrame();
            int pc = frame.nextPC - 1;

            int handlerPc = frame.method.findExceptionHandler(ex.getClazz(), pc);
            if (handlerPc > 0) {
                OperandStack stack = frame.operandStack;
                stack.clear();
                stack.pushRef(ex);
                frame.nextPC = handlerPc;
                return true;
            }

            thread.popFrame();
        } while (!thread.isStackEmpty());

        return false;
    }

    /**
     * 遍历完整个虚拟机栈，还是找不到异常处理代码
     * 则直接打印 虚拟机栈信息
     */
    private void handleUncaughtException(Thread thread, JObject ex) {
        // 清空虚拟机栈，终止接触器继续执行
        thread.clearStack();

        JObject jMsg = ex.getRefVar("detailMessage", "Ljava/lang/String;");

        String msg = StringPool.goString(jMsg);

        LogUtil.log(ex.getClazz().javaName() + "：" + msg);

        Object extra = ex.extra;

        _Throwable[] throwables = (_Throwable[]) extra;
        for (_Throwable t : throwables) {
            LogUtil.log(t.string());
        }
    }

}
