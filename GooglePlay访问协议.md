## AppStore HTTP接口文档

### 服务器地址_BASEURL
	http://127.0.0.1:8090/-->服务器在手机上
	http://192.168.1.3:8080/GooglePlayServer/-->服务器在电脑上,直接ip访问
	http://10.0.2.2:8080/GooglePlayServer/-->服务器在电脑上,android模拟器访问
	http://10.0.3.2:8080/GooglePlayServer/-->服务器在电脑上,genymotion模拟器访问

### 主页接口
1. 请求方式: GET
2. URL:	服务器地址 + home
3. 请求参数：index(分页显示中的第几条，默认从0开始)
4. 分页条目总数是20

例子：
	http://localhost:8080/GooglePlayServer/home?index=0
	


### 应用页面接口
1. 请求方式: GET
2. URL:	服务器地址 + app
3. 请求参数：index(分页显示中的第几条，默认从0开始)

例子：
	http://localhost:8080/GooglePlayServer/app?index=0



### 游戏页面接口
1. 请求方式: GET
2. URL:	服务器地址 + game
3. 请求参数：index(分页显示中的第几条，默认从0开始)

例子：
	http://localhost:8080/GooglePlayServer/game?index=0


### 专题页面接口
1. 请求方式: GET
2. URL:	服务器地址 + subject
3. 请求参数：index(分页显示中的第几条，默认从0开始)

例子：
	http://localhost:8080/GooglePlayServer/subject?index=0


### 推荐页面接口
1. 请求方式: GET
2. URL:	服务器地址 + recommend
3. 请求参数：index(分页显示中的第几条，默认从0开始)

例子：
	http://localhost:8080/GooglePlayServer/recommend?index=0


### 分类页面接口
1. 请求方式: GET
2. URL:	服务器地址 + category
3. 请求参数：index(分页显示中的第几条，默认从0开始)

例子：
	http://localhost:8080/GooglePlayServer/category?index=0



### 排行页面接口
1. 请求方式: GET
2. URL:	服务器地址 + hot
3. 请求参数：index(分页显示中的第几条，默认从0开始)

例子：
	http://localhost:8080/GooglePlayServer/hot?index=0



### 下载接口
1. 请求方式: GET
2. URL:	服务器地址 + download
3. 请求参数：name(应用的downloadUrl名称)，range(从什么位置开始下载)

例子：
	http://localhost:8080/GooglePlayServer/download?name=&range=0
	
	http://localhost:8080/GooglePlayServer/download?name=app/com.itheima.www/com.itheima.www.apk&range=0

### 图片接口
1. 请求方式: GET
2. URL:	服务器地址 + image
3. 请求参数：name(下载的应用名称)，range(从什么位置开始下载)

例子：
	http://localhost:8080/GooglePlayServer/image?name=

### 详情页面接口
1. 请求方式: GET
2. URL:	服务器地址 + detail
3. 请求参数：packageName(分页显示中的第几条，默认从0开始)

例子：
	http://localhost:8080/GooglePlayServer/detail?packageName=