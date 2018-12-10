#CURL
cURL是一个利用URL语法在命令行下工作的文件传输工具，1997年首次发行。它支持文件上传和下载，所以是综合传输工具，但按传统，习惯称cURL为下载工具。cURL还包含了用于程序开发的libcurl。

##如何安装调试curl
见如下百度经验
'https://jingyan.baidu.com/article/a681b0dec4c67a3b1943467c.html'

打开安装curl的目录下的 bin 在其中shift+右键 在此处打开命令行 即可使用curl命令了

##curl常用参数

-v 详细输出，包含请求和响应的首部

-o test 将指定curl返回保存为test文件，内容从html/jpg到各种MIME类型文件

-O  把输出写到该文件中，保留远程文件的文件名

-C 在保存文件时进行续传

-x  ip:port 指定使用的http代理

-c <file> 保存服务器的cookie文件

-H <header:value>  为HTTP请求设置任意header及值

-L 跟随重定向

-S 显示错误信息

-s 静默模式，不输出任何信息

-G 以get的方式发送数据

-f  连接失败是不显示http错误

-d 以post方式传送数据


##curl 调用示例
```
infura查询最近的区块number

 curl -X POST -H "Content-Type:application/json;charset=UTF-8" --data "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"eth_blockNumber\",\"params\":[]}" "https://mainnet.infura.io/v3/503f328f3e104b87aa3cbb77c0d1205a"
```
