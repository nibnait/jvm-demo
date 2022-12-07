package cc.tianbin.demo.jvm.instructions;

import cc.tianbin.demo.jvm.exception.InstructionException;
import cc.tianbin.demo.jvm.utils.NumberUtil;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by nibnait on 2022/12/06
 */
@Slf4j
public class InstructionFactory {

    private static final Map<Integer, Instruction> codeMap = new HashMap<>();
    private static final Map<String, Instruction> nameMap = new HashMap<>();

    static {
        Set<Class<?>> allImplClasses = ClassUtil.scanPackageBySuper(Instruction.class.getPackage().getName(), Instruction.class);

        for (Class<?> implClass : allImplClasses) {
            if (Modifier.isAbstract(implClass.getModifiers())) {
                continue;
            }
            try {
                Instruction instruction = (Instruction) implClass.newInstance();
                codeMap.put(instruction.opcode(), instruction);
                nameMap.put(instruction.operate(), instruction);
            } catch (Exception e) {
                log.error("InstructionFactory init error ", e);
            }
        }
    }

    public static Instruction getByOpcode(int opcode) {
        Instruction instruction = codeMap.get(opcode);
        if (instruction == null) {
            throw new InstructionException("invalid operation code {}", NumberUtil.int2HexString(opcode));
        }
        return instruction;
    }

    public static Instruction getByOpInstruction(String name) {
        Instruction instruction = nameMap.get(name);
        if (instruction == null) {
            throw new InstructionException("invalid operation instruction {}", name);
        }
        return instruction;
    }

    public static Map<Integer, Instruction> codeMap() {
        return codeMap;
    }

    public static Map<String, Instruction> nameMap() {
        return nameMap;
    }
}
