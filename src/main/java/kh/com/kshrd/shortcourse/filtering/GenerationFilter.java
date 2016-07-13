package kh.com.kshrd.shortcourse.filtering;

public class GenerationFilter {
	private String name;
	private String courseId;
	
	public GenerationFilter(){
		this.name = "";
		this.courseId="";
	}
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
	 * @return the cousetId
	 */
	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GenerationFilter [name=" + name + ", courseId=" + courseId + ", getName()=" + getName()
				+ ", getCourseId()=" + getCourseId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
