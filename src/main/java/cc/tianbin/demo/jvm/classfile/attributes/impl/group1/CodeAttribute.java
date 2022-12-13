package cc.tianbin.demo.jvm.classfile.attributes.impl.group1;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group3.LineNumberTableAttribute;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.ExceptionTable;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import lombok.Getter;

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
    @Getter
    private int maxStack;
    // 局部变量表大小
    @Getter
    private int maxLocals;
    // 字节码
    @Getter
    private byte[] bytecode;
    // 异常处理表
    @Getter
    private ExceptionTableEntry[] exceptionTable;
    // 属性表
    @Getter
    private AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);

        this.maxStack = reader.readU2ToInt();
        this.maxLocals = reader.readU2ToInt();

        // 字节码
        int codeLength = reader.readU4ToInt();
        this.bytecode = reader.readBytes(codeLength);

        // 异常处理表
        this.exceptionTable = ExceptionTableEntry.readExceptionTable(reader);

        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    public LineNumberTableAttribute lineNumberTableAttribute() {
        for (AttributeInfo attrInfo : this.attributes) {
            if (attrInfo instanceof LineNumberTableAttribute) {
                return (LineNumberTableAttribute) attrInfo;
            }
        }
        return null;
    }

    @Getter
    public static class ExceptionTableEntry {

        /**
         * start_pc 和 end_pc 可以锁定一部分字节码，这部分字节码对应某个可能抛出异常的 try{} 代码块
         */
        private int startPC;
        private int endPC;
        /**
         * 指出负责异常处理的 catch{} 块在哪里。
         */
        private int handlerPC;
        /**
         * catch_type是个索引，通过它可以从运行时常量池中查到一个类符号引用，解析后的类是个异常类X
         * 如果位于 start_pc 和 end_pc 之间的指令抛出异常x，且x是X（或者X的子类）的实例，则执行 catch{} 块的内容
         * @see ExceptionTable#findExceptionHandler(JClass, int)
         */
        private int catchType;

        private ExceptionTableEntry(ClassReader reader) {
            this.startPC = reader.readU2ToInt();
            this.endPC = reader.readU2ToInt();
            this.handlerPC = reader.readU2ToInt();
            this.catchType = reader.readU2ToInt();
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
