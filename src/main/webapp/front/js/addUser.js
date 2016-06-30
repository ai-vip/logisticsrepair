/**
 * Created by Chen on 2016/5/15.
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

$(document).ready(
	
);

$("#save").click(function() {

	var userName = document.getElementById('userName').value;
	var password = document.getElementById('password').value;
	var usrMobile = document.getElementById('phone').value;
	var address = document.getElementById('address').value;
	var groupId = document.getElementById('categorySelect').value;
	
	var url="/user/addUser";
	
	$.get(url, {
		userName : userName,
		password : password,
		usrMobile : usrMobile,
		address : address,
		groupId : groupId
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