package gen;

import java.util.Scanner;

public class Progen {
	
	public static int maxNrOfPro = 100;
	public static int maxNrOfJobs = 1000;
	
//	static int maxDu = 25;
//	static int maxReq = 200;����������ֻ��Ϊ�˱�֤һ���㹻�����,�����޸�Ϊ����һ��MAX
	public static int MAX = 1000000;
	
	public static int maxR = 20;
	public static int maxD = 20;
	public static int maxN = 20;
	public static int maxRDN;
	public static int maxNrOfModes = 20;
	public static int maxF = 3;

//	static int maxRenSup =5000;
//	static int maxNonSup = 50000; 	
//	static int maxCost = 500; �µķ����Ѿ�����Ҫ����������
	public static int maxHorizon = 2000;
	
	//==================�ռ���Դ���====================
	public static boolean includeSR = true; //�Ƿ�����ռ���ԴԼ��
	public static int maxNrOfTaskGroup=100;
	public static int maxNrOfSRType=20;
	/**
	 * ���ľ���ռ���Դ����
	 */
	public static int maxNrOfSR=200;

	
	public Progen(){
		maxRDN = maxR;
		if(maxRDN<maxD) maxRDN = maxD;
		if(maxRDN<maxN) maxRDN = maxN; //maxRDNȡ�����������Ǹ�
	}

	void genProj(Project p,Demand demand) {
		Utility utility = new Utility(demand.inval);
		String curExFileName; //���ڱ�ʾ��ǰ�������ļ���(����·��,������׺)
		
		//��ռ�¼�����ͬ���ļ�
		utility.setPrintStream(demand.errFilePath,true);//������ڼ�¼������ļ�,���������ļ�
		utility.reSetPrintStream();
		
		for(int i=1;i<=demand.nrOfEx;i++){		//ʵ����Ŵ�1��ʼ
			curExFileName=demand.bFileName+"-"+i+".txt";
			utility.setPrintStream(demand.errFilePath,false);
			System.out.println("------------------------------------------------------------------------");
			System.out.println("sample file -->"+curExFileName);
			System.out.println("------------------------------------------------------------------------");
			utility.reSetPrintStream();
			p.genProjData(demand.base, utility);
			if(p.net.genNetWork(demand, utility)){//������Ŀ������,���ҳɹ��Ļ�,��
				p.calcCPMTimes('d',0);//����һ������Ϊ'd'ʱ,��ʵ���õ�ģʽ�ǵ�0��ģʽ(����������Ŀ��ÿ�������EST/EFT/LST��ÿ������Ŀ��CPMT),�ڶ���������ʱ��������
				p.calcDueDates(demand.base);
				
				//��Դ�������ɹ���ϸ�ڻ���Щģ��
				p.reqGen.resReqMain(demand, utility);
				p.availGen.resAvlMain(demand);
				
				if(Progen.includeSR){
					//===================�ռ���Դ���====================
					//���������в���ֻ��һ������ʱ��������͹�������ʽ��2ά�ռ���Դ����
					if(demand.base.minNofJobTask!=1 && demand.base.maxNofJobTask!=1)
						Utility.genPolygon = false;
					
					p.calcCPMTimes('d',0);//����һ������Ϊ'd'ʱ,��ʵ���õ�ģʽ�ǵ�0��ģʽ(����������Ŀ��ÿ�������EST/EFT/LST��ÿ������Ŀ��CPMT),�ڶ���������ʱ��������
										  //��ʱҪ�ٴμ���һ�Σ�����Ϊ���㳣����Դ�Ŀ�����availGen.resAvlMain(demand)ʱ���޸���ÿ�������EST/EFT����ʱ�����ģʽ��һ���ǵ�0ģʽ��
					                      //���ռ���Դʱ����Ϊֻ����һ��ִ��ģʽ����Ҫ�������ø�����ִ��ģʽΪ0���������ʱ��EST/EFT�������������������EST/EFT(��͹�����ʱ���������/��ٿ�ʼʱ��)
		
					//������������Ϣ
					p.taskGroupGen.genTaskGroupMain(demand, utility);	
					//���ɿռ���Դ����
					p.srReqgen.SRresReqMain(demand, utility);
					//���ɿռ���Դ��������Ϣ
					//2013.5.23Ŀǰ������
					p.srAvailAndOGen.SRresAvlAndOMain(demand, utility);
				}
				
				p.writeToFile(demand,utility,demand.dirStr+"/"+curExFileName);
			}
			System.out.println("Generating case "+i+" has done.");
		}
	}
	
	/**
	 * ���ڿ���̨��Ľ�����ʾ
	 * @param demand
	 */
	 void showMenue(Demand demand){
		System.out.println("=====================================================");
		System.out.println("PROGEN2.0 - Generator For Project Scheduling Problems");
		System.out.println("=====================================================");
		System.out.println("");
		System.out.println("file basedata:"+demand.bFilePath);
		if (demand.inval==0)
			System.out.println("initial valuse:randomly");
		else
			System.out.println("initial value:"+demand.inval);
		System.out.println("number of instances:"+demand.nrOfEx);
		System.out.println("");
		System.out.println("1-basedata");
		System.out.println("2-initial value");
		System.out.println("3-number of instances");
		System.out.println("4-generate");
		System.out.println("5-end program");
		System.out.print("-->");
	}
	 
	/**
	 * ����̨���������
	 * @param argv
	 * @throws Exception
	 */
	public static void main(String argv[]) throws Exception {
//		int rr;
//		//����d���������
//		for(int k=0;k<500;k++){
//			Polygon  p = Utility.genPolygonK(6, 6, 6);
//			rr=p.check();
//			if(rr>0){
//				System.out.println("����");
//			}
//		}	
//		System.out.println("end");
		

		Progen progen = new Progen();
		Project p = new Project();
		Demand demand = new Demand();// ��ͬʱ��(��ѡ����һ�����ļ�ʱ),����demand�ǲ�ͬ��,��Ҫ��������һ������(��Ϊ����������Ԫ�ظ�������)[��ʱ��û�����������,������]
		int taste = 0;
		String basePath;
		boolean readBaseFSuccess = false;
		Scanner scanner = new Scanner(System.in);
		while (taste != 5) {
			progen.showMenue(demand);
			taste = scanner.nextInt();
			switch (taste) {
			case 1:
				System.out.print("baseFile path:");
				basePath = scanner.next();// Scanner���Զ���'\'ǰ�����ת���,���Բ�����ִ���
				readBaseFSuccess = demand.getBaseData(basePath);
				break;
			case 2:
				System.out.print("initial value:");
				demand.inval = scanner.nextInt();
				break;
			case 3:
				System.out.print("number of instances:");
				demand.nrOfEx = scanner.nextInt();
				break;
			case 4:
				if (demand.bFilePath.equals("no")) {
					System.out.print("baseFile path:");
					basePath = scanner.next();// Scanner���Զ���'\'ǰ�����ת���,���Բ�����ִ���
					readBaseFSuccess = demand.getBaseData(basePath);
				}
				if (readBaseFSuccess)
					progen.genProj(p, demand);
				// default:{
				// readBaseFSuccess=demand.getBaseData("d:/2.bas");
				// if(readBaseFSuccess)
				// progen.genProj(p,demand);
				// }
			}
		}
		// System.out.println("exit.");
	}
	
	/**
	 * ���غ���(���ڽ����)
	 * @param baseFilePath
	 * @param randomSeed
	 * @param nrOfEx
	 */
	public void gen(String baseFilePath,int randomSeed,int nrOfEx){
		Project p = new Project();
		Demand demand = new Demand();
		demand.inval=randomSeed;
		demand.nrOfEx=nrOfEx;
		if(demand.getBaseData(baseFilePath)){
			this.genProj(p,demand);		
		}			
	}
	
}
