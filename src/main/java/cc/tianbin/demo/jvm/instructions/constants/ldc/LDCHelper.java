package cc.tianbin.demo.jvm.instructions.constants.ldc;

import cc.tianbin.demo.jvm.exception.InstructionException;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.ClassRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.StringPool;

/**
 * Created by nibnait on 2022/12/10
 */
public class LDCHelper {

    private LDCHelper() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static void ldc(Frame frame, int index) {
        OperandStack stack = frame.operandStack;
        JClass clazz = frame.method.getClazz();
        RuntimeConstantPool runTimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        Object c = runTimeConstantPool.getConstants(index);

        if (c instanceof Integer) {
            stack.pushInt((Integer) c);
            return;
        }

        if (c instanceof Float) {
            stack.pushFloat((Float) c);
            return;
        }

        if (c instanceof String) {
            JObject internedStr = StringPool.jString(clazz.getLoader(), (String) c);
            stack.pushRef(internedStr);
            return;
        }

        if (c instanceof ClassRef) {
            ClassRef classRef = (ClassRef) c;
            JObject classObj = classRef.resolvedClass().jClass;
            stack.pushRef(classObj);
            return;
        }

        throw new InstructionException("invalid ldc {}", c.getClass().getName());
    }
}
