# SpringMvc-第一天

## 学习目标

![image-20200213093129763](images\image-20200213093129763.png)

## MVC思想 & SpringMvc框架概念与特点

### 什么叫MVC？

​		模型-视图-控制器（MVC）是一个众所周知的以设计界面应用程序为基础的设计思想。它主要通过分离模型、视图及控制器在应用程序中的角色将业务逻辑从界面中解耦。通常，模型负责封装应用程序数据在视图层展示。视图仅仅只是展示这些数据，不包含任何业务逻辑。控制器负责接收来自用户的请求，并调用后台服务（service或者dao）来处理业务逻辑。处理后，后台业务层可能会返回了一些数据在视图层展示。控制器收集这些数据及准备模型在视图层展示。MVC模式的核心思想是将业务逻辑从界面中分离出来，允许它们单独改变而不会相互影响。

<img src="images\image-20200211214118917.png" alt="image-20200211214118917" style="zoom: 50%;" />

### 常见MVC框架比较运行性能比较

​		Jsp+servlet>struts1>spring mvc>struts2+freemarker>struts2,ognl,值栈。

开发效率上,基本正好相反。值得强调的是，spring mvc开发效率和struts2不相上下，但从目前来看，spring mvc 的流行度已远远超过struts2。

### Spring MVC是什么?

​		Spring MVC是Spring家族中的一个web成员, 它是一种基于Java的实现了Web MVC设计思想的请求驱动类型的轻量级Web框架，即使用了MVC架构模式的思想，将web层进行职责解耦，基于请求驱动指的就是使用请求-响应模型，框架的目的就是帮助我们简化开发，Spring MVC也是要简化我们日常Web开发的。

​		Spring MVC是服务到工作者思想的实现。前端控制器是DispatcherServlet；应用控制器拆为处理器映射器(Handler Mapping)进行处理器管理和视图解析器(View Resolver)进行视图管理；支持本地化/国际化（Locale）解析及文件上传等；提供了非常灵活的数据验证、格式化和数据绑定机制；提供了强大的约定大于配置（惯例优先原则）的契约式编程支持。

### Spring MVC能帮我们做什么?

1. 让我们能非常简单的设计出干净的Web层；
2. 进行更简洁的Web层的开发；
3. 天生与Spring框架集成（如IoC容器、AOP等）；
4. 提供强大的约定大于配置的契约式编程支持；
5. 能简单的进行Web层的单元测试；
6. 支持灵活的URL到页面控制器的映射；
7. 非常容易与其他视图技术集成，如jsp、Velocity、FreeMarker等等，因为模型数据不放在特定的API里，而是放在一个Model里（Map数据结构实现，因此很容易被其他框架使用）；
8. 非常灵活的数据验证、格式化和数据绑定机制，能使用任何对象进行数据绑定，不必实现特定框架的API；
9. 支持灵活的本地化等解析；
10. 更加简单的异常处理；
11. 对静态资源的支持；
12. 支持Restful风格。

## SpringMvc 请求流程 & 环境搭建与测试

### Spring MVC请求处理流程分析

![image-20200211215029496](images\image-20200211215029496.png)

​		Spring MVC框架也是一个基于请求驱动的Web框架，并且使用了前端控制器模式（是用来提供一个集中的请求处理机制，所有的请求都将由一个单一的处理程序处理来进行设计，再根据请求映射规则分发给相应的页面控制器（动作/处理器）进行处理。首先让我们整体看一下Spring MVC处理请求的流程：

1. 首先用户发送请求,请求被SpringMvc前端控制器（DispatherServlet）捕获；

2. 前端控制器(DispatherServlet)对请求URL解析获取请求URI,根据URI, 调用HandlerMapping；

3. 前端控制器(DispatherServlet)获得返回的HandlerExecutionChain（包括Handler对象以及Handler对象对应的拦截器）；

4. DispatcherServlet 根据获得的HandlerExecutionChain，选择一个合适的HandlerAdapter。（附注：如果成功获得HandlerAdapter后，此时将开始执行拦截器的preHandler(...)方法）；

5. HandlerAdapter根据请求的Handler适配并执行对应的Handler；HandlerAdapter(提取Request中的模型数据，填充Handler入参，开始执行Handler（Controller)。 在填充Handler的入参过程中，根据配置，Spring将做一些额外的工作：

   HttpMessageConveter： 将请求消息（如Json、xml等数据）转换成一个对象，将对象转换为指定的响应信息。

   数据转换：对请求消息进行数据转换。如String转换成Integer、Double等数据格式化：

   数据格式化。 如将字符串转换成格式化数字或格式化日期等

   数据验证： 验证数据的有效性（长度、格式等），验证结果存储到BindingResult或Error中）

6. Handler执行完毕，返回一个ModelAndView(即模型和视图)给HandlerAdaptor

7. HandlerAdaptor适配器将执行结果ModelAndView返回给前端控制器。

8. 前端控制器接收到ModelAndView后，请求对应的视图解析器。

9. 视图解析器解析ModelAndView后返回对应View;

10. 渲染视图并返回渲染后的视图给前端控制器。

11、最终前端控制器将渲染后的页面响应给用户或客户端

### Spring MVC优势

1. 清晰的角色划分：前端控制器（DispatcherServlet）、请求到处理器映射（HandlerMapping）、处理器适配器（HandlerAdapter）、视图解析器（ViewResolver）、处理器或页面控制器（Controller）、验证器（ Validator）、命令对象（Command 请求参数绑定到的对象就叫命令对象）、表单对象（Form Object 提供给表单展示和提交到的对象就叫表单对象）。
2. 分工明确，而且扩展点相当灵活，可以很容易扩展，虽然几乎不需要；
3. 和Spring 其他框架无缝集成，是其它Web框架所不具备的；
4. 可适配，通过HandlerAdapter可以支持任意的类作为处理器；
5. 可定制性，HandlerMapping、ViewResolver等能够非常简单的定制；
6. 功能强大的数据验证、格式化、绑定机制；
7. 利用Spring提供的Mock对象能够非常简单的进行Web层单元测试；
8. 本地化、主题的解析的支持，使我们更容易进行国际化和主题的切换。
9. 强大的JSP标签库，使JSP编写更容易。

​        还有比如RESTful（一种软件架构风格，设计风格而不是标准，只是提供了一组设计原则和约束条件。它主要用于客户端和服务器交互类的软件，目前了解即可）风格的支持、简单的文件上传、约定大于配置的契约式编程支持、基于注解的零配置支持等等。

### Spring Mvc 环境搭建与测试

#### 开发环境

Idea + Maven + Jdk1.8 +Jetty

#### 新建Maven webApp

Idea 下创建springmvc01工程 

#### pom.xml 坐标添加

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
  </properties>
  <dependencies>
    <!-- spring web -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>4.3.2.RELEASE</version>
    </dependency>
    <!-- spring mvc -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>4.3.2.RELEASE</version>
    </dependency>
    <!-- web servlet -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.0.1</version>
    </dependency>
  </dependencies>
<build>
 <plugins>
            <!-- 编译环境插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>2.3.2</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.mortbay.jetty</groupId>
                <artifactId>maven-jetty-plugin</artifactId>
                <version>6.1.25</version>
                <configuration>
                    <scanIntervalSeconds>10</scanIntervalSeconds>
                    <contextPath>/springmvc01</contextPath>
                </configuration>
            </plugin>
        </plugins>
</build>
```

#### 配置web.xml 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app id="WebApp_ID" version="3.0"
    xmlns="http://java.sun.com/xml/ns/javaee" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
    http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">    

    <!-- 编码过滤 utf-8 -->
    <filter>
        <description>char encoding filter</description>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- servlet请求分发器 --> 
    <servlet>
        <servlet-name>springMvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:servlet-context.xml</param-value>
        </init-param>
        <!-- 表示启动容器时初始化该Servlet -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springMvc</servlet-name>
        <!-- 这是拦截请求, /代表拦截所有请求,拦截所有.do请求 -->
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
</web-app>  
```

​	要想启动我们的springMvc 环境，目前对于mvc 框架的配置还未进行。以上在web.xml中引用了**servlet-context.xml** 文件

#### servlet-context.xml 配置

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.xxxx.springmvc.controller"></context:component-scan>

    <mvc:annotation-driven/>

    <!--配置视图解析器  默认的视图解析器- -->
    <bean id="defaultViewResolver"
          class="org.springframework.web.servlet.view.InternalResourceViewResolver">
      <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
      <property name="contentType" value="text/html" />
      <property name="prefix" value="/WEB-INF/jsp/" />
      <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

#### 页面控制器的编写

```java
@Controller
public class HelloController {
    
    /**
     * 请求映射地址  /hello.do
     * @return
     */
    @RequestMapping(value={"/hello","asdasdas","asfs"})
    public ModelAndView hello(){
        ModelAndView mv=new ModelAndView(); 
        mv.addObject("hello", "hello spring mvc");
        mv.setViewName("hello");
        return mv;   
    }
}
```

#### 添加视图页面

在WEB-INF 下新建jsp文件夹 ，并在文件加下新建hello.jsp

```html
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'hello.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head> 
  <body>
  <!-- el表达式接收参数值 -->
    ${hello}
  </body>
</html>
```

#### 启动jetty服务器

![image-20200213110416589](images\image-20200213110416589.png)

访问地址 http://localhost:8080/springmvc01/hello.do

![image-20200211222025505](images\image-20200211222025505.png)

至此，springmvc 环境搭建完毕

## URL地址映射配置 & 参数绑定

### URL 映射地址配置之@RequestMapping

在类前面定义，则将url和类绑定,在方法前面定义，则将url和类的方法绑定

```java
//url: http://localhost:8080/springmvc01/hello2/test01.do

@RequestMapping("test01")

public ModelAndView test01(){

  ModelAndView mv=new ModelAndView();

  mv.setViewName("hello");

  mv.addObject("hello", "hello test01");

  return mv;

}

//url: http://localhost:8080/springmvc01/hello2.do?test02

@RequestMapping(params="test02")

public ModelAndView test02(){

  ModelAndView mv=new ModelAndView();

  mv.setViewName("hello");

  mv.addObject("hello", "hello test02");

  return mv;

}

//url: http://localhost:8080/springmvc01/hello2/test03.do

@RequestMapping("test03")

public String test03(Model model){

  model.addAttribute("hello", "hello test03");

  return "hello";

}

//url: http://localhost:8080/springmvc01/hello2/test04.do

@RequestMapping("test04")

public String test04(ModelMap modelMap){

  modelMap.addAttribute("hello", "hello test04");

  return "hello";

}

//url: http://localhost:8080/springmvc01/hello2/test05.do

@SuppressWarnings("unchecked")

@RequestMapping("test05")

public String test05(Map model){

  model.put("hello", "hello test05 ");
  return "hello";
}
```

### 参数绑定

请求参数到处理器功能处理方法的方法参数上的绑定，对于参数绑定非常灵活

#### 基本数据类型、字符串数据绑定

```java
/**
 * 简单数据类型 值必须存在  不传可以通过默认值代替
 */
@RequestMapping("data1")
public void data1(@RequestParam(defaultValue="10",name="age")int age,
        @RequestParam(defaultValue="1",name="flag")boolean flag,
        @RequestParam(defaultValue="100",name="s")double s){
    System.err.println("age:"+age+":flag:"+flag+":s:"+s);
}

/**
 * 包装类型  值可以为空
 */
@RequestMapping("data2")
public void data2(Integer age,Double s){
    System.err.println("age:"+age+":s:"+s);
}


/**
 * 字符串注入
 * @param str
 */
@RequestMapping("data3")
public void data3(String str){
    System.err.println("str:"+str);
}

```

#### 数组类型

```java
@RequestMapping("/dataTest3")
public void getParamsData3(@RequestParam(value="ids")String[] ids){
    for(String id:ids){
        System.out.println(id+"---");
    }
}
```

#### vo 类型

```java
@RequestMapping("/dataTest4")
public void getParamsData4(User user){
    System.out.println(user);
}
```

#### list 类型

此时user 实体需要定义list 属性

```java
public class User {
    private int id;
    private String userName;
    private String userPwd;
    
    private List<Phone> phones=new ArrayList<Phone>();

  
    public List<Phone> getPhones() {
        return phones;
    }
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", userPwd="
                + userPwd + ", phones=" + phones + "]";
    }

}

```

  Phone实体

```java
public class Phone {
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Phone [num=" + num + "]";
    }
    
}

```

Jsp 页面定义

```html
<form action="dataTest5.do" method="post">
    <input name="phones[0].num" value="123456" />
    <input name="phones[1].num" value="4576" />
    <button type="submit"> 提交</button>
</form>
```

Controller 方法

```java
@RequestMapping("/dataTest5")
public void getParamsData5(User user){
System.out.println(user);
}
```

#### Set 类型

​		Set和List类似，也需要绑定在对象上，而不能直接写在Controller方法的参数中。但是，绑定Set数据时，必须先在Set对象中add相应的数量的模型对象。

```java
public class User {
    private int id;
    private String userName;
    private String userPwd;
    
    private Set<Phone> phones=new HashSet<Phone>();
    
 
    
    public User() {
        phones.add(new Phone());
        phones.add(new Phone());
        phones.add(new Phone());
    }
    /*public List<Phone> getPhones() {
        return phones;
    }
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }*/
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public Set<Phone> getPhones() {
        return phones;
    }
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }
}

```

Controller 方法:

```java
@RequestMapping("/dataTest6")
public void getParamsData6(User user){
    System.out.println(user);
}

```

表单页面

```html
<form action="dataTest6.do" method="post">
   <input name="phones[0].num" value="123456" />
   <input name="phones[1].num" value="4576" />
   <input name="phones[2].num" value="4576" />
   <button type="submit"> 提交</button>
</form>

```

#### Map 类型数据

Map最为灵活，它也需要绑定在对象上，而不能直接写在Controller方法的参数中。 

```java
public class User {
    private int id;
    private String userName;
    private String userPwd;
    
    private Set<Phone> phones=new HashSet<Phone>();
    
    private Map<String, Phone> map=new HashMap<String, Phone>();
    
    
    
    //private List<Phone> phones=new ArrayList<Phone>();
    
    
    
    
    public User() {
        phones.add(new Phone());
        phones.add(new Phone());
        phones.add(new Phone());
    }
    /*public List<Phone> getPhones() {
        return phones;
    }
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }*/
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public Set<Phone> getPhones() {
        return phones;
    }
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }
    public Map<String, Phone> getMap() {
        return map;
    }
    public void setMap(Map<String, Phone> map) {
        this.map = map;
    }
}
```

  Controller 方法

```java
@RequestMapping("/dataTest7")
public void getParamsData7(User user){
    Set<Entry<String, Phone>>  set=user.getMap().entrySet();
    for(Entry<String, Phone> entry:set){
        System.out.println(entry.getKey()+"--"+entry.getValue().getNum());
        
    }   
}
```

 表单页面

```html
<form action="dataTest7.do" method="post">
   <input name="map['1'].num" value="123456" />
   <input name="map['2'].num" value="4576" />
   <input name="map['3'].num" value="4576" />
   <button type="submit"> 提交</button>
</form>
```

## 请求转发与重定向的问题

SpringMvc 默认采用服务器内部转发的形式展示页面信息。同样也支持重定向页面。

### 转发与重定向代码实现

```java
/**
* 重定向到 jsp 中文会出现乱码 
*/
@RequestMapping("/queryView1")
public String queryView1(){
    return "redirect:v1.jsp?a=admin&b=123456";
}
/**
 * 重定向到 jsp  中文乱码解决 
 */
@RequestMapping("/queryView3")
public String queryView3(RedirectAttributes attr){
    attr.addAttribute("a", "admin");
    attr.addAttribute("b", "奥利给");
    return "redirect:v1.jsp";
}

/**
 * 重定向到 jsp ModelAndView1
 */
@RequestMapping("/queryView4")
public ModelAndView queryView4(RedirectAttributes attr){
    
    ModelAndView mv=new ModelAndView(); 
    attr.addAttribute("a", "admin");
    attr.addAttribute("b", "奥利给");
    mv.setViewName("redirect:v1.jsp");
    return mv;
}

/**
 * 重定向到 jsp ModelAndView2  mv 携带参数
 */
@RequestMapping("/queryView5")
public ModelAndView queryView5(){
    
    ModelAndView mv=new ModelAndView(); 
    mv.setViewName("redirect:v1.jsp");
    mv.addObject("a", "admin");
    mv.addObject("b", "奥利给");
    System.out.println("重定向。。。");
    return mv;
}

/**
 * 重定向到Controller 并传递参数
 */
@RequestMapping("/queryView6")
public String queryView6(RedirectAttributes attr){
    attr.addAttribute("a", "admin");
    attr.addAttribute("b", "奥利给");
    return "redirect:/user/queryUserById.do";
}

/**
 * 重定向到Controller modelandview
 * @return
 */
@RequestMapping("/queryView7")
public ModelAndView queryView7(){
    ModelAndView mv=new ModelAndView(); 
    mv.setViewName("redirect:/user/queryUserById.do");
    mv.addObject("a", "admin");
    mv.addObject("b", "奥利给");
    return mv;
}
重定向页面值获取  ${param.a}|||${param.b}

/**
     * 转发到视图
     */
@RequestMapping("/queryView8")
public ModelAndView queryView8(){
    ModelAndView mv=new ModelAndView(); 
    mv.setViewName("v1");
    mv.addObject("a", "admin");
    mv.addObject("b", "奥利给");
    return mv;
}

/**
 * 转发到controller
 */
@RequestMapping("/queryView9")
public ModelAndView queryView9(HttpServletRequest request){
    ModelAndView mv=new ModelAndView(); 
    mv.setViewName("forward:user/queryUserById2.do?a=admin&b=奥利给");
    return mv;
}

页面值获取 ${a}||${b}

```

## SpringMvc 之Json数据开发

### 基本概念

Json在企业开发中已经作为通用的接口参数类型，在页面（客户端）解析很方便。SpringMvc 对于json 提供了良好的支持，这里需要修改相关配置，添加json数据支持功能

#### @ResponseBody

​		该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。

返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用（通常用于ajax 请求）

#### @RequestBody

​		该注解用于读取Request请求的body部分数据，使用系统默认配置的HttpMessageConverter进行解析，然后把相应的数据绑定到要返回的对象上 ,再把HttpMessageConverter返回的对象数据绑定到 controller中方法的参数上

### 使用配置

#### pom.xml 添加json相关坐标

```xml
<!-- 添加json 依赖jar包 -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.7.0</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.7.0</version>
</dependency>   
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.7.0</version>
</dependency>

```

#### 修改servlet-context.xml 

```xml
<!-- mvc 请求映射 处理器与适配器配置 -->
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="org.springframework.http.converter.StringHttpMessageConverter" />
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter" />
    </mvc:message-converters>
</mvc:annotation-driven>
```

#### 注解使用

```java
/**
 * 
 * @author Administrator
 * json 数据操作
 */
@Controller
@RequestMapping("/user")
public class UserLoginController {
    
    @RequestMapping("/addUser")
    @ResponseBody
    public  User addUser(@RequestBody User user){
        System.out.println(user);
        return user;    
    }
    
    @RequestMapping("/getUser")
    @ResponseBody
    public  User getUser(User user){
        System.out.println(user);
        return user;    
    }
    
}

```

```html
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        function test1(){
            $.ajax({
                type:"post",
                url:"user/addUser.do",
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                data:'{"userName":"admin","userPwd":"123456"}',
                success:function(data){
                    alert(data.userName+"--"+data.userPwd);
                }   
            })      
        }   
        function test2(){
            $.ajax({
                type:"post",
                url:"user/getUser.do",
                data:"userName=admin&userPwd=123456",
                dataType:"json", 
                success:function(data){
                    alert(data.userName+"--"+data.userPwd);
                }   
            })
        }
    </script>   
  </head> 
  <body>
    <input type="button" value="请求响应json" onclick="test1()"/>
<input type="button" value="响应json" onclick="test2()"/>
  </body>
</html>
```

## 课程总结

​		本节课程主要给大家讲解SpringMvc第一天相关知识点内容，这里需要大家重点理解MVC核心思想，SpringMvc 请求执行原理，能够借助Idea + Maven环境完成SpringMvc 基本环境的搭建与测试操作，重点掌握SpringMvc 请求到Handler 方法映射对应的URL地址的配置、SpringMvc 环境下页面转发与重定向实现配置，在SpringMvc环境下能够实现Json形式的参数绑定操作以及Json结果到客户端响应的处理。

