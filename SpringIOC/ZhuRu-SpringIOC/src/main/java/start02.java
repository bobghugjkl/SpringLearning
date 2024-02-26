import com.xxxx.service.TypeService;
import com.xxxx.service.UserService;
import com.xxxx.service.UserService02;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class start02 {
    public static void main(String[] args) {
        //获取上下文环境
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring02.xml");
        //获取对象
        UserService02 userService02 = (UserService02) ac.getBean("userService02");
        userService02.test();


        TypeService typeService = (TypeService) ac.getBean("typeService");
        typeService.test();
    }
}
