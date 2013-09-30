package gen;

import java.io.*;

/**
 * �������������������е�Ҫ��,����������/���������ֵ/���ļ������õ�����ֵ(��Ӧ��SampleInfo)
 * @author xung
 *
 */
public class Demand {
	BaseStruct base;//���ļ����ݶ�����������
	String bFilePath; //���ļ��ļ�·��(����·��/�ļ���/��չ��)
	String bFileName;//���ļ��ļ���(������׺)
	String dirStr; //���ļ���Ŀ¼
	String extStr;//���ļ���չ��(����".")
	String errFilePath;//���ɹ����м�¼������ļ�
	int nrOfEx; //Ҫ���ɵ������ĸ���
	int inval; //���������ֵ
	
	Demand(){
		this.base = new BaseStruct();
		this.nrOfEx = 10;
		this.inval = 0;
		this.bFilePath = "no";
	}

	public boolean getBaseData(String path){
	    try {
	    	File f = new File(path);
	    	path= f.getAbsolutePath();
	    	FileReader fr = new FileReader(path);
	  
	    	readFileToChar(fr,':'); base.nrOfPro=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minJob=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxJob=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxRel=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.dueDateFac=readAParameter(fr);
	    	readFileToChar(fr,':'); base.minMode=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxMode=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minDur=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxDur=(int)readAParameter(fr);
	    	
	    	readFileToChar(fr,':'); base.minOutSour=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxOutSour=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxOut=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minInSink=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxInSink=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxIn=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.compl=readAParameter(fr);
	    	
	    	readFileToChar(fr,':'); base.minRen=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxRen=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minRReq=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxRReq=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minRRU=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxRRU=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.RRF=readAParameter(fr);
	    	readFileToChar(fr,':'); base.RRS=readAParameter(fr);
	    	readFileToChar(fr,':'); base.nrOfRFunc=(int)readAParameter(fr);
	    	base.RFuncProb = new float[base.nrOfRFunc];
	    	for(int i=0;i<base.nrOfRFunc;i++){
	    		readFileToChar(fr,':');
	    		base.RFuncProb[i]=readAParameter(fr);
	    	}
	    	
	    	readFileToChar(fr,':'); base.minNon=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxNon=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minNReq=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxNReq=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minNRU=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxNRU=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.NRF=readAParameter(fr);
	    	readFileToChar(fr,':'); base.NRS=readAParameter(fr);
	    	readFileToChar(fr,':'); base.nrOfNFunc=(int)readAParameter(fr);
	    	base.NFuncProb = new float[base.nrOfNFunc];
	    	for(int i=0;i<base.nrOfNFunc;i++){
	    		readFileToChar(fr,':');
	    		base.NFuncProb[i]=readAParameter(fr);
	    	}
	    	
	    	readFileToChar(fr,':'); base.minDou=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxDou=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minDReq=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxDReq=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.minDRU=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxDRU=(int)readAParameter(fr);
	    	readFileToChar(fr,':'); base.DRF=readAParameter(fr);
	    	readFileToChar(fr,':'); base.DRST=readAParameter(fr);
	    	readFileToChar(fr,':'); base.DRSP=readAParameter(fr);
	    	readFileToChar(fr,':'); base.nrOfDFunc=(int)readAParameter(fr);
	    	base.DFuncProb = new float[base.nrOfDFunc];
	    	for(int i=0;i<base.nrOfDFunc;i++){
	    		readFileToChar(fr,':');
	    		base.DFuncProb[i]=readAParameter(fr);
	    	}
	    	
	    	if(Progen.includeSR){		//��������ռ���ԴԼ��
	    		//��������ز�������
		    	readFileToChar(fr,':'); 
		    	base.minNofTaskG=(int)readAParameter(fr);
		    	readFileToChar(fr,':'); base.maxNofTaskG=(int)readAParameter(fr);
		    	readFileToChar(fr,':'); base.minNofJobTask=(int)readAParameter(fr);
		    	readFileToChar(fr,':'); base.maxNofJobTask=(int)readAParameter(fr);
		    	readFileToChar(fr,':'); base.minNofQJob=(int)readAParameter(fr);
		    	readFileToChar(fr,':'); base.maxNofQJob=(int)readAParameter(fr);
		    	readFileToChar(fr,':'); base.minNofMJob=(int)readAParameter(fr);
		    	readFileToChar(fr,':'); base.maxNofMJob=(int)readAParameter(fr);
		    	
		    	//�ռ���Դ��ز�������
		    	readFileToChar(fr,':'); base.nrOfSR=(int)readAParameter(fr);
		    	base.SRTypeAll = new SRType[base.nrOfSR];
		    	for(int i=0;i<base.nrOfSR;i++){
		    		base.SRTypeAll[i]=new SRType();
		    		base.SRTypeAll[i].name=readAStringParameter(fr,'(');
		    		base.SRTypeAll[i].dimension=Integer.parseInt(readAStringParameter(fr,','));
		    		String temp=readAStringParameter(fr,',');
		    		if(temp.equals("d")||temp.equals("D"))
		    			base.SRTypeAll[i].dividable=true;
		    		else
		    			base.SRTypeAll[i].dividable=false;
		    		temp=readAStringParameter(fr,')');
		    		if(temp.equals("o")||temp.equals("O"))
		    			base.SRTypeAll[i].orientation=true;
		    		else
		    			base.SRTypeAll[i].orientation=false;
		    		readFileToChar(fr,'\n');
		    	}
		    	for(int i=0;i<base.nrOfSR;i++){
		    		readFileToChar(fr,':'); base.SRTypeAll[i].minKind=(int)readAParameter(fr);
		    		readFileToChar(fr,':'); base.SRTypeAll[i].maxKind=(int)readAParameter(fr);
		    		readFileToChar(fr,'(');
		    		if(base.SRTypeAll[i].dimension==1){
		    			base.SRTypeAll[i].minDemand.x=Integer.parseInt(readAStringParameter(fr,')'));
		    			readFileToChar(fr,'(');
		    			base.SRTypeAll[i].maxDemand.x=Integer.parseInt(readAStringParameter(fr,')'));
		    		}
		    		else if(base.SRTypeAll[i].dimension==2){
		    			base.SRTypeAll[i].minDemand.x=Integer.parseInt(readAStringParameter(fr,','));
		    			base.SRTypeAll[i].minDemand.y=Integer.parseInt(readAStringParameter(fr,')'));
		    			readFileToChar(fr,'(');
		    			base.SRTypeAll[i].maxDemand.x=Integer.parseInt(readAStringParameter(fr,','));
		    			base.SRTypeAll[i].maxDemand.y=Integer.parseInt(readAStringParameter(fr,')'));
		    		}
		    		else if(base.SRTypeAll[i].dimension==3){
		    			base.SRTypeAll[i].minDemand.x=Integer.parseInt(readAStringParameter(fr,','));
		    			base.SRTypeAll[i].minDemand.y=Integer.parseInt(readAStringParameter(fr,','));
		    			base.SRTypeAll[i].minDemand.z=Integer.parseInt(readAStringParameter(fr,')'));
		    			readFileToChar(fr,'(');
		    			base.SRTypeAll[i].maxDemand.x=Integer.parseInt(readAStringParameter(fr,','));
		    			base.SRTypeAll[i].maxDemand.y=Integer.parseInt(readAStringParameter(fr,','));
		    			base.SRTypeAll[i].maxDemand.z=Integer.parseInt(readAStringParameter(fr,')'));
		    		}
		    		readFileToChar(fr,':'); base.SRTypeAll[i].minR=(int)readAParameter(fr);
		    		readFileToChar(fr,':'); base.SRTypeAll[i].maxR=(int)readAParameter(fr);
		    		readFileToChar(fr,':'); base.SRTypeAll[i].SRF=readAParameter(fr);
		    		readFileToChar(fr,':'); base.SRTypeAll[i].SRS=readAParameter(fr);
		    	}
	    	}
	    	
	    	
	    	readFileToChar(fr,':'); base.netTol=readAParameter(fr);
	    	readFileToChar(fr,':'); base.reqTol=readAParameter(fr);
	    	readFileToChar(fr,':'); base.maxTrials=(int)readAParameter(fr);

	        fr.close();
	        
	        //�ļ��������Ѷ���,�����ȡ�ļ�����/��׺����Ϣ
	        this.bFilePath = path;
	        if(path.contains("\\")){
	        	this.bFileName = path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf(".")); 
	        	this.dirStr = path.substring(0,path.lastIndexOf("\\"));
	        }
	        else {
	        	this.bFileName = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf(".")); 
	        	this.dirStr = path.substring(0,path.lastIndexOf("/"));//���������"/"
	        }
	        this.extStr = path.substring(path.lastIndexOf("."));//�������"."	        
	        this.errFilePath = this.dirStr+"/"+this.bFileName+"-log.txt";	  
	        
	        return true;
	        
	      } catch (FileNotFoundException e) {
	        System.out.println("�Ҳ���ָ���ļ�");
	        return false;
	      } catch (Exception e) {//��Ϊ�ļ���ȡ������,������IO�쳣,�����������ָ�ʽת�����쳣,�����ڴ˾Ͳ��������쳣(�ļ�δ�ҵ����쳣���ڴ˱�����)
	        System.out.println("�ļ���ȡ����");
	        return false;
	      }
	}
	
	/**
	 * ���ļ���ǰλ�ö���mark��ǵķ��Ŵ�
	 * @param fr
	 * @param mark
	 * @throws IOException
	 */
	static void readFileToChar(FileReader fr,char mark) {
		try{
			int ch = fr.read();
			while (ch != mark){
				if(ch == -1){
					return;
				}
				ch =  fr.read();
				continue;	
			}
				
		}catch (Exception e) {//��Ϊ�ļ���ȡ������,������IO�쳣,�����������ָ�ʽת�����쳣,�����ڴ˾Ͳ��������쳣(�ļ�δ�ҵ����쳣���ڴ˱�����)
	        System.out.println("�ļ���ȡ����");
		}
	}
	
/**����һ������,�Ը�������ʽ����(����ʱ�Ѷ�����'\r')
 * @param fr
 * @return 
 * @throws IOException
 */
	static float readAParameter(FileReader fr) throws Exception{
    	char c;
    	char[] buff = new char[20];
    	String s = null;
    	int flag =0;//flagΪ0��ʾ��û��������������;Ϊ1��ʾ���ڶ����ݵĹ�����;
    	int i = 0;
		while (true) {
			c = (char) fr.read();
        	if((c!=' ')&&(c!='\t')&&flag==0){//�ҵ������ݿ�ʼ��λ��
        		flag = 1;
        		buff[i++]=c;
        		continue;
        	}
        	if(((c==' ')||(c=='\t')||((char)c=='\r'))&&flag==1){ //�ڶ����ݹ����������ո���л�س�,��ʾ���ݶ�ȡ����(windowsϵͳ��\r\n,Unix��\n,macϵͳ��\r��ʾһ�н���)
//        		flag = 2;
        		s = String.valueOf(buff,0,i);
//        		i = 0;
        		if(c!='\r')
        			readFileToChar(fr,'\r');//��֤����'\n'����
        		return Float.parseFloat(s);
        	}
        	if((c!=' ')&&flag==1){//�����ݶ�ȡ�����������ǿո��,�ͼ��������������в�������(��if������ǰһ��if����λ��)
        		buff[i++]=c;
        		continue;
        	}      	
		}
	}	

	/**
	 * �˺����ӵ�ǰ�α꿪ʼ������Ч�ַ���������(����ʱ�Ѷ������ַ�end)
	 * @param fr
	 * @return
	 * @throws Exception
	 */
	static String readAStringParameter(FileReader fr,char end) throws Exception{
    	char c;
    	char[] buff = new char[20];
    	String s = null;
    	int flag =0;//flagΪ0��ʾ��û��������������;Ϊ1��ʾ���ڶ����ݵĹ�����;
    	int i = 0;
		while (true) {
			c = (char) fr.read();
        	if((c!=' ')&&(c!='\t')&&(c!='\n')&&flag==0){//�ҵ������ݿ�ʼ��λ��
        		flag = 1;
        		buff[i++]=c;
        		continue;
        	}
        	if(((c==' ')||(c=='\t')||((char)c==end))&&flag==1){ 
        		s = String.valueOf(buff,0,i);
        		if((char)c!=end)
        			readFileToChar(fr,end);//��֤�˺����������ʱ,�Ѿ�������end
        		return s;
        	}
        	if((c!=' ')&&flag==1){
        		buff[i++]=c;
        		continue;
        	}      	
		}
	}
}

class BaseStruct{
	int nrOfPro; 
	int minJob;
	int maxJob;
	int maxRel;
	float dueDateFac;
	int minMode;
	int maxMode;
	int minDur;
	int maxDur;
	
	int minOutSour;
	int maxOutSour;
	int maxOut;
	int minInSink;
	int maxInSink;
	int maxIn;
	float compl;
	
	int minRen;
	int maxRen;
	int minRReq;
	int maxRReq;
	int minRRU;
	int maxRRU;
	float RRF;
	float RRS;
	int nrOfRFunc;
	float[] RFuncProb;
	
	int minNon;
	int maxNon;
	int minNReq;
	int maxNReq;
	int minNRU;
	int maxNRU;
	float NRF;
	float NRS;
	int nrOfNFunc;
	float[] NFuncProb;
	
	int minDou;
	int maxDou;
	int minDReq;
	int maxDReq;
	int minDRU;
	int maxDRU;
	float DRF;
	float DRST;
	float DRSP;
	int nrOfDFunc;
	float[] DFuncProb;
	
	//��������ز���
	int minNofTaskG;//��С��������
	int maxNofTaskG;//�����������
	
	//ÿ����������ز���
	int minNofJobTask;//ÿ������������С�(�������)��
	int maxNofJobTask;//ÿ�������������(�������)��
	int minNofQJob;//ÿ������������С������������
	int maxNofQJob;//ÿ�������������������������
	int minNofMJob;//ÿ������������С�ƶ���������
	int maxNofMJob;//ÿ��������������ƶ���������
	
	//�ռ���Դ��ز���
	int nrOfSR;//�ռ���Դ������
	SRType[] SRTypeAll;
	
	
	int maxTrials;
	float netTol;
	float reqTol;
		
}
