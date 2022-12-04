package cc.tianbin.demo.jvm.rtda.frame;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据槽
 * Created by nibnait on 2022/12/04
 */
public class Slot {

    private String num;

    /**
     * >0 为true  <0 为 false
     */
    @Getter
    @Setter
    private boolean flag;

    @Getter
    @Setter
    private Object ref;


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setNum(int num) {
        this.num = Integer.toBinaryString(num);
    }

    public int getNumInt() {
        return Integer.parseInt(num, 2);
    }

    public static Slot num(int num) {
        Slot slot = new Slot();
        slot.setNum(num);
        return slot;
    }

    public static Slot num(String num, boolean flag) {
        Slot slot = new Slot();
        slot.setNum(num);
        slot.setFlag(flag);
        return slot;
    }

    public static Slot num(int num, boolean flag) {
        Slot slot = new Slot();
        slot.setNum(num);
        slot.setFlag(flag);
        return slot;
    }

    public static Slot ref(Object ref) {
        Slot slot = new Slot();
        slot.setRef(ref);
        return slot;
    }

}
