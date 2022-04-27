package projekt_2022;

public class Technician extends Student {

	public Technician(String surname, String lastname,int ID, int day, int month, int year,double average) {
		super(surname, lastname,ID, day, month, year,average);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saySomeExtra() {
		// TODO Auto-generated method stub
		if(year%4==0) System.out.println("Rok mého narození je pøestupným.");
		else System.out.println("Rok mého narození není pøestupným.");		
	}
}
