package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.common.AccessFlag;
import cc.tianbin.demo.jvm.rtda.heap.classloader.ClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 方法区中的 类信息
 * Created by nibnait on 2022/12/08
 */
public class Class {

    @Getter
    private AccessFlag accessFlag;
    @Getter
    private String name;
    @Getter
    private String superClassName;
    @Getter
    private String[] interfaceNames;
    @Getter
    private RuntimeConstantPool runtimeConstantPool;
    @Getter
    private Field[] fields;
    @Getter
    private Method[] methods;
    @Setter
    @Getter
    private ClassLoader loader;
    @Setter
    @Getter
    private Class superClass;
    @Setter
    @Getter
    private Class[] interfaces;
    @Setter
    @Getter
    private int instanceSlotCount;
    @Getter
    private int staticSlotCount;
    @Getter
    private Slots staticVars;
    @Getter
    private boolean initStarted;

    public Class(ClassFile classFile) {
        this.accessFlag = classFile.getAccessFlag();
        this.name = classFile.getClassName();
        this.superClassName = classFile.getSuperClassName();
        this.interfaceNames = classFile.getInterfaceNames();
        this.runtimeConstantPool = new RuntimeConstantPool(this, classFile.getConstantPool());
        this.fields = Field.newFields(this, classFile.getFields());
        this.methods = Method.newMethods(this, classFile.getMethods());
    }

    // load Array Class
    public Class(int accessFlags, String name, ClassLoader loader, boolean initStarted, Class superClass, Class[] interfaces) {
        this.accessFlag = new AccessFlag(accessFlags, AccessFlag.FlagType.CLASS);
        this.name = name;
        this.loader = loader;
        this.initStarted = initStarted;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
        this.staticVars = new Slots(staticSlotCount);
    }

    public String getPackageName() {
        int i = this.name.lastIndexOf("/");
        if (i >= 0) return this.name;
        return "";
    }

    public JVMMAObject newObject() {
        return JVMMAObject.newObject(this);
    }

    public JVMMAObject newArray(int count) {
        if (!this.isArray()) {
            throw new RuntimeException("Not array class " + this.name);
        }
        switch (this.getName()) {
            case "[Z":
            case "[B":
                return JVMMAObject.newObject(this, new byte[count]);
            case "[C":
                return JVMMAObject.newObject(this, new char[count]);
            case "[S":
                return JVMMAObject.newObject(this, new short[count]);
            case "[I":
                return JVMMAObject.newObject(this, new int[count]);
            case "[J":
                return JVMMAObject.newObject(this, new long[count]);
            case "[F":
                return JVMMAObject.newObject(this, new float[count]);
            case "[D":
                return JVMMAObject.newObject(this, new double[count]);
            default:
                return JVMMAObject.newObject(this, new Object[count]);
        }
    }

    public void startInit(){
        this.initStarted = true;
    }

    public Class arrayClass() {
        String arrayClassName = ClassNameHelper.getArrayClassName(this.name);
        return this.loader.loadClass(arrayClassName);
    }

    public Class componentClass() {
        String componentClassName = ClassNameHelper.getComponentClassName(this.name);
        return this.loader.loadClass(componentClassName);
    }

    //---------------- descriptor ----------------
    public boolean isArray() {
        return this.name.startsWith(FieldDescriptor.A_REF.getCode());
    }

    public boolean isJlObject() {
        return this.name.equals("java/lang/Object");
    }

    public boolean isJlCloneable() {
        return this.name.equals("java/lang/Cloneable");
    }

    public boolean isJioSerializable() {
        return this.name.endsWith("java/io/Serializable");
    }

    //---------------- getXXXMethod ----------------
    public Method getMainMethod() {
        return this.getMethod("main", "([Ljava/lang/String;)V");
    }

    public Method getClinitMethod(){
        return this.getMethod("<clinit>","()V");
    }

    private Method getMethod(String name, String descriptor) {
        for (Method method : this.methods) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    //---------------- getXXXField ----------------
    public Field getField(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.superClass) {
            for (Field field : c.fields) {
                if (field.getAccessFlag().isStatic() == isStatic
                        && field.getName().equals(name)
                        && field.getDescriptor().equals(descriptor)) {
                    return field;
                }
            }
        }
        return null;
    }

    //------------------------------------------

    /**
     * 对 otherClass 是否是可达的
     */
    public boolean isAccessibleTo(Class otherClass) {
        return this.getAccessFlag().isPublic()
                || Objects.equals(this.getPackageName(), otherClass.getPackageName());
    }

    /**
     * 当前类，是否是 other 或 它的子类
     */
    public boolean isAssignableFrom(Class other) {
        Class s = this;
        Class t = other;

        if (s == t) {
            return true;
        }

        if (!s.isArray()) {
            if (!s.getAccessFlag().isInterface()) {
                if (!t.getAccessFlag().isInterface()) {
                    return s.isSubClassOf(t);
                } else {
                    return isImplements(t);
                }
            } else {
                if (!t.getAccessFlag().isInterface()) {
                    return t.isJlObject();
                } else {
                    return t.isSubInterfaceOf(s);
                }
            }
        } else {
            if (!t.isArray()) {
                if (!t.getAccessFlag().isInterface()) {
                    return t.isJlObject();
                } else {
                    return t.isJlCloneable() || t.isJioSerializable();
                }
            } else {
                Class sc = s.componentClass();
                Class tc = t.componentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
        }
    }

    public boolean isSubClassOf(Class other) {
        Class c = this.superClass;
        while (c != null) {
            if (c == other) {
                return true;
            }
            c = c.getSuperClass();
        }
        return false;
    }

    public boolean isImplements(Class other) {
        Class c = this;
        while (c != null) {
            for (Class clazz : c.getInterfaces()) {
                if (clazz == other || clazz.isSubInterfaceOf(other)) {
                    return true;
                }
            }
            c = c.getSuperClass();
        }
        return false;
    }

    private boolean isSubInterfaceOf(Class iface) {
        for (Class superInterface : this.interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

}
