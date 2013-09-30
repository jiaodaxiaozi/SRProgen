package gen;

import java.lang.Math;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * һ������������
 * @author xung
 *
 */
public class Utility {
	 Random random ;
	 PrintStream   oldPS;
	 
	 
	 final static  double	INF		= 1E200 ;  //���ֵ
	 final static double	EP		= 1E-5 ;   //��Сֵ
	 final static int		MAXV	= 100;  //��������Ķ�����
	 final static double	PI		= 3.14159265; 
	 
	 public static boolean genPolygon = false;
	 public static double rategenTriangle  = 0.2;//���������εĸ���
	 public static double rategenQuadrangle  = 0.3;//�����ı��εĸ���
	 public static double rategenPentagon  = 0.3;//��������εĸ���
	 public static double rategenHexagon  = 0.2;//���������εĸ���
	 
	 public static double rateRectAngele = 0.1;//�����ı���ʱ�����ɾ��εĸ���
	 public static double rateTrapezium  = 0.1;//�����ı���ʱ���������εĸ���
	 public static double rateParallelogram  = 0.1;//�����ı���ʱ������ƽ���ı��εĸ���
	 
	 public static double ratioArea  = 0.4;//���ɵ�͹����������ԭ�����������С����
	 
	 
	 Utility(int seed){
		 if(seed==0)
			 random = new Random();
		 else
			 random = new Random(seed); 
	 }

	 /**
	  * ������������ļ�
	  * @param filepath  ������趨�ļ�·��
	  * @param firstWrite  �Ƿ��ǵ�һ��д����ļ�(Ϊtrue,�����ԭ��ͬ���ļ�,���Ϊfalse,����д)
	  */
	void setPrintStream(String filepath,boolean firstWrite){
		this.oldPS = System.out;
        PrintStream ps = null;
        try {
        	FileOutputStream fos;
        	if(firstWrite)
                fos = new FileOutputStream(filepath);
        	else
        		fos = new FileOutputStream(filepath,true);       
          ps = new PrintStream(fos);
        } catch (IOException e) {
          e.printStackTrace();
        }
        if(ps != null){
          System.setOut(ps);
        }
	}
	
	/**
	 * �ָ�PrintStream���ָ��
	 */
	void reSetPrintStream(){
		System.out.close();//�رյ�ǰ���ļ��������(�����ļ��Ϳ��Դ���)
		System.setOut(this.oldPS);		
	}
	
	/**
	 * �ж�����Set�Ƿ��н���
	 * @param a
	 * @param b
	 * @return
	 */
	boolean hasIntersection(Set a,Set b){
		Iterator iter = a.iterator(); 
		while (iter.hasNext()){
			if(b.contains(iter.next()))
				return true;
		}
		return false;
	}
	boolean error(boolean crit,int errorNr,String filepath,String s){
			if(crit){
				this.setPrintStream(filepath,false);
				switch(errorNr){
				case 1:
					System.out.println("ERROR   1: Predecessor could not be determined."); break;
				case 2:
					System.out.println("ERROR   2: Successor could not be determined.");break;
				case 3:
					System.out.println("ERROR   3: Complexity could not be achieved (ʵ�����ɵ����趨ֵ).");break;
				case 4:
					System.out.println("ERROR   4: Complexity could not be achieved (ʵ�����ɸ����趨ֵ).");break;
				case 11:
					System.out.println("ERROR  11: max # req. resources > # resources for type R; -> max# := #.");break;
				case 12:
					System.out.println("ERROR  12: max # req. resources > # resources for type D; -> max# := #.");break;
				case 13:
					System.out.println("ERROR  13: max # req. resources > # resources for type N; -> max# := #.");break;
				case 14:
					System.out.println("ERROR  14: min # req. resources > max # for type R; -> min # := max #.");break;
				case 15:
					System.out.println("ERROR  15: min # req. resources > max # for type D; -> min # := max #.");break;
				case 16:
					System.out.println("ERROR  16: min # req. resources > max # for type N; -> min # := max #.");break;
				case 17:
					System.out.println("ERROR  17: RF for R can`t be achieved; min # req. resources too large.");break;
				case 18:
					System.out.println("ERROR  18: RF for D can`t be achieved; min # req. resources too large.");break;
				case 19:
					System.out.println("ERROR  19: RF for N can`t be achieved; min # req. resources too large.");break;
				case 20:
					System.out.println("ERROR  20: RF for R can`t be achieved; max # req. resources too small.");break;
				case 21:
					System.out.println("ERROR  21: RF for D can`t be achieved; max # req. resources too small.");break;
				case 22:
					System.out.println("ERROR  22: RF for N can`t be achieved; max # req. resources too small.");break;
				case 23:
					System.out.println("ERROR  23: Obtained RF falls short the tolerated range for R.");break;
				case 24:
					System.out.println("ERROR  24: Obtained RF falls short the tolerated range for D.");break;
				case 25:
					System.out.println("ERROR  25: Obtained RF falls short the tolerated range for N.");break;
				case 26:
					System.out.println("ERROR  26: Obtained RF exceeds the tolerated range for R.");break;
				case 27:
					System.out.println("ERROR  27: Obtained RF exceeds the tolerated range for D.");break;
				case 28:
					System.out.println("ERROR  28: Obtained RF exceeds the tolerated range for N.");break;
				case 29:
					System.out.println("ERROR  29: More than 1 trial was used to produce a job with non dominated modes.");break;
					
				//�������������
				case 41:
					System.out.println("ERROR  41: ��������������������������Կռ���Դ������������Ҳ�������(��С�������������������˶����пռ���Դ�����������������Сֵ).");break;
				case 42:
					System.out.println("ERROR  42: ���趨�ĳ��Դ�����,����Ŀ"+s+"������������ʧ��.");break;
				case 43:
					System.out.println("ERROR  43: ���趨�ĳ��Դ�����,��������Ŀ"+s+"������ʧ��.(�������ɴ�����Ŀ������������.)");break;
				case 44:
					System.out.println("ERROR  44: ��������Ŀ"+s+"�����������������ʧ��.");break;
				case 45:
					System.out.println("ERROR  45: ��������Ŀ"+s+"��������ƶ�������ʧ��.");break;
				
				//�ռ���Դ���	
				case 51:
//					System.out.println("ERROR  51: max # req. resources > # resources for type "+s+"; -> max# := #.");break;
					System.out.println("ERROR 51: �Կռ���Դ����"+s+"������������� > �ÿռ���Դ���͵����� ; -> ����Ϊ:����������� =�ÿռ���Դ��������");break;
				case 52:
//					System.out.println("ERROR  52: min # req. resources > max # resources for type "+s+"; -> min # := max #.");break;
					System.out.println("ERROR 52: �Կռ���Դ����"+s+"����С�������� > ����������� ; -> ����Ϊ:��С�������� =�����������");break;
				case 53:
//					System.out.println("ERROR  53: SRF for "+s+" can`t be achieved; min # req. resources too large.");break;
					System.out.println("ERROR 53: �ռ���Դ����"+s+"��SRF�޷�����;(��Ϊ��С��������̫����.)");break;
				case 54:
//					System.out.println("ERROR  54: SRF for "+s+" can`t be achieved; max # req. resources too small.");break;
					System.out.println("ERROR 54: �ռ���Դ����"+s+"��SRF�޷�����;(��Ϊ�����������̫С��.)");break;
				case 55:
//					System.out.println("ERROR  55: Obtained SRF falls short the tolerated range for "+s+".");break;
					System.out.println("ERROR 55: ���ɵĿռ���Դ����"+s+"��SRFС���˿�����ķ�Χ)");break;
				case 56:
//					System.out.println("ERROR  56: Obtained SRF exceeds the tolerated range for "+s+".");break;
					System.out.println("ERROR 56: ���ɵĿռ���Դ����"+s+"��SRF�����˿�����ķ�Χ)");break;
					
				case 1000:
					System.out.println("ERROR1000: Network generation without success.");break;
				case 1001:
					System.out.println("ERROR1001: Redundant arcs in network");break;
				case 1002:
					System.out.println("ERROR1002: Non dominated modes for a job could`nt be produced with max # trials.");break;
				}
				this.reSetPrintStream();
//				if(errorNr>=1000){
//					System.out.println("Error:"+errorNr);
//					System.out.println("PROJECT Generation stopped!!!");
//					System.out.println("---->>>>fatal error execution stopped");
//					System.out.println("terminate->>T::continue ->> RETURN");
//				}
			}
			return crit;
		}
	

	 
	 
	 /**********************
	  *                    * 
	  *   ��Ļ�������     * 
	  *                    * 
	  **********************/ 
	 
	 /**
	  * 1. [ƽ��������֮����� ](˵��:��[]�ı�ʾ�Ѳ���)
	  */
	static double dist(POINT p1,POINT p2)                // ��������֮��ŷ�Ͼ��� 
	{ 
		return( Math.sqrt( (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y) ) ); 
	} 
	
	/**
	 * 2. [�ж������Ƿ��غ� ](ע��:�õ���EP,����ȷ��EP̫Сʱ,���ܻ�����ж�ʧ��)
	 * @param p1
	 * @param p2
	 * @return
	 */
	static boolean equal_point(POINT p1,POINT p2)           // �ж��������Ƿ��غ�  
	{ 
		return ( (Math.abs(p1.x-p2.x)<EP)&&(Math.abs(p1.y-p2.y)<EP) ); 
	} 
	

	/**
	 * 3. [ʸ����� ](ע:����ʸ���������ֱ�ߺ��߶�����㷨�ĺ��Ĳ���.ע�������ж�)
	 * r=multiply(sp,ep,op),�õ�(sp-op)��(ep-op)�Ĳ�� 
	 * r>0��ep��ʸ��op-sp����ʱ�뷽�� 
	   r=0��op/sp/ep���㹲�ߣ� 
	   r<0��ep��ʸ��op-sp��˳ʱ�뷽�� 
	   r������������:ƽ���ı��εĴ����ŵ����
	 */
	static double multiply(POINT sp,POINT ep,POINT op) 
	{ 
		//return((sp.x-op.x)*(ep.y-op.y)-(ep.x-op.x)*(sp.y-op.y)); 
		double result = (sp.x-op.x)*(ep.y-op.y)-(ep.x-op.x)*(sp.y-op.y);
		if(Math.abs(result)<EP)
			return 0;
		else
			return result;
		
	} 
	
	
	/**
	 * 4.[ʸ����� ](ע�������ж�)
	 * r=dotmultiply(p1,p2,op),�õ�ʸ��(p1-op)��(p2-op)�ĵ��(�������ʸ��������ʸ��) 
	 * @param p1
	 * @param p2
	 * @param p0
	 * @return	r<0����ʸ���н�Ϊ��ǣ�
				r=0����ʸ���н�Ϊֱ�ǣ�
				r>0����ʸ���н�Ϊ�۽� 
	 */
	double dotmultiply(POINT p1,POINT p2,POINT p0) 
	{ 
		//return ((p1.x-p0.x)*(p2.x-p0.x)+(p1.y-p0.y)*(p2.y-p0.y)); 
		double result = (p1.x-p0.x)*(p2.x-p0.x)+(p1.y-p0.y)*(p2.y-p0.y);
		if(Math.abs(result)<EP)
			return 0;
		else
			return result;
	} 
	
	
	/**
	 * 5[�жϵ��Ƿ����߶���] 	
	 * ���߶��ϵ����ݣ�(p���߶�l���ڵ�ֱ����) && (��p�����߶�lΪ�Խ��ߵľ�����,�˾������߷ֱ���x��y��ƽ��)
	 * @param l
	 * @param p
	 * @return
	 */
	static boolean online(LINESEG l,POINT p) 
	{ 
		return( (multiply(l.e,p,l.s)==0) &&( ( (p.x-l.s.x)*(p.x-l.e.x)<=0 )&&( (p.y-l.s.y)*(p.y-l.e.y)<=0 ) ) ); 
	} 
	
	/**
	 * 6.[��һ����ĳ����ת�������]
	 * (java�����ж������ڶ��ڴ�����,�ں�����new����,Ȼ�󷵻ض���������ǿ��Ե�.����������ֻ����ն���û������ָ��Ķ���)
	 * @param o  ��ת��Բ�ĵ�
	 * @param alpha  ��ʱ����ת�ĽǶ�
	 * @param p		ԭʼ��
	 * @return	���ص�p�Ե�oΪԲ����ʱ����תalpha(��λ������)�����ڵ�λ��
	 */
	static POINT rotate(POINT o,double alpha,POINT p) 
	{ 
		POINT tp=new POINT(); 
		POINT pp = new POINT(p);//�����޸�ԭp��ֵ
		pp.x-=o.x; 
		pp.y-=o.y; 
		tp.x=pp.x*Math.cos(alpha)-pp.y*Math.sin(alpha)+o.x; 
		tp.y=pp.y*Math.cos(alpha)+pp.x*Math.sin(alpha)+o.y; 
		return tp; 
	} 

	/**
	 * 7.[��ʸ���н�]
	 * ���ض�����o�㣬��ʼ��Ϊos����ֹ��Ϊoe�ļн�(��λ������) 
		�����������߶�֮��ļн� 
	ԭ��
		r = dotmultiply(s,e,o) / (dist(o,s)*dist(o,e))
		r'= multiply(s,e,o)

		r >= 1	angle = 0;
		r <= -1	angle = -PI
		-1<r<1 && r'>0	angle = arccos(r)
	-	1<r<1 && r'<=0	angle = -arccos(r)
	 * @param o  ����
	 * @param s	 ��ʼ��(��ʼ����os)
	 * @param e  ��ֹ��(��ֹ��Ϊoe)
	 * @return	�Ƕ�С��pi��������ֵ 
	 * 			�Ƕȴ���pi�����ظ�ֵ 
	 * 			�Ƕȵ���pi������-PI
	 */
	static double angle(POINT o,POINT s,POINT e) 
	{ 
		double cosfi,fi,norm; 
		double dsx = s.x - o.x; 
		double dsy = s.y - o.y; 
		double dex = e.x - o.x; 
		double dey = e.y - o.y; 

		cosfi=dsx*dex+dsy*dey; 
		norm=(dsx*dsx+dsy*dsy)*(dex*dex+dey*dey); 
		cosfi /= Math.sqrt( norm ); 

		if (cosfi >=  1.0 ) return 0; 
		if (cosfi <= -1.0 ) return -PI; 

		fi=Math.acos(cosfi); 
		if (dsx*dey-dsy*dex>0) return fi;      // ˵��ʸ��os ��ʸ�� oe��˳ʱ�뷽�� 
		return -fi; 
	}
	
	
	  /*****************************\ 
	  *                             * 
	  *      �߶μ�ֱ�ߵĻ�������   * 
	  *                             * 
	  \*****************************/
	
	/**
	 * 1.[�����߶εĹ�ϵ]
	 * �жϵ����߶εĹ�ϵ,��;�ܹ㷺 
		�������Ǹ�������Ĺ�ʽд�ģ�P�ǵ�C���߶�AB����ֱ�ߵĴ��� 

               AC dot AB 
       r =     --------- 
                ||AB||^2 
            (Cx-Ax)(Bx-Ax) + (Cy-Ay)(By-Ay) 
         = ------------------------------- 
                         L^2         
    * @param p   P�ǵ�C���߶�AB����ֱ�ߵĴ��� (ע��:pΪ����)
	 * @param l		�߶�l
	 * @return	r=0      P = A 
	 * 			r=1      P = B 
	 * 			r<0		 P is on the backward extension of AB(����A����) 
	 * 			r>1      P is on the forward extension of AB(����B���ҷ�)
	 * 			0<r<1	 P is interior to AB (��AB��)
	 */
	double relation(POINT p,LINESEG l) 
	{ 
		LINESEG tl = new LINESEG(); 
		tl.s=l.s; 
		tl.e=p; 
		return dotmultiply(tl.e,l.e,l.s)/(dist(l.s,l.e)*dist(l.s,l.e)); 
	} 
	
	/**
	 * 2.[��㵽�߶�����ֱ�ߴ��ߵĴ���]
	 * @param p	��p
	 * @param l �߶�l
	 * @return	�����
	 */
	POINT perpendicular(POINT p,LINESEG l) 
	{ 
		double r=relation(p,l); 
		POINT tp = new POINT(); 
		tp.x=l.s.x+r*(l.e.x-l.s.x); 
		tp.y=l.s.y+r*(l.e.y-l.s.y); 
		return tp; 
	} 

	/**
	 * 3.[�㵽�߶ε������] 
	 * ���p���߶�l������� 
	 * ע�⣺np���߶�l�ϵ���p����ĵ㣬��һ���Ǵ���(��Ϊ���㲻һ�����߶���)
	 * @param p	��p
	 * @param l	�߶�l
	 * @return	p���߶�l�������
	 */
	POINT ptolinesegdist(POINT p,LINESEG l) 
	{ 
		double r=relation(p,l); 
		if(r<0) 
		{ 
			return l.s; 
		} 
		if(r>1) 
		{ 
			return l.e;
		} 
		return perpendicular(p,l); 
	} 
	
	/**
	 * [�㵽�߶ε���̾���]
	 * @param p
	 * @param l
	 * @return
	 */
	double ptolinesegdistLength(POINT p,LINESEG l) 
	{ 
		 POINT np = ptolinesegdist(p,l);
		 return dist(p,np);
	} 
		
	/**
	 * 4.[�㵽�߶�����ֱ�ߵľ���]
	 * ���p���߶�l����ֱ�ߵľ���,ע�Ȿ�������ϸ�����������
	 * @param p
	 * @param l
	 * @return
	 */
	static double ptoldist(POINT p,LINESEG l) 
	{ 
		return Math.abs(multiply(p,l.e,l.s))/dist(l.s,l.e); //�˴�����ƽ���ı��������������
	} 
	
	/**
	 * 5.[�㵽�߶�����ֱ�ߵľ������2(���ô���)]
	 * @param p
	 * @param l
	 * @return
	 */
	double ptoldist2(POINT p,LINESEG l) 
	{ 
		POINT tp = perpendicular(p,l);
		return dist(p,tp);
	} 

	

	
	/**
	 * 7.[��ʸ���н�����]
	 * ע�⣺������������нǵĻ���ע�ⷴ���Һ����Ķ������Ǵ� 0��pi
	 * @param l1	ʸ���߶�l1
	 * @param l2	ʸ���߶�l2
	 * @return		l1��l2�нǵ�����ֵ(-1~1)
	 */
	double cosine(LINESEG l1,LINESEG l2) 
	{ 
		return (((l1.e.x-l1.s.x)*(l2.e.x-l2.s.x) + 
		(l1.e.y-l1.s.y)*(l2.e.y-l2.s.y))/(dist(l1.e,l1.s)*dist(l2.e,l2.s))) ; 
	} 
	
	/**
	 * 8.[���߶�֮��ļн�]
	 * �����߶�l1��l2֮��ļн� ��λ:����    ��Χ(-pi��pi) ��l1��ʱ��ת��l2Ϊ��ֵ,����Ϊ��ֵ  
	 * @param l1
	 * @param l2
	 * @return
	 */
	static double lsAngle(LINESEG l1,LINESEG l2) 
	{ 
		POINT o = new POINT();
		POINT s = new POINT();
		POINT e = new POINT();
		o.x=o.y=0; 
		s.x=l1.e.x-l1.s.x; 
		s.y=l1.e.y-l1.s.y; 
		e.x=l2.e.x-l2.s.x; 
		e.y=l2.e.y-l2.s.y; 
		return angle(o,s,e); 
	} 
	
	/**
	 * 9.[�ж��߶��Ƿ��ཻ]
	 * ����߶�u��v�ཻ(�����ཻ�ڶ˵㴦)ʱ������true 
	 * �ж�P1P2����Q1Q2�������ǣ�( P1 - Q1 ) �� ( Q2 - Q1 ) * ( Q2 - Q1 ) �� ( P2 - Q1 ) >= 0��
	 * �ж�Q1Q2����P1P2�������ǣ�( Q1 - P1 ) �� ( P2 - P1 ) * ( P2 - P1 ) �� ( Q2 - P1 ) >= 0��
	 * @param u		�߶�u
	 * @param v		�߶�u
	 * @return		�Ƿ��ཻ(�����ཻ�ڶ˵�)
	 */
	static boolean intersect(LINESEG u,LINESEG v) 
	{ 
		return( (Math.max(u.s.x,u.e.x)>=Math.min(v.s.x,v.e.x))&&           //�ų�ʵ�� 
				(Math.max(v.s.x,v.e.x)>=Math.min(u.s.x,u.e.x))&& 
				(Math.max(u.s.y,u.e.y)>=Math.min(v.s.y,v.e.y))&& 
				(Math.max(v.s.y,v.e.y)>=Math.min(u.s.y,u.e.y))&& 
				(multiply(v.s,u.e,u.s)*multiply(u.e,v.e,u.s)>=0)&&         //����ʵ�� 
				(multiply(u.s,v.e,v.s)*multiply(v.e,u.e,v.s)>=0)); 
	} 
	
	/**
	 * 10.[�ж��߶��Ƿ��ཻ�������ڶ˵㴦]
	 * @param u		�߶�u
	 * @param v		�߶�u
	 * @return	(�߶�u��v�ཻ)&&(���㲻��˫���Ķ˵�) ʱ����true
	 */
	static boolean intersect_A(LINESEG u,LINESEG v) 
	{ 
		return	((intersect(u,v))&& 
				(!online(u,v.s))&& 
				(!online(u,v.e))&& 
				(!online(v,u.e))&& 
				(!online(v,u.s))); 
	} 
		
	/**
	 * [�ж��߶�u�Ƿ�����߶�v����ֱ��]  
	 * @param u	
	 * @param v	
	 * @return	�߶�v����ֱ�����߶�u�ཻʱ����true
	 */
	boolean intersect_l(LINESEG u,LINESEG v) 
	{ 
		return multiply(u.s,v.e,v.s)*multiply(v.e,u.e,v.s)>=0; 
	} 
	
	/**
	 * 11.[���߶�����ֱ�ߵķ���]
	 * ������֪�������꣬����������ֱ�߽������̣� a*x+b*y+c = 0  (a >= 0)
	 * @param p1
	 * @param p2
	 * @return
	 */
	static LINE makeline(POINT p1,POINT p2) 
	{ 
		LINE tl = new LINE(); 
		int sign = 1; 
		tl.a=p2.y-p1.y; 
		if(tl.a<0) 
		{ 
			sign = -1; 
			tl.a=sign*tl.a; 
		} 
		tl.b=sign*(p1.x-p2.x); 
		tl.c=sign*(p1.y*p2.x-p1.x*p2.y); 
		return tl; 
	} 
	
	/**
	 * 12.[��ֱ�ߵ�б��]
	 * @param l
	 * @return
	 */
	double slope(LINE l) 
	{ 
		if(Math.abs(l.a) < 1e-20)
			return 0; 
		if(Math.abs(l.b) < 1e-20)
			return INF; 
		return -(l.a/l.b); 
	} 
	
	/**
	 * 13.[��ֱ�ߵ���б��]
	 * @param l	ֱ��l
	 * @return	����ֱ�ߵ���б��alpha(0 - pi) 
	 */
	double alpha(LINE l) 
	{ 
		if(Math.abs(l.a)< EP)
			return 0; 
		if(Math.abs(l.b)< EP)
			return PI/2; 
		double k=slope(l); 
		if(k>0) 
			return Math.atan(k); 
		else 
			return PI+Math.atan(k); 
	}
	
	/**
	 * 14.[������ĳֱ�ߵĶԳƵ�] 
	 * @param l	ֱ��l
	 * @param p	��p
	 * @return	��p����ֱ��l�ĶԳƵ�
	 */
	POINT symmetry(LINE l,POINT p) 
	{ 
	   POINT tp = new POINT(); 
	   tp.x=((l.b*l.b-l.a*l.a)*p.x-2*l.a*l.b*p.y-2*l.a*l.c)/(l.a*l.a+l.b*l.b); 
	   tp.y=((l.a*l.a-l.b*l.b)*p.y-2*l.a*l.b*p.x-2*l.b*l.c)/(l.a*l.a+l.b*l.b); 
	   return tp; 
	} 

	/**
	 * 15.[�ж�����ֱ���Ƿ��ཻ����ֱ�߽���]
	 * @param l1	ֱ�� l1(a1*x+b1*y+c1 = 0)
	 * @param l2	ֱ��l2(a2*x+b2*y+c2 = 0)
	 * @return	����ཻ,���ؽ���p;���򷵻�null
	 */
	static POINT  lineintersect(LINE l1,LINE l2) // �� L1��L2 
	{ 
		POINT p = null;
		double d=l1.a*l2.b-l2.a*l1.b; 
		if(Math.abs(d)<EP) // ���ཻ 
			return p; 
		p = new POINT();
		p.x = (l2.c*l1.b-l1.c*l2.b)/d; 
		p.y = (l2.a*l1.c-l1.a*l2.c)/d; 
		return p; 
	} 
	
	/**
	 * 16.[�ж��߶��Ƿ��ཻ������ཻ���ؽ���]
	 * @param l1
	 * @param l2
	 * @return ����߶�l1��l2�ཻ,���ؽ���;���򷵻�null
	 */
	static POINT intersection(LINESEG l1,LINESEG l2) 
	{ 
		LINE ll1,ll2; 
		ll1=makeline(l1.s,l1.e); 
		ll2=makeline(l2.s,l2.e); 
		POINT p = lineintersect(ll1,ll2);
		if(p!=null){
			if(online(l1,p)&&online(l2,p))
				return p;
			else
				return null;
		}
		else 
			return null; 
	} 
	
	/******************************\ 
	*							  * 
	* ����γ����㷨ģ��		  * 
	*							  * 
	\******************************/ 
	// ������ر�˵�����������ζ���Ҫ����ʱ������ 

	
//	/**
//	 * 1.[�ж϶�����Ƿ��Ǽ򵥶����] 
//	 * Ҫ �����붥�����а���ʱ������ 
//	 * ˵ �����򵥶���ζ���(�򵥶�����Ǳ߲��ཻ�Ķ����,�ܽ�ƽ��ֳ��������򣬼����ں�����.��͹�����֣��򵥶���η�͹����κͰ�����Σ���͹���ı�ʾ�����ڽǶ�������180�㣬����֮��)�� 
//	 * 1��ѭ�������������߶ζԵĽ�������֮�乲�еĵ����� 
//	 * 2�������ڵ��߶β��ཻ 
//	 * ������Ĭ�ϵ�һ�������Ѿ�����
//	 * @param vcount ������Ŀ
//	 * @param polygon[]	��������
//	 * @return	����Ķ�����Ǽ򵥶���Σ�����true
//	 */
//	boolean issimple(int vcount,POINT polygon[]) 
//	{ 
//		int i,cn; 
//		LINESEG l1 = new LINESEG();
//		LINESEG l2 = new LINESEG();
//		for(i=0;i<vcount;i++) 
//		{ 
//			l1.s=polygon[i]; 
//			l1.e=polygon[(i+1)%vcount]; 
//			cn=vcount-3; 
//			while(cn>0) 
//			{ 
//				l2.s=polygon[(i+2)%vcount]; 
//				l2.e=polygon[(i+3)%vcount]; 
//				if(intersect(l1,l2)) 
//					break; 
//				cn--; 
//			} 
//			if(cn>0) 
//				return false; 
//		} 
//		return true; 
//	} 
//	
//	/**
//	 * 2. ������ζ����͹����
//	 * @param vcount
//	 * @param polygon
//	 * @param bc
//	 * ����ֵ��������˳�򷵻ض���ζ����͹�����жϣ�bc[i]=1,iff:��i��������͹����
//	 */
//	void checkconvex(int vcount,POINT polygon[],boolean bc[]) 
//	{ 
//		int i,index=0; 
//		POINT tp=polygon[0]; 
//		for(i=1;i<vcount;i++) // Ѱ�ҵ�һ��͹���� 
//		{ 
//			if(polygon[i].y<tp.y||(polygon[i].y == tp.y&&polygon[i].x<tp.x)) 
//			{ 
//				tp=polygon[i]; 
//				index=i; 
//			} 
//		} 
//		int count=vcount-1; 
//		bc[index]=true; 
//		while(count>0) // �ж�͹���� 
//		{ 
//			if(multiply(polygon[(index+1)%vcount],polygon[(index+2)%vcount],polygon[index])>=0 ) 
//				bc[(index+1)%vcount]=true; 
//			else 
//				bc[(index+1)%vcount]=false; 
//			index++; 
//			count--; 
//		} 
//	}
//	
//	
//	/**
//	 * 3. �ж϶�����Ƿ�͹�����  
//	 * @param vcount
//	 * @param polygon
//	 * @return
//	 */
//	boolean isconvex(int vcount,POINT polygon[]) 
//	{ 
//		boolean [] bc = new boolean[MAXV]; 
//		checkconvex(vcount,polygon,bc); 
//		for(int i=0;i<vcount;i++) // ��һ��鶥�㣬�Ƿ�ȫ����͹���� 
//			if(!bc[i]) 
//				return false; 
//		return true; 
//	} 
//	
	
	
	/**
	 * 7. ���߷��жϵ��Ƿ��ڶ������ 10
	 * ���߷��жϵ�q������polygon��λ�ù�ϵ��Ҫ��polygonΪ�򵥶���Σ�������ʱ������ 
	 * ������ڶ�����ڣ�   ����0 
	 * ������ڶ���α��ϣ� ����1 
	 * ������ڶ�����⣺	����2
	 * @param vcount
	 * @param Polygon
	 * @param q
	 * @return
	 */
	int insidepolygon(int vcount,POINT Polygon[],POINT q) 
	{ 
		int c=0,i,n; 
		LINESEG l1= new LINESEG();
		LINESEG l2= new LINESEG();
		boolean bintersect_a,bonline1,bonline2,bonline3; 
		double r1,r2; 

		l1.s=q; 
		l1.e=q; 
		l1.e.x=INF; 
		n=vcount; 
		for (i=0;i<vcount;i++) 
		{ 
			l2.s=Polygon[i]; 
			l2.e=Polygon[(i+1)%n]; 
			if(online(l2,q))
				return 1; // ������ڱ��ϣ�����1 
			if ( (bintersect_a=intersect_A(l1,l2))|| // �ཻ�Ҳ��ڶ˵� 
			( (bonline1=online(l1,Polygon[(i+1)%n]))&& // �ڶ����˵��������� 
			( (!(bonline2=online(l1,Polygon[(i+2)%n])))&& /* ǰһ���˵�ͺ�һ���˵����������� */ 
			((r1=multiply(Polygon[i],Polygon[(i+1)%n],l1.s)*multiply(Polygon[(i+1)%n],Polygon[(i+2)%n],l1.s))>0) ||    
			(bonline3=online(l1,Polygon[(i+2)%n]))&&     /* ��һ������ˮƽ�ߣ�ǰһ���˵�ͺ�һ���˵�����������  */ 
				((r2=multiply(Polygon[i],Polygon[(i+2)%n],l1.s)*multiply(Polygon[(i+2)%n], 
			Polygon[(i+3)%n],l1.s))>0) 
					) 
				) 
			) c++; 
		} 
		if(c%2 == 1) 
			return 0; 
		else 
			return 2; 
	} 
	
	
	/**
	 * 8. �жϵ��Ƿ���͹������� 11 
	 * ��q��͹�����polygon��ʱ������true��ע�⣺�����polygonһ��Ҫ��͹����� 
	 * @param vcount
	 * @param polygon
	 * @param q
	 * @return
	 */
	boolean InsideConvexPolygon(int vcount,POINT polygon[],POINT q) // �����������Σ� 
	{ 
		POINT p= new POINT(); 
		LINESEG l = new LINESEG(); 
		int i; 
		p.x=0;p.y=0; 
		for(i=0;i<vcount;i++) // Ѱ��һ���϶��ڶ����polygon�ڵĵ�p������ζ���ƽ��ֵ 
		{ 
			p.x+=polygon[i].x; 
			p.y+=polygon[i].y; 
		} 
		p.x /= vcount; 
		p.y /= vcount; 

		for(i=0;i<vcount;i++) 
		{ 
			l.s=polygon[i];l.e=polygon[(i+1)%vcount]; 
			if(multiply(p,l.e,l.s)*multiply(q,l.e,l.s)<0) /* ��p�͵�q�ڱ�l�����࣬˵����q�϶��ڶ������ */ 
			break; 
		} 
		return (i==vcount); 
	} 
		
	/**
	 * 9. Ѱ�ҵ㼯��graham�㷨 12 
	 * @param PointSet
	 * @param ch
	 * @param n
	 */
	void Graham_scan(POINT PointSet[],POINT ch[],int n,int len) 
	{ 
		int i,j,k=0,top=2; 
		POINT tmp; 
		// ѡȡPointSet��y������С�ĵ�PointSet[k]����������ĵ��ж������ȡ����ߵ�һ�� 
		for(i=1;i<n;i++) 
			if ( PointSet[i].y<PointSet[k].y || (PointSet[i].y==PointSet[k].y) && (PointSet[i].x<PointSet[k].x) ) 
				k=i; 
		tmp=PointSet[0]; 
		PointSet[0]=PointSet[k]; 
		PointSet[k]=tmp; // ����PointSet��y������С�ĵ���PointSet[0] 
		for (i=1;i<n-1;i++) /* �Զ��㰴�����PointSet[0]�ļ��Ǵ�С����������򣬼�����ͬ�İ��վ���PointSet[0]�ӽ���Զ�������� */ 
		{ 
			k=i; 
			for (j=i+1;j<n;j++) 
				if ( multiply(PointSet[j],PointSet[k],PointSet[0])>0 ||  // ���Ǹ�С    
					(multiply(PointSet[j],PointSet[k],PointSet[0])==0) && /* ������ȣ�������� */        
					dist(PointSet[0],PointSet[j])<dist(PointSet[0],PointSet[k])
				   ) 
					k=j; 
			tmp=PointSet[i]; 
			PointSet[i]=PointSet[k]; 
			PointSet[k]=tmp; 
		} 
		ch[0]=PointSet[0]; 
		ch[1]=PointSet[1]; 
		ch[2]=PointSet[2]; 
		for (i=3;i<n;i++) 
		{ 
			while (multiply(PointSet[i],ch[top],ch[top-1])>=0) 
				top--; 
			ch[++top]=PointSet[i]; 
		} 
		len=top+1; 
	} 
	
	/**
	 * 10.Ѱ�ҵ㼯͹���ľ������ 13  
	 * ����˵��ͬgraham�㷨
	 * @param PointSet
	 * @param ch
	 * @param n
	 */
	void ConvexClosure(POINT PointSet[],POINT ch[],int n,int len) 
	{ 
		int top=0,i,index,first; 
		double curmax,curcos,curdis; 
		POINT tmp; 
		LINESEG l1 = new LINESEG();
		LINESEG l2 = new LINESEG();
		boolean []use = new boolean[MAXV]; 
		tmp=PointSet[0]; 
		index=0; 
		// ѡȡy��С�㣬�������һ������ѡȡ����� 
		for(i=1;i<n;i++) 
		{ 
			if(PointSet[i].y<tmp.y||PointSet[i].y == tmp.y&&PointSet[i].x<tmp.x) 
			{ 
				index=i; 
			} 
			use[i]=false; 
		} 
		tmp=PointSet[index]; 
		first=index; 
		use[index]=true; 

		index=-1; 
		ch[top++]=tmp; 
		tmp.x-=100; 
		l1.s=tmp; 
		l1.e=ch[0]; 
		l2.s=ch[0]; 

		while(index!=first) 
		{ 
			curmax=-100; 
			curdis=0; 
			// ѡȡ�����һ��ȷ���߼н���С�ĵ㣬������ֵ����� 
			for(i=0;i<n;i++) 
			{ 
				if(use[i])continue; 
				l2.e=PointSet[i]; 
				curcos=cosine(l1,l2); // ����cosֵ��н����ң���Χ�� ��-1 -- 1 �� 
				if(curcos>curmax || Math.abs(curcos-curmax)<1e-6 && dist(l2.s,l2.e)>curdis) 
				{ 
					curmax=curcos; 
					index=i; 
					curdis=dist(l2.s,l2.e); 
				} 
			} 
			use[first]=false;            //��յ�first�������־��ʹ������γɷ�յ�hull 
			use[index]=true; 
			ch[top++]=PointSet[index]; 
			l1.s=ch[top-2]; 
			l1.e=ch[top-1]; 
			l2.s=ch[top-1]; 
		} 
		len=top-1; 
	} 
	
	
	/**
	 * ����͹�����(������ʱ����)P��Q�Ľ�[�ѳ���������֤]
	 * Ҫ��P/Q����Ϊ͹�����
	 * @param P
	 * @param Q
	 * @return
	 */
	static MyPolygon conVexPolgyonAnd (MyPolygon P,MyPolygon Q){
		POINT[] P_Q = P.thisInQ(Q);
		POINT[] Q_P = Q.thisInQ(P);
		int numOfP_Q,numOfQ_P;
		for(numOfP_Q=0;P_Q[numOfP_Q]!=null;numOfP_Q++);
		for(numOfQ_P=0;Q_P[numOfQ_P]!=null;numOfQ_P++);
		if(numOfP_Q==0 || numOfQ_P==0)
			return null;//��ʱ˵������͹�����û�н���
		for(int i=0;i<numOfQ_P;i++){
			if(pINArr(P_Q,numOfP_Q,Q_P[i])){
				Utility.rightShift(Q_P,numOfQ_P,(numOfQ_P-i));
				break;
			}
		}
		//step2
		for(;;){
			for(int i=0;i<numOfP_Q;i++){
				if(Utility.equal_point(P_Q[i],Q_P[0])){
					Utility.rightShift(P_Q,numOfP_Q,(numOfP_Q-i-1));
					break;
				}
			}
			
			for(;;){
				//����ɾ��Q_P���׶���
				for(int i=1;i<numOfQ_P;i++){
					Q_P[i-1].setPoint(Q_P[i]);
				}
				numOfQ_P--;
				if(pINArr(P_Q,numOfP_Q,Q_P[0]) || numOfQ_P==0)
					break;
				else{
					P_Q[numOfP_Q] = new POINT(Q_P[0]);
					numOfP_Q++;
				}
			}
			if(numOfQ_P==0)
				break;
		}
		
		//�������ϣ����������µ�͹�����
		MyPolygon and = new MyPolygon(numOfP_Q,P_Q);
		and.format();
		return and;
	}
	
	static boolean pINArr(POINT []arr,int k,POINT p){
		for(int i=0;i<k;i++){
			if(Utility.equal_point(arr[i],p))
				return true;
		}
		return false;
	}
	
	/**
	 * ʵ�������ѭ������
	 * @param arr	����
	 * @param N	����Ԫ����
	 * @param k	ѭ������λ��
	 */
	 static void rightShift(POINT []arr, int N, int k)
	{
	    k %= N;
	    Utility.reverse(arr, 0, N-k-1);
	    Utility.reverse(arr, N-k, N-1);
	    Utility.reverse(arr, 0, N-1);
	}
	 static void  reverse(POINT []arr, int b, int e)
	{
	    for(; b < e; b++, e--)
	    {
	        POINT temp = new POINT(arr[e]);
	        arr[e].setPoint(arr[b]);
	        arr[b].setPoint(temp);
	    }
	}
	 
	 static MyPolygon genPolygon(double x,double y){
		 Random random = new Random();
		 double v = random.nextDouble();
		 MyPolygon genP;
		 if(v<Utility.rategenTriangle){
			 genP = Utility.genPolygonK(x, y, 3);
		 }
		 else if(v<Utility.rategenQuadrangle+Utility.rategenTriangle){
			 genP = Utility.genPolygonK(x, y, 4);
		 }
		 else if(v<Utility.rategenPentagon+Utility.rategenQuadrangle+Utility.rategenTriangle){
			 genP = Utility.genPolygonK(x, y, 5);
		 }
		 else {
			 genP = Utility.genPolygonK(x, y, 6);
		 }
		 return genP;
	 }
	 
	 /**
	  * ��x,yΪ��Ӿ��Σ�����k͹����
	  * @param x
	  * @param y
	  * @param k
	  * @return
	  */
	static MyPolygon  genPolygonK(double x,double y,int k){
		if(k>6)  
			k=6;
		MyPolygon rectangle = new MyPolygon(x,y,new POINT(0,0));
		 POINT[] vSet = new POINT[k];
		 Random random = new Random();
		 for(int i=0;i<k;i++)
			 vSet[i]=new POINT();
		 MyPolygon result;
		 int numOfTest = 0;
		 do{
			 numOfTest++;
			 if(k==3){
					if(random.nextFloat()<0.5){
						//ѡ����γ��������˵㣬�ڶԱ�ѡ��һ��������������
						vSet[0].setPoint(rectangle.vSet[0]);
						vSet[1].setPoint(rectangle.vSet[1]);
						vSet[2].x = random.nextDouble()*x;
						vSet[2].y = rectangle.vSet[2].y;
					}
					else{
						//ѡ����ο�������˵㣬�ڶԱ�ѡ��һ��������������
						vSet[0].setPoint(rectangle.vSet[1]);
						vSet[1].setPoint(rectangle.vSet[2]);
						vSet[2].x = 0;
						vSet[2].y = random.nextDouble()*y;
					}	
				 }
			 else if(k==4){
				 double v = random.nextDouble();
				 if(v<Utility.rateRectAngele){
					 //��ʱ���ɾ��Σ�ֱ����ԭ���μ���
					 result = new MyPolygon(rectangle,new POINT(0,0));
					 return result;//��ʱ������ת,���������Ҳ�϶��������
				 }
				 else if(v<Utility.rateRectAngele+Utility.rateParallelogram){
					 //��ʱ����ƽ���ı���
					 if(random.nextFloat()<0.5){
						 vSet[0].setPoint(rectangle.vSet[0]);
						 vSet[1].x = random.nextDouble()*x;
						 for(;vSet[1].x<x/5;)
							 vSet[1].x = random.nextDouble()*x;//��֤���ɵ�ƽ���ı��������С��ԭ���������1/5
						 vSet[1].y = 0;
						 vSet[2].setPoint(rectangle.vSet[2]);
						 vSet[3].x = x - vSet[1].x;
						 vSet[3].y = y;
					 }
					 else{
						 vSet[0].setPoint(rectangle.vSet[1]);
						 vSet[1].x = vSet[0].x;
						 vSet[1].y = random.nextDouble()*y;
						 for(;vSet[1].y<y/5;)
							 vSet[1].y = random.nextDouble()*y;
						 vSet[2].setPoint(rectangle.vSet[3]);
						 vSet[3].x = 0;
						 vSet[3].y = y-vSet[1].y;
					 }
				 }
				 else if(v<Utility.rateRectAngele+Utility.rateParallelogram+Utility.rateTrapezium){
					 //��ʱ��������
					 if(random.nextFloat()<0.5){
						 vSet[0].setPoint(rectangle.vSet[0]);
						 vSet[1].setPoint(rectangle.vSet[1]);
						 vSet[2].y = y;
						 vSet[3].y = y;
						 double min = random.nextDouble()*x;
						 double max = random.nextDouble()*x;
						 for(;Math.abs(max-min)<x/10;){ //��֤�ϱ߳��Ȳ�С��ԭ���δ˱߳���1/10
							 min = random.nextDouble()*x;
							 max = random.nextDouble()*x;
						 }
						 if(min<max){
							 vSet[2].x = max;
							 vSet[3].x = min;
						 }
						 else{
							 vSet[2].x = min;
							 vSet[3].x = max;
						 }			 
					 }
					 else{
						 vSet[0].setPoint(rectangle.vSet[1]);
						 vSet[1].setPoint(rectangle.vSet[2]);
						 vSet[2].x = 0;
						 vSet[3].x = 0;
						 double min = random.nextDouble()*y;
						 double max = random.nextDouble()*y;
						 for(;Math.abs(max-min)<y/10;){ //��֤�ϱ߳��Ȳ�С��ԭ���δ˱߳���1/10
							 min = random.nextDouble()*y;
							 max = random.nextDouble()*y;
						 }
						 if(min<max){
							 vSet[2].y = max;
							 vSet[3].y = min;
						 }
						 else{
							 vSet[2].y = min;
							 vSet[3].y = max;
						 }			 
					 }
				 }
				 else{  //��ʱ������ͨ���ı���
					 vSet[0].x = random.nextDouble()*x;
					 vSet[0].y = 0;
					 vSet[1].x = x;
					 vSet[1].y = random.nextDouble()*y;
					 for(;Utility.equal_point(vSet[0], vSet[1]);)//��֤vSet[0]��vSet[1]���غ�
						 vSet[1].y = random.nextDouble()*y;
					 vSet[2].y = y;
					 vSet[2].x = random.nextDouble()*x;
					 for(;Utility.equal_point(vSet[1], vSet[2]);)
						 vSet[2].x = random.nextDouble()*x;
					 vSet[3].x = 0;
					 vSet[3].y = random.nextDouble()*y;
					 for(;Utility.equal_point(vSet[3], vSet[2]) || Utility.equal_point(vSet[3], vSet[0]) ;)
						 vSet[3].y = random.nextDouble()*y;			 
				 }			 
			 }
			 else if(k==5){
				 vSet[0].y = 0;
				 vSet[1].y = 0;
				 double min = random.nextDouble()*x;
				 double max = random.nextDouble()*x;
				 for(;Math.abs(max-min)<x/10;){ //��֤�ϱ߳��Ȳ�С��ԭ���δ˱߳���1/10
					 min = random.nextDouble()*x;
					 max = random.nextDouble()*x;
				 }
				 if(min<max){
					 vSet[0].x = min;
					 vSet[1].x = max;	
				 }
				 else{
					 vSet[0].x = max;
					 vSet[1].x = min;
				 }	
				 vSet[2].x = x;
				 vSet[2].y = random.nextDouble()*y;
				 for(;Utility.equal_point(vSet[2], vSet[1]);)//��֤vSet[2]��vSet[1]���غ�
					 vSet[2].y = random.nextDouble()*y;
				 vSet[3].y = y;
				 vSet[3].x = random.nextDouble()*x;
				 for(;Utility.equal_point(vSet[3], vSet[2]);)
					 vSet[3].x = random.nextDouble()*x;
				 vSet[4].x = 0;
				 vSet[4].y = random.nextDouble()*y;
				 for(;Utility.equal_point(vSet[4], vSet[3]) || Utility.equal_point(vSet[4], vSet[0]) ;)
					 vSet[4].y = random.nextDouble()*y;		 
			 }
			 else { //����6���Σ�ȫ����6���δ���
				 vSet[0].y = 0;
				 vSet[1].y = 0;
				 double min = random.nextDouble()*x;
				 double max = random.nextDouble()*x;
				 for(;Math.abs(max-min)<x/10;){ //��֤�ϱ߳��Ȳ�С��ԭ���δ˱߳���1/10
					 min = random.nextDouble()*x;
					 max = random.nextDouble()*x;
				 }
				 if(min<max){
					 vSet[0].x = min;
					 vSet[1].x = max;	
				 }
				 else{
					 vSet[0].x = max;
					 vSet[1].x = min;
				 }	
				 int ran = random.nextInt(3);
				 if(ran == 0){
					 vSet[2].x = x;
					 vSet[3].x = x;
					 min = random.nextDouble()*y;
					 max = random.nextDouble()*y;
					 for(;Math.abs(max-min)<y/10 || (Utility.equal_point(vSet[1],new POINT(x,min))) ||(Utility.equal_point(vSet[1],new POINT(x,max)));){ //��֤�ϱ߳��Ȳ�С��ԭ���δ˱߳���1/10
						 min = random.nextDouble()*y;
						 max = random.nextDouble()*y;
					 }
					 if(min<max){
						 vSet[2].y = min;
						 vSet[3].y = max;	
					 }
					 else{
						 vSet[2].y = max;
						 vSet[3].y = min;
					 }	
					 vSet[4].y = y;
					 vSet[4].x = random.nextDouble()*x;
					 for(;Utility.equal_point(vSet[4], vSet[3]);)
						 vSet[4].x = random.nextDouble()*x;
					 vSet[5].x = 0;
					 vSet[5].y = random.nextDouble()*y;
					 for(;Utility.equal_point(vSet[5], vSet[4]) || Utility.equal_point(vSet[5], vSet[0]) ;)
						 vSet[5].y = random.nextDouble()*y;	
				 }
				 else if(ran == 1){
					 vSet[2].x = x;
					 vSet[2].y = random.nextDouble()*y;
					 for(;Utility.equal_point(vSet[2], vSet[1]);)//��֤vSet[2]��vSet[1]���غ�
						 vSet[2].y = random.nextDouble()*y;
					 vSet[3].y = y;
					 vSet[4].y = y;
					 min = random.nextDouble()*x;
					 max = random.nextDouble()*x;
					 for(;Math.abs(max-min)<x/10 || (Utility.equal_point(vSet[2],new POINT(min,y))) ||(Utility.equal_point(vSet[2],new POINT(max,y)));){ //��֤�ϱ߳��Ȳ�С��ԭ���δ˱߳���1/10
						 min = random.nextDouble()*x;
						 max = random.nextDouble()*x;
					 }
					 if(min<max){
						 vSet[3].x = max;
						 vSet[4].x = min;	
					 }
					 else{
						 vSet[3].x = min;
						 vSet[4].x = max;
					 }	
					 vSet[5].x = 0;
					 vSet[5].y = random.nextDouble()*y;
					 for(;Utility.equal_point(vSet[5], vSet[4]) || Utility.equal_point(vSet[5], vSet[0]) ;)
						 vSet[5].y = random.nextDouble()*y;	
				 }
				 else{
					 vSet[2].x = x;
					 vSet[2].y = random.nextDouble()*y;
					 for(;Utility.equal_point(vSet[2], vSet[1]);)//��֤vSet[2]��vSet[1]���غ�
						 vSet[2].y = random.nextDouble()*y;
					 vSet[3].y = y;
					 vSet[3].x = random.nextDouble()*x;
					 for(;Utility.equal_point(vSet[3], vSet[2]);)
						 vSet[3].x = random.nextDouble()*x;
					 vSet[4].x = 0;
					 vSet[5].x = 0;
					 min = random.nextDouble()*y;
					 max = random.nextDouble()*y;
					 for(;Math.abs(max-min)<y/10 || (Utility.equal_point(vSet[3],new POINT(0,min))) ||(Utility.equal_point(vSet[3],new POINT(0,max)))||(Utility.equal_point(vSet[0],new POINT(0,min))) ||(Utility.equal_point(vSet[0],new POINT(0,max)));){ //��֤�ϱ߳��Ȳ�С��ԭ���δ˱߳���1/10
						 min = random.nextDouble()*y;
						 max = random.nextDouble()*y;
					 }
					 if(min<max){
						 vSet[5].y = min;
						 vSet[4].y = max;	
					 }
					 else{
						 vSet[5].y = max;
						 vSet[4].y = min;
					 }				 
				 }		 
			 }
			 result = new MyPolygon(k,vSet);
			 result.format();
		 }while(result.area/(x*y)<Utility.ratioArea && k!=3 && numOfTest <1000);
		 
		 MyPolygon result2 = result.rotate(random.nextInt(4)*90);//����ת0��90��180��270���ֽǶ�
		 result2.moveTo(new POINT(0,0));
		 return result2;
	 }
	
	
	//������������(�Ǽ���)
	 /**
	  * �ж�����ArrayList<POINT>�Ƿ������ͬ�ĵ�
	  */
	static boolean arrayListIsEqual(ArrayList<POINT> A,ArrayList<POINT> B){
		for(POINT o:A){
			if(!Utility.arrayListHasP(B, o))
				return false;
		}
		for(POINT o:B){
			if(!Utility.arrayListHasP(A, o))
				return false;
		}
		return true;
	}
	
	/**
	 * �ж�ArrayList arr���Ƿ������p
	 * @return
	 */
	static boolean arrayListHasP(ArrayList<POINT> arr,POINT p){
		for(POINT temp:arr){
			if(temp.equ(p))
				return true;
		}
		return false;
	}
	
}

/**
 * ��
 * @author xung
 *
 */
class POINT {
		double x; 
		double y; 
		
		POINT(double a, double b) { 
			x=a; 
			y=b;
			} //constructor 
		POINT() { 
			x=0; 
			y=0;
			}
		
		POINT(POINT p){
			this.x = p.x;
			this.y = p.y;
		}
		
		/**
		 * ���õ�ǰ����������p��ͬ
		 * @param p
		 */
		void setPoint(POINT p){
			this.x = p.x;
			this.y = p.y;
		}
		
		void setPoint(double x,double y){
			this.x = x;
			this.y = y;
		}
		
		boolean equ(POINT p){
			return ( (Math.abs(this.x-p.x)<Utility.EP)&&(Math.abs(this.y-p.y)<Utility.EP) ); 
		}
	}; 

/**
 * �߶�
 * @author xung
 *
 */
class LINESEG 
{ 
	POINT s; 
	POINT e; 
	LINESEG(POINT s, POINT e) {
		this.s=s; 
		this.e=e;
	} 
	LINESEG() { } 
}; 

/**
 * ֱ��
 * @author xung
 *
 */
class LINE           // ֱ�ߵĽ������� a*x+b*y+c=0  Ϊͳһ��ʾ��Լ�� a >= 0
{ 
   double a; 
   double b; 
   double c; 
   
   LINE(double d1, double d2, double d3) {
	   a=d1; b=d2; c=d3;
	   } 
   LINE() {
	   a=1; b=-1; c=0;
	   } 
}; 

/**
 * �����
 * @author xung
 *
 */
class MyPolygon{
	int vCount;//������Ŀ
	POINT vSet[];//���㼯,����ζ���Ҫ����ʱ������(���ǵ������ͨ����,�罫����ֲ��C++��,������������ʵ��,��û��ʹ��java��ArrayListʵ��)
	double angle[];//�ߵķ�������x��ĽǶ�
	double area; //���
	
	MyPolygon(){
		this.vCount =0;
		this.vSet = new POINT[Utility.MAXV];
		this.angle = new double[Utility.MAXV];
		this.area = 0;
	}
	
	MyPolygon(int vCount,POINT []vSet){
		this.vCount = vCount;
		this.vSet = new POINT[Utility.MAXV];
		for(int i=0;i<vCount;i++){
			this.vSet[i] = new POINT();
			this.vSet[i].x = vSet[i].x;
			this.vSet[i].y = vSet[i].y;
		}
		this.vSet[this.vCount] = new POINT();//����this.vSet[this.vCount]
		this.angle = new double[Utility.MAXV];
		this.area = 0;//��Ԥ��Ϊ0,�õ�ʱ�ټ���
	}
	
	MyPolygon(ArrayList<POINT> list){
		this.vCount = list.size();
		this.vSet = new POINT[Utility.MAXV];
		for(int i=0;i<this.vCount;i++){
			this.vSet[i] = new POINT();
			this.vSet[i].x = list.get(i).x;
			this.vSet[i].y =list.get(i).y;
		}
		this.vSet[this.vCount] = new POINT();//����this.vSet[this.vCount]	
		this.angle = new double[Utility.MAXV];
		this.area = 0;//��Ԥ��Ϊ0,�õ�ʱ�ټ���
	}
	
	/**
	 * ��������͹�����A����һ���µ�͹�����,ʹ�����õ�λ�ڵ�p
	 * @param A
	 * @param p
	 */
	MyPolygon(MyPolygon A,POINT p){
		//�ȼ�����ƶ�������
		POINT move = new POINT();
		move.x = p.x-A.vSet[0].x;
		move.y = p.y-A.vSet[0].y;
		
		this.vCount=A.vCount;
		this.vSet = new POINT[Utility.MAXV];
		for(int i=0;i<A.vCount;i++){
			this.vSet[i] = new POINT();
			this.vSet[i].x = A.vSet[i].x+move.x;
			this.vSet[i].y = A.vSet[i].y+move.y;
		}
		this.vSet[this.vCount] = new POINT();//����this.vSet[this.vCount]	
		this.angle = new double[Utility.MAXV];
		for(int i=0;i<A.vCount;i++){
			this.angle[i]=A.angle[i];
		}
		this.area = A.area;
		this.format();
	}
	
	//���ݳ�(������x��ƽ��)����(�����y��ƽ��)����һ�����Σ������õ�λ�ڵ�p
	MyPolygon(double length,double width,POINT p){
		this.vCount = 4;
		this.vSet = new POINT[Utility.MAXV];
		for(int i=0;i<vCount;i++){
			this.vSet[i] = new POINT();
		}
		this.vSet[0].x = p.x;
		this.vSet[0].y = p.y;
		this.vSet[1].x = p.x+length;
		this.vSet[1].y = p.y;
		this.vSet[2].x = p.x+length;
		this.vSet[2].y = p.y+width;
		this.vSet[3].x = p.x;
		this.vSet[3].y = p.y+width;
		this.vSet[this.vCount] = new POINT();//����this.vSet[this.vCount]	
		this.angle = new double[Utility.MAXV];
		this.area = 0;//��Ԥ��Ϊ0,�õ�ʱ�ټ���
		this.format();
	}
	
	/**
	 * �Զ���ν��м��(����˳��/����/͹����)
	 * @return
	 */
	int  check(){
		if(this.isconterclock()){
			if(this.isSimple()){
				if(this.isConvex()){
					return 0;//
				}
				return 3;//��ʾ����͹�����
			}
			return 2;//��ʾ���Ǽ򵥶����
		}
		return 1;//��ʾ���㲻�ǰ���ʱ��˳������
	}
	
	/**
	 * �������õ�(���µ�,����ж��,��ȡ������)�������е�index
	 */
	int getRefV(){
		int index=0;
		for(int i=1;i<this.vCount;i++){
			if(this.vSet[i].y<this.vSet[index].y){
				index=i;
				continue;
			}
			if(this.vSet[i].y==this.vSet[index].y){
				if(this.vSet[i].x<this.vSet[index].x){
					index=i;
				}
			}
		}
		return index;
	}
		
	/**
	 * ʵ�������ѭ������(���ھ����䰴��ʱ�����붥�������õ�������ԭ��,����ʼ�㲻�����õ�����)
	 * @param arr	����
	 * @param N	����Ԫ����
	 * @param k	ѭ������λ��
	 */
	void rightShift(POINT []arr, int N, int k)
	{
	    k %= N;
	    reverse(arr, 0, N-k-1);
	    reverse(arr, N-k, N-1);
	    reverse(arr, 0, N-1);
	}
	void reverse(POINT []arr, int b, int e)
	{
	    for(; b < e; b++, e--)
	    {
	        POINT temp = new POINT(arr[e]);
	        arr[e].setPoint(arr[b]);
	        arr[b].setPoint(temp);
	    }
	}
	
	/**
	 * ���������x������ļн�(ǰ�����Ѿ��ǹ涨�ı�׼��ʽ,�����õ�λ�������0λ,�����ɱ�֤�нǵ���)
	 */
	void calcAngle(){
		POINT os = new POINT(0,0);
		POINT oe = new POINT(1,0);
		LINESEG l1 = new LINESEG(os,oe);//���߶δ���x��������
		for(int i=0;i<this.vCount;i++){
			LINESEG l2 = new LINESEG(this.vSet[i],this.vSet[(i+1)%this.vCount]);
			this.angle[i]=Utility.lsAngle(l1,l2) ;
			if(this.angle[i]<0)
				this.angle[i]=this.angle[i]+2*Utility.PI;
		}
	}
	
	void format(){
		//�������õ�(���µ�,����ж��,��ȡ������),������ʹ�����õ�λ��vSet����ĵ�0λ
		int refVIndex = this.getRefV();
		if(refVIndex!=0)
			this.rightShift(this.vSet,this.vCount,this.vCount-refVIndex);//���õ㲻�ڵ�0λ,�������λ	
		this.vSet[this.vCount].setPoint(this.vSet[0]);//����this.vSet[this.vCount]��ֵ���0λ�����õ��������ͬ
		//����������
		this.area = this.areaOfPolygon();
	}

	

	/**
	 * [�ж϶�����Ƿ��Ǽ򵥶����] 
	 * Ҫ �����붥�����а���ʱ������ 
	 * ˵ �����򵥶���ζ���(�򵥶�����Ǳ߲��ཻ�Ķ����,�ܽ�ƽ��ֳ��������򣬼����ں�����.��͹�����֣��򵥶���η�͹����κͰ�����Σ���͹���ı�ʾ�����ڽǶ�������180�㣬����֮��)�� 
	 * 1��ѭ�������������߶ζԵĽ�������֮�乲�еĵ����� 
	 * 2�������ڵ��߶β��ཻ 
	 * ������Ĭ�ϵ�һ�������Ѿ�����
	 * @param vcount ������Ŀ
	 * @param polygon[]	��������
	 * @return	����Ķ�����Ǽ򵥶���Σ�����true
	 */
	boolean isSimple() 
	{ 
		int i,cn; 
		LINESEG l1 = new LINESEG();
		LINESEG l2 = new LINESEG();
		for(i=0;i<vCount;i++) 
		{ 
			l1.s=vSet[i]; 
			l1.e=vSet[(i+1)%vCount]; 
			cn=vCount-3; 
			while(cn>0) 
			{ 
				l2.s=vSet[(i+2)%vCount]; 
				l2.e=vSet[(i+3)%vCount]; 
				if(Utility.intersect(l1,l2)) 
					break; 
				cn--; 
			} 
			if(cn>0) 
				return false; 
		} 
		return true; 
	} 
		
	
	/**
	 * [�ж϶�����Ƿ�͹�����]  
	 * Ҫ �����붥�����а���ʱ������(����,�����жϻ���ִ���)
	 * @param vcount
	 * @param polygon
	 * @return
	 */
	boolean isConvex() 
	{ 
		boolean [] bc = new boolean[Utility.MAXV]; 
		//���水����˳�������ζ����͹�����жϣ�bc[i]=1,iff:��i��������͹����
		int index=0; 
		POINT tp=vSet[0]; 
		for(int i=1;i<vCount;i++) // Ѱ�ҵ�һ��͹���� 
		{ 
			if(vSet[i].y<tp.y||(vSet[i].y == tp.y&&vSet[i].x<tp.x)) 
			{ 
				tp=vSet[i]; 
				index=i; 
			} 
		} 
		int count=vCount-1; 
		bc[index]=true; 
		while(count>0) // �ж�͹���� 
		{ 
			if(Utility.multiply(vSet[(index+1)%vCount],vSet[(index+2)%vCount],vSet[index%vCount])>=0 ) 
				bc[(index+1)%vCount]=true; 
			else 
				bc[(index+1)%vCount]=false; 
			index++; 
			count--; 
		} 
		
		for(int i=0;i<vCount;i++) // ��һ��鶥�㣬�Ƿ�ȫ����͹���� 
			if(!bc[i]) 
				return false; 
		return true; 
	} 
	
	/**
	 * [��������� ](͹��������,�Ǽ򵥶���ν����Ϊ0)
	 * @param vcount
	 * @param polygon
	 * @return	��ʱ��ʱ������ֵ,˳ʱ�뷵�ظ������ֵ
	 */
	double areaOfPolygon() 
	{ 
		int i; 
		double s; 
		if (vCount<3) 
			return 0; 
		s=vSet[0].y*(vSet[vCount-1].x-vSet[1].x); 
		for (i=1;i<vCount;i++) 
			s+=vSet[i].y*(vSet[(i-1)].x-vSet[(i+1)%vCount].x); 
		return s/2; 
	}
	
	/**
	 * [�ж϶���ζ�������з���(����һ)]
	 * @param vcount
	 * @param polygon
	 * @return	��ʱ�뷽��,����ture
	 */
	boolean isconterclock() 
	{ 
		return areaOfPolygon()>0; 
	} 
	
	
//	/**
//	 * 6. �ж϶���ζ�������з���(������)
//	 * @param vcount
//	 * @param polygon
//	 * @return	��ʱ�뷽��,����ture
//	 */
//	boolean isccwize() 
//	{ 
//		int i,index; 
//		POINT a,b,v; 
//		v=vSet[0]; 
//		index=0; 
//		for(i=1;i<vCount;i++) // �ҵ���������󶥵㣬�϶���͹���� 
//		{ 
//			if(vSet[i].y<v.y||vSet[i].y == v.y && vSet[i].x<v.x) 
//			{ 
//				index=i; 
//			} 
//		} 
//		a=vSet[(index-1+vCount)%vCount]; // ����v��ǰһ���� 
//		b=vSet[(index+1)%vCount]; // ����v�ĺ�һ���� 
//		return Utility.multiply(v,b,a)>0; 
//	} 
	
	/**
	 * 7. [���߷��жϵ�q������polygon��λ�ù�ϵ]
	 * Ҫ��:��polygonΪ�򵥶���΢ڶ���ζ�����ʱ������  
	 * @param q
	 * @return	 ����0(���ڶ������ );  
	 * 			����1(���ڶ���α���);  
	 * 			����2(���ڶ������);
	 */
	int insidepolygon(POINT q) {
		int c = 0, i, n;
		LINESEG l1 = new LINESEG();
		LINESEG l2 = new LINESEG();
		// boolean bintersect_a,bonline1,bonline2,bonline3;
		// double r1,r2;

		l1.s = q;
		POINT end = new POINT(Utility.INF,q.y);
		l1.e = end;
		n = this.vCount;
		for (i = 0; i < this.vCount; i++) {
			l2.s = this.vSet[i];
			l2.e = this.vSet[(i + 1) % n];
			if (Utility.online(l2, q))
				return 1; // ������ڱ��ϣ�����1
			if ((Utility.intersect_A(l1, l2))
					|| // �ཻ�Ҳ��ڶ˵�
					((Utility.online(l1, this.vSet[(i + 1) % n])) && // �ڶ����˵���������
					((!(Utility.online(l1, this.vSet[(i + 2) % n])))
							&& /* ǰһ���˵�ͺ�һ���˵����������� */
							((Utility.multiply(this.vSet[i], this.vSet[(i + 1)% n], l1.s) * Utility.multiply(this.vSet[(i + 1) % n], this.vSet[(i + 2)% n], l1.s)) > 0) 
							|| (Utility.online(l1, this.vSet[(i + 2) % n]))
							&& /* ��һ������ˮƽ�ߣ�ǰһ���˵�ͺ�һ���˵����������� */
							((Utility.multiply(this.vSet[i], this.vSet[(i + 2)% n], l1.s) * Utility.multiply(this.vSet[(i + 2) % n], this.vSet[(i + 3)% n], l1.s)) > 0))))
				c++;
		}
		if (c % 2 == 1)
			return 0;
		else
			return 2;
	}
	
	/**
	 * [�жϵ��Ƿ���͹������ڻ���]
	 * Ҫ��:�����polygonһ��Ҫ��͹����� 
	 * @param q
	 * @return	��q��͹�����polygon�ڻ�͹�������ʱ������true;���򷵻�false
	 */
	boolean InsideConvexPolygon(POINT q) { 
		POINT p= new POINT(); 
		LINESEG l = new LINESEG(); 
		int i; 
		p.x=0;p.y=0; 
		for(i=0;i<this.vCount;i++) // Ѱ��һ���϶��ڶ����polygon�ڵĵ�p������ζ���ƽ��ֵ 
		{ 
			p.x+=this.vSet[i].x; 
			p.y+=this.vSet[i].y; 
		} 
		p.x /= this.vCount; 
		p.y /= this.vCount; 

		for(i=0;i<this.vCount;i++) 
		{ 
			l.s=this.vSet[i];l.e=this.vSet[(i+1)%this.vCount]; 
			if(Utility.multiply(p,l.e,l.s)*Utility.multiply(q,l.e,l.s)<0) /* ��p�͵�q�ڱ�l�����࣬˵����q�϶��ڶ������ */ 
			break; 
		} 
		return (i==this.vCount); 
	} 
	
	
	/*********************************************************************************************  
	�ж��߶��Ƿ��ڼ򵥶������(ע�⣺����������͹����Σ�������㷨���Ի���) 
   	��Ҫ����һ���߶ε������˵㶼�ڶ������(�����͹�����,ֻ�˼���)�� 
	��Ҫ���������߶κͶ���ε����б߶����ڽ��� 
	��;��	1. �ж������Ƿ��ڼ򵥶������ 
			2. �жϼ򵥶�����Ƿ�����һ���򵥶������ 
**********************************************************************************************/ 
	/**
	 * [�ж��߶��Ƿ��ڼ򵥶������]
	 * ע��:�߶����ж˵��ڼ򵥶���α���,����Ϊ�䲻�ڶ������
	 * @param l
	 * @return ������ڲ�,����true
	 */
	boolean LinesegInsidePolygon(LINESEG l) 
	{ 
		// �ж��߶�l�Ķ˵��Ƿ񲻶��ڶ������ 
		if(this.insidepolygon(l.s)>0||this.insidepolygon(l.e)>0) 
			return false; 
		int top=0,i,j; 
		POINT []PointSet = new POINT[Utility.MAXV]; 
		POINT tmp = new POINT();
		LINESEG s = new LINESEG(); 

		for(i=0;i<this.vCount;i++) 
		{ 
			s.s=this.vSet[i]; 
			s.e=this.vSet[(i+1)%this.vCount]; 
			if(Utility.online(s,l.s)) //�߶�l����ʼ�˵����߶�s�� 
				PointSet[top++]=l.s; 
			else if(Utility.online(s,l.e)) //�߶�l����ֹ�˵����߶�s�� 
				PointSet[top++]=l.e; 
			else 
			{ 
				if(Utility.online(l,s.s)) //�߶�s����ʼ�˵����߶�l�� 
					PointSet[top++]=s.s; 
				else if(Utility.online(l,s.e)) // �߶�s����ֹ�˵����߶�l�� 
					PointSet[top++]=s.e; 
				else 
				{ 
					if(Utility.intersect(l,s)) // ���ʱ������ཻ���϶����ڽ�������false 
					return false; 
				} 
			} 
		} 

		for(i=0;i<top-1;i++) /* ð������x����С������ǰ�棻x������ͬ�ߣ�y����С������ǰ�� */ 
		{ 
			for(j=i+1;j<top;j++) 
			{ 
				if( PointSet[i].x>PointSet[j].x || Math.abs(PointSet[i].x-PointSet[j].x)<Utility.EP && PointSet[i].y>PointSet[j].y ) 
				{ 
					tmp=PointSet[i]; 
					PointSet[i]=PointSet[j]; 
					PointSet[j]=tmp; 
				} 
			} 
		} 

		for(i=0;i<top-1;i++) 
		{ 
			tmp.x=(PointSet[i].x+PointSet[i+1].x)/2; //�õ��������ڽ�����е� 
			tmp.y=(PointSet[i].y+PointSet[i+1].y)/2; 
			if(this.insidepolygon(tmp)>0) 
				return false; 
		} 
		return true; 
	} 
	

//	Polygon setSum(Polygon a){
//		Polygon c = new Polygon();
//		int ea=1,eb=1,vc=0;
//		double ang,offset=0;
////		while(a.angle[ea]<=this.angle[1] || a.angle[ea-1]>=this.angle[1]){
////			ea++;
////		}
//		while(a.angle[ea]<=this.angle[1]){
//			ea++;
//		}
//		c.vSet[0] = new POINT();
//		c.vSet[0].x = a.vSet[ea-1].x + this.vSet[0].x;
//		c.vSet[0].y = a.vSet[ea-1].y + this.vSet[0].y;
//		
//		while(eb<=this.vCount){
//			vc++;
//			ang = offset+a.angle[ea];
//			if(ang<=this.angle[eb]){
//				if(ea>=a.vCount){
////					offset=2*Utility.PI;
//					offset=360;
//					ea=1;
//				}
//				else
//					ea++;
//			}
//			if(ang>=this.angle[eb]){
//				eb++;
//			}	
//			c.vSet[vc] = new POINT();
//			c.vSet[vc].x = a.vSet[ea-1].x + this.vSet[eb-1].x;
//			c.vSet[vc].y = a.vSet[ea-1].y + this.vSet[eb-1].y;
//		}
//		c.vCount=vc+1;
//		c.vSet[c.vCount] = new POINT();
//		c.vSet[c.vCount].x = c.vSet[0].x;
//		c.vSet[c.vCount].y = c.vSet[0].y;	
//		return c;
//	}
	
//	/**
//	 * [���㵱ǰ��͹�������͹�����a�ĺ�(��ǰ��͹�������Ϊb)]
//	 * @param a 
//	 * @return	 ���ص�ǰ͹�������͹�����a�ĺ͡���͹�����c
//	 */
//	MyPolygon setSum(MyPolygon a){
//		//�������x����������ʱ�뷽�򵽵�ǰ͹����κͶ����a�ĸ���(�з���)�ļн�,�ֱ��¼��angle[]������
//		this.calcAngle();
//		a.calcAngle();
//		MyPolygon c = new MyPolygon();
//		int ea=0,eb=0,vc=0;
//		double ang,offset=0;
//		while(a.angle[ea]<this.angle[0]){   //ԭ�㷨�д˴���"<=",������ʵ�ʼ�������У�����ʼʱ�����a.angle[0]==this.angle[0]�������ּ������
//			ea++;
//		}
//		c.vSet[0] = new POINT();
//		c.vSet[0].x = a.vSet[ea].x + this.vSet[0].x;
//		c.vSet[0].y = a.vSet[ea].y + this.vSet[0].y;
//		int start = ea;
//		
//		while(eb<this.vCount){
//			vc++;
//			ang = offset+a.angle[ea];
//			if(ang<=this.angle[eb]){
//				if(ea>=(a.vCount-1)){
//					offset=2*Utility.PI;
//					ea=0;
//				}
//				else
//					ea++;
//			}
//			if(ang>=this.angle[eb]){
//				eb++;
//			}
//			if(eb==this.vCount && ea==start){
//				vc--;
//				break;
//			}			
//			c.vSet[vc] = new POINT();
//			c.vSet[vc].x = a.vSet[ea].x + this.vSet[eb].x;
//			c.vSet[vc].y = a.vSet[ea].y + this.vSet[eb].y;
//		}
//		c.vCount=vc+1;
//		c.vSet[c.vCount] = new POINT();
//		c.vSet[c.vCount].x = c.vSet[0].x;
//		c.vSet[c.vCount].y = c.vSet[0].y;
//		//����ԭʼ���㷨��Ҫ��this��a���ཻ����ԭ���,������this�����õ�ȴ��this.vSet[0].�����Ļ�,Ҫ�ƶ���ԭ��,����this��a����ȥthis.vSet[0]
//		//��������ý����,��Ҫ���ڽ���ϼ���this.vSet[0],��ƥ��this.������ֱ������ý��,�ڼ�ȥһ��this.vSet[0]
//		for(int i=0;i<=c.vCount;i++){
//			c.vSet[i].x=c.vSet[i].x-this.vSet[0].x;
//			c.vSet[i].y=c.vSet[i].y-this.vSet[0].y;
//		}
//		
//		c.format();
//		return c;
//	}
	
	/**
	 * [���㵱ǰ��͹�������͹�����a�ĺ�(��ǰ��͹�������Ϊb)]
	 * @param a 
	 * @return	 ���ص�ǰ͹�������͹�����a�ĺ͡���͹�����c
	 */
	MyPolygon setSum_new(MyPolygon a){
		//�������x����������ʱ�뷽�򵽵�ǰ͹����κͶ����a�ĸ���(�з���)�ļн�,�ֱ��¼��angle[]������
		this.calcAngle();
		a.calcAngle();
		MyPolygon c = new MyPolygon();
		int ea=0,eb=0,vc=0;
		double ang_a,ang_b,offset_a=0,offset_b =0;
		while(a.angle[ea]<this.angle[0]){   //ԭ�㷨�д˴���"<=",������ʵ�ʼ�������У�����ʼʱ�����a.angle[0]==this.angle[0]�������ּ������
			ea++;
		}
		c.vSet[0] = new POINT();
		c.vSet[0].x = a.vSet[ea].x + this.vSet[0].x;
		c.vSet[0].y = a.vSet[ea].y + this.vSet[0].y;
		int start = ea;
		
		for(;;){
			vc++;
			ang_a = offset_a+a.angle[ea];
			ang_b = offset_b+this.angle[eb];
			if(ang_a<=ang_b){
				if(ea>=(a.vCount-1)){
					offset_a=2*Utility.PI;
					ea=0;
				}
				else
					ea++;
			}
			if(ang_a>=ang_b){
				eb++;
				if(eb == this.vCount){
					offset_b=2*Utility.PI;
					eb =0;
				}
			}
			if(eb==0 && ea==start){
//				vc--;
				break;
			}			
			c.vSet[vc] = new POINT();
			c.vSet[vc].x = a.vSet[ea].x + this.vSet[eb].x;
			c.vSet[vc].y = a.vSet[ea].y + this.vSet[eb].y;
		}
		c.vCount=vc;
		c.vSet[c.vCount] = new POINT();
		c.vSet[c.vCount].x = c.vSet[0].x;
		c.vSet[c.vCount].y = c.vSet[0].y;
		//����ԭʼ���㷨��Ҫ��this��a���ཻ����ԭ���,������this�����õ�ȴ��this.vSet[0].�����Ļ�,Ҫ�ƶ���ԭ��,����this��a����ȥthis.vSet[0]
		//��������ý����,��Ҫ���ڽ���ϼ���this.vSet[0],��ƥ��this.������ֱ������ý��,�ڼ�ȥһ��this.vSet[0]
		for(int i=0;i<=c.vCount;i++){
			c.vSet[i].x=c.vSet[i].x-this.vSet[0].x;
			c.vSet[i].y=c.vSet[i].y-this.vSet[0].y;
		}		
		c.format();
		return c;
	}
	
	/**
	 * [��ǰ͹����������(Relative To)͹�����B���ϰ��ռ�]
	 * @param B
	 * @return
	 */
	MyPolygon obstacleSpaceRTB(MyPolygon B){
		MyPolygon A1 = new MyPolygon(this,B.vSet[0]);
		for(int i=0;i<A1.vCount;i++){
			A1.vSet[i].x = 2*A1.vSet[0].x-A1.vSet[i].x;
			A1.vSet[i].y = 2*A1.vSet[0].y-A1.vSet[i].y;
		}
		A1.format();//A1��׼��(ע��:A1����check��,��ΪA1����this����,this�Ѽ���ʱ͹�������,ƽ�Ʒ�ת�仯����͹�����)
		MyPolygon C = B.setSum_new(A1);
		return C;
	}
	
	/**
	 * [��ǰ͹����������ƽ̨W�ĳ��ڿռ�]
	 * @param W	ƽ̨(������,����W�����λ������ԭ��,����x��������Ĳ����ص�)
	 * @return	���ص�͹�������һ������
	 */
	MyPolygon insideSpaceRTW(Yard W){
		//�ȼ������ǰ͹���������/�ҵ��x����ֵ,���ϵ��y����ֵ(����format��͹�����,���µ�ֵ��Ϊ���õ��y����ֵ)
		double left,right,top,bottom;
		bottom=top=this.vSet[0].y;//bottom�Ѽ������
		left=right=this.vSet[0].x;
		for(int i=1;i<this.vCount;i++){
			if(this.vSet[i].x<left)
				left=this.vSet[i].x;
			if(this.vSet[i].x>right)
				right=this.vSet[i].x;
			if(this.vSet[i].y>top)
				top=this.vSet[i].y;
		}
		
		//����ƽ̨Ϊ����,�ʳ��ڿռ�ض�ҲΪ����,������㳡�ڿռ�����
		POINT []tempSpace = new POINT[4];
		for(int i=0;i<tempSpace.length;i++)
			tempSpace[i]=new POINT();
		//�������½Ƕ���
		tempSpace[0].x = this.vSet[0].x-(left-0);
		tempSpace[0].y = this.vSet[0].y-(bottom-0);
		//�������½Ƕ���
		tempSpace[1].x = this.vSet[0].x-(right-W.length);
		tempSpace[1].y = this.vSet[0].y-(bottom-0);
		//�������ϽǶ���
		tempSpace[2].x = this.vSet[0].x-(right-W.length);
		tempSpace[2].y = this.vSet[0].y-(top-W.width);
		//�������ϽǶ���
		tempSpace[3].x = this.vSet[0].x-(left-0);
		tempSpace[3].y = this.vSet[0].y-(top-W.width);
		
		MyPolygon insideSpace = new MyPolygon(4,tempSpace);
		insideSpace.format();
		return insideSpace;
	}
	
	/**
	 * ������͹�����B�Ľ���
	 * @param B
	 * @return 
	 */
	ArrayList<POINT> intersectionPoint(MyPolygon B){
		ArrayList<POINT> points = new ArrayList<POINT>();
		for(int i=0;i<this.vCount;i++){
			LINESEG la = new LINESEG(this.vSet[i],this.vSet[i+1]);
			for(int j=0;j<B.vCount;j++){
				LINESEG lb = new LINESEG(B.vSet[j],B.vSet[j+1]);
				POINT p = Utility.intersection(la,lb);
				if(p!=null)
					points.add(p);
			}
		}
		return points;
	}
	
	/**
	 * ���㵱ǰ͹����θ������е���p��������
	 * @param p
	 * @return
	 */
	double maxDisTo(POINT p){
		double dis = 0;
		double temp;
		for(int i=0;i<this.vCount;i++){
			temp = Utility.dist(this.vSet[i],p);
			if(temp>dis)
				dis=temp;
		}
		return dis;
	}
	
	/**
	 * ���㵱ǰ͹����θ����㵽����rectangle���ڽ��ߵ�ƽ������
	 * @param rectangle
	 * @return
	 */
	public double AvgDisToAdjEdge(MyPolygon rectangle) {
		double minAvgDis=Utility.dist(rectangle.vSet[0],rectangle.vSet[2]);//��ʼ��Ϊ���εĶԽ��߳���
		for(int i=0;i<rectangle.vCount;i++){
			LINESEG l = new LINESEG(rectangle.vSet[i],rectangle.vSet[i+1]);
			double dis=0;
			for(int j=0;j<this.vCount;j++){
				dis = dis+Utility.ptoldist(this.vSet[j], l);
			}
			dis = dis/this.vCount;
			if(dis < minAvgDis){
				minAvgDis = dis;
			}
		}
		return minAvgDis;
	}
	
	/**
	 * �����õ�Ϊ���ģ���ת��ǰ͹�����angle(�Ƕȣ��ǻ���)�Ƕȣ������γɵ��µ�͹�����
	 * @param angle
	 * @return
	 */
	MyPolygon rotate(int angle){
		double newAngle = ((double)angle/180)*Utility.PI;
		MyPolygon newP = new MyPolygon(this,this.vSet[0]);
		for(int i=1;i<newP.vCount;i++){
			POINT p = Utility.rotate(newP.vSet[0], newAngle, newP.vSet[i]);
			newP.vSet[i].setPoint(p);
		}
		newP.format();
		return newP;		
	}
	
	/**
	 * �ƶ���ǰ͹����Σ�ʹ�����µ�λ�õ����õ�����Ϊp
	 * @param p
	 */
	void moveTo(POINT p){
		POINT vector = new POINT();
		vector.x = this.vSet[0].x-p.x;
		vector.y = this.vSet[0].y-p.y;
		for(int i=0;i<=this.vCount;i++){
			this.vSet[i].x = this.vSet[i].x-vector.x;
			this.vSet[i].y = this.vSet[i].y-vector.y;
		}
	}
	
	/**
	 * ���㵱ǰ͹����εĶ��������а����ڶ����Q�ڲ��Ķ��� �Ͷ����P��Q�Ľ�����ɵĵ�����
	 * @param Q
	 * @return
	 */
	POINT[] thisInQ(MyPolygon Q){
		POINT[] this_Q = new POINT[20];
//		for(int i=0;i<this_Q.length;i++)
//			this_Q[i] = new POINT();
		int now =0;
		for(int i=0;i<this.vCount;i++){
			int num = 0;
			if(Q.insidepolygon(this.vSet[i])==0){
				this_Q[now]  = new POINT(this.vSet[i]);
				now++;
			}
			LINESEG l_P = new LINESEG(this.vSet[i],this.vSet[i+1]);
			for(int j=0;j<Q.vCount;j++){
				LINESEG l_Q = new LINESEG(Q.vSet[j],Q.vSet[j+1]);
				POINT p = Utility.intersection(l_P,l_Q);
				if(p!=null){
					int flag = 0;
					for(int h=0;h<now;h++){
						if(Utility.equal_point(this_Q[h], p)){
							flag = 1;//��ʾ�˵��Ѿ��������
							break;
						}
					}
					if(flag==0){
						this_Q[now]  = new POINT(p);
						now++;
						num++;
					}
				}
			}
			if(num>1){//��ʱ˵���߶�l_P��Q����������,����ռ���������㵽this.vSet[i]�ľ����С����
				if(Utility.dist(this.vSet[i],this_Q[now-1])<Utility.dist(this.vSet[i], this_Q[now-2])){
					POINT temp = new POINT(this_Q[now-1]);
					this_Q[now-1].setPoint(this_Q[now-2]);
					this_Q[now-2].setPoint(temp);
				}
			}
		}
		return this_Q;
	}
	
	/**
	 * ���㵱ǰ����ε� Orthogonal  Circumscribed  Rectangle(������Χ����)
	 * @return
	 */
	MyPolygon calcOCR(){
		//�ȼ������ǰ���������/�ҵ��x����ֵ,���ϵ��y����ֵ(����format��͹�����,���µ�ֵ��Ϊ���õ��y����ֵ)
		double left,right,top,bottom;
		bottom=top=this.vSet[0].y;//bottom�Ѽ������
		left=right=this.vSet[0].x;
		for(int i=1;i<this.vCount;i++){
			if(this.vSet[i].x<left)
				left=this.vSet[i].x;
			if(this.vSet[i].x>right)
				right=this.vSet[i].x;
			if(this.vSet[i].y>top)
				top=this.vSet[i].y;
		}
		
		POINT []tempSpace = new POINT[4];
		for(int i=0;i<tempSpace.length;i++)
			tempSpace[i]=new POINT();
		//�������½Ƕ���
		tempSpace[0].x = left;
		tempSpace[0].y = bottom;
		//�������½Ƕ���
		tempSpace[1].x = right;
		tempSpace[1].y = bottom;
		//�������ϽǶ���
		tempSpace[2].x = right;
		tempSpace[2].y = top;
		//�������ϽǶ���
		tempSpace[3].x = left;
		tempSpace[3].y = top;
		
		MyPolygon OCR = new MyPolygon(4,tempSpace);
		OCR.format();
		return OCR;
	}

	//��͹����ε����� 
	POINT gravityCenter() 
	{  
		double x,y,s,x0,y0,cs,k; 
		x=0;y=0;s=0; 
		for(int i=1;i<this.vCount-1;i++) 
		{ 
			x0=(this.vSet[0].x+this.vSet[i].x+this.vSet[i+1].x)/3; 
			y0=(this.vSet[0].y+this.vSet[i].y+this.vSet[i+1].y)/3; //��ǰ�����ε����� 
			cs=Utility.multiply(this.vSet[i],this.vSet[i+1],this.vSet[0])/2; 
			//�������������ֱ�����øù�ʽ��� 
			if(Math.abs(s)<1e-20) { 
				x=x0;y=y0;s+=cs;
				continue; 
			} 
			k=cs/s; //��������� 
			x=(x+k*x0)/(1+k); 
			y=(y+k*y0)/(1+k); 
			s += cs; 
		} 
		return new POINT(x,y);
	}

	public boolean isEqual(MyPolygon b) {
		this.format();
		b.format();
		if(this.vCount == b.vCount){
			for(int i=0;i<this.vCount;i++){
				if(!this.vSet[i].equ(b.vSet[i]))
					return false;
			}
			return true;
		}
		return false;
	} 
	
}
