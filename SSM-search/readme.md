<h3>serach的服务层：提供搜索和一键将数据导入solr索引库</h3>

功能：  
service：  
--dao：这里不是用maybatis的mapper，因为没有跟数据库打交道，只是查询solr索引库，所以自己写了dao层代码.(根据查询的索引查询)搜索功能  
--map：这里用了mybatis，用于一键导入索引库.  
--service :SearchItemServiceImpl：一键导入所有  
          :SearchServiceImpl:搜索功能  

spring:applicationContext-solr.xml :配置solr的httpSolrServer类,用于连接solr 
     
test : 一些用来测试solr的例子。 