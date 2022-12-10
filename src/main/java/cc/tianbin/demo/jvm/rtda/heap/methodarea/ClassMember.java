package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.classfile.MemberInfo;
import cc.tianbin.demo.jvm.common.AccessFlag;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by nibnait on 2022/12/08
 */
public class ClassMember {

    @Getter
    private AccessFlag accessFlag;
    @Getter
    private String name;
    @Getter
    private String descriptor;
    // class 结构体指针
    @Setter
    @Getter
    private Class clazz;

    /**
     * 从 class 文件中复制数据
     */
    public void copyMemberInfo(MemberInfo memberInfo) {
        this.accessFlag = memberInfo.getAccessFlag();
        this.name = memberInfo.getName();
        this.descriptor = memberInfo.getDescriptor();
    }

    public boolean isLongOrDouble() {
        return this.descriptor.equals("J") || this.descriptor.equals("D");
    }

    /**
     * 对 otherClass 是否是可达的
     */
    public boolean isAccessibleTo(Class otherClass) {
        if (this.accessFlag.isPublic()) {
            return true;
        }
        Class curClass = this.clazz;
        if (this.accessFlag.isProtected()) {
            return otherClass == curClass || curClass.getPackageName().equals(otherClass.getPackageName());
        }
        if (!this.accessFlag.isPrivate()) {
            return curClass.getPackageName().equals(otherClass.getPackageName());
        }
        return otherClass == curClass;
    }

}
