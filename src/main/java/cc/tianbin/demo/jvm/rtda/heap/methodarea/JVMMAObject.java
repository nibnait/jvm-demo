package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.rtda.heap.classloader.LinkingHelper;
import io.github.nibnait.common.utils.DataUtils;
import lombok.Getter;

/**
 * Java Virtual Machine Method Area Object
 * Created by nibnait on 2022/12/08
 */
public class JVMMAObject {

    @Getter
    private Class clazz;
    @Getter
    private Slots fields;

    /**
     * 新创建对象的实例变量都是直接赋好【初始值】，不需要做额外的工作
     * @see LinkingHelper#initStaticVar
     */
    public static JVMMAObject newObject(Class clazz) {
        JVMMAObject object = new JVMMAObject();
        object.clazz = clazz;
        object.fields = new Slots(clazz.getInstanceSlotCount());
        return object;
    }

    public boolean isInstanceOf(Class other) {
        return this.clazz.isAssignableFrom(other);
    }

    @Override
    public String toString() {
        return DataUtils.format(
                "{} {}", clazz.getName(), fields.toString()
        );
    }
}
