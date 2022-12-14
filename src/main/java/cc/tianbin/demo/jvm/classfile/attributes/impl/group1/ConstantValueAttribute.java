package cc.tianbin.demo.jvm.classfile.attributes.impl.group1;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import lombok.Getter;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantValueAttribute extends AttributeInfoRefBase implements AttributeInfo {

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 constant_value_index;//常量池索引
     */
    //具体指向哪种常量因字段类型而异。
    @Getter
    private int constantValueIndex;

    public ConstantValueAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    public Object constantValue() {
        ConstantInfo constantInfo = constantPool.getConstantInfo(constantValueIndex);
        return constantInfo.printValue();
    }

    @Override
    public void readInfo(ClassReader reader) {
        // 先请爸爸把 name_index 和 length(常量值属性的 length 固定为2) 读了
        super.readInfo(reader);
        // 再读自己的 value_index。
        this.constantValueIndex = reader.readU2ToInt();
    }

    public ConstantInfo getConstantValue() {
        return this.constantPool.getConstantInfo(constantValueIndex);
    }
}
