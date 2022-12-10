package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantClassInfo;

/**
 * Created by nibnait on 2022/12/08
 */
public class ClassRef extends SymRef {

    public static ClassRef newClassRef(RuntimeConstantPool runtimeConstantPool, ConstantClassInfo classInfo) {
        ClassRef classRef = new ClassRef();
        classRef.runtimeConstantPool = runtimeConstantPool;
        classRef.className = classInfo.getName();
        return classRef;
    }
}
