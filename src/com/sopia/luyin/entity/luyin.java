package com.sopia.luyin.entity;

public class luyin {

	  private int id;
	  private int userId;
	  private String account;
	  private String depName;
	  private String realName;
	  private int count;
	  private String recordType;
	  
	  public int getId()
	  {
	    return this.id;
	  }
	  
	  public void setId(int id)
	  {
	    this.id = id;
	  }
	  
	  public int getCount()
	  {
	    return this.count;
	  }
	  
	  public void setCount(int count)
	  {
	    this.count = count;
	  }
	  
	  public String getRecordType()
	  {
	    return this.recordType;
	  }
	  
	  public void setRecordType(String recordType)
	  {
	    this.recordType = recordType;
	  }
	  
	  public int getUserId()
	  {
	    return this.userId;
	  }
	  
	  public void setUserId(int userId)
	  {
	    this.userId = userId;
	  }
	  
	  public String getAccount()
	  {
	    return this.account;
	  }
	  
	  public void setAccount(String account)
	  {
	    this.account = account;
	  }
	  
	  public String getDepName()
	  {
	    return this.depName;
	  }
	  
	  public void setDepName(String depName)
	  {
	    this.depName = depName;
	  }
	  
	  public String getRealName()
	  {
	    return this.realName;
	  }
	  
	  public void setRealName(String realName)
	  {
	    this.realName = realName;
	  }
}
