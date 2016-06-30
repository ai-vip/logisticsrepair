/**
 * Created by Chen on 2016/5/6.
 */
$(function(){
  $("select").select2({dropdownCssClass: 'dropdown-inverse'});
});

//判断登录
var isLogin=false;
$.get("/user/checkLogin", function(data, status) {
	if(status=='success'){
		if(data.code==200){
			isLogin=true;
			$("#login-button").hide();
		}else{
			 //location.href ="login.html";
		}
	}
});

//退出
$("#exit").click(function(){
	$.get("/user/logout");
	 location.href ="index.html";//location.href实现客户端页面的跳转  
});

//搜索
$("#searchButton").click(function(){
	//搜索订单
	$.get("/repairOrder/queryRepairOrderList", {
		title:$("#orderName").val(),
		timeRange:$("#timeSelect").val(),
		status:$("#statusSelect").val()
		},
		function(data, status) {
			var repairOrderList=data.response.repairOrderList;
			$("#indexOrderList").empty();
			for(i=0;i<repairOrderList.length;i++){
				var html=toHtml(repairOrderList[i]);
				$("#indexOrderList").append(html);
			}
			
		});
	var orderName=$("#orderName").val();
	var timeRange=$("#timeSelect").val();
	var status=$("#statusSelect").val();
	var pageNo=null;
	var pageSize=null;
	searchOrder($("#orderName").val(),$("#timeSelect").val(),$("#statusSelect").val(),pageNo,pageSize)
	//搜索订单
});

$(document).ready(function(){
	
	var categoryHtml="";
	//获取首页信息
	$.get("/index/getIndexInfo", function(data, status) {
		 //最新公告
		 $("#placard").text(data.response.lastedPlacard.content);
		//最新公告
	});
		var orderName=getQueryString("orderName");
		//搜索订单
		searchOrder(orderName,"","","","");
		//搜索订单
	
});
function searchOrder(orderName,timeRange,status,pageNo,pageSize){
	if(pageSize==null||pageSize==''){
		pageSize='5';
	}
	//搜索订单
	$.get("/repairOrder/queryRepairOrderList", {
		title:orderName,
		timeRange:timeRange,
		status:status,
		pageNo:pageNo,
		pageSize:pageSize
		},
		function(data, status) {
			var repairOrderList=data.response.repairOrderList;
			$("#indexOrderList").empty();
			for(i=0;i<repairOrderList.length;i++){
				var html=toHtml(repairOrderList[i]);
				$("#indexOrderList").append(html);
			}
			
			var pageNo=data.response.page.pageNo;
			var totalCounts=data.response.page.totalCounts;
			totalPage=data.response.page.totalPage;
			var a=Math.floor(totalPage/10);
	
			
			//页码链接
			$(".pageLi").remove();
			for(j=10;j>0;j--){
				var pageRange=Math.floor(pageNo/10);
				var temp=pageRange*10+j;
				if(temp<totalPage){
					$("#previous").after('<li class="pageLi"><a href="#" onclick="toPage('+temp+')" >'+temp+'</a></li>');
				}
			}
			//页码范围链接
			$("#pageRanges").empty();
			for(i=0;i<=a;i++){
				$("#pageRanges").append('<li><a href="#" onclick="toPageRange('+i+')">'+i*10+'–'+(i*10+10)+'</a></li>');
			}
			
		});
	//搜索订单

	
}

function toPage(pageNo){
	searchOrder("","","",pageNo,"");
}
function toPageRange(pageRange){
	//页码范围链接
	$(".pageLi").remove();
	for(j=10;j>0;j--){
		var temp=pageRange*10+j;
		if(temp<totalPage){
			$("#previous").after('<li class="pageLi"><a href="#" onclick="toPage('+temp+')" >'+temp+'</a></li>');
		}
	}
}


//tools
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
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
        html+=' <div class="panel_status"><p class="text-right"><i class="iconfont"></i>'+status+'</p></div> </div>';
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