package hello.vali.controller;

import hello.vali.testDto.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ExceptionController {

    @PostMapping("/api/v1/exception")
    public Map<String,String> exceptionTest(@RequestBody TestDTO testDTO){
        log.info("TestDTO = {}" , testDTO);
        Map<String,String> map = new HashMap<>();
        map.put("success","true");
        return map;
    }


}
