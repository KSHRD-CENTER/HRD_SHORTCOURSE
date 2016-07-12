package kh.com.kshrd.shortcourse.forms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerationForm {
	@JsonProperty("NAME")
	private String name;
	@JsonProperty("COURSE_TYPE")
	private Long courseType;
	@JsonProperty("IS_DEFAULT")
	private String isDefault;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the courseType
	 */
	public Long getCourseType() {
		return courseType;
	}
	/**
	 * @param courseType the courseType to set
	 */
	public void setCourseType(Long courseType) {
		this.courseType = courseType;
	}
	/**
	 * @return the isDefault
	 */
	public String getIsDefault() {
		return isDefault;
	}
	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GenerationForm [name=" + name + ", courseType=" + courseType + ", isDefault=" + isDefault + "]";
	}

	public static class UpdateGenerationForm extends GenerationForm{
		@JsonProperty("ID")
		private Long id;
		@JsonProperty("STATUS")
		private String status;
		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "UpdateGenerationForm [id=" + id + ", status=" + status + "]";
		}
	}
}
