package projekt_2022;
/**
 * VUT FEKT BPC-PC2T student project 2022
 * @author Vilem Pechacek
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Vilem Pechacek
 * ID: 230626, FEKT VUT 2022
 * 
 * Strucny popis programu je obsazen v souboru dokumentace.txt
 * 
 * Databaze se automaticky vytvori pri prvnim spusteni programu (prazdna) (indikace #studentsDB.db created)
 * Pri jakemkoliv dalsim spusteni se provede test jeji existence a konektivity (indikace #studentsDB.db responding),
 * pokud neexistuje, program ji znovu vytvori.
 * Nekolik studentu uz je ulozenych v textovem souboru root/data.txt a jejich znamky v marks.txt, lze je importovat (prikaz 10)
 * Synchronizace se SQL je defaultne nastavena na false (indikace #default SQL sync: false, lze zmenit prikazem 101), viz dokumentace
 * 
 */
public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean run = true, sqlOn = false;
		String firstname,lastname,cursorStr;
		int command,day,month,year,cursorInt;
		Scanner sc = new Scanner(System.in);
		
		Database dat = new Database();
		SQL sql = new SQL();
		
		//startup database check
		File database = new File("studentsDB.db");
		if(database.exists())
		{
			if(sql.connect()) System.out.print("#studentsDB.db responding");
			else System.err.print("studentsDB.db not responding");
			sql.disconnect();
		}
		else {sql.connect(); sql.createTable();System.out.print("#studentsDB.db created");} //creation of database if not exist
		System.out.print(" #default SQL sync: " + sqlOn + "\n");
		//Menu
		while(run) 
		{
			System.out.println("\n------------------------------------------- LOCAL ------------------------------------------------");
			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("| 1 - add student | 2 - add mark   | 3 - delete student | 4 - search for student | 5 - show skill|");
			System.out.println("| 6 - local data  | 7 - spec marks | 8 - students sum   | 9 - save   | 10 - load | 0 - exit      |  ");
			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("-------------------------------------------  SQL -------------------------------------------------");
			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("| 11 -  load     | 12 - save    | 13 - database  | 14 - delete student  | 15 - update query      |");
			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("| Note: to allow/forbid synchronizing local files with SQL type '101' currently: " + sqlOn + "           |");
			System.out.println("--------------------------------------------------------------------------------------------------\n");
			firstname = null;
			lastname = null;
			day = 0;
			month = 0;
			year = 0;
			
			if(!sc.hasNextInt())
			{
				System.err.println("error : wrong input");
				sc.next();
			}
			else
			{
				command = Integer.parseInt(sc.next());
				switch(command) {
				//Add student
				case 1 : System.out.println("\nAdd new student");
						 System.out.println("--------------------------------------------------------------------------------------------------");
						 System.out.print("firstname: "); firstname = sc.next(); sc.nextLine();
						 System.out.print("lastname: "); lastname = sc.next(); sc.nextLine();
				
					while(true) {
						System.out.print("day of birth: ");
						if(sc.hasNextInt()) {
							day = sc.nextInt();
							sc.nextLine();
							if(day < 1 || day > 31) System.err.println("invalid day");
							else break;
						} else {System.err.println("error : wrong input");sc.next();}
					}
					while(true) {	
					System.out.print("month of birth: ");
						if(sc.hasNextInt()) {
							month = sc.nextInt();
							sc.nextLine();
							if(month < 1 || month > 12) System.err.println("invalid month");
							else break;
						} else {System.err.println("error : wrong input");sc.next();}
					}
					while(true) {
						System.out.print("year of birth: ");
						if(sc.hasNextInt()) {
							year = sc.nextInt();
							sc.nextLine();
							if(year < 1930 || year > 2005) System.err.println("invalid year");
							else break;
						} else {System.err.println("error : wrong input");sc.nextLine();}
					}
					
					while(true) {
						System.out.println("\nchoose student group:\n't' - technician\n'h' - humanist\n'c' - combi");
						cursorStr = sc.next();
						
							if(cursorStr.equals("t")) 
							{
								dat.setTechnician(dat.idNow, firstname, lastname,dat.idNow, day, month, year);
								break;
							}
							else if(cursorStr.equals("h")) {
								dat.setHumanist(dat.idNow, firstname, lastname,dat.idNow, day, month, year);	
								break;
							}
							else if(cursorStr.equals("c")) {
								dat.setCombi(dat.idNow, firstname, lastname,dat.idNow, day, month, year);
								break;
							}
							else {System.err.println("error : wrong input");}
					}
				break;
				//add marks
				case 2 : System.out.println("\nAdd mark");
						 System.out.println("--------------------------------------------------------------------------------------------------");
				int mark;
				while(true) {
						 System.out.print("ID: ");
						 if(sc.hasNextInt()) {
						 cursorInt = sc.nextInt();
						 sc.nextLine();
						 break;
						 }
						 else {System.err.println("error : wrong input");sc.next();}
				}
				while(true) {
						 System.out.print("mark: ");
						 if(sc.hasNextInt()) {
						 mark = sc.nextInt();
						 sc.nextLine();
						 break;
						 }
						 else {System.err.println("error : wrong input");sc.next();}
				}
						 dat.setMark(cursorInt, mark);
					break;
				//delete student
				case 3 : System.out.println("\nDelete student");
						 System.out.println("--------------------------------------------------------------------------------------------------");
						 System.out.print("ID: ");
						 cursorInt = sc.nextInt();
					try {
						dat.deleteStudent(cursorInt);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					break;
				//search for student		 
				case 4 : System.out.println("\nSearch student");
						 System.out.println("--------------------------------------------------------------------------------------------------");
						 System.out.print("ID: ");
						 cursorInt = sc.nextInt();
						 dat.getStudent(cursorInt);
					break;
				//show skill
				case 5 : System.out.println("\nShow skill");
						 System.out.println("--------------------------------------------------------------------------------------------------");
						 System.out.print("ID: ");
						 cursorInt = sc.nextInt();
						 dat.showSkill(cursorInt);
					break;
				//list of all students
				case 6 : System.out.println("\nList of students");
						 System.out.println("--------------------------------------------------------------------------------------------------");
						 dat.listStudents();
					break;
				//get average mark	
				case 7 : System.out.println("\nGroup avg mark");
						 System.out.println("--------------------------------------------------------------------------------------------------");
						 System.out.println("Type 't' for technicians\n     'h' for humanists\n     'c' for combined students");
						 cursorStr = sc.next();
						 dat.avgSum(cursorStr);
					break;
				//number of students in groups
				case 8 : System.out.println("\nGroup numbers");
						 System.out.println("--------------------------------------------------------------------------------------------------");
						 dat.sizeOfSpec();
					break;
				//save database to txt
				case 9 : 
					try {
						dat.writeToFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				//load database from txt
				case 10 : dat.importStudent();
					break;
				//exit
				case 0 : run = false; 
						 if(sqlOn) sql.disconnect();
						 System.out.println("#programme exited");
					break;
				//import SQL
				case 11 : try {
						sql.importSQL();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				dat.importStudent();
				dat.update = true;
					break;
				//save to SQL
				case 12 : 
						try {
							dat.saveAll();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
					break;
				//list of SQL rows
				case 13 : sql.selectAll();
					break;
				//delete in SQL
				case 14 :
					System.out.println("\nDelete student");
					 System.out.println("--------------------------------------------------------------------------------------------------");
					System.out.print("ID: ");
					while(true) {
						if(sc.hasNextInt()) 
						{
						cursorInt = sc.nextInt();
						sql.delete(cursorInt);
						break;
						}
						else System.err.println("error : wrong input");
						sc.next();
					}
					break;
				//update SQL query
				case 15 : if(dat.update)
					try {
						dat.updateQuery();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					else System.err.println("cannot update!");
					break;
				//SQL sync toggle
				case 101 : if(sqlOn) 
				{
					sqlOn = false;
					dat.sqlOn = false;
					System.out.print("#SQL sync disabled\n");
				} 
				else 
				{
					sqlOn = true;
					dat.sqlOn = true;
					System.out.print("#SQL sync enabled\n");
				}
					break;
				}
				
			}
		}		
	}
}
