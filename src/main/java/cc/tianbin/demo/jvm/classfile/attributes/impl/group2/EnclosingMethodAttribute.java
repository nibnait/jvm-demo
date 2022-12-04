package cc.tianbin.demo.jvm.classfile.attributes.impl.group2;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2022/12/01
 */
public class EnclosingMethodAttribute extends AttributeInfoRefBase implements AttributeInfo {
    public EnclosingMethodAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 class_index;
     * u2 method_index;
     */
    private int classIndex;
    private int methodIndex;

    public String className() {
        return this.constantPool.getClassName(this.classIndex);
    }

    public Map<String, String> methodNameAndDescriptor() {
        if (this.methodIndex <= 0) {
            return new HashMap<>();
        }
        return this.constantPool.getNameAndType(this.methodIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.classIndex = reader.readU2ToInt();
        this.methodIndex = reader.readU2ToInt();
    }

}
