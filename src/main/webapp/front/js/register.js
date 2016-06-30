/**
 * Created by Chen on 2016/5/15.
 */

$(function(){
  document.getElementById("register").onclick=function(e){
    e.preventDefault();
    var ac=document.getElementById("inputEmail1").value;
    var pw=document.getElementById("inputPassword1").value;
    var pw2=document.getElementById("confirmPassword1").value;
    var datas={
      userName:ac,
      password:pw,
      password2:pw2
    };
    var req=new XMLHttpRequest();
    req.open('get','/user/singIn?'+$.param(datas),true);
    req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    req.onload=function(){
      if (this.status >= 200 && this.status < 400) {
        // Success!
        var resp = JSON.parse(this.response);
        if(resp.code==200){
          location.assign('login.html');
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
