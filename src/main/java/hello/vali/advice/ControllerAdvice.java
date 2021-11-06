package hello.vali.advice;


import hello.vali.testDto.TestEx;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {
    
    
    // 해당 예외는 BindException 발생 했을 경우에 터진다 (handler)
    // 다른 예외는 @ExceptionHandler 등록 후 사용 Exception으로 등록 할 경우 모든 예외의 부모 -> 하위 예외도 전부 잡아버림
    // 예외는 상세한 순서로 잡힘.
    @ExceptionHandler
    public ResponseEntity<TestEx> userFault(BindException bindException){
        TestEx testEx = TestEx.builder()
                              .status(HttpStatus.BAD_REQUEST.value())
                              .code("customCode")
                              .message("customMessage")
                              .curTime(LocalDateTime.now().toString())
                              .build();
        testEx.ApiResultError(bindException.getBindingResult());

        return  new ResponseEntity<>(testEx,HttpStatus.BAD_REQUEST);
    }


}
