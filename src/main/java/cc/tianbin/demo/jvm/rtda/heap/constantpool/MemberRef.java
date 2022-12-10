package cc.tianbin.demo.jvm.rtda.heap.constantpool;

import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantMemberRefInfo;
import lombok.Getter;

import java.util.Map;

/**
 * Created by nibnait on 2022/12/08
 */
public class MemberRef extends SymRef {

    @Getter
    private String name;
    @Getter
    private String descriptor;

    public void copyMemberRefInfo(ConstantMemberRefInfo refInfo) {
        this.className = refInfo.getClassName();
        Map<String, String> map = refInfo.getNameAndType();
        this.name = map.get(ConstantPool.NAME);
        this.descriptor = map.get(ConstantPool.TYPE);
    }

}
