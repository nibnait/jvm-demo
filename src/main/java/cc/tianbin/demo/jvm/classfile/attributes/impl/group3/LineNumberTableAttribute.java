package cc.tianbin.demo.jvm.classfile.attributes.impl.group3;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class LineNumberTableAttribute extends AttributeInfoRefBase implements AttributeInfo {

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 line_number_table_length;
     * {
     * u2 start_pc;
     * u2 line_number;
     * } line_number_table[line_number_table_length];
     */
    private LineNumberTableEntry[] lineNumberTable;

    public int getLineNumber(int pc) {
        for (int i = this.lineNumberTable.length - 1; i >= 0; i--) {
            LineNumberTableEntry entry = this.lineNumberTable[i];
            if (pc >= entry.startPC){
                return entry.lineNumber;
            }
        }
        return -1;
    }

    public LineNumberTableAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    private static class LineNumberTableEntry {
        private final int startPC;
        private final int lineNumber;

        public LineNumberTableEntry(ClassReader reader) {
            startPC = reader.readU2ToInt();
            lineNumber = reader.readU2ToInt();
        }
    }
    
    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        int length = reader.readU2ToInt();

        lineNumberTable = new LineNumberTableEntry[length];

        for (int i = 0; i < length; i++) {
            lineNumberTable[i] = new LineNumberTableEntry(reader);
        }
    }
}
