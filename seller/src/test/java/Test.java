import com.zq.seller.service.VerifyService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VerifyService.class)
@EnableAutoConfiguration
public class Test {
    @Autowired
    VerifyService verifyService;

    @org.junit.Test
    public void test() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-30");
        File file = verifyService.makeVerificationFile("10000",date);
        System.out.println(file.getAbsolutePath());
    }
}
