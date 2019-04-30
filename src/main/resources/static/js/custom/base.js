var cfs={
	/*Json 工具类*/
	isJson:function(str){
		var obj = null; 
		try{
			obj = cfs.paserJson(str);
		}catch(e){
			return false;
		}
		var result = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
		return result;
	},
	paserJson:function(str){
		return eval("("+str+")");
	},


	/*重新登录页面*/
	toLogin:function(){
		window.top.location= "/";
	},
	checkLogin:function(data){//检查是否登录超时
		if(data.logoutFlag){
            loginInValid();
			return false;
		}
		return true;
	},
	ajaxSubmit:function(form,option){
		form.ajaxSubmit(option);
	},
	ajaxPostJson: function(url,option,isAsync,callback,isLoding){
        if(isLoding&&!showlodingFn()){
            return;
        }else{
            $.ajax(url,{
                type:'post',
                dataType:'json',
                data:option,
                async:isAsync==false?isAsync:true,
                success:function(data){
                    //坚持登录
                    if(!cfs.checkLogin(data)){
                        return false;
                    }
                    if($.isFunction(callback)){
                    	if(isLoding){
                            setTimeout(function(){
                                callback(data);
                            },1000);
						}else{
                            callback(data);
						}
                    }
                },
                error:function(response, textStatus, errorThrown){
                    try{
                        var data = $.parseJSON(response.responseText);
                        //检查登录
                        if(!cfs.checkLogin(data)){
                            return false;
                        }else{
                            alertUseMsgFn( data.msg || "请求出现异常,请联系管理员");
                        }
                    }catch(e){
                        alertUseMsgFn("请求出现异常,请联系管理员.");
                    }
                },
                complete:function(){
                    closelodingFn();
                }
            });
		}

	},
	submitForm:function(form,callback,dataType){
			var option =
			{
			 	type:'post',
			 	dataType: dataType||'json',
			 	success:function(data){
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			var data = $.parseJSON(response.responseText);
				 		//检查登录
				 		if(!cfs.checkLogin(data)){
				 			return false;
				 		}else{
					 		alertUseMsgFn( data.msg || "请求出现异常,请联系管理员");
					 	}
			 		}catch(e){
			 			console.log(e);
			 			alertUseMsgFn("请求出现异常,请联系管理员.");
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
			 }
			 cfs.ajaxSubmit(form,option);
	},
	saveForm:function(form,callback){
		if(form.form('validate')){
			//ajax提交form
			cfs.submitForm(form,function(data){
				cfs.closeProgress();
			 	if(data.success){
			 		if(callback){
				       	callback(data);
				    }else{
			       		alertUseMsgFn('保存成功.');
			        } 
		        }else{
		       	   alertUseMsgFn(data.msg);
		        }
			});
		 }
	},
	addTab:function(_title,_url){
		var jq = top.jQuery; 
		var boxId = jq("#tab-box");
		console.log(boxId);
		if(boxId.tabs('exists',_title) ){
			var tab = boxId.tabs('getTab',_title);
			var index = boxId.tabs('getTabIndex',tab);
			boxId.tabs('select',index);
			if(tab && tab.find('iframe').length > 0){  
				 var _refresh_ifram = tab.find('iframe')[0];  
			     _refresh_ifram.contentWindow.location.href=_url;  
		    } 
		}else{		
			var _content ="<iframe scrolling='auto' frameborder='0' src='"+_url+"' style='width:100%; height:100%'></iframe>";
			boxId.tabs('add',{
				    title:_title,
				    content:_content,
				    closable:true});
		}
	}
}

