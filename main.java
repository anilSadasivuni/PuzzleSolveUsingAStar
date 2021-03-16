package programmingProject1;
import java.util.Scanner;

public class main {

	static int[] arrIn=new int[9];
	static int[] arrOut=new int[9];
	
	public static void main(String[] args) 
	{	
//		//input state
		inputArray(arrIn,"in");
//		//Goal state
		inputArray(arrOut,"out");
		
		System.out.println(" \nA * using manhattan heuristic\n");
		manhattan manHeuGoal=new manhattan();
		manHeuGoal.initialNode(arrIn);
		
		System.out.println(" \nA * using misplaced tiles heuristic\n");
		misplacedTiles misplacedHeu=new misplacedTiles();
		misplacedHeu.initialNode(arrIn);
		
	}
	//input array reads input from the user
	public static void inputArray(int[] array,String comp)
	{
		
		Scanner in=new Scanner(System.in);
		
		if(comp=="in")
		{
			System.out.println("Enter the initial state of the 8 puzzle");
		}
		else
		{
			System.out.println("Enter the final state of the 8 puzzle");
		}
		for(int count=0;count<9;count++)
		{
			array[count]=in.nextInt();
		}
	}
	//print array in 8 puzzle format
	public static void printArray(int[] array)
	{
		System.out.print("\n");
		System.out.print(array[0]+" ");
		System.out.print(array[1]+" ");
		System.out.print(array[2]+"\n");
		System.out.print(array[3]+" ");
		System.out.print(array[4]+" ");
		System.out.print(array[5]+"\n");
		System.out.print(array[6]+" ");
		System.out.print(array[7]+" ");
		System.out.print(array[8]);
		System.out.print("\n");
	}
	//To send goal state to another class
	public static int[] sendArray()
	{
		return arrOut.clone();
	}
}
