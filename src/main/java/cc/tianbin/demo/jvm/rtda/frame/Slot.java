package cc.tianbin.demo.jvm.rtda.frame;

import cc.tianbin.demo.jvm.utils.NumberUtil;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.MethodAreaObject;
import io.github.nibnait.common.utils.DataUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据槽
 * Created by nibnait on 2022/12/04
 */
public class Slot {

    /**
     * 二进制 字符串
     */
    private String num;

    /**
     * >0 为 true
     * <0 为 false
     */
    @Getter
    @Setter
    private boolean flag;

    @Getter
    @Setter
    private MethodAreaObject ref;

    public void setNum(String num) {
        this.num = num;
    }

    public void setNum(int num) {
        this.num = Integer.toBinaryString(num);
    }
    
    public String getNumBinaryStr() {
        return num;
    }

    public String getNumHexStr() {
        return NumberUtil.int2HexString(getNumDecimalInt());
    }
    
    public int getNumDecimalInt() {
        if (num == null) {
            return 0;
        }
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

    public static Slot ref(MethodAreaObject ref) {
        Slot slot = new Slot();
        slot.setRef(ref);
        return slot;
    }
    
    //------------------ formatSlots -------------
    public static String format(Slot[] slots) {
        List<String> slotStrList = new ArrayList<>();
        for (Slot slot : slots) {
            String slotStr = format(slot);
            if (StringUtils.isNotBlank(slotStr)) {
                slotStrList.add(slotStr);
            }
        }
        return DataUtils.toJsonStringArray(slotStrList);
    }

    private static String format(Slot slot) {
        if (StringUtils.isNotBlank(slot.getNumBinaryStr())) {
            return slot.getNumHexStr();
        }
        if (slot.getRef() != null) {
            return slot.getRef().toString();
        }
        return "";
    }

}
