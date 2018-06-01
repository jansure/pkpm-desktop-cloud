package pkpmdesktopgateway.puller;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desktop.constant.SubscriptionStatusEnum;

import pkpmdesktopgateway.puller.PullerMain.JobDetail;
import pkpmdesktopgateway.puller.PullerMain.PullerBusiness;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PullerApplicationTests {
	
	@Resource
    private PullerBusiness pullerBusiness;
	
	

	@Test
    public void updateSubTest() {
		JobDetail detail = new JobDetail();
		detail.setSubsId(152387788450129l);
		detail.setStatus(SubscriptionStatusEnum.VALID.toString());
		detail.setDesktopId("12345678");
		pullerBusiness.updateCloudSubscription(detail);
    	
    }
	
    @Test
    public void contextLoads() {
    	//for(int i = 0; i < 12; i ++) {
    		pullerBusiness.updateJobStatus();
    	//}
    	
    }

}
