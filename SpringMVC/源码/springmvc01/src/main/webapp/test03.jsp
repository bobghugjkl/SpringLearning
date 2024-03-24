<%--
  Created by IntelliJ IDEA.
  User: lp
  Date: 2020/2/14
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>


    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        function test1(){
            $.ajax({
                type:"post",
                url:"user/queryUser03.do",
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                data:'{"userName":"admin","userPwd":"123456"}',
                success:function(data){
                    alert(data.userName+"--"+data.userPwd);
                }
            })
        }
    </script>
</head>
<body>
<input type="button" value="请求响应json" onclick="test1()"/>
</body>
</html>
