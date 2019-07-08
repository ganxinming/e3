$(function () {
    var _ticket = $.cookie("token");
    alert("token是"+_ticket);
    if(!_ticket){
      return ;
    }
    $.ajax({
      url : "http://localhost:8088/user/token/" + _ticket,
      dataType : "jsonp",
      type : "GET",
      success : function(data){
        if(data.status == 200){
          var username = data.data.username;
          var html = username + "，欢迎来到宜立方购物网！<a href=\"http://www.e3mall.cn/user/logout.html\" class=\"link-logout\">[退出]</a>";
          $("#loginbar").html(html);
        }
      }
    });
  });