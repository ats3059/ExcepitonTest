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
