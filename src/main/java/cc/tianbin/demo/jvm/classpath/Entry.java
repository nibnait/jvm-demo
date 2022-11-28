package cc.tianbin.demo.jvm.classpath;

import cc.tianbin.demo.jvm.classpath.impl.CompositeEntry;
import cc.tianbin.demo.jvm.classpath.impl.DirEntry;
import cc.tianbin.demo.jvm.classpath.impl.WildcardEntry;
import cc.tianbin.demo.jvm.classpath.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

/**
 * 类路径接口
 * Created by nibnait on 2022/11/24
 */
public interface Entry {

    byte[] readClass(String className) throws IOException;

    static Entry create(String path) {

        //File.pathSeparator；路径分隔符(win/linux)
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }

        if (path.endsWith(".jar") || path.endsWith(".JAR") ||
                path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }

        return new DirEntry(path);
    }

}
