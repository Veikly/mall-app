package com.cdu.pojo.dto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
/**
 * @ProjectName: cdu-app
 * @Titile: UserRegDTO
 * @Author: Administrator
4.3 在控制器加注解
4.4 测试校验
校验成功，如果校验注解不能完成的功能，那么就需要使用逻辑代码（写在业务层）实现。
 * @Description: 用户注册DTO
 */
@Data
public class UserRegDTO {
    //用户名
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$", message = "用户名必须是4-16位的字母或数字")
    private String username;
    //密码
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度必须在6-16位之间")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+]{6,16}$", message = "密码只能包含字母、数字和特殊字符")
    private String password;
    //确认密码
    @NotBlank(message = "确认密码不能为空")
    private String rePassword;
}