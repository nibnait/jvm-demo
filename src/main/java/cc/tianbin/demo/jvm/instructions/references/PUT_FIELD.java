package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.common.CommonConstants;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.FieldDescriptor;
import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.FieldRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Field;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JVMMAObject;

/**
 * Created by nibnait on 2022/12/10
 */
public class PUT_FIELD extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb5;
    }

    @Override
    public String operate() {
        return "putfield";
    }

    @Override
    public void execute(Frame frame) {
        Method currentMethod = frame.method;
        Class currentClazz = currentMethod.getClazz();
        RuntimeConstantPool runTimeConstantPool = currentClazz.getRuntimeConstantPool();
        FieldRef fieldRef = (FieldRef) runTimeConstantPool.getConstants(this.index);
        Field field = fieldRef.resolvedField();
        if (field.getAccessFlag().isStatic()) {
            throw new IncompatibleClassChangeError("无法给静态变量赋值");
        }

        if (field.getAccessFlag().isFinal()) {
            if (currentClazz != field.getClazz() || !CommonConstants.INIT.equals(currentMethod.getName())) {
                throw new IllegalAccessError("final 修饰的实例变量 只能用当前 <init>方法 初始化");
            }
        }

        OperandStack stack = frame.operandStack;
        int slotId = field.getSlotId();

        FieldDescriptor fieldDescriptor = FieldDescriptor.getByCode(field.getDescriptor());
        switch (fieldDescriptor) {
            case Z:
            case B:
            case C:
            case S:
            case I:
                int valInt = stack.popInt();
                JVMMAObject refInt = stack.popRef();
                if (null == refInt) {
                    throw new NullPointerException();
                }
                refInt.getSlots().setInt(slotId, valInt);
                break;
            case F:
                float valFloat = stack.popFloat();
                JVMMAObject refFloat = stack.popRef();
                if (null == refFloat) {
                    throw new NullPointerException();
                }
                refFloat.getSlots().setFloat(slotId, valFloat);
                break;
            case J:
                long valLong = stack.popLong();
                JVMMAObject refLong = stack.popRef();
                if (null == refLong) {
                    throw new NullPointerException();
                }
                refLong.getSlots().setLong(slotId, valLong);
                break;
            case D:
                double valDouble = stack.popDouble();
                JVMMAObject refDouble = stack.popRef();
                if (null == refDouble) {
                    throw new NullPointerException();
                }
                refDouble.getSlots().setDouble(slotId, valDouble);
                break;
            case STR:
            case L_REF:
            case A_REF:
                JVMMAObject val = stack.popRef();
                JVMMAObject ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.getSlots().setRef(slotId, val);
                break;
            default:
                break;
        }
    }
}
