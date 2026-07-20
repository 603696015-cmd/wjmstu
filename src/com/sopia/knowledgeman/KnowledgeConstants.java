package com.sopia.knowledgeman;

public class KnowledgeConstants {
	public static final String KLTYPE_QUERY_BYID = "kltype.query.byid";

	public static final String QUERY_KLTYPE_CHILD_BYID = "select id,name,description,parentid from knowledgetype where parentid = ? order by id";

	public static final String INSERT_KLTYPE = "insert into knowledgetype(name,description,parentid,manager,isshared) values(?,?,?,?,?)";

	public static final String INSERT_KLTYPE_DEP = "insert into kltype_dep(kltypeid,depid) values(?,?)";

	public static final String CHECK_KLTYPE_DEP = "select * from kltype_dep where kltypeid = ? and depid = ?";

	public static final String KNOWLEDGE_MY_LIST = "knowledge.my.list";
	public static final String KNOWLEDGE_MY_LIST_SIZE = "knowledge.my.list.size";
	public static final String KNOWLEDGE_LIST_BYDEP = "knowledge.list.bydep";
	public static final String KNOWLEDGE_LIST_BYDEP_SUB = "knowledge.list.bydep.sub";
	public static final String KNOWLEDGE_LIST_BYDEPT = "knowledge.list.bydept";
	public static final String KNOWLEDGE_LIST_BYDEPT_SUB = "knowledge.list.bydept.sub";
	public static final String KNOWLEDGE_LIST_BYDEP_SIZE = "knowledge.list.bydep.size";
	public static final String KNOWLEDGE_LIST_BYDEP_SUB_SIZE = "knowledge.list.bydep.sub.size";
	public static final String KNOWLEDGE_LIST_BYDEPT_SIZE = "knowledge.list.bydept.size";
	public static final String KNOWLEDGE_LIST_BYDEPT_SUB_SIZE = "knowledge.list.bydept.sub.size";
	public static final String KNOWLEDGE_LIST_BYHOT = "knowledge.list.byhot";
	public static final String KNOWLEDGE_LIST_BYREADTIME = "knowledge.list.byreadtime";
	public static final String KNOWLEDGE_LIST_BYTITLE = "knowledge.list.bytitle";
	public static final String KNOWLEDGE_LIST_BYTITLE_SIZE = "knowledge.list.bytitle.size";
	public static final String KNOWLEDGE_READTIME_SET="knowledge.readtime.set";
	public static final String KNOWLEDGE_HOT_SET="knowledge.hot.set";
	public static final String KNOWLEDGE_LIST_BYNOTYPE = "knowledge.list.bynotype";
	public static final String KNOWLEDGE_LIST_BYNOTYPE_SIZE = "knowledge.list.bynotype.size";
	public static final String KNOWLEDGE_LIST_BYTYPE = "knowledge.list.bytype";
	public static final String KNOWLEDGE_LIST_BYTYPE_SIZE = "knowledge.list.bytype.size";
	public static final String KNOWLEDGE_SH_LIST = "knowledge.sh.list";
	public static final String KNOWLEDGE_SHM_LIST = "knowledge.shm.list";
	
	public static final String KNOWLEDGE_BYID = "knowledge.byid";
	public static final String KNOWLEDGE_ADD = "knowledge.add";
	public static final String KNOWLEDGE_ALTER = "knowledge.alter";

	public static final String QUERY_KLTYPE_BYDEPID = "kltype.query.bydepid";

	public static final String QUERY_DEP_BYKLTYPEID = "select kd.depid,dep.name from kltype_dep kd left join department dep on kd.depid = dep.id  where kltypeid=? ";

	public static final String ALTER_KLTYPE = "update knowledgetype set name=?,description= ?,parentid = ?,manager=?,isshared=? where id = ?";
 
//	public static final String DELTET_DEP_BYKLTYPEID = "delete from kltype_dep where kltypeid = ?";
	public static final String DELTET_DEP_BYKLTYPEID = "delete from knowledgeType where id = ?";

	public static final String KNOWLEDGE_DELETE="knowledge.delete";
	
}
