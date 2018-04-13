package pkpmdesktopgateway.puller.PullerMain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class JobDetail implements Serializable {
    private String id;
    private String jobId;
    private String projectId;
    private Integer userId;
    private Integer subsId;
    private Integer adId;
    private String userName;
    private String desktopId;
    private String computerName;
    private String operatorType;
    private String status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime finishTime;

    private Integer isFinished;
}