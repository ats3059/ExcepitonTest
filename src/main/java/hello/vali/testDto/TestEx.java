package hello.vali.testDto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class TestEx {
    @DateTimeFormat(pattern="yyyyMMddHHmmss")
    private String curTime;
    private String message;
    private Integer status;
    private List<FieldError> errors;
    private String code;

    public void ApiResultError(BindingResult bind) {
        this.errors = FieldError.fieldList(bind);
    }

    @Getter
    static class FieldError {
        private String field;
        private String message;
        private String rejectedVal;

        private FieldError(String title, String message, String rejectedVal) {
            this.field = title;
            this.message = message;
            this.rejectedVal = rejectedVal;
        }

        private static List<FieldError> fieldList(BindingResult bind) {
            List<org.springframework.validation.FieldError> list = bind.getFieldErrors();
            return list.stream()
                    .map((innerObject) -> new FieldError(innerObject.getField(), innerObject.getDefaultMessage(),innerObject.getRejectedValue().toString()))
                    .collect(Collectors.toList());
        }
    }


}
