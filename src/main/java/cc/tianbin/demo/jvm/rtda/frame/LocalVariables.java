package cc.tianbin.demo.jvm.rtda.frame;

import cc.tianbin.demo.jvm.exception.JvmStackException;

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
    private Slot[] localVars;

    public static void main(String[] args) {
        int[] ii = new int[0];
        System.out.println(ii);
    }

    public LocalVariables(int maxLocals) {
        if (maxLocals < 0) {
            throw new JvmStackException("LocalVars length {} error", maxLocals);
        }
        localVars = new Slot[maxLocals];
        for (int i = 0; i < maxLocals; i++) {
            localVars[i] = new Slot();
        }
    }

    //----------- boolean, byte, short, char 也一律按 int 处理 ---------------
    public void setInt(int index, int val) {
        this.localVars[index] = Util.setInt(val);
    }

    public int getInt(int index) {
        return Util.getInt(localVars[index]);
    }

    //----------- float 按 int 处理 ---------------
    public void setFloat(int index, float val) {
        int num = Float.floatToIntBits(val);
        this.localVars[index] = Util.setInt(num);
    }

    public Float getFloat(int index) {
        int num = Util.getInt(localVars[index]);
        return Float.intBitsToFloat(num);
    }

    //----------- long 和 double 占2个slot ---------------
    public void setLong(int index, long val) {
        Slot[] slots = Util.setLong(val);
        this.localVars[index] = slots[0];
        this.localVars[index + 1] = slots[1];
    }

    public Long getLong(int index) {
        Slot high = this.localVars[index];
        Slot low = this.localVars[index + 1];
        return Util.getLong(new Slot[]{high, low});
    }

    public void setDouble(int index, double val) {
        setLong(index, Double.doubleToLongBits(val));
    }

    public Double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    //----------- 引用值 ---------------
    public void setRef(int index, Object ref) {
        Slot slot = new Slot();
        slot.setRef(ref);
        localVars[index] = slot;
    }

    public Object getRef(int index) {
        return localVars[index].getRef();
    }

}
