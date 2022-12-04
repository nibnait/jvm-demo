package cc.tianbin.demo.jvm.classfile.constantpool.impl.invokdynamic;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

import java.util.Map;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantInvokeDynamicInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 bootstrap_method_attr_index;
     * u2 name_and_type_index;
     */
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    public ConstantInvokeDynamicInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    // 这个暂时还不知道是啥。先这么写下
    public int bootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public Map<String, String> getNameAndType() {
        return constantPool.getNameAndType(nameAndTypeIndex);
    }

    public String nameAndType() {
        return constantPool.nameAndType(nameAndTypeIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.bootstrapMethodAttrIndex = reader.readU2ToInt();
        this.nameAndTypeIndex = reader.readU2ToInt();
    }

}
