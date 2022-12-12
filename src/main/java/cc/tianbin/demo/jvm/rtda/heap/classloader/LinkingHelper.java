package cc.tianbin.demo.jvm.rtda.heap.classloader;

import cc.tianbin.demo.jvm.rtda.heap.methodarea.FieldDescriptor;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Field;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Slots;

/**
 * 2. 链接（Linking）
 * Created by nibnait on 2022/12/08
 */
public class LinkingHelper {
    
    private LinkingHelper() {
        throw new AssertionError("工具类不允许被实例化");
    }

    //----------------- 验证 ------------------
    public static void verify(Class clazz) {
        // 校验字节码
    }

    //----------------- 准备 ------------------
    public static void prepare(Class clazz) {
        // 计算非静态字段的 槽位
        calcInstanceFieldSlotIds(clazz);

        // 计算静态字段的 槽位
        calcStaticFieldSlotIds(clazz);
        // 为类中定义的静态变量分配内存
        allocAndInitStaticVars(clazz);
    }

    private static void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = 0;
        if (clazz.getSuperClass() != null) {
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        for (Field field : clazz.getFields()) {
            if (!field.getAccessFlag().isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (FieldDescriptor.isLongOrDouble(field.getDescriptor())) {
                    slotId++;
                }
            }
        }
        clazz.setInstanceSlotCount(slotId);
    }

    private static void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field : clazz.getFields()) {
            if (field.getAccessFlag().isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (FieldDescriptor.isLongOrDouble(field.getDescriptor())) {
                    slotId++;
                }
            }
        }
        clazz.setStaticSlotCount(slotId);
    }

    private static void allocAndInitStaticVars(Class clazz) {
        for (Field field : clazz.getFields()) {
            if (field.getAccessFlag().isStatic() && field.getAccessFlag().isFinal()) {
                initStaticFinalVar(clazz, field);
            } else if (field.getAccessFlag().isStatic()) {
                initStaticVar(clazz, field);
            }
        }
    }

    /**
     * 单被 static 修饰的变量，只赋当前字段类型的初始值
     * 他们要等 <clinit>() 方法执行时，才会被真正初始化
     */
    private static void initStaticVar(Class clazz, Field field) {
        Slots staticVars = clazz.getStaticVars();
        RuntimeConstantPool constantPool = clazz.getRuntimeConstantPool();
        int cpIdx = field.getConstantValueIndex();
        int slotId = field.getSlotId();

        if (cpIdx > 0) {
            FieldDescriptor fieldDescriptor = FieldDescriptor.getByCode(field.getDescriptor());
            switch (fieldDescriptor) {
                case Z:
                case B:
                case S:
                case C:
                case I:
                    staticVars.setInt(slotId, (int) fieldDescriptor.getDefaultValue());
                    break;
                case J:
                    staticVars.setLong(slotId, (long) constantPool.getConstants(cpIdx));
                    break;
                case F:
                    staticVars.setFloat(slotId, (float) constantPool.getConstants(cpIdx));
                    break;
                case D:
                    staticVars.setDouble(slotId, (double) constantPool.getConstants(cpIdx));
                    break;
                case STR:
                    // todo 依赖 StringPool
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 被 static final 修饰的变量，直接赋当前field.constantValueIndex 对应的常量池中的值
     */
    private static void initStaticFinalVar(Class clazz, Field field) {
        Slots staticVars = clazz.getStaticVars();
        RuntimeConstantPool constantPool = clazz.getRuntimeConstantPool();
        int cpIdx = field.getConstantValueIndex();
        int slotId = field.getSlotId();

        if (cpIdx > 0) {
            FieldDescriptor fieldDescriptor = FieldDescriptor.getByCode(field.getDescriptor());
            switch (fieldDescriptor) {
                case Z:
                case B:
                case S:
                case C:
                case I:
                    Object val = constantPool.getConstants(cpIdx);
                    staticVars.setInt(slotId, (Integer) val);
                    break;
                case J:
                    staticVars.setLong(slotId, (Long) constantPool.getConstants(cpIdx));
                    break;
                case F:
                    staticVars.setFloat(slotId, (Float) constantPool.getConstants(cpIdx));
                    break;
                case D:
                    staticVars.setDouble(slotId, (Double) constantPool.getConstants(cpIdx));
                    break;
                case STR:
                    // todo 依赖 StringPool
                    break;
                default:
                    break;
            }
        }
    }

    //----------------- 准备 ------------------

}
