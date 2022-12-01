package cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

import java.util.Map;

/**
 * Created by nibnait on 2022/11/30
 */
public abstract class ConstantMemberRefInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 class_index;
     * u2 name_and_type_index;
     */
    // 当前类 在常量池中的索引
    private int classIndex;
    // 当前字段/方法 在常量池中的所有
    private int nameAndTypeIndex;

    public ConstantMemberRefInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * 类名
     */
    public String className() {
        return constantPool.getClassName(this.classIndex);
    }

    /**
     * 字段名, 字段描述符 / 方法名, 方法描述符
     */
    public Map<String, String> getNameAndType() {
        return constantPool.getNameAndType(this.nameAndTypeIndex);
    }

    public String nameAndType() {
        return constantPool.nameAndType(this.nameAndTypeIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.classIndex = reader.nextU2ToInt();
        this.nameAndTypeIndex = reader.nextU2ToInt();
    }
}
