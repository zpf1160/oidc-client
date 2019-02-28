<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/jumbotron-narrow.css">
    <link rel="stylesheet" type="text/css" href="/css/home.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery.growl.css"/>
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="/js/jquery.growl.js" type="text/javascript"></script>
</head>

<body>

<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li class="active" id="home"><a href="#">Home</a></li>
                <li id="qsLogoutBtn"><a href="#">Logout</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">风险平台</h3>
    </div>
    
    <table class="table">
    <thead>
    <tr>
        <th>风险案例编号</th>
        <th>风险案例名字</th>
        <th>风险案例详情</th>
        <th>跳转合同平台</button></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>001</td>
        <td>Rammohan </td>
        <td>Reddy</td>
        <td><button type="button" class="btn btn-default" onclick="myFunction()">go to</button></td>
    </tr>
    <tr>
        <td>002</td>
        <td>Smita</td>
        <td>Pallod</td>
        <td><button type="button" class="btn btn-default" onclick="myFunction()">go to</button></td>
    </tr>
    <tr>
        <td>003</td>
        <td>Rabindranath</td>
        <td>Sen</td>
        <td><button type="button" class="btn btn-default" onclick="myFunction()">go to</button></td>
    </tr>
    </tbody>
</table>
    
    
    

    <footer class="footer">
        <p> &copy; 2016 Company Inc</p>
    </footer>

</div>

<script type="text/javascript">
    
    $("#qsLogoutBtn").click(function(e) {
        e.preventDefault();
        $("#qsLogoutBtn").removeClass("active");
        $("#password-login").removeClass("active");
        $("#logout").addClass("active");
        // assumes we are not part of SSO so just logout of local session
        window.location = "${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}/signout-remote";
    });
    
    function myFunction() {
    	
    	window.location.href = "http://192.168.0.158:8196/home/table";
    }
</script>

</body>
</html>
