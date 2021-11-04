package hello.vali.testDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String age;



}
