-- 商务汉语教师端菜单收敛（第一批）
-- 目标：仅隐藏“信息中心”及其下新闻、知识库、论坛、资料采集、问卷等通用平台入口。
-- 范围：仅超级管理员角色（ELROLE.ID = 1）；不删除 ELFUNC 定义、业务数据或源码。
-- 恢复：执行本文末尾的恢复语句。

CREATE TABLE ELROLEFUNC_WJM_HIDDEN_20260730 AS
SELECT rf.*
  FROM ELROLEFUNC rf
 WHERE rf.ROLEID = 1
   AND rf.FUNCID IN (
       SELECT id
         FROM ELFUNC
        START WITH id = 953
      CONNECT BY PRIOR id = parentid
   );

DELETE FROM ELROLEFUNC
 WHERE ROLEID = 1
   AND FUNCID IN (
       SELECT id
         FROM ELFUNC
        START WITH id = 953
      CONNECT BY PRIOR id = parentid
   );

COMMIT;

-- 验证：应返回 0，表示该角色不再拥有信息中心菜单。
SELECT COUNT(*) AS REMAINING_INFO_PERMS
  FROM ELROLEFUNC
 WHERE ROLEID = 1
   AND FUNCID IN (
       SELECT id
         FROM ELFUNC
        START WITH id = 953
      CONNECT BY PRIOR id = parentid
   );

-- 恢复（如需回滚，执行以下语句）：
-- INSERT INTO ELROLEFUNC (ROLEID, FUNCID)
-- SELECT ROLEID, FUNCID
--   FROM ELROLEFUNC_WJM_HIDDEN_20260730 b
--  WHERE NOT EXISTS (
--      SELECT 1 FROM ELROLEFUNC rf
--       WHERE rf.ROLEID = b.ROLEID AND rf.FUNCID = b.FUNCID
--  );
-- COMMIT;
