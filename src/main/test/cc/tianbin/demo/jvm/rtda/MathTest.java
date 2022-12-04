package cc.tianbin.demo.jvm.rtda;

import cc.tianbin.demo.jvm.Args;
import cc.tianbin.demo.jvm.rtda.frame.LocalVariables;
import cc.tianbin.demo.jvm.rtda.frame.OperandStack;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nibnait on 2022/12/04
 */
public class MathTest {

    /**
     * 1. run Math.main() 方法
     * @see Math#main(String[])
     * <p>
     * 2. Edit configurations -> program arguments
     * -cp /Users/nibnait/github/jvm-demo/target/test-classes/cc/tianbin/demo/jvm/rtda Math
     */
    public static void main(String[] argv) {
        Args args = Args.parse(argv);
        startJVM(args);
    }

    private static void startJVM(Args args) {



    }

    @Test
    public void testLocalVars() {
        LocalVariables localVars = new LocalVariables(1024);

        localVars.setInt(0, Integer.MAX_VALUE);
        localVars.setInt(1, Integer.MIN_VALUE);
        localVars.setFloat(2, Float.MAX_VALUE);
        localVars.setFloat(3, Float.MIN_VALUE);

        localVars.setLong(4, Long.MAX_VALUE);
        localVars.setLong(6, Long.MIN_VALUE);
        localVars.setDouble(8, Double.MAX_VALUE);
        localVars.setDouble(10, Double.MIN_VALUE);
        Object object = new Object();
        localVars.setRef(12, object);


        Assert.assertEquals(Integer.MAX_VALUE, localVars.getInt(0));
        Assert.assertEquals(Integer.MIN_VALUE, localVars.getInt(1));

        Assert.assertEquals(Float.MAX_VALUE, localVars.getFloat(2), 0);
        Assert.assertEquals(Float.MIN_VALUE, localVars.getFloat(3), 0);

        Assert.assertEquals(Long.MAX_VALUE, localVars.getLong(4), 0);
        Assert.assertEquals(Long.MIN_VALUE, localVars.getLong(6), 0);

        Assert.assertEquals(Double.MAX_VALUE, localVars.getDouble(8), 0);
        Assert.assertEquals(Double.MIN_VALUE, localVars.getDouble(10), 0);

        Assert.assertEquals(object, localVars.getRef(12));
    }

    @Test
    public void testOperandStack() {
        OperandStack operandStack = new OperandStack(1024);
        operandStack.pushInt(Integer.MAX_VALUE);
        operandStack.pushInt(Integer.MIN_VALUE);
        operandStack.pushFloat(Float.MAX_VALUE);
        operandStack.pushFloat(Float.MIN_VALUE);
        operandStack.pushLong(Long.MAX_VALUE);
        operandStack.pushLong(Long.MIN_VALUE);
        operandStack.pushDouble(Double.MAX_VALUE);
        operandStack.pushDouble(Double.MIN_VALUE);

        Object object = new Object();
        operandStack.pushRef(object);

        Assert.assertEquals(object, operandStack.popRef());
        Assert.assertEquals(Double.MIN_VALUE, operandStack.popDouble(), 0);
        Assert.assertEquals(Double.MAX_VALUE, operandStack.popDouble(), 0);

        Assert.assertEquals(Long.MIN_VALUE, operandStack.popLong(), 0);
        Assert.assertEquals(Long.MAX_VALUE, operandStack.popLong(), 0);

        Assert.assertEquals(Float.MIN_VALUE, operandStack.popFloat(), 0);
        Assert.assertEquals(Float.MAX_VALUE, operandStack.popFloat(), 0);

        Assert.assertEquals(Integer.MIN_VALUE, operandStack.popInt());
        Assert.assertEquals(Integer.MAX_VALUE, operandStack.popInt());
    }


}
