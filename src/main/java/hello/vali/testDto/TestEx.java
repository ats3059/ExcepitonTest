package hello.vali.testDto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Builder
@Getter
public class TestEx {
    private String curTime;
    private String message;
    private Integer status;
    private List<FieldError> errors;
    private String code;

    public void ApiResultError(BindingResult bind,MessageSource messageSource) {
        this.errors = FieldError.fieldList(bind,messageSource);
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

        private static List<FieldError> fieldList(BindingResult bind,MessageSource messageSource) {
            Locale locale = LocaleContextHolder.getLocale();
            List<org.springframework.validation.FieldError> list = bind.getFieldErrors();

            //MessageSource 적용
            return list.stream()
                    .map((innerObject) -> new FieldError(innerObject.getField()
                            ,messageSource.getMessage(innerObject,locale)
                            ,innerObject.getRejectedValue().toString()))
                    .collect(Collectors.toList());
        }
    }


}
