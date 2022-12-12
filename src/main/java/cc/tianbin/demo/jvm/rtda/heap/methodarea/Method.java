package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import lombok.Getter;

/**
 * Created by nibnait on 2022/12/08
 */
public class Method extends ClassMember {

    @Getter
    private int maxStack;
    @Getter
    private int maxLocals;
    @Getter
    private byte[] bytecode;
    @Getter
    private int argSlotCount;

    public static Method[] newMethods(Class clazz, MemberInfo[] classFileMethods) {
        Method[] methods = new Method[classFileMethods.length];
        for (int i = 0; i < classFileMethods.length; i++) {
            Method method = new Method();
            method.setClazz(clazz);
            method.copyMemberInfo(classFileMethods[i]);
            method.copyAttributes(classFileMethods[i]);
            method.calcArgSlotCount();
            methods[i] = method;
        }
        return methods;
    }

    private void copyAttributes(MemberInfo classFileMethod) {
        CodeAttribute codeAttribute = classFileMethod.getCodeAttribute();
        if (codeAttribute != null) {
            this.maxStack = codeAttribute.getMaxStack();
            this.maxLocals = codeAttribute.getMaxLocals();
            this.bytecode = codeAttribute.getBytecode();
        }
    }

    /**
     * 计算当前方法的入参，在局部变量表中占了多少个槽位
     */
    private void calcArgSlotCount() {
        MethodDescriptor parsedDescriptor = MethodDescriptorParser.parseMethodDescriptorParser(this.getDescriptor());
        for (String paramType : parsedDescriptor.getParameterTypes()) {
            this.argSlotCount++;
            if (FieldDescriptor.isLongOrDouble(paramType)) {
                // long 和 double 占2个槽位
                this.argSlotCount++;
            }
        }

        // 实例方法，参数列表中的第一个参数用于是 this
        if (!this.getAccessFlag().isStatic()) {
            this.argSlotCount++;
        }
    }
}
