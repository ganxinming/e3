<h3>商城展示的表现层</h3>
功能：实现跳转，轮播图展示.  
技术：  


1.controller :跳转index页面，并通过resource.properties文件里的id，
作为查询轮播图的id。将查询出来的list存入model，前端取出。(轮播图技术)  
2.resource.properties :作为配置轮播图的id  
3.springmvc.xml ：配置dubbo调用服务.  
4.web.xml ：实现伪静态化 
5.<h4>jsonp带着token发送请求至sso验证登录:e3mallJsonp.js</h4>
