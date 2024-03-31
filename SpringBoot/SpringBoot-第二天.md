# SpringBoot_第二天

## 学习目标

![image-20200305102317815](images\image-20200305102317815.png)

## Mybatis整合&数据访问

​		使用SpringBoot开发企业项目时，持久层数据访问是前端页面数据展示的基础，SpringBoot支持市面上常见的关系库产品(Oracle,Mysql,SqlServer,DB2等)对应的相关持久层框架，当然除了对于关系库访问的支持，也支持当下众多的非关系库(Redis,Solr,MongoDB等)数据访问操作,这里主要介绍SpringBoot集成Mybatis并实现持久层数据基本增删改查操作。

### SpringBoot 整合Mybatis

#### 环境整合配置

- Idea 下创建Maven 普通工程 springboot_mybatis

<img src="images\image-20200301172804236.png" alt="image-20200301172804236" style="zoom:80%;" />

- pom.xml 添加核心依赖

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>

<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.2.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--
          mybatis 集成
        -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>2.1.1</version>
    </dependency>
    <!-- springboot分页插件 -->
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper-spring-boot-starter</artifactId>
        <version>1.2.13</version>
    </dependency>

    <!--mysql 驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
      
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
    <dependency>
        <groupId>com.mchange</groupId>
        <artifactId>c3p0</artifactId>
        <version>0.9.5.5</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>      
```
- application.yml 整合配置

```yml
## 端口号
server:
  port: 9999

## 数据源配置
spring:
  datasource:
    type: com.mchange.v2.c3p0.ComboPooledDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/springboot_mybatis?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: root

## mybatis 配置
mybatis:
  mapper-locations: classpath:/mappers/*.xml
  type-aliases-package: com.xxxx.springboot.vo
  configuration:
    ## 下划线转驼峰配置
    map-underscore-to-camel-case: true

## pageHelper
pagehelper:
  helper-dialect: mysql
  
#显示dao 执行sql语句
logging:
  level:
    com:
      xxxx:
        springboot:
          dao: debug
```

#### 源代码添加

- Dao层接口方法定义

com.xxxx.springboot.dao 包下创建UserDao.java 接口声明查询方法

```java
package com.xxxx.springboot.dao;

import com.xxxx.springboot.vo.User;

public interface UserMapper  {
	// 根据用户名查询用户记录
    User queryUserByUserName(String userName);
}
```

- SQL映射文件添加

  resources/mappers 目录下添加UserMapper.xml 配置查询statetment

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.xxxx.springboot.dao.UserMapper">
    <select id="queryUserByUserName" parameterType="string" resultType="com.xxxx.springboot.vo.User">
        select
        id,user_name,user_pwd
        from t_user
        where user_name=#{userName}
    </select>
</mapper>
```

- 添加service 、controller 对应代码

UserService.java

```java
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User queryUserByUserName(String userName){
        return userMapper.queryUserByUserName(userName);
    }
}
```

UserController.java

```java
@RestController
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("user/{userName}")
    public User queryUserByUserName(@PathVariable String userName){
        return userService.queryUserByUserName(userName);
    }
}
```

- 添加应用启动入口

```java
@SpringBootApplication
@MapperScan("com.xxxx.springboot.dao")
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
```

#### 启动测试

运行Starter  main方法,启动应用浏览器测试查询

<img src="images\image-20200303154921885.png" alt="image-20200303154921885" style="zoom:80%;" />

后端日志打印效果:

![image-20200305105914531](images\image-20200305105914531.png)

### SpringBoot数据访问操作

​	完成SpringBoot 与Mybatis 集成后，接下来以用户表为例实现一套用户模块基本数据维护。

#### 接口方法 & Sql映射文件

- UserDao 接口方法定义

UserDao 接口添加数据访问基本方法

```java
public interface UserMapper  {

    public User queryById(Integer id);

    User queryUserByUserName(String userName);

    public int save(User user);

    public int update(User user);

    public List<User> selectByParams(UserQuery userQuery);

}
```

- UserMapper.xml 映射文件配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.xxxx.springboot.dao.UserMapper">
    <select id="queryById" parameterType="int" resultType="com.xxxx.springboot.vo.User">
        select *
        from t_user
        where id = #{id,jdbcType=INTEGER}
    </select>

    <select id="queryUserByUserName" parameterType="string" resultType="com.xxxx.springboot.vo.User">
        select *
        from t_user
        where user_name=#{userName}
    </select>
    <insert id="save" parameterType="com.xxxx.springboot.vo.User" useGeneratedKeys="true" keyProperty="id">
        insert into t_user(id,user_name,user_pwd) values(#{id},#{userName},#{userPwd})
    </insert>
    <update id="update" parameterType="com.xxxx.springboot.vo.User">
        update t_user set user_name =#{userName},user_pwd=#{userPwd}
        where id = #{id}
    </update>
    <select id="selectByParams" parameterType="com.xxxx.springboot.query.UserQuery" resultType="com.xxxx.springboot.vo.User">
        select *
        from t_user
        <where>
            <if test="null !=userName and userName !=''">
                and user_name like concat('%',#{userName},'%')
            </if>
        </where>
    </select>
</mapper>
```

#### UserService.java方法实现

```java
public User queryUserByUserName(String userName){
    return userMapper.queryUserByUserName(userName);
}

public User queryUserByUserId(Integer userId){
    return userMapper.queryById(userId);
}

public void saveUser(User user) {
    AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空!");
    AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()),"用户密码不能为空!");
    User temp = userMapper.queryUserByUserName(user.getUserName());
    AssertUtil.isTrue(null != temp, "该用户已存在!");
    AssertUtil.isTrue(userMapper.save(user)<1,"用户记录添加失败!");
}

public void updateUser(User user) {
    AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空!");
    AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()),"用户密码不能为空!");
    User temp = userMapper.queryUserByUserName(user.getUserName());
    AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "该用户已存在!");
    AssertUtil.isTrue(userMapper.update(user)<1,"用户记录添加失败!");
}

public  void deleteUser(Integer id){
    AssertUtil.isTrue(null == id || null ==userMapper.queryById(id),"待删除记录不存在!");
    AssertUtil.isTrue(userMapper.delete(id)<1,"用户删除失败!");
}

public PageInfo<User> queryUserByParams(UserQuery userQuery){
    PageHelper.startPage(userQuery.getPageNum(),userQuery.getPageSize());
    return new PageInfo<User>(userMapper.selectByParams(userQuery));
}
```

#### UserController.java 接口方法

```java
@GetMapping("user/{userId}")
public User queryUserByUserId(@PathVariable  Integer userId){
    return userService.queryUserByUserId(userId);
}

@GetMapping("user/list")
public PageInfo<User> list(UserQuery userQuery){
    return userService.queryUserByParams(userQuery);
}

@PutMapping("user")
public ResultInfo saveUser(User user){
    ResultInfo resultInfo=new ResultInfo();
    try {
        userService.saveUser(user);
    } catch (ParamsException e) {
        e.printStackTrace();
        resultInfo.setCode(e.getCode());
        resultInfo.setMsg(e.getMsg());
    }catch (Exception e) {
        e.printStackTrace();
        resultInfo.setCode(300);
        resultInfo.setMsg("记录添加失败!");
    }
    return resultInfo;
}


@PostMapping("user")
public ResultInfo updateUser(User user){
    ResultInfo resultInfo=new ResultInfo();
    try {
        userService.updateUser(user);
    } catch (ParamsException e) {
        e.printStackTrace();
        resultInfo.setCode(e.getCode());
        resultInfo.setMsg(e.getMsg());
    }catch (Exception e) {
        e.printStackTrace();
        resultInfo.setCode(300);
        resultInfo.setMsg("记录更新失败!");
    }
    return resultInfo;
}


@DeleteMapping("user/{userId}")
public ResultInfo deleteUser(@PathVariable  Integer  userId){
    ResultInfo resultInfo=new ResultInfo();
    try {
        userService.deleteUser(userId);
    } catch (ParamsException e) {
        e.printStackTrace();
        resultInfo.setCode(e.getCode());
        resultInfo.setMsg(e.getMsg());
    }catch (Exception e) {
        e.printStackTrace();
        resultInfo.setCode(300);
        resultInfo.setMsg("记录删除失败!");
    }
    return resultInfo;
}
```

### PostMan 接口测试工具下载与使用

​		在企业web 应用开发中，对服务器端接口进行测试，通常借助接口测试工具，这里使用Postman 接口测试工具来对后台restful接口进行测试，Postman 工具下载地址: https://www.getpostman.com/apps 选中对应平台下载即可。

<img src="images\image-20200303162820483.png" alt="image-20200303162820483" style="zoom:50%;" />

下载安装后，启动Postman 根据后台接口地址发送响应请求即可对接口进行测试。

<img src="images\image-20200303163450461.png" alt="image-20200303163450461" style="zoom:67%;" />

## API 文档构建工具-Swagger2

​		由于Spring Boot能够快速开发、便捷部署等特性，通常在使用Spring Boot构建Restful 接口应用时考虑到多终端的原因，这些终端会共用很多底层业务逻辑，因此我们会抽象出这样一层来同时服务于多个移动端或者Web前端。对于不同的终端公用一套接口API时对于联调测试时就需要知道后端提供的接口Api 列表文档，对于服务端开发人员来说就需要编写接口文档，描述接口调用地址参数结果等，这里借助第三方构建工具Swagger2来实现Api文档生成功能。

### 环境整合配置

- pom.xml 依赖添加

```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.9.2</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>2.9.2</version>
</dependency>
```

- 配置类添加

```java
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xxxx.springboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("用户管理接口API文档参考")
                .version("1.0")
                .build();
    }
}
```

### Swagger2 常用注解说明

#### @Api

```java
@Api：用在请求的类上，说明该类的作用
    tags="说明该类的作用"
```

```java
@Api(tags="APP用户注册Controller")
```

#### @ApiOperation

```csharp
@ApiOperation："用在请求的方法上，说明方法的作用"
    value="说明方法的作用"
    notes="方法的备注说明"
```

```java
@ApiOperation(value="用户注册",notes="手机号、密码都是必输项，年龄随边填，但必须是数字")
```

#### @ApiImplicitParams

```dart
@ApiImplicitParams：用在请求的方法上，包含一组参数说明
    @ApiImplicitParam：用在 @ApiImplicitParams 注解中，指定一个请求参数的配置信息       
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值
```

```css
@ApiImplicitParams({
    @ApiImplicitParam(name="mobile",value="手机号",required=true,paramType="form"),
    @ApiImplicitParam(name="password",value="密码",required=true,paramType="form"),
    @ApiImplicitParam(name="age",value="年龄",required=true,paramType="form",dataType="Integer")
})
```

#### @ApiResponses

```dart
@ApiResponses：用于请求的方法上，表示一组响应
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类
```

```css
@ApiOperation(value = "select请求",notes = "多个参数，多种的查询参数类型")
@ApiResponses({
    @ApiResponse(code=400,message="请求参数没填好"),
    @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
})
```

#### @ApiModel

```dart
@ApiModel：用于响应类上，表示一个返回响应数据的信息
            （这种一般用在post创建的时候，使用@RequestBody这样的场景，
            请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    @ApiModelProperty：用在属性上，描述响应类的属性
```

```css
@ApiModel(description= "返回响应数据")
public class RestMessage implements Serializable{
    @ApiModelProperty(value = "是否成功")
    private boolean success=true;
    @ApiModelProperty(value = "返回对象")
    private Object data;
    @ApiModelProperty(value = "错误编号")
    private Integer errCode;
    @ApiModelProperty(value = "错误信息")
    private String message;
    /* getter/setter */
}
```

### 用户模块注解配置

#### UserController.java 接口方法注解使用

```java
@GetMapping("user/uname/{userName}")
@ApiOperation(value = "根据用户名查询用户记录")
@ApiImplicitParam(name = "userName",value = "查询参数",required = true,paramType = "path")
public User queryUserByUserName(@PathVariable String userName){
    return userService.queryUserByUserName(userName);
}

@ApiOperation(value = "根据用户id查询用户记录")
@ApiImplicitParam(name = "userId",value = "查询参数",required = true,paramType = "path")
@GetMapping("user/{userId}")
public User queryUserByUserId(@PathVariable  Integer userId, HttpServletRequest request){
    return userService.queryUserByUserId(userId);
}

@GetMapping("user/list")
@ApiOperation(value = "多条件查询用户列表记录")
public PageInfo<User> list(UserQuery userQuery){
    return userService.queryUserByParams(userQuery);
}

@PutMapping("user")
@ApiOperation(value = "用户添加")
@ApiImplicitParam(name = "user",value = "用户实体类",dataType = "User")
public ResultInfo saveUser(@RequestBody  User user){
    ResultInfo resultInfo=new ResultInfo();
    try {
        userService.saveUser(user);
    } catch (ParamsException e) {
        e.printStackTrace();
        resultInfo.setCode(e.getCode());
        resultInfo.setMsg(e.getMsg());
    }catch (Exception e) {
        e.printStackTrace();
        resultInfo.setCode(300);
        resultInfo.setMsg("记录添加失败!");
    }
    return resultInfo;
}

@PostMapping("user")
@ApiOperation(value = "用户更新")
@ApiImplicitParam(name = "user",value = "用户实体类",dataType = "User")
public ResultInfo updateUser(@RequestBody  User user){
    ResultInfo resultInfo=new ResultInfo();
    try {
        userService.updateUser(user);
    } catch (ParamsException e) {
        e.printStackTrace();
        resultInfo.setCode(e.getCode());
        resultInfo.setMsg(e.getMsg());
    }catch (Exception e) {
        e.printStackTrace();
        resultInfo.setCode(300);
        resultInfo.setMsg("记录更新失败!");
    }
    return resultInfo;
}

@PutMapping("user/{userId}")
@ApiOperation(value = "根据用户id删除用户记录")
@ApiImplicitParam(name = "userId",value = "查询参数",required = true,paramType = "path")
public ResultInfo deleteUser(@PathVariable  Integer  userId){
    ResultInfo resultInfo=new ResultInfo();
    try {
        userService.deleteUser(userId);
    } catch (ParamsException e) {
        e.printStackTrace();
        resultInfo.setCode(e.getCode());
        resultInfo.setMsg(e.getMsg());
    }catch (Exception e) {
        e.printStackTrace();
        resultInfo.setCode(300);
        resultInfo.setMsg("记录删除失败!");
    }
    return resultInfo;
}
```

#### JavaBean 使用

- User.java

```java
@ApiModel(description = "响应结果-用户信息")
public class User {
    @ApiModelProperty(value = "用户id",example = "0")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String userPwd;
    /*
       省略get|set
    */
}
```

- UserQuery.java 

```java
@ApiModel(description = "用户模块条件查询类")
public class UserQuery {
    @ApiModelProperty(value = "分页页码",example = "1")
    private Integer pageNum=1;
    @ApiModelProperty(value = "每页大小",example = "10")
    private Integer pageSize=10;
    @ApiModelProperty(value = "用户名")
    private String userName;
    /*
       省略get|set
    */
 }
```

- ResultInfo.java

```java
@ApiModel(description = "响应结果-Model信息")
public class ResultInfo {

    @ApiModelProperty(value = "响应状态码",example = "200")
    private Integer code=200;
    @ApiModelProperty(value = "响应消息结果")
    private String msg="success";

    @ApiModelProperty(value = "响应具体结果信息")
    private Object result;
     /*
       省略get|set
    */
}
```

### Swagger2 接口文档访问

启动工程,浏览器访问:http://localhost:9999/swagger-ui.html

<img src="images\image-20200303221234718.png" alt="image-20200303221234718" style="zoom:67%;" />

<img src="images\image-20200303221324305.png" alt="image-20200303221324305" style="zoom:67%;" />

## SpringBoot应用热部署

### 什么是热部署？

​	热部署，就是在应用正在运行的时候升级软件（增加业务/修改bug），却不需要重新启动应用

​	大家都知道在项目开发过程中，常常会改动页面数据或者修改数据结构，为了显示改动效果，往往需要重启应用查看改变效果，其实就是重新编译生成了新的 Class 文件，这个文件里记录着和代码等对应的各种信息，然后 Class 文件将被虚拟机的 ClassLoader 加载。

​	而热部署正是利用了这个特点，它监听到如果有 Class 文件改动了，就会创建一个新的 ClaassLoader 进行加载该文件，经过一系列的过程，最终将结果呈现在我们眼前，Spring Boot通过配置DevTools  工具来达到热部署效果。

​	在原理上是使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间。

### 热部署环境配置与测试

#### 配置 DevTools 环境

- 修改 Pom 文件，添加 DevTools 依赖

```xml
<!-- DevTools 的坐标 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
     <!--当前这个项目被继承之后，这个不向下传递-->
	<optional>true</optional>	
</dependency>
```

同时在plugin中添加devtools生效标志

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
      <fork>true</fork><!-- 如果没有该配置，热部署的devtools不生效 -->
  </configuration>
</plugin>
```

​		devtools可以实现页面热部署（即页面修改后会立即生效，这个可以直接在application.properties文件中配置spring.thymeleaf.cache=false来实现），实现类文件热部署（类文件修改后不会立即生效），实现对属性文件的热部署。即devtools会监听classpath下的文件变动，并且会立即重启应用（发生在保存时机），注意：因为其采用的虚拟机机制，该项重启是很快的。`
​	`配置了后在修改java文件后也就支持了热启动，不过这种方式是属于项目重启（速度比较快的项目重启），会清空session中的值，也就是如果有用户登陆的话，项目重启后需要重新登陆。

​		默认情况下，/META-INF/maven，/META-INF/resources，/resources，/static，/templates，/public这些文件夹下的文件修改不会使应用重启，但是会重新加载（devtools内嵌了一个LiveReload server，当资源发生改变时，浏览器刷新）

#### 全局配置文件配置

`在application.yml中配置spring.devtools.restart.enabled=false，此时restart类加载器还会初始化，但不会监视文件更新。`

```yml
spring:
  ## 热部署配置
  devtools:
    restart:
      enabled: true
      # 设置重启的目录，添加目录的文件需要restart
      additional-paths: src/main/java
      # 解决项目自动重新编译后接口报404的问题
      poll-interval: 3000
      quiet-period: 1000
```

#### Idea 配置

​		当我们修改了Java类后，IDEA默认是不自动编译的，而spring-boot-devtools又是监测classpath下的文件发生变化才会重启应用，所以需要设置IDEA的自动编译

- 自动编译配置

File-Settings-Compiler-Build Project automatically

![image-20200303223129659](images\image-20200303223129659.png)

- Registry 属性修改

ctrl + shift + alt + /，选择Registry,勾上 Compiler autoMake allow when app running

<img src="images\image-20200303223354472.png" alt="image-20200303223354472" style="zoom:80%;" />

![image-20200303223435887](images\image-20200303223435887.png)

#### 热部署效果测试

- 第一次访问 user/uname/{uname} 接口

```java
@GetMapping("user/uname/{userName}")
@ApiOperation(value = "根据用户名查询用户记录")
@ApiImplicitParam(name = "userName",value = "查询参数",required = true,paramType = "path")
public User queryUserByUserName(@PathVariable String userName){
    return userService.queryUserByUserName(userName);
}
```

![image-20200304101157683](images\image-20200304101157683.png)

![image-20200304101343263](images\image-20200304101343263.png)

- 修改接口代码 控制台打印接收的uname参数 ctrl+f9 键重新编译 浏览器访问

```java
@GetMapping("user/uname/{userName}")
@ApiOperation(value = "根据用户名查询用户记录")
@ApiImplicitParam(name = "userName",value = "查询参数",required = true,paramType = "path")
public User queryUserByUserName(@PathVariable String userName){
    System.out.println("查询参数-->userName:"+userName);
    return userService.queryUserByUserName(userName);
}
```

<img src="images\image-20200304101402407.png" alt="image-20200304101402407" style="zoom:80%;" />

![image-20200304101433128](images\image-20200304101433128.png)

## SpringBoot单元测试

​		做过web项目开发的对于单元测试都并不陌生了，通过它能够快速检测业务代码功能的正确与否，SpringBoot框架对单元测试也提供了良好的支持，来看SpringBoot应用中单元测试的使用。

### pom.xml 测试依赖添加

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```
### Service业务方法测试

这里以UserService为例,src/tets/java 目录下添加测试包 com.xxxx.sprinboot.service 定义测试类代码如下:

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Starter.class})
public class TestUserService {
    private Logger log = LoggerFactory.getLogger(TestUserService.class);

    @Resource
    private UserService userService;

    @Before
    public void before(){
        log.info("单元测试开始...");
    }
    @Test
    public  void test01(){
        log.info(userService.queryUserByUserId(10).toString());
    }
    @Test
    public  void test02(){
        log.info(userService.queryUserByParams(new UserQuery()).toString());
    }
    @After
    public void after(){
        log.info("单元测试结束...");
    }
}
```

<img src="images\image-20200304111210114.png" alt="image-20200304111210114" style="zoom:80%;" />

### 控制层接口方法测试

​		视图层代码使用MockMvc 进行测试，这里以UserCntroller 为例,src/tets/java 目录下添加测试包 com.xxxx.sprinboot.controller 定义测试类代码如下:

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Starter.class})
@AutoConfigureMockMvc
public class TestUserController {
    private Logger log = LoggerFactory.getLogger(TestUserController.class);

    @Autowired
    private MockMvc mockMvc;

    //用户列表查询
    @Test
    public void apiTest01()throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/user/list")).
                andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        log.info("响应状态:{}",mvcResult.getResponse().getStatus());
        log.info("响应内容:{}",mvcResult.getResponse().getContentAsString());;
    }
    // 用户名记录查询
    @Test
    public void apiTest02()throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/user/uname/admin")).
                andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        log.info("响应状态:{}",mvcResult.getResponse().getStatus());
        log.info("响应内容:{}",mvcResult.getResponse().getContentAsString());;
    }
}
```

<img src="images\image-20200304111511031.png" alt="image-20200304111511031" style="zoom:80%;" />

## 分布式缓存Ehcache整合

​	EhCache是一个比较成熟的Java缓存框架，最早从hibernate发展而来， 是进程中的缓存系统，它提供了用内存，磁盘文件存储，以及分布式存储方式等多种灵活的cache管理方案，快速简单。 

​	Spring Boot对Ehcache的使用提供支持，所以在Spring Boot中只需简单配置即可使用Ehcache实现数据缓存处理。

### Spring Cache 相关注解说明

​		SpringBoot 内部使用SpringCache 来实现缓存控制，这里集成Ehcache实际上是对SpringCache 抽象的其中一种实现，这里在使用Ehcache实现缓存控制时相关注解说明如下

#### @CacheConfig

用于标注在类上，可以存放该类中所有缓存的公有属性，比如设置缓存的名字。

```java
@CacheConfig(cacheNames = "users")
public class UserService {。。。}
```

​		这里也可以不使用该注解，直接使用@Cacheable配置缓存集的名字。

#### @Cacheable

​		应用到读取数据的方法上，即可缓存的方法，如查找方法，先从缓存中读取，如果没有再调用相应方法获取数据，然后把数据添加到缓存中。

该注解主要有下面几个参数：

- **value、cacheNames：**两个等同的参数（cacheNames为Spring 4新增，作为value的别名），用于指定缓存存储的集合名。由于Spring 4中新增了@CacheConfig，因此在Spring 3中原本必须有的value属性，也成为非必需项了
- **key：**缓存对象存储在Map集合中的key值，非必需，缺省按照函数的所有参数组合作为key值，若自己配置需使用SpEL表达式，比如：@Cacheable(key = "#p0")：使用函数第一个参数作为缓存的key值，更多关于SpEL表达式的详细内容可参考官方文档
- **condition：**缓存对象的条件，非必需，也需使用SpEL表达式，只有满足表达式条件的内容才会被缓存，比如：@Cacheable(key = "#p0", condition = "#p0.length() < 3")，表示只有当第一个参数的长度小于3的时候才会被缓存。
- **unless：**另外一个缓存条件参数，非必需，需使用SpEL表达式。它不同于condition参数的地方在于它的判断时机，该条件是在函数被调用之后才做判断的，所以它可以通过对result进行判断。
- **keyGenerator：**用于指定key生成器，非必需。若需要指定一个自定义的key生成器，我们需要去实现org.springframework.cache.interceptor.KeyGenerator接口，并使用该参数来指定。需要注意的是：该参数与key是互斥的
- **cacheManager：**用于指定使用哪个缓存管理器，非必需。只有当有多个时才需要使用
- **cacheResolver：**用于指定使用那个缓存解析器，非必需。需通过org.springframework.cache.interceptor.CacheResolver接口来实现自己的缓存解析器，并用该参数指定。

```java
@Cacheable(value = "user", key = "#id")
User selectUserById(final Integer id);
```

#### **@CachePut** 

应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存，@CachePut的参数与@Cacheable类似，示例如下：

```java
@CachePut(value = "user", key = "#user.id")  
public User save(User user) {  
    users.add(user);  
    return user;  
}  
```

#### @CacheEvict 

应用到移除数据的方法上，如删除方法，调用方法时会从缓存中移除相应的数据，示例如下：

```java
@CacheEvict(value = "user", key = "#id")
void delete(final Integer id);
```

除了同@Cacheable一样的参数之外，@CacheEvict还有下面两个参数：

- **allEntries**：非必需，默认为false。当为true时，会移除所有数据
- **beforeInvocation**：非必需，默认为false，会在调用方法之后移除数据。当为true时，会在调用方法之前移除数据。

#### @Caching

组合多个Cache注解使用。示例：

```java
@Caching( 
    put = { 
        @CachePut(value = "user", key = "#user.id"), 
        @CachePut(value = "user", key = "#user.username"), 
        @CachePut(value = "user", key = "#user.age") 
   } 
} 
```

将id-->user；username--->user；age--->user进行缓存。

### 用户管理模块缓存引入

#### 环境配置

- pom.xml 依赖添加

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<!-- Ehcache 坐标 -->
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
```

- ehcahe.xml 文件添加

src/main/resources 目录下添加ehcache.xml 文件，内容如下:

```xml
<ehcache name="mycache">
    <diskStore path="C:\java\cache"/>
    <!--
        name:缓存名称。
        maxElementsInMemory:缓存最大数目
        maxElementsOnDisk：硬盘最大缓存个数。
        eternal:对象是否永久有效，一但设置了，timeout将不起作用。
        overflowToDisk:是否保存到磁盘，当系统宕机时
        timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。
               仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
        timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。
             最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
        diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
        diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
        diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
        memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。
             默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
        clearOnFlush：内存数量最大时是否清除。
        memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
            FIFO，first in first out，这个是大家最熟的，先进先出。
            LFU， Less Frequently Used，最近最少被访问的。
            LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，
               当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
        -->
    <defaultCache
            maxElementsInMemory="10000"
            eternal="false"
            timeToIdleSeconds="120"
            timeToLiveSeconds="120"
            maxElementsOnDisk="10000000"
            diskExpiryThreadIntervalSeconds="120"
            memoryStoreEvictionPolicy="LRU">
    </defaultCache>

    <cache
            name="users"
            eternal="false"
            maxElementsInMemory="100"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="0"
            timeToLiveSeconds="300"
            memoryStoreEvictionPolicy="LRU"/>
</ehcache>
```

- application.yml 添加缓存配置

```yml
spring:
  datasource:
    type: com.mchange.v2.c3p0.ComboPooledDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/springboot_mybatis?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: root
  devtools:
    restart:
      enabled: true
      # 设置重启的目录，添加目录的文件需要restart
      additional-paths: src/main/java
      # 解决项目自动重新编译后接口报404的问题
      poll-interval: 3000
      quiet-period: 1000
  cache:
    ehcache:
      config: classpath:ehcahe.xml
```

- Starter 启动入口类启动缓存

```java
@MapperScan("com.xxxx.springboot.dao")
@EnableCaching
@SpringBootApplication
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
```

- 缓存User 对象实现序列化接口

```java
@ApiModel(description = "用户实体对象")
public class User implements Serializable {
    @ApiModelProperty(value = "用户id主键")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String userPwd;
    /*
      省略 get|set方法
    */
}
```

#### 缓存代码添加

这里以UserService 方法为例

##### 用户详情查询缓存添加

```java
@Cacheable(value = "users",key = "#userId")
public User queryUserByUserId(Integer userId){
    return userMapper.queryById(userId);
}
```

##### 用户列表查询缓存

```java
@Cacheable(value = "users",key="#userQuery.userName+'-'+#userQuery.pageNum+'-'+#userQuery.pageSize")
public PageInfo<User> queryUserByParams(UserQuery userQuery){
    PageHelper.startPage(userQuery.getPageNum(),userQuery.getPageSize());
    return new PageInfo<User>(userMapper.selectByParams(userQuery));
}
```

##### 用户更新&删除缓存清除

```java
@Transactional(propagation = Propagation.REQUIRED)
@CacheEvict(value = "users",key="#user.id")
public void updateUser(User user) {
    AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空!");
    AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()),"用户密码不能为空!");
    User temp = userMapper.queryUserByUserName(user.getUserName());
    AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "该用户已存在!");
    AssertUtil.isTrue(userMapper.update(user)<1,"用户记录添加失败!");
}

@Transactional(propagation = Propagation.REQUIRED)
@CacheEvict(value = "users",allEntries=true)
public  void deleteUser(Integer userId){
    AssertUtil.isTrue(null == userId || null ==userMapper.queryById(userId),"待删除记录不存在!");
    AssertUtil.isTrue(userMapper.delete(userId)<1,"用户删除失败!");
}
```

## 定时调度集成-Quartz

​		在日常项目运行中，我们总会有需求在某一时间段周期性的执行某个动作。比如每天在某个时间段导出报表，或者每隔多久统计一次现在在线的用户量等。

​		在Spring Boot中有Java自带的java.util.Timer类，SpringBoot自带的Scheduled来实现,也有强大的调度器Quartz。Scheduled 在Spring3.X 引入，默认SpringBoot自带该功能,使用起来也很简单，在启动类级别添加@EnableScheduling注解即可引入定时任务环境。但遗憾的是Scheduled  默认不支持分布式环境，这里主要讲解Quartz 时钟调度框架与Spring Boot 集成。

### 环境整合配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
```

### 源代码添加

#### 定义job

com.xxxx.springboot下添加jobs包,定义待执行job任务

```java
public class MyFirstJob implements Job {
    
    private Logger log = LoggerFactory.getLogger(MyFirstJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TriggerKey triggerKey =  context.getTrigger().getKey();
        log.info("触发器:"+triggerKey.getName()+"-->所属组:"+triggerKey.getGroup()+"-->"+sdf.format(new Date())+"-->"+"hello Spring Boot Quartz...");
    }
}
```

#### 构建调度配置类

```java
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail1(){
        return JobBuilder.newJob(MyFirstJob.class).storeDurably().build();
    }

    @Bean
    public Trigger trigger1(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //每一秒执行一次
                .withIntervalInSeconds(1)
                //永久重复，一直执行下去
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .withSchedule(scheduleBuilder)
                .forJob(jobDetail1())
                .build();
    }

    // 每两秒触发一次任务
    @Bean
    public Trigger trigger2(){
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? *"))
                .forJob(jobDetail1())
                .build();
    }
}
```

#### 启动StarterApplication 查看控制台打印效果

![image-20200304204554389](images\image-20200304204554389.png)



## 全局异常与事物控制

### Spring Boot事物支持

​		在使用Jdbc 作为数据库访问技术时，Spring Boot框架定义了基于jdbc 的PlatformTransactionManager 接口的实现DataSourceTransactionManager,并在Spring Boot 应用启动时自动进行配置。如果使用jpa 的话 Spring Boot 同样提供了对应实现。

![](images\20200304111916.png)

![image-20200304112205932](images\image-20200304112205932.png)

​		这里Spring Boot 集成了Mybatis框架，Mybatis底层数据访问层实现基于jdbc 来实现，所以在Spring Boot 环境下对事物进行控制，事物实现由Spring Boot实现并自动配置，在使用时通过注解方式标注相关方法加入事物控制即可

- 声明式事物配置

```java
@Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()),"用户密码不能为空!");
        User temp = userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp, "该用户已存在!");
        AssertUtil.isTrue(userMapper.save(user)<1,"用户记录添加失败!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()),"用户密码不能为空!");
        User temp = userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "该用户已存在!");
        AssertUtil.isTrue(userMapper.update(user)<1,"用户记录添加失败!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public  void deleteUser(Integer id){
        AssertUtil.isTrue(null == id || null ==userMapper.queryById(id),"待删除记录不存在!");
        AssertUtil.isTrue(userMapper.delete(id)<1,"用户删除失败!");
    }
```



### Spring Boot 全局异常处理

​	SpringMvc 中对异常统一处理提供了相应处理方式，推荐大家使用的是实现接口HandlerExceptionResolver的方式，对代码侵入性较小。

​	在Spring Boot 应用中同样提供了对异常的全局性处理，相关注解如下:

####    @ControllerAdvice

​		该注解组合了@Component注解功能,最常用的就是作为全局异常处理的切面类,同时通过该注解可以指定包扫描的范围。@ControllerAdvice约定了几种可行的返回值，如果是直接返回model类的话，需要使用@ResponseBody进行json转换

#### @ExceptionHandler

```
  该注解在Spring 3.X 版本引入，在处理异常时标注在方法级别，代表当前方法处理的异常类型有哪些 具体应用以Restful 接口为例,测试保存用户接口
```

### 全局异常应用

#### 异常抛出与全局捕捉

- UserController 查询接口

```java
@ApiOperation(value = "根据用户id查询用户记录")
@ApiImplicitParam(name = "userId",value = "查询参数",required = true,paramType = "path")
@GetMapping("user/{userId}")
public User queryUserByUserId(@PathVariable  Integer userId){
    return userService.queryUserByUserId(userId);
}
```

- UserService 查询业务方法，抛出ParamExceptions 异常

```java
public User queryUserByUserId(Integer userId){
    AssertUtil.isTrue(true,"异常测试...");
    return userMapper.queryById(userId);
}
```

- 全局异常处理类GlobalExceptionHandler定义

```java
@ControllerAdvice
public class GlobalExceptionHandler{
    /**
     * 全局异常处理 返回json
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo exceptionHandler(Exception e){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setCode(300);
        resultInfo.setMsg("操作失败!");
        if(e instanceof ParamsException){
            ParamsException pe= (ParamsException) e;
            resultInfo.setMsg(pe.getMsg());
            resultInfo.setCode(pe.getCode());
        }
        return resultInfo;
    }
}
```

-  Postman 执行测试效果

![image-20200304113238513](images\image-20200304113238513.png)

#### 特定异常处理

通过@ExceptionHandler 标注方法可以处理特定异常，这里以用户未登录异常为例，通过全局异常进行统一处理

```java
/**
 * 用户未登录异常特殊处理 返回json
 * @param authExceptions
 * @return
 */
@ExceptionHandler(value = NoLoginException.class)
@ResponseBody
public  ResultInfo userNotLoginHandler(NoLoginException authExceptions){
    System.out.println("用户未登录异常处理。。。");
    return new ResultInfo(authExceptions.getCode(),authExceptions.getMsg());
}
```

在用户添加接口中抛出未登录异常为例进行测试

```java
@PutMapping("user")
@ApiOperation(value = "用户添加")
@ApiImplicitParam(name = "user",value = "用户实体类",dataType = "User")
public ResultInfo saveUser(@RequestBody  User user){
    if(1==1){
        throw  new NoLoginException();
    }
    ResultInfo resultInfo=new ResultInfo();
        userService.saveUser(user);
    return resultInfo;
}
```

 ![image-20200304114220452](images\image-20200304114220452.png)

## SpringBoot 数据校验-Validation

​		日常项目开发中，对于前端提交的表单，后台接口接收到表单数据后，为了程序的严谨性，通常后端会加入业务参数的合法校验操作来避免程序的非技术性bug，这里对于客户端提交的数据校验，SpringBoot通过spring-boot-starter-validation 模块包含了数据校验的工作。

​	这里主要介绍Spring Boot中对请求数据进行校验，相关概念如下

- JSR303/JSR-349: JSR303是一项标准,只提供规范不提供实现，规定一些校验规范即校验注解，如@Null，@NotNull，@Pattern，位于javax.validation.constraints包下。JSR-349是其升级版本，添加了一些新特性。
- Hibernate Validation：Hibernate Validation是对这个规范的实现，并增加了一些其他校验注解，如@Email，@Length，@Range等等
- Spring Validation：Spring Validation对Hibernate Validation进行了二次封装，在Spring Mvc模块中添加了自动校验，并将校验信息封装进了特定的类中

### 环境配置

​		实现参数校验，程序必须引入spring-boot-starter-validation 依赖，只是在引入spring-boot-starter-web依赖时，该模块会自动依赖spring-boot-starter-validation，所以程序中引入spring-boot-starter-web 会一并依赖spring-boot-starter-validation到项目中。

<img src="images\image-20200304173808123.png" alt="image-20200304173808123" style="zoom:80%;" />

### 校验相关注解

| 注解         | 功能                                                         |
| :----------- | :----------------------------------------------------------- |
| @AssertFalse | 可以为null,如果不为null的话必须为false                       |
| @AssertTrue  | 可以为null,如果不为null的话必须为true                        |
| @DecimalMax  | 设置不能超过最大值                                           |
| @DecimalMin  | 设置不能超过最小值                                           |
| @Digits      | 设置必须是数字且数字整数的位数和小数的位数必须在指定范围内   |
| @Future      | 日期必须在当前日期的未来                                     |
| @Past        | 日期必须在当前日期的过去                                     |
| @Max         | 最大不得超过此最大值                                         |
| @Min         | 最大不得小于此最小值                                         |
| @NotNull     | 不能为null，可以是空                                         |
| @Pattern     | 必须满足指定的正则表达式                                     |
| @Size        | 集合、数组、map等的size()值必须在指定范围内                  |
| @Email       | 必须是email格式                                              |
| @Length      | 长度必须在指定范围内                                         |
| @NotBlank    | 字符串不能为null,字符串trin()后也不能等于“”                  |
| @NotEmpty    | 不能为null，集合、数组、map等size()不能为0；字符串trin()后可以等于“” |
| @Range       | 值必须在指定范围内                                           |
| @URL         | 必须是一个URL                                                |

### 参数校验注解使用

- User实体类参数校验注解

```java
public class User  implements Serializable {
    private Integer id;
    @NotBlank(message = "用户名不能为空!")
    private String userName;

    @NotBlank(message = "用户密码不能为空!")
    @Length(min = 6, max = 10,message = "密码长度至少6位但不超过10位!")
    private String userPwd;
    @Email
    private String email;
    
    /*
      省略get set 方法  
    */
}
```

- 接口方法形参@Valid注解添加

```java
@PostMapping("user02")
@ApiOperation(value = "用户添加")
@ApiImplicitParam(name = "user02",value = "用户实体类",dataType = "User")
public ResultInfo saveUser02(@Valid  User user){
    ResultInfo resultInfo=new ResultInfo();
    //userService.saveUser(user);
    return resultInfo;
}
```

- 全局异常错误信息捕捉

```java
/**
 * 全局异常处理 返回json
 * @param e
 * @return
 */
@ExceptionHandler(value = Exception.class)
@ResponseBody
public ResultInfo exceptionHandler(Exception e){
    ResultInfo resultInfo=new ResultInfo();
    resultInfo.setCode(300);
    resultInfo.setMsg("操作失败!");
    if(e instanceof ParamsException){
        ParamsException pe= (ParamsException) e;
        resultInfo.setMsg(pe.getMsg());
        resultInfo.setCode(pe.getCode());
    }else if(e instanceof BindException){
        BindException be = (BindException) e;
        resultInfo.setResult(be.getBindingResult().getFieldError().getDefaultMessage());
    }
    return resultInfo;
}
```

- PostMan 接口测试

<img src="images\image-20200304202414162.png" alt="image-20200304202414162" style="zoom:80%;" />

![image-20200304202504600](images\image-20200304202504600.png)

## 总结

​		今天课程主要介绍了SpringBoot中各种环境的整合与测试工作，持久层框架Mybatis集成与数据访问基本操作，借助SpringBoot单元测试实现业务方法与控制器接口测试，同时集成了Swagger2 接口文件生成工具来快速生成接口文档的功能。在web项目开发中常见的项目热部署配置，这里集成DevTools工具来帮助web开发者在项目开发中不断手动启动服务器部署项目的繁琐流程，通过引入EhCache 缓存技术来加快应用程序数据的访问效率，然后对于项目中常见的定时任务的执行，这里集成了Quartz 任务调度框架来实现任务定时执行处理，最后提到了SpringBoot 应用中对象项目事物控制与异常统一处理，从而提高项目代码的健壮性与数据的一致性，借助SpringBoot 的Validation 实现后端数据参数的校验机制，结合全局异常来对校验结果进行输出操作，提高后端应参数处理的严谨性。

