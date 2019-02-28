<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="ch">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
        <h3 class="text-muted">App.com</h3>
    </div>
    
    <table class="table">
    <thead>
    <tr>
        <th>Student-ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>go to</button></th>
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
    function myFunction() {
    	
    	window.location.href = "http://192.168.0.158:8196/home/table";
    }
</script>

</body>
</html>
