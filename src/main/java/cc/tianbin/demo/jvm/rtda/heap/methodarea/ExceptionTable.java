package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.ClassRef;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;

/**
 * Created by nibnait on 2022/12/13
 */
public class ExceptionTable {

    private ExceptionHandler[] exceptionTable;

    public ExceptionTable(CodeAttribute.ExceptionTableEntry[] entries, RuntimeConstantPool runTimeConstantPool) {
        this.exceptionTable = new ExceptionHandler[entries.length];
        int i = 0;
        for (CodeAttribute.ExceptionTableEntry entry : entries) {
            ExceptionHandler handler = new ExceptionHandler(
                    entry.getStartPC(),
                    entry.getEndPC(),
                    entry.getHandlerPC(),
                    getCatchType(entry.getCatchType(), runTimeConstantPool)
            );
            this.exceptionTable[i++] = handler;
        }
    }

    public ClassRef getCatchType(int index, RuntimeConstantPool runTimeConstantPool) {
        if (index == 0) {
            return null;
        }
        return (ClassRef) runTimeConstantPool.getConstants(index);
    }

    public ExceptionHandler findExceptionHandler(JClass exClass, int pc) {
        for (ExceptionHandler handler : exceptionTable) {
            if (pc >= handler.startPC && pc < handler.endPC) {
                if (null == handler.catchType) {
                    return handler;
                }
                JClass catchClass = handler.catchType.resolvedClass();
                if (catchClass == exClass || catchClass.isSubClassOf(exClass)) {
                    return handler;
                }
            }
        }
        return null;
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-3.html#jvms-3.12
     */
    class ExceptionHandler {

        // try{} 语句块的第一条指令
        int startPC;
        // try{} 语句块的下一条指令
        int endPC;
        int handlerPC;
        // null(在class文件中是0) 标识可以处理所有异常
        ClassRef catchType;

        ExceptionHandler(int startPC, int endPC, int handlerPC, ClassRef catchType) {
            this.startPC = startPC;
            this.endPC = endPC;
            this.handlerPC = handlerPC;
            this.catchType = catchType;
        }
    }

}
