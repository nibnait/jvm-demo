package cc.tianbin.demo.jvm.instructions.reserved;

import cc.tianbin.demo.jvm._native.NativeMethod;
import cc.tianbin.demo.jvm._native.Registry;
import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;

/**
 * Created by nibnait on 2022/12/13
 */
public class INVOKE_NATIVE extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0xfe;
    }

    @Override
    public String operate() {
        return "impdep1";
    }

    @Override
    public void execute(Frame frame) {
        Method method = frame.method;
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();

        NativeMethod nativeMethod = Registry.findNativeMethod(className, methodName, methodDescriptor);
        if (nativeMethod == null) {
            throw new UnsatisfiedLinkError(Registry.formatKey(className, methodName, methodDescriptor));
        }

        nativeMethod.invoke(frame);
    }
}
