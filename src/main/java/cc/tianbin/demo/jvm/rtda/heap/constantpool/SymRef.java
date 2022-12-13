package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.exception.JvmException;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;

/**
 * symbolic reference
 * Created by nibnait on 2022/12/08
 */
public class SymRef {

    protected RuntimeConstantPool runtimeConstantPool;
    protected String className;
    protected JClass clazz;

    public JClass resolvedClass() {
        if (this.clazz == null) {
            this.resolvedClassRef();
        }
        return this.clazz;
    }

    private void resolvedClassRef() {
        JClass d = this.runtimeConstantPool.getClazz();
        JClass c = d.getLoader().loadClass(this.className);
        if (!c.isAccessibleTo(d)) {
            throw new JvmException("IllegalAccessException");
        }
        this.clazz = c;
    }

}
