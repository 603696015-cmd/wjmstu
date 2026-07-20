package com.sopia.courseman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.dao.ErblockDao;
import com.sopia.courseman.entities.ErepBlock;
import com.sopia.courseman.entities.EroomBlock;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;

public class ErblockDaoImpl implements ErblockDao {
	private static final Log logger = LogFactory.getLog(ErblockDaoImpl.class);
	public void addErblock(EroomBlock erblock) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into erblock( title, description,creater,roomid ) values(?,?,?,?)");
			ps.setString(1, erblock.getName());
			ps.setString(2, erblock.getDescription());
			ps.setInt(3, erblock.getCreater().getId());
			ps.setInt(4, erblock.getEroom().getId());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('erblock') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select erblock_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				erblock.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加考场试卷模块失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	public void alterErblock(EroomBlock erblock) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update erblock set title = ? , description =? ,roomid=? where id= ?");
			ps.setString(1, erblock.getName());
			ps.setString(2, erblock.getDescription());
			ps.setInt(3, erblock.getEroom().getId());
			ps.setInt(4, erblock.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public EroomBlock getErblock(int bid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		EroomBlock eb = new EroomBlock();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eb.id ebid,eb.title,eb.description ,eb.creater,eu.realname,er.id,er.title from erblock eb " +
					"left join eluser eu on eu.id = eb.creater left join exam_room er on er.id = eb.roomid where eb.id= ?");
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			if (rs.next()) {
				eb.setId(rs.getInt(1));
				eb.setTitle(rs.getString(2));
				eb.setDescription(rs.getString(3));
				eb.setCreater(new ELUser(rs.getInt(4),rs.getString(5)));
				eb.setEroom(new ExamRoom(rs.getInt(6),rs.getString(7)));
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eb;
	}
	public EroomBlock deleteErblock(int bid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		EroomBlock eb = new EroomBlock();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from erblock where id= ?");
			ps.setInt(1, bid);
			ps.executeUpdate();	
			ps.close();
			ps = null;
			ps = ct.prepareStatement("delete from erblock_epbs where erblockid= ?");
			ps.setInt(1, bid);
			ps.executeUpdate();	
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eb;
	}
	public List<ExamPaperBlock> listErepblocks(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock>  epb = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select epb.id,epb.title,epb.type,ep.id,ep.title from exam_reps er,exampaperblock epb ,exampaper ep where roomid = ? and er.epid = epb.exampaperid and epb.exampaperid=ep.id and er.status = 0 order by epb.type");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock eb = new ExamPaperBlock(rs.getInt(1),rs.getString(2));
				eb.setType(rs.getInt(3));
				eb.setExamPaper(new ExamPaper(rs.getInt(4),rs.getString(5)));
				epb.add(eb);
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epb;
	}
	public void addErepblock(ErepBlock erBlock) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into ERBLOCK_EPBS( title, blockids,blocktitles,erblockid) values(?,?,?,?)");
			ps.setString(1, erBlock.getTitle());
			ps.setString(2, erBlock.getBlockids());
			ps.setString(3, erBlock.getBlocktitles());
			ps.setInt(4, erBlock.getErblock().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加考场试卷模块失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void alterErepblock(ErepBlock erBlock) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update ERBLOCK_EPBS set title=?, blockids=?,blocktitles=? where id = ?");
			ps.setString(1, erBlock.getTitle());
			ps.setString(2, erBlock.getBlockids());
			ps.setString(3, erBlock.getBlocktitles());
			ps.setInt(4, erBlock.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加考场试卷模块失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void deleteErepblock(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from ERBLOCK_EPBS where id = ?");
			ps.setInt(1,id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加考场试卷模块失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public List<ErepBlock> listErepblock(int erbid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ErepBlock>  epb = new ArrayList<ErepBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,title,blockids,blocktitles from erblock_epbs where erblockid = ? order by id");
			ps.setInt(1, erbid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ErepBlock eb = new ErepBlock(rs.getInt(1),rs.getString(2));
				eb.setBlockids(rs.getString(3));
				eb.setBlocktitles(rs.getString(4));
				epb.add(eb);
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epb;
	}
}
