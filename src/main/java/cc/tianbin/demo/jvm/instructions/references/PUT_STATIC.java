package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.common.CommonConstants;
import cc.tianbin.demo.jvm.instructions.base.ClassInitLogic;
import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.FieldRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.*;

/**
 * Created by nibnait on 2022/12/10
 */
public class PUT_STATIC extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb3;
    }

    @Override
    public String operate() {
        return "putstatic";
    }

    @Override
    public void execute(Frame frame) {
        // 当前方法
        Method currentMethod = frame.method;
        // 当前类
        Class currentClazz = currentMethod.getClazz();
        // 当前常量池
        RuntimeConstantPool runTimeConstantPool = currentClazz.getRuntimeConstantPool();
        // 解析字段符合引用
        FieldRef fieldRef = (FieldRef) runTimeConstantPool.getConstants(this.index);
        Field field = fieldRef.resolvedField();

        Class clazz = field.getClazz();
        if (!clazz.isInitStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread, clazz);
            return;
        }

        if (!field.getAccessFlag().isStatic()) {
            throw new IncompatibleClassChangeError("字段必须是 static");
        }

        if (field.getAccessFlag().isFinal()) {
            if (currentClazz != clazz || !CommonConstants.CLINIT.equals(currentMethod.getName())) {
                throw new IllegalAccessError("static final 只能用当前 clinit方法 初始化");
            }
        }

        OperandStack stack = frame.operandStack;
        Slots slots = clazz.getStaticVars();
        int slotId = field.getSlotId();

        FieldDescriptor fieldDescriptor = FieldDescriptor.getByCode(field.getDescriptor());
        switch (fieldDescriptor) {
            case Z:
            case B:
            case C:
            case S:
            case I:
                slots.setInt(slotId, stack.popInt());
                break;
            case F:
                slots.setFloat(slotId, stack.popFloat());
                break;
            case J:
                slots.setLong(slotId, stack.popLong());
                break;
            case D:
                slots.setDouble(slotId, stack.popDouble());
                break;
            case STR:
            case LREF:
            case AREF:
                slots.setRef(slotId, stack.popRef());
                break;
            default:
                break;
        }
    }
}
