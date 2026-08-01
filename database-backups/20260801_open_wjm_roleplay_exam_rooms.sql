-- 商务汉语自学自测系统：恢复角色扮演（做练习）考场的开放时间。
-- 适用范围：第 73 至 120 单元（课程 ID 893 至 940）的 177 个练习考场。
-- 该脚本已为本机 Oracle 测试库准备，可重复执行；首次执行会保留原始时间备份。

DECLARE
  v_backup_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_backup_count
    FROM user_tables
   WHERE table_name = 'WJM_RP_EROOM_BAK_260801';

  IF v_backup_count = 0 THEN
    EXECUTE IMMEDIATE '
      CREATE TABLE WJM_RP_EROOM_BAK_260801 AS
      SELECT id, begintime, endtime
        FROM exam_room
       WHERE title = ''做练习''
         AND courseid BETWEEN 893 AND 940
         AND endtime < SYSDATE';
  END IF;
END;
/

UPDATE exam_room
   SET begintime = DATE '2012-03-06',
       endtime = DATE '2099-12-31'
 WHERE title = '做练习'
   AND courseid BETWEEN 893 AND 940
   AND endtime < SYSDATE;

COMMIT;

-- 验证：应返回 0，表示不存在已过期的商务汉语角色扮演练习考场。
SELECT COUNT(*) AS expired_roleplay_rooms
  FROM exam_room
 WHERE title = '做练习'
   AND courseid BETWEEN 893 AND 940
   AND endtime < SYSDATE;

-- 如需恢复导入库中的原始时间，执行以下语句后提交：
-- UPDATE exam_room er
--    SET (begintime, endtime) = (
--      SELECT bak.begintime, bak.endtime
--        FROM WJM_RP_EROOM_BAK_260801 bak
--       WHERE bak.id = er.id)
--  WHERE EXISTS (
--      SELECT 1 FROM WJM_RP_EROOM_BAK_260801 bak
--       WHERE bak.id = er.id);
-- COMMIT;
