/**
 * Created by Chen on 2016/5/6.
 */
$(function() {
	$("select").select2({
		dropdownCssClass : 'dropdown-inverse'
	});
});

// 判断登录
var isLogin = false;
$.get("/user/checkLogin", function(data, status) {
	if (status == 'success') {
		if (data.code == 200 && data.response.groupId==3) {
			isLogin = true;
			$("#login-button").hide();
		} else {
			location.href = "login.html";
		}
	}
});

// 添加公告
$("#save").click(function() {

	var title = document.getElementById('title').value;
	var content = document.getElementById('content').value;

	$.get("/placard/addPlacard", {
		title : title,
		content : content,
	}, function(data, status) {

		if (status = 'success') {
			// Success!
			var resp = data;
			if (resp.code == 200) {
				location.reload();
			} else {
				alert(resp.message.error);
			}
			;

		} else {
			// We reached our target server, but it returned an error

		}

	});

});

// 退出
$("#exit").click(function() {
	$.get("/user/logout");
	location.href = "index.html";// location.href实现客户端页面的跳转
});

// 删除tag
$(".tagsinput-primary").on('itemRemoved', function(event) {
	var tag = event.item.trim();
	console.log(event);
	// 删除数据库tag
	
		$.get("/repairCategory/removeRepairCategory", {
			name : tag
		}, function(data, status) {
			if (status = 'success') {
				// Success!
				var resp = data;
				if (resp.code == 200) {
					// location.reload();
				} else {
					alert(resp.message.error);
				}
			} else {
			}
		});
	

});
// 添加tag
$(".tagsinput-primary").on('itemAdded', function(event) {
	var tag = event.item.trim();
	console.log("-----------------------");
	console.log(event);
	
	// 添加数据库tag
		$.get("/repairCategory/addRepairCategory", {
			name : tag
		}, function(data, status) {
			if (status = 'success') {
				// Success!
				var resp = data;
				if (resp.code == 200) {
					// location.reload();
				} else {
					alert(resp.message.error);
				}
			} else {
			}
		});
	

});
var repairCategoryList;

$(document).ready(function() {
	// 初始化分类tag
	$.get("/repairCategory/getRepairCategoryList", {

	}, function(data, status) {

		if (status = 'success') {
			// Success!
			var resp = data;
			if (resp.code == 200) {
				repairCategoryList = data.response.repairCategoryList;
				$(".tagsinput-primary").tagsinput('')
				
				for (i = 0; i < repairCategoryList.length; i++) {
					cur = repairCategoryList[i];
					
					$(".tagsinput-primary").tagsinput('add', cur.name);
				}
				

			} else {
				alert(resp.message.error);
			}
		}
	});

	// 初始化分类tag

	var categoryHtml = "";
	$.get("/index/getIndexInfo", function(data, status) {
		// 获取维修分类
		/*
		 * var repairCategoryList=data.response.repairCategoryList;
		 * console.log(repairCategoryList); //清空 $("#category-ul").empty(); //增加
		 * for(var i=0;i<repairCategoryList.length;i++){ var cur =
		 * repairCategoryList[i]; console.log(cur); $("#category-ul").append("<option
		 * value='"+cur.id+"'>"+cur.name+"</option>"); } //选择第一个
		 * $("#categorySelect").val(repairCategoryList[0].id+"");
		 * //document.getElementById('categorySelect').value=repairCategoryList[0].id+"";
		 */// 获取维修分类
		// 最新公告
		$("#placard").text(data.response.lastedPlacard.content);
		// 最新公告
	});

});