package cc.tianbin.demo.jvm._native.java.lang;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/13
 */
public class _Double {

    /**
     * @see java.lang.Double
     */
    private final String jlDouble = "java/lang/Double";

    public _Double() {
        Registry.register(jlDouble, "doubleToRawLongBits", "(D)J", new NativeMethod( "doubleToRawLongBits", this));
        Registry.register(jlDouble,"longBitsToDouble", "(J)D",new NativeMethod("longBitsToDouble", this));
        Registry.register(jlDouble,"registerNatives", "()V",new NativeMethod("registerNatives", this));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    /**
     * @see Double#doubleToRawLongBits(double)
     */
    public void doubleToRawLongBits(Frame frame) {
        double val = frame.localVariables.getDouble(0);
        frame.operandStack.pushLong((long) val);
    }

    /**
     * @see Double#doubleToRawLongBits(double)
     */
    public void longBitsToDouble(Frame frame) {
        long val = frame.localVariables.getLong(0);
        frame.operandStack.pushDouble(val);
    }

}
