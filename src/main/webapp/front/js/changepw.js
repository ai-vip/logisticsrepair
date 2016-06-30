/**
 * Created by Chen on 2016/5/15.
 */

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

$(function() {
	document.getElementById("save").onclick = function() {
		var op = document.getElementById('oldPassword1').value, np1 = document
				.getElementById('newPassword1').value;
		np2 = document.getElementById('newPassword2').value;

		if (np1 != np2) {
			alert("确认密码错误");
		} else {
			var datas = {
				password : op,
				password2 : np1
			};
			var req = new XMLHttpRequest();
			req.open("get", "/user/changePassword?"
					+ $.param(datas), true);
			req.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded; charset=UTF-8');
			req.onload = function() {
				if (this.status >= 200 && this.status < 400) {
					// Success!
					var resp = JSON.parse(this.response);
					if (resp.code == 200) {
						location.reload();
					} else {
						alert(resp.message.error);
					}
					;

				} else {
					// We reached our target server, but it returned an error

				}
			};
			req.send();
		}
	};
});
