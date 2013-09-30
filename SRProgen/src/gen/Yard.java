package gen;

import java.util.ArrayList;

public class Yard {
	double length;//����((������x��ƽ��)
	double width;//���(�����y��ƽ��))
	
	ArrayList<Block> existBlocks;//��ǰ���ڴ�ƽ̨�ϼӹ���Blocks
	ArrayList<Block> needConfigBlocks;//ĳʱ������(��)�е�ǰ��Ҫ���õķֶμ���
	
	//����(���ص���״����)
	MyPolygon shape;	
	
	
	//�˹��캯��ֻ���ڹ�������ĳ���
	Yard(double length,double width,ArrayList<Block> needConfigBlocks){
		this.length =length;
		this.width = width;
		this.needConfigBlocks  = needConfigBlocks;
		
		this.existBlocks = new ArrayList<Block>();
		this.shape = new MyPolygon(this.length,this.width,new POINT(0,0));
	}
	

	/**
	 * �����ص��쳡����Դ�ռ䲼�ֵ�������
	 */
	public ArrayList<Block> process() {
		for(;this.needConfigBlocks.size()!=0;){
			Block a=this.needConfigBlocks.get(0);
			//���㵱ǰ�ķֶ�����ڳ��صĳ��ڿռ�
			a.insideSpaceRTW = a.shape.insideSpaceRTW(this);
			//���㵱ǰ�ֶ�����ڳ������Ѿ����ڵ����зֶε��ϰ��ռ�
			for(Block b_exist:this.existBlocks){
				b_exist.obstacleSpaceOfA = a.shape.obstacleSpaceRTB(b_exist.positionShape);
			}
			//�������������õ㼯
			ArrayList<POINT> availPointSet = new ArrayList<POINT>();
			
			ArrayList<POINT> Do = new ArrayList<POINT>();
			ArrayList<POINT> Di = new ArrayList<POINT>();
			ArrayList<POINT> Doi = new ArrayList<POINT>();
			ArrayList<POINT> Doo = new ArrayList<POINT>();
			
			//�ٰѳ������ÿռ�S(ai|W)�ĸ��������������õ㼯
			for(int i=0;i<a.insideSpaceRTW.vCount;i++){
				availPointSet.add(a.insideSpaceRTW.vSet[i]);
				Di.add(a.insideSpaceRTW.vSet[i]);
			}
			//�ڶ��ѷ��ü���B�е�ÿ��bj��Ӧ��S(ai|bj)��������������Ƿ���S(ai|W)��,�����,�Ѷ������������õ㼯
			for(Block b_exist:this.existBlocks){
				for(int i=0;i<b_exist.obstacleSpaceOfA.vCount;i++){
					if(a.insideSpaceRTW.InsideConvexPolygon(b_exist.obstacleSpaceOfA.vSet[i])){
						if(!Utility.arrayListHasP(availPointSet, b_exist.obstacleSpaceOfA.vSet[i]))
							availPointSet.add(b_exist.obstacleSpaceOfA.vSet[i]);//��availPointSet������˵���ȵĵ�ʱ������˵�
						Do.add(b_exist.obstacleSpaceOfA.vSet[i]);
					}	
				}
			}
			//�۶��ѷ��ü���B�е�ÿ��bj���������Ӧ��S(ai|bj)��S(ai|W)�Ľ���,������Щ�������������õ㼯��
			for(Block b_exist:this.existBlocks){
				ArrayList<POINT> points = a.insideSpaceRTW.intersectionPoint(b_exist.obstacleSpaceOfA);
				for(POINT p:points){
					if(!Utility.arrayListHasP(availPointSet, p))
						availPointSet.add(p);//��availPointSet������˵���ȵĵ�ʱ������˵�
					Doi.add(p);
				}
			}
			//�ܶ��ѷ��ü���B�е�������bj��bh��Ӧ��S(ai|bj)��S(ai|bh)���������ǵĽ���,���ѽ�����S(ai|W)�ڵļ���������õ㼯
			for(int i=0;i<this.existBlocks.size()-1;i++){
				for(int j=i+1;j<this.existBlocks.size();j++){
					ArrayList<POINT> points = this.existBlocks.get(i).obstacleSpaceOfA.intersectionPoint(this.existBlocks.get(j).obstacleSpaceOfA);
					for(POINT p:points){
						if(a.insideSpaceRTW.InsideConvexPolygon(p)){
							if(!Utility.arrayListHasP(availPointSet, p))
								availPointSet.add(p);//��availPointSet������˵���ȵĵ�ʱ������˵�
							Doo.add(p);
						}
					}
				}
			}
			//�ݶԵ�ǰ�������õ㼯�е�ÿ���㣬�������Ƿ���ĳ��S(ai|bj)��(�����ϲ��Ƴ�)������ǣ�����ӿ������õ㼯���Ƴ�
			ArrayList<POINT> removePoints = new ArrayList<POINT>();
			for(POINT p:availPointSet){
				for(Block b_exist:this.existBlocks){
					if(b_exist.obstacleSpaceOfA.insidepolygon(p)==0){
						removePoints.add(p);
						break;
					}
				}
			}
			availPointSet.removeAll(removePoints);
			
			Do.removeAll(removePoints);
			Di.removeAll(removePoints);
			Doi.removeAll(removePoints);
			Doo.removeAll(removePoints);
						
			//����Կ������õ�ѡȡ
			POINT resultPoint = choosePoint(a,availPointSet,Doi);
			setBlock(a,resultPoint,0);//���õ�ǰ�ֶε�λ�ò���¼
		}	
		return  this.existBlocks;
	}
	
	
	private POINT choosePoint(Block a,ArrayList<POINT> availPointSet,ArrayList<POINT> Doi) {
		if(availPointSet.isEmpty()){	
			return null;
		}
		else 
			return Blf_PS(a,availPointSet);	
	}



	/**
	 * �������������÷ֶ�(�������)���Ϊ����ʱ,���(Bottom-Left-Fill)�㷨,���㷨�Ƕ�װ���㷨BF��һ�ָĽ��㷨.�����ھ��ο��װ������
	 * ѡ������(���ȼ���)/����(���ȼ���)�Ĳο���
	 * @param a
	 * @param availPointSet
	 * @return
	 */
	private POINT Blf_PS(Block a, ArrayList<POINT> availPointSet) {
		POINT cur = availPointSet.get(0);//�ܵ��õ��˷���ʱ,availPointSet�ض���Ϊ��,���п���ֱ��ȥ��һ��Ԫ��
		for(int i=0;i<availPointSet.size();i++){
			POINT p = availPointSet.get(i);
			if(p.y < cur.y){
				cur = p;
			}
			else if(p.y == cur.y && p.x < cur.x ){
				cur = p;
			}
		}
		return cur;
	}



	/**
	 * ���÷ֶ�a
	 * @param a   ���õķֶ�
	 * @param p   ���õ�λ��(���õ�λ��)
	 * @param positionAngle ��ת�Ƕ�
	 * @param k  �����ڵ���
	 */
	private void setBlock(Block a, POINT p,int positionAngle) {
		a.setPosition(p,positionAngle);
		a.position = new POINT(p);
		a.isConfiged = true;
		
		this.existBlocks.add(a);
		this.needConfigBlocks.remove(a);
		
	}
	

	


}
