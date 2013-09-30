package gen;

public class Reqgen {
	Project p;//���ڱ�������ĸ�����,����Ŀ������
	int[][][] resDens;
	FuncSel funcSel;
	Utility utility;
	Demand demand;
	
	Reqgen(Project p){
		this.p = p;
		resDens = new int[Progen.maxNrOfJobs][Progen.maxNrOfModes][Progen.maxRDN];
		funcSel = new FuncSel();
	}
	
	void resReqMain(Demand demand,Utility utility){
		this.utility = utility;
		this.demand = demand;
		
		resReqSub('R');
		chooseResFunc('R');
		
		chooseResFunc('D');
		resReqSub('D');
		
		chooseResFunc('N');
		resReqSub('N');
		
		testEfficiency();
	}


	private void testEfficiency() {
		int numTrials;
		for(int j=1;j<this.p.nrOfJobs-1;j++){
			if(this.p.jobs[j].nrOfModes>1){
				//����ù��̳���ȱ��
				if(!efficiency(j)){
					//ʵ�����
					numTrials=0;
					do{
						//����������Դ�����Լ�������
						assNewDens(j,'R');
						assNewDens(j,'D');
						assNewReq(j,'R');
						assNewReq(j,'D');
						assNewDens(j,'N');
						assNewReq(j,'N');
						numTrials++;
					}
					while(!efficiency(j) && numTrials<this.demand.base.maxTrials);
					
					if(efficiency(j))
						this.utility.error(true, 29,demand.errFilePath,"");//ͨ���������j��Ч�Եõ�����
					else
						this.utility.error(true, 1002,demand.errFilePath,"");
				}
			}
		}
		
	}

	private void assNewReq(int j, char type) {
		// TODO Auto-generated method stub
		initReq(j,type);
		for(int r=0;r<numRes(type);r++)
			reqAm(j,r,type);
	}

	private void assNewDens(int j, char type) {
		int nonZeros,element,sum,rchosen;
		nonZeros=0;
		for(int m=0;m<this.p.jobs[j].nrOfModes;m++){
			for(int r=0;r<numRes(type);r++){
				if(perReq(j,m,r,type)>0)
					nonZeros++;
			}
		}
		initResDens(j,type);
		setMinResDens(j,type);
		nonZeros=nonZeros-minResUsed(type)*p.jobs[j].nrOfModes;
		while(nonZeros>0){		//������ԭ����ô������ķ�0Ԫ��,ʹ���ܵ�RF����
			element=1+this.utility.random.nextInt(nonZeros);
			sum=0;
			boolean found=false;
			for(int m=0;((m<this.p.jobs[j].nrOfModes)&&!found);m++){
				int k=0;//k������¼��ǰ[j][m]�Ļģʽ��,�Ѿ����˶����ֵ�ǰ��Դ���͵���Դ
				for(int r=0;r<numRes(type);r++){
					if(resDens[j][m][r]==1)
						k++;	
				}
				sum=sum+(maxResUsed(type)-k);
				if(sum>=element){
					rchosen=1+this.utility.random.nextInt(numRes(type)-k);//�ڵ�ǰ[j][m]�Ļģʽ����ЩֵΪ0��Ԫ�������ѡ��һ��
					int n=0;
					for(int r=0;r<numRes(type);r++){
						if(resDens[j][m][r]==0)
							n++;
						if(n==rchosen){
							resDens[j][m][r]=1;
							nonZeros--;
							found=true;
							break;
						}
					}						
				}
			}
		}		
	}

	private int perReq(int j, int m, int r, char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R': return this.p.jobs[j].modes[m].RResReq[r];
		case 'D': return this.p.jobs[j].modes[m].DResReq[r];
		default : return this.p.jobs[j].modes[m].NResReq[r]; //typeΪ'N'ʱ
		}
	}

	/**
	 * zdg-�ж��Ƿ���������������������ģʽM1��M2��M1�ĳ����ڱ�M2�̣�����
	 *     M1����Դ������ȴ��M2��
	 * @param j
	 * @return
	 */
	private boolean efficiency(int j) {
		// TODO Auto-generated method stub
		int m1=0,m2=1;
		boolean M1supM2,M2supM1,efficient=true;
		
		while((efficient)&& (m1<this.p.jobs[j].nrOfModes-1)){
			M1supM2=false;
			M2supM1=false;
			if(this.p.jobs[j].modes[m1].duration<this.p.jobs[j].modes[m2].duration) 
				M1supM2=true;
			else if(this.p.jobs[j].modes[m1].duration>this.p.jobs[j].modes[m2].duration)
				M2supM1=true;
			if(!(M1supM2 && M2supM1)){
				for(int r=0;r<this.p.R;r++){
					if(this.p.jobs[j].modes[m1].RResReq[r]<this.p.jobs[j].modes[m2].RResReq[r]) 
						M1supM2=true;
					else if(this.p.jobs[j].modes[m1].RResReq[r]>this.p.jobs[j].modes[m2].RResReq[r])
						M2supM1=true;
				}
			}
			if(!(M1supM2 && M2supM1)){
				for(int r=0;r<this.p.D;r++){
					if(this.p.jobs[j].modes[m1].DResReq[r]<this.p.jobs[j].modes[m2].DResReq[r]) 
						M1supM2=true;
					else if(this.p.jobs[j].modes[m1].DResReq[r]>this.p.jobs[j].modes[m2].DResReq[r])
						M2supM1=true;
				}
			}
			if(!(M1supM2 && M2supM1)){
				for(int r=0;r<this.p.N;r++){
					if(this.p.jobs[j].modes[m1].NResReq[r]<this.p.jobs[j].modes[m2].NResReq[r]) 
						M1supM2=true;
					else if(this.p.jobs[j].modes[m1].NResReq[r]>this.p.jobs[j].modes[m2].NResReq[r])
						M2supM1=true;
				}
			}
			if(!(M1supM2 && M2supM1))
				efficient=false;
			if(m2==this.p.jobs[j].nrOfModes-1){
				m1++;
				m2=m1+1;
			}
			else
				m2++;							
		}
		return efficient;
	}

	/**
	 * Ϊָ������Դ����(type)�������̶ĵķ���,ѡ��������ÿ��������Դ���������������ڵĺ�����ϵ���
	 * @param type ָ������Դ����
	 */
	private void chooseResFunc(char type) {
		// TODO Auto-generated method stub
		int func;
		double choice,cumProb;
		if(type=='R'){
			for(int r=0;r<this.p.R;r++){
				choice=utility.random.nextDouble();//�������޸���һЩ,�������̶ķ���(ԭ������Ϊ���Ƶ����̶�)=
				cumProb=0;
				for(func=0;(func<this.demand.base.RFuncProb.length)&&(cumProb<choice);func++)
					cumProb=cumProb+this.demand.base.RFuncProb[func];
				this.funcSel.R[r]=func-1;//ֵ��0��ʼ,Ϊ0,1.   0��ʾ�޹�ϵ;1��ʾ�ɷ���ϵ
			}			
		}
		else if(type=='D'){
			for(int r=0;r<this.p.D;r++){
				choice=utility.random.nextDouble();//�������޸���һЩ,�������̶ķ���(ԭ������Ϊ���Ƶ����̶�)=
				cumProb=0;
				for(func=0;(func<this.demand.base.DFuncProb.length)&&(cumProb<choice);func++)
					cumProb=cumProb+this.demand.base.DFuncProb[func];
				this.funcSel.D[r]=func-1;
			}
		}
		else if(type=='N'){
			for(int r=0;r<this.p.N;r++){
				choice=utility.random.nextDouble();//�������޸���һЩ,�������̶ķ���(ԭ������Ϊ���Ƶ����̶�)=
				cumProb=0;
				for(func=0;(func<this.demand.base.NFuncProb.length)&&(cumProb<choice);func++)
					cumProb=cumProb+this.demand.base.NFuncProb[func];
				this.funcSel.N[r]=func-1;
			}
		}		
	}
	

	/**
	 * Ϊÿ������ɶ�ָ������Դ�����е�ÿ�־�����Դ������(������,1.ȷ���Ƿ���Ҫ;2.ȷ����Ҫ����)
	 * @param type  ָ������Դ����
	 */
	private void resReqSub(char type) {
		genResDens(type);
		for(int j=1;j<this.p.nrOfJobs-1;j++){
			initReq(j,type);
			for(int r=0;r<numRes(type);r++){
				reqAm(j,r,type);
			}
		}
	}
	

	private void reqAm(int j, int r, char type) {
		// TODO Auto-generated method stub
		int req1,req2,reqMin,reqMax,d;
		float delta;
		if(resFunc(type,r)==0){
			req1=minResReq(type)+this.utility.random.nextInt(maxResReq(type)-minResReq(type)+1);
			//=====================��������ģʽ��ִ��ʱ���޹�.�޹�ʱ,ֻҪ�������Դ,ͬһ�����ģʽ���������,����������??(2011��1��21��:�������Ǻ��ʵ�)
			for(int m=0;m<this.p.jobs[j].nrOfModes;m++){
				if(resDens[j][m][r]==1){
//					req1=minResReq(type)+this.utility.random.nextInt(maxResReq(type)-minResReq(type)+1);//����ԭ����,�޸��˴˴�==========================================
					writeResReq(j,m,r,req1,type);
				}
			}
		}
		else if(resFunc(type,r)==1){
			req1=minResReq(type)+this.utility.random.nextInt(maxResReq(type)-minResReq(type)+1);
			req2=minResReq(type)+this.utility.random.nextInt(maxResReq(type)-minResReq(type)+1);
			reqMin=(req1<req2)?req1:req2;
			reqMax=(req1>req2)?req1:req2;
			d=1;
			delta=(float)(reqMax-reqMin)/(float)((1>modesReqResWithoutSameDur(j,r))?1:modesReqResWithoutSameDur(j,r));
			for(int m=0;m<this.p.jobs[j].nrOfModes;m++){
				if(resDens[j][m][r]==1){
					req1=(int)(reqMax-d*delta)+this.utility.random.nextInt((int)(reqMax-(d-1)*delta)-(int)(reqMax-d*delta)+1);
					writeResReq(j,m,r,req1,type);
					if(nextModeWithDiffDur(j,m))
						d++;
				}
			}
		}
	}

	/**
	 * �ж��»j����һ��ģʽ�͵�ǰģʽm�Ƿ��в�ִͬ��ʱ��
	 * @param j
	 * @param m
	 * @return
	 */
	private boolean nextModeWithDiffDur(int j, int m) {
		if((m<this.p.jobs[j].nrOfModes-1)&&(this.p.jobs[j].modes[m].duration==this.p.jobs[j].modes[m+1].duration))
			return false;
		else
			return true;
	}

	/**
	 * ͳ�ƻj��ģʽ����Ҫ��Դr��ִ��ʱ�䲻ͬ��ģʽ��
	 * @param j
	 * @param r
	 * @return
	 */
	private int modesReqResWithoutSameDur(int j, int r) {
		int count=0;
		for(int m=0;m<this.p.jobs[j].nrOfModes;m++){
			if(resDens[j][m][r]==1){
				int flag=0;
				for(int i=0;i<m;i++){
					if((resDens[j][i][r]==1)&&(this.p.jobs[j].modes[i].duration==this.p.jobs[j].modes[m].duration)){
						flag=1;break;
					}
				}
				if(flag==0)
					count++;
			}
		}
		return count;
	}

	private int maxResReq(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return this.demand.base.maxRReq;
		case 'D':return this.demand.base.maxDReq;
		default :return this.demand.base.maxNReq;//typeΪNʱ
		}
	}

	private int minResReq(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return this.demand.base.minRReq;
		case 'D':return this.demand.base.minDReq;
		default :return this.demand.base.minNReq;//typeΪNʱ
		}
	}

	/**
	 * ����ָ��ĳ����Դ��ĳ��������Դ�ĺ�����ϵ���
	 * @param type ĳ����Դ
	 * @param r	 ĳ��������Դ�ı��
	 * @return
	 */
	private int resFunc(char type, int r) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return this.funcSel.R[r];
		case 'D':return this.funcSel.D[r];
		default :return this.funcSel.N[r];//typeΪNʱ
		}
	}
	
	private void initReq(int j, char type) {
		// TODO Auto-generated method stub
		for(int m=0;m<this.p.jobs[j].nrOfModes;m++){
			for(int r=0;r<numRes(type);r++)
				writeResReq(j,m,r,0,type);
		}
	}

	/**
	 * д�����Դr�����󵽻j��ģʽm��
	 * @param j
	 * @param m
	 * @param r
	 * @param req
	 * @param type
	 */
	private void writeResReq(int j, int m, int r, int req, char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':this.p.jobs[j].modes[m].RResReq[r]=req; break;
		case 'D':this.p.jobs[j].modes[m].DResReq[r]=req; break;
		default :this.p.jobs[j].modes[m].NResReq[r]=req; break;//typeΪNʱ
		}
	}

	/**
	 * ������type����Դ�е�ÿ����Դ�Ƿ��������Դ�����ܶȾ���(����ָ������Դ��������)
	 * @param type
	 */
	private void genResDens(char type) {
		// TODO Auto-generated method stub
		testResDens(type);
		for(int j=1;j<this.p.nrOfJobs-1;j++){
			initResDens(j,type);//�����,��Ϊ������Դ,��������,����ÿ����Դ����ʱ,��Ҫ��ʼ��
			setMinResDens(j,type);
		}
		setResDens(type);
	}
	

	/**
	 * ʹ������Դ�����ܶȾ�����ܶȴﵽ�趨����Դ��������(��Χ��,��Ϊ����ƫ��),���ѵ�ǰ��RF������Ҫ���RF
	 * @param type
	 */
	private void setResDens(char type) {
		// TODO Auto-generated method stub
		float actResFac ; //��ǰ����Դ�����ܶȾ�����ܶ�(Ҳ����Դ��������)
		int jchosen,element,freeelements;
		
		actResFac=(float)minResUsed(type)/(1>numRes(type)?1:numRes(type));//��ֹ��ĸΪ0
		freeelements=0;
		for(int j=1;j<this.p.nrOfJobs-1;j++)
			freeelements=freeelements+this.p.jobs[j].nrOfModes;
		freeelements=freeelements*(maxResUsed(type)-minResUsed(type));//��ǰ��������������Ϊ1��Ԫ����Ŀ
		while((resFac(type)>actResFac) && (freeelements>0)){
			element=1+this.utility.random.nextInt(freeelements);//����random.nextInt(freeelements)����0��freeelements-1��Щ��,��+1
			jchosen=determine(element,type);
			freeelements--;
			actResFac=actResFac+(float)1/(float)((this.p.nrOfJobs-2)*numRes(type)*this.p.jobs[jchosen].nrOfModes);//�µ�RFֵ			
		}
		//���Ϊ���������ɵ�������ʵ��RF(actualRF�����ARF),
		//����ARF����[ARF(l-��),ARF(1+��)]����
		this.utility.error(actResFac<(resFac(type)*(1-this.demand.base.reqTol)), errorNum(22,type),demand.errFilePath,"");
		this.utility.error(actResFac>(resFac(type)*(1+this.demand.base.reqTol)), errorNum(25,type),demand.errFilePath,"");
	}
	

	/**
	 * ����(����õ���)element,�ҵ������е�ĳ����Ԫ������Ϊ1.
	 * @param element  
	 * @param type
	 * @return ��������Ϊ1�ĵ�Ԫ���ڵĻ���
	 */
	private int determine(int element, char type) {
		// TODO Auto-generated method stub
		int rchosen,sum=0;
		for(int j=1;j<this.p.nrOfJobs-1;j++){
			for(int m=0;m<this.p.jobs[j].nrOfModes;m++){
				int k=0;//k������¼��ǰ[j][m]�Ļģʽ��,�Ѿ���Ҫ�˶����ֵ�ǰ��Դ���͵���Դ
				for(int r=0;r<numRes(type);r++){
					if(resDens[j][m][r]==1)
						k++;	
				}
				sum=sum+(maxResUsed(type)-k);
				if(sum>=element){
					rchosen=1+this.utility.random.nextInt(numRes(type)-k);//�ڵ�ǰ[j][m]�Ļģʽ����ЩֵΪ0��Ԫ�������ѡ��һ��
					for(int r=0,n=0;r<numRes(type);r++){
						if(resDens[j][m][r]==0)
							n++;
						if(n==rchosen){
							resDens[j][m][r]=1;	
							return j;
						}
					}						
				}
			}
		}
		return -1;					
	}

	
	/**
	 * ʹ�j����ÿ��ģʽ��,��type����Դ�е�������������Ҫ����С��������(����ĳ����Դ,����ά�ܶȾ����аѶ�Ӧ��Ԫ����Ϊ1)
	 * @param j
	 * @param type
	 */
	private void setMinResDens(int j, char type) {
		// TODO Auto-generated method stub
		int r;
		for(int m=0;m<this.p.jobs[j].nrOfModes;m++){
			for(int actNr=0;actNr<minResUsed(type);){
				r=this.utility.random.nextInt(numRes(type));//����һ��α�����������ȡ�Դ���������������еġ��� 0����������ָ��ֵ����������֮����ȷֲ��� int ֵ
				if(resDens[j][m][r]==0){
					resDens[j][m][r]=1;
					actNr++;
			}
			}
		}
	}


	/**
	 * ��ʼ���j�����ģʽ�¶�type���е�ÿ����Դ������Ҫ(��������Դ�����ܶȾ����һ��ƽ���ϵ�ֵ��Ϊ0)
	 * @param j
	 * @param type
	 */
	private void initResDens(int j, char type) {
		// TODO Auto-generated method stub
		for(int m=0;m<this.p.jobs[j].nrOfModes;m++)
			for(int r=0;r<numRes(type);r++)
				this.resDens[j][m][r]=0;
	}

	/**
	 * ��⵱ǰ�Ĳ���(���ݻ��ļ�������ɵĲ���)�Ƿ�������,����,�ɾ����ľ�������¼����,����ֻ����
	 * @param type
	 */
	private void testResDens(char type) {
		// TODO Auto-generated method stub
		if(this.utility.error(maxResUsed(type)>numRes(type),errorNum(10,type),demand.errFilePath,""))//����ĳ����Դ��������������ʱ��ָ����Χ������ɵ�,���п��ܸ�RRU��ͻ
			adjustMaxResUsed(type);
		if(this.utility.error(minResUsed(type)>maxResUsed(type), errorNum(13,type),demand.errFilePath,""))
			adjustMinResUsed(type);
		if(numRes(type)>0){
			this.utility.error(resFac(type)<(float)minResUsed(type)/(float)numRes(type),errorNum(16,type),demand.errFilePath,"");
			this.utility.error(resFac(type)>(float)maxResUsed(type)/(float)numRes(type),errorNum(19,type),demand.errFilePath,"");
		}
	}

	/**
	 * ��ȡtype����Դ����Դ��������
	 * @param type
	 * @return
	 */
	private float resFac(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return this.demand.base.RRF;
		case 'D':return this.demand.base.DRF;
		default: return this.demand.base.NRF;//��ʱtypeΪN 
		}
	}

	/**
	 * ������type����Դ�������С����(Ϊ��type����Դ������������)
	 * @param type
	 */
	private void adjustMinResUsed(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R': this.demand.base.minRRU=this.demand.base.maxRRU;break;
		case 'D':this.demand.base.minDRU=this.demand.base.maxDRU;break;
		default: this.demand.base.minNRU=this.demand.base.maxNRU;//��ʱtypeΪN
		}
	}

	/**
	 * ��ȡ��type����Դ����С��������
	 * @param type
	 * @return
	 */
	private int minResUsed(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return this.demand.base.minRRU;
		case 'D':return this.demand.base.minDRU;
		default: return this.demand.base.minNRU;//��ʱtypeΪN
		}
	}

	/**
	 * ������type����Դ������������(Ϊtype����Դӵ�е�����)
	 * @param type
	 */
	private void adjustMaxResUsed(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R': this.demand.base.maxRRU=this.p.R;break;
		case 'D':this.demand.base.maxDRU=this.p.D;break;
		default: this.demand.base.maxNRU=this.p.N;//��ʱtypeΪN
		}
	}

	/**
	 * ��ȡ��type����Դ������������
	 * @param type
	 * @return
	 */
	private int maxResUsed(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return this.demand.base.maxRRU;
		case 'D':return this.demand.base.maxDRU;
		default: return this.demand.base.maxNRU;//��ʱtypeΪN
		}
	}

	private int errorNum(int i, char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return i+1;
		case 'D':return i+2;
		default: return i+3;//��ʱtypeΪN
		}
	}

	/**
	 * ��ȡtype����Դ������
	 * @param type
	 * @return
	 */
	private int numRes(char type) {
		// TODO Auto-generated method stub
		switch(type){
		case 'R':return this.p.R;
		case 'D':return this.p.D;
		case 'N':return this.p.N;
		default: return -1;
		}
	}

}


/**
 * ���ౣ���������Դ�������������ڹ�ϵ�ĺ������
 * @author xung
 *
 */
class FuncSel{
	int[] N;
	int[] R;
	int[] D;

	/**
	 * FuncSel��Ĺ��캯��,new3������3����Դ���������������ڹ�ϵ������ŵ�����
	 */
	FuncSel(){
		N = new int[Progen.maxRDN];
		R = new int[Progen.maxRDN];
		D = new int[Progen.maxRDN];
	}
}