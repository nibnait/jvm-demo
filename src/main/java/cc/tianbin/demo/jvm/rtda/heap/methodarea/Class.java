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

    public void startInit(){
        this.initStarted = true;
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
        if (this == other) {
            return true;
        } else if (!other.getAccessFlag().isInterface()) {
            return this.isSubClassOf(other);
        } else {
            return this.isImplements(other);
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
