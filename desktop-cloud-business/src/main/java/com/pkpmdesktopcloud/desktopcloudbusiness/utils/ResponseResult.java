package com.pkpmdesktopcloud.desktopcloudbusiness.utils;

public class ResponseResult {
	    private String count="";  
	    /** 响应数据 */  
	    private Object data;  
	    /** 响应分页 */  
	    /** 响应状态 */  
	    private Integer status ;  
	    /** 响应消息 */  
	    private String message;  
	  
	    public ResponseResult() {}  
	  
	    public ResponseResult(Object data) {  
	        this.data = data;  
	    }  
	    public ResponseResult(String message, Integer status) {  
	        this.set(message, status);  
	    }  
	  
	    public ResponseResult(String message, Integer status,String count, Object data) {  
	        this.set(message, status);  
	        this.data = data;  
	        this.count=count;  
	    }  
	  
	    public void set(String message, Integer status) {  
	        this.status = status;  
	        this.message = message;  
	        this.count="";  
	        this.data="";  
	    }  
	    
	    public void set(String message, Integer status, Object data) {
			this.status = status;
			this.message = message;
			this.data = data;
		}
	  
	    public void set(String message, Integer status,String count, Object data) {  
	        this.status = status;  
	        this.message = message;  
	        this.data = data;  
	        this.count=count;  
	    }  
	  
	    public Integer getStatus() {  
	        return status;  
	    }  
	  
	    public String getMessage() {  
	        return message;  
	    }  
	  
	    public void setMessage(String message) {  
	        this.message = message;  
	    }  
	  
	    public Object getData() {  
	        return data;  
	    }  
	  
	    public void setData(Object data) {  
	        this.data = data;  
	    }  
	  
}
