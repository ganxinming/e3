
<h3>商品详情页面展示</h3>
功能：  
ItemController：根据商品id，查询商品和详细信息，存入request，前端展示。  
HtmlGenController: 用于测试Freemarker，模板生成.html

HtmlGenListener：消息队列监听器，当加入新的商品，自动生成相应的html。
Item：对tbItem的再次封装，增加getImages方法，获取第一张图片。
