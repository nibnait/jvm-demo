package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nibnait on 2022/12/12
 */
public class MethodDescriptor {

    @Getter
    private List<String> parameterTypes = new ArrayList<>();
    @Setter
    @Getter
    private String returnType;

    public void addParameterType(String type) {
        this.parameterTypes.add(type);
    }

}
