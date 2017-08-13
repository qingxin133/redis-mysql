# redis-mysql
#### Redis和Mysql数据一致性测试
#### 实现：
- 1.需要高一致性的数据，在写入的Mysql的时候，同步写入Redis。更新的数据的时候一样，同步更新。相当于是捆绑式的操作。
- 2.通过Mysql UDF来实现数据同步：http://www.cnblogs.com/zhxilin/archive/2016/09/30/5923671.html 没做。
