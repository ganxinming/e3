<h3>serach的服务层：提供搜索和一键将数据导入solr索引库</h3>

功能：  
service：  
LoginServiceImpl：用户名密码正确，则生成token存入redis。
RegisterServiceImpl：1.验证数据2.完成注册
TokenServiceImpl：根据token验证用户登录