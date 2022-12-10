package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.rtda.heap.classloader.LinkingHelper;
import lombok.Getter;

/**
 * Created by nibnait on 2022/12/08
 */
public class MethodAreaObject {

    @Getter
    private Class clazz;
    @Getter
    private Slots fields;

    /**
     * 新创建对象的实例变量都是直接赋好【初始值】，不需要做额外的工作
     * @see LinkingHelper#initStaticVar
     */
    public static MethodAreaObject newObject(Class clazz) {
        MethodAreaObject object = new MethodAreaObject();
        object.clazz = clazz;
        object.fields = new Slots(clazz.getInstanceSlotCount());
        return object;
    }

    public boolean isInstanceOf(Class other) {
        return this.clazz.isAssignableFrom(other);
    }

}
