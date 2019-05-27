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
	
	// ����һ������¼ֵ����������һ��intֵ�����ˣ�������Ҫadd��nodeRecord���Ϻ��棬����add�������飬��Ҳ���ø��ˣ���ֱ�Ӱѱ������������顣
	static int[] notExist = {-1};
	static int[] notknow={0};
	static int[] success = {1};
	static int[] fail = {2};	
	static int[] sameSituation = {3};
	
//	 ��������״̬�飬������ƽ����
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
			// ���ڱ����������Ľ����б�����[״̬Ԫ��+���̷���Ԫ��+���̷���Ԫ��+�����ֵ]����д��״̬+�������б�
			ArrayList<int[]> List = new ArrayList<int[]>();	
			System.out.print("������δ֪Ӳ�ҵĸ�����");
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
			System.out.print("���������������");
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
			
			// ������ʼ״̬��Ԫ��
			int[] initialState = {uncertainty,0,0,0,0};
			// ����Ŀ��״̬��Ԫ��
	//		int[] goalState1 = {0,1,0,uncertainty-1,limit};
	//		int[] goalState2 = {0,0,1,uncertainty-1,limit};
			
			// ���״̬��ֵ����ǰ״̬��
			int [] currentState = initialState;
			
			long t1=System.currentTimeMillis();			
			//���в�������
	//		�����һ����㡣		
			boolean isok = checkThisState(List,currentState);			
			long t2=System.currentTimeMillis();
	
			System.out.println("\nѰ�ҽ�������Ĺ����в���������״̬���������б���...");
			showWaytoGoal(List,initialState);
			
	//		System.out.print("\n\n����ʱ��Ϊ��"+(t2-t1)+"���룡");
	
			if (isok)
			{
				System.out.print("\n��Ӳ������Ϊ"+uncertainty+"������ƽ��ʹ�ô���Ϊ"+limit+"�ε�����¿�����ɼٱҵ����");
			}
			else {
				System.out.print("\n��Ӳ������Ϊ"+uncertainty+"������ƽ��ʹ�ô���Ϊ"+limit+"�ε�����²�����ɼٱҵ����");
			}
	//		System.out.println("�б��гɹ��ġ�״̬+����������ж��ٸ���"+triumph);
	//		System.out.println("��¼�гɹ�·���ж�������"+ways);
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
			System.out.println("����������������������...");
		}
	}
	
	/**
	 * ��顾״̬Ԫ�顿���ܷ񵽴�Ŀ��״̬������ܵ���͸�TRUE��
	 * @param state ��״̬Ԫ�顿
	 */
	public static boolean checkThisState(ArrayList<int[]> recordList,int[] state)
	{
//		System.out.print("\n"+"��顾״̬��"+Arrays.toString(state)+">>>>>>>>>>>>>>");
		
		// ѡ����ж����������ȼ����Ȳ鿴�ǲ���Ŀ�꣬�ٿ��ǲ��ǻ����Է�
		if (isGoalState(state))
		{
			return true;
		}
		// ��������Ĵ���С��3��
		if (state[t] < limit)
		{
			boolean or = false;
			// �ж����״̬�Ƿ����ù���ƽ
			if (indexThisState(recordList, state)==-1)
			{
				// û�����ù���ƽ
	//			System.out.print("��״̬��"+Arrays.toString(state)+"��ʼѡ����ƽ���÷���>>>");
				int index = ArrangeLeftRight(recordList, state);
				if (index!=-1)// ���ݵ�ǰ״̬������ƽ����
				{
					boolean situationCanGotoGoal = false;
					// ����ɸ�״̬���ɵ�num����״̬+�������Ƿ���ͨ·��ֻҪ��һ��ͨ·�����С�
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
	//						System.out.print("\n"+"��״̬��"+Arrays.toString(state)+
	//							"�ܵ���Ŀ��״̬!");
					}
					else {
	//						System.out.print("\n"+"��״̬��"+Arrays.toString(state)+
	//							"���ܵ���Ŀ��!");
					}
					return situationCanGotoGoal;
				}
				else {
	//					System.out.print("\n"+"��״̬��"+Arrays.toString(state)+
	//							"���ܽ�����ƽ����"+false);
					return false;
				}
			}
			else {
				//�Ѿ����ù���ƽ���б����м�¼��
				//	System.out.print("\n"+"��״̬��"+Arrays.toString(state)+"����Ŀ��״̬����Ҫ���б����Ҽ�¼>>>");
				
				// ������״̬�б��еļ�¼�Ƿ��н�		
				for (int it = 0; it < recordList.size()/4; it++)
				{
					/// ���������⣬���״ֻ̬��Ҫ�Ա�ǰ��4�����飬�������������Ҫ�Աȡ�
					int[] storeState = recordList.get(4*it);
//					int[] leftSide = recordList.get(4*it+1);
//					int[] rightSide = recordList.get(4*it+2);
					int[] access = recordList.get(4*it+3);
					//if(state==currentState) ����д���Ǵ�ģ��Ҳ�֪��Ϊɶ���о�state���б������ֵ����
					//��currentState������ֵ����ͬһ���������ҹ����������ֵ��̬�仯��
					if (storeState[0]==state[0] && storeState[1]==state[1]&&storeState[2]==state[2] && 
							storeState[3]==state[3]&&storeState[4]==state[4])			
					{				
						if (access==success)
						{
							or=true;
						}
						if (access==notknow)
						{
							//�϶�û�б�־ֵΪ notknow �ġ�״̬+��������㡣
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
//				System.out.print("\n"+"��״̬��"+Arrays.toString(state)+"���б����м�¼Ϊ"+true+"~~");
				return true;
			}
			else {
//				System.out.print("\n"+"��״̬��"+Arrays.toString(state)+"���б����м�¼����û��·������Ŀ��״̬");	
				return false;
			}		
		}
		else
		{
//			System.out.print("\n�����״̬��"+Arrays.toString(state)+
//					"�ǵ�"+state[t]+"�γ�����Ľ�����������Ŀ��״̬����ôֻ����������Ҫ�ٷ����ˣ�");
			return false;
		}
	}
	
	/**
	 * ��顾״̬+ѡ�񷽰����Ƿ��ܵ���Ŀ��״̬��
	 * @param Ҫ���ġ�״̬+ѡ�񷽰���
	 */
	public static boolean checkThisSituation(ArrayList<int[]> recordList,int[] currentState,int[] leftSide,int[] rightSide)
	{		
		// ���ýڵ���{LHS,LS,HS,S}��״̬�б�firstNodes���Ƿ��Ѿ��н��������н���Ͳ�Ҫ������ȱ����ˡ�
		boolean and = true;
		boolean canPICKUP = false;
		int[] newState = new int[5];
//		*********************************�������
		//�϶��������������һ����������û�����ر���+���û���ر���+�ұ�û�������
		//������û�з��ϱ�ǰ����ر���ͺţ����ر��ͺ��ر��ͣ����֡�
//		������û�з��ϱ�ǰ��������ͺţ����ر��ͺ�����ͣ����֡�
		if (leftSide[lhs]==0&&rightSide[lhs]==0&&
			leftSide[hs]==0&&0==rightSide[ls])
		{
//			System.out.print("\n"+"��״̬��"+Arrays.toString(currentState)+"--"+Arrays.toString(leftSide)+
//					"^^"+Arrays.toString(rightSide)+"�������㣡");
		}
		else {
			// ����Ҫ������б����һ����ƽ�ϱ���Ҫ��δ֪�͵�Ӳ�ҡ�
			// ���������������Ҫ�� ���ر��ͻ����ر��͡�
			// �������������ұ�Ҫ�� ���ر��ͻ��������
			if (leftSide[hs]>0||leftSide[lhs]>0||rightSide[ls]>0||rightSide[lhs]>0)
			{
				canPICKUP = canPICKUP || true;
				newState = PICKUP(leftSide, rightSide, currentState, -1);
				
				// ��������ʽ����������û�в���Ч������Ҫԭ�����ڷ�����ƽ������ʱ���ʹ��������ʽ�ķ��䷽����
//				if ((currentState[lhs]+currentState[ls]+currentState[hs]-
//						newState[lhs]-newState[ls]-newState[hs])>=0)
				{
					// ��״̬��û��ͨ��Ŀ��״̬��·����
					// һ���ܴ�ܴ�Ĵ��󣬾���--->��and��falseʱ�������checkThisState�������Զ����Σ��������ˡ�
	//				and = and && checkThisState(recordList,newState);XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
					
					and = checkThisState(recordList,newState)&&and;	
				}
//				else {
//					and = false;
//				}
			}
																								
		}
//		*********************************ƽ�����
//			�϶�����ƽ�������һ����δ֪�͵�Ӳ�Ҷ�����ȥ��������϶�����ƽ�⡣
		if (leftSide[lhs]+rightSide[lhs]==currentState[lhs]&&
			leftSide[ls]+rightSide[ls]==currentState[ls]&&
			leftSide[hs]+rightSide[hs]==currentState[hs])
		{
//			System.out.print("\n"+"��״̬��"+Arrays.toString(currentState)+"--"+Arrays.toString(leftSide)+
//					"^^"+Arrays.toString(rightSide)+"����ƽ�⣡");
		}
		else {
			// ����ƽ��������ǣ��Ȳ������㣬�ֲ������㡣
//			if ((leftSide[lhs]==0&&rightSide[lhs]==0&&leftSide[hs]==0&&rightSide[ls]==0)&&
//				(leftSide[lhs]==0&&rightSide[lhs]==0&&rightSide[hs]==0&&leftSide[ls]==0))
//			{
				canPICKUP = canPICKUP || true;
				newState = PICKUP(leftSide, rightSide, currentState, 0);
				and = checkThisState(recordList,newState)&&and;				
//			}
		}	

//		*********************************�������
		//�϶��������������һ����������û�����ر���+�ұ�û���ر���+���û�������
		if (leftSide[lhs]==0&&rightSide[lhs]==0&&
			0==rightSide[hs]&&leftSide[ls]==0)
		{
//			System.out.print("\n"+"��״̬��"+Arrays.toString(currentState)+"--"+Arrays.toString(leftSide)+
//					"^^"+Arrays.toString(rightSide)+"�������㣡");
		}
		else {
			// ����Ҫ������б������һ����ƽ�ϱ���Ҫ��δ֪�͵�Ӳ�ҡ�
			// �������������ұ�Ҫ�� ���ر��ͻ����ر��͡�
			// ���������������Ҫ�� ���ر��ͻ�������� 
			if (rightSide[hs]>0||rightSide[lhs]>0||leftSide[ls]>0||leftSide[lhs]>0)
			{
				canPICKUP = canPICKUP || true;
				newState = PICKUP(leftSide, rightSide, currentState, 1);
					and = checkThisState(recordList,newState)&&and;					
			}
		}
		if (canPICKUP)
		{
//			System.out.print("\n"+"��״̬+������"+Arrays.toString(currentState)+"="+
//					Arrays.toString(leftSide)+"+"+Arrays.toString(rightSide)+"�Ƿ��ܵ���Ŀ���أ�"+and);
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
//			System.out.print("\n"+"��״̬+������"+Arrays.toString(currentState)+"="+
//					Arrays.toString(leftSide)+"+"+Arrays.toString(rightSide)+"�Ƿ��ܵ���Ŀ���أ�"+false);
			int index = indexThisSituation(recordList, currentState, leftSide, rightSide);			
			recordList.set(index+3, fail);
			return false;
		}
	}
	/**
	 * ɾ���б��ж�Ӧ�ġ�״̬+���������
	 * @param recordList
	 * @param currentState
	 * @param leftSide
	 * @param rightSide
	 */
	public static void deleteThisSituation(ArrayList<int[]> recordList,int[] currentState,int[] leftSide,int[] rightSide)
	{
		int index = indexThisSituation(recordList, currentState, leftSide, rightSide);
		if (index == -1)
		{//���û�ҵ���ʲô����ɾ����
			recordList.remove(index);
			recordList.remove(index);
			recordList.remove(index);
			recordList.remove(index);
		}

	}

	/**
	 * ���ݵ�ǰ��Ӳ��״̬Ԫ�飬�ҵ�һ���������ƽ���߷���Ӳ�ҵķ��������û�ҵ��ͷ���NULL��
	 * @param currentState ��ǰӲ��״̬Ԫ�顣
	 * @return ����ҵ���һ���������ƽ���÷��������ظá�״̬+���������б��е��±�ֵ��
	 */
	public static int ArrangeLeftRight(ArrayList<int[]> recordList,int[] currentState)
	{
		// �ɷ���ı�ʶ��
		boolean canArrange = false;
		// ����Ľ�����б��е��±�ֵ
		int firstIndex = -1;
		
		// �����ǰ״̬��δ֪�͵�Ӳ��ֻ��һ������ͻ��ر��ͣ���ô��û��Ҫ���䣬�ͷ���FALSE
		if ((currentState[lhs]==0&&currentState[ls]==0&&currentState[hs]==1)||
				(currentState[lhs]==0&&currentState[ls]==1&&currentState[hs]==0))
		{
			return -1;
		}
		
//		���̷���lhs1�����ر���;leftSide[ls]=ls1;leftSide[hs]=hs1;s1 ���� leftSide[s] 
//		rightSide[lhs]=lhs2;rightSide[ls]=ls2;rightSide[hs]=hs2;s2 ���� rightSide[s]
		int s1 = 0 ; int s2 = 0;
		for (int lhs1 = 0; lhs1 <= currentState[lhs]; lhs1++)
		{
			for (int ls1 = 0; ls1 <= currentState[ls]; ls1++)
			{
				for (int hs1 = 0; hs1 <= currentState[hs]; hs1++)
				{
					// �����ƽ�����Ϸ���δ֪�͵�Ӳ�ң���ô����Ҫ���жԱ��ˡ�
					if(lhs1+ls1+hs1>0)
					{
						for (int lhs2 = 0; lhs2 <= (currentState[lhs]-lhs1); lhs2++)
						{
							for (int ls2 = 0; ls2 <= (currentState[ls]-ls1); ls2++)
							{
								for (int hs2 = 0; hs2 <= (currentState[hs]-hs1); hs2++)
								{
									// Ϊ��������ѡ�񷽰�ʱ��Ϊ����Գ�����ĳ��֣���ѭ����ʼ��Ҫ��
//									lhs1<=lhs2,lhs1+ls1<=lhs2+ls2,lhs1+ls1+hs1<lhs2+ls2+hs2��
//									�Լ������ɽ����
//									if ((lhs1<=lhs2)||(ls1<=ls2)||
//											(hs1<=hs2))
									{
										// �ж��������е�Ӳ��֮��ľ���ֵ�Ƿ���ڵ�ǰ״̬�п���ȡ�ı�׼Ӳ�ҵĸ���
										if(Math.abs(lhs1+ls1+hs1-lhs2-ls2-hs2)<=currentState[s])
										{
											// ���С�ڵ��ڱ�׼Ӳ�ҵĸ�������ô����ķ�����������ģ���Ϊ��ƽ�����䲻�롣�������ϲ�forѭ��
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
												// ���뵽��һ���ͱ���������ѡ��������ƽ���ߵ�ѡ�񷽰��Ѿ�ͨ���ˡ�
												if (!canArrange)
												{
													canArrange = true;
												}	
												int[] left = {lhs1,ls1,hs1,s1};
												int[] right = {lhs2,ls2,hs2,s2};												
												
												// ��״̬��ѡ�񷽰����ϸ��鷽����״̬��־ֵ ���ӵ�һ�������У�����true��
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
	 * ����[״̬Ԫ��+��ƽ�����ֵ+��ƽ�ҷ���ֵ]���Ԫ���������INDEX����ֵ������ -1 ��ʾû���ҵ���
	 * @return i ��ʾ�ҵ���Ҫ������Ԫ�����������ֵ�� 
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
	 * ���б���������Ӧ�ġ�״̬+�����������������״̬+�������Ƿ���ͨ��Ŀ��״̬��·����
	 * @param recordList ��״̬+�������б�
	 * @param currentState ��״̬��
	 * @param left ���������̡�
	 * @param right ���������̡�
	 * @return ���������״̬+�������Ƿ���ͨ��Ŀ��״̬��·����
	 */
	// ��������ֵsuccess��fail��notknow��sameState��notExist
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
									// ���Ԫ�������н⣬����success{1}��
									// ���Ԫ�������޽⣬����fail{2}��
									// ���Ԫ�����黹û����ȱ�������֪����û�н⣬���ܷ���notknow{0}��
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
		// �ڼ�¼������״̬Ԫ����û��ƥ�䵽��ͬ�ġ�״̬Ԫ��+ѡ�񷽰���������result ��
		return result;		
	}

	/**
	 * �õ���״̬+�������б��С�״̬��ֵΪstate�Ľ�����
	 * @param state �б������״̬��ͬ��ֵ��
	 * @return 0 ��ʾû�ҵ�һ��������0��ʾ�ҵ�����
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
	 * �жϸ��µ�״̬�Ƿ���Ŀ��״̬
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
	
	/**������ѡ������Ӳ����Ϸŵ���ƽ�ϳ�����
	 * @param leftSide ��ʾ�����Ϸŵ�
	 * @param rightSide ��ʾ�����Ϸŵ�
	 * @param LBR ��ʾ��ƽ����б����
	 * @return ��ƽ������Ӳ�ҵ�ȫ���µ�״̬��
	 */
	public static int[] PICKUP(/*ArrayList<int[]>recordList,*/int[] leftSide,int[] rightSide,int[] currentState,int LBR)
	{
		int[] updateState = new int[5];
		switch (LBR)
		{
			case -1:
				updateState=leftLeaning(leftSide, rightSide, currentState);
//				System.out.print("\n"+Arrays.toString(currentState)+"-->"+
//						Arrays.toString(leftSide)+"^��^"+Arrays.toString(rightSide)+
//						"����="+Arrays.toString(updateState));
				break;
			case 0:
				updateState=balance(leftSide, rightSide, currentState);
//				System.out.print("\n"+Arrays.toString(currentState)+"-->"+
//						Arrays.toString(leftSide)+"^��^"+Arrays.toString(rightSide)+
//						"ƽ��="+Arrays.toString(updateState));
				break;
			case 1:
				updateState=rightLeaning(leftSide, rightSide, currentState);
//				System.out.print("\n"+Arrays.toString(currentState)+"-->"+
//						Arrays.toString(leftSide)+"^��^"+Arrays.toString(rightSide)+
//						"����="+Arrays.toString(updateState));
				break;

			default:
				break;
		}
		//���ԣ��������ɵ��½���Ƿ���ڲ��Խ�㡣
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
	
	// �������������ֱ��ʾǰһ״̬currentStateͨ�����㣬���㣬ƽ������õ��µ�״̬updateState��
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
	 * ��ʾ��������ͨ��Ŀ��״̬��·����
	 * @param recordList
	 */
	static int ways = 0;
	public static void showWaytoGoal(ArrayList<int[]> recordList,int[] state)
	{
		if (isGoalState(state))
		{
//			ways++;
//			System.out.print(Arrays.toString(state)+"��Ŀ��״ֱ̬�ӷ���.");

		}
		else {			
			int index = indexThisState(recordList, state);
//			System.out.print("\n"+Arrays.toString(state)+"����ֵ��"+index);
			if (index!=-1)
			{
	 			int sum = HowManyOptions(recordList, state);
				System.out.print("\n\n���ҡ�״̬��"+Arrays.toString(state)+":");
				for (int i = index/4; i < index/4+sum; i++)
				{					
					if (recordList.get(4*i+3)==success)
					{
						int[] leftSide = recordList.get(4*i+1);
						int[] rightSide = recordList.get(4*i+2);
						
						System.out.print("\n��ʼ״̬Ϊ��"+Arrays.toString(state));
						System.out.print("\t��ƽ��ࣺ"+Arrays.toString(recordList.get(4*i+1))+
								"\t��ƽ�Ҳ�:"+Arrays.toString(recordList.get(4*i+2)));
						int[] state1 = new int[5];
						int[] state2 = new int[5];
						int[] state3 = new int[5];
						
						if (leftSide[lhs]==0&&rightSide[lhs]==0&&
								leftSide[hs]==0&&0==rightSide[ls])
						{
//							System.out.print("\n������������"+Arrays.toString(leftSide)+
//									Arrays.toString(rightSide)+"�������㣡");
							System.out.print("\n���������ƽ���㣡");
						}
						else {
							// ����Ҫ������б����һ����ƽ�ϱ���Ҫ��δ֪�͵�Ӳ�ҡ�
							// ���������������Ҫ�� ���ر��ͻ����ر��͡�
							// �������������ұ�Ҫ�� ���ر��ͻ��������
							if (leftSide[hs]>0||leftSide[lhs]>0||rightSide[ls]>0||rightSide[lhs]>0)
							{
								state1 = PICKUP(recordList.get(4*i+1), recordList.get(4*i+2), 
										recordList.get(4*i), -1);
								System.out.print("\n��ƽ���㣺"+Arrays.toString(state1));
							}
						}
						if (leftSide[lhs]+rightSide[lhs]==state[lhs]&&
								leftSide[ls]+rightSide[ls]==state[ls]&&
								leftSide[hs]+rightSide[hs]==state[hs])
						{
//							System.out.print("������������"+Arrays.toString(leftSide)+
//									Arrays.toString(rightSide)+"����ƽ�⣡");
							System.out.print("�������ƽ�⣡");
						}
						else {
							state2 = PICKUP(recordList.get(4*i+1), recordList.get(4*i+2), state, 0);
							System.out.print("��ƽƽ�⣺"+Arrays.toString(state2));
						}
						if (leftSide[lhs]==0&&rightSide[lhs]==0&&
								0==rightSide[hs]&&leftSide[ls]==0)
						{
//							System.out.print("������������"+Arrays.toString(leftSide)+
//									Arrays.toString(rightSide)+"�������㣡");
							System.out.print("���������ƽ���㣡");
						}
						else {
							// ����Ҫ������б������һ����ƽ�ϱ���Ҫ��δ֪�͵�Ӳ�ҡ�
							// �������������ұ�Ҫ�� ���ر��ͻ����ر��͡�
							// ���������������Ҫ�� ���ر��ͻ�������� 
							if (rightSide[hs]>0||rightSide[lhs]>0||leftSide[ls]>0||leftSide[lhs]>0)
							{
								state3 = PICKUP(recordList.get(4*i+1), recordList.get(4*i+2), state, 1);
								System.out.print("��ƽ���㣺"+Arrays.toString(state3));
							}
						}
						
						showWaytoGoal(recordList,state1);
						
						if (state1[0]==state2[0]&&state1[1]==state2[1]&&state1[2]==state2[2]&&
								state1[3]==state2[3]&&state1[4]==state2[4])
						{
//							System.out.print("\n����������ͬ��");
						}
						else {
							showWaytoGoal(recordList,state2);
						}
						
						if ((state1[0]==state3[0]&&state1[1]==state3[1]&&state1[2]==state3[2]&&
								state1[3]==state3[3]&&state1[4]==state3[4])||
							(state2[0]==state3[0]&&state2[1]==state3[1]&&state2[2]==state3[2]&&
								state2[3]==state3[3]&&state2[4]==state3[4]))
						{
//							System.out.print("\n�ȶԳɹ�");
						}
						else {
							showWaytoGoal(recordList,state3);
						}
						break;
					}					
				}
				
			}			
			else {
//				System.out.print(Arrays.toString(state)+"û�ҵ�����.");
			}
		}
	}
	
	/**
	 * �������״̬�ڡ�״̬+ѡ�񷽰����б��е�indexֵ
	 * @param currentState ��Ҫ����״̬��
	 * @return -1 ��ʾû�����б����ҵ���״̬��
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
//				if(state == currentState)	//������Ա�һ�������⣬�����ص����⣬��̬�������⡣
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
	 * �������״̬�ڡ�״̬+ѡ�񷽰����б��е�indexֵ
	 * @param currentState ��Ҫ����״̬��
	 * @return -1 ��ʾû�����б����ҵ���״̬��
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
//				if(state == currentState)	//������Ա�һ�������⣬�����ص����⣬��̬�������⡣
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
	 * ͨ�������б��б����״̬+ѡ�񷽰������н�㣬��鵽�á�״̬+�������ڵ��Ƿ���Ե���Ŀ��״̬���ɵ��ﷵ��TRUE��
	 * @param recordList ���桾״̬+ѡ�񷽰������б�
	 * @param currentState ��Ҫƥ���״̬
	 * @return true��ʾ���Ե���Ŀ��״̬��false��ʾ���ܴﵽ��
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
//				// ���Ԫ�������н⣬����success{1}��
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









