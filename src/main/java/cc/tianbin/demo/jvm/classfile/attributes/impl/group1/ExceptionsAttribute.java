package cc.tianbin.demo.jvm.classfile.attributes.impl.group1;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nibnait on 2022/12/01
 */
public class ExceptionsAttribute extends AttributeInfoRefBase implements AttributeInfo {

    public ExceptionsAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 number_of_exceptions;
     * u2 exception_index_table[number_of_exceptions];
     */
    // 常量池索引 -> ConstantClassInfo -> ConstantUtf8Info 所有异常的名字
    private int[] exceptionIndexTable;

    public List<String> exceptionTable() {
        List<String> exceptionTable = new ArrayList<>();
        for (int exceptionIndex : exceptionIndexTable) {
            exceptionTable.add(constantPool.getClassName(exceptionIndex));
        }
        return exceptionTable;
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.exceptionIndexTable = reader.readUint16s();
    }

}
