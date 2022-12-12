package cc.tianbin.demo.jvm.rtda.frame;

import cc.tianbin.demo.jvm.exception.JvmStackException;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;
import cc.tianbin.demo.jvm.utils.NumberUtil;

/**
 * 局部变量表
 * Created by nibnait on 2022/12/04
 */
public class LocalVariables {

    /**
     * 数据槽
     * 每个元素至少可以容纳一个 int 或 引用值
     * 两个连续的元素 可以容纳一个 long/double 值
     */
    private Slot[] slots;

    public LocalVariables(int maxLocals) {
        if (maxLocals < 0) {
            throw new JvmStackException("LocalVars length {} error", maxLocals);
        }
        slots = new Slot[maxLocals];
        for (int i = 0; i < maxLocals; i++) {
            slots[i] = new Slot();
        }
    }

    public String formatSlots() {
        return Slot.format(slots);
    }

    public void setSlot(int index, Slot slot) {
        this.slots[index] = slot;
    }

    //----------- boolean, byte, short, char 也一律按 int 处理 ---------------
    public void setInt(int index, int val) {
        this.slots[index] = NumberUtil.setInt(val);
    }

    public int getInt(int index) {
        return NumberUtil.getInt(slots[index]);
    }

    //----------- float 按 int 处理 ---------------
    public void setFloat(int index, float val) {
        int num = Float.floatToIntBits(val);
        this.slots[index] = NumberUtil.setInt(num);
    }

    public Float getFloat(int index) {
        int num = NumberUtil.getInt(slots[index]);
        return Float.intBitsToFloat(num);
    }

    //----------- long 和 double 占2个slot ---------------
    public void setLong(int index, long val) {
        Slot[] slots = NumberUtil.setLong(val);
        this.slots[index] = slots[0];
        this.slots[index + 1] = slots[1];
    }

    public Long getLong(int index) {
        Slot high = this.slots[index];
        Slot low = this.slots[index + 1];
        return NumberUtil.getLong(new Slot[]{high, low});
    }

    public void setDouble(int index, double val) {
        setLong(index, Double.doubleToLongBits(val));
    }

    public Double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    //----------- 引用值 ---------------
    public void setRef(int index, JVMMAObject ref) {
        Slot slot = new Slot();
        slot.setRef(ref);
        slots[index] = slot;
    }

    public JVMMAObject getRef(int index) {
        return slots[index].getRef();
    }

}
