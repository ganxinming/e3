<h3>common公共层</h3>
功能:   
--jedis:提供连接redis的类，  
--pojo:   
EasyUIDataGridResult和EasyUITreeNode，
封装了特殊的对象格式，用于返回前端解析树形格式。  
SearchItem：查询商品需要展示的内容封装起来。  
SearchResult：每一次查询的List<SearchItem>，当前页数，总页数等  
--utils：  
 E3Result ：自己封装的类，包括json，jsonlist转换。和响应状态码的封装  
 FastDFSClient:fasdfs连接服务端的类。包括文件上传的方法等。  
 IDUtils:用于生成文件名，随机ID等。  
 JsonUtils：pojo和json的相互转换
 