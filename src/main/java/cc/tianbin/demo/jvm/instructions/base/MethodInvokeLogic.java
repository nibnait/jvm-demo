package cc.tianbin.demo.jvm.instructions.base;

import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;
import cc.tianbin.demo.jvm.rtda.frame.Slot;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/12
 */
public class MethodInvokeLogic {

    private MethodInvokeLogic() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static void invokeMethod(Frame invokeFrame, Method method) {
        Thread thread = invokeFrame.thread;
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);

        // 确定方法的参数在局部变量表中占用多少位置
        int argSlotCount = method.getArgSlotCount();
        // 依次把这n个变量从调用者的操作数栈中弹出，放进被调用方法的局部变量表中，参数传递就完成了。
        if (argSlotCount > 0) {
            for (int i = argSlotCount - 1; i >= 0; i--) {
                Slot slot = invokeFrame.operandStack.popSlot();
                newFrame.localVariables.setSlot(i, slot);
            }
        }
    }
}
