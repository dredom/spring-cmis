<html>
<head>
 <title>News</title>
</head>
<body>
 <h3>English news</h3>
 <img src="img/newsroom.jpg">
 <#if bean.type == "news">
  <p> <#include "news/static-header.ftl"> </p>
 </#if>
 <div class="page">
  <#include "news/article.ftl" parse=true>
  <span>${bean.dynamicContent}</span>
 </div>
 <h4>~0~</h4>
</body>
</html>