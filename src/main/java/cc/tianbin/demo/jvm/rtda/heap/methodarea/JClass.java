package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.classfile.ClassFile;
import cc.tianbin.demo.jvm.common.AccessFlag;
import cc.tianbin.demo.jvm.rtda.heap.classloader.JClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.constantpool.RuntimeConstantPool;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 方法区中的 类信息
 * Created by nibnait on 2022/12/08
 */
public class JClass {

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
    private JClassLoader loader;
    @Setter
    @Getter
    private JClass superClass;
    @Setter
    @Getter
    private JClass[] interfaces;
    @Setter
    @Getter
    private int instanceSlotCount;
    @Getter
    private int staticSlotCount;
    @Getter
    private Slots staticVars;
    @Getter
    private boolean initStarted;

    public JObject jClass;

    public JClass(ClassFile classFile) {
        this.accessFlag = classFile.getAccessFlag();
        this.name = classFile.getClassName();
        this.superClassName = classFile.getSuperClassName();
        this.interfaceNames = classFile.getInterfaceNames();
        this.runtimeConstantPool = new RuntimeConstantPool(this, classFile.getConstantPool());
        this.fields = Field.newFields(this, classFile.getFields());
        this.methods = Method.newMethods(this, classFile.getMethods());
    }

    // load Array Class
    public JClass(int accessFlags, String name, JClassLoader loader, boolean initStarted, JClass superClass, JClass[] interfaces) {
        this.accessFlag = new AccessFlag(accessFlags, AccessFlag.FlagType.CLASS);
        this.name = name;
        this.loader = loader;
        this.initStarted = initStarted;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    public JClass(int accessFlags, String name, JClassLoader loader, boolean initStarted) {
        this.accessFlag = new AccessFlag(accessFlags, AccessFlag.FlagType.CLASS);
        this.name = name;
        this.loader = loader;
        this.initStarted = initStarted;
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

    public JObject newObject() {
        return JObject.newObject(this);
    }

    public JObject newArray(int count) {
        if (!this.isArray()) {
            throw new RuntimeException("Not array class " + this.name);
        }
        switch (this.getName()) {
            case "[Z":
            case "[B":
                return JObject.newObject(this, new byte[count]);
            case "[C":
                return JObject.newObject(this, new char[count]);
            case "[S":
                return JObject.newObject(this, new short[count]);
            case "[I":
                return JObject.newObject(this, new int[count]);
            case "[J":
                return JObject.newObject(this, new long[count]);
            case "[F":
                return JObject.newObject(this, new float[count]);
            case "[D":
                return JObject.newObject(this, new double[count]);
            default:
                return JObject.newObject(this, new Object[count]);
        }
    }

    public void startInit() {
        this.initStarted = true;
    }

    public JClass arrayClass() {
        String arrayClassName = ClassNameHelper.getArrayClassName(this.name);
        return this.loader.loadClass(arrayClassName);
    }

    public JClass componentClass() {
        String componentClassName = ClassNameHelper.getComponentClassName(this.name);
        return this.loader.loadClass(componentClassName);
    }

    public String javaName() {
        return this.name.substring(0, 1) + this.name.substring(1).replace("/", ".");
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

    /**
     * 是基本数据类型
     */
    public boolean IsPrimitive() {
        return FieldDescriptor.PRIMITIVE_TYPE.get(this.name) != null;
    }

    public JObject getRefVar(String fieldName, String fieldDescriptor) {
        Field field = this.getField(fieldName, fieldDescriptor, true);
        return this.staticVars.getRef(field.getSlotId());
    }

    public void setRefVar(String fieldName, String fieldDescriptor, JObject ref) {
        Field field = this.getField(fieldName, fieldDescriptor, true);
        this.staticVars.setRef(field.getSlotId(), ref);
    }

    //---------------- getXXXMethod ----------------
    public Method getMainMethod() {
        return this.getStaticMethod("main", "([Ljava/lang/String;)V", true);
    }

    public Method getClinitMethod() {
        return this.getStaticMethod("<clinit>", "()V", true);
    }

    public Method getInstanceMethod(String name, String descriptor) {
        return this.getStaticMethod(name, descriptor, false);
    }

    private Method getStaticMethod(String name, String descriptor, boolean isStatic) {
        for (Method method : this.methods) {
            if (method.getAccessFlag().isStatic() == isStatic
                    && method.getName().equals(name)
                    && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    //---------------- getXXXField ----------------
    public Field getField(String name, String descriptor, boolean isStatic) {
        for (JClass c = this; c != null; c = c.superClass) {
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
    public boolean isAccessibleTo(JClass otherClass) {
        return this.getAccessFlag().isPublic()
                || Objects.equals(this.getPackageName(), otherClass.getPackageName());
    }

    /**
     * 当前类，是否是 other 或 它的子类
     */
    public boolean isAssignableFrom(JClass other) {
        JClass s = this;
        JClass t = other;

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
                JClass sc = s.componentClass();
                JClass tc = t.componentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
        }
    }

    public boolean isSubClassOf(JClass other) {
        JClass c = this.superClass;
        while (c != null) {
            if (c == other) {
                return true;
            }
            c = c.getSuperClass();
        }
        return false;
    }

    public boolean isImplements(JClass other) {
        JClass c = this;
        while (c != null) {
            for (JClass clazz : c.getInterfaces()) {
                if (clazz == other || clazz.isSubInterfaceOf(other)) {
                    return true;
                }
            }
            c = c.getSuperClass();
        }
        return false;
    }

    private boolean isSubInterfaceOf(JClass iface) {
        for (JClass superInterface : this.interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

}
