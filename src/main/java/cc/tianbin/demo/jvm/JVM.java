package cc.tianbin.demo.jvm;

import cc.tianbin.demo.jvm.classpath.Classpath;
import cc.tianbin.demo.jvm.instructions.base.ClassInitLogic;
import cc.tianbin.demo.jvm.rtda.Frame;
import cc.tianbin.demo.jvm.rtda.Thread;
import cc.tianbin.demo.jvm.rtda.heap.classloader.JClassLoader;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JClass;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.JObject;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.Method;
import cc.tianbin.demo.jvm.rtda.heap.methodarea.StringPool;
import cc.tianbin.demo.jvm.utils.LogUtil;

import java.util.List;

/**
 * Created by nibnait on 2022/12/13
 */
public class JVM {

    private final Args args;
    private final JClassLoader classLoader;
    private final Thread mainThread;

    public JVM(Args args) {
        Classpath classpath = new Classpath(args.jre, args.classpath);
        this.args = args;
        this.classLoader = new JClassLoader(classpath, args.verboseClassFlag);
        this.mainThread = new Thread();
    }

    public void start() {
        initVM();
        execMain();
    }

    private void initVM() {
        // 加载 sun.misc.VM 类
        JClass vmClass = classLoader.loadClass("sun/misc/VM");
        // 执行其类初始化方法
        ClassInitLogic.initClass(mainThread, vmClass);
        Interpreter.interpret(mainThread, args.verboseClassFlag);
    }

    /**
     * 执行 main 方法
     */
    private void execMain() {
        String className = args.getMainClass().replace(".", "/");
        JClass mainClass = classLoader.loadClass(className);
        Method mainMethod = mainClass.getMainMethod();
        if (mainMethod == null) {
            LogUtil.printf("Main method not found in class %s", args.getMainClass());
            return;
        }

        JObject argsArray = createArgsArray();
        Frame frame = mainThread.newFrame(mainMethod);
        frame.localVariables.setRef(0, argsArray);
        mainThread.pushFrame(frame);
        Interpreter.interpret(mainThread, args.verboseClassFlag);
    }

    /**
     * 把命令行入参 转化成数组对象
     */
    private JObject createArgsArray() {
        JClass stringClass = classLoader.loadClass("java/lang/String");
        List<String> appArgs = args.getAppArgs();
        JObject argsArr = stringClass.arrayClass().newArray(appArgs.size());
        JObject[] jArgs = argsArr.refs();
        for (int i = 0; i < jArgs.length; i++) {
            jArgs[i] = StringPool.jString(classLoader, appArgs.get(i));
        }
        return argsArr;
    }
}
