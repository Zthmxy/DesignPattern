# DesignPattern
[王峥老师的面向对象设计模式](https://time.geekbang.org/column/article/171767)

需求: 为每个接口提供鉴别服务, 当用户匹配上才可以查询接口

分析:

+ 匹配的方式 可以通过 userID 和 password 进行鉴别, 将用户的 password 保存在 mysql , redis , ... 跟 url 一起发送过来 进行鉴别
+ 密码发送是明文, 不安全 使用 MD5 / Sha 等加密方式形成 Token , 将发送过来的 Token , 与从数据库取出的 password 加密后的 Token 对比，如果一致访问通过。
+ 还是不安全, 可以进行 重放攻击。 在传过来的 url 中加上动态的变量, 例如 时间戳。这样每个 Token 可以有一个过期时间, 比如一分种



流程:

+ 将 url , userID , password, timestamp 拼接成一个字符串
+ 将字符串加密成 Token
+ 将 Token 放入到 url 中发送过来
+ 解析 url 得到 Token , userID 和 timestamp
+ 从存储中根据 userID 得到对应的 password
+ 判断 Token 时间是否过期
+ 验证两个 Token 是否匹配
