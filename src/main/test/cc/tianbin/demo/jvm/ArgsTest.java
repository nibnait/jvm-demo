package cc.tianbin.demo.jvm;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by nibnait on 2022/11/28
 */
public class ArgsTest {

    @Test
    public void test() {
        assertTrue(Args.parse(new String[]{"-?"}).helpFlag);
        assertTrue(Args.parse(new String[]{"-help"}).helpFlag);
        assertTrue(Args.parse(new String[]{"-version"}).versionFlag);
        assertFalse(Args.parse(new String[]{"-cp"}).ok);
        assertFalse(Args.parse(new String[]{"-classpath"}).ok);
        assertEquals("foo.jar", Args.parse(new String[]{"-cp", "foo.jar"}).classpath);
        assertEquals("foo.jar", Args.parse(new String[]{"-classpath", "foo.jar"}).classpath);
        assertEquals(Arrays.asList("Main", "foo"), Args.parse(new String[]{"Main", "foo"}).mainClassAndArgs);
    }

}
