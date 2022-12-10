package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantFieldRefInfo;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Field;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/12/08
 */
@Slf4j
public class FieldRef extends MemberRef {

    private Field field;

    public static FieldRef newFieldRef(RuntimeConstantPool runtimeConstantPool, ConstantFieldRefInfo refInfo) {
        FieldRef ref = new FieldRef();
        ref.runtimeConstantPool = runtimeConstantPool;
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public Field resolvedField() {
        if (null == field) {
            try {
                this.resolveFieldRef();
            } catch (NoSuchFieldException e) {
                log.error("FieldRef resolvedField error ", e);
            }
        }
        return this.field;
    }

    private void resolveFieldRef() throws NoSuchFieldException {
        Class d = this.runtimeConstantPool.getClazz();
        Class c = this.resolvedClass();

        Field field = this.lookupField(c, this.getName(), this.getDescriptor());
        if (null == field){
            throw new NoSuchFieldException();
        }

        if (!field.isAccessibleTo(d)){
            throw new IllegalAccessError();
        }

        this.field = field;
    }

    private Field lookupField(Class c, String name, String descriptor) {
        for (Field field : c.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }

        for (Class iface : c.getInterfaces()) {
            Field field = lookupField(iface, name, descriptor);
            if (null != field) return field;
        }

        if (c.getSuperClass() != null) {
            return lookupField(c.getSuperClass(), name, descriptor);
        }

        return null;
    }

}
