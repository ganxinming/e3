<h3>单点登录web层</h3>

功能：  
LoginController: 用户判断登录用户名密码是否正确，如果成功将，生成token放在cookie中。

RegitsterController:注册用户，包括校验数据是否已经存在的功能。

TokenController:根据token查询用户，是否登录。