package cc.tianbin.demo.jvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.util.List;

/**
 * Created by nibnait on 2022/11/23
 */
public class Args {

    @Parameter(names = {"-?", "-help"}, description = "print help message", order = 3, help = true)
    boolean helpFlag = false;

    @Parameter(names = "-version", description = "print version and exit", order = 2)
    boolean versionFlag = false;

    @Parameter(names = {"-cp", "-classpath"}, description = "classpath", order = 1)
    String classpath;

    @Parameter(names = "-XJre", description = "path to jre", order = 4)
    String jre;

    // todo 解析命令
    @Parameter(names = "-Xms", description = "堆的初始大小", order = 4)
    String xms;
    @Parameter(names = "-Xmx", description = "堆的最大大小", order = 4)
    String xmx;

    @Parameter(names = "-Xss", description = "虚拟机栈的大小", order = 4)
    String xss;

    @Parameter(names = {"-verbose", "-verbose:class"}, description = "enable verbose class output", order = 5)
    boolean verboseClassFlag = false;

    @Parameter(names = "-verbose:inst", description = "enable verbose instruction output", order = 5)
    boolean verboseInstFlag = false;

    @Parameter(description = "main class and args")
    List<String> mainClassAndArgs;

    boolean ok;

    String getMainClass() {
        return mainClassAndArgs != null && !mainClassAndArgs.isEmpty()
                ? mainClassAndArgs.get(0)
                : null;
    }

    List<String> getAppArgs() {
        return mainClassAndArgs != null && mainClassAndArgs.size() > 1
                ? mainClassAndArgs.subList(1, mainClassAndArgs.size())
                : null;
    }

    /**
     * https://github.com/cbeust/jcommander
     */
    public static Args parse(String[] argv) {
        Args args = new Args();
        try {
            JCommander.newBuilder().addObject(args).build().parse(argv);
            args.ok = true;
        } catch (ParameterException ignored) {

        }
        return args;
    }

}
