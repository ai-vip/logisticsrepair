

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
	
	
	var categoryHtml="";
	$.get("/userInfo/getUserInfo", function(data, status) {
		if(status="success"){
			 if(data.code==200){
				 var user=data.response.userInfo;
				 	$("#userName").val(user.trueName);
					$("#phone").val(user.phone);
					$("#address").val(user.address);
		        }else{
		          alert(resp.message.error);
		        };
			
		}
	});
	

	
});

$(function(){
  document.getElementById("save").onclick=function(){
    var userName=document.getElementById('userName').value;
    var phone=document.getElementById('phone').value;
    var address= document.getElementById('address').value;
    var datas={
    		trueName:userName,
    		phone:phone,
    		address:address
    };
    var req=new XMLHttpRequest();
    req.open("get","/userInfo/updateUserInfo?"+$.param(datas),true);
    req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    req.onload=function(){
      if (this.status >= 200 && this.status < 400) {
        // Success!
        var resp = JSON.parse(this.response);
        if(resp.code==200){
          location.reload();
        }else{
          alert(resp.message.error);
        };

      } else {
        // We reached our target server, but it returned an error

      }
    };
    req.send();
  };
});
