package gen;

import java.util.Iterator;

public class SRreqgen {
	Project p;//���ڱ�������ĸ�����,����Ŀ������
	int [][] SRresDens;
	Utility utility;
	Demand demand;
	
	SRreqgen(Project p){
		this.p = p;
		SRresDens = new int[Progen.maxNrOfTaskGroup][Progen.maxNrOfSR];
	}
	
	void SRresReqMain(Demand demand,Utility utility){
		this.utility = utility;
		this.demand = demand;

		init();
		for(int i=0;i<this.p.nrOfSRType;i++){
			SRresReqSub(this.p.typeSR[i]);
		}
	}
	
	private void init() {
		for(int g=0;g<Progen.maxNrOfTaskGroup;g++){
			for(int r=0;r<Progen.maxNrOfSR;r++)
				SRresDens[g][r]=0;
		}
		for(int j=0;j<this.p.jobs.length;j++){
			for(int r=0;r<this.p.jobs[j].requestSR.length;r++)
				this.p.jobs[j].requestSR[r].clear();//������һ����Ŀռ���Դ��Ҫ����Ϊ0(�������������)
		}
		
	}

	/**
	 * Ϊÿ������������ָ����type��Դ�����е�ÿ�־�����Դ������
	 * @param type
	 */
	private void SRresReqSub(SRType type) {
		genSRresDens(type);
		for(int g=0;g<this.p.taskGroup.size();g++){
			initSRReq(g,type);
			//��ȡ��Դ������
			for(int r=type.startNr;r<=type.endNr;r++){
				SRreqAm(g,r,type);
			}
		}
	}
	
	/**
	 * ������type��ռ���Դ�е�ÿ�־�����Դ�Ƿ��������Դ�����ܶȾ���(����ָ������Դ��������)
	 * @param type
	 */
	private void genSRresDens(SRType type) {
		testSRResDens(type);
		for(int g=0;g<this.p.taskGroup.size();g++){
			initSRResDens(g,type);
			setMinSRResDens(g,type);
		}
		setSRResDens(type);
	}
	
	/**
	 * ��⵱ǰ�Ŀռ���Դ�Ĳ���(�����ݻ��ļ�������ɵĲ���)�Ƿ�������,����,�ɾ����ľ�������¼����,����ֻ����
	 * @param type
	 */
	private void testSRResDens(SRType type) {
		if(this.utility.error(type.maxR>type.kind,51,demand.errFilePath,type.name))
			type.maxR=type.kind;
		if(this.utility.error(type.minR>type.maxR,52,demand.errFilePath,type.name))
			type.minR=type.maxR;
		if(type.kind>0){
			this.utility.error(type.SRF<(float)type.minR/(float)type.kind,53,demand.errFilePath,type.name);
			this.utility.error(type.SRF>(float)type.maxR/(float)type.kind,54,demand.errFilePath,type.name);
		}
	}
	
	/**
	 * ��ʼ��������g��type���е�ÿ����Դ������Ҫ
	 * @param g
	 * @param type
	 */
	private void initSRResDens(int g, SRType type) {
		for(int r=type.startNr;r<=type.endNr;r++)
			this.SRresDens[g][r]=0;
	}
	
	/**
	 * ʹ����g��type����Դ��������������Ҫ�����С��������(����ĳ����Դ,�ڶ�ά�ܶȾ����аѶ�Ӧ��Ԫ����Ϊ1)
	 * @param g
	 * @param type
	 */
	private void setMinSRResDens(int g, SRType type) {
		for(int actNr=0,r;actNr<type.minR;){
			r=type.startNr+this.utility.random.nextInt(type.kind);
			if(this.SRresDens[g][r]==0){
				this.SRresDens[g][r]=1;
				actNr++;
			}
		}
	}
	
	/**
	 * ʹ������Դ�����ܶȾ�����ܶȴﵽ�趨����Դ��������(��Χ��,��Ϊ����ƫ��),
	 * ���ѵ�ǰ��SRF������Ҫ���SRF
	 * @param type
	 */
		float actSRF;
		private void setSRResDens(SRType type) {
		int element,freeElements;
		
		actSRF=(float)type.minR/(1>type.kind?1:type.kind);
		freeElements=0;
//		for(int g=0;g<this.p.taskGroup.size();g++)
		freeElements=this.p.taskGroup.size()*(type.maxR-type.minR);
		while((type.SRF>actSRF)&&(freeElements>0)){
			element=1+this.utility.random.nextInt(freeElements);//����random.nextInt(freeElements)����0��freeElements-1��Щ��,��+1
			determineSR(element,type);
			freeElements--;
			actSRF=actSRF+(float)1/(float)(this.p.taskGroup.size()*type.kind);
		}
		this.utility.error(actSRF<(type.SRF*(1-this.demand.base.reqTol)), 55,demand.errFilePath,type.name);
		this.utility.error(actSRF>(type.SRF*(1+this.demand.base.reqTol)), 56,demand.errFilePath,type.name);
	}
	
	private void determineSR(int element, SRType type) {
		int rchosen,sum=0;
		for(int g=0;g<this.p.taskGroup.size();g++){
			int k=0;
			for(int r=type.startNr;r<=type.endNr;r++){
				if(this.SRresDens[g][r]==1)
					k++;
			}
			//��ǰ������һ������Ҫ����Դ��
			sum=sum+(type.maxR-k);
			if(sum>element){
				rchosen=1+this.utility.random.nextInt(type.kind-k);
				for(int r=type.startNr,n=0;r<=type.endNr;r++){
					if(this.SRresDens[g][r]==0)
						n++;
					if(n==rchosen){
						this.SRresDens[g][r]=1;
						return;
					}
				}
			}
		}
	}
	
	private void initSRReq(int g, SRType type) {
		for(int r=type.startNr;r<=type.endNr;r++){
			((TaskGroup)this.p.taskGroup.get(g)).requestSR[r].x=0;
			((TaskGroup)this.p.taskGroup.get(g)).requestSR[r].y=0;
			((TaskGroup)this.p.taskGroup.get(g)).requestSR[r].z=0;
		}
	}
	
	private void SRreqAm(int g, int r, SRType type) {
		//�Կռ���Դ������
		if(this.SRresDens[g][r]==1){
			((TaskGroup)this.p.taskGroup.get(g)).requestSR[r].x=type.minDemand.x+this.utility.random.nextInt(type.maxDemand.x-type.minDemand.x+1);
			if(type.dimension>1){
				((TaskGroup)this.p.taskGroup.get(g)).requestSR[r].y=type.minDemand.y+this.utility.random.nextInt(type.maxDemand.y-type.minDemand.y+1);
//				//������״--��ʼ==========================================
//				TaskGroup gtemp = (TaskGroup)this.p.taskGroup.get(g);
//				int k = 3+this.utility.random.nextInt(4);
//				gtemp.shape = Utility.genPolygon(gtemp.requestSR[r].x, gtemp.requestSR[r].y, k);
//				//������״--����==========================================
				
				if(type.dimension>2){
					((TaskGroup)this.p.taskGroup.get(g)).requestSR[r].z=type.minDemand.z+this.utility.random.nextInt(type.maxDemand.z-type.minDemand.z+1);
				}					
			}
			//����������g�и������������type�����Դr��������
			genRequsetTaskRes(g,r,type);
		}
	}

	/**
	 * ����������g�и������������type�����Դr��������
	 * @param g
	 * @param r
	 * @param type
	 */
	private void genRequsetTaskRes(int g, int r, SRType type) {
		TaskGroup group=((TaskGroup)this.p.taskGroup.get(g));
		int requestTaskNr = group.requestTaskSet.size();
		
		if(type.dimension==1){
			if(requestTaskNr>group.requestSR[r].x){//��Ϊ������������ɵ�,���Կ��ܻ�����������
				//���������ֵ�������Ĳ���:�ٵ��������������((��Ϊ���������������ɵ�ʱ��,�Ѿ�
				//��֤����������������һ����������������))�ڵ���������������������
				if(this.utility.random.nextFloat()<0.5)
					group.requestSR[r].x=requestTaskNr;//��ʱ������������Ǿ���;
				else
					group.requestSR[r].x=type.maxDemand.x;
			}
			//��������
			divideRequest(1, 'x', group, requestTaskNr,r);
		}
		if(type.dimension==2){
			int min=group.requestSR[r].x<group.requestSR[r].y?group.requestSR[r].x:group.requestSR[r].y;
			int max=group.requestSR[r].x>group.requestSR[r].y?group.requestSR[r].x:group.requestSR[r].y;
			if(requestTaskNr<=min){
				int choosen=this.utility.random.nextInt(2);
				if(choosen==0){
					divideRequest(2, 'x', group, requestTaskNr,r);
				}
				else{
					divideRequest(2, 'y', group, requestTaskNr,r);
				}
			}
			if((requestTaskNr>min)&&(requestTaskNr<=max)){
				if(group.requestSR[r].x==max){
					divideRequest(2, 'x', group, requestTaskNr,r);
				}
				else{
					divideRequest(2, 'y', group, requestTaskNr,r);
				}
			}
			if(requestTaskNr>max){
				//�ȵ������������Դr������,���������������������
				if(type.maxDemand.x>type.maxDemand.y){
					if(this.utility.random.nextFloat()<0.5)
						group.requestSR[r].x=requestTaskNr;//��ʱ������������Ǿ���;
					else
						group.requestSR[r].x=type.maxDemand.x;
					divideRequest(2, 'x', group, requestTaskNr,r);
				}					
				else{
					if(this.utility.random.nextFloat()<0.5)
						group.requestSR[r].y=requestTaskNr;//��ʱ������������Ǿ���;
					else
						group.requestSR[r].y=type.maxDemand.y;
					divideRequest(2, 'y', group, requestTaskNr,r);
				}									
			}
		}
		
		if(type.dimension==3){
			int xx=group.requestSR[r].x;
			int yy=group.requestSR[r].y;
			int zz=group.requestSR[r].z;
			//��8�����
			//��1�����
			if(requestTaskNr<=xx && requestTaskNr<=yy && requestTaskNr<=zz){
				int choosen=this.utility.random.nextInt(3);
				if(choosen==0)
					divideRequest(3, 'x', group, requestTaskNr,r);
				else if(choosen==1)
					divideRequest(3, 'y', group, requestTaskNr,r);
				else
					divideRequest(3, 'z', group, requestTaskNr,r);
			}
			//��2�����
			if(requestTaskNr>xx &&requestTaskNr<=yy && requestTaskNr<=zz){
				int choosen=this.utility.random.nextInt(2);
				if(choosen==0)
					divideRequest(3, 'y', group, requestTaskNr,r);
				else
					divideRequest(3, 'z', group, requestTaskNr,r);
			}
			//��3�����
			if(requestTaskNr>yy &&requestTaskNr<=xx && requestTaskNr<=zz){
				int choosen=this.utility.random.nextInt(2);
				if(choosen==0)
					divideRequest(3, 'x', group, requestTaskNr,r);
				else
					divideRequest(3, 'z', group, requestTaskNr,r);
			}
			//��4�����
			if(requestTaskNr>zz &&requestTaskNr<=xx && requestTaskNr<=yy){
				int choosen=this.utility.random.nextInt(2);
				if(choosen==0)
					divideRequest(3, 'x', group, requestTaskNr,r);
				else
					divideRequest(3, 'y', group, requestTaskNr,r);
			}
			//��5�����
			if(requestTaskNr<=xx &&requestTaskNr>yy && requestTaskNr>zz){
					divideRequest(3, 'x', group, requestTaskNr,r);
			}
			//��6�����
			if(requestTaskNr<=yy &&requestTaskNr>xx && requestTaskNr>zz){
				divideRequest(3, 'y', group, requestTaskNr,r);
			}	
			//��7�����
			if(requestTaskNr<=zz &&requestTaskNr>xx && requestTaskNr>yy){
				divideRequest(3, 'z', group, requestTaskNr,r);
			}
			//��8�����
			if(requestTaskNr>xx &&requestTaskNr>yy && requestTaskNr>zz){
				//�ȵ������������Դr������,���������������������
				int max=type.maxDemand.x>type.maxDemand.y?type.maxDemand.x:type.maxDemand.y;
				max=max>type.maxDemand.z?max:type.maxDemand.z;
				if(type.maxDemand.x==max){
					if(this.utility.random.nextFloat()<0.5)
						group.requestSR[r].x=requestTaskNr;//��ʱ������������Ǿ���;
					else
						group.requestSR[r].x=type.maxDemand.x;
					divideRequest(3, 'x', group, requestTaskNr,r);
				}
				if(type.maxDemand.y==max){
					if(this.utility.random.nextFloat()<0.5)
						group.requestSR[r].y=requestTaskNr;//��ʱ������������Ǿ���;
					else
						group.requestSR[r].y=type.maxDemand.y;
					divideRequest(3, 'y', group, requestTaskNr,r);
				}
				if(type.maxDemand.z==max){
					if(this.utility.random.nextFloat()<0.5)
						group.requestSR[r].z=requestTaskNr;//��ʱ������������Ǿ���;
					else
						group.requestSR[r].z=type.maxDemand.z;
					divideRequest(3, 'z', group, requestTaskNr,r);
				}
			}
					
		}
		
	}
	
	/**
	 * ��temp[temp.length-1]�����Ϊtemp.length-1��(��Ҫѡtemp.length-2���ָ��,��Щ�ָ���λ�ü�¼��temp[1]~temp[temp.length-2]��)
	 * @param temp
	 */
	void genSection(int[] temp){
		int endNr = temp[temp.length-1];
		int chooseFlag[] = new int[endNr];
		//�Ȱѱ�������Ԫ����Ϊ0
		for(int i=0;i<chooseFlag.length;i++)
			chooseFlag[i]=0;
		//��[1~endNr-1]��Χ���������temp.length-2����
		for(int i=0;i<temp.length-2;){
			int random = 1+this.utility.random.nextInt(endNr-1);
			if(chooseFlag[random]==0){
				chooseFlag[random]=1;
				i++;
			}
		}
		for(int i=1,j=1;i<temp.length-1;i++){
			for(;j<chooseFlag.length;j++){
				if(chooseFlag[j]==1){
					temp[i]=j;
					j++;
					break;
				}
			}
		}
	}
	
	void divideRequest(int SRdimension,char Dividedimension,TaskGroup group,int requestTaskNr,int r){
		int temp[] = new int[requestTaskNr+1];
		temp[0]=0;
		Iterator iter = group.requestTaskSet.iterator(); 
		if(SRdimension==1){
			temp[temp.length-1]=group.requestSR[r].x;
			genSection(temp);	
			for(int i=1;i<temp.length;i++){
				int task=(Integer)(iter.next());
				this.p.jobs[task].requestSR[r].x=temp[i]-temp[i-1];			
			}
		}
		if(SRdimension==2){
			if(Dividedimension=='x'){
				temp[temp.length-1]=group.requestSR[r].x;
				genSection(temp);
				for(int i=1;i<temp.length;i++){
					int task=(Integer)(iter.next());
					this.p.jobs[task].requestSR[r].x=temp[i]-temp[i-1];		
					this.p.jobs[task].requestSR[r].y=group.requestSR[r].y;
					//===========������״--��ʼ===========================
					if(Utility.genPolygon){
						this.p.jobs[task].shape = Utility.genPolygon(this.p.jobs[task].requestSR[r].x, this.p.jobs[task].requestSR[r].y);
					}
					//==========================================
				}
			}
			if(Dividedimension=='y'){
				temp[temp.length-1]=group.requestSR[r].y;
				genSection(temp);
				for(int i=1;i<temp.length;i++){
					int task=(Integer)(iter.next());
					this.p.jobs[task].requestSR[r].y=temp[i]-temp[i-1];		
					this.p.jobs[task].requestSR[r].x=group.requestSR[r].x;
					//============������״--��ʼ==============================
					if(Utility.genPolygon){
						this.p.jobs[task].shape = Utility.genPolygon(this.p.jobs[task].requestSR[r].x, this.p.jobs[task].requestSR[r].y);
					}
					//==========================================
				}
			}
		}
		if(SRdimension==3){
			if(Dividedimension=='x'){
				temp[temp.length-1]=group.requestSR[r].x;
				genSection(temp);
				for(int i=1;i<temp.length;i++){
					int task=(Integer)(iter.next());
					this.p.jobs[task].requestSR[r].x=temp[i]-temp[i-1];		
					this.p.jobs[task].requestSR[r].y=group.requestSR[r].y;
					this.p.jobs[task].requestSR[r].z=group.requestSR[r].z;
				}
			}
			if(Dividedimension=='y'){
				temp[temp.length-1]=group.requestSR[r].y;
				genSection(temp);
				for(int i=1;i<temp.length;i++){
					int task=(Integer)(iter.next());
					this.p.jobs[task].requestSR[r].y=temp[i]-temp[i-1];		
					this.p.jobs[task].requestSR[r].x=group.requestSR[r].x;
					this.p.jobs[task].requestSR[r].z=group.requestSR[r].z;
				}
			}
			if(Dividedimension=='z'){
				temp[temp.length-1]=group.requestSR[r].z;
				genSection(temp);
				for(int i=1;i<temp.length;i++){
					int task=(Integer)(iter.next());
					this.p.jobs[task].requestSR[r].z=temp[i]-temp[i-1];		
					this.p.jobs[task].requestSR[r].x=group.requestSR[r].x;
					this.p.jobs[task].requestSR[r].y=group.requestSR[r].y;
				}
			}
		}
	}
		
}
