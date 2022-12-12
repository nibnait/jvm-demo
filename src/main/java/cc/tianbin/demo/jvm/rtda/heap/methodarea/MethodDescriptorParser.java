package cc.tianbin.demo.jvm.rtda.heap.methodarea;

import cc.tianbin.demo.jvm.exception.DescriptorParseException;

/**
 * Created by nibnait on 2022/12/12
 */
public class MethodDescriptorParser {

    private String raw;
    private int offset;
    private MethodDescriptor parsed;

    public static MethodDescriptor parseMethodDescriptorParser(String descriptor) {
        MethodDescriptorParser parser = new MethodDescriptorParser();
        return parser.parse(descriptor);
    }

    private MethodDescriptor parse(String descriptor) {
        this.raw = descriptor;
        this.parsed = new MethodDescriptor();
        this.startParams();
        this.parseParamTypes();
        this.endParams();
        this.parseReturnType();
        this.finish();
        return this.parsed;
    }

    //-------------------- 解析 descriptor --------------------

    private void parseParamTypes() {
        while (true) {
            String type = this.parseFieldType();
            if (FieldDescriptor.UNKNOWN.getCode().equals(type)) {
                break;
            }
            this.parsed.addParameterType(type);
        }
    }

    private void parseReturnType() {
        if (this.readU1() == 'V') {
            this.parsed.setReturnType("V");
            return;
        }

        this.unreadU1();
        String type = this.parseFieldType();
        if (!FieldDescriptor.UNKNOWN.getCode().equals(type)) {
            this.parsed.setReturnType(type);
            return;
        }

        this.causePanic();
    }

    //-------------------- 解析 descriptor 各个字段的 类型 --------------------

    private String parseFieldType() {
        char fieldType = this.readU1ToChar();
        FieldDescriptor fieldDescriptor = FieldDescriptor.getByStartCode(fieldType);
        if (FieldDescriptor.LREF.equals(fieldDescriptor)) {
            return this.parseObjectType();
        }
        if (FieldDescriptor.AREF.equals(fieldDescriptor)) {
            return this.parseArrayType();
        }
        if (FieldDescriptor.UNKNOWN.equals(fieldDescriptor)) {
            this.unreadU1();
        }
        return fieldDescriptor.getCode();
    }

    /**
     * 解析引用类型的描述符
     * L + 类的完全限定名 + 分号
     */
    private String parseObjectType() {
        String unread = this.raw.substring(this.offset);
        int semicolonIndex = unread.indexOf(";");
        if (semicolonIndex == -1) {
            this.causePanic();
            return "";
        }
        int objStart = this.offset - 1;
        int ojbEnd = this.offset + semicolonIndex + 1;
        this.offset = ojbEnd;
        //descriptor
        return this.raw.substring(objStart, ojbEnd);
    }

    /**
     * 解析数组类型的描述符是
     * [ + 数组元素类型描述符
     */
    private String parseArrayType() {
        int arrStart = this.offset - 1;
        this.parseFieldType();
        int arrEnd = this.offset;
        //descriptor
        return this.raw.substring(arrStart, arrEnd);
    }

    //-------------------- 校验 descriptor 格式 --------------------
    private void startParams() {
        if (this.readU1() != '(') {
            causePanic();
        }
    }

    private void endParams() {
        if (this.readU1() != ')') {
            causePanic();
        }
    }

    private void finish() {
        if (this.offset != this.raw.length()) {
            causePanic();
        }
    }

    //-------------------- 基础工具方法 --------------------
    private char readU1ToChar() {
        return (char) readU1();
    }

    private byte readU1() {
        byte[] bytes = this.raw.getBytes();
        byte b = bytes[this.offset];
        this.offset++;
        return b;
    }

    public void unreadU1() {
        this.offset--;
    }

    private void causePanic() {
        throw new DescriptorParseException("BAD descriptor：{}", this.raw);
    }
}
