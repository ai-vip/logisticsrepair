/**
 * Created by Chen on 2016/5/15.
 */


$(function(){
  var login=document.getElementById('login');
  login.onclick=function(e){
    e.preventDefault();
    var acname=document.getElementById('inputEmail1').value;
    var pw=document.getElementById('inputPassword1').value;
    var rememberMe=(document.getElementById('inputPassword1').value)?1:0;
    var datas={
      userName:acname,
      password:pw,
      rememberMe:rememberMe
    };
    var req=new XMLHttpRequest();
    req.open('get','/user/login?'+$.param(datas));
    req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    req.onload=function(){
      if (this.status >= 200 && this.status < 400) {
        // Success!
        var resp = JSON.parse(this.response);
        console.log(resp);
        if(resp.code==200){
        	
        	if(resp.response.user.groupId==1){
        		 location.assign('index.html');
        	}
        	if(resp.response.user.groupId==2){
       		 location.assign('repairindex.html');
        	}
        	if(resp.response.user.groupId==3){
          		 location.assign('platformedit.html');
           	}
         
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
