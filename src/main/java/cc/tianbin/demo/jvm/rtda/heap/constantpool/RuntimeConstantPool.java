package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.literal.ConstantDoubleInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.literal.ConstantFloatInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.literal.ConstantIntegerInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.literal.ConstantLongInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantFieldRefInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantInterfaceMethodRefInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantMethodRefInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantClassInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantStringInfo;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Class;

/**
 * Created by nibnait on 2022/12/08
 */
public class RuntimeConstantPool {

    private Class clazz;
    private Object[] constants;
    
    public RuntimeConstantPool(Class clazz, ConstantPool constantPool) {
        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        int constantPoolCount = constantInfos.length;
        this.clazz = clazz;
        this.constants = new Object[constantPoolCount];

        for (int i = 1; i < constantPoolCount; i++) {
            ConstantInfo constantInfo = constantInfos[i];
            switch (constantInfo.tag()) {
                case CONSTANT_TAG_INTEGER:
                    ConstantIntegerInfo integerInfo = (ConstantIntegerInfo) constantInfo;
                    this.constants[i] = integerInfo.printValue();
                    break;
                case CONSTANT_TAG_FLOAT:
                    ConstantFloatInfo floatInfo = (ConstantFloatInfo) constantInfo;
                    this.constants[i] = floatInfo.printValue();
                    break;
                case CONSTANT_TAG_LONG:
                    ConstantLongInfo longInfo = (ConstantLongInfo) constantInfo;
                    this.constants[i] = longInfo.printValue();
                    break;
                case CONSTANT_TAG_DOUBLE:
                    ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo) constantInfo;
                    this.constants[i] = doubleInfo.printValue();
                    break;
                case CONSTANT_TAG_STRING:
                    ConstantStringInfo stringInfo = (ConstantStringInfo) constantInfo;
                    this.constants[i] = stringInfo.string();
                    break;
                case CONSTANT_TAG_CLASS:
                    ConstantClassInfo classInfo = (ConstantClassInfo) constantInfo;
                    this.constants[i] = ClassRef.newClassRef(this, classInfo);
                    break;
                case CONST_TAG_FIELD_REF:
                    ConstantFieldRefInfo fieldInfo = (ConstantFieldRefInfo) constantInfo;
                    this.constants[i] = FieldRef.newFieldRef(this, fieldInfo);
                    break;
                case CONSTANT_TAG_METHOD_REF:
                    ConstantMethodRefInfo methodRefInfo = (ConstantMethodRefInfo) constantInfo;
                    this.constants[i] = MethodRef.newMethodRef(this, methodRefInfo);
                    break;
                case CONSTANT_TAG_INTERFACE_METHOD_REF:
                    ConstantInterfaceMethodRefInfo interfaceMethodRefInfo = (ConstantInterfaceMethodRefInfo) constantInfo;
                    this.constants[i] = InterfaceMethodRef.newInterfaceMethodRef(this, interfaceMethodRefInfo);
                    break;
                default:
                    break;
            }
        }

    }

    public Class getClazz() {
        return this.clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getConstants(int index) {
        if (index >= constants.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return constants[index];
    }
}
