/**
* 用于单双层IFrame的情况
*/
function resizeAndNotifyParentFrame(parentFrame,grandfatherFrame){
  autoResizeIFrame(parentFrame);
  notifyParentFrame(grandfatherFrame);
}
/**
* 用于单三层IFrame的情况
*/
function resizeAndNotifyParentParentFrame(parentFrame,grandfatherFrame,grandfatherfatherFrame){
  resizeAndNotifyParentFrame(parentFrame,grandfatherFrame);
  notifyParentParentFrame(grandfatherfatherFrame);
}
/**
* 用于单四层IFrame的情况
*/
function resizeAndNotifyParentParentParentFrame(parentFrame,grandfatherFrame,grandfatherfatherFrame,grandfatherfatherfatherFrame){
	resizeAndNotifyParentParentFrame(parentFrame,grandfatherFrame,grandfatherfatherFrame)
  resizeAndNotifyParentFrame(parentFrame,grandfatherFrame);
  notifyParentParentFrame(grandfatherfatherfatherFrame);
}
/**
* 第2层
*/
function notifyParentFrame(frameName){
  // 检查明细页面的
  if(parent == null)
    return;

  var wfFrame = parent.parent;
  if(wfFrame != null){
    if(wfFrame.document.all(frameName) != null){
      wfFrame.document.all(frameName).style.height=parent.document.body.scrollHeight;
    }
  }
}

/**
* 第3层
*/
function notifyParentParentFrame(frameName){
  // 检查明细页面的
  if(parent == null)
    return;

  var wfFrame = parent.parent.parent;
  if(wfFrame != null){
    if(wfFrame.document.all(frameName) != null){
      wfFrame.document.all(frameName).style.height=parent.parent.document.body.scrollHeight;
    }
  }
}
/**
* 第4层
*/
function notifyParentParentParentFrame(frameName){
  // 检查明细页面的
  if(parent == null)
    return;

  var wfFrame = parent.parent.parent.parent;
  if(wfFrame != null){
    if(wfFrame.document.all(frameName) != null){
      wfFrame.document.all(frameName).style.height=parent.parent.parent.document.body.scrollHeight;
    }
  }
}
/**
* 第1层
*/
/**
* 用于单IFrame的情况
*/
function autoResizeIFrame(frameName){
  if(parent != null){
    if(parent.document.all(frameName) != null){
      parent.document.all(frameName).style.height=document.body.scrollHeight;
    }
  }
}