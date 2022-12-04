package cc.tianbin.demo.jvm.classfile;

import lombok.Data;

import java.util.List;

/**
 * Created by nibnait on 2022/12/04
 */
@Data
public class AccessFlag {

    private String code;
    private List<AccessFlagEnum> flags;

}
