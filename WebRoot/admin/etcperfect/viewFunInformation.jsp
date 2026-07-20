<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>

</HEAD>

<body>
<center><table border="1" cellspacing="0" cellpadding="0" width='80%'>
  <tr>
    <td width="100%" colspan="2" valign="top">
    	<span style='color:red'>
    		功能菜单由功能代码和参数组合而成。 
    		<br />
      		自定义添加的模块，各功能菜单的功能代码都一样，参数不一样。 
      	</span>
    </td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>添加数据页 </p></td>
    <td width="469" valign="top"><p>addContactTagsInit </p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>我添加列表页 </p></td>
    <td width="469" valign="top"><p>myContactTags </p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>初审列表页 </p></td>
    <td width="469" valign="top"><p>searchContactTags </p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>终审列表页 </p></td>
    <td width="469" valign="top"><p>finalsearchContactTags </p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>自定义审核设置 </p></td>
    <td width="469" valign="top"><p>customAuditManageInit</p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>自定义审核列表页 </p></td>
    <td width="469" valign="top"><p>customAuditListContactTags</p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>公共查询列表页 </p></td>
    <td width="469" valign="top"><p>myPassSearchContactTags </p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>数据申请 </p></td>
    <td width="469" valign="top"><p>dataApplicationInit </p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>数据分配 </p></td>
    <td width="469" valign="top"><p>dataAllocationInit </p></td>
  </tr>
  <tr>
    <td width="476" valign="top"><p>我获得分配的数据 </p></td>
    <td width="469" valign="top"><p>myGetDataAllocationInit </p></td>
  </tr>
</table>
</center>
</body>
</html>
