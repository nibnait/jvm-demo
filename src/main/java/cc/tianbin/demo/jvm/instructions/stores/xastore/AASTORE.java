package cc.tianbin.demo.jvm.instructions.stores.xastore;

import cc.tianbin.demo.jvm.instructions.base.NoOperandsInstruction;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;

/**
 * Created by nibnait on 2022/12/07
 */
public class AASTORE extends NoOperandsInstruction {
    @Override
    public int opcode() {
        return 0x53;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        JObject ref = stack.popRef();
        int index = stack.popInt();
        JObject arrRef = stack.popRef();
        checkNotNull(arrRef);

        JObject[] refs = arrRef.refs();
        checkIndex(refs.length, index);

        refs[index] = ref;
    }
}
