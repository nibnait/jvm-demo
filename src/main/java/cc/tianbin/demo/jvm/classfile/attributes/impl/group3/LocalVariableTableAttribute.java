package cc.tianbin.demo.jvm.classfile.attributes.impl.group3;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class LocalVariableTableAttribute extends AttributeInfoRefBase implements AttributeInfo {

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 local_variable_table_length;
     * {
     * u2 start_pc;
     * u2 length;
     * u2 name_index;
     * u2 descriptor_index;
     * u2 index;
     * } local_variable_table[local_variable_table_length];
     */
    private LocalVariableTableEntry[] localVariableTable;

    public LocalVariableTableAttribute(ConstantPool constantPool) {
        super(constantPool);
    }
    
    static class LocalVariableTableEntry {
        private final int startPC;
        private final int length;
        private final int nameIndex;
        private final int descriptorIndex;
        private final int index;

        public LocalVariableTableEntry(ClassReader reader) {
            startPC = reader.readU2ToInt();
            length = reader.readU2ToInt();
            nameIndex = reader.readU2ToInt();
            descriptorIndex = reader.readU2ToInt();
            index = reader.readU2ToInt();
        }
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        int length = reader.readU2ToInt();
        localVariableTable = new LocalVariableTableEntry[length];
        for (int i = 0; i < length; i++) {
            localVariableTable[i] = new LocalVariableTableEntry(reader);
        }
    }
}
