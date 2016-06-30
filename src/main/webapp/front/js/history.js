// 判断登录
var isLogin = false;
$.get("/user/checkLogin", function(data, status) {
	if (status == 'success') {
		if (data.code == 200) {
			isLogin = true;
			$("#login-button").hide();
		} else {
			location.href = "login.html";
		}
	}
});

$(document).ready(function(){
		//搜索订单
		$.get("/repairOrder/getMyRepairOrderList", 
			function(data, status) {
				var repairOrderList=data.response.repairOrderList;
				$("#historyOrderList").empty();
				for(i=0;i<repairOrderList.length;i++){
					var html=toHtml(repairOrderList[i]);
					$("#historyOrderList").append(html);
					//初始化星星
					$(".rating").rating({'size':'2'});
				}
			});
		//搜索订单
		
});
//完成订单
function complete(id){
	$.get("/repairOrder/completeOrder",{
		orderId:id
	}, function(data, status) {
		if(status=='success'){
			if(data.code==200){
				location.reload();
			}else{
				alert(data.message.error);
			}
		}else{
			alert("请求失败");
		}
	});
	

}
//评论
function comment(orderId){
	var rat=$("#order"+orderId).val();
	console.log(rat);
	$.get("/repairOrder/commentOrder",{
		orderId:orderId,
		star:rat,
		comment:""
	}, function(data, status) {
		if(status=='success'){
			if(data.code==200){
				alert("评价完成");
				location.reload();
			}else{
				alert(data.message.error);
			}
		}else{
			alert("请求失败");
		}
	});
}
//修改维修单
function updateOrder(orderId){
	location.href = "update.html?orderId="+orderId;
}


function toHtml(order){
	var t=new Date(order.createTime);
	var date=t.Format("yyyy-MM-dd hh:mm:ss");
	
	var status="haha";
	switch(order.status){
	case 1:status='未受理';break;
	case 2:status='已受理';break;
	case 3:status='已完成';break;
	case 4:status='已评价';break;
	default:status='无状态信息';
	}
	var html="";
	html+='<div class="panel panel-info">';
    html+='<div class="panel-heading">';
    html+='<h3 class="panel-title"><i class="iconfont"></i>';
    html+=order.title+'<span>'+date+'</span></h3>  </div>';
    html+='<div class="panel-body">';
    html+=order.des+'</div>';
    html+=' <div class="panel_status"><p class="text-right"><i class="iconfont"></i>'+status+'</p></div> <div class="panel-footer clearfix"> ';
    if(order.status==1){
    	html+=' <div class="col-md-3"> <button class="btn btn-primary"  onclick=updateOrder('+order.id+')>修改维修单</button> </div> ';
    }
    if(order.status==2){
    	//受理后，暂不允许修改
    	//html+=' <div class="col-md-3"> <button class="btn btn-primary"  onclick=updateOrder('+order.id+')>修改维修单</button> </div> ';
    	html+=' <div class="col-md-3"> <button class="btn btn-primary"  onclick=complete('+order.id+')>确认完成</button> </div> ';
    }
    if(order.status==3){
    	html+='<div class="col-md-3"><input id="order'+order.id+'" value="4" type="number" class="rating" min=0 max=5 step=0.2 data-size="gly"> </div> <div class="col-md"> <button class="btn btn-primary"  onclick=comment('+order.id+')>评分</button> </div> ';
    }
    html+='</div></div>';
    
    return html;
}
	
	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	// 例子： 
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}