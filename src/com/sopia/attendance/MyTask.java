package com.sopia.attendance;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.sopia.attendance.dao.AttendanceDao;
import com.sopia.attendance.dao.impl.AttendanceDaoImpl;
import com.sopia.common.ElException;

public class MyTask extends TimerTask {
	
	public MyTask() {

	}

	@Override
	public void run() {
		try {
			new AttendanceDaoImpl().insertWorkAttendance();
		} catch (ElException e) {
			e.printStackTrace();
		}
	}


}
