<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title><fmt:message key="welcome.title"/></title>
	<link rel="stylesheet" href="<c:url value="/styles/switch.css" />" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="<c:url value="/styles/switch-print.css" />" type="text/css" media="print">
	<!--[if lt IE 8]>
		<link rel="stylesheet" href="<c:url value="/styles/switch-ie.css" />" type="text/css" media="screen, projection">
	<![endif]-->
</head>
<body>
<div class="container">  
	<h1>
		<fmt:message key="welcome.title"/> SWITCH!
	</h1>
	<p>Variable msg = ${msg} </p>
    <table>
      <c:forEach var="line" items="${lines}">
        <tr>
          <td>${line.key}</td><td>${line.value}</td>
        </tr>
      </c:forEach>
    </table>
	<p>
		Locale = ${pageContext.response.locale}
	</p>
	<hr>	
	<ul>
		<li> <a href="?locale=en_us">us</a> |  <a href="?locale=en_gb">gb</a> | <a href="?locale=es_es">es</a> | <a href="?locale=de_de">de</a> </li>
	</ul>
	<img src="images/amy.jpg"/>
</div>
</body>
</html>