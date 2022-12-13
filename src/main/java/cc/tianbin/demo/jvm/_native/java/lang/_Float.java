package cc.tianbin.demo.jvm._native.java.lang;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.rtda.Frame;

/**
 * Created by nibnait on 2022/12/13
 */
public class _Float {

    /**
     * @see java.lang.Float
     */
    private final String jlFloat = "java/lang/Float";

    public _Float() {
        Registry.register(jlFloat, "floatToRawIntBits", "(F)I", new NativeMethod( "floatToRawIntBits", this));
        Registry.register(jlFloat,"intBitsToFloat", "(I)F",new NativeMethod("intBitsToFloat", this));
        Registry.register(jlFloat,"registerNatives", "()V",new NativeMethod("registerNatives", this));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    /**
     * @see Float#floatToRawIntBits(float)
     */
    public void floatToRawIntBits(Frame frame){
        float val = frame.localVariables.getFloat(0);
        frame.operandStack.pushInt((int) val);
    }

    /**
     * @see Float#intBitsToFloat(int)
     */
    public void intBitsToFloat(Frame frame){
        int val = frame.localVariables.getInt(0);
        frame.operandStack.pushFloat(val);
    }

}
