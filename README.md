# shortlink

这是我学习的一个项目：

短链接（Short Link）是指将一个原始的长 URL（Uniform Resource Locator）通过特定的算法或服务转化为一个更短、易于记忆的 URL。短链接通常只包含几个字符，而原始的长 URL 可能会非常长。

短链接的原理非常简单，通过一个原始链接生成个相对短的链接，然后通过访问短链接跳转到原始链接。

如果更细节一些的话，那就是：

1. **生成唯一标识符**：当用户输入或提交一个长 URL 时，短链接服务会生成一个唯一的标识符或者短码。
2. **将标识符与长 URL 关联**：短链接服务将这个唯一标识符与用户提供的长 URL 关联起来，并将其保存在数据库或者其他持久化存储中。
3. **创建短链接**：将生成的唯一标识符加上短链接服务的域名（例如：[http://nurl.ink](https://gitee.com/link?target=http%3A%2F%2Fnurl.ink) ）作为前缀，构成一个短链接。
4. **重定向**：当用户访问该短链接时，短链接服务接收到请求后会根据唯一标识符查找关联的长 URL，然后将用户重定向到这个长 URL。
5. **跟踪统计**：一些短链接服务还会提供访问统计和分析功能，记录访问量、来源、地理位置等信息。

短链接经常出现在咱们日常生活中，大家总是能在某些活动节日里收到各种营销短信，里边就会出现短链接。帮助企业在营销活动中，识别用户行为、点击率等关键信息监控。

还需要学习的知识点有：

1、mybatis-plus  数据库操作

2、redission，包括分布式锁、布隆过滤器

3、shardingsphere，分库分表组件

4、transmittable-thread-local，是一个线程安全的 ThreadLocal 框架，地址为：https://github.com/alibaba/transmittable-thread-local

1. 启动redis
```shell
redis-cli
exit
```
2. 启动kafka
```shell
cd /
cd /usr/local/kafka_2.12-2.8.0
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
./bin/kafka-server-start.sh ./config/server.properties
tail -f logs/server.log
```
