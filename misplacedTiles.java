package programmingProject1;

import java.util.ArrayList;

public class misplacedTiles{
	
	static main goal=new main();
	static int[] goalArray=goal.sendArray();
	static ArrayList<misplacedNode> queList=new ArrayList<misplacedNode>();
	static ArrayList<misplacedNode> exploredList=new ArrayList<misplacedNode>();
	static int index=0;
	static ArrayList<misplacedNode> printList=new ArrayList<misplacedNode>();
	static int goalCheck=1;
	static int lowFun;
	static int nodesGen=0;
	
	//Generate Initial node
	public static void initialNode(int[] arraylowFun)
	{
		misplacedNode initial=new misplacedNode();
		initial.array=arraylowFun.clone();
		initial.g=0;
		initial.h=Heuristic(arraylowFun);
		initial.f=initial.g+initial.h;
		initial.parent=null;
		
		nodesGen++;
		queList.add(initial);
		
		expandTillGoal();
		if(goalCheck==0)
		{
			printFinalPath();
		}
	}
	//check goal state configuration of a node
	public static int goalCheck(int[] goalCheck)
	{
		return Heuristic(goalCheck);
	}
	//expand nodes till goal state is reached
	public static void expandTillGoal()
	{
		while(goalCheck!=0 && nodesGen!=50000)
		{
			checkAndExpand();
			if(goalCheck!=0)
			{
				genPoss(queList.get(index).array);	
				exploredList.add(queList.get(index));
				queList.remove(queList.get(index));	
			}
		}
		if(goalCheck == 0)
		{
			System.out.println("\nGoal State Found\n");
		}
		else
		{
			System.out.println("\nGoal State not found after exploring 50000 nodes");
		}
	}
	//look for node with lowest function value in que and do not expand if it is the goal node
	public static void checkAndExpand()
	{
		lowFun=queList.get(0).f;
		index=0;
		
		//look for node with lowest f value
		for(int count=1;count<queList.size();count++)
		{
			if(queList.get(count).f<lowFun)
			{
				lowFun=queList.get(count).f;
				index=count;
			}
		}
		goalCheck = goalCheck(queList.get(index).array);
		if(goalCheck==0)
		{
			return;
		}
		else
		{
			//check if node with same lowest f values is a goal state 
			for(int count=0;count<queList.size();count++)
			{
				if(queList.get(count).f==lowFun)
				{
					goalCheck = goalCheck(queList.get(count).array);
					if(goalCheck == 0)
					{
						index=count;
						break;
					}	
				}
			}	
		}
	}
	//Generate different configurations for the node to be expanded
	public static void genPoss(int[] arrayIn)
	{
		int position=10;
		
		for(int look=0;look<9;look=look+1)
		{
			if(arrayIn[look]==0)
			{
				position=look;
				break;
			}
		}
		if(position==1 || position==4 || position==7)
		{
			genArray(arrayIn,position,position+1);
			genArray(arrayIn,position,position-1);
		}
		else if(position==2 || position==5 || position==8)
		{
			genArray(arrayIn,position,position-1);
		}
		else
		{
			genArray(arrayIn,position,position+1);
		}
		if((position-3)>=0)
		{
			genArray(arrayIn,position,position-3);
		}
		if((position+3)<=8)
		{
			genArray(arrayIn,position,position+3);
		}
	}
	//Generate node and add to the que if it is not repeated
	public static void genArray(int[] storeArray,int pos1,int pos2)
	{
		misplacedNode checkParent;
		
		misplacedNode one=new misplacedNode();
		
		one.array=storeArray.clone();
		
		int temp = one.array[pos1];
		one.array[pos1]=one.array[pos2];
		one.array[pos2]=temp;

		one.parent=queList.get(index);
		one.h=Heuristic(one.array);
		one.g=1+one.parent.g;
		one.f=one.g+one.h;
		
		int return1=1;
		int tot;
		
		checkParent = queList.get(index);
		checkParent=checkParent.parent;
		
		//check if node is repeated in the parent node
		while(checkParent!=null)
		{
			return1=0;
			for(int k=0;k<9;k++)
			{
				if(checkParent.array[k]!=one.array[k])
				{
					return1=1;
					break;
				}
			}
			if(return1!=0)
			{
				checkParent=checkParent.parent;
			}
			else
			{
				checkParent=null;
			}
			
		}
		
		//check if same node configuration with higher f value exists in the que
		if(return1==1 && queList.size()>0)
		{	
			for(tot=0;tot<queList.size();tot++)
			{
				return1=0;
				for(int k=0;k<9;k++)
				{
					if(queList.get(tot).array[k]!=one.array[k])
					{
						return1=1;
						break;
					}
				}
				if(return1==0)
				{
					if(queList.get(tot).f>one.f)
					{
						queList.remove(tot);
						nodesGen++;
						queList.add(one);
					}
					break;
				}
			}
		}
		
		// check if the node exists in the explored list
		if(exploredList.size()>0 && return1==1)
		{
			
			for(tot=0;tot<exploredList.size();tot++)
			{
				return1=2;
				for(int k=0;k<9;k++)
				{
					if(exploredList.get(tot).array[k]!=one.array[k])
					{
						return1=1;
						break;
					}
				}
				if(return1==2)
				{
					break;
				}
			}
		}
		
		if(return1==1)
		{
			nodesGen++;
			queList.add(one);
		}
	}
	//manhattan heursitic calculation
	public static int Heuristic(int[] arrayHlowFun)
	{
		int h=0;
		
		for(int count=0;count<9;count=count+1)
		{
			if(arrayHlowFun[count]!=0)
			{
				if(arrayHlowFun[count]!=goalArray[count])
				{
					h=h+1;
				}
			}
		}
		return h;
	}
	//Fetch the solution path
	public static void printFinalPath()
	{
		misplacedNode fetchParent = queList.get(index);
		printList.add(fetchParent);
		
		while(fetchParent.parent != null)
		{
			fetchParent=fetchParent.parent;
			printList.add(fetchParent);
		}
		
		printResultArray();
	}
	//print the solution path in proper order from input to output
	public static void printResultArray()
	{
		int step=1;
		for(int count=printList.size()-1;count>=0;count--)
		{
			System.out.println("\nStep :"+step);
			goal.printArray(printList.get(count).array);
			step++;
			System.out.println("------------");
		}
		System.out.println("number of steps:"+printList.size());
		System.out.println("number of nodes explored:"+exploredList.size());
		System.out.println("number of nodes generated:"+nodesGen);
	}
}

