package cc.tianbin.demo.jvm.ch09;

public class GetClassTest {

    /**
     * -XJre /Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre /Users/nibnait/github/jvm-demo/src/main/test/cc/tianbin/demo/jvm/ch09/GetClassTest -verbose true -args 你好，java版虚拟机v1.0，欢迎你的到来。
     */
    public static void main(String[] args) {
        System.out.println(void.class.getName()); // void
        System.out.println(boolean.class.getName()); // boolean
        System.out.println(byte.class.getName()); // byte
        System.out.println(char.class.getName()); // char
        System.out.println(short.class.getName()); // short
        System.out.println(int.class.getName()); // int
        System.out.println(long.class.getName()); // long
        System.out.println(float.class.getName()); // float
        System.out.println(double.class.getName()); // double
        System.out.println(Object.class.getName()); // java.lang.Object
        System.out.println(GetClassTest.class.getName()); // jvmgo.book.ch09.GetClassTest
        System.out.println(int[].class.getName()); // [I
        System.out.println(int[][].class.getName()); // [[I
        System.out.println(Object[].class.getName()); // [Ljava.lang.Object;
        System.out.println(Object[][].class.getName()); // [[Ljava.lang.Object;
        System.out.println(Runnable.class.getName()); // java.lang.Runnable
        System.out.println("abc".getClass().getName()); // java.lang.String
        System.out.println(new double[0].getClass().getName()); // [D
        System.out.println(new String[0].getClass().getName()); // [Ljava.lang.String;

        System.out.println(String.class);
        System.out.println("abc".getClass());
    }

}
