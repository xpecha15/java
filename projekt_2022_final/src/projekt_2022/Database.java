package projekt_2022;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
public class Database {
	public int idNow;
	protected boolean sqlOn, dataLoad = false;
	protected boolean update = false; //for updating sql after saving records
	SQL sql = new SQL();
	protected TreeMap<Integer, Humanist> humItem = new TreeMap<Integer,Humanist>();//map for humanists
	protected TreeMap<Integer, Technician> techItem = new TreeMap<Integer,Technician>();//map for technicians
	protected TreeMap<Integer, Combi> combiItem = new TreeMap<Integer,Combi>();//map for combis
	protected Set<Integer> identification = new HashSet<Integer>();//not allowing import of students with ID which already exists
	protected Set<Integer> IDException = new HashSet<Integer>(); //not allowing import of marks for not imported students
	
	//function for numerical round
		private static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}
	
	public void setTechnician(int id, String firstname, String lastname,int ID, int day,int month, int year)
	{
		if (techItem.put(id,new Technician(firstname,lastname,id,day,month,year,0.))==null)
		{
			if(sqlOn) 
			{
				sql.insertRecord(id,"t", firstname, lastname, day, month, year, 0.f);
			}
			
			System.out.println(firstname + " added with ID: " + id + "\n");			
			identification.add(id);
			idNow++;
		}
		else 
		{
			System.out.println("error : student not added");
		}
	}
	
	public void setHumanist(int id, String firstname, String lastname,int ID, int day,int month, int year)
	{
		if (humItem.put(id,new Humanist(firstname,lastname,id, day,month,year,0.))==null)
		{
			if(sqlOn) 
			{
				sql.insertRecord(id,"h", firstname, lastname, day, month, year, 0.f);
			}

			System.out.println(firstname + " added with ID: " + id + "\n");
			identification.add(id);
			idNow++;
		}
		else 
		{
			System.out.println("error : student not added");
		}
	}
	
	public void setCombi(int id, String firstname, String lastname,int ID, int day,int month, int year)
	{
		if (combiItem.put(id,new Combi(firstname,lastname,id,day,month,year,0.))==null)
		{
			if(sqlOn) 
			{
				sql.insertRecord(id,"c", firstname, lastname, day, month, year, 0.f);
			}

			System.out.println(firstname + " added with ID: " + id + "\n");
			identification.add(id);
			idNow++;
		}
		else 
		{
			System.out.println("error : student not added");
		}
	}
	
	public void deleteStudent(int id) throws IOException
	{
		if(techItem.containsKey(id)) {
			System.out.println("ID: " + techItem.get(id).getID() + " " + techItem.get(id).getFirstname() + " " + techItem.get(id).getLastname() + " " + techItem.get(id).getBirth() + " was deleted");
			techItem.remove(id);
		}
		else if(humItem.containsKey(id)) {
			System.out.println("ID: " + humItem.get(id).getID() + " " + humItem.get(id).getFirstname() + " " + humItem.get(id).getLastname() + " " + humItem.get(id).getBirth() + " was deleted");
			humItem.remove(id);
		}
		else if(combiItem.containsKey(id)){
			System.out.println("ID: " + combiItem.get(id).getID() + " " + combiItem.get(id).getFirstname() + " " + combiItem.get(id).getLastname() + " " + combiItem.get(id).getBirth() + " was deleted");
			combiItem.remove(id);
		}
		else {System.err.println("error : ID doesn't exist");}
		writeToFile();
		
		if(sqlOn)
		{
			sql.delete(id);			
		}
	}
	
	public void showSkill(int id) {
		if(techItem.containsKey(id)) {
			System.out.println(techItem.get(id).getFirstname() + " says: ");
			techItem.get(id).saySomeExtra();
		}
		else if(humItem.containsKey(id)) {
			System.out.println(humItem.get(id).getFirstname() + " says: ");
			humItem.get(id).saySomeExtra();
		}
		else if(combiItem.containsKey(id)){
			System.out.println(combiItem.get(id).getFirstname() + " says: ");
			combiItem.get(id).saySomeExtra();
		}
		else {System.err.println("error : ID doesn't exist");}
	}
	
	public void getStudent(int id) {
		if(techItem.containsKey(id)) {
			System.out.println("ID: " + techItem.get(id).getID() + " " + techItem.get(id).getFirstname() + " " + techItem.get(id).getLastname() + " " + techItem.get(id).getBirth() + " average mark: " + techItem.get(id).getAvgMark());
		}
		else if(humItem.containsKey(id)) {
			System.out.println("ID: " + humItem.get(id).getID() + " " + humItem.get(id).getFirstname() + " " + humItem.get(id).getLastname() + " " + humItem.get(id).getBirth() + " average mark: " + humItem.get(id).getAvgMark());
		}
		else if(combiItem.containsKey(id)){
			System.out.println("ID: " + combiItem.get(id).getID() + " " + combiItem.get(id).getFirstname() + " " + combiItem.get(id).getLastname() + " " + combiItem.get(id).getBirth() + " average mark: " + combiItem.get(id).getAvgMark());
		}
		else {System.err.println("error : ID doesn't exist");}
	}
	

	public void setMark(int id, int mark) {
		boolean error = false;
		if(techItem.containsKey(id)) {	
		if(techItem.get(id).setMark(mark))
			System.out.println("mark " + mark + " added to " + techItem.get(id).getLastname() + " with ID: " + id);
		}
		else if(humItem.containsKey(id)) {
			if(humItem.get(id).setMark(mark))
			System.out.println("mark " + mark + " added to " + humItem.get(id).getLastname() + " with ID: " + id);
		}
		else if(combiItem.containsKey(id)) {
			if(combiItem.get(id).setMark(mark))
			System.out.println("mark " + mark + " added to " + combiItem.get(id).getLastname() + " with ID: " + id);
		}
		else {System.err.println("error : ID doesn't exist");error = true;}
		if(sqlOn && update==true && !error) System.out.println("SQL: to save each student's avg mark you need to update query (15) after your're done adding marks");
	}
	
	public void avgSum(String field) {
		double sum = 0, avgSum = 0;
		int size = techItem.size() + humItem.size() + combiItem.size();
	try {	
			if(field.equals("t")) {
				for(int i = 0; i < size; i++) {
					if(techItem.containsKey(i)) sum += techItem.get(i).getAvgMark();
				}
				avgSum = sum/techItem.size();
			}
			else if(field.equals("h")) {
				for(int i = 0; i < size; i++) {
					if(humItem.containsKey(i)) sum += humItem.get(i).getAvgMark();
				}
				avgSum = sum/humItem.size();
			}
			else if(field.equals("c")) {
				for(int i = 0; i < size; i++) {
					if(combiItem.containsKey(i)) sum += combiItem.get(i).getAvgMark();
				}
				avgSum = sum/combiItem.size();
			}
			else {System.err.println("error : wrong input");}
			System.out.println("Average mark from selected specialization is " + round(avgSum,2));
			
		} catch(NoSuchElementException e) {System.err.println("error : average mark is NaN");}
		  catch (NullPointerException e) {}
	}
	
	public void sizeOfSpec() {
		
			System.out.println("There are " + techItem.size() + " technician students");
			System.out.println("There are " + humItem.size() + " humanist students");
			System.out.println("There are " + combiItem.size() + " combined teaching students");
			System.out.println("\nSum: " + (techItem.size()+humItem.size()+combiItem.size()));
	}
	
	public void listStudents()
	{	
		System.out.println("Technician students:\n");
		Set<Integer> keysTech = techItem.keySet();
		for (Integer id:keysTech )
		System.out.println("ID: " + techItem.get(id).getID() + " " + techItem.get(id).getFirstname() + " " + techItem.get(id).getLastname() + " " + techItem.get(id).getBirth() + " average mark: " + techItem.get(id).getAvgMark());

		System.out.println("\nHumanist students:\n");
		Set<Integer> keysHum = humItem.keySet();
		for (Integer id:keysHum )
		System.out.println("ID: " + humItem.get(id).getID() + " " + humItem.get(id).getFirstname() + " " + humItem.get(id).getLastname() + " " + humItem.get(id).getBirth() + " average mark: " + humItem.get(id).getAvgMark());
	
		System.out.println("\nCombined teaching students:\n");
		Set<Integer> keysCom = combiItem.keySet();
		for (Integer id:keysCom )
		System.out.println("ID: " + combiItem.get(id).getID() + " " + combiItem.get(id).getFirstname() + " " + combiItem.get(id).getLastname() + " " + combiItem.get(id).getBirth() + " average mark: " + combiItem.get(id).getAvgMark());
	}
	
	public void sortBy() {
		techItem.entrySet().stream()
        .sorted(Comparator.comparing(o -> o.getValue().getLastname()))
        .forEach(System.out::println);
	}
	
	public void writeToFile() throws IOException {
		int i;
		int arraySize = idNow+1;
		BufferedWriter data = new BufferedWriter(new FileWriter("root/data.txt"));
		BufferedWriter marks = new BufferedWriter(new FileWriter("root/marks.txt"));
		if(dataLoad || techItem.size() > 0 || humItem.size() > 0 || combiItem.size() > 0) {
			for (i = 0; i <= arraySize; i++) {
			try {
				if(techItem.containsKey(i)) {
				data.write("t " + techItem.get(i).getID() + " " + techItem.get(i).getFirstname()+ " " + techItem.get(i).getLastname()+ " " + techItem.get(i).getDay()+ " "+ " " + techItem.get(i).getMonth()+ " " + techItem.get(i).getYear() + " " + techItem.get(i).getAvgMark() + "\n");	
				marks.write(techItem.get(i).getID() +" "+ techItem.get(i).exportMarks() + "\n");
				}
				if(humItem.containsKey(i)) {
				data.write("h " + humItem.get(i).getID() + " " + humItem.get(i).getFirstname()+ " " + humItem.get(i).getLastname()+ " " + humItem.get(i).getDay()+ " "+ " " + humItem.get(i).getMonth()+ " " + humItem.get(i).getYear() + " " + humItem.get(i).getAvgMark() + "\n");	
				marks.write(humItem.get(i).getID() +" "+ humItem.get(i).exportMarks() + "\n");
				}
				if(combiItem.containsKey(i)) {
				data.write("c " + combiItem.get(i).getID() + " " + combiItem.get(i).getFirstname()+ " " + combiItem.get(i).getLastname()+ " " + combiItem.get(i).getDay()+ " "+ " " + combiItem.get(i).getMonth()+ " " + combiItem.get(i).getYear()+ " " + combiItem.get(i).getAvgMark() + "\n");	
				marks.write(combiItem.get(i).getID() +" "+ combiItem.get(i).exportMarks() + "\n");
				}
				}catch(NullPointerException e) {System.err.println("error : null pointer exception at ID " + i);}			
			}
			System.out.print("Students saved to root/data.txt\nMarks saved to root/marks.txt");
		} else System.err.println("error : cannot save because no data was loaded");
		marks.close();
		data.close();
	}
	
	public void importStudent() {
	 Scanner scStudent = null;
	 int line = 0,point = 0,error = 0;
	 String[] read = new String[8];
	 
		    try {
		        scStudent = new Scanner(new File("root/data.txt"));
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();  
		    }
		    while (scStudent.hasNextLine()) {
		        Scanner s2 = new Scanner(scStudent.nextLine());
		        while (s2.hasNext()) {
		            read[point] = s2.next();
		            point++;
		        }
		        int ID = Integer.parseInt(read[1]);
		        if(read[0].equals("t") && !identification.contains(ID)) {
			        	techItem.put(ID,new Technician(read[2],read[3],ID,Integer.parseInt(read[4]),Integer.parseInt(read[5]),Integer.parseInt(read[6]),Double.parseDouble(read[7])));
			        	System.out.println("student "+ read[2] + " loaded with ID: "+ ID);
			        	identification.add(Integer.parseInt(read[1]));
			        	idNow = ID+1;
	            }
		        
		        else if(read[0].equals("h") && !identification.contains(Integer.parseInt(read[1]))) {
			        	humItem.put(ID,new Humanist(read[2],read[3],ID,Integer.parseInt(read[4]),Integer.parseInt(read[5]),Integer.parseInt(read[6]),Double.parseDouble(read[7])));
			        	System.out.println("student "+ read[2] + " loaded with ID: "+ ID);
			        	identification.add(Integer.parseInt(read[1]));
			        	idNow = ID+1;       	
	            }
		       
		        else if(read[0].equals("c") && !identification.contains(Integer.parseInt(read[1]))) {
			        	combiItem.put(ID,new Combi(read[2],read[3],ID,Integer.parseInt(read[4]),Integer.parseInt(read[5]),Integer.parseInt(read[6]),Double.parseDouble(read[7])));
			        	System.out.println("student "+ read[2] + " loaded with ID: "+ ID);
			        	identification.add(Integer.parseInt(read[1]));
			        	idNow = ID+1;
	            }
		        else
		        {
		        	System.err.print("error : ID duplicate in root/data.txt at line " + (line+1) + "\n");
		        	IDException.add(Integer.parseInt(read[1]));
	        		error++;
		        }
		        point = 0;
		        line++;
		    }
		    System.out.println("\nStudents import exited with " + error + " errors\n");
		    importMarks();//calling import of marks
		    listStudents();
		    update = true;
		    dataLoad = true;
		   	    
	}
	
	public void importMarks() {
		 Scanner scMarks = null;
		 int point = 0,id,markCnt = 0;
		 String[] read = new String[100];
		 
			    try {
			        scMarks = new Scanner(new File("root/marks.txt"));
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();  
			    }
			    while (scMarks.hasNextLine()) {
			        Scanner s2 = new Scanner(scMarks.nextLine());
			        while (s2.hasNext()) {
			        	read[point] = s2.next();
			        	point++;
			        }
			        id = Integer.parseInt(read[0]);
			        if(!IDException.contains(id))//if student was not imported due to ID duplication, his marks will not be imported
			        {
				        for(int i = 2; i < point; i++)
			        	{
			        		setMark(id,Integer.parseInt(read[i]));
			        		markCnt++;
			        	}
			        }
			        else
			        {
			        	System.err.println("marks for ID " + id + " not imported due to ID duplication");
			        }
			        point = 0;			        
			    } 
			    System.out.println("\n" + markCnt + " marks imported\n");
	}
	//SQL
	
	public void saveAll() throws IOException {
		int i;
		int arraySize = idNow;
		Double avg;
		if(dataLoad) {
			for (i = 0; i < arraySize; i++) {
				if(techItem.containsKey(i)) {
					avg = techItem.get(i).getAvgMark();
					sql.insertRecord(techItem.get(i).getID(),"t",techItem.get(i).getFirstname(),techItem.get(i).getLastname(),techItem.get(i).getDay(),techItem.get(i).getMonth(),techItem.get(i).getYear(),avg.floatValue());
				}
				else if(humItem.containsKey(i)) {
					avg = humItem.get(i).getAvgMark();
					sql.insertRecord(humItem.get(i).getID(),"h",humItem.get(i).getFirstname(),humItem.get(i).getLastname(),humItem.get(i).getDay(),humItem.get(i).getMonth(),humItem.get(i).getYear(),avg.floatValue());
				}
				else if(combiItem.containsKey(i)) {
					avg = combiItem.get(i).getAvgMark();
					sql.insertRecord(combiItem.get(i).getID(),"c",combiItem.get(i).getFirstname(),combiItem.get(i).getLastname(),combiItem.get(i).getDay(),combiItem.get(i).getMonth(),combiItem.get(i).getYear(),avg.floatValue());
				} 
				else System.err.println("error : ID " + i + " doesn't exist");
			}
			update = true;
		} else System.err.println("error : cannot save because no data was loaded");		
		}
	
	public void updateQuery() throws IOException {
		int i;
		int arraySize = idNow;
		Double avg;
		BufferedWriter bw = new BufferedWriter(new FileWriter("root/marks.txt"));
		if(dataLoad || techItem.size() > 0 || humItem.size() > 0 || combiItem.size() > 0) { //checks if any data was loaded either from SQL or from local txt, if not data cannot be saved
			for (i = 0; i < arraySize; i++) {
				if(techItem.containsKey(i)) {
					avg = techItem.get(i).getAvgMark();
					sql.update(techItem.get(i).getID(),"t",techItem.get(i).getFirstname(),techItem.get(i).getLastname(),techItem.get(i).getDay(),techItem.get(i).getMonth(),techItem.get(i).getYear(),avg.floatValue());
					bw.write(techItem.get(i).getID() +" "+ techItem.get(i).exportMarks() + "\n");
				}
				else if(humItem.containsKey(i)) {
					avg = humItem.get(i).getAvgMark();
					sql.update(humItem.get(i).getID(),"h",humItem.get(i).getFirstname(),humItem.get(i).getLastname(),humItem.get(i).getDay(),humItem.get(i).getMonth(),humItem.get(i).getYear(),avg.floatValue());
					bw.write(humItem.get(i).getID() +" "+ humItem.get(i).exportMarks() + "\n");
				}
				else if(combiItem.containsKey(i)) {
					avg = combiItem.get(i).getAvgMark();
					sql.update(combiItem.get(i).getID(),"c",combiItem.get(i).getFirstname(),combiItem.get(i).getLastname(),combiItem.get(i).getDay(),combiItem.get(i).getMonth(),combiItem.get(i).getYear(),avg.floatValue());
					bw.write(combiItem.get(i).getID() +" "+ combiItem.get(i).exportMarks() + "\n");
				}
				else System.err.println("SQL: row: " + i + " status: Row with ID "+ i + " doesn't exist");
			}
		}
		else System.err.println("error : cannot update queary because no data was loaded");
		bw.close();
	}

}
