package cc.tianbin.demo.jvm.classfile.attributes.impl.group1;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

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
    private int constantValueIndex;

    public ConstantValueAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    public String constantValue(){
        ConstantInfo constantInfo = constantPool.getConstantInfo(constantValueIndex);
        return constantInfo.value();
    }

    @Override
    public void readInfo(ClassReader reader) {
        // 先请爸爸把 name_index 和 length 读了
        super.readInfo(reader);
        // 再读自己的 value_index
        this.constantValueIndex = reader.nextU2ToInt();
    }
}
