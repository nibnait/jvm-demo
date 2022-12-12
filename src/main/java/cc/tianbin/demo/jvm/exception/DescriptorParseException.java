package cc.tianbin.demo.jvm.exception;

import io.github.nibnait.common.enums.AbstractErrorCode;
import io.github.nibnait.common.utils.DataUtils;

/**
 * Created by nibnait on 2022/12/04
 */
public class DescriptorParseException extends RuntimeException {

    private Long code;

    private String message;

    public DescriptorParseException() {
        super();
    }

    public DescriptorParseException(String message) {
        super(message);
        this.message = message;
    }

    public DescriptorParseException(String message, Object... args) {
        super(DataUtils.format(message, args));
        this.message = DataUtils.format(message, args);
    }

    public DescriptorParseException(Long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public DescriptorParseException(Long code, String message, Object... args) {
        super(DataUtils.format(message, args));
        this.code = code;
        this.message = DataUtils.format(message, args);
    }

    public DescriptorParseException(AbstractErrorCode errorCode, String message, Object... args) {
        super(DataUtils.format(message, args));
        this.code = errorCode.getCode();
        this.message = DataUtils.format(message, args);
    }

    public DescriptorParseException(AbstractErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
