package cc.tianbin.demo.jvm.instructions;

import cc.tianbin.demo.jvm.instructions.constants.consts.ACONST_NULL;
import cc.tianbin.demo.jvm.instructions.constants.nop.NOP;
import cc.tianbin.demo.jvm.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by nibnait on 2022/12/06
 */
@Slf4j
public class InstructionsTest {

    @Test
    public void test() {
        Instruction instruction = InstructionFactory.getByOpcode(0x00);
        Assert.assertEquals(NOP.class, instruction.getClass());

        instruction = InstructionFactory.getByOpInstruction("nop");
        Assert.assertEquals(NOP.class, instruction.getClass());

        instruction = InstructionFactory.getByOpcode(0x01);
        Assert.assertEquals(ACONST_NULL.class, instruction.getClass());
    }

    @Test
    public void printAllOpCode() {
        Map<Integer, Instruction> codeMap = InstructionFactory.codeMap();
        int code = 0x00;
        for (Integer curOpCode : codeMap.keySet().stream().sorted().collect(Collectors.toList())) {
            if (code >= 0xb2 && code <= 0xc3) {
                continue;
            }
            String expectOpCode = NumberUtil.int2HexString(code);
            String expectInstruction = InstructionMap.getInstruction(expectOpCode);

            log.info("expect instruction: {}, {}", expectOpCode, expectInstruction);
            Assert.assertEquals(expectOpCode, NumberUtil.int2HexString(curOpCode));
            Assert.assertEquals(expectInstruction, codeMap.get(curOpCode).getClass().getSimpleName().toLowerCase());

            code++;
        }

    }

}
