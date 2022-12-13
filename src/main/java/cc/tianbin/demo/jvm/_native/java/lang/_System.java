package cc.tianbin.demo.jvm._native.java.lang;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.LocalVariables;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JArrayObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/13
 */
public class _System {

    /**
     * @see java.lang.System
     */
    private final String jlSystem = "java/lang/System";

    public _System() {
        Registry.register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", new NativeMethod( "arraycopy", this));
        Registry.register(jlSystem, "registerNatives", "()V", new NativeMethod( "registerNatives", this));
    }

    /**
     * @see System#registerNatives()
     */
    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void arraycopy(Frame frame) {
        LocalVariables vars = frame.localVariables;
        JObject src = vars.getRef(0);
        int srcPos = vars.getInt(1);
        JObject dest = vars.getRef(2);
        int destPos = vars.getInt(3);
        int length = vars.getInt(4);

        if (src == null || dest == null) {
            throw new NullPointerException();
        }

        if (!JArrayObject.checkArrayCopy(src, dest)) {
            throw new ArrayStoreException("原数组和目标数组 元素类型不兼容");
        }

        if (srcPos < 0 || destPos < 0 || length < 0 ||
                srcPos + length > src.arrayLength() ||
                destPos + length > dest.arrayLength()) {
            throw new IndexOutOfBoundsException();
        }

        dest.data = JArrayObject.arrayCopy(src, srcPos, length);
    }

}
