package gen;



public class Block {
	String name;//�ֶ�����
	MyPolygon shape;//�ֶε���״(��ά͹�����)
	
	//���������ý����Ϣ
	POINT position;//��ʾ���ú����õ��λ��
	String yard_code;//����ƽ̨���
	boolean isConfiged;//�Ƿ��ѱ�����
	
	
	MyPolygon insideSpaceRTW;//�ֶ�����ڳ���W�ĳ��ڿռ�
	MyPolygon obstacleSpaceOfA;//A�ֶ�����ڴ˷ֶ�(B)���ϰ��ռ�
	MyPolygon positionShape;//�ֶ����ú��ڳ����е���״(λ��)

	Block(MyPolygon shape){
		this.shape = shape;
	}
	
	
	Block(String block_code,MyPolygon shape){
		this.name = block_code;
		this.shape = shape;
	}
	
	

	public Block(String block_code, MyPolygon shape,boolean isConfiged) {
		this.name = block_code;
		this.shape = shape;
		this.isConfiged = isConfiged;
	}
	
	//�˹��캯��������������ֶ�(���/�����ϰ�)
	Block(String block_code,MyPolygon shape,int beginT,int finishT,int b_type){
		this.name = block_code;
		this.shape = shape;
		this.positionShape = shape;
		this.isConfiged = true;
	}

	/**
	 * ���õ�ǰ�ֶ��ڳ����е�λ�õ�����p(���õĽ����¼��positionShape��)
	 * @param p
	 * @param Angle
	 */
	public void setPosition(POINT p, int Angle) {
		this.positionShape = this.shape.rotate(Angle);
		this.positionShape.moveTo(p);						
	}
}
