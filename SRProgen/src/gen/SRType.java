package gen;

public class SRType{
	String name;//�ռ���Դ��������(���ļ����Զ���)
	int dimension;//ά��
	boolean dividable;//�ɻ�����
	boolean orientation;//������(ͨ��ά��+�ɻ�����+��������Ψһȷ��һ�ֿռ���Դ����)
	int minKind;//����ռ���Դ����Դ����С����
	int maxKind;//����ռ���Դ����Դ���������
	SRmeasure minDemand;
	SRmeasure maxDemand;
	int minR;//ÿ����������ҪSRA����Դ����С����
	int maxR;//ÿ����������ҪSRA����Դ���������
	float SRF;
	float SRS;
	
	int kind;//����ռ���Դ��������
	int startNr;//����ռ���Դ��concreteSR�����еĿ�ʼ���
	int endNr;//����ռ���Դ��concreteSR�����еĽ������
	
	SRType(){
		minDemand = new SRmeasure();
		maxDemand = new SRmeasure();
	}

	public void copy(SRType type) {
		this.name = type.name;
		this.dimension = type.dimension;
		this.dividable = type.dividable;
		this.orientation = type.orientation;
		this.minKind = type.minKind;
		this.maxKind = type.maxKind;
		this.minDemand.x= type.minDemand.x;
		this.minDemand.y= type.minDemand.y;
		this.minDemand.z= type.minDemand.z;
		this.maxDemand.x = type.maxDemand.x;
		this.maxDemand.y = type.maxDemand.y;
		this.maxDemand.z = type.maxDemand.z;
		this.minR = type.minR;
		this.maxR = type.maxR;
		this.SRF = type.SRF;
		this.SRS = type.SRS;
		
		this.kind = type.kind;
		this.startNr = type.startNr;
		this.endNr = type.endNr;		
	}
}

class SRmeasure{
	int x;
	int y;
	int z;
	
	SRmeasure(){
		this.x=0;
		this.y=0;
		this.z=0;
	}
	
	void clear(){
		this.x=0;
		this.y=0;
		this.z=0;
	}
}

class concreteSR{
	SRmeasure avail;//������
	SRmeasure orientation;//����ķ���
	int belongSRTypeNr;//������Դ���ͱ��
	
	concreteSR(){
		avail = new SRmeasure();
		orientation = new SRmeasure();
	}
}
