<h3>内容模块pom</h3>
包含interface和service层。
interface:没什么好讲的  
service:  
ContentServiceImpl:内容管理Service  
ContentCategoryServiceImpl:内容分类管理Service（进行节点增加和删除）  

conf:resource.properties作为redis缓存的key值（可以随便取，保持唯一就行）

spring:applicationContext-redis.xml配置jedisPool，并且注入到我们编写的工具类jedisClientPool中。