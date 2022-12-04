package cc.tianbin.demo.jvm.rtda;

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

    public Thread(int maxStack){
        this.stack = new JvmStack(1024);
    }

    public Thread(){
        this.stack = new JvmStack(1024);
    }

    public void pushFrame(Frame frame){
        this.stack.push(frame);
    }

    public Frame popFrame(){
        return this.stack.pop();
    }

    public Frame currentFrame(){
        return this.stack.top();
    }

}
