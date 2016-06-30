package kh.com.kshrd.shortcourse.models;
	
import com.fasterxml.jackson.annotation.JsonProperty;

import kh.com.kshrd.shortcourse.utilities.Pagination;

public class ResponseModel<T> extends Response {

	@JsonProperty("DATA")
	public T data;
	
	@JsonProperty("PAGINATION")
	public Pagination pagination; 
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	@Override
	public String toString() {
		return "ResponseModel [data=" + data + ", pagination=" + pagination + "]";
	}
}
