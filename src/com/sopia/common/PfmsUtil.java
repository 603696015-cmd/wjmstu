package com.sopia.common;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.pfms.dao.DamageMemberDao;
import com.sopia.pfms.entities.DamageMember;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.impl.DamageMemberDaoImpl;
import com.sopia.pfms.impl.IndexDaoImpl;

public class PfmsUtil {
	private static final Log logger = LogFactory.getLog(PfmsUtil.class);
	

	public  List<DamageMember> writeDamageMembers(File batchImport,String batchImportFileName){
		DamageMemberDaoImpl damageMemberDao = new DamageMemberDaoImpl();
		List<DamageMember> damageMemberList = new ArrayList<DamageMember>();
		DamageMember dm = new DamageMember();
		Workbook book = null;
		try {
			FileInputStream in = new FileInputStream(batchImport);
			book = Workbook.getWorkbook(in);
			in.close();
			Sheet ss[] = book.getSheets();
			if(null != ss && ss.length>0){
				Sheet ss1 = ss[0];
				for(int i=0;i<ss1.getRows();i++){
					if(i==0)
						continue;
					String name = ss1.getCell(0, i).getContents();
					String sex = ss1.getCell(1, i).getContents();
					String personId = ss1.getCell(2, i).getContents();
					String birthday = ss1.getCell(3, i).getContents();
					String workCompany = ss1.getCell(4, i).getContents();
					String hometown = ss1.getCell(5, i).getContents();
					String picture = ss1.getCell(6, i).getContents();
					
					dm.setName(name);
					dm.setSex(sex);
					dm.setPersonId(personId);
					dm.setBirthday(getDate(birthday));
					dm.setWorkCompany(workCompany);
					dm.setHometown(hometown);
					dm.setPicture(picture);
					damageMemberDao.addDamageMember(dm);
					damageMemberList.add(dm);
					
				}
			}
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return damageMemberList;
	}
	
	public List<PfmsUser> writePfmsUsers(File batchImport,String batchImportFileName,int depid){
		IndexDaoImpl indexDao = new IndexDaoImpl();
		List<PfmsUser> pfmsUserList = new ArrayList<PfmsUser>();
		PfmsUser pfmsUser = new PfmsUser();
		ELUser elUser = new ELUser();
		Workbook book = null;
		try {
			FileInputStream in = new FileInputStream(batchImport);
			book = Workbook.getWorkbook(in);
			in.close();
			Sheet ss[] = book.getSheets();
			if(null != ss && ss.length>0){
				Sheet ss1 = ss[0];
				for(int i=0;i<ss1.getRows();i++){
					if(i==0)
						continue;
					String username = ss1.getCell(0, i).getContents();
					String password = ss1.getCell(1, i).getContents();
					String roleid = ss1.getCell(2, i).getContents();
					String realname = ss1.getCell(3, i).getContents();
					String danwei = ss1.getCell(4, i).getContents();
					String sex = ss1.getCell(5, i).getContents();
					String shenfenzheng = ss1.getCell(6, i).getContents();
					String movephone = ss1.getCell(7, i).getContents();
					
					String head = ss1.getCell(8, i).getContents();
					String huiyuanleixing = ss1.getCell(9, i).getContents();
					String province_city_county = ss1.getCell(10, i).getContents();
					String respName = ss1.getCell(11, i).getContents();
					String address = ss1.getCell(12, i).getContents();
					String mobile = ss1.getCell(13, i).getContents();
					String fex = ss1.getCell(14, i).getContents();
					String email = ss1.getCell(15, i).getContents();
					
					ElRole role = new ElRole();
					role.setId(Integer.parseInt(roleid));
					
					elUser.setUsername(username);
					elUser.setPassword(password);
					
					elUser.setRole(role);
					elUser.setRealname(realname);
					elUser.setDanwei(danwei);
					elUser.setSex(sex);
					elUser.setShenfenzheng(shenfenzheng);
					elUser.setMovephone(movephone);
					
					Department d = new Department();
					d.setId(depid);
					elUser.setDepartment(d);
					
					pfmsUser.setHead(head);
					pfmsUser.setRespName(respName);
					pfmsUser.setAddress(address);
					pfmsUser.setMobile(mobile);
					pfmsUser.setFex(fex);
					pfmsUser.setEmail(email);
					pfmsUser.setHuiyuanleixing(huiyuanleixing);
					pfmsUser.setProvince_city_county(province_city_county);
					
					pfmsUser.setUser(elUser);
					
					indexDao.addPfmsUser(pfmsUser,depid);
					pfmsUserList.add(pfmsUser);
					
				}
			}
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return pfmsUserList;
	}
	
	public static Timestamp getDate(String birthday) {
		SimpleDateFormat sdf = 	new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
		try { 
			date = sdf.parse(birthday);
			sqlDate = new java.sql.Timestamp(date.getTime());
			System.out.println(sqlDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	public String toProvince_city_county(String province,String city,String county){
		if(province.split(" ").length>1 && city.split(" ").length>1 && county.split(" ").length>1){
			return province.split(" ")[1] + " " + city.split(" ")[1] + " " + county.split(" ")[1];
		}
		return province + " " + city + " " + county;
	}
	
	
	
	public String[] changeStringToStringArray(String str){
		if(!str.equals(""))
			return str.contains(" ")? str.split(" "):null;
		String[] s = new String[0];
		s[0] = "";
		return s;
	}
	
	
	public String changeToString(String[] array){
		String str = "";
		for(int i=0;i<array.length;i++){
			if(i == array.length - 1){
				str += array[i];
			}else{
				if(!array[i].equals("")){
					str += array[i]+" ";
				}
			}
		}
		return str;
	}
	

}
