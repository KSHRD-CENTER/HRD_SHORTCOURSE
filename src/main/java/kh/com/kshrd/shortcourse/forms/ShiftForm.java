package kh.com.kshrd.shortcourse.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShiftForm {
	@JsonProperty("NAME")
	private String name;
	
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShiftForm [name=" + name + "]";
	}
	
	public static class UpdateShiftForm extends ShiftForm{
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
			return "UpdateShiftForm [id=" + id + ", status=" + status + "]";
		}
	}
}
