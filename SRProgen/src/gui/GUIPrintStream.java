package gui;

import java.io.OutputStream;
import java.io.PrintStream;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;


public class GUIPrintStream extends PrintStream{
   
    private Text text;
   
    public GUIPrintStream(OutputStream out, Text text){
        super(out);
        this.text = text;
    }
   
    /** *//**
     * ��дwrite()�������������Ϣ��䵽GUI���
     * @param buf
     * @param off
     * @param len
     */
    @Override
//    public void write(byte[] buf, int off, int len) {
//        final String message = new String(buf, off, len);
//        sb.append(message);
//        component.setText(sb.toString());
////        component.setCaretPosition(component.getText().length()); ���ù��λ��
//    }
    
//    /**
//    * �������ؽ�,���еĴ�ӡ������Ҫ���õķ���
//    */
//        public void write(byte[] buf, int off, int len) {
//            final String message = new String(buf, off, len);
//           
//            /**//* SWT�ǽ����̷߳�������ķ�ʽ */
//           Display.getDefault().syncExec(new Thread(){
//               public void run(){
//                    /**//* ���������Ϣ��ӵ������ */
//                   text.append(message);
//                }
//           });
//        }
    
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
		if (text != null && !text.isDisposed()) {
			/**//* SWT�ǽ����̷߳�������ķ�ʽ */
			Display.getDefault().syncExec(new Thread() {
				public void run() {
					/**//* ���������Ϣ��ӵ������ */
					text.append(message);
				}
			});
		} else {
			super.write(buf, off, len);
		}
	}
  
}