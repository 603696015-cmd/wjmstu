package com.sopia.courseman.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.ErblockDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.ErepBlock;
import com.sopia.courseman.entities.EroomBlock;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaperBlock;

public class EroomBlockAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(EroomBlockAction.class);
	private EroomBlock erblock;
	private ErblockDao erblockDao;
	private ExamRoom examRoom;
	private EroomDao eroomDao ;
	private List<ExamPaperBlock> epblocks;
	private List<ErepBlock> erepblocks;
	private ErepBlock erepblock ;

	public List<ExamPaperBlock> getEpblocks() {
		return epblocks;
	}

	public void setEpblocks(List<ExamPaperBlock> epblocks) {
		this.epblocks = epblocks;
	}

	public EroomDao getEroomDao() {
		return eroomDao;
	}

	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public ErblockDao getErblockDao() {
		return erblockDao;
	}

	public void setErblockDao(ErblockDao erblockDao) {
		this.erblockDao = erblockDao;
	}

	/**模块添加初始化
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_addinit() throws ElException {

		return "eroom_block_add";
	}

	/**模块添加处理
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_add() throws ElException {
		erblock.setCreater(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		erblockDao.addErblock(erblock);
		if(erepblocks!=null){
			for (int i = 0; i < erepblocks.size(); i++) {
				ErepBlock eb = erepblocks.get(i);
				if(eb!=null){
					eb.setErblock(erblock);
					if(eb.getId()==0){
						erblockDao.addErepblock(eb);
					}
				}
			}
		}
		return "stat_eroom_block_list";
	}

	/**模块修改初始化
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_alterinit() throws ElException {
		erblock = erblockDao.getErblock(erblock.getId());
		erblock.setErepblocks(erblockDao.listErepblock(erblock.getId()));
		return "eroom_block_alter";
	}

	/**模块修改处理
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_alter() throws ElException {
		erblockDao.alterErblock(erblock);
		if(erepblocks!=null){
			for (int i = 0; i < erepblocks.size(); i++) {
				ErepBlock eb = erepblocks.get(i);
				if(eb!=null){
					eb.setErblock(erblock);
					if(eb.getId()!=0){
						erblockDao.alterErepblock(eb);
					}else
						erblockDao.addErepblock(eb);
				}
			}
		}
		return "stat_eroom_block_list";
	}
	/**模块设置删除
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_erblock_delete() throws ElException {
		erblockDao.deleteErepblock(erepblock.getId());
		return null;
	}
	/**模块删除
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_delete() throws ElException {
		erblockDao.deleteErblock(erblock.getId());
		return "stat_eroom_block_list";
	}
	/**获取考场
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_erview() throws ElException {
		///erblockDao.deleteErblock(erblock.getId());
		examRoom = eroomDao.getExamRoomByid(examRoom.getId());
		printMsg("{'id':"+examRoom.getId()+",'title':'"+examRoom.getTitle()+"'}");
		return null;
	}
	/**考场试卷大题列表
	 * @return
	 * @throws ElException
	 */
	public String eroom_block_eplist() throws ElException {
		epblocks = erblockDao.listErepblocks(examRoom.getId());
		return "eroom_block_eplist";
	}
	public EroomBlock getErblock() {
		return erblock;
	}

	public void setErblock(EroomBlock erblock) {
		this.erblock = erblock;
	}

	public List<ErepBlock> getErepblocks() {
		return erepblocks;
	}

	public void setErepblocks(List<ErepBlock> erepblocks) {
		this.erepblocks = erepblocks;
	}

	public ErepBlock getErepblock() {
		return erepblock;
	}

	public void setErepblock(ErepBlock erepblock) {
		this.erepblock = erepblock;
	}

}
