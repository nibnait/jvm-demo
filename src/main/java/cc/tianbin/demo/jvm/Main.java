package cc.tianbin.demo.jvm;

/**
 * Created by nibnait on 2022/11/23
 */
public class Main {

    /**
     * Edit configurations -> program arguments
     * -help
     * -version
     * -cp foo/bar MyApp arg1 arg2
     */
    public static void main(String[] args) {
        Args cmd = Args.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
        } else if (cmd.versionFlag) {
            System.out.println("java version \"1.8.0\"");
        } else {
            startJVM(cmd);
        }
    }

    private static void startJVM(Args args) {
        System.out.printf("classpath:%s class:%s args:%s\n", args.classpath, args.getMainClass(), args.getAppArgs());
    }

}
