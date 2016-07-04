package kh.com.kshrd.shortcourse.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("CODE")
	public String code;
	@JsonProperty("MESSAGE")
	public String message;
		
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		switch(code){
			case StatusCode.SUCCESS:
				message = "SUCCESSFULLY...";
				break;
			case StatusCode.NOT_SUCCESS:
				message = "NOT SUCCESSFULLY...";
				break;
			default:
				message = "GO HOME YOU ARE DRUNK"; 
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Response [code=" + code + ", message=" + message + "]";
	}
	
}
