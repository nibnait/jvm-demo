package cc.tianbin.demo.jvm.instructions.extended;

import cc.tianbin.demo.jvm.instructions.Instruction;
import cc.tianbin.demo.jvm.instructions.base.BytecodeReader;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.ClassRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/13
 */
public class MULTI_ANEW_ARRATY implements Instruction {

    private int index;
    private int dimensions;

    @Override
    public int opcode() {
        return 0xc5;
    }

    @Override
    public String operate() {
        return "multianewarray";
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        // 运行时常量池索引 -> 类的符号引用
        this.index = reader.readU2ToInt();
        // 数组的维度
        this.dimensions = reader.readU1ToInt();
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef) runtimeConstantPool.getConstants(this.index);
        Class arrClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack;
        int[] counts = popAndCheckCounts(stack, this.dimensions);
        JVMMAObject arr = newMultiDimensionalArray(counts, arrClass);
        stack.pushRef(arr);
    }

    private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = stack.popInt();
            if (counts[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }

        return counts;
    }

    private JVMMAObject newMultiDimensionalArray(int[] counts, Class arrClass) {
        int count = counts[0];
        JVMMAObject arr = arrClass.newArray(count);
        if (counts.length > 1) {
            JVMMAObject[] refs = arr.refs();
            for (int i = 0; i < refs.length; i++) {
                int[] copyCount = new int[counts.length - 1];
                System.arraycopy(counts, 1, copyCount, 0, counts.length - 1);
                refs[i] = newMultiDimensionalArray(copyCount, arrClass.componentClass());
            }
        }

        return arr;
    }

}
