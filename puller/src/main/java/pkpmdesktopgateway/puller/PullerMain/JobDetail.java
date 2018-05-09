package pkpmdesktopgateway.puller.PullerMain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class JobDetail implements Serializable {
	
	private Integer id;
    private String jobId;
    private String projectId;
    private Integer userId;
    private Long subsId;
    private Integer adId;
    private String userName;
    private String desktopId;
    private String computerName;
    private String operatorType;
    private String areaCode;
    private String status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime finishTime;

    private Integer isFinished;

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @return  
	 * @see java.lang.Object#toString()  
	 */  
	@Override
	public String toString() {
		return "JobDetail [id=" + id + ", jobId=" + jobId + ", projectId=" + projectId + ", userId=" + userId
				+ ", subsId=" + subsId + ", adId=" + adId + ", userName=" + userName + ", desktopId=" + desktopId
				+ ", computerName=" + computerName + ", operatorType=" + operatorType + ", areaCode=" + areaCode
				+ ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime + ", finishTime="
				+ finishTime + ", isFinished=" + isFinished + "]";
	}
}