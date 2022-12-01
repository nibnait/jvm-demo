package cc.tianbin.demo.jvm.classfile.attributes.impl.group1;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class BootstrapMethodsAttribute extends AttributeInfoRefBase implements AttributeInfo {
    public BootstrapMethodsAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 num_bootstrap_methods;
     * {
     * u2 bootstrap_method_ref;
     * u2 num_bootstrap_arguments;
     * u2 bootstrap_arguments[num_bootstrap_arguments];
     * } bootstrap_methods[num_bootstrap_methods];
     */
    BootstrapMethod[] bootstrapMethods;

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        int bootstrapMethodNum = reader.nextU2ToInt();
        bootstrapMethods = new BootstrapMethod[bootstrapMethodNum];
        for (int i = 0; i < bootstrapMethodNum; i++) {
            BootstrapMethod bootstrapMethod = new BootstrapMethod();
            bootstrapMethod.readInfo(reader);
            bootstrapMethods[i] = bootstrapMethod;
        }
    }

    static class BootstrapMethod {
        int bootstrapMethodRef;
        int[] bootstrapArguments;

        public void readInfo(ClassReader reader) {
            this.bootstrapMethodRef = reader.nextU2ToInt();
            this.bootstrapArguments = reader.nextUint16s();
        }
    }

}
