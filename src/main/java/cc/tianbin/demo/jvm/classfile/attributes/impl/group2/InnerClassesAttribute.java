package cc.tianbin.demo.jvm.classfile.attributes.impl.group2;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class InnerClassesAttribute extends AttributeInfoRefBase implements AttributeInfo {

    public InnerClassesAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 number_of_classes;
     * {   
     * u2 inner_class_info_index;
     * u2 outer_class_info_index;
     * u2 inner_name_index;
     * u2 inner_class_access_flags;
     * } classes[number_of_classes];
     */
    private InnerClassInfo[] classes;

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        int numberOfClasses = reader.readU2ToInt();
        this.classes = new InnerClassInfo[numberOfClasses];
        for (int i = 0; i < numberOfClasses; i++) {
            InnerClassInfo innerClassInfo = new InnerClassInfo();
            innerClassInfo.readInfo(reader);
            classes[i] = innerClassInfo;
        }
    }

    static class InnerClassInfo {

        private int innerClassInfoIndex;
        private int outerClassInfoIndex;
        private int innerNameIndex;
        private int innerClassAccessFlags;

        public void readInfo(ClassReader reader) {
            this.innerClassInfoIndex = reader.readU2ToInt();
            this.outerClassInfoIndex = reader.readU2ToInt();
            this.innerNameIndex = reader.readU2ToInt();
            this.innerClassAccessFlags = reader.readU2ToInt();
        }
    }


}
