package cc.tianbin.demo.jvm.instructions.references;

import cc.tianbin.demo.jvm.instructions.base.ClassInitLogic;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.FieldDescriptor;
import cc.tianbin.demo.jvm.instructions.base.Index16Instruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.FieldRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Field;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Slots;

/**
 * Created by nibnait on 2022/12/10
 */
public class GET_STATIC extends Index16Instruction {
    @Override
    public int opcode() {
        return 0xb2;
    }

    @Override
    public String operate() {
        return "getstatic";
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.method.getClazz().getRuntimeConstantPool();
        FieldRef fieldRef = (FieldRef) runtimeConstantPool.getConstants(this.index);
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

        OperandStack stack = frame.operandStack;
        int slotId = field.getSlotId();
        Slots slots = clazz.getStaticVars();

        FieldDescriptor fieldDescriptor = FieldDescriptor.getByCode(field.getDescriptor());
        switch (fieldDescriptor) {
            case Z:
            case B:
            case C:
            case S:
            case I:
                stack.pushInt(slots.getInt(slotId));
                break;
            case F:
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case J:
                stack.pushLong(slots.getLong(slotId));
                break;
            case D:
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case STR:
            case L_REF:
            case A_REF:
                stack.pushRef(slots.getRef(slotId));
                break;
            default:
                break;

        }

    }
}
