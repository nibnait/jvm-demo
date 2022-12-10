package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.exception.JvmStackException;
import cc.tianbin.demo.jvm.rtda.frame.Slot;
import cc.tianbin.demo.jvm.utils.NumberUtil;

/**
 * Created by nibnait on 2022/12/08
 */
public class Slots {

    private Slot[] slots;

    public Slots(int slotCount) {
        if (slotCount < 0) {
            throw new JvmStackException("slotCount length {} error", slotCount);
        }
        slots = new Slot[slotCount];
        for (int i = 0; i < slotCount; i++) {
            slots[i] = new Slot();
        }
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
    public void setRef(int index, MethodAreaObject ref) {
        Slot slot = new Slot();
        slot.setRef(ref);
        slots[index] = slot;
    }

    public MethodAreaObject getRef(int index) {
        return slots[index].getRef();
    }

}
