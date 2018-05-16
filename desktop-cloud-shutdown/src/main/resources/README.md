##使用说明
**内容**  
华为云桌面自动关机脚本  
关机条件如下：
- _白名单不处理_
- _状态为Connected不关机_
- _12小时内存在小于5分钟登陆记录不关机_  
  
  
 
**参数说明**  
可以手动配置参数，指定某一项目id；
否则使用自动配置参数，处理数据库中所有项目；  
java -jar **.jar   
 项目Id必填
-DprojectId=58cd927978ff4f85b16ee64468da0e53 
 用户登陆记录查询间隔
-DrecordQueryInterval=12;      
 断开超过5分钟则关机
-DshutdownOver=5;  
核心线程数
-DcorePoolSize=2;
最大线程数  
-DmaximumPoolSize=5;
每线程处理任务数  
-DtaskPerThread=5;
连接超时  
-DhttpConnectionTimeout=60000;