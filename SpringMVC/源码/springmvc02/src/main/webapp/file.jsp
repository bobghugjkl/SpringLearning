<%--
  Created by IntelliJ IDEA.
  User: lp
  Date: 2020/2/15
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="uploadFile.do" method="post" enctype="multipart/form-data">
    <input type="file" name="file" />
    <button type="submit"> 提交</button>
</form>
</body>
</html>
