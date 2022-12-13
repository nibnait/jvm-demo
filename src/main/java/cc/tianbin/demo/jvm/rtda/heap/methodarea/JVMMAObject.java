package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.rtda.heap.classloader.LinkingHelper;
import io.github.nibnait.common.utils.DataUtils;
import lombok.Getter;

/**
 * Java Virtual Machine Method Area Object
 * Created by nibnait on 2022/12/08
 */
public class JVMMAObject {

    @Getter
    private Class clazz;
    @Getter
    private Object data;

    /**
     * 新创建对象的实例变量都是直接赋好【初始值】，不需要做额外的工作
     * @see LinkingHelper#initStaticVar
     */
    // 普通对象初始化
    public static JVMMAObject newObject(Class clazz) {
        JVMMAObject object = new JVMMAObject();
        object.clazz = clazz;
        object.data = new Slots(clazz.getInstanceSlotCount());
        return object;
    }

    // 数组对象初始化
    public static JVMMAObject newObject(Class clazz, Object data) {
        JVMMAObject object = new JVMMAObject();
        object.clazz = clazz;
        object.data = data;
        return object;
    }

    /**
     * instanceof
     */
    public boolean isInstanceOf(Class other) {
        return this.clazz.isAssignableFrom(other);
    }

    @Override
    public String toString() {
        return DataUtils.format(
                "{} {}", clazz.getName(), data.toString()
        );
    }

    //---------- get 普通对象 --------------------
    public Slots getSlots() {
        return (Slots) this.data;
    }

    public JVMMAObject getRefVar(String name, String descriptor) {
        Field field = this.clazz.getField(name, descriptor, false);
        Slots slots = (Slots) this.data;
        return slots.getRef(field.getSlotId());
    }

    public void setRefVal(String name, String descriptor, JVMMAObject ref) {
        Field field = this.clazz.getField(name, descriptor, false);
        Slots slots = (Slots) this.data;
        slots.setRef(field.getSlotId(), ref);
    }

    //---------- get 数组对象 --------------------
    public byte[] bytes() {
        return (byte[]) this.data;
    }

    public short[] shorts() {
        return (short[]) this.data;
    }

    public int[] ints() {
        return (int[]) this.data;
    }

    public long[] longs() {
        return (long[]) this.data;
    }

    public char[] chars() {
        return (char[]) this.data;
    }

    public float[] floats() {
        return (float[]) this.data;
    }

    public double[] doubles() {
        return (double[]) this.data;
    }

    public JVMMAObject[] refs() {
        return (JVMMAObject[]) this.data;
    }

    public int arrayLength() {
        if (this.data instanceof byte[]) {
            return ((byte[]) this.data).length;
        }

        if (this.data instanceof short[]) {
            return ((short[]) this.data).length;
        }

        if (this.data instanceof int[]) {
            return ((int[]) this.data).length;
        }

        if (this.data instanceof long[]) {
            return ((long[]) this.data).length;
        }

        if (this.data instanceof char[]) {
            return ((char[]) this.data).length;
        }

        if (this.data instanceof float[]) {
            return ((float[]) this.data).length;
        }

        if (this.data instanceof double[]) {
            return ((double[]) this.data).length;
        }

        if (this.data instanceof Object[]) {
            return ((Object[]) this.data).length;
        }

        throw new RuntimeException("Not array");
    }

}
