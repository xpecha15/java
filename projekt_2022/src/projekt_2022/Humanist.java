package projekt_2022;

public class Humanist extends Student{

	public Humanist(String surname, String lastname,int id, int day, int month, int year, double average) {
		super(surname, lastname,id, day, month, year,average);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saySomeExtra() {
		// TODO Auto-generated method stub
		System.out.print("Jsem znamen�");
		switch(month) 
		{
			case 1 : System.out.print(" Ryba.");
				break;
			
			case 2 : System.out.print(" Vodn��.");
				break;
			
			case 3 : System.out.print(" Kozoroh.");
				break;
					
			case 4 : System.out.print(" St�elec.");
				break;
			
			case 5 : System.out.print(" �t�r.");
				break;
			
			case 6 : System.out.print(" V�hy.");
				break;
			
			case 7 : System.out.print(" Panna.");
				break;
			
			case 8 : System.out.print(" Lev.");
				break;
					
			case 9 : System.out.print(" Rak.");
				break;
			
			case 10 : System.out.print(" Bl�enec.");
				break;
			
			case 11 : System.out.print(" B�k.");
				break;
			
			case 12 : System.out.print(" Beran.");
				break;
		}
	}

}
