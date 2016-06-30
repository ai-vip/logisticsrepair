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

// 退出
$("#exit").click(function() {
	$.get("/user/logout");
	location.href = "index.html";// location.href实现客户端页面的跳转
});

// 获取用户列表
$.get("/user/getAllUserList", function(data, status) {
	if (status == 'success') {
		if (data.code == 200) {
			var userList = data.response.userList;
			var html = "";
			for (i = 0; i < userList.length; i++) {
				html += toHtml(userList[i]);
			}
			$("#userTableBody").append(html);

		} else if (data.code == 500) {
			alert(data.message.error);
		}
	}
});
// 删除用户
function deleteUser(id) {
	if (confirm("非必要不建议删除用户,确认删除?")) {
		$.get("/user/removeUser", {
			userId:id
		},function(data, status) {
			if (status == 'success') {
				if (data.code == 200) {
					location.reload();
				} else if (data.code == 500) {
					alert(data.message.error);
				}
			}
		});
	}
}
function toHtml(user) {
	var group = "";
	switch (user.groupId) {
	case 1:
		group = "普通用户";
		break;
	case 2:
		group = "维修人员";
		break;
	case 3:
		group = "管理员";
		break;
	default:
		group = "默认用户";
		;
	}
	var html = "";
	html += " <tr>";
	html += " <td>" + user.usrId + "</td>";
	html += " <td>" + user.usrAccount + "</td>";
	html += " <td>" + user.usrMobile + "</td>";
	html += "<td>" + group + "</td>";
	html += "<td><a  href='#' onclick='deleteUser(" + user.usrId
			+ ")'>删除</a></td>";
	html += "</tr>";
	return html;
}
