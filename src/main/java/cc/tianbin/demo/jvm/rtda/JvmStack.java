package cc.tianbin.demo.jvm.rtda;

import cc.tianbin.demo.jvm.exception.JvmStackException;

/**
 * Created by nibnait on 2022/12/03
 */
public class JvmStack {

    private final int maxSize;
    private int size;
    private Frame top;

    public JvmStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(Frame frame) {
        if (this.size > this.maxSize) {
            throw new JvmStackException("jvm stack is overflow");
        }

        if (this.top != null) {
            frame.lower = this.top;
        }

        this.top = frame;
        this.size++;
    }

    public Frame pop() {
        if (this.top == null) {
            throw new JvmStackException("jvm stack is empty!");
        }

        Frame top = this.top;
        this.top = top.lower;
        top.lower = null;
        this.size--;

        return top;
    }

    public Frame top(){
        if (this.top == null){
            throw new JvmStackException("jvm stack is empty!");
        }
        return this.top;
    }

}
