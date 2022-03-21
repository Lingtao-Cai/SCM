import com.aowin.scm.service.ScmUserService;
import com.aowin.scm.service.impl.ScmUserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ScmUserService scmUserService = context.getBean(ScmUserServiceImpl.class);
        System.out.println(scmUserService);
    }
}
