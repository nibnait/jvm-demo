package cc.tianbin.demo.jvm.instructions.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nibnait on 2022/12/07
 */
public class InstructionMap {

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(0x00, "nop");
        map.put(0x01, "aconst_null");
        map.put(0x02, "iconst_m1");
        map.put(0x03, "iconst_0");
        map.put(0x04, "iconst_1");
        map.put(0x05, "iconst_2");
        map.put(0x06, "iconst_3");
        map.put(0x07, "iconst_4");
        map.put(0x08, "iconst_5");
        map.put(0x09, "lconst_0");
        map.put(0x0a, "lconst_1");
        map.put(0x0b, "fconst_0");
        map.put(0x0c, "fconst_1");
        map.put(0x0d, "fconst_2");
        map.put(0x0e, "dconst_0");
        map.put(0x0f, "dconst_1");
        map.put(0x10, "bipush");
        map.put(0x11, "sipush");
        map.put(0x12, "ldc");
        map.put(0x13, "ldc_w");
        map.put(0x14, "ldc2_w");
        map.put(0x15, "iload");
        map.put(0x16, "lload");
        map.put(0x17, "fload");
        map.put(0x18, "dload");
        map.put(0x19, "aload");
        map.put(0x1a, "iload_0");
        map.put(0x1b, "iload_1");
        map.put(0x1c, "iload_2");
        map.put(0x1d, "iload_3");
        map.put(0x1e, "lload_0");
        map.put(0x1f, "lload_1");
        map.put(0x20, "lload_2");
        map.put(0x21, "lload_3");
        map.put(0x22, "fload_0");
        map.put(0x23, "fload_1");
        map.put(0x24, "fload_2");
        map.put(0x25, "fload_3");
        map.put(0x26, "dload_0");
        map.put(0x27, "dload_1");
        map.put(0x28, "dload_2");
        map.put(0x29, "dload_3");
        map.put(0x2a, "aload_0");
        map.put(0x2b, "aload_1");
        map.put(0x2c, "aload_2");
        map.put(0x2d, "aload_3");
        map.put(0x2e, "iaload");
        map.put(0x2f, "laload");
        map.put(0x30, "faload");
        map.put(0x31, "daload");
        map.put(0x32, "aaload");
        map.put(0x33, "baload");
        map.put(0x34, "caload");
        map.put(0x35, "saload");
        map.put(0x36, "istore");
        map.put(0x37, "lstore");
        map.put(0x38, "fstore");
        map.put(0x39, "dstore");
        map.put(0x3a, "astore");
        map.put(0x3b, "istore_0");
        map.put(0x3c, "istore_1");
        map.put(0x3d, "istore_2");
        map.put(0x3e, "istore_3");
        map.put(0x3f, "lstore_0");
        map.put(0x40, "lstore_1");
        map.put(0x41, "lstore_2");
        map.put(0x42, "lstore_3");
        map.put(0x43, "fstore_0");
        map.put(0x44, "fstore_1");
        map.put(0x45, "fstore_2");
        map.put(0x46, "fstore_3");
        map.put(0x47, "dstore_0");
        map.put(0x48, "dstore_1");
        map.put(0x49, "dstore_2");
        map.put(0x4a, "dstore_3");
        map.put(0x4b, "astore_0");
        map.put(0x4c, "astore_1");
        map.put(0x4d, "astore_2");
        map.put(0x4e, "astore_3");
        map.put(0x4f, "iastore");
        map.put(0x50, "lastore");
        map.put(0x51, "fastore");
        map.put(0x52, "dastore");
        map.put(0x53, "aastore");
        map.put(0x54, "bastore");
        map.put(0x55, "castore");
        map.put(0x56, "sastore");
        map.put(0x57, "pop");
        map.put(0x58, "pop2");
        map.put(0x59, "dup");
        map.put(0x5a, "dup_x1");
        map.put(0x5b, "dup_x2");
        map.put(0x5c, "dup2");
        map.put(0x5d, "dup2_x1");
        map.put(0x5e, "dup2_x2");
        map.put(0x5f, "swap");
        map.put(0x60, "iadd");
        map.put(0x61, "ladd");
        map.put(0x62, "fadd");
        map.put(0x63, "dadd");
        map.put(0x64, "isub");
        map.put(0x65, "lsub");
        map.put(0x66, "fsub");
        map.put(0x67, "dsub");
        map.put(0x68, "imul");
        map.put(0x69, "lmul");
        map.put(0x6a, "fmul");
        map.put(0x6b, "dmul");
        map.put(0x6c, "idiv");
        map.put(0x6d, "ldiv");
        map.put(0x6e, "fdiv");
        map.put(0x6f, "ddiv");
        map.put(0x70, "irem");
        map.put(0x71, "lrem");
        map.put(0x72, "frem");
        map.put(0x73, "drem");
        map.put(0x74, "ineg");
        map.put(0x75, "lneg");
        map.put(0x76, "fneg");
        map.put(0x77, "dneg");
        map.put(0x78, "ishl");
        map.put(0x79, "lshl");
        map.put(0x7a, "ishr");
        map.put(0x7b, "lshr");
        map.put(0x7c, "iushr");
        map.put(0x7d, "lushr");
        map.put(0x7e, "iand");
        map.put(0x7f, "land");
        map.put(0x80, "ior");
        map.put(0x81, "lor");
        map.put(0x82, "ixor");
        map.put(0x83, "lxor");
        map.put(0x84, "iinc");
        map.put(0x85, "i2l");
        map.put(0x86, "i2f");
        map.put(0x87, "i2d");
        map.put(0x88, "l2i");
        map.put(0x89, "l2f");
        map.put(0x8a, "l2d");
        map.put(0x8b, "f2i");
        map.put(0x8c, "f2l");
        map.put(0x8d, "f2d");
        map.put(0x8e, "d2i");
        map.put(0x8f, "d2l");
        map.put(0x90, "d2f");
        map.put(0x91, "i2b");
        map.put(0x92, "i2c");
        map.put(0x93, "i2s");
        map.put(0x94, "lcmp");
        map.put(0x95, "fcmpl");
        map.put(0x96, "fcmpg");
        map.put(0x97, "dcmpl");
        map.put(0x98, "dcmpg");
        map.put(0x99, "ifeq");
        map.put(0x9a, "ifne");
        map.put(0x9b, "iflt");
        map.put(0x9c, "ifge");
        map.put(0x9d, "ifgt");
        map.put(0x9e, "ifle");
        map.put(0x9f, "if_icmpeq");
        map.put(0xa0, "if_icmpne");
        map.put(0xa1, "if_icmplt");
        map.put(0xa2, "if_icmpge");
        map.put(0xa3, "if_icmpgt");
        map.put(0xa4, "if_icmple");
        map.put(0xa5, "if_acmpeq");
        map.put(0xa6, "if_acmpne");
        map.put(0xa7, "goto");
        map.put(0xa8, "jsr");
        map.put(0xa9, "ret");
        map.put(0xaa, "tableswitch");
        map.put(0xab, "lookupswitch");
        map.put(0xac, "ireturn");
        map.put(0xad, "lreturn");
        map.put(0xae, "freturn");
        map.put(0xaf, "dreturn");
        map.put(0xb0, "areturn");
        map.put(0xb1, "return");
        map.put(0xb2, "getstatic");
        map.put(0xb3, "putstatic");
        map.put(0xb4, "getfield");
        map.put(0xb5, "putfield");
        map.put(0xb6, "invokevirtual");
        map.put(0xb7, "invokespecial");
        map.put(0xb8, "invokestatic");
        map.put(0xb9, "invokeinterface");
        map.put(0xba, "invokedynamic");
        map.put(0xbb, "new");
        map.put(0xbc, "newarray");
        map.put(0xbd, "anewarray");
        map.put(0xbe, "arraylength");
        map.put(0xbf, "athrow");
        map.put(0xc0, "checkcast");
        map.put(0xc1, "instanceof");
        map.put(0xc2, "monitorenter");
        map.put(0xc3, "monitorexit");
        map.put(0xc4, "wide");
        map.put(0xc5, "multianewarray");
        map.put(0xc6, "ifnull");
        map.put(0xc7, "ifnonnull");
        map.put(0xc8, "gogo_w");
        map.put(0xc9, "jsr_w");
        map.put(0xca, "breakpoint");
        map.put(0xfe, "impdep1");
        map.put(0xff, "impdep2");
    }

    public static String getInstruction(Integer code) {
        return map.get(code);
    }

    public static String getInstruction(String code) {
        return map.get(Integer.parseInt(code.substring(2), 16));
    }
}
