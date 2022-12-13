package cc.tianbin.demo.jvm.rtda;

import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import lombok.Data;

/**
 * Created by nibnait on 2022/12/03
 */
@Data
public class Thread {

    // Program Counter 寄存器
    private int pc;

    // 虚拟机栈
    private JvmStack stack;

    public Thread(int maxStack) {
        this.stack = new JvmStack(maxStack);
    }

    public Thread() {
        this.stack = new JvmStack(1024);
    }

    public void pushFrame(Frame frame) {
        this.stack.push(frame);
    }

    public Frame popFrame() {
        return this.stack.pop();
    }

    public Frame currentFrame() {
        return this.stack.top();
    }

    public Frame topFrame() {
        return this.stack.top();
    }

    public Frame newFrame(Method method) {
        return new Frame(this, method);
    }

    public Frame[] getFrames() {
        return this.stack.getFrames();
    }

    public boolean isStackEmpty() {
        return this.stack.isEmpty();
    }

    public void clearStack() {
        this.stack.clear();
    }
}
