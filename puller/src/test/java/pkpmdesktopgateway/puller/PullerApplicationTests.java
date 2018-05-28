package pkpmdesktopgateway.puller;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pkpmdesktopgateway.puller.PullerMain.PullerBusiness;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PullerApplicationTests {
	
	@Resource
    private PullerBusiness pullerBusiness;

    @Test
    public void contextLoads() {
    	for(int i = 0; i < 12; i ++) {
    		pullerBusiness.updateJobStatus();
    	}
    	
    }

}
