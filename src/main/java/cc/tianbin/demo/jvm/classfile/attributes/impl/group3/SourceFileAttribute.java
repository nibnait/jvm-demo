package cc.tianbin.demo.jvm.classfile.attributes.impl.group3;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class SourceFileAttribute extends AttributeInfoRefBase implements AttributeInfo {

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 source_file_index;
     */
    // 常量池索引 -> ConstantUtf8Info -> 文件名
    private int sourceFileIndex;

    public SourceFileAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    public String fileName() {
        return constantPool.getUTF8(sourceFileIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.sourceFileIndex = reader.readU2ToInt();
    }
}
