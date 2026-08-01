-- 商务汉语自学自测系统：补齐角色扮演阅读排序字段。
-- 原 Oracle 导入库缺少 READSORT，而角色扮演页面已依赖该字段排序。
-- 可重复执行：字段已存在时不做任何修改；既有试卷模块默认排序为 0。

DECLARE
  v_column_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_column_count
    FROM user_tab_columns
   WHERE table_name = 'EXAMPAPERBLOCK'
     AND column_name = 'READSORT';

  IF v_column_count = 0 THEN
    EXECUTE IMMEDIATE
      'ALTER TABLE EXAMPAPERBLOCK ADD (READSORT NUMBER DEFAULT 0 NOT NULL)';
  END IF;
END;
/

-- 验证：应返回 1，且已有记录的默认值为 0。
SELECT COUNT(*) AS readsort_column_count
  FROM user_tab_columns
 WHERE table_name = 'EXAMPAPERBLOCK'
   AND column_name = 'READSORT';

