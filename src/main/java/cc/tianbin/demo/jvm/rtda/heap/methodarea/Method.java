package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group3.LineNumberTableAttribute;
import lombok.Getter;

import java.util.List;

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
    private ExceptionTable exceptionTable;
    private LineNumberTableAttribute lineNumberTable;

    public static Method[] newMethods(JClass clazz, MemberInfo[] classFileMethods) {
        Method[] methods = new Method[classFileMethods.length];
        for (int i = 0; i < classFileMethods.length; i++) {
            methods[i] = newMethod(clazz, classFileMethods[i]);
        }
        return methods;
    }

    private static Method newMethod(JClass clazz, MemberInfo classFileMethod) {
        Method method = new Method();
        method.setClazz(clazz);
        method.copyMemberInfo(classFileMethod);
        method.copyAttributes(classFileMethod);
        MethodDescriptor methodDescriptor = MethodDescriptorParser.parseMethodDescriptorParser(method.getDescriptor());
        method.calcArgSlotCount(methodDescriptor.getParameterTypes());
        if (method.getAccessFlag().isNative()) {
            method.injectCodeAttribute(methodDescriptor.getReturnType());
        }
        return method;
    }

    /**
     * 本地方法在文件中没有Code属性
     * 所以需要给 maxStack, maxLocals 字段复制
     */
    private void injectCodeAttribute(String returnType) {
        this.maxStack = 4; //todo
        this.maxLocals = this.argSlotCount;

        /**
         * 本地方法的字节码
         * 第1条指令都是 0xfe impdep1
         * 第2条指令，根据函数的返回值选择相应的返回指令
         */
        switch (returnType.getBytes()[0]) {
            case 'V': // return
                this.bytecode = new byte[]{(byte) 0xfe, (byte) 0xb1};
                break;
            case 'L':
            case '[': // areturn
                this.bytecode = new byte[]{(byte) 0xfe, (byte) 0xb0};
                break;
            case 'D': // dreturn
                this.bytecode = new byte[]{(byte) 0xfe, (byte) 0xaf};
                break;
            case 'F': // freturn
                this.bytecode = new byte[]{(byte) 0xfe, (byte) 0xae};
                break;
            case 'J': // lreturn
                this.bytecode = new byte[]{(byte) 0xfe, (byte) 0xad};
                break;
            default: // ireturn
                this.bytecode = new byte[]{(byte) 0xfe, (byte) 0xac};
                break;
        }
    }

    private void copyAttributes(MemberInfo classFileMethod) {
        CodeAttribute codeAttribute = classFileMethod.getCodeAttribute();
        if (codeAttribute != null) {
            this.maxStack = codeAttribute.getMaxStack();
            this.maxLocals = codeAttribute.getMaxLocals();
            this.bytecode = codeAttribute.getBytecode();
            this.lineNumberTable = codeAttribute.lineNumberTableAttribute();
            this.exceptionTable = new ExceptionTable(codeAttribute.getExceptionTable(), this.getClazz().getRuntimeConstantPool());
        }
    }

    /**
     * 计算当前方法的入参，在局部变量表中占了多少个槽位
     */
    private void calcArgSlotCount(List<String> parameterTypes) {
        for (String paramType : parameterTypes) {
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

    /**
     * 搜索异常处理表
     */
    public int findExceptionHandler(JClass exClass, int pc) {
        ExceptionTable.ExceptionHandler handler = this.exceptionTable.findExceptionHandler(exClass, pc);
        if (handler != null) {
            return handler.handlerPC;
        }
        return -1;
    }

    public int getLineNumber(int pc) {
        if (this.getAccessFlag().isNative()) {
            return -2;
        }
        if (this.lineNumberTable == null) {
            return -1;
        }
        return this.lineNumberTable.getLineNumber(pc);
    }
}
