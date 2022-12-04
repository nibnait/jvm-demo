package cc.tianbin.demo.jvm.classfile.attributes;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.impl.UnparsedRefAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.BootstrapMethodsAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.ConstantValueAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.ExceptionsAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group2.EnclosingMethodAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group2.InnerClassesAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group2.SignatureAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group2.SyntheticAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group3.*;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public interface AttributeInfo {

    // 属性名
    String attrName();
    // 属性长度
    int attrLen();
    // 读取属性信息
    void readInfo(ClassReader reader);

    /**
     * 读取属性表
     */
    static AttributeInfo[] readAttributes(ClassReader reader, ConstantPool constantPool) {
        int attributesCount = reader.readU2ToInt();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];

        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(reader, constantPool);
        }

        return attributes;
    }

    /**
     * 读取单个属性
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u1 info[attribute_length];
     */
    static AttributeInfo readAttribute(ClassReader reader, ConstantPool constantPool) {
        // 读取属性名
        int attrNameIndex = reader.readU2ToInt();
        String attrName = constantPool.getUTF8(attrNameIndex);
        // 退回 当前属性的开头位置
        reader.back(2);

        // 再创建具体的属性实例
        AttributeInfo attributeInfo = newAttributeInfo(attrName, constantPool);
        // 读出属性信息
        attributeInfo.readInfo(reader);
        return attributeInfo;
    }

    /**
     * 23种预定义属性：
     * group1. 实现Java虚拟机所必须的（5种）
     *
     * group2. Java类库所必须的（12种）
     *
     * group3. 提供给工具使用的（6种）
     *
     */
    static AttributeInfo newAttributeInfo(String attrName, ConstantPool constantPool) {
        switch (attrName) {
            //------------ group1 ------------
            //field属性: 常量表达式的值  存的是常量池索引
            case "ConstantValue":
                return new ConstantValueAttribute(constantPool);
            //method属性: 方法体
            case "Code":
                return new CodeAttribute(constantPool);
            //method属性: 变长属性，记录方法抛出的异常表
            case "Exceptions":
                return new ExceptionsAttribute(constantPool);
//            case "StackMapTable": 
            case "BootstrapMethods":
                return new BootstrapMethodsAttribute(constantPool);

            //------------ group2 ------------
            case "InnerClasses":
                return new InnerClassesAttribute(constantPool);

            // Synthetic属性用来标记源文件中不存在、由编译器生成的类成员，
            // 引入Synthetic属性主要是为了支持嵌套类和嵌套接口
            case "Synthetic":
                return new SyntheticAttribute(constantPool);

            case "EnclosingMethod":
                return new EnclosingMethodAttribute(constantPool);
            case "Signature":
                return new SignatureAttribute(constantPool);
//            case "RuntimeVisibleAnnotations":
//            case "RuntimeInvisibleAnnotations":
//            case "RuntimeVisibleParameterAnnotations":
//            case "RuntimeInvisibleParameterAnnotations":
//            case "AnnotationDefault":
//            case "RuntimeVisibleTypeAnnotations":
//            case "RuntimeInvisibleTypeAnnotations":
//            case "MethodParameters":

            //------------ group3 ------------
            // 以下3是调试信息 不一定要 使用javac编译器编译Java程序时，默认会在class文件中生成 这些信息
            // class属性
            // 源文件名 如 XXX.java
            case "SourceFile":
                return new SourceFileAttribute(constantPool);
            // method属性的Code属性的属性: 方法行号
            case "LineNumberTable":
                return new LineNumberTableAttribute(constantPool);
            // method属性的Code属性的属性: 方法局部变量
            case "LocalVariableTable":
                return new LocalVariableTableAttribute(constantPool);

            // 废弃标记: Deprecated属性用于指出类、接口、字段或方法已经不建议使用，编 译器等工具可以根据Deprecated属性输出警告信息。
            case "Deprecated":
                return new DeprecatedAttribute(constantPool);
//            case "SourceDebugExtension":
            case "LocalVariableTypeTable":
                return new LocalVariableTypeTableAttribute(constantPool);
            //不支持
            default:
                return new UnparsedRefAttribute(constantPool);
        }
    }

}
