# SpringBoot_第一天

## 学习目标

![image-20200229230101073](images\image-20200229230101073.png)

## Spring框架发展史

### Spring1.x 时代	

​		在Spring1.x时代，都是通过xml文件配置bean，随着项目的不断扩大，需要将xml配置分放到不同的配置文件中，需要频繁的在java类和xml配置文件中切换。

### Spring2.x时代

​		随着JDK 1.5带来的注解支持，Spring2.x可以使用注解对Bean进行申明和注入，大大的减少了xml配置文件，同时也大大简化了项目的开发。

那么，问题来了，究竟是应该使用xml还是注解呢？

最佳实践：

1. 应用的基本配置用xml，比如：数据源、资源文件等；
2. 业务开发用注解，比如：Service中注入bean等；

### Spring3.x到Spring4.x再到Spring5.x

​		从Spring3.x开始提供了Java配置方式，使用Java配置方式可以更好的理解你配置的Bean，现在我们就处于这个时代，并且Spring4.x、Spring5.x和Spring Boot都推荐使用java配置的方式。

## Spring 5.X 应用零配置开发

​		Spring 框架从5.x版本推荐使用注解形式来对java应用程序进行开发与配置,并且可以完全替代原始的XML+注解形式的开发,在使用注解形式进行项目开发与环境配置时，Spring 框架提供了针对环境配置与业务bean开发相关注解。

### 注解

#### 声明Bean 注解

```properties
@Component:组件 没有明确规定其角色，作用在类级别上声明当前类为一个业务组件，被Spring Ioc 容器维护;

@Service:在业务逻辑层（Service 层）类级别进行声明;

@Repository:在数据访问层(dao 层) 类级别声明;

@Controller:在展现层(MVC) 使用 标注当前类为一个控制器
```

#### 注入Bean 注解

```properties
@AutoWired:Spring 官方提供注解

@Inject:JSR-330 提供注解（标准制定方）

@Resource:JSR-250 提供注解
```

​		以上三种注解在Set 方法或属性上声明，一般情况下通用一般开发中更习惯声明在属性上，代码简洁清晰。基于5.x 注解配置方式简化了xml 配置，应用程序开发与xml 环境配置均通过相应注解来实现。

#### Spring5.x 中配置与获取Bean注解

```properties
 @Configuration:作用与类上，将当前类声明为一个配置类，相当于一个xml 配置文件
 @ComponentScan:自动扫描指定包下标注有@Repository,@Service,@Controller
 @Component:注解的类并由Ioc 容器进行实例化和维护
 @Bean::作用于方法上,相当于xml 文件中<bean> 声明当前方法返回值为一个bean
 @Value:获取properties 文件指定key value值 
```

### 实例1-Ioc中Bean的实例化与获取

#### 创建Spring 普通工程并添加坐标相关配置

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.2.3.RELEASE</version>
    </dependency>
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>2.3.2</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <encoding>utf-8</encoding>
            </configuration>
        </plugin>
    </plugins>
</build>
```

#### 添加源代码

##### 创建UserDao ,UserService Bean 对象

```java
// UserDao.java
@Repository
public class UserDao {
    public  void test(){
        System.out.println("UserDao.test...");
    }
}


// UserService.java
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public  void test(){
        System.out.println("UserService.test...");
        userDao.test();
    }
}
```

##### 创建IocConfig配置类

```java
// Ioc 容器配置 Java 代码实现
@Configuration
@ComponentScan("com.xxxx.springboot")
public class IocConfig {
}
```

##### 创建启动类执行测试

```java
public class Starter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(IocConfig.class);
        UserService userService= ac.getBean(UserService.class);
        userService.test();
    }
}
```

​		此时启动Spring Ioc 容器通过实例化**AnnotationConfigApplicationContext**类，接收配置参数类IocConfig,并获取UserService Bean 实现方法调用,此时应用环境不存在xml配置文件，简化了应用的xml 配置。

使用@Bean注解声明在方法(注意:方法名一般为bean对象名称)级别用于返回实例化的Bean对象。

##### @Bean注解使用

定义AccountDao 对象,并交给Spring Ioc 容器进行实例化

```java
// 注意 此时类级别并未添加 @Repository 注解
public class AccountDao {
    public  void test(){
        System.out.println("AccountDao.test...");
    }
}
```

修改IocConfig 配置类中添加返回AccountDao Bean对象方法

```java
@Configuration
@ComponentScan("com.xxxx.springboot")
public class IocConfig {

    // 返回实例化的单例Bean对象
    @Bean
    public AccountDao accountDao(){
        return new AccountDao();
    }
}
```

UserService 中注入AccountDao 对象

```java

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private AccountDao accountDao;

    public  void test(){
        System.out.println("UserService.test...");
        userDao.test();
        accountDao.test();
    }
}
```

执行测试

```java
public class Starter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(IocConfig.class);
        UserService userService= ac.getBean(UserService.class);
        userService.test();
        System.out.println(ac.isSingleton("accountDao"));
    }
}
```

### 实例2-读取外部配置文件

​		在开发Java web 应用进行时,配置文件是比较常见的，比如xml,properties,yml等文件，在Spring 应用中对于配置文件的读取同样提供支持。对于配置文件读取，我们可以通过@PropertySource 注解声明到类级别来指定读取相关配置配置。

​		Spring El表达式语言，支持在Xml和注解中使用表达式，类似于JSP中EL表达式，Spring框架在借助该表达式实现资源注入，主要通过@Value 注解来使用表达式，通过@Value 注解，可以实现普通字符串，表达式运算结果，Bean 属性文件内容，属性文件等参数注入。具体使用如下：

#### 准备properties 配置文件 

src/main/resources 目录下添加user.properties jdbc.roperties 文件

```properties
# user.properties
user.userName=admin
user.password=admin

# jdbc.properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/hr?useUnicode=true&characterEncoding=utf8
jdbc.username=root
jdbc.password=root
```

#### @PropertySource加载properties配置文件

```java
@Configuration
@ComponentScan("com.shsxt")
@PropertySource(value = {"classpath:jdbc.properties","classpath:user.properties"})
public class IocConfig {
 @Value("${jdbc.driver}")
    private String url;
    @Value("${jdbc.url}")
    private String driver;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
    @Bean
    public AccountDao accountDao(){
        return new AccountDao();
    }
    // 控制台打印属性值信息
    public  void showConfigInfo(){
        System.out.println("driver:"+driver+":url:"+url);
        System.out.println(":userName:"+userName+":password:"+password);
    }
}
```

#### 其他Bean对象获取properties文件内容

```java
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private AccountDao accountDao;

    @Value("${user.userName}")
    private String userName;
    @Value("${user.password}")
    private String password;

    public  void test(){
        System.out.println("UserService.test...");
        userDao.test();
        accountDao.test();
        System.out.println("userName:"+userName+":password:"+password);
    }
}
```

启动Starter 查看控制台输出内容效果

### 组合注解与元注解

​		Spring 从2.x版本开始引入注解支持(目的是jdk1.5中推出注解功能),通过引入注解来消除大量xml 配置,Spring 引入注解主要用来注入bean以及aop 切面相关配置,但由于注解大量使用，就会造成大量重复注解代码出现，代码出现了重复，Spring 为了消除重复注解，在元注解上引入了组合注解，其实可以理解为对代码的重构，相当于注解的注解，拥有元注解的原始功能，比如在定义配置类时用到的@Configuration 注解就是组合注解，拥有Component 注解功能，即配置类本身也是一个被Ioc维护的单例Bean。

![image-20200215105138078](images\image-20200215105138078.png)

#### 自定义组合注解

- 定义MyCompScan 注解，拥有@ComponentScan 扫描器注解功能

```java
/**
 * 组合注解MyCompScan 定义
 *    拥有元注解@Configuration + @ComponentScan 两者功能
 *    覆盖value 属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
@ComponentScan
public @interface MyCompScan {
    String[] value() default {};
}
```

#### 应用组合注解

```java
@MyCompScan("com.xxxx.springboot")
@PropertySource(value = {"classpath:jdbc.properties","classpath:user.properties"})
public class IocConfig02 {
    @Value("${jdbc.driver}")
    private String url;
    @Value("${jdbc.url}")
    private String driver;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
    @Bean
    public AccountDao accountDao(){
        return new AccountDao();
    }
    
    public  void showConfigInfo(){
        System.out.println("driver:"+driver+":url:"+url);
        System.out.println(":userName:"+userName+":password:"+password);
    }
}
```

测试组合注解

```java
public class Starter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(IocConfig02.class);
        UserService userService= ac.getBean(UserService.class);
        userService.test();
        System.out.println(ac.isSingleton("accountDao"));
        IocConfig iocConfig=ac.getBean(IocConfig.class);
        iocConfig.showConfigInfo();
    }
}
```

## Spring MVC 零配置创建与部署

​		基于Spring Mvc 5.X 使用Maven搭建SpringMvc Web项目,通过Spring 提供的注解与相关配置来对项目进行创建与部署。

### 创建Spring Mvc Web工程

### pom.xml添加坐标相关配置

```xml
   <!-- spring web -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-web</artifactId>
  <version>5.2.3.RELEASE</version>
</dependency>

<!-- spring mvc -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-webmvc</artifactId>
  <version>5.2.3.RELEASE</version>
</dependency>

<!-- web servlet -->
<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>javax.servlet-api</artifactId>
  <version>3.0.1</version>
</dependency>

<build>
<finalName>springmvc</finalName>
<plugins>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>2.3.2</version>
    <configuration>
      <source>1.8</source>
      <target>1.8</target>
      <encoding>utf-8</encoding>
    </configuration>
  </plugin>
    <plugin>
        <groupId>org.eclipse.jetty</groupId>
        <artifactId>jetty-maven-plugin</artifactId>
        <version>9.4.27.v20200227</version>
      </plugin>
</plugins>
</build>
```

### 添加源代码

```java
@Controller
public class HelloController {
	@RequestMapping("/index")
	public  String index(){
		return "index";
	}
}
```

### 添加视图

在WEB-INF/views 目录下创建index.jsp（这里以jsp为模板）

```html
<html>
<body>
<h2>test web mvc</h2>
</body>
</html>
```

### SpringMvc配置类添加 

​		Spring Mvc 配置信息MvcConfig文件添加,作为Mvc 框架环境，原来是通过xml来进行配置(视图解析器，Json转换器，文件上传解析器等)，这里基于注解通过继承WebMvcConfigurerAdapter类 并重写相关方法来进行配置(注意通过@EnableWebMvc注解来启动MVC环境)。

```java
/**
 * mvc 基本配置
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.xxxx.springboot")
public class MvcConfig{


    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}

```

​		MvcConfig 类定义好了，那么问题来了，怎么加载MvcConfig 类呢，原来在构建Mvc应用时是通过容器启动应用时加载web.xml 文件 实现配置文件加载，现在的环境web.xml文件不存在，此时基于注解方式构建的Mvc 应用，定义WebInitializer 实现WebApplicationInitializer接口(该接口用来配置Servlet3.0+配置的接口，用于替代web.xml 配置)，当servlet 容器启动Mvc应用时会通过SpringServletContainerInitializer接口进行加载 从而加载Mvc 应用信息配置。实现该接口onStartup 方法 ，加载应用信息配置。

### 入口文件代码添加

```java
public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx=new AnnotationConfigWebApplicationContext();
        // 注册Mvc 配置信息
        ctx.register(MvcConfig.class);
        // 设置ServletContext 上下文信息
        ctx.setServletContext(servletContext);
        // 配置转发器Dispatcher
        ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher",new DispatcherServlet(ctx));
        servlet.addMapping("/");
        // 启动时即实例化Bean
        servlet.setLoadOnStartup(1);
    }
}
```

### 部署与测试

通过tomcat 启动项目并访问 

![image-20200215110733343](images\image-20200215110733343.png)

此时地址访问成功。

​		当项目访问成功后，那么问题来了，如果项目中存在静态资源文件,Handler放行处理该如何配置，定义的拦截器怎么应用，此时关注WebMvcConfigurationSupport 父类方法，重写相关方法即可。

```java
// 静态资源 handler不进行处理 直接响应到客户端
@Override
public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
}

// 配置拦截器
@Bean
public LoginInterceptor loginInterceptor(){
    return new LoginInterceptor();
}
// 添加拦截器到mvc 环境
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor());
}
```

## Spring Boot 概念&特点

<img src="images\image-20200229103633024.png" alt="image-20200229103633024" style="zoom:80%;" />

### 框架概念

​		随着动态语言流行(Ruby,Scala,NodeJs等)，Java 开发变得相对笨重，配置繁琐，开发效率低下，部署流程复杂，以及第三方集成难度也相对较大，针对该环境，Spring Boot被开发出来，其使用“习惯大于配置目标”,借助Spring Boot 能够让项目快速运行起来，同时借助Spring Boot可以快速创建web 应用并独立进行部署(jar包 war 包方式，内嵌servlet 容器)，同时借助Spring Boot 在开发应用时可以不用或很少去进行相关xml环境配置，简化了开发，大大提高项目开发效率。

​		Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。通过这种方式，让Spring Boot在蓬勃发展的快速应用开发领域(rapid application development)成为领导者.

### 框架特点

​		创建独立Spring应用程序、嵌入式Tomcat，Jetty容器、无需部署WAR包、简化Maven及Gradle配置、尽可能自动化配置Spring、直接植入产品环境下的实用功能，比如度量指标、健康检查及扩展配置、无需代码生成及XML配置等，同时Spring Boot不仅对web应用程序做了简化，还提供一系列的依赖包来把其它一些工作做成开箱即用。

### Spring Boot快速入门

#### 环境:Idea、Maven、Jdk 1.8+ 、Spring Boot 2.x

#### 创建Maven 普通项目

#### 添加依赖坐标

```xml
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
</dependencies>
```

​		Spring Boot的项目必须要将parent设置为Spring Boot的parent，该parent包含了大量默认的配置，简化程序的开发。

#### 导入Spring Boot的web坐标与相关插件

```xml
<plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

#### 添加源代码

```java
@Controller
public class HelloController {
    @RequestMapping("hello")
    @ResponseBody
    public  String hello(){
        return "Hello Spring Boot";
    }
}
```

#### 创建启动程序

在HelloController.java 所在包下创建StarterApplication.java 

```java
@SpringBootApplication
public class StarterApplication
{
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
```

#### 启动Spring Boot应用并测试

这里运行main 方法即可 通过浏览器访问localhost:8080/hello 效果如下:

![image-20200229104722592](images\image-20200229104722592.png)

## Spring Boot 核心配置

### 自定义Banner与Banner关闭

<img src="images\image-20200216162710816.png" alt="image-20200216162710816" style="zoom:80%;" />

​		在搭建Spring Boot项目环境时，程序启动后会在控制台打印醒目的SpringBoot图标,图标描述了Spring Boot 版本信息，这是Spring Boot项目与Spring项目启动区别较大的地方，Spring Boot通过默认Banner在程序启动时显示应用启动图标，当然图标我们也可以进行自定义。

#### Banner图标自定义

​		Spring Boot项目启动时默认加载src/main/resources 目录下的banner.txt 图标文件,如果该目录文件未提供,则使用Spring Boot 默认图标打印在main 目录下新建resources 资源目录,并在该目录下新建banner.txt 文本文件。

打开网址: [http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20](http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type Something )

在线生成图标对应文本并将文本内容copy 到banner.txt 中

![image-20200216163758598](images\image-20200216163758598.png)

启动Spring Boot 应用打印如下:

![image-20200216164207654](images\image-20200216164207654.png)

#### Banner 图标关闭

如果启动时不想要看到启动图标 ，这里也可以通过代码进行关闭操作,修改StarterApplication 设置BannerMode值为Banner.Mode.OFF,启动Spring Boot 应用关闭图标输出功能即可

```java
@SpringBootApplication
public class StarterApplication  {

    public static void main(String[] args) {
        SpringApplication springApplication=new SpringApplication(StarterApplication .class);
        // 设置banner 图标关闭
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run();
    }
}
```

### Spring Boot 配置文件

​		Spring Boot 默认会读取全局配置文件，配置文件名固定为:application.properties或application.yml，放置在src/main/resources资源目录下,使用配置文件来修改SpringBoot自动配置的默认值；

在resources 资源目录下添加application.properties 文件,配置信息如下:

```properties
## 项目启动端口号配置
server.port=8989
## 项目访问上下文路径
server.servlet-path=/mvc

## 数据源配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/hr?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=root
```

或者application.yml 文件

```yml
## 端口号  上下文路径
server:
  port: 8989
  servlet:
    context-path: /mvc

## 数据源配置
spring:
  datasource:
    type: com.mchange.v2.c3p0.ComboPooledDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/hr
    username: root
    password: root
```

### Starter 坐标 &  自动化配置

#### Starter坐标配置

​		Spring Boot 引入了全新的Starter坐标体系，简化企业项目开发大部分场景的Starter pom,应用程序引入指定场景的Start pom 相关配置就可以消除 ，通过Spring Boot就可以得到自动配置的Bean。

##### Web starter

使用Spring MVC来构建RESTful Web应用，并使用Tomcat作为默认内嵌容器

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

##### Freemarker Starter & Thymeleaf starter

集成视图技术，引入Freemarker Starter ,Thymeleaf Starter

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

##### JavaMail邮件发送 Starter

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

##### 引入AOP环境

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

- [ ] 相关starter系列坐标参考

| 名称                                   |                             描述                             |
| :------------------------------------- | :----------------------------------------------------------: |
| spring-boot-starter                    |    核心Spring Boot starter，包括自动配置支持，日志和YAML     |
| spring-boot-starter-actuator           |           生产准备的特性，用于帮我们监控和管理应用           |
| spring-boot-starter-amqp               |      对”高级消息队列协议”的支持，通过spring-rabbit实现       |
| spring-boot-starter-aop                |        对面向切面编程的支持，包括spring-aop和AspectJ         |
| spring-boot-starter-batch              |            对Spring Batch的支持，包括HSQLDB数据库            |
| spring-boot-starter-cloud-connectors   | 对Spring Cloud Connectors的支持，简化在云平台下（例如，Cloud Foundry 和Heroku）服务的连接 |
| spring-boot-starter-data-elasticsearch | 对Elasticsearch搜索和分析引擎的支持，包括spring-data-elasticsearch |
| spring-boot-starter-data-gemfire       |    对GemFire分布式数据存储的支持，包括spring-data-gemfire    |
| spring-boot-starter-data-jpa           | 对”Java持久化API”的支持，包括spring-data-jpa，spring-orm和Hibernate |
| spring-boot-starter-data-mongodb       |     对MongoDB NOSQL数据库的支持，包括spring-data-mongodb     |
| spring-boot-starter-data-rest          | 对通过REST暴露Spring Data仓库的支持，通过spring-data-rest-webmvc实现 |
| spring-boot-starter-data-solr          |      对Apache Solr搜索平台的支持，包括spring-data-solr       |
| spring-boot-starter-freemarker         |                  对FreeMarker模板引擎的支持                  |
| spring-boot-starter-groovy-templates   |                    对Groovy模板引擎的支持                    |
| spring-boot-starter-hateoas            |   对基于HATEOAS的RESTful服务的支持，通过spring-hateoas实现   |
| spring-boot-starter-hornetq            |          对”Java消息服务API”的支持，通过HornetQ实现          |
| spring-boot-starter-integration        |              对普通spring-integration模块的支持              |
| spring-boot-starter-jdbc               |                      对JDBC数据库的支持                      |
| spring-boot-starter-jersey             |              对Jersey RESTful Web服务框架的支持              |
| spring-boot-starter-jta-atomikos       |           对JTA分布式事务的支持，通过Atomikos实现            |
| spring-boot-starter-jta-bitronix       |           对JTA分布式事务的支持，通过Bitronix实现            |
| spring-boot-starter-mail               |                      对javax.mail的支持                      |
| spring-boot-starter-mobile             |                    对spring-mobile的支持                     |
| spring-boot-starter-mustache           |                   对Mustache模板引擎的支持                   |
| spring-boot-starter-redis              |         对REDIS键值数据存储的支持，包括spring-redis          |
| spring-boot-starter-security           |                   对spring-security的支持                    |
| spring-boot-starter-social-facebook    |                对spring-social-facebook的支持                |
| spring-boot-starter-social-linkedin    |                对spring-social-linkedin的支持                |
| spring-boot-starter-social-twitter     |                对spring-social-twitter的支持                 |
| spring-boot-starter-test               | 对常用测试依赖的支持，包括JUnit, Hamcrest和Mockito，还有spring-test模块 |
| spring-boot-starter-thymeleaf          |        对Thymeleaf模板引擎的支持，包括和Spring的集成         |
| spring-boot-starter-velocity           |                   对Velocity模板引擎的支持                   |
| spring-boot-starter-web                |       对全栈web开发的支持， 包括Tomcat和spring-webmvc        |
| spring-boot-starter-websocket          |                    对WebSocket开发的支持                     |
| spring-boot-starter-ws                 |                    对Spring Web服务的支持                    |

`传统的maven坐标这里同样适用，如果引入传统maven坐标需要考虑相关配置类的编写`

 如果引入相关starter坐标这里不存在，可以到<a href='https://mvnrepository.com/search?q=spring-boot'>这里</a>搜索。

#### 自动化配置

##### SpringBoot Starter坐标版本查看

​		前面介绍了SpringBoot Starter相关坐标，引入Starter坐标来简化应用环境的配置。这里以环境搭建spring-boot-starter-web坐标来简单分析SpringBoot 自动化配置过程。

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

​		这里引入的web环境坐标不像传统的maven坐标那样包含坐标的版本号，项目中引入的starter 系列坐标对应的版本库统一由父工程坐标统一控制即项目中引入的parent标签。

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <!--
     父类项目统一对项目依赖版本统一控制!
  -->  
  <version>2.2.2.RELEASE</version>
</parent>
```

这里spring-boot-starter-parent 继承spring-boot-dependencies 项目,在spring-boot-dependencies项目中定义了spring-boot-starter-web坐标的版本!（spring-boot-dependencies 项目中定义了当前SpringBoot版本下各个starter 坐标版本以及依赖的其他坐标版本）

![image-20200229114450576](images\image-20200229114450576.png)



![image-20200229114845014](images\image-20200229114845014.png)

这里依赖的坐标较多,不在截图展示，可关注spring-boot-dependencies-2.2.2.RELEASE.pom文件。

##### Spring Boot自动化配置

​		Spring Boot的项目一般都会有*Application的入口类，入口类中提供main方法，这是一个标准的Java应用程序的入口方法。@SpringBootApplication注解是Spring Boot的核心注解，它其实是一个组合注解：

- @SpringBootApplication

![image-20200229115424565](images\image-20200229115424565.png)

​		可以看出该注解也是一个组合注解，组合了@Configuration注解,对于Spring Boot应用，@SpringBootConfiguration 注解属于Boot 项目的配置注解也是属于一个组合注解，Spring Boot 项目中推荐使用@SpringBootConfiguration注解,因为其组合了@Configuration注解。

- @EnableAutoConfiguration 

![image-20200215113502148](images\image-20200215113502148.png)

@EnableAutoConfiguration注解组合了@AutoConfigurationPackage、@Import(AutoConfigurationImportSelector.class) 注解。

​		@AutoConfigurationPackage底层也是一个@Import(AutoConfigurationPackages.Registrar.class)，其会把启动类的包下组件都扫描到Spring容器中。

![image-20200229115723691](images\image-20200229115723691.png)

​		@Import(AutoConfigurationImportSelector.class) 自动配置的核心类AutoConfigurationImportSelector.class，该类导入大量的自动配置类，debug可以发现，其读取的是classpath下的META-INF/spring.factories下配置文件。

![image-20200229150149852](images\image-20200229150149852.png)

以WebMvcAutoConfiguration为例，可以看出该类使用@Configuration 注解进行标注其为一个配置类。

![image-20200229151326686](images\image-20200229151326686.png)

当然spring.factories 文件中配置类默认不会都生效，具体哪些配置类生效由配置类上标注的@ConditionalOnClass 注解来决定，这里了解下@ConditionalOnClass 注解含义

```java
@ConditionalOnBean         //   当给定的在bean存在时,则实例化当前Bean
@ConditionalOnMissingBean  //   当给定的在bean不存在时,则实例化当前Bean
@ConditionalOnClass        //   当给定的类名在类路径上存在，则实例化当前Bean
@ConditionalOnMissingClass //   当给定的类名在类路径上不存在，则实例化当前Bean
```

意味着WebMvcAutoConfiguration 配置类生效需要环境中存在Servlet.class，DispatcherServlet.class，WebMvcConfigurer.class实例，配置类才会生效。 

从以上分析可以得出如下结论:

   	 `Spring Boot通过maven中的starter导入了所需场景下的jar包，并通过主启动类上的@SpringBootApplication中的@EnableAutoConfiguration读取了类路径下的META-INF/spring.factories下EnableAutoConfiguration的配置类，这些配置类使用@ConditionalOnClass来标注，根据@ConditionalOnClass标注的约束条件来引入自动化的环境配置。`

### Profile 配置

 	Profile 是Spring 用来针对不同环境对不同配置提供支持的全局Profile配置使用application-{profile}.yml，比如application-dev.yml ,application-test.yml。

通过在application.yml中设置spring.profiles.active=test|dev|prod 来动态切换不同环境,具体配置如下:

- application-dev.yml  开发环境配置文件

```yml
server:
  port: 8989
```

- application-test.yml 测试环境配置文件

```yml
server:
  port: 9999
```

- application-prod.yml 生产环境配置文件

```yml
server:
  port: 8686
```

- application.yml 主配置文件

```yml
## 环境选择配置
spring:
  profiles:
    active: dev
```

启动Starter 查看控制台输入效果

![image-20200228224950812](images\image-20200228224950812.png)

修改application.yml 设置active 值为prod 

```yml
## 环境选择配置
spring:
  profiles:
    active: prod
```

启动Starter 再次查看控制台输入效果

![image-20200228225304288](images\image-20200228225304288.png)

### 日志配置

​		在开发企业项目时，日志的输出对于系统bug 定位无疑是一种比较有效的方式，也是项目后续进入生产环境后快速发现错误解决错误的一种有效手段，所以日志的使用对于项目也是比较重要的一块功能。

​		Spring Boot默认使用LogBack日志系统，如果不需要更改为其他日志系统如Log4j2等，则无需多余的配置，LogBack默认将日志打印到控制台上。如果要使用LogBack，原则上是需要添加dependency依赖的

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

<img src="images\image-20200229153940293.png" alt="image-20200229153940293" style="zoom:80%;" />		

​		因为新建的Spring Boot项目一般都会引用`spring-boot-starter`或者`spring-boot-starter-web`，而这两个起步依赖中都已经包含了对于`spring-boot-starter-logging`的依赖，所以，无需额外添加依赖。

#### 项目中日志信息输出

Starter 启动类中添加Log 日志类,控制台打印日志信息。

```java
package com.xxxx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Starter {
    private static Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        logger.info("SpringBoot 应用开始启动...");
        SpringApplication.run(Starter.class);
    }
}
```

![image-20200229154455037](images\image-20200229154455037.png)

#### 日志输出格式配置

​		修改application.yml文件添日志输出格式信息配置，可以修改application.yml文件来控制控制台日志输出格式，同时可以设置日志信息输出到外部文件。

```yml
logging:
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger- %msg%n"
    level: debug
  file:
    path: "."
    name: "springboot.log"
```

![image-20200229164644849](images\image-20200229164644849.png)

更多日志输出，参考<a href=''>官网</a>

## Freemarker & Thymeleaf视图技术集成

### Freemarker 视图集成

​		SpringBoot内部支持Freemarker 视图技术的集成，并提供了自动化配置类FreeMarkerAutoConfiguration，借助自动化配置可以很方便的集成Freemarker基础到SpringBoot环境中。这里借助入门项目引入Freemarker环境配置。

- starter坐标引入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

- 添加Freemarker 配置信息

  ​		Freemarker 默认默认视图路径文 resources/templates 目录(由自动化配置类FreemarkerProperties 类决定)，该目录可以进行在application.yml 中进行修改。

![](images\1564043016714.png)

修改application.yml 添加freemarker 基本配置如下:

```yml
spring:
  freemarker:
    suffix: .ftl
    content-type: text/html
    charset: UTF-8
    template-loader-path: classpath:/views/
```

- 编写IndexController 控制器转发视图

```java
package com.xxxx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
```

- views目录下添加index.ftl视图

![](images\1564043361329.png)

- 启动Starter访问

![](images\1564041880499.png)

### Thymeleaf视图集成

​		SpringBoot 支持多种视图技术集成,并且SpringBoot 官网推荐使用Thymeleaf 作为前端视图页面，这里实现Thymeleaf 视图集成，借助入门项目引入Thymeleaf 环境配置。

- starter坐标引入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

- 添加Thymeleaf 配置信息

  ​		Thymeleaf 默认默认视图路径文 resources/templates 目录(由自动化配置类FreemarkerProperties 类决定)，该目录可以进行在application.yml 中进行修改。

  ![](images\1564044493357.png)

  

  ```yml
  ## 环境选择配置
  spring:
    thymeleaf:
      prefix: classpath:/html/
      ## 关闭thymeleaf 页面缓存
      cache: false
  ```

- 编写IndexController 控制器转发视图

```java
@Controller
public class IndexController {

    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("msg","hello SpringBoot ");
        return "index";
    }
}
```

- html目录下添加index.html视图

修改Thymeleaf模板默认存放路径(在resources 目录下创建html文件夹)

  ```html
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>Title</title>
  </head>
  <body>
     <h1 th:text="${msg}"></h1>
  </body>
  </html>
  ```

-   启动Starter访问


![image-20200229221138069](images\image-20200229221138069.png)

## SpringBoot 静态资源访问

从入门项目中可以看到:对于Spring Mvc 请求拦截规则为‘/’,Spring Boot 默认静态资源路径如下:

```java
public class ResourceProperties {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
    private String[] staticLocations;
    private boolean addMappings;
    private final ResourceProperties.Chain chain;
    private final ResourceProperties.Cache cache;
```

即:我们可以在resources 资源目录下存放web 应用静态资源文件。

在resources 目录下创建static 或者public 存放images、js、css等静态资源文件  

![1564045870538](C:/工作/lotbyte/spring-boot资料/spring-boot课程/SpringBoot-视图/文档/images/1564045870538.png)

浏览器访问:

![1564045898635](images\1564045898635.png)

![1564045921556](images\1564045921556.png)

<img src="images\1564045973581.png" alt="1564045973581" style="zoom: 40%;" />



## SpringBoot应用打包与部署

​		当项目开发完毕进行部署上线时，需要对项目进行打包操,入门中构建的项目属于普通应用，由于SpringBoot内嵌Tomcat容器,所有打包后的jar包默认可以自行运行。

### Jar 包部署

#### 配置打包命令

​		idea 下配置clean compile package -Dmaven.test.skip=true 执行打包命令，target目录得到待部署的项目文件。

![image-20200229222802633](images\image-20200229222802633.png)

<img src="images\image-20200229222952894.png" alt="image-20200229222952894" style="zoom:50%;" />



#### 部署并访问

​	打开本地dos 窗口,执行java -jar 命令 部署已打好的jar包文件

![image-20200229223215826](images\image-20200229223215826.png)

浏览器访问

<img src="images\image-20200229223320543.png" alt="image-20200229223320543" style="zoom:50%;" />

### war包部署

​	War 包形式部署Web 项目在生产环境中是比较常见的部署方式，也是目前大多数web 应用部署的方案，这里对于Spring Boot Web 项目进行打包部署步骤如下

#### pom.xml修改

- 应用类型修改

由于入门项目构建的项目默认为jar应用，所以这里打war需要作如下修改

<img src="images\image-20200229224431677.png" alt="image-20200229224431677" style="zoom:80%;" />

- 内嵌tomcat忽略

构建SPringBoot应用时，引入的spring-boot-starter-web默认引入tomcat容器，这里忽略掉内容tomcat

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>

  <build>
    <!--
       配置生成的war文件名
	-->   
    <finalName>springboot</finalName>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
```

#### Starter修改

​      添加容器启动加载文件(类似于读取web.xml),这里通过继承SpringBootServletInitializer 类并重写configure方法来实现，在部署项目时指定外部tomcat 读取项目入口方法。

```java
@SpringBootApplication
public class Starter  extends SpringBootServletInitializer {
    private static Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        logger.info("SpringBoot 应用开始启动...");
        SpringApplication.run(Starter.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return  builder.sources(Starter.class);
    }
}
```

#### 部署并访问

- 外部tomcat部署并访问

<img src="images\image-20200229225119792.png" alt="image-20200229225119792" style="zoom: 70%;" />

![image-20200229225207545](images\image-20200229225207545.png)

![image-20200229225233963](images\image-20200229225233963.png)





## 总结

​		今天课程中给大家介绍了Spring框架的发展史，然后引入今天的SpringBoot框架，使用SpringBoot框架的最终目标是简化原生的Spring应用开发带来的繁琐配置，SpringBoot引入的全新的Starter坐标简化了原有坐标的引入方式，借助SpringBoot的自动化配置让开发者面更加专注业务本身的开发，而不是花费大量的时间来解决配置的问题，同时SpringBoot还提供了便捷的环境切换操作，做到不同环境方便快捷的切换，在开发web项目中遇到的视图整合与静态文件的访问操作，更多要归功于SpringBoot本身Starter坐标与自动化配置功能，最后给大家讲了SpringBoot打包与部署问题，相比较原有的Maven项目打包与部署更加



