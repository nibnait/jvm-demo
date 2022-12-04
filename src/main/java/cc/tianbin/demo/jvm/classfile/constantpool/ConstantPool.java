package cc.tianbin.demo.jvm.classfile.constantpool;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantClassInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantNameAndTypeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.literal.ConstantUtf8Info;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2022/11/29
 */
@Getter
public class ConstantPool {

    // 常量池大小（本身占1个位置）。所以实际大小为 constantPoolSize - 1)

    private final int constantPoolSize;
    private final ConstantInfo[] constantInfos;

    public ConstantPool(ClassReader reader) {
        constantPoolSize = reader.readU2ToInt();
        this.constantInfos = new ConstantInfo[constantPoolSize];

        // 第0个位置，用来标记常量池的大小了
        int i = 1;
        while (i < constantPoolSize) {
            // 读取常量信息
            constantInfos[i] = ConstantInfo.readConstantInfo(reader, this);

            // double, long 占2个位置
            // 所以 constantInfos 有些索引位置 有可能是无效的
            if (ConstantTag.OCCUPY_2INDEX_TAG.contains(constantInfos[i].tag())) {
                i += 2;
            } else {
                i += 1;
            }
        }
    }

    // 按索引找常量
    public ConstantInfo getConstantInfo(int index) {
        ConstantInfo constantInfo = this.constantInfos[index];
        if (constantInfo == null) {
            throw new IllegalArgumentException("Invalid constant pool index!");
        }
        return constantInfo;
    }

    // 从常量池中查找 字段或方法的名字 和 描述符
    public Map<String, String> getNameAndType(int index) {
        ConstantNameAndTypeInfo constantInfo = (ConstantNameAndTypeInfo) this.constantInfos[index];
        if (constantInfo == null) {
            throw new IllegalArgumentException("Invalid constant pool index!");
        }
        Map<String, String> map = new HashMap<>();
        map.put("name", constantInfo.name());
        map.put("_type", constantInfo.descriptor());
        return map;
    }

    public String nameAndType(int index) {
        ConstantNameAndTypeInfo constantInfo = (ConstantNameAndTypeInfo) this.constantInfos[index];
        if (constantInfo == null) {
            throw new IllegalArgumentException("Invalid constant pool index!");
        }
        return constantInfo.name() + "&" + constantInfo.descriptor();
    }

    // 从常量池查找 类名
    public String getClassName(int index) {
        ConstantClassInfo classInfo = (ConstantClassInfo) this.constantInfos[index];
        if (classInfo == null) {
            throw new IllegalArgumentException("Invalid constant pool index!");
        }
        return this.getUTF8(classInfo.nameIndex());
    }

    // 从常量池查找 UTF-8 字符串
    public String getUTF8(int index) {
        ConstantUtf8Info utf8Info = (ConstantUtf8Info) this.constantInfos[index];
        return utf8Info == null ? "" : utf8Info.value();
    }

}
