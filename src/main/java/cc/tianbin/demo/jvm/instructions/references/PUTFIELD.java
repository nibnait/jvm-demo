package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.common.CommonConstants;
import cc.tianbin.demo.jvm.common.FieldDescriptor;
import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.FieldRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Field;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.MethodAreaObject;

/**
 * Created by nibnait on 2022/12/10
 */
public class PUTFIELD extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb5;
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
                MethodAreaObject refInt = stack.popRef();
                if (null == refInt) {
                    throw new NullPointerException();
                }
                refInt.getFields().setInt(slotId, valInt);
                break;
            case F:
                float valFloat = stack.popFloat();
                MethodAreaObject refFloat = stack.popRef();
                if (null == refFloat) {
                    throw new NullPointerException();
                }
                refFloat.getFields().setFloat(slotId, valFloat);
                break;
            case J:
                long valLong = stack.popLong();
                MethodAreaObject refLong = stack.popRef();
                if (null == refLong) {
                    throw new NullPointerException();
                }
                refLong.getFields().setLong(slotId, valLong);
                break;
            case D:
                double valDouble = stack.popDouble();
                MethodAreaObject refDouble = stack.popRef();
                if (null == refDouble) {
                    throw new NullPointerException();
                }
                refDouble.getFields().setDouble(slotId, valDouble);
                break;
            case STR:
            case LREF:
            case AREF:
                MethodAreaObject val = stack.popRef();
                MethodAreaObject ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.getFields().setRef(slotId, val);
                break;
            default:
                break;
        }
    }
}
