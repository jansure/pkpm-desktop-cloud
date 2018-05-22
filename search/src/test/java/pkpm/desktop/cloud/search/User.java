package pkpm.desktop.cloud.search;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.searchbox.annotations.JestId;

public class User {  
  
    @JestId  
    private Integer id;  
    private String name;  
    private Date birth;  
    public Integer getId() {  
        return id;  
    }  
    public void setId(Integer id) {  
        this.id = id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public Date getBirth() { 
    	
        return birth;  
    }  
    
    public String getBirthStr() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(birth);
    }
    public void setBirth(Date birth) {  
        this.birth = birth;  
    }  
    public User() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
    public User(Integer id, String name, Date birth) {  
        super();  
        this.id = id;  
        this.name = name;  
        this.birth = birth;  
    }  
    @Override  
    public String toString() {  
        return "User [id=" + id + ", name=" + name + ", birth=" + getBirthStr() + "]";  
    }  
}  