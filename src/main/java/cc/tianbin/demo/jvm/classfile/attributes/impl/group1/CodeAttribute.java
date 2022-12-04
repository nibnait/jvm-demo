package cc.tianbin.demo.jvm.classfile.attributes.impl.group1;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 * Code 是一个 变长属性，只存在于 method_info 结构中。
 * -
 * u2 attribute_name_index;
 * u4 attribute_length;
 * -
 * // 操作数栈的最大深度
 * u2 max_stack;
 * // 局部变量表大小
 * u2 max_locals;
 * -
 * // 字节码
 * u4 code_length;
 * u1 code[code_length];
 * -
 * // 异常处理表
 * u2 exception_table_length;
 * {
 * u2 start_pc;
 * u2 end_pc;
 * u2 handler_pc;
 * u2 catch_type;
 * } exception_table[exception_table_length];
 * -
 * // 属性表
 * u2 attributes_count;
 * attribute_info attributes[attributes_count];
 */
public class CodeAttribute extends AttributeInfoRefBase implements AttributeInfo {

    // 操作数栈的最大深度
    private int maxStack;
    // 局部变量表大小
    private int maxLocals;
    // 字节码
    private byte[] code;
    // 异常处理表
    private ExceptionTableEntry[] exceptionTable;
    // 属性表
    private AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    public int maxStack() {
        return maxStack;
    }

    public int maxLocals() {
        return maxLocals;
    }

    public byte[] code() {
        return code;
    }

    public ExceptionTableEntry[] exceptionTable() {
        return exceptionTable;
    }

    public AttributeInfo[] attributes() {
        return attributes;
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);

        this.maxStack = reader.readU2ToInt();
        this.maxLocals = reader.readU2ToInt();

        // 字节码
        int codeLength = reader.readU4ToInt();
        this.code = reader.readBytes(codeLength);

        // 异常处理表
        this.exceptionTable = ExceptionTableEntry.readExceptionTable(reader);

        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    static class ExceptionTableEntry {

        private int startPC;
        private int endPC;
        private int handlerPC;
        private int catchType;

        private ExceptionTableEntry(ClassReader reader) {
            this.startPC = reader.readU2ToInt();
            this.endPC = reader.readU2ToInt();
            this.handlerPC = reader.readU2ToInt();
            this.catchType = reader.readU2ToInt();
        }

        public int startPC() {
            return startPC;
        }

        public int endPC() {
            return endPC;
        }

        public int handlerPC() {
            return handlerPC;
        }

        public int catchType() {
            return catchType;
        }

        static ExceptionTableEntry[] readExceptionTable(ClassReader reader) {
            int length = reader.readU2ToInt();
            ExceptionTableEntry[] exceptionTable = new ExceptionTableEntry[length];

            for (int i = 0; i < length; i++) {
                exceptionTable[i] = new ExceptionTableEntry(reader);
            }
            return exceptionTable;
        }
    }

}
