package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.ConstantValueAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by nibnait on 2022/12/08
 */
public class Field extends ClassMember {

    @Getter
    private int constantValueIndex;
    @Setter
    @Getter
    private int slotId;

    public static Field[] newFields(JClass clazz, MemberInfo[] classFileFields) {
        Field[] fields = new Field[classFileFields.length];
        for (int i = 0; i < classFileFields.length; i++) {
            Field field = new Field();
            field.setClazz(clazz);
            field.copyMemberInfo(classFileFields[i]);
            field.copyAttributes(classFileFields[i]);
            fields[i] = field;
        }
        return fields;
    }

    public void copyAttributes(MemberInfo classFileField) {
        ConstantValueAttribute constantAttribute = classFileField.getConstantAttribute();
        if (constantAttribute != null) {
            this.constantValueIndex = constantAttribute.getConstantValueIndex();
        }
    }
}
