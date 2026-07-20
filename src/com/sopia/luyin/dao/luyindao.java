package com.sopia.luyin.dao;

import com.sopia.common.ElException;
import com.sopia.luyin.entity.luyin;
import java.util.List;

public abstract interface luyindao
{
  public abstract void addluyin(luyin paramluyin)
    throws ElException;
  
  public abstract List<luyin> serchLuYinData(int paramInt, String paramString)
    throws ElException;
  
  public abstract luyin getLuyinById(int paramInt)
    throws ElException;
}
