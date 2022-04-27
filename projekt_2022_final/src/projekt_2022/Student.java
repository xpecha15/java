package projekt_2022;

import java.util.ArrayList;
import java.util.OptionalDouble;

public abstract class Student {
	protected int id, day,month, year;
	private String firstname,lastname;
	public ArrayList<Integer> mark = new ArrayList<Integer>();
	private double average;
	
	public Student(String firstname, String lastname,int id, int day, int month, int year, double average) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.id = id;
		this.day = day;
		this.month = month;
		this.year = year;
		this.average = average;
	}
	//function for numerical round
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public boolean setMark(int mark) {
		if(mark >= 1 && mark <= 5 && this.mark.size() < 100) 
		{
			this.mark.add(mark); 
			return true;
		}
		else if(this.mark.size()==100)
		{
			System.err.println("error : maximum mark count achieved (100)");
			return false;
		}
		else 
		{
			System.err.println("error : invalid mark (1 - 5 allowed)");
			return false;
		}
	}
	
	public int getID() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public String getBirth() {
	//one string
		return day + "." + month + "." + year;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public double getAvgMark() {
	
		OptionalDouble x = mark.stream().mapToDouble(a -> a).average();
		if(x.isEmpty())
		{
			return 0.;
		}
		else 
		{
			average = x.getAsDouble();
			return round(average,2);
		}
	}
	
	//EXPORT / IMPORT to txt
	public String exportMarks() {
	//for student export info
	String export = "@ ";
	
		for(int i = 0; i < mark.size(); i++) 
		{
			export += mark.get(i) + " ";
		}
		
		return export;
	}
	
	//student's ability
	public abstract void saySomeExtra();

}
