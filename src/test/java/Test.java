import com.aowin.scm.dao.CategoryDao;
import com.aowin.scm.dao.ModelDao;
import com.aowin.scm.dao.ProductDao;
import com.aowin.scm.dao.ScmUserDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test {

    @Autowired
    private ScmUserDao scmUserDao;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @org.junit.Test
    public void test1() {
        System.out.println(scmUserDao.findUserByAccount("1234"));
    }

    @org.junit.Test
    public void test2() {
        //System.out.println(modelDao.findModel("ws"));
        System.out.println(categoryDao.findCategoriesId("乳品类"));
    }

//    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        ScmUserService userService = context.getBean(ScmUserServiceImpl.class);
//        System.out.println(userService.findUserByAccount("1234"));
//    }
}
