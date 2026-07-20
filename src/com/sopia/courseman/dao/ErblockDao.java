package com.sopia.courseman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.ErepBlock;
import com.sopia.courseman.entities.EroomBlock;
import com.sopia.questionman.entities.ExamPaperBlock;

/**
 * @author闻益舜
 *
 */
public interface ErblockDao {
	/**添加模块
	 * @param erblock
	 * @throws ElException
	 */
	public void addErblock(EroomBlock erblock) throws ElException;
	
	/**修改模块
	 * @param erblock
	 * @throws ElException
	 */
	public void alterErblock(EroomBlock erblock) throws ElException;
	
	/**获取模块
	 * @param bid
	 * @return
	 * @throws ElException
	 */
	public EroomBlock getErblock(int bid)throws ElException;
	/**考场模块删除
	 * @param bid
	 * @return
	 * @throws ElException
	 */
	public EroomBlock deleteErblock(int bid)throws ElException;
	
	/**
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaperBlock> listErepblocks(int roomid)throws ElException;

	/**增加模块设置
	 * @param erBlock
	 * @throws ElException
	 */
	public void addErepblock(ErepBlock erBlock) throws ElException;
	
	/**修改模块设置
	 * @param erBlock
	 * @throws ElException
	 */
	public void alterErepblock(ErepBlock erBlock) throws ElException;
	
	/**删除模块设置
	 * @param id
	 * @throws ElException
	 */
	public void deleteErepblock(int id)throws ElException;
	
	/**获取模块设置列表
	 * @param erbid
	 * @return
	 * @throws ElException
	 */
	public List<ErepBlock> listErepblock(int erbid)throws ElException;
}
