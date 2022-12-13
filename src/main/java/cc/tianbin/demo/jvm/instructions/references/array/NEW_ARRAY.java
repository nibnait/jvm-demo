package cc.tianbin.demo.jvm.instructions.references.array;

import cc.tianbin.demo.jvm.common.ArrayType;
import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.classloader.JClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/13
 */
public class NEW_ARRAY implements Instruction {

    private int atype;

    @Override
    public int opcode() {
        return 0xbc;
    }

    @Override
    public String operate() {
        return "newarray";
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.atype = reader.readU1ToInt();
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        JClassLoader classLoader = frame.method.getClazz().getLoader();
        ArrayType arrayType = ArrayType.getByCode(this.atype);
        JClass arrClass = classLoader.loadClass(arrayType.getDescriptor());
        JObject arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }
}
