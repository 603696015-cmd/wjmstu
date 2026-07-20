package com.sopia.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.StationDaoImpl;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.dao.impl.StudyCourseDaoImpl;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyRoom;
 

/**
 * �û����ŵ��빤��
 * @author Administrator
 *
 */
public class UserExcelUtil{
	private static final Log logger = LogFactory.getLog(UserExcelUtil.class);
	private static StringBuffer impTip;//������ʾ  
	//
//	public static void writeUser(File source) throws ElException {
//		try {
//			InputStream is = new FileInputStream(source);
//			jxl.Workbook rwb = Workbook.getWorkbook(is);
//			Sheet ss[] = rwb.getSheets();
//			if (null != ss && ss.length > 0) {
//				Sheet ss1 = ss[0];
//				for (int i = 1; i < ss1.getRows(); i++) {
//
//					ELUser eu = new ELUser();
//					String username = ss1.getCell(0, i).getContents().trim();
//					eu.setUsername(username);
//					// �û���
//					String password = ss1.getCell(1, i).getContents().trim();
//					eu
//							.setPassword(MD5.crypt(password == null
//									|| "".equals(password.trim()) ? username
//									: password));
//					// �� ��
//					eu.setXuhao(ss1.getCell(2, i).getContents().trim());
//					// ���
//					eu.setRealname(ss1.getCell(3, i).getContents().trim());
//					// �� ��
//					eu.setSex(ss1.getCell(4, i).getContents().trim());
//					// �Ա�
//					eu.setDishi(ss1.getCell(5, i).getContents().trim());
//					// ����
//					eu.setShenfenzheng(ss1.getCell(6, i).getContents().trim()
//							.equals("") ? username : ss1.getCell(6, i)
//							.getContents().trim());
//					// ���֤��
////					eu.setShengri(getDate(ss1.getCell(7, i).getContents().trim(),"dd/MM/yyyy"));
//					eu.setShengri(getShengriBySfz(eu.getShenfenzheng()));
//					// ��������
//					eu.setZhiji(ss1.getCell(8, i).getContents().trim());
//					// ְ��
//					eu.setZhiwu(ss1.getCell(9, i).getContents().trim());
//					// ְ��
//					eu.setJingzhong(ss1.getCell(10, i).getContents().trim());
//					// ����
//					eu.setGangwei(ss1.getCell(11, i).getContents().trim());
//					// ��λ
//					eu.setRole(new ElRole(4));
//					String depname = ss1.getCell(12, i).getContents().trim();
//					// * ��λ��
//					// eu.setEmail("");
//					int depparent = 1;// getDep(suozaidanwei, 1);
//					depparent = new DepartmentDaoImpl().getDepByBH(depname).getId();
//					if (depparent == 0)
//						depparent = 1;
//					// ����
//					// int depid = getDep(suozaibumen, depparent);
//
//					// new UserDaoImpl().insert(eu);
//					eu.setDepartment(new Department(depparent));
//					eu.setValid(true);
//					UserDaoImpl eud = new UserDaoImpl();
//					if (!eud.checkUsername(username)) {
//						eud.userAdd(eu);
//					}
//					// }
//
//				}
//			}
//		} catch (Exception e) {
//			logger.error("�����˺�ʧ��", e);
//			throw new ElException("�����˺�ʧ��");
//		}
//	}
	/**
	 * 
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 */
	
	private static int checkImport(Sheet ss1,int i)throws ElException{  
			UserDaoImpl userDao = new UserDaoImpl();
			boolean name = true; //�û���
			boolean pass = true; //����
			boolean xh = true;	 //���
			boolean rn = true;	 //��ʵ����
			boolean xb = true;	 //�Ա�
			boolean ds = true;	 //����
			boolean jz = true;	 //���� 
			boolean gw = true;	 //��λ
			boolean zj = true;	 //ְ��
			boolean zw = true;	 //ְ��
			boolean sfz = true;	 //���֤
			boolean dep = true;	 //����
			boolean phone = true;	 //����
			int is = 0;	 //����ֵ
			String username = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У��û���
			//username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
			if(username == null || username.equals("")){impTip.append("<br/> �û���Ϊ��! ");name = false;	}else
			if(username.length() < 6){impTip.append("<br/> �û���������6λ! ");name = false;}else 
			if(new UserDaoImpl().checkUsername(username)){impTip.append("'"+username+"'���û����Ѵ���!");name = false;}
			
//			String password = ss1.getCell(1, i).getContents().trim();
//			if(password == null || password.equals("")){impTip.append("<br/> ���벻��Ϊ��! "); pass = false;} else
//			if(password.length() < 6){impTip.append("<br/> ���벻������6λ! ");pass = false;}
//			 �� ��
			
//			String xuhao = ss1.getCell(2, i).getContents().trim();
//			if(xuhao == null||xuhao.equals("")){impTip.append("<br/> ��Ų���Ϊ��! "); xh = false;}
			// ���
			
			String Realname = ss1.getCell(3, i).getContents().trim();
			if(Realname == null ||Realname.equals("")){impTip.append("<br/> ��ʵ������Ϊ��! "); rn = false;}
			// �� ��
			
			String sex = ss1.getCell(4, i).getContents().trim();
			if(sex == null||sex.equals("")){impTip.append("<br/> �Ա���Ϊ��! ");xb = false;}else{
				if(!sex.equals("��") && !sex.equals("Ů")){impTip.append("<br/> �Ա�:'"+sex+"' ����"); xb = false;}
			}
			// �Ա�
			
			String dishi = ss1.getCell(5, i).getContents().trim();
			if(dishi == null||dishi.equals("")){impTip.append("<br/> ���в���Ϊ��! "); ds = false;}
			else if(!userDao.checkBase(dishi, 5)){
				impTip.append("<br/> '"+dishi+"' �õ�����ϵͳ�Ļ���ݿⲻ����! ");
				ds = false;
			}
			// ����
			
			
			//���û��������֤�����������м�飨�Ǻǣ����������Ͼ���ȫ����ˣ���Ԫ�ף�
			if ("".equals(CheckCard.IDCardValidate(username))) {//����û�������Ч���֤
				boolean isExistUserName = false;
			
				
				//����û������֤������15λ���ж���ݿ����Ƿ���ڸ���15λ��18λ�����֤����		
				if (username.length() == 15) {
					isExistUserName =userDao.checkUsername(username)?true:userDao.checkUsername(CheckCard.fixPersonIDCode(username));
				} else {//����û������֤������18λ���ж���ݿ����Ƿ���ڸ���15λ��18λ�����֤����
					isExistUserName =userDao.checkUsername(username)?true:userDao.checkUsername(CheckCard.fixPersonIDCode15(username));
			    } 
				if (isExistUserName) {
					impTip.append("<br/>���û����Ѵ���! ");
					sfz = false ;
	 
				} 
			}
			
			
			//������֤���ܺ�ϵͳ�����е���ͬ
			String shenfenzheng = ss1.getCell(6, i).getContents().trim();
			
			boolean isExist15 = false;
			boolean isExist18 = false;
			
			//System.out.println("Ҫ�����û������֤���룺"+shenfenzheng+"\t ���֤���볤�ȣ�"+shenfenzheng.length());
			
			//����û������֤������15λ���ж���ݿ����Ƿ���ڸ���15λ��18λ�����֤����		
			if (shenfenzheng.length() == 15) {
				isExist15 =userDao.checkUserShenfenzheng(shenfenzheng,0)?true:userDao.checkUserShenfenzheng(CheckCard.fixPersonIDCode(shenfenzheng),0);
			} else {//����û������֤������18λ���ж���ݿ����Ƿ���ڸ���15λ��18λ�����֤����
				isExist18 = userDao.checkUserShenfenzheng(shenfenzheng,0)?true:userDao.checkUserShenfenzheng(CheckCard.fixPersonIDCode15(shenfenzheng),0);
		    }
			
			//System.out.println("isExist15��"+isExist15+"\t isExist18:"+isExist18);
			
			
			
			if(shenfenzheng == null||shenfenzheng.equals("")){impTip.append("<br/> ���֤����Ϊ��! ");sfz = false;}else
			if(!CheckCard.IDCardValidate(shenfenzheng.toLowerCase()).equals("")){
					impTip.append("<br/> '"+shenfenzheng+"'"+CheckCard.IDCardValidate(shenfenzheng));
					sfz = false ;
			}
			//if(userDao.checkUserShenfenzheng(shenfenzheng,0)){
			if(isExist15 || isExist18){
				impTip.append("<br/> ���֤�Ѿ���������ע���! ");
				sfz = false ;
			}
//			else if(userDao.checkCard(shenfenzheng)){
//				impTip.append("<br/> '"+shenfenzheng+"' �����֤�Ѵ���");
//				sfz = false ;
//			}
			
			// ���֤�� 
			
			String zhiji = ss1.getCell(7, i).getContents().trim();
			if(zhiji==null||zhiji.equals("")){impTip.append("<br/> ְ������Ϊ��! "); zj = false;}else 
			if(!userDao.checkBase(zhiji, 3)){
				impTip.append("<br/> '"+zhiji+"' ��ְ����ϵͳ�Ļ���ݿⲻ����! ");
				zj = false;
			}
			// ְ��
			
			String zhiwu = ss1.getCell(8, i).getContents().trim();
			if(zhiwu == null || zhiwu.equals("")){impTip.append("<br/>  ְ����Ϊ��! "); zw = false;}else
			if(!userDao.checkBase(zhiwu, 2)){
				impTip.append("<br/> '"+zhiwu+"' ��ְ����ϵͳ�Ļ���ݿⲻ����! ");
				zw = false;
			}
			// ְ��
			
			String jingzhong = ss1.getCell(9, i).getContents().trim();
			if(jingzhong == null || jingzhong.equals("")){impTip.append("<br/>  ���ֲ���Ϊ��! "); jz = false;}else
			if(!userDao.checkBase(jingzhong, 1)){
				impTip.append("<br/>'"+jingzhong+"' �þ�����ϵͳ�Ļ���ݿⲻ����! ");
				jz = false;
			}
			// ����
			
//			String gangwei = ss1.getCell(10, i).getContents().trim();
//			if(gangwei == null || gangwei.equals("")){impTip.append("<br/>  ��λ����Ϊ��! "); gw = false;}else
//			if(!userDao.checkBase(gangwei, 4)){
//				impTip.append("<br/>'"+gangwei+"' �ø�λ��ϵͳ����ݿⲻ����! ");
//				gw = false;
//			}
			// ��λ  
			
			String depname = ss1.getCell(10, i).getContents().trim(); 
			if(depname == null || depname.equals("")){impTip.append("<br/>���ű�Ų���Ϊ��! "); dep = false;}  
//			if(name&&pass&&xh&&rn&&xb&&ds&&jz&&gw&&zj&&zw&&sfz){
//				is = 1;//�ɹ�
//			}else
//			if(!name&&!pass&&!xh&&!rn&&!xb&&!ds&&!jz&&!gw&&!zj&&!zw&&!sfz){//ȫ��Ϊfalse֤���������
//				is = 2;//�������
//			}else{
//				is = 0;//ʧ��
//			}
			if(name&&pass&&xh&&rn&&xb&&ds&&jz&&zj&&zw&&sfz){
				is = 1;//�ɹ�
			}else
			if(!name&&!pass&&!xh&&!rn&&!xb&&!ds&&!jz&&!zj&&!zw&&!sfz){//ȫ��Ϊfalse֤���������
				is = 2;//�������
			}else{
				is = 0;//ʧ��
			}
		return is;
	}
	
	/**
	 * ���¿γ�ѧϰ��¼������
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 * @throws ParseException 
	 */
	
	private static int checkImportCourse(Sheet ss1,int i,int courseid,int elclassid)throws ElException{  
			UserDaoImpl userDao = new UserDaoImpl();
			boolean realname = true; //����
			boolean shichang = true;//ѧϰʱ��
			boolean jindu = true;//ѧϰ���
			boolean xuefen = true;//ѧ��
			
			
			int is = 0;	 //����ֵ
			String name = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У�����
			//username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
			if(name == null || name.equals("")){impTip.append("<br/> ����Ϊ��! ");realname = false;	}
			if(!userDao.checkRealname(name)){
				impTip.append("<br/> ���������!");
				realname = false;
			}else{
				if(!userDao.checkStudyCourse(name,courseid,elclassid)){  //�������γ�id����ѵ��id��ѯ���û��Ƿ��Ѿ�������
					impTip.append("<br/> ���û�δ������ÿγ�!");
					realname = false;
				}
			}
			
			
			String sc = ss1.getCell(1,i).getContents().trim();
			if(sc == null||sc.equals("")){
				impTip.append("<br/> ѧϰʱ��Ϊ��!");
				shichang = false;
			}else{
				try {
					Integer.parseInt(sc);
				} catch (Exception e) {
					impTip.append("'"+sc+"'��ʽ����ȷ!");
					shichang = false;
					e.printStackTrace();
				}
			}
			
			String jd = ss1.getCell(2,i).getContents().trim();
			if(jd==null || jd.equals("")){
				impTip.append("<br/>ѧϰ���Ϊ��!");
				jindu = false;
			}else{
				try {
					Float.valueOf(jd);
				} catch (Exception e) {
					impTip.append("'"+jd+"'��ʽ����ȷ!");
					jindu = false;
					e.printStackTrace();
				}
			}
			
			String xf = ss1.getCell(3, i).getContents().trim(); 
			if(xf==null||xf.equals("")){
				impTip.append("<br/>ѧ�ֽ��Ϊ��!");
				xuefen = false;
			}else{
				try{
					Float.valueOf(xf);
				}catch(Exception e){
					impTip.append("'"+xf+"'��ʽ����ȷ!");
					xuefen = false;
					e.printStackTrace();
				}
			}
			if(realname&&shichang&&jindu&&xuefen){
				is = 1;//�ɹ�
			}else
			if(!realname&&!shichang&&!jindu&&!xuefen){//ȫ��Ϊfalse֤���������
				is = 2;//�������
			}else{
				is = 0;//ʧ��
			}
		return is;
	}
	
	/**
	 * ���¿����ɼ�������
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 * @throws ParseException 
	 */
	
	private static int checkImportExamRoom(Sheet ss1,int i,int courseid,int elclassid,int eroomid)throws ElException{  
			UserDaoImpl userDao = new UserDaoImpl();
			boolean realname = true; //����
			boolean defen = true;//�÷�
			boolean tongguo = true;//�Ƿ�ͨ��
			
			
			int is = 0;	 //����ֵ
			String name = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У�����
			if(name == null || name.equals("")){impTip.append("<br/> ����Ϊ��! ");realname = false;	}
			if(!userDao.checkRealname(name)){
				impTip.append("<br/> ���������!");
				realname = false;
			}else{
				if(!userDao.checkStudyClass(name,elclassid)){  //���������ѵ��id��ѯ���û��Ƿ��Ѿ������䵽����ѵ��
					impTip.append("<br/> ���û�δ�����䵽����ѵ��!");
					realname = false;
				}
			}
			
			
			
			String jd = ss1.getCell(1,i).getContents().trim();
			if(jd==null || jd.equals("")){
				impTip.append("<br/>�÷�Ϊ��!");
				defen = false;
			}else{
				try {
					Float.valueOf(jd);
				} catch (Exception e) {
					impTip.append("'"+jd+"'��ʽ����ȷ!");
					defen = false;
					e.printStackTrace();
				}
			}
			
			String xf = ss1.getCell(2, i).getContents().trim(); 
			if(xf==null||xf.equals("")){
				impTip.append("<br/>�Ƿ�ͨ��Ϊ��!");
				tongguo = false;
			}else if(xf.equals("��")||xf.equals("��")){
				
			}
			else{
				impTip.append("'"+xf+"'��ʽ����ȷ!");
				tongguo = false;
			}
			if(realname&&defen&&tongguo){
				is = 1;//�ɹ�
			}else
			if(!realname&&!defen&&!tongguo){//ȫ��Ϊfalse֤���������
				is = 2;//�������
			}else{
				is = 0;//ʧ��
			}
		return is;
	}
	/**
	 * ���¿γ�ѧϰ��¼����
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 * @throws ParseException 
	 */
	public static String writeCourse(File source,int courseid,int elclassid) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		StudyCourseDaoImpl studyCourseDao = new StudyCourseDaoImpl();
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					ck= checkImportCourse(ss1, i,courseid,elclassid);
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					MyCourse mycourse = new MyCourse();
					String realname = ss1.getCell(0, i).getContents().trim();
					ELUser user = userDao.getUserByRealname(realname);
					if(user!=null){
						mycourse.setUser(user);
						mycourse.setCourse(new Course(courseid));
						mycourse.setClassId(elclassid);
						String shichang = ss1.getCell(1,i).getContents().trim();//ѧϰʱ��
						mycourse.setPasstime(Integer.parseInt(shichang));
						String jindu = ss1.getCell(2,i).getContents().trim();//ѧϰ���
						mycourse.setProcess(Float.valueOf(jindu));
						String xuefen = ss1.getCell(3,i).getContents().trim();//ѧ��
						mycourse.setMyCredit(Float.valueOf(xuefen));
						
						studyCourseDao.updateStudyCourse(mycourse);
					}
				} 
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
	
	/**
	 * ���¿γ̿����ɼ���¼����
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 * @throws ParseException 
	 */
	public static String writeExamRoomScore(File source,int courseid,int elclassid,int eroomid) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		EroomDao eroomdao = new EroomDaoImpl();
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					ck= checkImportExamRoom(ss1, i,courseid,elclassid,eroomid);
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					MyRoom myroom = new MyRoom();
					String realname = ss1.getCell(0, i).getContents().trim();
					ELUser user = userDao.getUserByRealname(realname);
					if(user!=null){
						myroom.setTester(user);
						myroom.setExamroom(new ExamRoom(eroomid));
						myroom.getExamroom().setClassid(elclassid);
						String defen = ss1.getCell(1,i).getContents().trim();//�÷�
						myroom.setMyScore(Float.valueOf(defen));
						String tongguo = ss1.getCell(2,i).getContents().trim();//�Ƿ�ͨ��
						if(tongguo.equals("��")){
							myroom.setIspassed(1);
						}else{
							myroom.setIspassed(2);
						}
						myroom.setStatus(2);//����״̬��0.ȱ�� 1.δ���� 2.������ 3.������ 4.������ -1.��Ҫ��˵�״̬����˺�״̬Ϊ0
						if(eroomdao.checkusereroom(eroomid, user.getId(), elclassid)){
							eroomdao.updateStudyroom(myroom);
						}else{
							eroomdao.addStudyRoom(myroom);
						}
						
					}
				} 
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
	
	/**
	 * ��ҵ�û�����
	 */
	private static int checkCompanyImport(Sheet ss1,int i)throws ElException{  
		UserDaoImpl userDao = new UserDaoImpl();
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		boolean name = true; //�û���
		boolean pass = true; //����
		boolean rn = true;	 //��ϵ������2
		boolean email = true;//����3
		boolean dianhua = true;//�绰
		boolean chuanzhen = true;// ����
		boolean shouji = true;//�ֻ�
		boolean qiye = true;//��ҵ���
		boolean dep = true;	 //�������򣨲��ű�ţ�
		boolean dizhi = true; //��ϵ��ַ
		
		
		
		int is = 0;	 //����ֵ
		String username = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У��û���
		//username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
		if(username == null || username.equals("")){impTip.append("<br/> �û���Ϊ��! ");name = false;	}
//		if(username.length() < 2){impTip.append("<br/> ����������6λ! ");name = false;}else 
		if(userDao.checkUsername(username)){impTip.append("'"+username+"'���û����Ѵ���!");name = false;}
		
		String password = ss1.getCell(1, i).getContents().trim();
		if(password==null || password.equals("")){
			impTip.append("<br/> ����Ϊ��!");
			pass = false;
		}
		
		String realname = ss1.getCell(2, i).getContents().trim();
		if(realname==null || realname.equals("")){
			impTip.append("<br/> ��ϵ������Ϊ��!");
			rn = false;
		}
		
		String qiyename = ss1.getCell(7, i).getContents().trim();
		if(qiyename==null || qiyename.equals("")){
			impTip.append("<br/> ��ҵ���Ϊ��!");
			qiye = false;
		}
		
		String bianhao = ss1.getCell(8, i).getContents().trim();
		if(bianhao==null || bianhao.equals("")){
			impTip.append("<br/> ��������Ϊ��!");
			dep = false;
		}else if(!depDao.checkDepBh(bianhao)){
			impTip.append("<br/> �������򲻴���!");
			dep = false;
		}
		
		
		
//		String password = ss1.getCell(1, i).getContents().trim();
//		if(password == null || password.equals("")){impTip.append("<br/> ���벻��Ϊ��! "); pass = false;} else
//		if(password.length() < 6){impTip.append("<br/> ���벻������6λ! ");pass = false;}
//		 �� ��
		
//		String xuhao = ss1.getCell(2, i).getContents().trim();
//		if(xuhao == null||xuhao.equals("")){impTip.append("<br/> ��Ų���Ϊ��! "); xh = false;}
		// ���
		
		
//		boolean isExist15 = false;
//		boolean isExist18 = false;
		
		if(name&&pass&&dep&&qiye&&rn&&email){
			is = 1;//�ɹ�
		}else
		if(!name&&!pass&&!dep&&!qiye&&!rn&&!email){//ȫ��Ϊfalse֤���������
			is = 2;//�������
		}else{
			is = 0;//ʧ��
		}
	return is;
}
	
	/**
	 * ����û�����
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 * @throws ParseException 
	 */
	
	private static int checkImport3(Sheet ss1,int i)throws ElException{  
			UserDaoImpl userDao = new UserDaoImpl();
			boolean name = true; //�û���
			boolean pass = true; //����
			boolean rybh = true;	 //��Ա���1
			boolean rn = true;	 //��ʵ����2
			boolean danwei = true; //�ֹ�����λ4
			boolean dep = true;	 //���ű��
			boolean zw = true;	 //��ְλ5
			boolean zj = true;	 //ְ��6
			boolean age = true; //����7
			boolean xb = true;	 //�Ա�8
			boolean minzu = true; //����9
			boolean jiguan = true;//����10
			boolean cjgzsj = true;//�μӹ���ʱ��11
			boolean rssj  = true;//��˾ʱ��12
			boolean zzmm  = true;//������ò13
			boolean xl = true;//ѧ��14
			boolean pyjx = true;//ƴ����д15
			boolean csrq = true;//��������16
			boolean csd = true;//�����17
			boolean xrzsj = true;//����ְʱ��18
			boolean xygz = true;//��Ա����19
			boolean byyx = true;//��ҵԺУ20
			boolean xw = true;//ѧλ21
			boolean zy = true;//רҵ22
			boolean email = true;//����3
			
			
			int is = 0;	 //����ֵ
			String username = ss1.getCell(1, i).getContents().trim();//��ȡexcel�еĵ�2�У��û���
			//username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
			if(username == null || username.equals("")){impTip.append("<br/> ����Ϊ��! ");rn = false;	}
//			if(username.length() < 2){impTip.append("<br/> ����������6λ! ");name = false;}else 
			String chushengriqi = ss1.getCell(14, i).getContents().trim();
			if(userDao.checkUsername(username+chushengriqi)){impTip.append("'"+username+"'���û����Ѵ���!");name = false;}
			
			String time = ss1.getCell(10,i).getContents().trim();
			if(!time.equals("")){
				try {
					Date d = new Date(new SimpleDateFormat("yyyyMMdd").parse(time).getTime());
				} catch (ParseException e) {
					impTip.append("'"+time+"'��ʽ����ȷ!");
					cjgzsj = false;
					e.printStackTrace();
				}
			}
			
			
			String time2 = ss1.getCell(11,i).getContents().trim();
			if(!time2.equals("")){
				try {
					Date d2 = new Date(new SimpleDateFormat("yyyyMMdd").parse(time2).getTime());
				} catch (ParseException e) {
					impTip.append("'"+time2+"'��ʽ����ȷ!");
					rssj = false;
					e.printStackTrace();
				}
			}
			
			String sr = ss1.getCell(16,i).getContents().trim();
			if(!sr.equals("")){
				try {
					Date shengri = new Date(new SimpleDateFormat("yyyyMMdd").parse(sr).getTime());
				} catch (ParseException e) {
					impTip.append("'"+sr+"'��ʽ����ȷ!");
					csrq = false;
					e.printStackTrace();
				}
			}
			
			
			
			String rzsj = ss1.getCell(14,i).getContents().trim();
			if(!rzsj.equals("")){
				try {
					Date renzhishijian = new Date(new SimpleDateFormat("yyyyMMdd").parse(rzsj).getTime());
				} catch (ParseException e) {
					impTip.append("'"+rzsj+"'��ʽ����ȷ!");
					xrzsj = false;
					e.printStackTrace();
				}
			}
			
//			String password = ss1.getCell(1, i).getContents().trim();
//			if(password == null || password.equals("")){impTip.append("<br/> ���벻��Ϊ��! "); pass = false;} else
//			if(password.length() < 6){impTip.append("<br/> ���벻������6λ! ");pass = false;}
//			 �� ��
			
//			String xuhao = ss1.getCell(2, i).getContents().trim();
//			if(xuhao == null||xuhao.equals("")){impTip.append("<br/> ��Ų���Ϊ��! "); xh = false;}
			// ���
			
			
//			boolean isExist15 = false;
//			boolean isExist18 = false;
			
			
		//	DepartmentDaoImpl depDao = new DepartmentDaoImpl();
			String depname = ss1.getCell(4, i).getContents().trim(); 
			if(depname == null || depname.equals("")){
				impTip.append("<br/>���ű�Ų���Ϊ��! "); 
				dep = false;
			}
			if(name&&pass&&rybh&&danwei&&dep&&zw&&zj&&rn&&xb&&age&&minzu&&jiguan&&cjgzsj&&rssj&&zzmm&&xl&&pyjx&&csrq&&csd&&xrzsj&&xygz&&byyx&&xw&&zy&&email){
				is = 1;//�ɹ�
			}else
			if(!name&&!pass&&!rybh&&!danwei&&!dep&&!zw&&!zj&&!rn&&!xb&&!age&&!minzu&&!jiguan&&!cjgzsj&&!rssj&&!zzmm&&!xl&&!pyjx&&!csrq&&!csd&&!xrzsj&&!xygz&&!byyx&&!xw&&!zy&&!email){//ȫ��Ϊfalse֤���������
				is = 2;//�������
			}else{
				is = 0;//ʧ��
			}
		return is;
	}
	
	
	/**
	 * 
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 */
	
	private static int checkImport2(Sheet ss1,int i)throws ElException{  
			DepartmentDaoImpl depDao = new DepartmentDaoImpl();
			boolean name = true; //�������
			boolean bh = true;	 //��ţ������ϼ���ţ�
			
			int is = 0;	 //����ֵ
			String depname = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У�������ƣ�
			
			if(depname == null || depname.equals("")){impTip.append("<br/> ������Ϊ��! ");name = false;	}
			//if(depname.length() < 6){impTip.append("<br/> �û���������6λ! ");name = false;}else 
//			if(new DepartmentDaoImpl().checkDepName(depname)){impTip.append("'"+depname+"'�ò������Ѵ���!");name = false;}
			
			String depbh = ss1.getCell(1, i).getContents().trim();//��ȡexcel�ĵڶ���(���ű��)
			if(depbh == null ||depbh.equals("")){impTip.append("<br/> ���ű�Ų���Ϊ��! "); bh = false;}
			
//			String sjbh = depbh.substring(0,depbh.length()-3);
//			if(!depDao.checkDepBh(sjbh)){
//				impTip.append("<br/> �ϼ����ű�Ų�����!");
//				bh=false;
//			}
			
			
//			if(depsjbh != null ||!depsjbh.equals("")){
//				int parentid = depDao.getDepId(depsjbh);
//				if(parentid==1){
//					impTip.append("<br/> û���ϼ�����! "); 
//				}
//				sjbh = false;
//			}
			
			boolean isExist15 = false;
			boolean isExist18 = false;
			
			
			if(name&&bh){
				is = 1;//�ɹ�
			}else
			if(!name&&!bh){//ȫ��Ϊfalse֤���������
				is = 2;//�������
			}else{
				is = 0;//ʧ��
			}
		return is;
	}
	
	
	/**��λ����
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 */
	
	private static int checkImportsta(Sheet ss1,int i)throws ElException{  
			StationDaoImpl  staDao = new StationDaoImpl();
			DepartmentDaoImpl  depDao = new DepartmentDaoImpl();
			boolean name = true; //��λ���
			boolean bh = true;	 //���(�����������ű��)
			boolean lb = true; //��λ���
			boolean cj = true;//��λ�㼶
			boolean bmbh = true;//���ű��
			int is = 0;	 //����ֵ
			String staname = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У���λ��ƣ�
			
			if(staname == null || staname.equals("")){impTip.append("<br/> ��λ��Ϊ��! ");name = false;	}
			//if(depname.length() < 6){impTip.append("<br/> �û���������6λ! ");name = false;}else 
		//	if(staDao.checkStaName(staname)){impTip.append("'"+staname+"'�ø�λ���Ѵ���!");name = false;}
			
			
			String sjbh = ss1.getCell(1, i).getContents().trim();
		//	if(!depDao.checkDepBh(sjbh)){
		//		impTip.append(""+sjbh+"û�иò��Ų��ű��!");
		//		bh = false;
		//	}
			
			
//			String gwlb = ss1.getCell(3, i).getContents().trim();
//			if(gwlb == null || gwlb.equals("")){impTip.append("<br/> ��λ�����Ϊ��!");lb = false;}

			
//			String depbh = ss1.getCell(4, i).getContents().trim();
//			if(depbh==null || depbh.equals("")){impTip.append("<br/> �������ű�Ų���Ϊ��!");bmbh = false;}
			
			boolean isExist15 = false;
			boolean isExist18 = false;
			
			
			if(name&&bh&&bmbh){
				is = 1;//�ɹ�
			}else
			if(!name&&!bh&&!bmbh){//ȫ��Ϊfalse֤���������
				is = 2;//�������
			}else{
				is = 0;//ʧ��
			}
		return is;
	}
	
	private static int checkImport_noall(Sheet ss1,int i)throws ElException{
		boolean name = true; //�û���
		boolean pass = true; //����
//		boolean xh = true;	 //���
		boolean rn = true;	 //��ʵ����
		boolean xb = true;	 //�Ա�
//		boolean ds = true;	 //����
//		boolean jz = true;	 //���� 
//		boolean gw = true;	 //��λ
//		boolean zj = true;	 //ְ��
//		boolean zw = true;	 //ְ��
//		boolean sfz = true;	 //���֤
		boolean dep = true;	 //����
		boolean phone = true;	 //����
		int is = 0;	 //����ֵ
		String username = ss1.getCell(0, i).getContents().trim();
		username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
		if(username == null || username.equals("")){impTip.append("<br/> �û�����Ϊ��! ");name = false;	}else
		if(new UserDaoImpl().checkUsername(username)){impTip.append("'"+username+"'���û����Ѵ���!");name = false;}
		// �û���
		
//		String password = ss1.getCell(1, i).getContents().trim();
//		if(password == null || password.equals("")){impTip.append("<br/> ���벻��Ϊ��! "); pass = false;} else
//		if(password.length() < 6){impTip.append("<br/> ���벻������6λ! ");pass = false;}
//		 �� ��
		
		//String xuhao = ss1.getCell(2, i).getContents().trim();
		//if(xuhao == null||xuhao.equals("")){impTip.append("<br/> ��Ų���Ϊ��! "); xh = false;}
		// ���
		String sex = ss1.getCell(4, i).getContents().trim();
		if(sex == null||sex.equals("")){impTip.append("<br/> �Ա���Ϊ��! ");xb = false;}else{
			if(!sex.equals("��") && !sex.equals("Ů")){impTip.append("<br/> �Ա�:'"+sex+"' ����"); xb = false;}
		}
		
		String Realname = ss1.getCell(3, i).getContents().trim();
		if(Realname == null ||Realname.equals("")){impTip.append("<br/> ��ʵ������Ϊ��! "); rn = false;}
		// �� ��
		
		String depname = ss1.getCell(10, i).getContents().trim(); 
		if(depname == null || depname.equals("")){impTip.append("<br/>���ű�Ų���Ϊ��! "); dep = false;}
		
		if(name&&rn&&xb){
			is = 1;//�ɹ�
		}else if(!name&&!rn&&!xb){//ȫ��Ϊfalse֤���������
			is = 2;//�������
		}else{
			is = 0;//ʧ��
		}
		return is;
	}
	//����û�����
	private static int checkImport_noall3(Sheet ss1,int i)throws ElException{
		boolean name = true; //�û���
		boolean pass = true; //����
//		boolean xh = true;	 //���
		boolean rn = true;	 //��ʵ����
		boolean xb = true;	 //�Ա�
//		boolean ds = true;	 //����
//		boolean jz = true;	 //���� 
//		boolean gw = true;	 //��λ
//		boolean zj = true;	 //ְ��
//		boolean zw = true;	 //ְ��
//		boolean sfz = true;	 //���֤
		boolean dep = true;	 //����
		boolean phone = true;	 //����
		int is = 0;	 //����ֵ
		String username = ss1.getCell(0, i).getContents().trim();
		username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
		if(username == null || username.equals("")){impTip.append("<br/> �û�����Ϊ��! ");name = false;	}else
		if(new UserDaoImpl().checkUsername(username)){impTip.append("'"+username+"'���û����Ѵ���!");name = false;}
		// �û���
		
//		String password = ss1.getCell(1, i).getContents().trim();
//		if(password == null || password.equals("")){impTip.append("<br/> ���벻��Ϊ��! "); pass = false;} else
//		if(password.length() < 6){impTip.append("<br/> ���벻������6λ! ");pass = false;}
//		 �� ��
		
		//String xuhao = ss1.getCell(2, i).getContents().trim();
		//if(xuhao == null||xuhao.equals("")){impTip.append("<br/> ��Ų���Ϊ��! "); xh = false;}
		// ���

		String Realname = ss1.getCell(3, i).getContents().trim();
		if(Realname == null ||Realname.equals("")){impTip.append("<br/> ��ʵ������Ϊ��! "); rn = false;}
		// �� ��
		
		String depname = ss1.getCell(10, i).getContents().trim(); 
		if(depname == null || depname.equals("")){impTip.append("<br/>���ű�Ų���Ϊ��! "); dep = false;}
		
		if(name&&rn&&xb){
			is = 1;//�ɹ�
		}else if(!name&&!rn&&!xb){//ȫ��Ϊfalse֤���������
			is = 2;//�������
		}else{
			is = 0;//ʧ��
		}
		return is;
	}
	
	
	private static int checkImport_noall2(Sheet ss1,int i)throws ElException{
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		boolean name = true; //�������
		boolean bh = true;	 //��ţ������ϼ���ţ�
		
		int is = 0;	 //����ֵ
		String depname = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У�������ƣ�
		
		if(depname == null || depname.equals("")){impTip.append("<br/> ������Ϊ��! ");name = false;	}else
		//if(depname.length() < 6){impTip.append("<br/> �û���������6λ! ");name = false;}else 
		if(new DepartmentDaoImpl().checkDepName(depname)){impTip.append("'"+depname+"'�ò������Ѵ���!");name = false;}
		
		String depbh = ss1.getCell(1, i).getContents().trim();//��ȡexcel�ĵڶ���(���ű��)
		if(depbh == null ||depbh.equals("")){impTip.append("<br/> ���ű�Ų���Ϊ��! "); bh = false;}
		
//		String sjbh = depbh.substring(0,depbh.length()-3);
//		if(!depDao.checkDepBh(sjbh)){
//			impTip.append("<br/> �ϼ����ű�Ų�����!");
//			bh=false;
//		}

		
		//		if(depsjbh != null ||!depsjbh.equals("")){
//			int parentid = depDao.getDepId(depsjbh);
//			if(parentid==1){
//				impTip.append("<br/> û���ϼ�����! "); 
//			}
//			sjbh = false;
//		}
		
		boolean isExist15 = false;
		boolean isExist18 = false;
		
		
		if(name&&bh){
			is = 1;//�ɹ�
		}else
		if(!name&&!bh){//ȫ��Ϊfalse֤���������
			is = 2;//�������
		}else{
			is = 0;//ʧ��
		}
		return is;
	}
	
	private static int checkImport_noallsta(Sheet ss1,int i)throws ElException{
		StationDaoImpl  staDao = new StationDaoImpl();
		boolean name = true; //��λ���
		boolean bh = true;	 //���(�����������ű��)
		boolean lb = true; //��λ���
		boolean cj = true;//��λ�㼶
		boolean bmbh = true;//���ű��
		int is = 0;	 //����ֵ
		String staname = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У���λ��ƣ�
		
		if(staname == null || staname.equals("")){impTip.append("<br/> ��λ��Ϊ��! ");name = false;	}else
		//if(depname.length() < 6){impTip.append("<br/> �û���������6λ! ");name = false;}else 
		if(staDao.checkStaName(staname)){impTip.append("'"+staname+"'�ø�λ���Ѵ���!");name = false;}
		
		String stabh = ss1.getCell(1, i).getContents().trim();//��ȡexcel�ĵڶ���(��λ���)
		if(stabh == null ||stabh.equals("")){impTip.append("<br/> ��λ��Ų���Ϊ��! "); bh = false;}else
		if(staDao.checkStBh(stabh)){impTip.append(""+stabh+"�ø�λ����Ѵ���!");bh = false;}
		
		String sjbh = stabh.substring(0,stabh.length()-3);
		if(!staDao.checkStBh(sjbh)){
			impTip.append(""+sjbh+"û�иò��Ų��ű��!");
			bh = false;
		}
//		String gwlb = ss1.getCell(3, i).getContents().trim();
//		if(gwlb == null || gwlb.equals("")){impTip.append("<br/> ��λ�����Ϊ��!");lb = false;}

		
		String depbh = ss1.getCell(4, i).getContents().trim();
		if(depbh==null || depbh.equals("")){impTip.append("<br/> �������ű�Ų���Ϊ��!");bmbh = false;}
		
		boolean isExist15 = false;
		boolean isExist18 = false;
		
		
		if(name&&bh&&bmbh){
			is = 1;//�ɹ�
		}else
		if(!name&&!bh&&!bmbh){//ȫ��Ϊfalse֤���������
			is = 2;//�������
		}else{
			is = 0;//ʧ��
		}
	return is;
}
	
	
	
	/**
	 * ����û������ĵ�����Ƿ�����
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String checkWriteUser(File source,int depid) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImport(ss1, i);
					}else{
						ck= checkImport_noall(ss1, i);
					}
					if(depid==0){
						//��������,��ⲿ�ű���Ƿ����
						String depname = ss1.getCell(10, i).getContents().trim();
						int depId = new DepartmentDaoImpl().getDepByBH(depname).getId();
						if (depId == 0){
							impTip.append("<br/>���ű�Ų�����! ");
							ck=0;
						}
					}else{
						int n=impTip.indexOf("<br/>���ű�Ų���Ϊ��! ");
						if(n>0){
							impTip=new StringBuffer(impTip.substring(0, n));
						}
					}
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	
	
	/**
	 * ����û������ĵ�����Ƿ�����
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String checkWriteUser(File source) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImport2(ss1, i);
					}else{
						ck= checkImport_noall2(ss1, i);
					}
					
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	
	/**���¿γ�ѧϰ��¼������
	 * ����û������ĵ�����Ƿ�����
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String checkWriteCourse(File source,int courseid,int elclassid) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
				//	if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImportCourse(ss1, i,courseid,elclassid);
				//	}else{
				//		ck= checkImport_noall3(ss1, i);
				//	}
					
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	
	/**���¿γ̿��Գɼ�������
	 * ����û������ĵ�����Ƿ�����
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String checkWriteExamRoom(File source,int courseid,int elclassid,int eroomid) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
				//	if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImportExamRoom(ss1, i,courseid,elclassid,eroomid);
				//	}else{
				//		ck= checkImport_noall3(ss1, i);
				//	}
					
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	/**
	 * ��ҵ�û�������
	 */
	public static String checkCompanyUser(File source) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				if(ss1.getRows()==1){
					return "���ĵ�û����ݣ�";
				}
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
			//		if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkCompanyImport(ss1, i);
			//		}else{
			//			ck= checkImport_noall3(ss1, i);
			//		}
					
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	
	/**����û�����
	 * ����û������ĵ�����Ƿ�����
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String checkWriteUser2(File source) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImport3(ss1, i);
					}else{
						ck= checkImport_noall3(ss1, i);
					}
					
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	
	/**
	 * ����û������ĵ�����Ƿ�����(��λ����)
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String checkWriteSta(File source) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImportsta(ss1, i);
					}else{
						ck= checkImport_noallsta(ss1, i);
					}
					
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	
	
	
	
	public static String writeUser2(File source) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		BaseDatat base = new BaseDatat();
//		String Return = "";
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImport(ss1, i);
					}else{
						ck= checkImport_noall(ss1, i);
					}
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					ELUser eu = new ELUser();
					String username = ss1.getCell(0, i).getContents().trim();
					eu.setUsername(username);
					// �û���
					
					String password = ss1.getCell(1, i).getContents().trim();
//					eu.setPassword(MD5.crypt(password == null
//									|| "".equals(password.trim()) ? username
//									: password));
					eu.setPassword(password == null
							|| "".equals(password.trim()) ?MD5.crypt("123456")
							: MD5.crypt(password));
					// �� ��
					
					eu.setXuhao(ss1.getCell(2, i).getContents().trim());
					// ���
					eu.setRealname(ss1.getCell(3, i).getContents().trim());
					// �� ��
					eu.setSex(ss1.getCell(4, i).getContents().trim());
					// �Ա�
					
//					base =  userDao.getBaseInfo_NameType(ss1.getCell(5, i).getContents().trim(), 5);
//					eu.setDishi(base.getId()+"");
//					// ����
//					
//					eu.setShenfenzheng(ss1.getCell(6, i).getContents().trim()
//							.equals("") ? username : ss1.getCell(6, i)
//							.getContents().trim());
//					// ���֤��
//					CheckCard card = new CheckCard(); 
//					String shengri = card.getShengri(ss1.getCell(6, i).getContents().trim());
//					eu.setShengri(getDate(shengri,"yyyy-MM-dd"));
//					// ��������
//					base =  userDao.getBaseInfo_NameType(ss1.getCell(7, i).getContents().trim(), 3);
//					eu.setZhiji(base.getId()+""); 
//					// ְ��
//					
//					base =  userDao.getBaseInfo_NameType(ss1.getCell(8, i).getContents().trim(), 2);
//					eu.setZhiwu(base.getId()+"");  
//					// ְ�� 
//
//					base =  userDao.getBaseInfo_NameType(ss1.getCell(9, i).getContents().trim(), 1);
//					eu.setJingzhong(base.getId()+"");   
//					// ����
//
////					base =  userDao.getBaseInfo_NameType(ss1.getCell(10, i).getContents().trim(), 4);
////					eu.setGangwei(base.getId()+"");   
//					// ��λ
//					eu.setRole(new ElRole(4));
//					String depname = ss1.getCell(10, i).getContents().trim();
//					// * ��λ��
//					// eu.setEmail("");
//					int depparent = 1;// getDep(suozaidanwei, 1); 
//					depparent = new DepartmentDaoImpl().getDepByBH(depname).getId();
//					if (depparent == 0)
//						depparent = 1;
//					// ����
//					// int depid = getDep(suozaibumen, depparent);
//
//					eu.setDepartment(new Department(depparent));
//					eu.setValid(true); 
//					new UserDaoImpl().insert(eu);
//					Return = "true";
					
					
					base =  userDao.getBaseInfo_NameType(ss1.getCell(5, i).getContents().trim(), 5);
					if(base.getId()==0){
						//eu.setDishi(ss1.getCell(5, i).getContents().trim());
						//eu.setDishi(-1);//���벻��Ҫ����֤������� ���ܳ���
						eu.setDishi(bdMap.get("����").getId());
					}else{
						eu.setDishi(base.getId());
					}
					// ����
					//if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						eu.setShenfenzheng(ss1.getCell(6, i).getContents().trim()
								.equals("") ? username : ss1.getCell(6, i)
								.getContents().trim());
						// ���֤��
//						CheckCard card = new CheckCard();
						//eu.setShengri(getDate(card.getShengri(ss1.getCell(6, i).getContents().trim()),"dd/MM/yyyy"));
						eu.setShengri(getShengriBySfz(eu.getShenfenzheng()));
						// �������� 
					//}
					base =  userDao.getBaseInfo_NameType(ss1.getCell(7, i).getContents().trim(), 3);
					if(base.getId()==0){
						//eu.setZhiji(ss1.getCell(7, i).getContents().trim());
						//eu.setZhiji(-1);//���벻��Ҫ����֤������� ���ܳ���
						eu.setZhiji(bdMap.get("ְ��").getId());
					}else{
						eu.setZhiji(base.getId());
					}
					// ְ��
					
					base =  userDao.getBaseInfo_NameType(ss1.getCell(8, i).getContents().trim(), 2);
					if(base.getId()==0){
						//eu.setZhiwu(ss1.getCell(8, i).getContents().trim());
						//eu.setZhiwu(-1);//���벻��Ҫ����֤������� ���ܳ���
						eu.setZhiwu(bdMap.get("ְ��").getId());
					}else{
						eu.setZhiwu(base.getId());
					}
					// ְ�� 

					base =  userDao.getBaseInfo_NameType(ss1.getCell(9, i).getContents().trim(), 1);
					if(base.getId()==0){
						//eu.setJingzhong(ss1.getCell(9, i).getContents().trim());
						//eu.setJingzhong(-1);
						eu.setJingzhong(bdMap.get("����").getId());
					}else{
						eu.setJingzhong(base.getId());
					}
					// ����

//					base =  userDao.getBaseInfo_NameType(ss1.getCell(10, i).getContents().trim(), 4);
//					//eu.setGangwei(base.getId()+"");
//					if(base.getId()==0){
//						eu.setGangwei(ss1.getCell(10, i).getContents().trim());
//					}else{
//						eu.setGangwei(base.getId()+"");
//					}
					// ��λ
					
					// * ��λ��
					eu.setRole(new ElRole(4));
					String depname = ss1.getCell(10, i).getContents().trim();
					int depparent = 1;// getDep(suozaidanwei, 1); 
					depparent = new DepartmentDaoImpl().getDepByBH(depname).getId();
					if (depparent == 0){
						//depparent = 1;
						//�Ҳ�����ŵ�ʱ�򲻵���
						continue;
					}
					eu.setDepartment(new Department(depparent));
					eu.setValid(true); 
					new UserDaoImpl().insert2(eu);
//					Return = "true";
				} 
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
	
	public static String writeCompanyUser(File source) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		BaseDatat base = new BaseDatat();
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		StationDaoImpl staDao = new StationDaoImpl();
//		String Return = "";
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					//if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkCompanyImport(ss1, i);
					//}else{
					//	ck= checkImport_noall3(ss1, i);
					//}
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					ELUser eu = new ELUser();
					String username = ss1.getCell(0, i).getContents().trim();
					eu.setUsername(username);
					// �û���
					if(!userDao.checkUsername(eu.getUsername())){
						String password = ss1.getCell(1, i).getContents().trim();
						eu.setPassword(password == null
								|| "".equals(password.trim()) ?MD5.crypt("111111")
								: MD5.crypt(password));
						// �� ��
						
						eu.setRealname(ss1.getCell(2, i).getContents().trim());
						// ��ϵ���� ��
						
						eu.setEmail(ss1.getCell(3,i).getContents().trim());//����
						eu.setPhone(ss1.getCell(4,i).getContents().trim());//�绰
						eu.setXuhao(ss1.getCell(5,i).getContents().trim());//����
						eu.setMovephone(ss1.getCell(6,i).getContents().trim());//�ֻ�
						eu.setRole(new ElRole(2));//���Ź���Ա
						String depname = ss1.getCell(7, i).getContents().trim(); //��ҵ���
						
						String depbh = ss1.getCell(8, i).getContents().trim();
						int depparentid = depDao.getDepByBH(depbh).getId();
						Department dep = new Department();
						dep.setName(depname);
						dep.setParent(new ElNode(depparentid));
						dep.setManager(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
						int depid = depDao.addDep1(dep);
						((ElNodeSQL) SpringContextUtil
								.getBean(ElConstants.CLASS_ELNODESQL))
								.updatetlrid("department");
						ElLogger.busilogger(
								getSessionIntValue(ElConstants.SESSION_USERID),
								ElLoggerConstants.LOG_MOD_DEPARTMENT,
								ElLoggerConstants.LOG_TYPE_ADD, dep
										.getName(), ElLoggerConstants.LOG_RES_SUCC,
										depid);
						
						eu.setDepartment(new Department(depid));
						eu.setValid(true); 
						eu.setAddress(ss1.getCell(9, i).getContents().trim());//��ϵ��ַ
						eu.setSex("��");
						eu.setStaid(1);
						eu.setXianzhiwei("");
						if(!userDao.checkUsername(eu.getUsername())){
							int userid = userDao.insert(eu);
							depDao.addOpusers("op", userid, depid);
						}else{
							userDao.updateUserByName(eu);
						}
						
					}
					
//					Return = "true";
				} 
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
	public static int getSessionIntValue(String key) {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		if(session.getAttribute(key)!=null){
			return (Integer) session.getAttribute(key);
		}
		return 0;
	}
	
	
	public static String writeUser22(File source) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		BaseDatat base = new BaseDatat();
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		StationDaoImpl staDao = new StationDaoImpl();
//		String Return = "";
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					//if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImport3(ss1, i);
					//}else{
					//	ck= checkImport_noall3(ss1, i);
					//}
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					ELUser eu = new ELUser();
					String username = ss1.getCell(1, i).getContents().trim();
					String chushengriqi = ss1.getCell(14, i).getContents().trim();
					eu.setUsername(username+chushengriqi);
					// �û���
					if(!userDao.checkUsername(eu.getUsername())){
						String password = chushengriqi;
						eu.setPassword(password == null
								|| "".equals(password.trim()) ?MD5.crypt("123456")
								: MD5.crypt(password));
						// �� ��
						
						eu.setXuhao(ss1.getCell(0, i).getContents().trim());
						// ���
						eu.setRealname(username);
						// �� ��
						
						String danwei = ss1.getCell(3, i).getContents().trim();
						danwei = danwei.replace("-", "");
						eu.setDanwei(danwei);//������λ
						
						eu.setRole(new ElRole(4));
						String depname = ss1.getCell(4, i).getContents().trim();
						depname = depname.replace("-", "");
						int depparent = 1;// getDep(suozaidanwei, 1); 
						depparent = depDao.getDepByBH(depname).getId();
						if (depparent == 0){
							//depparent = 1;
							//�Ҳ�����ŵ�ʱ�򲻵���
							continue;
						}
						eu.setDepartment(new Department(depparent));
						
						eu.setXianzhiwei(ss1.getCell(5,i).getContents().trim());//��ְλ
						
						eu.setZhideng(ss1.getCell(6,i).getContents().trim());//ְ��
						
						eu.setSex(ss1.getCell(7, i).getContents().trim());// �Ա�
						
						eu.setMinzu(ss1.getCell(8, i).getContents().trim());//����
						
						eu.setJiguan(ss1.getCell(9,i).getContents().trim());//����
						
						String time = ss1.getCell(10,i).getContents().trim();
						if(!time.equals("")){
							Date d = new Date(new SimpleDateFormat("yyyyMMdd").parse(time).getTime());
							
							eu.setCanjiagongzuoshijian(d);//�μӹ���ʱ��
						}
						
						
						String time2 = ss1.getCell(11,i).getContents().trim();
						if(!time2.equals("")){
							Date d2 = new Date(new SimpleDateFormat("yyyyMMdd").parse(time2).getTime());
							eu.setRusishijian(d2);//��˾ʱ��
						}
						
						
						//Date ds = new SimpleDateFormat("yyyy-MM-dd").parse("2010-05-22 00:00:00");
						
						eu.setZhengzhimianmao(ss1.getCell(12,i).getContents().trim());//������ò
						
						String xueli = ss1.getCell(13,i).getContents().trim();
						if(xueli.equals("")||xueli==null){
							xueli = "δ��д";
						}
						eu.setXueli(xueli);//ѧ��
						
						
						String sr = ss1.getCell(14,i).getContents().trim();
						if(!sr.equals("")){
							Date shengri = new Date(new SimpleDateFormat("yyyyMMdd").parse(sr).getTime());
							eu.setShengri(shengri);//��������
						}
						
						
						eu.setChushengdi(ss1.getCell(15,i).getContents().trim());//�����
						
						String xrzsj = ss1.getCell(16,i).getContents().trim();
						if(!xrzsj.equals("")){
							Date renzhishijian = new Date(new SimpleDateFormat("yyyyMMdd").parse(xrzsj).getTime());
							eu.setXianrenzhishijian(renzhishijian);//����ְʱ��
						}
						
						
						eu.setXianyuangongzu(ss1.getCell(17,i).getContents().trim());//��Ա����
						
						String school = ss1.getCell(18,i).getContents().trim();
						if(school.equals("")||school==null){
							school = "δ��д";
						}
						eu.setSchool(school);//��ҵԺУ
						
						String xuewei = ss1.getCell(19,i).getContents().trim();
						if(xuewei.equals("")||xuewei==null){
							xuewei = "δ��д";
						}
						eu.setXuewei(xuewei);//ѧλ
						
						String zhuanye = ss1.getCell(20,i).getContents().trim();
						if(zhuanye.equals("")||zhuanye==null){
							zhuanye = "δ��д";
						}
						eu.setSpecialty(zhuanye);//רҵ
						
						eu.setEmail(ss1.getCell(2,i).getContents().trim());//����
						
						
						int staid = staDao.getStationId(depparent,eu.getXianzhiwei());
						eu.setValid(true); 
						eu.setStation(new Station(staid));
						if(!userDao.checkUsername(eu.getUsername())){
							userDao.insert(eu);
						}else{
							userDao.updateUserByName(eu);
						}
						
					}
					
//					Return = "true";
				} 
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
	
	
	
	public static String writeUser3(File source) throws ElException { 
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		BaseDatat base = new BaseDatat();
//		String Return = "";
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					ck= checkImport2(ss1, i);
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					Department dep = new Department();
					String depname = ss1.getCell(0, i).getContents().trim();
					String[] name = depname.split("-");
					dep.setName(name[name.length-1]);
					// �������
					
					String bh = ss1.getCell(1, i).getContents().trim();
					if(bh.indexOf("-")!=-1){
						bh = bh.replace("-","");
					}
					if(!depDao.checkDepBh(bh)){
						dep.setBh(bh);
						
						String sjbh = bh.substring(0,bh.length()-3);
						int parentid = depDao.getDepId(sjbh);
						dep.setParent(new ElNode(parentid));
						dep.setManager(new ELUser());
						depDao.addDep(dep);
						
					}
				
				} 
				((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
				.updatetlrid("department");
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
	
	
	public static String writeSta(File source) throws ElException { 
		StationDaoImpl staDao = new StationDaoImpl();
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		BaseDatat base = new BaseDatat();
//		String Return = "";
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					ck= checkImportsta(ss1, i);
					
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					Station sta = new Station();
					String staname = ss1.getCell(0, i).getContents().trim();
					sta.setName(staname);
					
					String bh = ss1.getCell(1, i).getContents().trim();
					bh = bh.replace("-", "");
					
					
					String leibie = ss1.getCell(2, i).getContents().trim();
					sta.setLeibie(leibie);
					
					String cj = ss1.getCell(3,i).getContents().trim();
					sta.setCengji(cj);
				//	String sjbh = bh.substring(0,bh.length()-3);
					int parentid = staDao.getParentidByBh(bh);
					sta.setParent(new ElNode(parentid));
					int depid = depDao.getDepId(bh);
					sta.setDepid(depid);
					sta.setManager(new ELUser());
					if(!staDao.checkSta(staname,depid)){
						
						String gwbh = bh+1000+i;
						sta.setBh(gwbh);
						staDao.addSt(sta);
					}
					
			//		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_ST);
				} 
				((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
				.updatetlrid("station");
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
	
	public static String writeSta2(File source) throws ElException { 
		StationDaoImpl staDao = new StationDaoImpl();
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		BaseDatat base = new BaseDatat();
//		String Return = "";
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					ck= checkImportsta(ss1, i);
					
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					Station sta = new Station();
					String staname = ss1.getCell(0, i).getContents().trim();
					sta.setName(staname);
					
					String bh = ss1.getCell(1, i).getContents().trim();
					
					
					String leibie = ss1.getCell(2, i).getContents().trim();
					sta.setLeibie(leibie);
					
					String cj = ss1.getCell(3,i).getContents().trim();
					sta.setCengji(cj);
					
					String sjbh = bh.substring(0,bh.length()-3);
					int parentid = staDao.getParentidByBh(sjbh);
					sta.setParent(new ElNode(parentid));
					int depid = depDao.getDepId(bh);
					sta.setDepid(depid);
					sta.setManager(new ELUser());
					if(!staDao.checkSta(staname,depid)){
						
						String gwbh = bh+1000+i;
						sta.setBh(bh);
						staDao.addSt(sta);
					}
					
			//		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_ST);
				} 
				((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL))
				.updatetlrid("station");
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}

//	public static void writeUser(File source, int depid) throws ElException {
//		try {
//			InputStream is = new FileInputStream(source);
//			jxl.Workbook rwb = Workbook.getWorkbook(is);
//			Sheet ss[] = rwb.getSheets();
//			if (null != ss && ss.length > 0) {
//				Sheet ss1 = ss[0];
//				for (int i = 1; i < ss1.getRows(); i++) {
//					ELUser eu = new ELUser();
//					String username = ss1.getCell(0, i).getContents().trim();
//					eu.setUsername(username);
//					// �û���
//					String password = ss1.getCell(1, i).getContents().trim();
//					eu
//							.setPassword(MD5.crypt(password == null
//									|| "".equals(password.trim()) ? username
//									: password));
//					// �� ��
//					eu.setXuhao(ss1.getCell(2, i).getContents().trim());
//					// ���
//					eu.setRealname(ss1.getCell(3, i).getContents().trim());
//					// �� ��
//					eu.setSex(ss1.getCell(4, i).getContents().trim());
//					// �Ա�
//					eu.setDishi(ss1.getCell(5, i).getContents().trim());
//					// ����
//					eu.setShenfenzheng(ss1.getCell(6, i).getContents().trim()
//							.equals("") ? username : ss1.getCell(6, i)
//							.getContents().trim());
//					// ���֤��
////					eu.setShengri(getDate(ss1.getCell(7, i).getContents().trim(),"dd/MM/yyyy"));
//					eu.setShengri(getShengriBySfz(eu.getShenfenzheng()));
//					// ��������
//					eu.setZhiji(ss1.getCell(8, i).getContents().trim());
//					// ְ��
//					eu.setZhiwu(ss1.getCell(9, i).getContents().trim());
//					// ְ��
//					eu.setJingzhong(ss1.getCell(10, i).getContents().trim());
//					// ����
//					eu.setGangwei(ss1.getCell(11, i).getContents().trim());
//					// ��λ
//					eu.setRole(new ElRole(4));
////					String realname = ss1.getCell(2, i).getContents().trim();
////					eu.setRealname(realname);
//
////					String sex = ss1.getCell(3, i).getContents();
////					eu.setSex(sex);
//					// String depname = ss1.getCell(4, i).getContents();
//					// * ��λ��
//					// eu.setEmail("");
//					// int depparent =1;//getDep(suozaidanwei, 1);
//					// depparent = new
//					// DepartmentDaoImpl().getDepByName(depname);
//					// if(depparent==-1)
//					// depparent=1;
//					// ����
//					// int depid = getDep(suozaibumen, depparent);
//
//					// new UserDaoImpl().insert(eu);
//					eu.setDepartment(new Department(depid));
//					eu.setValid(true);
//					UserDaoImpl eud = new UserDaoImpl();
//					if (!eud.checkUsername(username)) {
//						eud.userAdd(eu);
//					}
//					// }
//
//				}
//			}
//		} catch (Exception e) {
//			logger.error("�����˺�ʧ��", e);
//			throw new ElException("�����˺�ʧ��");//���������ʾ �����Դ˴����׳��쳣
//		}
//	}
	public static Date getShengriBySfz(String shengfz){
		if(shengfz==null || (shengfz.length()!=18 && shengfz.length()!=15))
			return new Date(System.currentTimeMillis());
		String date8 ="";
		if(shengfz.length()==18){
			date8 = shengfz.substring(6, 14);
		}else{
			date8 ="19"+ shengfz.substring(6, 12);
		}
		date8=date8.substring(0,4)+"-"+date8.substring(4,6)+"-"+date8.substring(6,8);
		Date d = null;
		try {
			 d = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date8).getTime());
		} catch (Exception e) {
			d =new Date(System.currentTimeMillis());
		}
		return d  ;
	}
	/**
	 * ��ʼ��һЩ����ݣ����û��"����",�Ͷ����ϣ�
	 */
	private static Map<String, BaseDatat> initBasedatat() throws ElException{
		Map<String, BaseDatat> bdMap=new HashMap<String, BaseDatat>();
		String baseValue="����";
		UserDao userDao=(UserDao)SpringContextUtil.getBean("userDao");
		BaseDatat baseDatat=new BaseDatat();
		baseDatat.setBasevalue(baseValue);
		baseDatat.setRemack(baseValue);
		baseDatat.setElUser(new ELUser(1));
		if(!userDao.checkBase(baseValue, 1)){//�����û��������
			baseDatat.setTypeid(1);
			userDao.addBaseDb(baseDatat);
		}
		if(!userDao.checkBase(baseValue, 2)){//�����û������ְ��
			baseDatat.setTypeid(2);
			userDao.addBaseDb(baseDatat);
		}
		if(!userDao.checkBase(baseValue, 3)){//�����û������ְ��
			baseDatat.setTypeid(3);
			userDao.addBaseDb(baseDatat);
		}
		if(!userDao.checkBase(baseValue, 5)){//�����û���������
			baseDatat.setTypeid(5);
			userDao.addBaseDb(baseDatat);
		}
		bdMap.put("����", userDao.getBaseDatatByBasevalue(1,baseValue));
		bdMap.put("ְ��", userDao.getBaseDatatByBasevalue(2,baseValue));
		bdMap.put("ְ��", userDao.getBaseDatatByBasevalue(3,baseValue));
		bdMap.put("����", userDao.getBaseDatatByBasevalue(5,baseValue));
		return bdMap;
	}
	
	/**
	 * Description: ָ�����ŵĵ���
	* @Version1.0 2012-7-16 ����08:45:43 by ����˴��wenyishun110@163.com������
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String writeUser2(File source, int depid) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		BaseDatat base = new BaseDatat();
		jxl.Workbook rwb=null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
//			String Return = "";
			impTip = new StringBuffer(); 
			InputStream is = new FileInputStream(source);
			//jxl.Workbook rwb = Workbook.getWorkbook(is);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) { 
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImport(ss1, i);
					}else{
						ck= checkImport_noall(ss1, i);
					}
					
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true");
						break;
 					}
					//}
					ELUser eu = new ELUser();
					String username = ss1.getCell(0, i).getContents().trim();
					eu.setUsername(username);
					// �û���
					String password = ss1.getCell(1, i).getContents().trim();
//					eu
//							.setPassword(MD5.crypt(password == null
//									|| "".equals(password.trim()) ? username
//									: password));
					eu
					.setPassword(password == null
							|| "".equals(password.trim()) ?MD5.crypt("123456")
							: MD5.crypt(password));
			
					// �� ��
					eu.setXuhao(ss1.getCell(2, i).getContents().trim());
					// ���
					eu.setRealname(ss1.getCell(3, i).getContents().trim());
					// �� ��
					eu.setSex(ss1.getCell(4, i).getContents().trim());
					// �Ա� 
					base =  userDao.getBaseInfo_NameType(ss1.getCell(5, i).getContents().trim(), 5);
					//eu.setDishi(base.getId()+"");
					if(base.getId()==0){
						//eu.setDishi(ss1.getCell(5, i).getContents().trim());
						//eu.setDishi(-1);
						eu.setDishi(bdMap.get("����").getId());
					}else{
						eu.setDishi(base.getId());
					}
					// ����
					eu.setShenfenzheng(ss1.getCell(6, i).getContents().trim()
							.equals("") ? username : ss1.getCell(6, i)
							.getContents().trim());
					// ���֤��
//					CheckCard card = new CheckCard();
					//eu.setShengri(getDate(card.getShengri(ss1.getCell(6, i).getContents().trim()),"dd/MM/yyyy"));
					eu.setShengri(getShengriBySfz(eu.getShenfenzheng()));
					// �������� 
					base =  userDao.getBaseInfo_NameType(ss1.getCell(7, i).getContents().trim(), 3);
					if(base.getId()==0){
						//eu.setZhiji(ss1.getCell(7, i).getContents().trim());
						//eu.setZhiji(-1);
						eu.setZhiji(bdMap.get("ְ��").getId());
					}else{
						eu.setZhiji(base.getId());
					}
					// ְ��
					
					base =  userDao.getBaseInfo_NameType(ss1.getCell(8, i).getContents().trim(), 2);
					//eu.setZhiwu(base.getId()+"");
					if(base.getId()==0){
						//eu.setZhiwu(ss1.getCell(8, i).getContents().trim());
						//eu.setZhiwu(-1);
						eu.setZhiwu(bdMap.get("ְ��").getId());
					}else{
						eu.setZhiwu(base.getId());
					}
					// ְ�� 

					base =  userDao.getBaseInfo_NameType(ss1.getCell(9, i).getContents().trim(), 1);
					//eu.setJingzhong(base.getId()+"");
					if(base.getId()==0){
						//eu.setJingzhong(ss1.getCell(9, i).getContents().trim());
						//eu.setJingzhong(-1);
						eu.setJingzhong(bdMap.get("����").getId());
					}else{
						eu.setJingzhong(base.getId());
					}
					// ����

//					base =  userDao.getBaseInfo_NameType(ss1.getCell(10, i).getContents().trim(), 4);
//					//eu.setGangwei(base.getId()+"");
//					if(base.getId()==0){
//						eu.setGangwei(ss1.getCell(10, i).getContents().trim());
//					}else{
//						eu.setGangwei(base.getId()+"");
//					}
					// ��λ
					eu.setRole(new ElRole(4));
//					String realname = ss1.getCell(2, i).getContents().trim();
//					eu.setRealname(realname);

//					String sex = ss1.getCell(3, i).getContents();
//					eu.setSex(sex);
					// String depname = ss1.getCell(4, i).getContents();
					// * ��λ��
					// eu.setEmail("");
					// int depparent =1;//getDep(suozaidanwei, 1);
					// depparent = new
					// DepartmentDaoImpl().getDepByName(depname);
					// if(depparent==-1)
					// depparent=1;
					// ����
					// int depid = getDep(suozaibumen, depparent);

					// new UserDaoImpl().insert(eu);
					eu.setDepartment(new Department(depid));
					eu.setValid(true);
					UserDaoImpl eud = new UserDaoImpl();
//					if (!eud.checkUsername(username)) {
						eud.userAdd(eu);
//					}
//					Return = "true";
					// }

				}
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e);
			//throw new ElException("�����˺�ʧ��");//���������ʾ �����Դ˴����׳��쳣
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		//rwb.close();
		return new String(impTip);
	}
	//����û�����
	public static String writeUser22(File source, int depid) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		BaseDatat base = new BaseDatat();
		jxl.Workbook rwb=null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
//			String Return = "";
			impTip = new StringBuffer(); 
			InputStream is = new FileInputStream(source);
			//jxl.Workbook rwb = Workbook.getWorkbook(is);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) { 
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= checkImport3(ss1, i);
					}else{
						ck= checkImport_noall3(ss1, i);
					}
					
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true");
						break;
 					}
					//}
					ELUser eu = new ELUser();
					String username = ss1.getCell(0, i).getContents().trim();
					eu.setUsername(username);
					// �û���
					String password = ss1.getCell(1, i).getContents().trim();
					eu
					.setPassword(password == null
							|| "".equals(password.trim()) ?MD5.crypt("123456")
							: password);
			
					// �� ��
					eu.setXuhao(ss1.getCell(2, i).getContents().trim());
					// ���
					eu.setRealname(ss1.getCell(3, i).getContents().trim());
					// �� ��
					eu.setSex(ss1.getCell(4, i).getContents().trim());
					// �Ա� 
					base =  userDao.getBaseInfo_NameType(ss1.getCell(5, i).getContents().trim(), 5);
					//eu.setDishi(base.getId()+"");
					if(base.getId()==0){
						//eu.setDishi(ss1.getCell(5, i).getContents().trim());
						//eu.setDishi(-1);
						eu.setDishi(bdMap.get("����").getId());
					}else{
						eu.setDishi(base.getId());
					}
					// ����
					eu.setShenfenzheng(ss1.getCell(6, i).getContents().trim()
							.equals("") ? username : ss1.getCell(6, i)
							.getContents().trim());
					eu.setShengri(getShengriBySfz(eu.getShenfenzheng()));
					// �������� 
					base =  userDao.getBaseInfo_NameType(ss1.getCell(7, i).getContents().trim(), 3);
					if(base.getId()==0){
						//eu.setZhiji(ss1.getCell(7, i).getContents().trim());
						//eu.setZhiji(-1);
						eu.setZhiji(bdMap.get("ְ��").getId());
					}else{
						eu.setZhiji(base.getId());
					}
					// ְ��
					
					base =  userDao.getBaseInfo_NameType(ss1.getCell(8, i).getContents().trim(), 2);
					//eu.setZhiwu(base.getId()+"");
					if(base.getId()==0){
						//eu.setZhiwu(ss1.getCell(8, i).getContents().trim());
						//eu.setZhiwu(-1);
						eu.setZhiwu(bdMap.get("ְ��").getId());
					}else{
						eu.setZhiwu(base.getId());
					}
					// ְ�� 

					base =  userDao.getBaseInfo_NameType(ss1.getCell(9, i).getContents().trim(), 1);
					//eu.setJingzhong(base.getId()+"");
					if(base.getId()==0){
						//eu.setJingzhong(ss1.getCell(9, i).getContents().trim());
						//eu.setJingzhong(-1);
						eu.setJingzhong(bdMap.get("����").getId());
					}else{
						eu.setJingzhong(base.getId());
					}
					// ��λ
					eu.setRole(new ElRole(4));
					eu.setDepartment(new Department(depid));
					eu.setValid(true);
					UserDaoImpl eud = new UserDaoImpl();
					eud.userAdd(eu);

				}
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e);
			//throw new ElException("�����˺�ʧ��");//���������ʾ �����Դ˴����׳��쳣
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}

	public void writeDep(File source) throws ElException {
		try {
			InputStream is = new FileInputStream(source);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 0; i < ss1.getRows(); i++) {

				}
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e);
			throw new ElException("�����˺�ʧ��");
		}
	}

//	public static void main(String[] args) {
//		try {
//			File source = new File("E:\\��Ա��Ϣ20090727.XLS");
//			InputStream is = new FileInputStream(source);
//			jxl.Workbook rwb = Workbook.getWorkbook(is);
//			Sheet ss[] = rwb.getSheets();
//			if (null != ss && ss.length > 0) {
//				Sheet ss1 = ss[0];
//				for (int i = 1; i < ss1.getRows(); i++) {
//					String biyeshijian = ss1.getCell(18, i).getContents();
//					String zhiwupinrenriqi = ss1.getCell(23, i).getContents();
//					String zhichengquderiqi = ss1.getCell(24, i).getContents();
//				}
//			}
//		} catch (Exception e) {
//		}
//
//	}
	public static Date getDate(String dateStr,String dateFormat) {
		// Date d = new Date(System.currentTimeMillis()); 
		Date d = null; 
		try {
			// if(date.matches(""))  
			DateFormat format1 = new SimpleDateFormat(dateFormat);  
			d =  new Date( format1.parse(dateStr).getTime()); 
//			d = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());  
		} catch (Exception e) { 
		} 
		return d;
	}
	public static Date getDate(String date) {
		// Date d = new Date(System.currentTimeMillis()); 
		Date d = null; 
		try {
			// if(date.matches(""))  
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
			d = new Date(  format1.parse(date).getTime()); 
//			d = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());  
		} catch (Exception e) {
			logger.error("\"" + date + "\"����ת������");
		}

		return d;
	}

	/**
	 * ��ѵ��ѧԱ����
	 * @param source
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public static String writeUserToClass(File source, int classid) throws ElException { 
		UserDao userDao = (UserDao)SpringContextUtil.getBean("userDao");
		ClassDao classDao = (ClassDao)SpringContextUtil.getBean("classDao");
		jxl.Workbook rwb=null;
		StringBuffer msg=new StringBuffer("");
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				msg=new StringBuffer();
				Sheet ss1 = ss[0];
				for (int i = 0; i < ss1.getRows(); i++) {
					//�Ȳ�ѯ���ĵ��е��û����֤��û�д�����ϵͳ�У��о͵��뵽��ѵ�ࣨ����Ѿ�����ѵ������ô�����룩��û�о͸����ʾ��Ȼ���������һ��
					ELUser user=userDao.getEluserByShenfenzhang(ss1.getCell(0,i).getContents().trim());
					if(user!=null){
						//����Ƿ�������ѵ��
						if(!classDao.checkElclassIsUsers(user.getId(),classid)){
							classDao.assign2userAdd3(user.getId(),classid,ClassConstants.CLASS_SQFS_FP);//�����ж�
						}
					}else{
						msg.append("��"+(i+1)+"�У���ѧԱ���֤��ϵͳ�в����ڣ����ɵ��룡<br />");
					}
				}
				if(msg.toString().length()==0){
					msg.append("����ȫ���ɹ���");
				}
				return msg.toString();
			}
		} catch (Exception e) {
			logger.error("����ѧԱʧ��", e);
			//throw new ElException("�����˺�ʧ��");//���������ʾ �����Դ˴����׳��쳣
			msg.append("����ʧ�ܣ��ĵ���ʽ����");
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return msg.toString();
	}
	/**
	 * �����Ծ���Ա����
	 * @param source
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public static String writeUserToEroomEp(File source, int roomid,int epid) throws ElException { 
		UserDao userDao = (UserDao)SpringContextUtil.getBean("userDao");
		EroomDao eroomDao = (EroomDao)SpringContextUtil.getBean("eroomDao");
		StudyQuizDao studyQuizDao = (StudyQuizDao)SpringContextUtil.getBean("studyQuizDao");
		jxl.Workbook rwb=null;
		StringBuffer msg=new StringBuffer("");
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				msg=new StringBuffer();
				ExamRoom examRoom=eroomDao.getExamRoomByid(roomid);
				Sheet ss1 = ss[0];
				for (int i = 0; i < ss1.getRows(); i++) {
					//�Ȳ�ѯ���ĵ��е��û����֤��û�д�����ϵͳ�У��о͵��뵽��ѵ�ࣨ����Ѿ�����ѵ������ô�����룩��û�о͸����ʾ��Ȼ���������һ��
					ELUser user=userDao.getEluserByShenfenzhang(ss1.getCell(0,i).getContents().trim());
					if(user!=null){
						// ����ѧԱ�Ƿ�����˸��Ծ�
						if (!studyQuizDao.checkStudyExamPaper(user.getId(),
								epid, examRoom.getId(),examRoom.getClassid())) {
							// ��Ӹ�ѧԱ�� ѧԱ�Ծ����
							studyQuizDao.addStudyExamPaper(user.getId(), epid, examRoom.getId(), examRoom.getClassid());
						}
						//����û���û�з��䵽�ÿ���
						if (!eroomDao.checkuser2eroom(examRoom.getId(), 
								user.getId(), examRoom.getClassid())) {
							eroomDao.adduser2eroom(examRoom.getId(), user.getId(), 1,
									examRoom.getClassid(), CourseConstants.EXAMROOM_FPFS_SQ);
						}
					}else{
						msg.append("��"+(i+1)+"�У���ѧԱ���֤��ϵͳ�в����ڣ����ɵ��룡<br />");
					}
				}
				if(msg.toString().length()==0){
					msg.append("����ȫ���ɹ���");
				}
				return msg.toString();
			}
		} catch (Exception e) {
			logger.error("����ѧԱʧ��", e);
			//throw new ElException("�����˺�ʧ��");//���������ʾ �����Դ˴����׳��쳣
			msg.append("����ʧ�ܣ��ĵ���ʽ����");
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return msg.toString();
	}
	/**
	 * �����Ծ���Ա�޳�
	 * @param source
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public static String writeUserDeleteEroomEp(File source, int roomid,int epid) throws ElException { 
		UserDao userDao = (UserDao)SpringContextUtil.getBean("userDao");
		EroomDao eroomDao = (EroomDao)SpringContextUtil.getBean("eroomDao");
		StudyQuizDao studyQuizDao = (StudyQuizDao)SpringContextUtil.getBean("studyQuizDao");
		jxl.Workbook rwb=null;
		StringBuffer msg=new StringBuffer("");
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				msg=new StringBuffer();
				ExamRoom examRoom=eroomDao.getExamRoomByid(roomid);
				Sheet ss1 = ss[0];
				for (int i = 0; i < ss1.getRows(); i++) {
					//�Ȳ�ѯ���ĵ��е��û����֤��û�д�����ϵͳ�У��о͵��뵽��ѵ�ࣨ����Ѿ�����ѵ������ô�����룩��û�о͸����ʾ��Ȼ���������һ��
					ELUser user=userDao.getEluserByShenfenzhang(ss1.getCell(0,i).getContents().trim());
					if(user!=null){
						// ����ѧԱ�Ƿ�����˸��Ծ�
						if (studyQuizDao.checkStudyExamPaper(user.getId(),
								epid, examRoom.getId(),examRoom.getClassid())) {
							studyQuizDao.deleteQuiz(user.getId(), examRoom.getId(),epid);
						}
						
					}else{
						msg.append("��"+(i+1)+"�У���ѧԱ���֤��ϵͳ�в����ڣ������޳�<br />");
					}
				}
				if(msg.toString().length()==0){
					msg.append("�޳�ȫ���ɹ���");
				}
				return msg.toString();
			}
		} catch (Exception e) {
			logger.error("�޳�ѧԱʧ��", e);
			//throw new ElException("�����˺�ʧ��");//���������ʾ �����Դ˴����׳��쳣
			msg.append("�޳�ʧ�ܣ��ĵ���ʽ����");
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return msg.toString();
	}
	
	public static int getAgeBySfz(String sfz){
		int a = 0 ;
		Date d= getShengriBySfz(sfz);
		Calendar c = Calendar.getInstance();
		Calendar cd = Calendar.getInstance(TimeZone.getDefault());
		cd .setTimeInMillis(d.getTime());
		return c.get(Calendar.YEAR)-cd.get(Calendar.YEAR) ;
	}
	/*
	 * public static int getDep(String comp, int parentid) throws ElException {
	 * int depid = new DepartmentDaoImpl().getDepId(comp, parentid); if (comp ==
	 * null || "".equals(comp.trim())) return parentid; if (depid == -1) {
	 * Department dep = new Department(); dep.setName(comp); dep.setParent(new
	 * Department(parentid)); dep.setCompany(new Company(1)); dep.setManager(new
	 * ELUser(0)); new DepartmentDaoImpl().addDep(dep); depid = dep.getId(); }
	 * return depid; }
	 */
	
	/**�⾭ó�û�����
	 * ����û������ĵ�����Ƿ�����
	 * @param source
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public static String wjm_checkWriteUser(File source) throws ElException { 
		StringBuffer msg=new StringBuffer("");
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("<br/>����ʧ��, ʧ��ԭ���� : ��"+i+"��");
				//	if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= wjm_checkImport(ss1, i);
				//	}else{
				//		ck= checkImport_noall3(ss1, i);
				//	}
					
					if(ck == 0){//ʧ��
						msg.append(impTip.toString());
						impTip.setLength(0);
						//break;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						//impTip.append("true"); 
						break;
 					}
				}
				if(msg.toString().equals("")){
					return "���ĵ����û�����⣡";
				}else{
					return msg.toString();
				}
			}
		}catch (Exception e) {
			logger.error("�����û�ʧ��", e); 
			impTip.append("�����û�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return "";
	}
	
	/**
	 * �⾭ó�û�����
	 * @param ss1
	 * @param i
	 * @return 0.ʧ�� 1.�ɹ� 2.�������
	 * @throws ElException
	 * @throws ParseException 
	 */
	
	private static int wjm_checkImport(Sheet ss1,int i)throws ElException{  
			UserDaoImpl userDao = new UserDaoImpl();
			boolean username = true; //ѧ��0
			boolean pass = true; //����1
			boolean realname = true;	 //������2
			boolean userno = true;	 //Ӣ����3
			boolean xb = true;	 //�Ա�4
			boolean gj = true;//��5
			boolean xy = true;//ѧԺ6
			boolean zy = true;//רҵ7
			boolean nj = true;//�꼶8
			
			
			int is = 0;	 //����ֵ
			String xuehao = ss1.getCell(0, i).getContents().trim();//��ȡexcel�еĵ�1�У��û���
			//username=CheckCard.fixPersonIDCode(username.toLowerCase()).toLowerCase();
			if(xuehao == null || xuehao.equals("")){impTip.append("<br/> ѧ��Ϊ��! ");username = false;	}
//			if(username.length() < 2){impTip.append("<br/> ����������6λ! ");name = false;}else 
			if(userDao.checkUsername(xuehao)){impTip.append("'"+xuehao+"'��ѧ���Ѵ���!");username = false;}
			
			String zhongwen = ss1.getCell(2, i).getContents().trim(); 
			if(zhongwen == null || zhongwen.equals("")){
				impTip.append("<br/>��������Ϊ��! "); 
				realname = false;
			}
			String yingwen = ss1.getCell(3, i).getContents().trim(); 
			if(yingwen == null || yingwen.equals("")){
				impTip.append("<br/>Ӣ������Ϊ��! "); 
				userno = false;
			}
			
			String sex = ss1.getCell(4, i).getContents().trim(); 
			if(sex == null || sex.equals("")){
				impTip.append("<br/>�Ա���Ϊ��! "); 
				xb = false;
			}
			
			String guoji = ss1.getCell(5, i).getContents().trim(); 
			if(guoji == null || guoji.equals("")){
				impTip.append("<br/>����Ϊ��! "); 
				gj = false;
			}
			
			DepartmentDaoImpl depDao = new DepartmentDaoImpl();
			String depno = ss1.getCell(6, i).getContents().trim(); 
			if(depno == null || depno.equals("")){
				impTip.append("<br/>ѧԺ��Ų���Ϊ��! "); 
				xy = false;
			}
			if(!depDao.checkDepBh(depno)){
				impTip.append("<br/>ѧԺ��Ų�����!");
				xy = false;
			}
			
			String zhuanye = ss1.getCell(7, i).getContents().trim(); 
			if(zhuanye == null || zhuanye.equals("")){
				impTip.append("<br/>רҵ����Ϊ��! "); 
				zy = false;
			}
			
			String nianji = ss1.getCell(8, i).getContents().trim(); 
			if(nianji == null || nianji.equals("")){
				impTip.append("<br/>�꼶����Ϊ��! "); 
				nj = false;
			}
//			String password = ss1.getCell(1, i).getContents().trim();
//			if(password == null || password.equals("")){impTip.append("<br/> ���벻��Ϊ��! "); pass = false;} else
//			if(password.length() < 6){impTip.append("<br/> ���벻������6λ! ");pass = false;}
//			 �� ��
			
//			String xuhao = ss1.getCell(2, i).getContents().trim();
//			if(xuhao == null||xuhao.equals("")){impTip.append("<br/> ��Ų���Ϊ��! "); xh = false;}
			// ���
			
			
//			boolean isExist15 = false;
//			boolean isExist18 = false;
			
			
		
			if(username&&pass&&realname&&userno&&xb&&gj&&xy&&zy&&nj){
				is = 1;//�ɹ�
			}else
			if(!username&&!pass&&!realname&&!userno&&!xb&&!gj&&!xy&&!zy&&!nj){//ȫ��Ϊfalse֤���������
				is = 2;//�������
			}else{
				is = 0;//ʧ��
			}
		return is;
	}
	
	public static String wjm_writeUser(File source) throws ElException { 
		UserDaoImpl userDao = new UserDaoImpl();
		BaseDatat base = new BaseDatat();
		DepartmentDaoImpl depDao = new DepartmentDaoImpl();
		StationDaoImpl staDao = new StationDaoImpl();
//		String Return = "";
		impTip = new StringBuffer();
		jxl.Workbook rwb = null;
		try {
			//����ǰ�ȳ�ʼ��Щ�����
			Map<String, BaseDatat> bdMap=initBasedatat();
			InputStream is = new FileInputStream(source);
			rwb = Workbook.getWorkbook(is);
			Sheet ss[] = rwb.getSheets();
			if (null != ss && ss.length > 0) {
				Sheet ss1 = ss[0];
				for (int i = 1; i < ss1.getRows(); i++) {
					//impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");	
					//int ck = checkImport(ss1, i);
					//ѧԱ�����Ƿ���Ҫ��֤
					int ck=0;
					impTip.append("����ʧ��, ʧ��ԭ���� : ��"+i+"��");
					//if(SystemConfOp.getBooleanValue(ElConstants.SYSTEM_CONF_USERIMP_ISCHECK)){
						ck= wjm_checkImport(ss1, i);
					//}else{
					//	ck= checkImport_noall3(ss1, i);
					//}
					if(ck == 0){//ʧ��
//						Return = "false";
						//break;
						continue;
 					}else if(ck == 1){//�ɹ�
 						impTip.setLength(0);
 					}else if(ck == 2){//�������
 						impTip.setLength(0);
 						impTip.append("true"); 
						break;
 					}
					ELUser eu = new ELUser();
					String username = ss1.getCell(0, i).getContents().trim();
					eu.setUsername(username);
					// �û���
					if(!userDao.checkUsername(eu.getUsername())){//�����û�����ڣ�ֱ�ӵ��룬�������дԭ��¼
						String password = ss1.getCell(1, i).getContents().trim();
						eu.setPassword(password == null
								|| "".equals(password.trim()) ?MD5.crypt("111111")
								: MD5.crypt(password));
						// �� ��
						eu.setRealname(ss1.getCell(2, i).getContents().trim());
						// ������
						
						String yingwenming = ss1.getCell(3, i).getContents().trim();
						eu.setUserno(yingwenming);//Ӣ����
						
						eu.setSex(ss1.getCell(4, i).getContents().trim());// �Ա�
						
						eu.setDanwei(ss1.getCell(5, i).getContents().trim());//��
						
						String depname = ss1.getCell(6, i).getContents().trim();
					//	depname = depname.replace("-", "");
						int depparent = 1;// getDep(suozaidanwei, 1); 
						depparent = depDao.getDepByBH(depname).getId();
						if (depparent == 0){
							//depparent = 1;
							//�Ҳ�����ŵ�ʱ�򲻵���
							continue;
						}
						eu.setDepartment(new Department(depparent));
						
						eu.setSpecialty(ss1.getCell(7, i).getContents().trim());//רҵ
					
						eu.setSchool(ss1.getCell(8, i).getContents().trim());//�꼶
						
						eu.setRole(new ElRole(4));
						
						eu.setStation(new Station(16371));//ע���û�station
						eu.setXianzhiwei("ְλ");//��ְλdep
						eu.setValid(true);
						eu.setJingzhong(81);
						eu.setZhiwu(82);
						eu.setZhiji(83);
						if(!userDao.checkUsername(eu.getUsername())){
							userDao.insert_cisco(eu);
						}else{
							userDao.update_wjm(eu);
						}
						
					}
					
//					Return = "true";
				} 
			}
		} catch (Exception e) {
			logger.error("�����˺�ʧ��", e); 
			impTip.append("�����˺�ʧ��,��鿴��־�ļ�!");
			//throw new ElException(e);
			return new String(impTip);
		}finally{
			if(rwb!=null){
				rwb.close();
			}
		}
		return new String(impTip);
	}
}
