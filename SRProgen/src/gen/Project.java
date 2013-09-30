package gen;

import java.util.*;

public class Project {
	int nrOfPro; //����Ŀ�ĸ���
	int nrOfJobs = 0; //���еĹ�����(�����鿪ʼ�����β�),ע�⿪ʼ������Ϊ0,β����λnrOfJobs-1.
	int[] pro;//����Ԫ�ظ���Ϊ����Ŀ�ĸ���,ÿ��Ԫ��ֵ��ʾ��Ӧ����Ŀ�ķ��鹤����
	int[] CPMT; //����Ԫ�ظ���Ϊ����Ŀ�ĸ���
	int[] dueData;
	int[] relDate;//����Ԫ�ظ���Ϊ����Ŀ�ĸ���,ÿ��Ԫ�ر�ʾ��Ӧ����Ŀ��ʼ������ʱ��
	int[] tardCost;//
	Job[] jobs;//��������л������,Ԫ�ظ���Ϊ������,ÿһԪ��Ϊһ�
	int R;//�ɸ�����Դ������
	int D;//˫����Դ������
	int N;//���ɸ�����Դ����
	int horizon;//
//	Time[] T;//�������Ϊ������Ŀ��,ÿ��Ԫ�ؼ�¼�˶�Ӧ��ŵĻ��EST/EFT�ʹ�ʱ��Mode
	
	int[] SJ;//SJ[k]��ʾ����Ŀk�Ŀ�ʼ����
	int[] FJ;//FJ[k]��ʾ����Ŀk�����һ����ı��
	int[] NSJ;//NSJ[k]��ʾ����Ŀk�鿪ʼ���ֱ�ӽ�������Ŀ
	int[] NFJ;//NFJ[k]��ʾ����Ŀkβ����ֱ�ӽ�ǰ�����Ŀ
	
	int[] RPer;//�ɸ�����Դ������
	int[] Dper;//˫����Դ��ÿ�׶εľ��пɸ�����Դ���ʵ���Դ������
	int[] DTot;//
	int[] NTot;//���ɸ�����Դ������
	
	Network net;
	Reqgen reqGen;
	AvailGen availGen;
	
	//===============�ռ���Դ���======================
	TaskGroupGen taskGroupGen;
	ArrayList<TaskGroup> taskGroup;
	int[] nrOfTaskG;//������Ŀ����������
	SRreqgen srReqgen;//���ɿռ���Դ����
	SRAvailAndOGen srAvailAndOGen;//���ɾ���ռ���Դ������������
	
//	int[] SRNr;//��¼�����Ϳռ���Դ��������(��SRNr[3]=4,��ʾ������ռ���Դ��4��)
	SRType[] typeSR;//��¼�����Ϳռ���Դ
	concreteSR[] SR;//����Ŀռ���Դ����,ÿ��Ԫ�ر�ʾһ�־���Ŀռ���Դ(��¼�������������)
	int nrOfSRType;//�ռ���Դ������
	
	Project(){	
		this.pro = new int[Progen.maxNrOfPro];
		this.CPMT = new int[Progen.maxNrOfPro];
		this.dueData = new int[Progen.maxNrOfPro];
		this.relDate = new int[Progen.maxNrOfPro];
		this.tardCost = new int[Progen.maxNrOfPro];
		this.jobs = new Job[Progen.maxNrOfJobs];
		for(int i=0;i<this.jobs.length;i++)
			this.jobs[i] = new Job();
//		this.T = new Time[Progen.maxNrOfJobs];
//		for(int i=0;i<this.T.length;i++)
//			this.T[i] = new Time();
		this.SJ = new int[Progen.maxNrOfPro];
		this.FJ = new int[Progen.maxNrOfPro];
		this.NSJ = new int[Progen.maxNrOfPro];
		this.NFJ = new int[Progen.maxNrOfPro];
		
		this.RPer = new int[Progen.maxR];
		this.Dper = new int[Progen.maxD];
		this.DTot = new int[Progen.maxD];
		this.NTot = new int[Progen.maxN];
		
		this.net = new Network(this);
		this.reqGen = new Reqgen(this);
		this.availGen = new AvailGen(this);
		
		//===============�ռ���Դ���======================
		taskGroupGen = new TaskGroupGen(this);
//		this.taskGroup = new TaskGroup[Progen.maxTaskGroup];
//		for(int i=0;i<this.taskGroup.length;i++)
//			this.taskGroup[i] = new TaskGroup();
		taskGroup = new ArrayList();
		this.nrOfTaskG = new int[Progen.maxNrOfPro];
		
		this.typeSR = new SRType[Progen.maxNrOfSRType];
		for(int i=0;i<this.typeSR.length;i++)
			this.typeSR[i] = new SRType();
		this.SR = new concreteSR[Progen.maxNrOfSR];
		for(int i=0;i<this.SR.length;i++)
			this.SR[i] = new concreteSR();
		this.srReqgen = new SRreqgen(this);
		this.srAvailAndOGen = new SRAvailAndOGen(this);
	}
	
	
	void genProjData(BaseStruct base,Utility utility){
//		this.proInit();//���ڶ�������ļ�������ʹ�õ���ͬһProject����,����Ҫ�Զ�������ݽ��г�ʼ��(�������)
		
		this.nrOfPro = base.nrOfPro;//����Ŀ�ĸ���
		this.nrOfJobs = 0;//���ļ���ֻ�涨�˻��С�������Ŀ,������ÿ��ʵ���ܻ��Ŀ�ɲ�ͬ
		int maxRelDate = 0;//��¼��ʱ����Ǹ�����Ŀ�ĳ���ʱ��
		
		//=======================�ռ���Դ���================
		if(Progen.includeSR){//�����ռ���ԴԼ��
			//�ռ���Դ������
			this.nrOfSRType = base.nrOfSR;
			for(int i=0,j=0;i<this.nrOfSRType;i++){
				//�ռ���Դ��������
				this.typeSR[i].copy(base.SRTypeAll[i]);
				this.typeSR[i].kind = this.typeSR[i].minKind + utility.random.nextInt(this.typeSR[i].maxKind-this.typeSR[i].minKind+1);
				this.typeSR[i].startNr=j;
				this.typeSR[i].endNr=j+this.typeSR[i].kind-1;
				j=j+this.typeSR[i].kind;
			}
		}
		
		//����Ŀ����
		for(int i=0;i<this.nrOfPro;i++){ 
			this.pro[i] = base.minJob+utility.random.nextInt(base.maxJob-base.minJob+1);
			this.SJ[i] = this.nrOfJobs+1; //���������Ŀi�Ŀ�ʼ����
			this.nrOfJobs = this.nrOfJobs+this.pro[i];
			this.FJ[i]= this.nrOfJobs; //���������Ŀi�Ľ�������
			this.relDate[i]=utility.random.nextInt(base.maxRel+1);
			maxRelDate = maxRelDate>relDate[i]?maxRelDate : relDate[i];
			tardCost[i] = (int)(utility.random.nextDouble()*this.pro[i]);//Java��ǿ��ת��Ϊint���Ƕ���С��λ
			
			if(Progen.includeSR){
				//=======================�ռ���Դ(������)���================
				this.nrOfTaskG[i] = base.minNofTaskG+utility.random.nextInt(base.maxNofTaskG-base.minNofTaskG+1);
			}
		}
		
		this.nrOfJobs = this.nrOfJobs+2;//�������Ŀ,������β����Ŀ���Լ�2
		this.horizon = 0;
		
		//����������Դ�ĸ�����Ŀ
		this.R = base.minRen+utility.random.nextInt(base.maxRen-base.minRen+1);
		this.N = base.minNon+utility.random.nextInt(base.maxNon-base.minNon+1);
		this.D = base.minDou+utility.random.nextInt(base.maxDou-base.minDou+1);
		
		this.genModes(base,utility);
		//����this.horizon
		for(int i=0;i<this.nrOfJobs;i++)
			this.horizon = this.horizon + this.jobs[i].modes[this.jobs[i].nrOfModes-1].duration;

		/**
		 * �����ʱ����[�������ѡȡִ��ʱ������Ǹ�ģʽ����ִ��,����깤��ʱ��,
		 * �ټ���MaxRelDate(ΪʲôҪ����?)],��Ŀ�깤ʱ�䲻���ܱȴ˸���
		 */
		this.horizon = this.horizon + maxRelDate;                       		
	}
	
	
	/**
	 * ������Ŀ�и���Ļģʽ
	 */
	void genModes(BaseStruct base,Utility utility){
		//�ȳ�ʼ������Ļģʽ��Ϊ0,ģʽʱ��Ϊ0(ʵ�����ڶ���Ĺ��캯�����ѳ�ʼ��Ϊ0,���˴��Բ���ʡ��,��Ϊ������һ��,������Ҫ���ɶ��)
		for(int i=0;i<this.nrOfJobs;i++){
			this.jobs[i].nrOfModes = 0;
			for(int j=0;j<Progen.maxNrOfModes;j++)
				this.jobs[i].modes[j].duration = 0;
		}
		//�������û���Ϊ0�ͱ��Ϊ(nrOfJobs-1)����β�ֻ��һ������ģʽ,ģʽ����ʱ��Ϊ0,�Ը���Դ����Ϊ0
		this.jobs[0].dumMyJob(this.R,this.N, this.D);
		this.jobs[nrOfJobs-1].dumMyJob(this.R,this.N, this.D);
		//���������еĻ����ģʽ����ģʽ�ĳ���ʱ��
		for(int i=1;i<this.nrOfJobs-1;i++){
			this.jobs[i].nrOfModes = base.minMode+utility.random.nextInt(base.maxMode-base.minMode+1);
			for(int j=0;j<this.jobs[i].nrOfModes;j++){
				this.jobs[i].modes[j].duration = base.minDur+utility.random.nextInt(base.maxDur-base.minDur+1);
			}
		}
		//�Ը��(����β)��ģʽ��ʱ����������(��������),ʹ��ģʽ0ʱ�����,ģʽnrOfModes-1ʱ���
		for(int i=1;i<this.nrOfJobs-1;i++){
			for(int j=1,temp,k;j<this.jobs[i].nrOfModes;j++){
				temp = this.jobs[i].modes[j].duration;
				for( k=j-1;k>=0&&this.jobs[i].modes[k].duration>temp;k--)
					this.jobs[i].modes[k+1].duration = this.jobs[i].modes[k].duration;
				this.jobs[i].modes[k+1].duration = temp;
			}
		}		
	}
	
/**
 * ����ÿ�������EST(���翪ʼʱ��)��EFT(�������ʱ��)��(��type=='d'ʱ)��LST(����ʼʱ��)�����CPMTʱ�䡿
 * @param type ��Դ����
 * @param resnr ĳ��Դ�ı��
 */
	 void calcCPMTimes(char type,int resnr) {
		// TODO Auto-generated method stub
		for(int pnr=0;pnr<this.nrOfPro;pnr++){
			for(int j=this.SJ[pnr];j<=this.FJ[pnr];j++){
				this.jobs[j].T.EST = this.relDate[pnr];
				//ÿ����������翪ʼʱ��Ҫ���ڵ�����ֱ�ӽ�ǰ��������翪ʼʱ��
				for(int i=this.SJ[pnr];i<j;i++){
					if(this.jobs[j].dirPred.contains(i))
						this.jobs[j].T.EST = (this.jobs[j].T.EST>this.jobs[i].T.EFT)?this.jobs[j].T.EST:this.jobs[i].T.EFT;
				}
				this.jobs[j].selectMode(type,resnr);
				this.jobs[j].T.EFT = this.jobs[j].T.EST+this.jobs[j].modes[this.jobs[j].T.mode].duration;
			}
			
			if(type=='d'){ //��ʱ��Ҫ�����ÿ������Ŀ��CPM(�ؼ�·��)ʱ�估ÿ�������LST(����ʼʱ��)
				this.CPMT[pnr]=0;
				
				//�ؼ�·��ʱ��Ϊ��β�鹤���Ľ�ǰ����������������Ǹ��Ľ���ʱ��
				for(int j=this.FJ[pnr]-this.NFJ[pnr]+1;j<=this.FJ[pnr];j++){
					this.CPMT[pnr] = (this.CPMT[pnr]>this.jobs[j].T.EFT)?this.CPMT[pnr]:this.jobs[j].T.EFT;
				} 
				
				//�������ÿ�������LST(����ʼʱ��)
				for(int j=this.FJ[pnr];j>=this.SJ[pnr];j--){
					this.jobs[j].T.LST = this.CPMT[pnr]-this.jobs[j].modes[this.jobs[j].T.mode].duration;
					//ÿ�����������ʼʱ��ҪС�ڵ�����ֱ�ӽ�����������翪ʼʱ��
					for(int i=this.FJ[pnr];i>j;i--){
						if(this.jobs[j].dirSucc.contains(i))
							this.jobs[j].T.LST = (this.jobs[j].T.LST<(this.jobs[i].T.LST-this.jobs[j].modes[this.jobs[j].T.mode].duration))?this.jobs[j].T.LST:(this.jobs[i].T.LST-this.jobs[j].modes[this.jobs[j].T.mode].duration);
					}
				}
				
			}
		}
	}
/**
 * ����ÿ������Ŀ��dueData
 * @param base
 */
	 void calcDueDates(BaseStruct base) {
	// TODO Auto-generated method stub
	for(int pnr=0;pnr<this.nrOfPro;pnr++){
		this.dueData[pnr] = (int)(this.CPMT[pnr]+base.dueDateFac*(this.horizon-this.CPMT[pnr]));
	}
}
/**
 * �ѵ�ǰ���ɵ�����(project����ǰ��״̬����)д�뵽�ļ�
 * @param demand  ��Ϊ����ļ�Ҫ��ʾ���ݵĻ��ļ�/���������,����˲���
 * @param utility	
 * @param filepath ����ļ�·��(���ļ���/��׺)
 */
	public void writeToFile(Demand demand, Utility utility,String filepath) {
		// TODO Auto-generated method stub
		utility.setPrintStream(filepath,true);
		Formatter f = new Formatter(System.out);
	    System.out.println("************************************************************************");
	    System.out.println("file with basedata            : "+demand.bFileName+demand.extStr);
	    System.out.println("initial value random generator: "+demand.inval);
	    System.out.println("************************************************************************");
	    System.out.println("projects                      :  "+this.nrOfPro);
	    System.out.println("jobs (incl. supersource/sink ):  "+this.nrOfJobs);
	    System.out.println("horizon                       :  "+this.horizon);
	    System.out.println("RESOURCES");
	    System.out.println("  - renewable                 :  "+this.R+"   R");
	    System.out.println("  - nonrenewable              :  "+this.N+"   N");
	    System.out.println("  - doubly constrained        :  "+this.D+"   D");
	    
	    if(Progen.includeSR){
	    	//����ռ���Դ����
		    System.out.println("  - SPATIAL  RESOURCES ");
		    for(int i=0;i<this.nrOfSRType;i++){
		    	String div,ori,blank;
		    	if(this.typeSR[i].dividable)
		    		div=" d";
		    	else
		    		div="nd";
		    	if(this.typeSR[i].orientation) 
		    		ori=" o";
		    	else
		    		ori="no";
		    	System.out.println("  	- "+this.typeSR[i].name+"("+this.typeSR[i].dimension+","+div+","+ori+")            :  "+this.typeSR[i].kind+"   "+this.typeSR[i].name);
		    }
	    }
	    
	    if(Progen.includeSR){
		    //�����Ŀ��Ϣ
		    System.out.println("************************************************************************");
		    System.out.println("PROJECT INFORMATION:");
		    System.out.println("pronr.  #jobs  #TaskGNr  rel.date duedate tardcost  MPM-Time");
		  //��Ϊԭ����Progen����Ŀ��š����š�ģʽ��š���Դ��Ŷ���1��ʼ���������ʱ����һ��
		    for(int i=0;i<this.nrOfPro;i++){
		    	f.format("  %3d    %3d    %3d       %3d      %3d      %3d      %3d",i+1,this.pro[i],this.nrOfTaskG[i],this.relDate[i],this.dueData[i],this.tardCost[i],this.CPMT[i]);
		    	System.out.println();
		    }
	    }
	    else{
		    System.out.println("************************************************************************");
		    System.out.println("PROJECT INFORMATION:");
		    System.out.println("pronr.  #jobs rel.date duedate tardcost  MPM-Time");
		  //��Ϊԭ����Progen����Ŀ��š����š�ģʽ��š���Դ��Ŷ���1��ʼ���������ʱ����һ��
		    for(int i=0;i<this.nrOfPro;i++){
		    	f.format("  %3d    %3d    %3d      %3d      %3d      %3d",i+1,this.pro[i],this.relDate[i],this.dueData[i],this.tardCost[i],this.CPMT[i]);
		    	System.out.println();
		    }
	    }
	    
	    //�����Ŀ������Ϣ
	    System.out.println("************************************************************************");
	    System.out.println("PRECEDENCE RELATIONS:");
	    System.out.println("jobnr.    #modes  #successors   successors");
	    for(int i=0;i<this.nrOfJobs;i++){
	    	f.format(" %3d      %3d        %3d        ",i+1,this.jobs[i].nrOfModes,this.jobs[i].nrOfSucc);
	    	TreeSet ts = new TreeSet(this.jobs[i].dirSucc);//����һ��TreeSet,TreeSet��������
	        ts.comparator();
	    	Iterator iter = ts.iterator(); 
			while (iter.hasNext())
				f.format(" %3d", (Integer)iter.next()+1);
			System.out.println();
	    }
	    
	    if(Progen.includeSR){
	    	//������������������Ϣ
		    System.out.println("************************************************************************");
		    System.out.println("TASKGROUP INFO:");
		    System.out.println("taskGroupNr #belongPro  #tasks  #QTasks  #MTasks  tasks              requestTasks           moveTasks");
		    for(int g=0;g<this.taskGroup.size();g++){
		    	TaskGroup group = (TaskGroup)this.taskGroup.get(g);
		    	f.format(" %3d           %3d      %3d      %3d     %3d     ",g+1,group.belongPro+1,group.taskSet.size(),group.requestTaskSet.size(),group.moveTaskSet.size());
		    	TreeSet ts = new TreeSet(group.taskSet);//����һ��TreeSet,TreeSet��������
		    	ts.comparator();
		    	Iterator iter = ts.iterator(); 
				while (iter.hasNext())
					f.format(" %3d", (Integer)iter.next()+1);
				System.out.print("       ");
				ts = new TreeSet(group.requestTaskSet);
				ts.comparator();
		    	iter = ts.iterator(); 
				while (iter.hasNext())
					f.format(" %3d", (Integer)iter.next()+1);
				System.out.print("				");
				ts = new TreeSet(group.moveTaskSet);
				ts.comparator();
		    	iter = ts.iterator(); 
				while (iter.hasNext())
					f.format(" %3d", (Integer)iter.next()+1);
				System.out.println();
		    }
	    }
	   	    
	    
	    //���������Դ������Ϣ
	    System.out.println("************************************************************************");
	    System.out.println("REQUESTS/DURATIONS:");
	    System.out.print("jobnr. mode duration");
	    for(int r=0;r<this.R;r++)
	    	f.format("  R%2d",r+1);
	    for(int r=0;r<this.N;r++)
	    	f.format("  N%2d",r+1);
	    for(int r=0;r<this.D;r++)
	    	f.format("  D%2d",r+1);
	    System.out.println();
	    System.out.println("------------------------------------------------------------------------");
	    for(int j=0;j<this.nrOfJobs;j++){
	    	for(int m=0;m<this.jobs[j].nrOfModes;m++){
	    		if(m==0){
	    			f.format("%3d    %3d   %3d   ",j+1,m+1,this.jobs[j].modes[m].duration);
	    			for(int r=0;r<this.R;r++)
	    				f.format("  %3d", this.jobs[j].modes[m].RResReq[r]);
	    			for(int r=0;r<this.N;r++)
	    				f.format("  %3d", this.jobs[j].modes[m].NResReq[r]);
	    			for(int r=0;r<this.D;r++)
	    				f.format("  %3d", this.jobs[j].modes[m].DResReq[r]);
	    			System.out.println();
	    		}
	    		else{
	    			f.format("       %3d   %3d   ",m+1,this.jobs[j].modes[m].duration);
	    			for(int r=0;r<this.R;r++)
	    				f.format("  %3d", this.jobs[j].modes[m].RResReq[r]);
	    			for(int r=0;r<this.N;r++)
	    				f.format("  %3d", this.jobs[j].modes[m].NResReq[r]);
	    			for(int r=0;r<this.D;r++)
	    				f.format("  %3d", this.jobs[j].modes[m].DResReq[r]);
	    			System.out.println();
	    		}
	    	}
	    }
	    
	    //���������Դ������
	    System.out.println("************************************************************************");
	    System.out.println("RESOURCEAVAILABILITIES:");
	    for(int r=0;r<this.R;r++)
	    	f.format("  R%2d",r+1);
	    for(int r=0;r<this.N;r++)
	    	f.format("  N%2d",r+1);
	    for(int r=0;r<this.D;r++)
	    	f.format("'    D%5d",r+1);
	    System.out.println();
	    for(int r=0;r<this.R;r++)
	    	f.format("%5d", this.RPer[r]);
	    for(int r=0;r<this.N;r++)
	    	f.format("%5d", this.NTot[r]);
	    for(int r=0;r<this.D;r++){
	    	f.format("%5d", this.DTot[r]);
	    	f.format("%5d", this.Dper[r]);
	    }
	    System.out.println();
	    System.out.println("************************************************************************");
	    
	    if(Progen.includeSR){
		    //����ռ���Դ������
		    System.out.println("SPATIAL  RESOURCE REQUESTS:");
		    System.out.print("taskGroupNr   #RequestTask ");
		    for(int i=0;i<this.nrOfSRType;i++){
		    	for(int k=0;k<this.typeSR[i].kind;k++){
		    		if(this.typeSR[i].dimension==1)
		    			System.out.print("  "+this.typeSR[i].name+(k+1));
		    		if(this.typeSR[i].dimension==2)
		    			System.out.print("   "+this.typeSR[i].name+(k+1));
		    		if(this.typeSR[i].dimension==3)
		    			System.out.print("    "+this.typeSR[i].name+(k+1));
		    	}   	
		    }
		    System.out.println();
		    System.out.println("------------------------------------------------------------------------");
		    for(int g=0;g<this.taskGroup.size();g++){
		    	TaskGroup group = (TaskGroup)this.taskGroup.get(g);
		    	f.format("%3d     				",g+1);
		    	for(int i=0;i<this.nrOfSRType;i++){
		    		for(int r=this.typeSR[i].startNr;r<=this.typeSR[i].endNr;r++){
		    			if(this.typeSR[i].dimension==1)
		    				System.out.print("("+group.requestSR[r].x+")  ");
		    			if(this.typeSR[i].dimension==2)
		    				System.out.print("("+group.requestSR[r].x+","+group.requestSR[r].y+") ");
		    			if(this.typeSR[i].dimension==3)
		    				System.out.print("("+group.requestSR[r].x+","+group.requestSR[r].y+","+group.requestSR[r].z+")");
		    			System.out.print(" ");
		    		}
		    	}
		    	System.out.println();
		    	//��������������������������������
		    	TreeSet ts = new TreeSet(group.requestTaskSet);//����һ��TreeSet,TreeSet��������
		    	ts.comparator();
		    	Iterator iter = ts.iterator(); 
				while(iter.hasNext()){
					int requestTaskNr = (Integer)iter.next();
					f.format(" 				%d	  	", requestTaskNr+1);//�����ʾ��ʱ���1
					for(int i=0;i<this.nrOfSRType;i++){
			    		for(int r=this.typeSR[i].startNr;r<=this.typeSR[i].endNr;r++){
			    			if(this.typeSR[i].dimension==1)
			    				f.format("("+"%d"+")  ",this.jobs[requestTaskNr].requestSR[r].x);
			    			if(this.typeSR[i].dimension==2)
			    				f.format("("+"%d"+",%d"+") ",this.jobs[requestTaskNr].requestSR[r].x,this.jobs[requestTaskNr].requestSR[r].y);
			    			if(this.typeSR[i].dimension==3)
			    				f.format("("+"%d"+",%d"+",%d"+")",this.jobs[requestTaskNr].requestSR[r].x,this.jobs[requestTaskNr].requestSR[r].y,this.jobs[requestTaskNr].requestSR[r].z);
			    			System.out.print(" ");
			    		}
			    	}
					System.out.println();//��������������һ��������
				}   	
		    }
		    
		    //��������ռ���Դ������
		    System.out.println("************************************************************************");
		    System.out.println("SPATIAL  RESOURCE AVAILABILITIES:");
		    for(int i=0;i<this.nrOfSRType;i++){
		    	for(int k=0;k<this.typeSR[i].kind;k++){
		    		if(this.typeSR[i].dimension==1)
		    			System.out.print(this.typeSR[i].name+(k+1)+"  ");
		    		if(this.typeSR[i].dimension==2)
		    			System.out.print(this.typeSR[i].name+(k+1)+"   ");
		    		if(this.typeSR[i].dimension==3)
		    			System.out.print(this.typeSR[i].name+(k+1)+"    ");
		    	}   	
		    }
		    System.out.println();
		    for(int i=0;i<this.nrOfSRType;i++){
		    	for(int r=this.typeSR[i].startNr;r<=this.typeSR[i].endNr;r++){
		    		if(this.typeSR[i].dimension==1)
		    			System.out.print("("+this.SR[r].avail.x+")  ");
		    		if(this.typeSR[i].dimension==2)
		    			System.out.print("("+this.SR[r].avail.x+","+this.SR[r].avail.y+") ");
		    		if(this.typeSR[i].dimension==3)
		    			System.out.print("("+this.SR[r].avail.x+","+this.SR[r].avail.y+","+this.SR[r].avail.z+")");
		    		System.out.print(" ");
		    	}
		    }
		    System.out.println();
		    
		    //��������ռ���Դ����
		    System.out.println("************************************************************************");
		    System.out.println("SPATIAL RESOURCE ORIENTATION:");
		    for(int i=0;i<this.nrOfSRType;i++){
		    	for(int k=0;k<this.typeSR[i].kind;k++){
		    		if(this.typeSR[i].dimension==1)
		    			System.out.print(this.typeSR[i].name+(k+1)+"  ");
		    		if(this.typeSR[i].dimension==2)
		    			System.out.print(this.typeSR[i].name+(k+1)+"   ");
		    		if(this.typeSR[i].dimension==3)
		    			System.out.print(this.typeSR[i].name+(k+1)+"    ");
		    	}   	
		    }
		    System.out.println();
		    for(int i=0;i<this.nrOfSRType;i++){
		    	for(int r=this.typeSR[i].startNr;r<=this.typeSR[i].endNr;r++){
		    		if(this.typeSR[i].dimension==1)
		    			System.out.print("("+this.SR[r].orientation.x+")  ");
		    		if(this.typeSR[i].dimension==2)
		    			System.out.print("("+this.SR[r].orientation.x+","+this.SR[r].orientation.y+") ");
		    		if(this.typeSR[i].dimension==3)
		    			System.out.print("("+this.SR[r].orientation.x+","+this.SR[r].orientation.y+","+this.SR[r].orientation.z+")");
		    		System.out.print(" ");
		    	}
		    }
		    System.out.println();
		    
		    //�����ά�ռ���Դ������͹�������ʽ������,�����
		    if(Utility.genPolygon  ){
		    	System.out.println("************************************************************************");
			    System.out.println("POLYGON STYLE 2-DIMENSIONAL SPATIAL  RESOURCE REQUESTS:");
			    System.out.println("jobnr. mode duration  EST   LST  REQSP   VertexNr  SHAPE" );
			    System.out.println("------------------------------------------------------------------------");
			    for(int j=0;j<this.nrOfJobs;j++){
			    	for(int m=0;m<this.jobs[j].nrOfModes;m++){
			    		if(m==0){
			    			f.format("%3d    %3d   %3d   ",j+1,m+1,this.jobs[j].modes[m].duration);
			    			if(j==0){
			    				System.out.print("    ");
			    				for(int pnr=0;pnr<this.nrOfPro;pnr++){
			    					System.out.print(this.relDate[pnr]+1);//+1��Ϊ����ʱ���1��ʼ��
			    					if(pnr<this.nrOfPro-1)
			    						System.out.print("/");
			    				}
			    				System.out.print("   ");
			    				for(int pnr=0;pnr<this.nrOfPro;pnr++){
			    					System.out.print(this.relDate[pnr]+1);
			    					if(pnr<this.nrOfPro-1)
			    						System.out.print("/");
			    				}
			    				System.out.print("   ");
			    			}
			    			else if(j==this.nrOfJobs-1){
			    				System.out.print("   ");
			    				for(int pnr=0;pnr<this.nrOfPro;pnr++){
			    					System.out.print(this.CPMT[pnr]+1);
			    					if(pnr<this.nrOfPro-1)
			    						System.out.print("/");
			    				}
			    				System.out.print("   ");
			    				for(int pnr=0;pnr<this.nrOfPro;pnr++){
			    					System.out.print(this.CPMT[pnr]+1);
			    					if(pnr<this.nrOfPro-1)
			    						System.out.print("/");
			    				}
			    				System.out.print("   ");
			    			}
			    			else{
				    			f.format("  %3d", this.jobs[j].T.EST+1);//+1��Ϊ����ʱ���1��ʼ��
				    			f.format("  %3d  ", this.jobs[j].T.LST+1);
			    			}
			    			for(int i=0;i<this.nrOfSRType;i++){
					    		for(int r=this.typeSR[i].startNr;r<=this.typeSR[i].endNr;r++){
					    			f.format("("+"%d"+",%d"+")  ",this.jobs[j].requestSR[r].x,this.jobs[j].requestSR[r].y);
					    		}
			    			}
			    			if(this.jobs[j].shape == null){
			    				f.format("  %3d   ", 0);
		    					System.out.print("null");
		    				}
			    			else{
			    				f.format("  %3d   ", this.jobs[j].shape.vCount);
			    				for(int i=0;i<this.jobs[j].shape.vCount;i++){
			    					f.format("("+"%.2f"+",%.2f"+")",this.jobs[j].shape.vSet[i].x,this.jobs[j].shape.vSet[i].y);
			    				}
			    			}
			    			System.out.println();
			    		}
			    	}
			    }
		    }
		    System.out.println("************************************************************************");
	    }
	    
	    utility.reSetPrintStream();
	}	

}


class Job {
	int nrOfModes;
	Mode[] modes;
	
	Time T;
	
	int nrOfPred; //ֱ�ӽ�ǰ���Ŀ
	int nrOfSucc; //ֱ�ӽ�����Ŀ
	Set dirPred; //ֱ�ӽ�ǰ�����
	Set dirSucc; //ֱ�ӽ�������
	Set inDirPred;//��ǰ�����
	Set inDirSucc;//��������
	
	//=====================�ռ���Դ���==================
	boolean inGroup;
	SRmeasure[] requestSR;//��¼�Ը��ռ���Դ������(��������������)
	
	MyPolygon shape;
		
	Job(){
		this.nrOfModes = 0;
		this.modes = new Mode[Progen.maxNrOfModes];
		for(int i=0;i<this.modes.length;i++)
			this.modes[i] = new Mode();
		this.T = new Time();
		this.nrOfPred = 0;
		this.nrOfSucc = 0;
		this.dirPred = new HashSet();
		this.dirSucc = new HashSet();
		this.inDirPred = new HashSet();
		this.inDirSucc = new HashSet();
		
		//=====================�ռ���Դ���==================
		this.inGroup=false;
		this.requestSR=new SRmeasure[Progen.maxNrOfSR];
		for(int i=0;i<requestSR.length;i++)
			this.requestSR[i] = new SRmeasure();
	}
	
	/**
	 * ��typeΪ'd'(�����ڵ�һ����Դ����),��ѡ��ģʽ0,����������С��ģʽ. ����ѡ��ǰ���type������Դ���Ϊresnr����Դ��������ģʽ. ģʽ��¼��T������
	 * @param type
	 * @param resnr
	 */
	 void selectMode(char type, int resnr) {
		// TODO Auto-generated method stub
		int maxReq=-1,maxReqMode=0;
		switch(type){
		case 'd':
			this.T.mode = 0;break; //ע��,����ģʽ��Ŵ�0��ʼ,��ģʽ����ʱ�䰴��������,������ʱ��̵�ģʽ��Ϊ���Ϊ0��ģʽ
		case 'R':
			for(int m=0;m<this.nrOfModes;m++){ //���ģʽ��Ŵ�0��ʼ��nrofModes-1
				if(maxReq<this.modes[m].RResReq[resnr]){
					maxReq = this.modes[m].RResReq[resnr];
					maxReqMode = m;
				}
			}
			this.T.mode = maxReqMode;
			break;
		case 'D':
			for(int m=0;m<this.nrOfModes;m++){//���ģʽ��Ŵ�0��ʼ��nrofModes-1
				if(maxReq<this.modes[m].DResReq[resnr]){
					maxReq = this.modes[m].DResReq[resnr];
					maxReqMode = m;
				}
			}
			this.T.mode = maxReqMode;
		}
	}

	/**
	 * ����ĳ����ģʽΪ1,ģʽ����ʱ��Ϊ0,��Դ����Ϊ0
	 * @param R �ɸ�����Դ������
	 * @param N ���ɸ�����Դ������
	 * @param D ˫����Դ������
	 */
	void dumMyJob(int R,int N,int D){
		nrOfModes =1;
		modes[0].duration=0;
		for(int i=0;i<R;i++)
			modes[0].RResReq[i]=0;
		for(int i=0;i<N;i++)
			modes[0].NResReq[i]=0;
		for(int i=0;i<D;i++)
			modes[0].DResReq[i]=0;		
	}
}

class Mode {
	int duration; //ģʽ�ĳ���ʱ��
	int[] RResReq;
	int[] DResReq;
	int[] NResReq;//��3����Դ��ÿ�ֵ�������
	
	Mode(){
		this.duration = 0;
		this.RResReq = new int[Progen.maxRDN];
		this.DResReq = new int[Progen.maxRDN];
		this.NResReq = new int[Progen.maxRDN];
	}
}


class Time{
	int mode;
	int EST;//���翪ʼʱ��
	int EFT;//�������ʱ��
	int LST;//����ʼʱ��
	
	Time(){
		this.mode = 0;
		this.EST = 0;
		this.EFT = 0;
		this.LST = 0;
	}
}


