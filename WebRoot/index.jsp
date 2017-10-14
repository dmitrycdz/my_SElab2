<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
    <h1 align="center">作家作品查询</h1>
<div align = "center"><a href=Gotoadd><button type="button">添加图书</button></a></div>
    <center>
    <s:form action="Search">
        <s:textfield name="username" label="作家名"/><s:submit value="查询"/>
    </s:form>
    </center>
<hr />
<p align="right">陈德钊1150310317</p>
</body>
</html>