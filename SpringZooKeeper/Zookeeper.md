# Zookeeper

## 学习目标

![image-20200213152603839](Zookeeper.assets/image-20200213152603839.png)

## 为什么使用Zookeeper

![image-20200213174641640](Zookeeper.assets/image-20200213174641640.png)

ZooKeeper是一个分布式的，开源的分布式应用程序协调服务，从设计模式角度来理解的话就是一个基于观察者模式设计的分布式服务管理框架。即管理着一些数据，这些数据发生变化的时候要给观察者提供响应。

目前，大部分应用需要开发私有的一个主控、协调器或控制器的协调程序来管理物理分布的子进程（如资源、任务分配等）。而协调程序的反复编写浪费，且难以形成通用、伸缩性好的协调器,所以zookeeper应用而生。

它是一个为分布式应用提供一致性服务的软件，提供的功能包括：统一命名服务，统一配置管理，统一集群管理，服务节点动态上下线，软负载均衡等。

简而言之：有了zookeeper，分布式应用在其上进行注册即可实现集群中的主从管理模式。



## Zookeeper中的内部原理

### 选举机制

半数机制：集群中半数以上机器alive则集群可用。所以 zookeeper更适合装在奇数台机器上（容忍度：奇数台和偶数台机器的容忍度是一样的。3:1,4:1）。并且不同于其他框架的主从机制，zookeeper并没有在配置文件中界定leader和follower的具体对象，而是在集群启动的时候通过内部选举的方式临时产生一个leader。

Server一共有如下三种状态：

- `LOOKING`：当前Server不知道leader是谁，正在搜寻
- `LEADING`：当前Server即为选举出来的leader
- `FOLLOWING`：leader已经选举出来，当前Server与之同步![image-20200213153856899](Zookeeper.assets/image-20200213153856899.png)具体leader如何产生看下面这个例子：

1. 服务器 1 启动，此时只有它一台服务器启动了，它发出去的报没有任何响应，所以它的选举状态只能是搜寻其他响应即Looking状态。
2. 服务器 2 启动，它与最开始启动的服务器 1 进行通信，互相交换自己的选举结果，由于两者都没有历史数据即所谓zxid，所以 myid 值较大的服务器 2 胜出，但是由于没有达到超过半数以上（3个）的服务器都同意选举它，所以服务器 1、2 还是继续保持LOOKING 状态。
3. 服务器 3 启动，根据前面的理论分析，服务器 3 成为服务器 1、2、3 中的老大，而与上面不同的是，此时有三台服务器选举了它，所以它成为了这次选举的临时leader。
4. 服务器 4 启动，根据前面的分析，理论上服务器 4 应该是服务器 1、2、3、4 中最大的，但是由于前面已经有半数以上的服务器选举了服务器 3，所以他也只能当follower了。
5. 服务器 5 启动，同 4 一样当follower了。

总结两句话：打铁还需自身硬，把握时机很重要。

### paxos算法

详见[Zookeeper——Paxos作为灵魂](./Zookeeper——Paxos作为灵魂.md)

### 角色

![image-20200213155309561](Zookeeper.assets/image-20200213155309561.png)

![image-20200213160310739](Zookeeper.assets/image-20200213160310739.png)

### 广播机制

1.首先每个zkServer在内存中存储了一份数据（小）；

2.Zookeeper启动时，将从实例中选举一个leader（Paxos协议）

3.Leader负责处理数据更新等操作

4.一个更新操作成功时机当且仅当大多数Server在内存中成功修改数据。

Zookeeper的核心是原子广播，这个机制保证了各个server之间的同步。实现这个机制的协议叫做Zab协议。

Zab协议有两种模式，它们分别是**恢复模式**和**广播模式**。

当服务启动或者在领导者崩溃后，Zab就进入了恢复模式，当领导者被选举出来，且大多数server的完成了和leader的状态同步以后，恢复模式就结束了。状态同步保证了leader和server具有相同的系统状态。一旦leader已经和多数的follower进行了状态同步后，他就可以开始广播消息了，即进入广播状态。这时候当一个server加入zookeeper服务中，它会在恢复模式下启动，发现leader，并和leader进行状态同步。待到同步结束，它也参与消息广播。Zookeeper服务一直维持在Broadcast状态，直到leader崩溃了或者leader失去了大部分的followers支持。

广播模式需要保证proposal被按顺序处理，因此zk采用了递增的事务id号(zxid)来保证。所有的提议(proposal)都在被提出的时候加上了zxid。实现中zxid是一个64位的数字，它高32位是epoch用来标识leader关系是否改变，每次一个leader被选出来，它都会有一个新的epoch。低32位是个递增计数。  

当leader崩溃或者leader失去大多数的follower，这时候zk进入恢复模式，恢复模式需要重新选举出一个新的leader，让所有的server都恢复到一个正确的状态。

###  特点

|    特点    | 说明                                                         |
| :--------: | ------------------------------------------------------------ |
| 最终一致性 | 为客户端展示同一个视图，这是zookeeper里面一个非常重要的功能。 |
|   可靠性   | 如果消息被到一台服务器接受，那么它将被所有的服务器接受。     |
|   实时性   | 在一定时间范围内，client 能读到最新数据，如果需要最新数据，应该在读数据之前调用         sync()接口。 |
|   独立性   | 各个Client之间互不干预                                       |
|   原子性   | 更新只能成功或者失败，没有中间状态。                         |
|   顺序性   | 所有Server，同一消息发布顺序一致。                           |

这就是欲攘外必先安内，只有自己内部消息安全可靠（`选举模式`选出leader才能对外服务），在对外提供服务的时候才能有一个统一的视图即能对外提供正确的服务（`广播模式`完成消息的同步）。节点故障也不担心，只需要满足过半机制也不会出现`脑裂问题`。

在zookeeper这样一个主从模型之下，leader和follower之间也组成一写多读。即Leader 负责进行投票的发起和决议，更新系统状态而Follower 用于接收客户请求并向客户端返回结果，并在选举 Leader 过程中参与投票。

## Znode--数据模型

![image-20200213170253058](Zookeeper.assets/image-20200213170253058.png)

zookeeper的目录结构与 Unix 文件系统很类似，整体上可以看作是一棵树，这一个树形结构由 zookeeper 集群自身维护，其上的每一个节点，我们称之为"znode"，各自的路径作为节点的唯一标识。

### znode的类型

#### 短暂（ephemeral）

客户端和服务器端断开连接后，创建的节点znode自动删除。

#### 持久（persistent）

客户端和服务器端断开连接后，创建的节点znode不会自动删除。

## Zookeeper的下载与安装

### 下载地址

官网首页：

http://zookeeper.apache.org/

下载截图

![image-20200213171210175](Zookeeper.assets/image-20200213171210175.png)

### 安装

#### 准备工作

1. 安装jdk
2. 将zookeeper的tar包通过xftp上传至linux服务器
3. 解压到指定目录

#### 本地模式

##### 配置文件修改

解压安装好后对配置文件进行修改

将$ZOOKEEPER_HOME/conf目录下的zoo_sample.cfg修改为zoo.cfg；

```shell
cp zoo_sample.cfg zoo.cfg
vi zoo.cfg
```

将内容修改成如下即可

```shell
# The number of milliseconds of each tick 
tickTime=2000 
# The number of ticks that the initial # synchronization phase can take 
initLimit=10 
# The number of ticks that can pass between 
# sending a request and getting an acknowledgement 
syncLimit=5 
# the directory where the snapshot is stored. 
# do not use /tmp for storage, /tmp here is just 
# example sakes. 
dataDir=/usr/local/zookeeper/apache-zookeeper-3.5.6-bin/data 
dataLogDir=/usr/local/zookeeper/apache-zookeeper-3.5.6-bin/log 
# the port at which the clients will connect 
clientPort=2181 
# the maximum number of client connections. 
# increase this if you need to handle more clients 
#
# Be sure to read the maintenance section of the 
# administrator guide before turning on autopurge. 
#
#http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_ma intenance 
#
# The number of snapshots to retain in dataDir 
#autopurge.snapRetainCount=3 
# Purge task interval in hours 
# Set to "0" to disable auto purge feature 
#autopurge.purgeInterval=1 23242526272829
```

dataDir和dataLogDir所对应的路径符合自己需要即可。

##### 启动

```shell
cd /usr/local/zookeeper/apache-zookeeper-3.5.6-bin/bin
./zkServer.sh start
```

![image-20200213221111371](Zookeeper.assets/image-20200213221111371.png)

通过命令查看状态可以看到是standalone模式。

#### 集群模式

##### 配置文件修改

不同于本地模式的配置文件修改，内容如下

```shell
tickTime=2000  
initLimit=5
syncLimit=2
dataDir=/usr/local/zookeeper/apache-zookeeper-3.5.6-bin/data 
dataLogDir=/usr/local/zookeeper/apache-zookeeper-3.5.6-bin/log
clientPort=2181
server.1=server1:2888:3888
server.2=server2:2888:3888
server.3=server3:2888:3888  observer
```

如果说配置文件如上，说明你的服务器一共有三台，myid分别对应是1,2,3并且myid为3的机器是观察者即不参与投票。配置文件修改成上述的内容后在对应机器的dataDir路径下创建文件名为myid的文件，将对应的myid（1,2,3）键入到文件中即可。文件中的内容只需要是一个数字就行。

##### 启动

启动方式和本地模式启动方式相同，但现在是集群模式所以我们对应的zk服务器都要进行启动。

#### 配置参数解释

- tickTime：发送心跳的间隔时间，单位：毫秒
- dataDir：zookeeper保存数据的目录。
- clientPort：客户端连接 Zookeeper 服务器的端口，Zookeeper  会监听这个端口，接受客户端的访问请求。
- initLimit： 这个配置项是用来配置 Zookeeper 接受客户端（这里所说的客户端不是用户连接Zookeeper服务器的客户端，而是 Zookeeper 服务器集群中连接到 Leader的Follower 服务器）初始化连接时最长能忍受多少个心跳时间间隔数。当已经超过 5 个心跳的时间（也就是 tickTime）长度后 Zookeeper 服务器还没有收到客户端的返回信息，那么表明这个客户端连接失败。总的时间长度就是 5*2000=10秒
- syncLimit：这个配置项标识 Leader 与 Follower 之间发送消息，请求和应答时间长度，最长不能超过多少个tickTime 的时间长度，总的时间长度就是 2*2000=4 秒
- server.A=B：C：D：其 中 A 是一个数字即myid，表示这个是第几号服务器；B 是这个服务器的ip地址；C 表示的是这个服务器与集群中的Leader服务器交换信息的端口；D表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新的Leader，而这个端口就是用来执行选举时服务器相互通信的端口。

## Zookeeper操作实战

详见代码

## 总结

 Zookeeper 作为 Hadoop 项目中的一个子项目，是Hadoop 集群管理的一个必不可少的模块，它主要用来控制集群中的数据，如它管理 Hadoop 集群中的NameNode，还有 Hbase 中 Master、 Server 之间状态同步等。

  Zoopkeeper 提供了一套很好的分布式集群管理的机制，就是它这种基于层次型的目录树的数据结构，并对树中的节点进行有效管理，从而可以设计出多种多样的分布式的数据管理模型。

