package cc.tianbin.demo.jvm.classfile.attributes.impl.group3;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class LocalVariableTypeTableAttribute extends AttributeInfoRefBase implements AttributeInfo {
    public LocalVariableTypeTableAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     *     u2 attribute_name_index;
     *     u4 attribute_length;
     *     u2 local_variable_type_table_length;
     *     {   u2 start_pc;
     *         u2 length;
     *         u2 name_index;
     *         u2 signature_index;
     *         u2 index;
     *     } local_variable_type_table[local_variable_type_table_length];
     */
    private LocalVariableTypeTableEntry[] localVariableTypeTable;

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        int localVariableTypeTableLength = reader.readU2ToInt();
        this.localVariableTypeTable = new LocalVariableTypeTableEntry[localVariableTypeTableLength];
        for (int i = 0; i < localVariableTypeTableLength; i++) {
            LocalVariableTypeTableEntry localVariableTypeTableEntry = new LocalVariableTypeTableEntry();
            localVariableTypeTableEntry.readInfo(reader);
            this.localVariableTypeTable[i] = localVariableTypeTableEntry;
        }
    }

    static class LocalVariableTypeTableEntry {
        private int startPC;
        private int length;
        private int nameIndex;
        private int signatureIndex;
        private int index;

        public void readInfo(ClassReader reader) {
            this.startPC = reader.readU2ToInt();
            this.length = reader.readU2ToInt();
            this.nameIndex = reader.readU2ToInt();
            this.signatureIndex = reader.readU2ToInt();
            this.index = reader.readU2ToInt();
        }
    }

}
