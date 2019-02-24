package Genesis;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MarkingPeriod implements Serializable
{
	private HashMap<String, Course> courses;
	
	public MarkingPeriod()
	{
		courses = new HashMap<String, Course>();
	}
	
	public HashMap<String, Course> getHashMap()
	{
		return courses;
	}
	
	public boolean hasGrades()
	{
		for (Course c : courses.values())
		{
			if (c.hasGrades())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public double getWeightedGPA()
	{
		double gpa = 0;
		
		for (Course c : courses.values())
		{
			gpa += c.getWeightedGPAContribution();
		}
		
		return (int)((gpa / courses.size()) * 100) / 100;
	}
	
	public double getUnweightedGPA()
	{
		double gpa = 0;
		
		for (Course c : courses.values())
		{
			if (c.getRawGPAContribution() != Double.NaN)
			{
				gpa += c.getRawGPAContribution();
				
				System.out.println("Contribution - " + c.getRawGPAContribution());
			}
			
			System.out.println(gpa);
		}
		
		return (int)((gpa / courses.size()) * 100) / 100.0;
	}
	
	public void addToHashMap(String id, Course course)
	{
		courses.put(id, course);
	}
	
	public Course retrieveFromHashMap(String courseId)
	{
		return courses.get(courseId);
	}
	
	public void addAssignmentToCourse(String courseId, Assignment assignment)
	{
		courses.get(courseId).addAssignment(assignment);
	}
	
	public int getNumCourses()
	{
		return courses.size();
	}
	
	private void writeObject(final ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
	}
	 
	/**
	 * Deserialize this instance from input stream.
	 * 
	 * @param in Input Stream from which this instance is to be deserialized.
	 * @throws IOException Thrown if error occurs in deserialization.
	 * @throws ClassNotFoundException Thrown if expected class is not found.
	 */
	
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
	}

	private void readObjectNoData() throws ObjectStreamException
	{
		throw new InvalidObjectException("Stream data required");
	}
}
