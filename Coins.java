import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Coins
{
	static int lhs = 0;
	static int ls = 1;
	static int hs = 2;
	static int s = 3;
	static int t = 4;

	static int uncertainty = 12;
	static int limit = 3; 
	
	// 设置一个结点记录值，本来想用一个int值就行了，不过我要add到nodeRecord集合后面，必须add整型数组，我也懒得改了，就直接把标记设成整型数组。
	static int[] notExist = {-1};
	static int[] notknow={0};
	static int[] success = {1};
	static int[] fail = {2};	
	static int[] sameSituation = {3};
	
//	 创建测试状态组，测试天平方案
	static int[] testState= {39, 0, 0, 0, 0};
	static int[] testleft = {13,0,0,0};
	static int[] testright = {13,0,0,0};
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		while (true)
		{
			// TODO Auto-generated method stub
			// 用于保留遍历过的结点的列表，其中[状态元组+左盘分配元组+右盘分配元组+结点标记值]，简写【状态+方案】列表。
			ArrayList<int[]> List = new ArrayList<int[]>();	
			System.out.print("请输入未知硬币的个数：");
			try
			{
			   InputStreamReader temp=new InputStreamReader(System.in);
			   BufferedReader input=new BufferedReader(temp); 
	//		   System.out.println(input.readLine());
			   uncertainty = Integer.valueOf(input.readLine());
			}
			catch (IOException e)
			{
			   e.printStackTrace();
			}
			System.out.print("请输入称量次数：");
			try
			{
				InputStreamReader temp=new InputStreamReader(System.in);
				BufferedReader input=new BufferedReader(temp); 
	//			System.out.println(input.readLine());
				limit = Integer.valueOf(input.readLine());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			// 创建初始状态五元组
			int[] initialState = {uncertainty,0,0,0,0};
			// 创建目标状态五元组
	//		int[] goalState1 = {0,1,0,uncertainty-1,limit};
	//		int[] goalState2 = {0,0,1,uncertainty-1,limit};
			
			// 最初状态赋值给当前状态。
			int [] currentState = initialState;
			
			long t1=System.currentTimeMillis();			
			//运行测试内容
	//		构造第一个结点。		
			boolean isok = checkThisState(List,currentState);			
			long t2=System.currentTimeMillis();
	
			System.out.println("\n寻找解决方案的过程中产生的所有状态都保存在列表中...");
			showWaytoGoal(List,initialState);
			
	//		System.out.print("\n\n运行时间为："+(t2-t1)+"毫秒！");
	
			if (isok)
			{
				System.out.print("\n在硬币数量为"+uncertainty+"个、天平可使用次数为"+limit+"次的情况下可以完成假币的甄别。");
			}
			else {
				System.out.print("\n在硬币数量为"+uncertainty+"个、天平可使用次数为"+limit+"次的情况下不能完成假币的甄别。");
			}
	//		System.out.println("列表中成功的【状态+方案】结点有多少个？"+triumph);
	//		System.out.println("记录中成功路径有多少条？"+ways);
			System.out.println();
			System.out.println();
			System.out.println();
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("我们再来试试其他的设置...");
		}
	}
	
	/**
	 * 检查【状态元组】看能否到达目标状态，如果能到达，就给TRUE。
	 * @param state 【状态元组】
	 */
	public static boolean checkThisState(ArrayList<int[]> recordList,int[] state)
	{
//		System.out.print("\n"+"检查【状态】"+Arrays.toString(state)+">>>>>>>>>>>>>>");
		
		// 选择好判断条件的优先级，先查看是不是目标，再看是不是还可以分
		if (isGoalState(state))
		{
			return true;
		}
		// 如果称量的次数小于3次
		if (state[t] < limit)
		{
			boolean or = false;
			// 判断这个状态是否配置过天平
			if (indexThisState(recordList, state)==-1)
			{
				// 没有配置过天平
	//			System.out.print("【状态】"+Arrays.toString(state)+"开始选择天平放置方案>>>");
				int index = ArrangeLeftRight(recordList, state);
				if (index!=-1)// 根据当前状态进行天平分配
				{
					boolean situationCanGotoGoal = false;
					// 检查由该状态生成的num个【状态+方案】是否有通路，只要有一个通路，就行。
					int num = HowManyOptions(recordList, state);
					for (int i = 0; i < num; i++)
					{
						if (checkThisSituation(recordList,recordList.get(index+4*i), 
								recordList.get(index+1+4*i), recordList.get(index+2+4*i)))
						{
							situationCanGotoGoal = true;
						}
					}
					if (situationCanGotoGoal)
					{
	//						System.out.print("\n"+"【状态】"+Arrays.toString(state)+
	//							"能到达目标状态!");
					}
					else {
	//						System.out.print("\n"+"【状态】"+Arrays.toString(state)+
	//							"不能到达目标!");
					}
					return situationCanGotoGoal;
				}
				else {
	//					System.out.print("\n"+"【状态】"+Arrays.toString(state)+
	//							"不能进行天平分配"+false);
					return false;
				}
			}
			else {
				//已经配置过天平，列表中有记录。
				//	System.out.print("\n"+"【状态】"+Arrays.toString(state)+"不是目标状态，需要在列表中找记录>>>");
				
				// 检查这个状态列表中的记录是否有解		
				for (int it = 0; it < recordList.size()/4; it++)
				{
					/// 这里有问题，这个状态只需要对比前面4个数组，后面称量次数需要对比。
					int[] storeState = recordList.get(4*it);
//					int[] leftSide = recordList.get(4*it+1);
//					int[] rightSide = recordList.get(4*it+2);
					int[] access = recordList.get(4*it+3);
					//if(state==currentState) 这样写就是错的，我不知道为啥，感觉state是列表的索引值可能
					//与currentState的索引值不是同一个。而且我估计这个索引值动态变化。
					if (storeState[0]==state[0] && storeState[1]==state[1]&&storeState[2]==state[2] && 
							storeState[3]==state[3]&&storeState[4]==state[4])			
					{				
						if (access==success)
						{
							or=true;
						}
						if (access==notknow)
						{
							//肯定没有标志值为 notknow 的【状态+方案】结点。
						}
//						if (access==fail)
//						{
//							deleteThisSituation(recordList, storeState, leftSide, rightSide);
//						}
					}
				}
			}

			if (or)
			{
//				System.out.print("\n"+"【状态】"+Arrays.toString(state)+"在列表中有记录为"+true+"~~");
				return true;
			}
			else {
//				System.out.print("\n"+"【状态】"+Arrays.toString(state)+"在列表中有记录，但没有路径到达目标状态");	
				return false;
			}		
		}
		else
		{
//			System.out.print("\n这个【状态】"+Arrays.toString(state)+
//					"是第"+state[t]+"次称量后的结果，如果不是目标状态，那么只有舍弃，不要再分析了！");
			return false;
		}
	}
	
	/**
	 * 检查【状态+选择方案】是否能到达目标状态。
	 * @param 要检查的【状态+选择方案】
	 */
	public static boolean checkThisSituation(ArrayList<int[]> recordList,int[] currentState,int[] leftSide,int[] rightSide)
	{		
		// 检查该节点中{LHS,LS,HS,S}在状态列表firstNodes中是否被已经有结果，如果有结果就不要操作深度遍历了。
		boolean and = true;
		boolean canPICKUP = false;
		int[] newState = new int[5];
//		*********************************左倾操作
		//肯定不能左倾的条件一：左右两边没有轻重标型+左边没有重标型+右边没有轻标型
		//即左盘没有放上标记包含重标的型号（轻重标型和重标型）两种。
//		即右盘没有放上标记包含轻标的型号（轻重标型和轻标型）两种。
		if (leftSide[lhs]==0&&rightSide[lhs]==0&&
			leftSide[hs]==0&&0==rightSide[ls])
		{
//			System.out.print("\n"+"【状态】"+Arrays.toString(currentState)+"--"+Arrays.toString(leftSide)+
//					"^^"+Arrays.toString(rightSide)+"不能左倾！");
		}
		else {
			// 必须要能左倾斜条件一：天平上必须要有未知型的硬币。
			// 条件二：或者左边要有 轻重标型或者重标型。
			// 条件三：或者右边要有 轻重标型或者轻标型
			if (leftSide[hs]>0||leftSide[lhs]>0||rightSide[ls]>0||rightSide[lhs]>0)
			{
				canPICKUP = canPICKUP || true;
				newState = PICKUP(leftSide, rightSide, currentState, -1);
				
				// 加入启发式函数，但是没有产生效果，主要原因是在分配天平方案的时候就使用了启发式的分配方法。
//				if ((currentState[lhs]+currentState[ls]+currentState[hs]-
//						newState[lhs]-newState[ls]-newState[hs])>=0)
				{
					// 新状态有没有通往目标状态的路径。
					// 一个很大很大的错误，就是--->当and是false时，后面的checkThisState函数就自动屏蔽，不运行了。
	//				and = and && checkThisState(recordList,newState);XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
					
					and = checkThisState(recordList,newState)&&and;	
				}
//				else {
//					and = false;
//				}
			}
																								
		}
//		*********************************平衡操作
//			肯定不能平衡的条件一：把未知型的硬币都放上去称量，则肯定不会平衡。
		if (leftSide[lhs]+rightSide[lhs]==currentState[lhs]&&
			leftSide[ls]+rightSide[ls]==currentState[ls]&&
			leftSide[hs]+rightSide[hs]==currentState[hs])
		{
//			System.out.print("\n"+"【状态】"+Arrays.toString(currentState)+"--"+Arrays.toString(leftSide)+
//					"^^"+Arrays.toString(rightSide)+"不能平衡！");
		}
		else {
			// 必须平衡的条件是：既不能左倾，又不能右倾。
//			if ((leftSide[lhs]==0&&rightSide[lhs]==0&&leftSide[hs]==0&&rightSide[ls]==0)&&
//				(leftSide[lhs]==0&&rightSide[lhs]==0&&rightSide[hs]==0&&leftSide[ls]==0))
//			{
				canPICKUP = canPICKUP || true;
				newState = PICKUP(leftSide, rightSide, currentState, 0);
				and = checkThisState(recordList,newState)&&and;				
//			}
		}	

//		*********************************右倾操作
		//肯定不能右倾的条件一：左右两边没有轻重标型+右边没有重标型+左边没有轻标型
		if (leftSide[lhs]==0&&rightSide[lhs]==0&&
			0==rightSide[hs]&&leftSide[ls]==0)
		{
//			System.out.print("\n"+"【状态】"+Arrays.toString(currentState)+"--"+Arrays.toString(leftSide)+
//					"^^"+Arrays.toString(rightSide)+"不能右倾！");
		}
		else {
			// 必须要能右倾斜的条件一：天平上必须要有未知型的硬币。
			// 条件二：或者右边要有 轻重标型或者重标型。
			// 条件三：或者左边要有 轻重标型或者轻标型 
			if (rightSide[hs]>0||rightSide[lhs]>0||leftSide[ls]>0||leftSide[lhs]>0)
			{
				canPICKUP = canPICKUP || true;
				newState = PICKUP(leftSide, rightSide, currentState, 1);
					and = checkThisState(recordList,newState)&&and;					
			}
		}
		if (canPICKUP)
		{
//			System.out.print("\n"+"【状态+方案】"+Arrays.toString(currentState)+"="+
//					Arrays.toString(leftSide)+"+"+Arrays.toString(rightSide)+"是否能到达目标呢？"+and);
			int index = indexThisSituation(recordList, currentState, leftSide, rightSide);
			if (and)
			{
				recordList.set(index+3, success);
			}
			else
			{
				recordList.set(index+3, fail);
			}			
			return and;
		}
		else {
//			System.out.print("\n"+"【状态+方案】"+Arrays.toString(currentState)+"="+
//					Arrays.toString(leftSide)+"+"+Arrays.toString(rightSide)+"是否能到达目标呢？"+false);
			int index = indexThisSituation(recordList, currentState, leftSide, rightSide);			
			recordList.set(index+3, fail);
			return false;
		}
	}
	/**
	 * 删除列表中对应的【状态+方案】结点
	 * @param recordList
	 * @param currentState
	 * @param leftSide
	 * @param rightSide
	 */
	public static void deleteThisSituation(ArrayList<int[]> recordList,int[] currentState,int[] leftSide,int[] rightSide)
	{
		int index = indexThisSituation(recordList, currentState, leftSide, rightSide);
		if (index == -1)
		{//如果没找到，什么都不删除。
			recordList.remove(index);
			recordList.remove(index);
			recordList.remove(index);
			recordList.remove(index);
		}

	}

	/**
	 * 根据当前的硬币状态元组，找到一个合理的天平两边放置硬币的方案，如果没找到就返回NULL。
	 * @param currentState 当前硬币状态元组。
	 * @return 如果找到了一个合理的天平放置方案，返回该【状态+方案】在列表中的下标值。
	 */
	public static int ArrangeLeftRight(ArrayList<int[]> recordList,int[] currentState)
	{
		// 可分配的标识符
		boolean canArrange = false;
		// 分配的结点在列表中的下标值
		int firstIndex = -1;
		
		// 如果当前状态，未知型的硬币只有一个轻标型或重标型，那么就没必要分配，就返回FALSE
		if ((currentState[lhs]==0&&currentState[ls]==0&&currentState[hs]==1)||
				(currentState[lhs]==0&&currentState[ls]==1&&currentState[hs]==0))
		{
			return -1;
		}
		
//		左盘放置lhs1个轻重标型;leftSide[ls]=ls1;leftSide[hs]=hs1;s1 代表 leftSide[s] 
//		rightSide[lhs]=lhs2;rightSide[ls]=ls2;rightSide[hs]=hs2;s2 代表 rightSide[s]
		int s1 = 0 ; int s2 = 0;
		for (int lhs1 = 0; lhs1 <= currentState[lhs]; lhs1++)
		{
			for (int ls1 = 0; ls1 <= currentState[ls]; ls1++)
			{
				for (int hs1 = 0; hs1 <= currentState[hs]; hs1++)
				{
					// 如果天平左盘上放置未知型的硬币，那么才需要进行对比了。
					if(lhs1+ls1+hs1>0)
					{
						for (int lhs2 = 0; lhs2 <= (currentState[lhs]-lhs1); lhs2++)
						{
							for (int ls2 = 0; ls2 <= (currentState[ls]-ls1); ls2++)
							{
								for (int hs2 = 0; hs2 <= (currentState[hs]-hs1); hs2++)
								{
									// 为了在生成选择方案时，为避免对称情况的出现，在循环中始终要求
//									lhs1<=lhs2,lhs1+ls1<=lhs2+ls2,lhs1+ls1+hs1<lhs2+ls2+hs2，
//									以减少生成结点数
//									if ((lhs1<=lhs2)||(ls1<=ls2)||
//											(hs1<=hs2))
									{
										// 判断左右盘中的硬币之差的绝对值是否大于当前状态中可以取的标准硬币的个数
										if(Math.abs(lhs1+ls1+hs1-lhs2-ls2-hs2)<=currentState[s])
										{
											// 如果小于等于标准硬币的个数，那么上面的分配是有问题的，因为天平两边配不齐。则跳出上层for循环
											if((lhs1+ls1+hs1)>=(lhs2+ls2+hs2))
											{ 
												s1=0;
												s2=(lhs1+ls1+hs1)-(lhs2+ls2+hs2);
											}
											else {
												s2=0;
												s1=(lhs2+ls2+hs2)-(lhs1+ls1+hs1);
											}										
											if (0<(lhs1+ls1+hs1+s1) && 0<(lhs2+ls2+hs2+s2) &&(lhs1+ls1+hs1+s1)==(lhs2+ls2+hs2+s2)
													&&(lhs1+ls1+hs1+s1)<=uncertainty/3 &&lhs2+ls2+hs2+s2<=uncertainty/3)
											{
												// 进入到这一步就表明上面挑选出来的天平两边的选择方案已经通过了。
												if (!canArrange)
												{
													canArrange = true;
												}	
												int[] left = {lhs1,ls1,hs1,s1};
												int[] right = {lhs2,ls2,hs2,s2};												
												
												// 该状态和选择方案加上该组方案的状态标志值 附加到一个链表中，返回true。
												recordList.add(currentState);
												if (firstIndex==-1)
												{
													firstIndex = recordList.size()-1;
												}
												recordList.add(left);
												recordList.add(right);
												recordList.add(notknow);
												if (currentState[0] ==testState[0] && 
														currentState[1] ==testState[1] &&
														currentState[2] ==testState[2] &&
														currentState[3] ==testState[3] &&
														currentState[4] ==testState[4] &&
														left[0]==testleft[0] && left[1]==testleft[1] && 
														left[2]==testleft[2] &&left[3]==testleft[3] &&  
														right[0]==testright[0]&&right[1]==testright[1]&&
														right[2]==testright[2]&&right[3]==testright[3])
												{
													System.out.println();
												}
											}
										}
									}
								}
							}
						}						
					}
				}
			}
		}
		return firstIndex;
	}

	/**
	 * 返回[状态元组+天平左分配值+天平右分配值]这个元组数组结点的INDEX索引值，返回 -1 表示没有找到。
	 * @return i 表示找到需要搜索的元组数组的索引值。 
	 */
	public static int indexThisSituation(ArrayList<int[]> recordList,int[] currentState,int[] leftSide,int[] rightSide)
	{
		if(recordList.size()>0)
		{
			for (int i = 0; i < recordList.size()/4; i++)
			{			
				int[] state = recordList.get(4*i);
				if (currentState[0]==state[0] && currentState[1]==state[1]&&
					currentState[2]==state[2] && currentState[3]==state[3]&&
					currentState[4]==state[4])
				{						
					int[] leftSideRec = recordList.get(4*i+1);
					if (leftSideRec[0]==leftSide[0] && leftSideRec[1]==leftSide[1]&&
						leftSideRec[2]==leftSide[2] && leftSideRec[3]==leftSide[3])
					{
						int[] rightSideRec = recordList.get(4*i+2);
						if (rightSideRec[0]==rightSide[0] &&rightSideRec[1]==rightSide[1]&&
							rightSideRec[2]==rightSide[2] && rightSideRec[3]==rightSide[3])
						{
							return 4*i;
						}
						else {
						}
					}
					else {
					}
				}
				else {
				}
			}			
		}
		return -1;
	}
	/**
	 * 在列表中搜索相应的【状态+方案】，返回这个【状态+方案】是否有通向目标状态的路径。
	 * @param recordList 【状态+方案】列表
	 * @param currentState 【状态】
	 * @param left 【方案左盘】
	 * @param right 【方案右盘】
	 * @return 返回这个【状态+方案】是否有通向目标状态的路径。
	 */
	// 返回特征值success、fail、notknow、sameState、notExist
	public static int[] HowAboutThisSituation(ArrayList<int[]> recordList,int[] currentState,int[] left,int[] right)
	{
		Iterator<int[]> it = recordList.iterator();
		int[] result = notExist;
		while (it.hasNext())
		{
			int[] state = (int[]) it.next();
			if (currentState[0]==state[0]&&
				currentState[1]==state[1]&&
				currentState[2]==state[2]&&
				currentState[3]==state[3]&&
				currentState[4]==state[4])
			{
				if (it.hasNext())
				{
					int[] leftSideRec = (int[]) it.next();
					if (leftSideRec==left)
					{
						if (it.hasNext())
						{
							int[] rightSideRec = (int[]) it.next();
							if (rightSideRec==right)
							{
								result = sameSituation;
								if (it.hasNext())
								{
									int[] markRec = (int[]) it.next();
									// 这个元组数组有解，返回success{1}。
									// 这个元组数组无解，返回fail{2}。
									// 这个元组数组还没有深度遍历过不知道有没有解，不能返回notknow{0}。
									if (markRec==success)
									{
										return success;
									}
									if (markRec == fail)
									{
										return fail;
									}
								}
								else {
									it.next();
								}
							}
						}
					}
					else {
						it.next();
						it.next();
					}
				}
			}
			else {
				if (it.hasNext())
				{
					it.next();
					if (it.hasNext())
					{
						it.next();
						if (it.hasNext())
						{
							it.next();						
						}
					}
				}				
			}
		}
		// 在记录的所有状态元组中没有匹配到相同的【状态元组+选择方案】，返回result 。
		return result;		
	}

	/**
	 * 得到【状态+方案】列表中【状态】值为state的结点个数
	 * @param state 列表中与该状态相同的值。
	 * @return 0 表示没找到一个，大于0表示找到个数
	 */
	public static int HowManyOptions(ArrayList<int[]> recordList ,int[] state)
	{
		
		int index = indexThisState(recordList, state);
		if (index==-1)
		{
			return 0;
		}
		else {
			int num = 0;
			int lastIndex = lastIndexThisState(recordList, state);
			for (int i = index/4; i <= lastIndex/4; i++)
			{			
				int[] currentState = recordList.get(4*i);
				if (currentState[0]==state[0] && currentState[1]==state[1]&&
					currentState[2]==state[2] && currentState[3]==state[3]&&
					currentState[4]==state[4])
				{
					num++;
				}
				else {
				}
			}
			return num;
		}
	}
	
	/**
	 * 判断更新的状态是否是目标状态
	 * @param updateState
	 * @return
	 */ 
//	int[] goalState1 = {0,1,0,uncertainty-1,limit};
//	int[] goalState2 = {0,0,1,uncertainty-1,limit};
	public static boolean isGoalState(int[] updateState)
	{
		if ((updateState[lhs]==0
		    &&updateState[ls]==1
		    &&updateState[hs]==0
		    &&updateState[s]==uncertainty-1
		    &&updateState[t]<=limit))
		{
			return true;
		}
		if ((updateState[lhs]==0
		    &&updateState[ls]==0
		    &&updateState[hs]==1
		    &&updateState[s]==uncertainty-1
		    &&updateState[t]<=limit))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	/**按照挑选出来的硬币组合放到天平上称量。
	 * @param leftSide 表示左盘上放的
	 * @param rightSide 表示右盘上放的
	 * @param LBR 表示天平的倾斜方向
	 * @return 天平称量后硬币的全体新的状态。
	 */
	public static int[] PICKUP(/*ArrayList<int[]>recordList,*/int[] leftSide,int[] rightSide,int[] currentState,int LBR)
	{
		int[] updateState = new int[5];
		switch (LBR)
		{
			case -1:
				updateState=leftLeaning(leftSide, rightSide, currentState);
//				System.out.print("\n"+Arrays.toString(currentState)+"-->"+
//						Arrays.toString(leftSide)+"^…^"+Arrays.toString(rightSide)+
//						"左倾="+Arrays.toString(updateState));
				break;
			case 0:
				updateState=balance(leftSide, rightSide, currentState);
//				System.out.print("\n"+Arrays.toString(currentState)+"-->"+
//						Arrays.toString(leftSide)+"^…^"+Arrays.toString(rightSide)+
//						"平衡="+Arrays.toString(updateState));
				break;
			case 1:
				updateState=rightLeaning(leftSide, rightSide, currentState);
//				System.out.print("\n"+Arrays.toString(currentState)+"-->"+
//						Arrays.toString(leftSide)+"^…^"+Arrays.toString(rightSide)+
//						"右倾="+Arrays.toString(updateState));
				break;

			default:
				break;
		}
		//测试，看看生成的新结点是否等于测试结点。
//		if (updateState[lhs]==testState[lhs]&&updateState[ls]==testState[ls]&&
//				updateState[hs]==testState[hs]&&updateState[s]==testState[s]&&
//				updateState[t]==testState[t])
//		{
//			System.out.println(Arrays.toString(updateState));
//		}
//		else {
//			System.out.println(Arrays.toString(currentState));
//		}
		return updateState;	
	}
	
	// 下面三个函数分别表示前一状态currentState通过左倾，右倾，平衡规则后得到新的状态updateState。
	public static int[] leftLeaning(int[] leftSide,int[] rightSide,int[] currentState)
	{
		int[] updateState = new int[5];
		updateState[s]=currentState[s]+currentState[ls]-rightSide[ls]+
						currentState[hs]-leftSide[hs]+currentState[lhs]-
						leftSide[lhs]-rightSide[lhs];
		updateState[ls]=rightSide[ls]+rightSide[lhs];
		updateState[hs]=leftSide[lhs]+leftSide[hs];
		updateState[lhs]=0;
		updateState[t]=currentState[t]+1;
		return updateState;
	}
	
	public static int[] rightLeaning(int[] leftSide,int[] rightSide,int[] currentState)
	{
		int[] updateState = new int[5];
		updateState[s]=currentState[s]+currentState[ls]-leftSide[ls]+
						currentState[hs]-rightSide[hs]+currentState[lhs]-
						leftSide[lhs]-rightSide[lhs];
		updateState[ls]=leftSide[ls]+leftSide[lhs];
		updateState[hs]=rightSide[lhs]+rightSide[hs];
		updateState[lhs]=0;
		updateState[t]=currentState[t]+1;		
		return updateState;		
	}
	
	public static int[] balance(int[] leftSide,int[] rightSide,int[] currentState)
	{
		int[] updateState = new int[5];
		updateState[s]=currentState[s]+leftSide[ls]+rightSide[ls]+
						leftSide[hs]+rightSide[hs]+leftSide[lhs]+rightSide[lhs];
		updateState[ls]=currentState[ls]-leftSide[ls]-rightSide[ls];
		updateState[hs]=currentState[hs]-leftSide[hs]-rightSide[hs];
		updateState[lhs]=currentState[lhs]-leftSide[lhs]-rightSide[lhs];
		updateState[t]=currentState[t]+1;		
		return updateState;
	}
	
	/**
	 * 显示所有完整通向目标状态的路径。
	 * @param recordList
	 */
	static int ways = 0;
	public static void showWaytoGoal(ArrayList<int[]> recordList,int[] state)
	{
		if (isGoalState(state))
		{
//			ways++;
//			System.out.print(Arrays.toString(state)+"是目标状态直接返回.");

		}
		else {			
			int index = indexThisState(recordList, state);
//			System.out.print("\n"+Arrays.toString(state)+"索引值："+index);
			if (index!=-1)
			{
	 			int sum = HowManyOptions(recordList, state);
				System.out.print("\n\n查找【状态】"+Arrays.toString(state)+":");
				for (int i = index/4; i < index/4+sum; i++)
				{					
					if (recordList.get(4*i+3)==success)
					{
						int[] leftSide = recordList.get(4*i+1);
						int[] rightSide = recordList.get(4*i+2);
						
						System.out.print("\n初始状态为："+Arrays.toString(state));
						System.out.print("\t天平左侧："+Arrays.toString(recordList.get(4*i+1))+
								"\t天平右侧:"+Arrays.toString(recordList.get(4*i+2)));
						int[] state1 = new int[5];
						int[] state2 = new int[5];
						int[] state3 = new int[5];
						
						if (leftSide[lhs]==0&&rightSide[lhs]==0&&
								leftSide[hs]==0&&0==rightSide[ls])
						{
//							System.out.print("\n【上述方案】"+Arrays.toString(leftSide)+
//									Arrays.toString(rightSide)+"不能左倾！");
							System.out.print("\n不会出现天平左倾！");
						}
						else {
							// 必须要能左倾斜条件一：天平上必须要有未知型的硬币。
							// 条件二：或者左边要有 轻重标型或者重标型。
							// 条件三：或者右边要有 轻重标型或者轻标型
							if (leftSide[hs]>0||leftSide[lhs]>0||rightSide[ls]>0||rightSide[lhs]>0)
							{
								state1 = PICKUP(recordList.get(4*i+1), recordList.get(4*i+2), 
										recordList.get(4*i), -1);
								System.out.print("\n天平左倾："+Arrays.toString(state1));
							}
						}
						if (leftSide[lhs]+rightSide[lhs]==state[lhs]&&
								leftSide[ls]+rightSide[ls]==state[ls]&&
								leftSide[hs]+rightSide[hs]==state[hs])
						{
//							System.out.print("【上述方案】"+Arrays.toString(leftSide)+
//									Arrays.toString(rightSide)+"不能平衡！");
							System.out.print("不会出现平衡！");
						}
						else {
							state2 = PICKUP(recordList.get(4*i+1), recordList.get(4*i+2), state, 0);
							System.out.print("天平平衡："+Arrays.toString(state2));
						}
						if (leftSide[lhs]==0&&rightSide[lhs]==0&&
								0==rightSide[hs]&&leftSide[ls]==0)
						{
//							System.out.print("【上述方案】"+Arrays.toString(leftSide)+
//									Arrays.toString(rightSide)+"不能右倾！");
							System.out.print("不会出现天平右倾！");
						}
						else {
							// 必须要能右倾斜的条件一：天平上必须要有未知型的硬币。
							// 条件二：或者右边要有 轻重标型或者重标型。
							// 条件三：或者左边要有 轻重标型或者轻标型 
							if (rightSide[hs]>0||rightSide[lhs]>0||leftSide[ls]>0||leftSide[lhs]>0)
							{
								state3 = PICKUP(recordList.get(4*i+1), recordList.get(4*i+2), state, 1);
								System.out.print("天平右倾："+Arrays.toString(state3));
							}
						}
						
						showWaytoGoal(recordList,state1);
						
						if (state1[0]==state2[0]&&state1[1]==state2[1]&&state1[2]==state2[2]&&
								state1[3]==state2[3]&&state1[4]==state2[4])
						{
//							System.out.print("\n两个数组相同！");
						}
						else {
							showWaytoGoal(recordList,state2);
						}
						
						if ((state1[0]==state3[0]&&state1[1]==state3[1]&&state1[2]==state3[2]&&
								state1[3]==state3[3]&&state1[4]==state3[4])||
							(state2[0]==state3[0]&&state2[1]==state3[1]&&state2[2]==state3[2]&&
								state2[3]==state3[3]&&state2[4]==state3[4]))
						{
//							System.out.print("\n比对成功");
						}
						else {
							showWaytoGoal(recordList,state3);
						}
						break;
					}					
				}
				
			}			
			else {
//				System.out.print(Arrays.toString(state)+"没找到索引.");
			}
		}
	}
	
	/**
	 * 返回这个状态在【状态+选择方案】列表中的index值
	 * @param currentState 需要检查的状态。
	 * @return -1 表示没有在列表中找到该状态。
	 */
	public static int indexThisState(ArrayList<int[]> recordList,int[] currentState)
	{
		if(recordList.size()>0)
		{
			for (int i = 0; i < recordList.size()/4; i++)
			{			
				int[] state = recordList.get(4*i);
				if (currentState[0]==state[0] && currentState[1]==state[1]&&
					currentState[2]==state[2] && currentState[3]==state[3]&&
					currentState[4]==state[4])
//				if(state == currentState)	//与上面对比一样的问题，很严重的问题，动态索引问题。
				{
					return 4*i;
				}
				else {
				}
			}			
		}
		return -1;
	}
	/**
	 * 返回这个状态在【状态+选择方案】列表中的index值
	 * @param currentState 需要检查的状态。
	 * @return -1 表示没有在列表中找到该状态。
	 */
	public static int lastIndexThisState(ArrayList<int[]> recordList,int[] currentState)
	{
		if(recordList.size()>0)
		{
			for (int i = (recordList.size()/4)-1; i >= 0; i--)
			{			
				int[] state = recordList.get(4*i);
				if (currentState[0]==state[0] && currentState[1]==state[1]&&
					currentState[2]==state[2] && currentState[3]==state[3]&&
					currentState[4]==state[4])
//				if(state == currentState)	//与上面对比一样的问题，很严重的问题，动态索引问题。
				{
					return 4*i;
				}
				else {
				}
			}			
		}
		return -1;
	}
	/**
	 * 通过遍历列表中保存的状态+选择方案的所有结点，检查到该【状态+方案】节点是否可以到达目标状态，可到达返回TRUE。
	 * @param recordList 保存【状态+选择方案】的列表
	 * @param currentState 需要匹配的状态
	 * @return true表示可以到达目标状态；false表示不能达到。
	 */
//	public static boolean CanThisStateWaytoGoal(ArrayList<int[]> recordList,int[] currentState)
//	{
//		Iterator<int[]> it = recordList.iterator();
//		while (it.hasNext())
//		{
//			int[] state = (int[]) it.next();
//			if (currentState[0]==state[0]&&
//				currentState[1]==state[1]&&
//				currentState[2]==state[2]&&
//				currentState[3]==state[3]&&
//				currentState[4]==state[4])
//			{
//				it.next();
//				it.next();
//				int[] markRec = (int[]) it.next();
//				// 这个元组数组有解，返回success{1}。
//				if (markRec==success)
//				{
//					return true;
//				}
//			}
//			else {
//				it.next();
//				it.next();
//				it.next();
//			}
//		}
//		return false;
//	}
}









