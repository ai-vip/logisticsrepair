/**
 * Created by Chen on 2016/5/15.
 */
$(function() {
	$("select").select2({
		dropdownCssClass : 'dropdown-inverse'
	});
});
// 获取url参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null){
		return unescape(r[2]);
	}
		
	return null;
}
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

$(document).ready(
		function() {
			var orderId=getQueryString("orderId");
			var order;
			//获取订单信息
			var url="/repairOrder/getOrder";
			$.get(url, {
				orderId:orderId,
			}, function(data, status) {

				if (status = 'success') {
					if (data.code == 200) {
						order=data.response.repairOrder;
						$("#title").val(order.title);
						$("#des").val(order.des);
						$("#phone").val(order.phone);
						$("#address").val(order.address);
						
						
					} else {
						alert(data.message.error);
					}
					;

				} else {
					// We reached our target server, but it returned an error

				}

			});
			
			// 获取维修分类
			$.get("/repairCategory/getRepairCategoryList", function(data,
					status) {
				var repairCategoryList = data.response.repairCategoryList;
				// 清空
				$("#categorySelect").empty();
				// 增加
				for (var i = 0; i < repairCategoryList.length; i++) {
					var cur = repairCategoryList[i];
					console.log(cur);
					
					$("#categorySelect").append(
							"<option  value='" + cur.id + "'>" + cur.name
									+ "</option>");
				}
				//选中
				

				$("#categorySelect").val(order.categoryId);
				$("#categorySelect").find("option[text='"+order.categoryId+"']").attr("selected","selected");
				$("#categorySelect").select(order.categoryId);
				$("#categorySelect").select2(order.categoryId);
				

			});
			// 获取维修分类

		});

$("#save").click(function() {
	var orderId=getQueryString("orderId");
	var title = document.getElementById('title').value;
	var des = document.getElementById('des').value;
	var phone = document.getElementById('phone').value;
	var address = document.getElementById('address').value;
	var categoryId = document.getElementById('categorySelect').value;
	
	var url="/repairOrder/updateOrder";
	
	
	$.get(url, {
		orderId:orderId,
		title : title,
		des : des,
		phone : phone,
		address : address,
		categoryId : categoryId
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