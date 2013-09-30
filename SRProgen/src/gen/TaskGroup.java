package gen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TaskGroup {
	int nrOfTask;//�����麬�еĻ��
	int nrOfQTask;//�����麬�е������Ի��
	int nrOfMTask;//�����麬�е��ƶ���������
	SRmeasure[] requestSR;//�Ը��ռ���Դ������
	Set taskSet;//����������Ļ��ż���
	Set requestTaskSet;//����������
	Set moveTaskSet;//�ƶ�������
	int belongPro;//����������Ŀ���
	int groupN; //����������
	int EST;//������������翪ʼʱ��
	int EFT;//����������������ʱ��
	
//	Polygon shape;
	
	TaskGroup(){
		this.requestSR = new SRmeasure[Progen.maxNrOfSR];
		for(int i=0;i<requestSR.length;i++)
			this.requestSR[i] = new SRmeasure();
		this.taskSet = new HashSet();
		this.requestTaskSet = new HashSet();
		this.moveTaskSet = new HashSet();
	}
	
	void genGroupData(BaseStruct base,Utility utility){
		this.nrOfTask=base.minNofJobTask+utility.random.nextInt(base.maxNofJobTask-base.minNofJobTask+1);
		this.nrOfQTask=base.minNofQJob+utility.random.nextInt(base.maxNofQJob-base.minNofQJob+1);
		this.nrOfMTask=base.minNofMJob+utility.random.nextInt(base.maxNofMJob-base.minNofMJob+1);
		//�������������ĺ�����(���������,��������)[���ֲ���������Ϊ���,����������ȡ[4,8],��������������ȡ[2,5],��һ�����������Ϊ4,�������������Ϊ5,�ͳ����˲�����]
		if(this.nrOfQTask>this.nrOfTask)
			this.nrOfQTask=this.nrOfTask;
		if(this.nrOfMTask>this.nrOfTask)
			this.nrOfMTask=this.nrOfTask;
	}
	
	void initGroup(){
		this.nrOfTask=0;
		this.nrOfQTask=0;
		this.nrOfMTask=0;
		for(int i=0;i<this.requestSR.length;i++){
			this.requestSR[i].x=0;
			this.requestSR[i].y=0;
			this.requestSR[i].z=0;
		}
		this.taskSet.clear();
		this.requestTaskSet.clear();
		this.moveTaskSet.clear();
		this.belongPro=-1;
		this.groupN=-1;
	}
	
	void calcESTAndEFT(Project p){
		this.EST=Progen.maxHorizon;//��ʼ��Ϊ���ֵ
		this.EFT=0;//��ʼ��Ϊ��Сֵ
		Iterator iter = this.taskSet.iterator(); 
		while(iter.hasNext()){
			int taskNr=(Integer)iter.next();
			this.EST=(this.EST<(p.jobs[taskNr].T.EST))?this.EST:(p.jobs[taskNr].T.EST);
			this.EFT=(this.EFT>(p.jobs[taskNr].T.EFT))?this.EFT:(p.jobs[taskNr].T.EFT);	
		}	
	}
	
}
