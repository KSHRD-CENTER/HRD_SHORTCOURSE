package kh.com.kshrd.shortcourse.models;
	
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kh.com.kshrd.shortcourse.utilities.Pagination;

public class ResponseList<T> extends Response {

	@JsonProperty("DATA")
	public List<T> data;
	
	@JsonProperty("PAGINATION")
	public Pagination pagination;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	} 
	
}
