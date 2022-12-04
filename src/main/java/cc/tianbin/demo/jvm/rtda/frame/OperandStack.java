package cc.tianbin.demo.jvm.rtda.frame;

import cc.tianbin.demo.jvm.exception.JvmStackException;

/**
 * 操作数栈
 * Created by nibnait on 2022/12/04
 */
public class OperandStack {

    private int size = 0;
    private Slot[] slots;

    public OperandStack(int maxStack) {
        if (maxStack < 0) {
            throw new JvmStackException("OperandStack length {} error", maxStack);
        }
        slots = new Slot[maxStack];
        for (int i = 0; i < maxStack; i++) {
            slots[i] = new Slot();
        }
    }

    public void pushSlot(Slot slot) {
        if (size == slots.length) {
            throw new JvmStackException("OperandStack is overflow");
        }
        slots[size++] = slot;
    }

    public Slot popSlot() {
        if (size == 0) {
            throw new JvmStackException("OperandStack is empty");
        }
        return slots[--size];
    }

    //----------- boolean, byte, short, char 也一律按 int 处理 ---------------
    public void pushInt(int val) {
        pushSlot(Util.setInt(val));
    }

    public int popInt() {
        return Util.getInt(popSlot());
    }

    //----------- float 按 int 处理 ---------------
    public void pushFloat(float val) {
        pushSlot(Slot.num(Float.floatToIntBits(val)));
    }

    public Float popFloat() {
        return Float.intBitsToFloat(popSlot().getNumInt());
    }

    //----------- long 和 double 占2个slot ---------------
    public void pushLong(long val) {
        Slot[] slots = Util.setLong(val);
        pushSlot(slots[0]);
        pushSlot(slots[1]);
    }

    public Long popLong() {
        Slot low = popSlot();
        Slot high = popSlot();
        return Util.getLong(new Slot[]{high, low});
    }

    public void pushDouble(double val) {
        pushLong(Double.doubleToLongBits(val));
    }

    public double popDouble() {
        return Double.longBitsToDouble(popLong());
    }

    //----------- 引用值 ---------------
    public void pushRef(Object ref) {
        pushSlot(Slot.ref(ref));
    }

    public Object popRef() {
        return popSlot().getRef();
    }

}
