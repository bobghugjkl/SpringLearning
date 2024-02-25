import com.xxxx.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class start01 {
    public static void main(String[] args) {
        //获取上下文环境
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        //获取对象
        UserService userService = (UserService) ac.getBean("userService");
        userService.test();

    }
}
