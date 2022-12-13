package cc.tianbin.demo.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by nibnait on 2022/11/23
 */
@Slf4j
public class MainNewJVM {

    /**
     *
     */
    public static void main(String[] argv) {
        Args args = Args.parse(argv);
        new JVM(args).start();
    }

}