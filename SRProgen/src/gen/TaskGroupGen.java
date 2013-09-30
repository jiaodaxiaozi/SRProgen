package gen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class TaskGroupGen {
	Project p;//���ڱ�������ĸ�����,����Ŀ������	
	Utility utility;
	Demand demand;
	
	TaskGroupGen(Project p){
		this.p = p;
	}
	
	
	/**
	 * ����������
	 */
	 void genTaskGroupMain(Demand demand,Utility utility) {
		 this.demand = demand;
		 this.utility = utility;
		this.init();
		//����ÿ������Ŀ��������
		for(int pnr=0;pnr<p.nrOfPro;pnr++){
			genTaskGroup(pnr);
		}		
	}

	/**
	 * ����һ������Ŀ��������
	 * @param pnr
	 * @return
	 */
	boolean genTaskGroup(int pnr) {
		int trialNr;// ��ʾ��ǰ�ǵڼ�����������pnr�������Ŀ������������
//		boolean success = false; //��ʾһ������Ŀ���������Ƿ����ɳɹ�
		ArrayList<TaskGroup> TGtemp=new ArrayList<TaskGroup>();
		for(trialNr=1;trialNr<=demand.base.maxTrials;trialNr++){
			int gnr;
			//ѭ�����ɴ�����Ŀ��p.nrOfTaskG��������
			for(gnr=0;gnr<this.p.nrOfTaskG[pnr];gnr++){
				String pnrStr,gnrStr,remark;
				pnrStr=new Integer(pnr+1).toString();
				gnrStr=new Integer(gnr+1).toString();
				remark = pnrStr+"�ĵ�"+gnrStr+"��";
				int testNr=p.pro[pnr];//��ÿ��������ɳ��Ե����ɴ���testNrΪ�������Ŀ������������
				for(;testNr>0;testNr--){
					TaskGroup tempTaskGroup = new TaskGroup();
					tempTaskGroup.initGroup();
					tempTaskGroup.belongPro=pnr;//���õ�ǰ��������������������Ŀ
					tempTaskGroup.genGroupData(this.demand.base, utility);
					testNrOfQTaskWithTypeMaxDemand(tempTaskGroup);
					if(genOneGroup(pnr,gnr,tempTaskGroup)){
						this.utility.error(!genRequsetTask(tempTaskGroup),44,demand.errFilePath,remark);//��������������
						this.utility.error(!genMoveTask(tempTaskGroup),45,demand.errFilePath,remark);//�����ƶ�������)
						TGtemp.add(tempTaskGroup);
						break;
					}					
				}
				if(testNr==0){//����������ڳ��������㹻��κ�����ʧ��,��ʱ���������������Ŀ��������������������ʧ��,���������ɺ���ļ�����������
					this.utility.error(true,43,demand.errFilePath,remark);
					break;
				}
			}
			if(gnr==this.p.nrOfTaskG[pnr]){
				for(int index=0;index<TGtemp.size();index++){
					p.taskGroup.add(TGtemp.get(index));
				}
				return true;
			}
			else{//��ʱ�Դ�����Ŀ�����������е�������
				//����մ���Ŀ�Ѿ����ɵ�������
				TGtemp.clear();
				//���������Ŀ�����������inGroup���
				for(int j=this.p.SJ[pnr];j<=this.p.FJ[pnr];j++)
					this.p.jobs[j].inGroup=false;
			}
		}
		
		this.utility.error(true,42,demand.errFilePath,new Integer(pnr+1).toString());//���趨���Դ�����,û�гɹ���������Ŀ��pnr������.
		return false;
	}


	/**
	 * ����һ������Ŀ��һ��������
	 * @param pnr ����Ŀ���
	 * @param gnr ��������
	 * @return
	 */
	private boolean genOneGroup(int pnr,int gnr,TaskGroup tempTaskGroup) {
		int curTask; //��ǰѡ�е�������
		int index;// ��������Ӻ�ѡ������ѡԪ��
		ArrayList candidateTaskSet = new ArrayList();//��ѡ��չ�����񼯺�
		//���㵱ǰ����������ı��
		int groups=0;
		for(int i=0;i<pnr;i++){
			groups+=this.p.nrOfTaskG[i];
		}
		tempTaskGroup.groupN=groups+gnr;
		
		//�ѵ�ǰ��Ŀ�����в����κ��������е���������ѡ��
		for(int i=p.SJ[pnr];i<=p.FJ[pnr];i++){
			if(!p.jobs[i].inGroup){
				candidateTaskSet.add(i);
			}
		}
		if(candidateTaskSet.isEmpty())
			return false; //��ʱ������Ŀ�е����л���Ѿ�����������������
		
		//���ɴ�������ĵ�һ������curTask
		index = utility.random.nextInt(candidateTaskSet.size());
		curTask = (Integer)candidateTaskSet.get(index);
		candidateTaskSet.clear();//���,��Ϊ�������չ������ͬ��
		
		for(;tempTaskGroup.taskSet.size()<tempTaskGroup.nrOfTask;){
			tempTaskGroup.taskSet.add(curTask);//�ѵ�ǰ���������������
			p.jobs[curTask].inGroup = true;
					
			Iterator iter = p.jobs[curTask].dirPred.iterator(); 
			while (iter.hasNext()) {
				int tempTask = (Integer)iter.next();
				//�ѵ�ǰ�����ֱ�ӽ�ǰ�����еĻ�����ĳһ�������е���������ѡ��
				//(ע��:�����ӵ���������Ѿ��ں�ѡ����,����ʹ�õ���List��������,������Ҫ����.
				//����ʹ��Set,���ǲ����״�Set�����ѡԪ��),�����鿪ʼ�����β������Ϊ������������
				if((!p.jobs[tempTask].inGroup)&&(!candidateTaskSet.contains(tempTask))&&(tempTask!=0)&&(tempTask!=(p.nrOfJobs-1)))  
					candidateTaskSet.add(tempTask);
			}
			iter = p.jobs[curTask].dirSucc.iterator(); 
			while (iter.hasNext()) {
				int tempTask = (Integer)iter.next();
				if((!p.jobs[tempTask].inGroup)&&(!candidateTaskSet.contains(tempTask))&&(tempTask!=0)&&(tempTask!=(p.nrOfJobs-1)))  //�ѵ�ǰ�����ֱ�ӽ��󼯺��еĻ�����ĳһ�������е���������ѡ��
					candidateTaskSet.add(tempTask);
			}
			
			if(candidateTaskSet.isEmpty()){
				if(tempTaskGroup.taskSet.size()<tempTaskGroup.nrOfTask){
					//�������ʧ��,Ҫ���Ѽ��뵽�������е��������±��Ϊ������������,�����������
					iter = tempTaskGroup.taskSet.iterator();
					while (iter.hasNext()){
						p.jobs[(Integer)iter.next()].inGroup = false;
					}
					tempTaskGroup.taskSet.clear();
					return false; //û�дﵽ�涨��������,ȴû�п���չ��������
				}
				else{
					//����ע�͵������ƶ��˺����ⲿ��
//					genRequsetTask(tempTaskGroup);//��������������
//					p.taskGroup.add(tempTaskGroup);
					return true;//��ʱ��Ȼ��������չ��,�������ڼ�����һ���µ�curTask,��������������ôﵽҪ��,�������ɳɹ�
				}				
			}		
			//����Ӻ�ѡ���������ѡ��һ������,��Ϊ�µĵ�ǰ����
			else
			{
				index = utility.random.nextInt(candidateTaskSet.size());
				curTask = (Integer)candidateTaskSet.get(index);
				candidateTaskSet.remove(index);//����ѡ����Ϊ��ǰ���������Ӻ�ѡ���Ƴ�			
			}			
		}		
		return true;
	}
	
	/**
	 * ���ں������ɶԿռ���Դ�����ʱ��,Ҫ����Դ���󻮷ֵ�ÿ��������������,
	 * ����Ҫ��֤������������������벻���ڶ�ĳ������Դ���������.
	 * ��һ�������������������,Ҳ�޷��޸��������.����Ҫ������ǰ�����������
	 * @param taskGroup
	 */
	void testNrOfQTaskWithTypeMaxDemand(TaskGroup taskGroup){
		for(int i=0;i<this.p.nrOfSRType;i++){
			int max=this.p.typeSR[i].maxDemand.x;
			if(this.p.typeSR[i].dimension==2){
				max=(max>this.p.typeSR[i].maxDemand.y)?max:this.p.typeSR[i].maxDemand.y;
			}
			if(this.p.typeSR[i].dimension==3){
				max=(max>this.p.typeSR[i].maxDemand.y)?max:this.p.typeSR[i].maxDemand.y;
				max=(max>this.p.typeSR[i].maxDemand.z)?max:this.p.typeSR[i].maxDemand.z;
			}
			taskGroup.nrOfQTask=(taskGroup.nrOfQTask<max)?taskGroup.nrOfQTask:max;
		}
		this.utility.error(taskGroup.nrOfQTask<this.demand.base.minNofQJob, 41,demand.errFilePath,"");
	}

	boolean genRequsetTask(TaskGroup taskGroup){
		if(0==taskGroup.nrOfQTask)
			return true;		
		int index,curTask;
		ArrayList candidateRequsetTaskSet = new ArrayList();//��ѡ��չ�����������񼯺�
		//����Ѱ�Ҵ���������û�н�ǰ�ڴ�������������,���뵽candidateRequsetTaskSet��(��ΪҪ��֤�����������������һ����������)
		Iterator iter = taskGroup.taskSet.iterator(); 
		while (iter.hasNext()){
			int tempTask = (Integer)iter.next();
			if(!this.utility.hasIntersection(this.p.jobs[tempTask].dirPred, taskGroup.taskSet))
				candidateRequsetTaskSet.add(tempTask);
		}
		if(candidateRequsetTaskSet.isEmpty())
			return false; //�������Ӧ���ǲ����ܷ�����
		//������ӵ�һ������������
		index = utility.random.nextInt(candidateRequsetTaskSet.size());
		curTask = (Integer)candidateRequsetTaskSet.get(index);
		taskGroup.requestTaskSet.add(curTask);//�ȱ�֤����һ���޽�ǰ���������е�����������
		candidateRequsetTaskSet.clear();
		
		//���������������������
		iter = taskGroup.taskSet.iterator(); 
		while (iter.hasNext()){
			candidateRequsetTaskSet.add((Integer)iter.next());
		}
		candidateRequsetTaskSet.remove(new Integer(curTask));//��ѡ�����Ƴ�����ӵĵ�һ������
	     
		//Ȼ������������������������ѡ��������Ϊ����������ֱ�����������������ﵽָ����ֵ��
		for(int i=1;i<taskGroup.nrOfQTask;i++){
			index = utility.random.nextInt(candidateRequsetTaskSet.size());
			curTask = (Integer)candidateRequsetTaskSet.get(index);
			taskGroup.requestTaskSet.add(curTask);
			candidateRequsetTaskSet.remove(index);			
		}
		return true;
	}
	


    /**
     * �����ƶ�������û�й涨�ṹԼ������ֱ���������������ѡ��ָ����Ŀ��������Ϊ�ƶ������񼴿�
     * @param taskGroup
     * @return
     */
	boolean genMoveTask(TaskGroup taskGroup){
		int index,curTask;
		ArrayList candidateMoveTaskSet = new ArrayList();//��ѡ��չ���ƶ��������񼯺�
		Iterator iter = taskGroup.taskSet.iterator(); 
		iter = taskGroup.taskSet.iterator(); 
		while (iter.hasNext()){
			candidateMoveTaskSet.add((Integer)iter.next());
		}
		for(int i=0;i<taskGroup.nrOfMTask;i++){
			index = utility.random.nextInt(candidateMoveTaskSet.size());
			curTask = (Integer)candidateMoveTaskSet.get(index);
			taskGroup.moveTaskSet.add(curTask);
			candidateMoveTaskSet.remove(index);			
		}
		return true;
	}

	/**
	 * ��ʼ��
	 */
	void init(){
//		for(int i=0;i<p.nrOfTaskG;i++){
//			p.taskGroup[i].initGroup();
//		}
		this.p.taskGroup.clear();
		//��ÿ������Ƿ����������еı�Ƕ���Ϊflase
		for(int i=0;i<p.nrOfJobs;i++){
			p.jobs[i].inGroup = false;
		}
	}
		
}
