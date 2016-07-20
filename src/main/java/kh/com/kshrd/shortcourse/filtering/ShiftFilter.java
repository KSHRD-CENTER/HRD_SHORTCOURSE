package kh.com.kshrd.shortcourse.filtering;

public class ShiftFilter {
	private String name;
	private String courseTypeId;
	private String generationId;
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
	 * @return the courseTypeId
	 */
	public String getCourseTypeId() {
		return courseTypeId;
	}
	/**
	 * @param courseTypeId the courseTypeId to set
	 */
	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	/**
	 * @return the generationId
	 */
	public String getGenerationId() {
		return generationId;
	}
	/**
	 * @param generationId the generationId to set
	 */
	public void setGenerationId(String generationId) {
		this.generationId = generationId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShiftFilter [name=" + name + ", courseTypeId=" + courseTypeId + ", generationId=" + generationId + "]";
	}
}
