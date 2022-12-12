package cc.tianbin.demo.jvm.classfile;

import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.common.AccessFlag;
import lombok.Getter;

/**
 * Created by nibnait on 2022/11/29
 */
public class ClassFile {

    // 版本号
    @Getter
    private int minorVersion;
    @Getter
    private int majorVersion;
    // 常量池
    @Getter
    private ConstantPool constantPool;
    // 类访问标志：指出是 class文件，还是 interface。是 public 还是 private
    @Getter
    private AccessFlag accessFlag;
    // 当前类 索引
    private int thisClassIndex;
    // 超类 索引
    private int supperClassIndex;
    // 接口索引表（该类实现的所有接口的名字）
    private int[] interfaces;
    // 字段
    @Getter
    private MemberInfo[] fields;
    // 方法表
    @Getter
    private MemberInfo[] methods;
    // 属性表
    @Getter
    private AttributeInfo[] attributes;

    public ClassFile(byte[] classData) {
        ClassReader reader = new ClassReader(classData);
        this.readAndCheckMagic(reader);
        this.readAndCheckVersion(reader);
        this.constantPool = this.readConstantPool(reader);
        this.accessFlag = readAccessFlag(reader, AccessFlag.FlagType.CLASS);
        this.thisClassIndex = reader.readU2ToInt();
        this.supperClassIndex = reader.readU2ToInt();
        this.interfaces = reader.readUint16s();
        this.fields = MemberInfo.readMembers(constantPool, reader, AccessFlag.FlagType.FIELD);
        this.methods = MemberInfo.readMembers(constantPool, reader, AccessFlag.FlagType.METHOD);
        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    // 魔数
    // class 十六进制 字节流文件，必须以 "cafe babe" 开头。
    private void readAndCheckMagic(ClassReader reader) {
        String magic = reader.readU4ToHexString();
        if (!"CAFEBABE".equalsIgnoreCase(magic)) {
            throw new ClassFormatError("magic!");
        }
    }

    // 校验版本号。某些虚拟机 只能处理特定的版本的 class 文件
    private void readAndCheckVersion(ClassReader reader) {
        // 次版本
        this.minorVersion = reader.readU2ToInt();
        // 主版本
        this.majorVersion = reader.readU2ToInt();
        switch (this.majorVersion) {
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                // Java 1.2 之后的版本，次版本号 都是0
                if (this.minorVersion == 0)
                    return;
        }
        throw new UnsupportedClassVersionError();
    }

    /**
     * access flag
     */
    public static AccessFlag readAccessFlag(ClassReader reader, AccessFlag.FlagType flagType) {
        int code = reader.readU2ToInt();
        return new AccessFlag(code, flagType);
    }

    private ConstantPool readConstantPool(ClassReader reader) {
        return new ConstantPool(reader);
    }

    public String getClassName() {
        return this.constantPool.getClassName(this.thisClassIndex);
    }

    public String getSuperClassName() {
        if (this.supperClassIndex <= 0) return "";
        return this.constantPool.getClassName(this.supperClassIndex);
    }

    public String[] getInterfaceNames() {
        String[] interfaceNames = new String[this.interfaces.length];
        for (int i = 0; i < this.interfaces.length; i++) {
            interfaceNames[i] = this.constantPool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }

}