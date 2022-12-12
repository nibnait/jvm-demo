## 指令表
Java虚拟机规范把已经定义的205条指令按用途分成了11类， 分别是：常量（constants）指令、加载（loads）指令、存储（stores）指令、
操作数栈（stack）指令、数学（math）指令、转换（conversions）指令、比较（comparisons）指令、控制（control）指令、引用（references）指令、
扩展（extended）指令和保留（reserved）指令。

### Constants

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>                  | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>            | <div style="width:50px">功能</div> |
|-----------------------------------|----------------------------------------------------|---------------------------------| ------ |----------------------------------------------| ---- |
| 0x00                              | [nop](./constants/nop/NOP.java)                    | 啥也不干                            | 0x0b | [fconst_0](./constants/consts/FCONST_0.java) | 把 float型 0，推入操作数栈顶 |
| 0x01                              | [aconst_null](./constants/consts/ACONST_NULL.java) | 把 null 引用推入操作数栈顶                | 0x0c | [fconst_1](./constants/consts/FCONST_1.java) | 把 float型 1，推入操作数栈顶 |
| 0x02 | [iconst_m1](./constants/consts/ICONST_M1.java)     | 把 int型 -1，推入操作数栈顶 | 0x0d | [fconst_2](./constants/consts/FCONST_2.java) | 把 float型 2，推入操作数栈顶 |
| 0x03 | [iconst_0](./constants/consts/ICONST_0.java)       | 把 int型 0，推入操作数栈顶 | 0x0e | [dconst_0](./constants/consts/DCONST_0.java) | 把 double型 0，推入操作数栈顶 |
| 0x04 | [iconst_1](./constants/consts/ICONST_1.java)       | 把 int型 1，推入操作数栈顶 | 0x0f | [dconst_1](./constants/consts/DCONST_1.java) | 把 double型 1，推入操作数栈顶 |
| 0x05 | [iconst_2](./constants/consts/ICONST_2.java)       | 把 int型 2，推入操作数栈顶 | 0x10 | [bipush](./constants/ipush/BIPUSH.java)      | 从操作数中获取一个byte型整数，扩展成int型，然 后推入栈顶 |
| 0x06 | [iconst_3](./constants/consts/ICONST_3.java)       | 把 int型 3，推入操作数栈顶 | 0x11 | [sipush](./constants/ipush/SIPUSH.java)      | 从操作数中获取一个short型整数，扩展成 int型，然后推入栈顶 |
| 0x07 | [iconst_4](./constants/consts/ICONST_4.java)       | 把 int型 4，推入操作数栈顶 | 0x12 | [ldc](./constants/ldc/LDC.java)              | 从运行时常量池中加载 int/float/String/ClassRef 型常量值(1个槽位)，并把它推入操作数栈 |
| 0x08 | [iconst_5](./constants/consts/ICONST_5.java)       | 把 int型 5，推入操作数栈顶 | 0x13 | [ldc_w](./constants/ldc/LDC_W.java)       | 从运行时常量池中加载 int/float/String/ClassRef 型常量值(2个槽位)，并把它推入操作数栈 |
| 0x09 | [lconst_0](./constants/consts/LCONST_0.java)       | 把 long型 0，推入操作数栈顶 | 0x14 | [ldc2_w](./constants/ldc/LDC2_W.java)     | 从运行时常量池中加载 long/double型常量值，并把它推入操作数栈 |
| 0x0a | [lconst_1](./constants/consts/LCONST_1.java)       | 把 long型 1，推入操作数栈顶 |        |                                              |      |

### Loads

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>     | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>     | <div style="width:50px">功能</div> |
| ------ |---------------------------------------| ---- | ------ |---------------------------------------| ---- |
| 0x15   | [iload](./loads/iload/ILOAD.java)     | 从局部变量表获取 int型变量，然后推入操作数栈顶 | 0x26 | [dload_0](./loads/dload/DLOAD_0.java) |      |
| 0x16   | [lload](./loads/lload/LLOAD.java)     | （索引来自操作数） | 0x27 | [dload_1](./loads/dload/DLOAD_1.java) |      |
| 0x17 | [fload](./loads/fload/FLOAD.java)     | ...float... | 0x28 | [dload_2](./loads/dload/DLOAD_2.java) |      |
| 0x18 | [dload](./loads/dload/DLOAD.java)     | ...double... | 0x29 | [dload_3](./loads/dload/DLOAD_3.java) |      |
| 0x19 | [aload](./loads/aload/ALOAD.java)     | ...引用变量... | 0x2a | [aload_0](./loads/aload/ALOAD_0.java) |      |
| 0x1a | [iload_0](./loads/iload/ILOAD_0.java) | 将局部变量表中索引为0的 int型变量，然后推入操作数栈顶 | **0x2b** | [aload_1](./loads/aload/ALOAD_1.java) |      |
| 0x1b | [iload_1](./loads/iload/ILOAD_1.java) | （索引隐含在操作码中） | 0x2c | [aload_2](./loads/aload/ALOAD_2.java) |      |
| 0x1c | [iload_2](./loads/iload/ILOAD_2.java) |      | 0x2d | [aload_3](./loads/aload/ALOAD_3.java) |      |
| 0x1d | [iload_3](./loads/iload/ILOAD_3.java) |      | 0x2e | [iaload](./loads/xaload/IALOAD.java)  |      |
| 0x1e | [lload_0](./loads/lload/LLOAD_0.java) |      | 0x2f | [laload](./loads/xaload/LALOAD.java)  |      |
| 0x1f | [lload_1](./loads/lload/LLOAD_1.java) |      | 0x30 | [faload](./loads/xaload/FALOAD.java)  |      |
| 0x20 | [lload_2](./loads/lload/LLOAD_2.java) | | 0x31 | [daload](./loads/xaload/DALOAD.java)  | |
| 0x21 | [lload_3](./loads/lload/LLOAD_3.java) | | 0x32 | [aaload](./loads/xaload/AALOAD.java)  | |
| 0x22 | [fload_0](./loads/fload/FLOAD_0.java)                              | | 0x33 | [baload](./loads/xaload/BALOAD.java)  | |
| 0x23 | [fload_1](./loads/fload/FLOAD_1.java)                              | | 0x34 | [caload](./loads/xaload/CALOAD.java)  |  |
| 0x24 | [fload_2](./loads/fload/FLOAD_2.java)                              | | 0x35 | [saload](./loads/xaload/SALOAD.java)  | |
| 0x25 | [fload_3](./loads/fload/FLOAD_3.java)                              | |  |                                       | |

### Stores

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>         | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>         | <div style="width:50px">功能</div> |
| ------ |-------------------------------------------| ---- | ------ |-------------------------------------------| ---- |
| 0x36 | [istore](./stores/istore/ISTORE.java)     | 从操作数栈顶弹出 int 变量，存入局部变量表 | 0x47 | [dstore_0](./stores/dstore/DSTORE_0.java) |      |
| 0x37 | [lstore](./stores/lstore/LSTORE.java)     | | 0x48 | [dstore_1](./stores/dstore/DSTORE_1.java) | |
| 0x38 | [fstore](./stores/fstore/FSTORE.java)     |  | 0x49 | [dstore_2](./stores/dstore/DSTORE_2.java) | |
| 0x39 | [dstore](./stores/dstore/DSTORE.java)     | | 0x4a | [dstore_3](./stores/dstore/DSTORE_3.java) | |
| 0x3a | [astore](./stores/astore/ASTORE.java)     | | 0x4b | [astore_0](./stores/astore/ASTORE_0.java) | |
| 0x3b | [istore_0](./stores/istore/ISTORE_0.java) | 从操作数栈顶弹出 int 变量，存入局部变量表的0号位置 | 0x4c | [astore_1](./stores/astore/ASTORE_1.java) | |
| 0x3c | [istore_1](./stores/istore/ISTORE_1.java) | | 0x4d | [astore_2](./stores/astore/ASTORE_2.java) | |
| 0x3d | [istore_2](./stores/istore/ISTORE_2.java) | | 0x4e | [astore_3](./stores/astore/ASTORE_3.java) | |
| 0x3e | [istore_3](./stores/istore/ISTORE_3.java) | | 0x4f | [iastore](./stores/xastore/IASTORE.java)  | |
| 0x3f | [lstore_0](./stores/lstore/LSTORE_0.java) | | 0x50 | [lastore](./stores/xastore/LASTORE.java)  | |
| 0x40 | [lstore_1](./stores/lstore/LSTORE_1.java) | | 0x51 | [fastore](./stores/xastore/FASTORE.java)  | |
| 0x41 | [lstore_2](./stores/lstore/LSTORE_2.java) | | 0x52 | [dastore](./stores/xastore/DASTORE.java)  | |
| 0x42 | [lstore_3](./stores/lstore/LSTORE_3.java) | | 0x53 | [aastore](./stores/xastore/AASTORE.java)  | |
| 0x43 | [fstore_0](./stores/fstore/FSTORE_0.java) | | 0x54 | [bastore](./stores/xastore/BASTORE.java)  | |
| 0x44 | [fstore_1](./stores/fstore/FSTORE_1.java) | | 0x55 | [castore](./stores/xastore/CASTORE.java)  | |
| 0x45 | [fstore_2](./stores/fstore/FSTORE_2.java) | | 0x56 | [sastore](./stores/xastore/SASTORE.java)  | |
| 0x46 | [fstore_3](./stores/fstore/FSTORE_3.java) | |  |                                           | |

### Stack

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>   | <div style="width:50px">功能</div> |
| ------ |-----------------------------------| ---- | ------ |-------------------------------------| ---- |
| 0x57 | [pop](./stack/pop/POP.java)       | 将栈顶变量（int, float 等占用1个操作数位置的变量）弹出 | 0x5c | [dup2](./stack/dup/DUP2.java)       | 复制栈顶[2个槽位]的操作数堆栈值 |
| 0x58 | [pop2](./stack/pop/POP2.java)     | 将栈顶变量（double, long 占用2个操作数位置的变量）弹出 | 0x5d | [dup2_x1](./stack/dup/DUP2_X1.java) | 复制栈顶[2个槽位]操作数堆栈值，然后向下插入3个槽位（两个或三个值） |
| 0x59 | [dup](./stack/dup/DUP.java)       | 复制栈顶[1个槽位]变量 | 0x5e | [dup2_x2](./stack/dup/DUP2_X2.java) | 复制栈顶[2个槽位]操作数堆栈值，然后向下插入4个槽位（两个、三个或四个值） |
| 0x5a | [dup_x1](./stack/dup/DUP_X1.java) | 复制栈顶[1个槽位]操作数堆栈值并向下插入2个槽位（1个或2个值） | 0x5f | [swap](./stack/swap/SWAP.java)      | 交换栈顶的两个变量 |
| 0x5b | [dup_x2](./stack/dup/DUP_X2.java) | 复制栈顶[1个槽位]操作数堆栈值并向下插入3个槽位（2个或3个值） |  |                                     | |

### Math

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> |
|-----------------------------------|-----------------------------------| ---- | ------ |-----------------------------------| ---- |
| 0x60                              | [iadd](./math/add/IADD.java)      | 加法 | 0x74 | [ineg](./math/neg/INEG.java)      | 取反 |
| 0x61                              | [ladd](./math/add/LADD.java)      |  |  0x75| [lneg](./math/neg/LNEG.java)      | |
| 0x62                              | [fadd](./math/add/FADD.java)      |  |  0x76| [fneg](./math/neg/FNEG.java)      | |
| 0x63                              | [dadd](./math/add/DADD.java)      |  |  0x77| [dneg](./math/neg/DNEG.java)      | |
| 0x64                              | [isub](./math/sub/ISUB.java)      | 减法 |  0x78| [ishl](./math/sh/ISHL.java)       | << 算术左位移（有符号） |
| 0x65                              | [lsub](./math/sub/LSUB.java)      | pop2 - pop1 |  0x79| [lshl](./math/sh/LSHL.java)       |  |
| 0x66                              | [fsub](./math/sub/FSUB.java)      |  |  0x7a| [ishr](./math/sh/ISHR.java)       | >> 算术右位移（有符号） |
| 0x67                              | [dsub](./math/sub/DSUB.java)      |  |  0x7b| [lshr](./math/sh/LSHR.java)       |  |
| 0x68                              | [imul](./math/mul/IMUL.java)      | 乘法 |  0x7c| [iushr](./math/sh/IUSHR.java)     | >>> 逻辑右位移（无符号） |
| 0x69                              | [lmul](./math/mul/LMUL.java)      |  |  0x7d| [lushr](./math/sh/LUSHR.java)     | >>> 逻辑右位移（无符号） |
| 0x6a                              | [fmul](./math/mul/FMUL.java)      |  |  0x7e| [iand](./math/and/IAND.java)      | 按位与 |
| 0x6b                              | [dmul](./math/mul/DMUL.java)      |  |  0x7f| [land](./math/and/LAND.java)      | |
| 0x6c                              | [idiv](./math/div/IDIV.java)      | 除法 |  0x80| [ior](./math/or/IOR.java)         | 按位或 |
| 0x6d                              | [ldiv](./math/div/LDIV.java)      | pop2 / pop1 |  0x81| [lor](./math/or/LOR.java)         | |
| 0x6e                              | [fdiv](./math/div/FDIV.java)      |  |  0x82| [ixor](./math/xor/IOR.java)       | 按位异或 |
| 0x6f                              | [ddiv](./math/div/DDIV.java)      |  |  0x83| [lxor](./math/xor/LOR.java)       | |
| 0x70                              | [irem](./math/rem/IREM.java)      | 求余 |  0x84| [iinc](./math/iinc/IINC.java)     | 给局部变量表中 index 位置的值，加上当前的操作数 constVal（int 常量值） |
| 0x71                              | [lrem](./math/rem/LREM.java)      | pop2 % pop1 |  |                                   | |
| 0x72                              | [frem](./math/rem/FREM.java)      |  |  |                                   | |
| 0x73                              | [drem](./math/rem/DREM.java)      |  |  |                                   | |

### Conversions

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> |
|-----------------------------------|-----------------------------------| ---- | ------ |-----------------------------------| ---- |
| 0x85                            | [i2l](./conversions/i2x/I2L.java) | int 转成 long | 0x8e | [d2i](./conversions/d2x/D2I.java) | double 转成 int |
| 0x86 | [i2f](./conversions/i2x/I2F.java) | | 0x8f | [d2l](./conversions/d2x/D2L.java) |  |
| 0x87 | [i2d](./conversions/i2x/I2D.java) | | 0x90 | [d2f](./conversions/d2x/D2F.java) |  |
| 0x88 | [l2i](./conversions/l2x/L2I.java) | long 转成 int | 0x91 | [i2b](./conversions/i2x/I2B.java) | int to byte |
| 0x89 | [l2f](./conversions/l2x/L2F.java) | | 0x92 | [i2c](./conversions/i2x/I2C.java) | int to char |
| 0x8a | [l2d](./conversions/l2x/L2D.java) | | 0x93 | [i2s](./conversions/i2x/I2S.java) | int to short |
| 0x8b | [f2i](./conversions/f2x/F2I.java) | float 转成 int |  |                                   |  |
| 0x8c | [f2l](./conversions/f2x/F2L.java) | |  |                                   |  |
| 0x8d | [f2d](./conversions/f2x/F2D.java) | |  |                                   |  |

### Comparisons

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>       | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>                   | <div style="width:50px">功能</div> |
|-----------------------------------|-----------------------------------------| ---- | ------ |-----------------------------------------------------|----------------------------------|
| 0x94                       | [lcmp](./comparisons/lcmp/LCMP.java)   | 比较栈顶的两个long | 0x9f | [if_icmpeq](./comparisons/if_icmp/IF_ICMPEQ.java) | pop2 == pop1, branch             |
| 0x95 | [fcmpl](./comparisons/fcmp/FCMPL.java) | 两个 float 中有 NaN，直接返回-1 |0xa0  | [if_icmpne](./comparisons/if_icmp/IF_ICMPNE.java) | pop2 != pop1, branch             |
| 0x96 | [fcmpg](./comparisons/fcmp/FCMPG.java) | 两个 float 中有 NaN，直接返回1 |0xa1  | [if_icmplt](./comparisons/if_icmp/IF_ICMPLT.java) | pop2 < pop1, branch              |
| 0x97 | [dcmpl](./comparisons/dcmp/DCMPL.java) | 两个 double 中有 NaN，直接返回-1 |0xa2  | [if_icmpge](./comparisons/if_icmp/IF_ICMPGE.java) | pop2 >= pop1, branch             |
| 0x98 | [dcmpg](./comparisons/dcmp/DCMPG.java) | 两个 double 中有 NaN，直接返回1 |0xa3  | [if_icmpgt](./comparisons/if_icmp/IF_ICMPGT.java) | pop2 > pop1, branch              |
| 0x99 | [ifeq](./comparisons/ifcond/IFEQ.java) | == 0, branch |0xa4  | [if_icmple](./comparisons/if_icmp/IF_ICMPLE.java) | pop2 >= pop1, branch             |
| 0x9a | [ifne](./comparisons/ifcond/IFNE.java) | != 0, branch |0xa5  | [if_acmpeq](./comparisons/if_acmp/IF_ACMPEQ.java) | pop2 == pop1, branch             |
| 0x9b | [iflt](./comparisons/ifcond/IFLT.java) | < 0, branch |0xa6  | [if_acmpne](./comparisons/if_acmp/IF_ACMPNE.java) | pop2 != pop2, branch             |
| 0x9c | [ifge](./comparisons/ifcond/IFGE.java) | >= 0, branch |  |                                                     |                                  |
| 0x9d | [ifgt](./comparisons/ifcond/IFGT.java) | > 0, branch |  |                                                     |                                  |
| 0x9e | [ifle](./comparisons/ifcond/IFLE.java) | <= 0, branch |  |                                                     |                                  |

### Control

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>     | <div style="width:50px">功能</div> |
|-----------------------------------|-----------------------------------| ---- | ------ |---------------------------------------| ---- |
| 0xa7                         | [goto](./control/GOTO.java)       | goto offset | 0xac | [ireturn](./control/ret/IRETURN.java) |  |
| 0xa8 | [jsr](./control/JSR.java)         | 从Java 6开始，Oracle的Java编译器已经不再使用 | 0xad | [lreturn](./control/ret/LRETURN.java) | |
| 0xa9 | [ret](./control/RET.java)         | 从Java 6开始，Oracle的Java编译器已经不再使用 | 0xae | [freturn](./control/ret/FRETURN.java) | |
| 0xaa | [tableswitch](./control/TABLESWITCH.java)                      | 按索引跳转 | 0xaf | [dreturn](./control/ret/DRETURN.java) | |
| 0xab | [lookupswitch](./control/LOOKUPSWITCH.java)                     | 按key匹配跳转 | 0xb0 | [areturn](./control/ret/ARETURN.java) | |
|  |                                   | | 0xb1 | [return](./control/ret/RETURN.java)   | |

### References

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>                     | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>          | <div style="width:50px">功能</div> |
|-----------------------------------|-------------------------------------------------------| ---- |-----------------------------------|--------------------------------------------| ---- |
| 0xb2                              | [getstatic](./references/GETSTATIC.java)              | 取出类的某个静态变量值，push到栈顶 | 0xbb                              | [new](./references/NEW.java)               | 创建类实例 |
| 0xb3 | [putstatic](./references/PUTSTATIC.java)              | 给类的某个静态变量赋值 | 0xbc | newarray                                   | |
| 0xb4 | [getfield](./references/GETFIELD.java)                | 获取对象的实例变量值，push到栈顶 | 0xbd | anewarray                                  | |
| 0xb5 | [putfield](./references/PUTFIELD.java)                | 给实例变量赋值 | 0xbe | arraylength                                | |
| 0xb6 | [invokevirtual](./references/INVOKE_VIRTUAL.java)     | 调用动态方法 | 0xbf | athrow                                     | |
| 0xb7 | [invokespecial](./references/INVOKE_SPECIAL.java)     | 调用无需动态绑定的实例方法（构造函数、私有方法、super 父类方法） | 0xc0 | [checkcast](./references/CHECKCAST.java)   | 判断对象是否属于某种类型。<br/>如果属于啥事没有，如果不属于，直接报 ClassCastException |
| 0xb8 | [invokestatic](./references/INVOKE_STATIC.java)       | 调用静态方法 | 0xc1 | [instanceof](./references/INSTANCEOF.java) | 判断对象是否是某个类的实例（或者对象的类是否实现了某个接口），并把结果推入操作数栈。 |
| 0xb9 | [invokeinterface](./references/INVOKE_INTERFACE.java) | 调用（接口类型的）动态方法 | 0xc2 | monitorenter                               | |
| 0xba | [invokedynamic](./references/INVOKE_DYNAMIC.java)     | 调用动态方法 | 0xc3 | monitorexit                                | |

### Extended

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>                | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> |
|-----------------------------------|--------------------------------------------------| ---- |-----------------------------------|-----------------------------------| ---- |
| 0xc4                              | [wide](./extended/WIDE.java)                     | 将局部变量表的索引扩到2个字节 | 0xc8                              | [gogo_w](./extended/GOTO_W.java)  | goto 指令的索引是1字节<br/>goto_w 指令的索引是2字节 |
| 0xc5 | [multianewarray](./extended/MULTIANEWARRAY.java) | | 0xc9 | [jsr_w](./extened/JSR_W.java)     | 从Java 6开始，Oracle的Java编译器已经不再使用 |
| 0xc6 | [ifnull](./extended/IFNULL.java)                 | popRef == null, branch |  |                                   | |
| 0xc7 | [ifnonnull](./extended/IFNONULL.java)            | popRef != null, branch |  |                                   | |

### Reserved

| <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div>   | <div style="width:50px">功能</div> | <div style="width:50px">操作码</div> | <div style="width:50px">助记符</div> | <div style="width:50px">功能</div> |
|-----------------------------------| ------ | ---- |-----------------------------------|-----------------------------------| ---- |
| 0xca                              | breakpoint |  | 0xfe                              | impdep1 |  |
|  | | | 0xff | impdep2 | |
