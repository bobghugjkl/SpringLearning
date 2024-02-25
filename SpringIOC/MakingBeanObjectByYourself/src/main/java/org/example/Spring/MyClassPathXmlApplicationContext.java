package org.example.Spring;

import org.dom4j.Document;
import org.dom4j.Element;

import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//模拟Spring的实现
/*
通过构造器得到对应的配置文件
通过dom4j解析配置文件（xml）得到一个list集合，存放bean标签的id和class属性
通过反射得到对应的实例化对象，放置在map中，可以通过Id找指定内容（遍历list集合，获取对应的class属性，利用class.forname(class).newIntances()得到对应的实例化对象）
获取指定的实例化好的对象
 */
public class MyClassPathXmlApplicationContext implements MyFactory{
    private List<MyBean>beanList;//存放从配置文件中获取到的bean标签的id和class属性(Mybean means every-bean object)
    private Map<String,Object> beanMap = new HashMap<>();//存放实例好的对象
//通过构造器得到对应的配置文件
    public MyClassPathXmlApplicationContext(String fileName) {
//        通过dom4j解析配置文件（xml）得到一个list集合，存放bean标签的id和class属性
        this.parseXml(fileName);
//        通过反射得到对应的实例化对象，放置在map中，可以通过Id找指定内容（遍历list集合，获取对应的class属性，利用class.forname(class).newIntances()得到对应的实例化对象）
        this.instanceBean();
    }

    private void instanceBean() {
        /*
        通过反射得到对应的实例化对象，放置在map对象
        1.判断对象集合是否为空，不为空则遍历集合获取id和class属性
        2.通过类的全路径名反射得到实例化对象Class.forName(class).newInstance()
        3.将对应的id和实例化好的bean对象设置到map对象中
         */
//        first
        if(beanList != null && beanList.size()>0){
            for (MyBean bean:
                 beanList) {
                String id = bean.getId();
                String clazz = bean.getClazz();
//                second
                try{
                    Object object = Class.forName(clazz).newInstance();
//                    third
                    beanMap.put(id,object);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseXml(String fileName) {
        /*
        1.获取解析器
        2.获取配置文件url
        3.通过解析器解析配置文件xml
        4.通过xpath语法解析，获取Beans下的所有bean标签
        5.通过指定的解析语法解析文档对象，返回集合
        6.判断元素集合是否为空
        7.如果元素集合不为空，遍历集合，获取bean标签元素的属性（id和class属性）
        8.获取mybean对象，将id和class属性值设置到对象中，再将对象设置到集合中
         */
//        第一步
        SAXReader saxReader = new SAXReader();
//        第二步
        URL url = this.getClass().getClassLoader().getResource(fileName);
//        第三步
        try{
            Document document = saxReader.read(url);
            //        第四步
            XPath xPath = document.createXPath("beans/bean");
//            第五步,访问的是一个元素集合
            List<Element> elementList = xPath.selectNodes(document);
//            第六步
            if(elementList != null&&elementList.size() > 0){
//                实例化beanlist
                beanList = new ArrayList<MyBean>();
//                第七步
                for (Element el :
                        elementList) {
                    String id = el.attributeValue("id");
                    String clazz = el.attributeValue("class");
//                    第八步
                    MyBean myBean = new MyBean(id,clazz);
                    beanList.add(myBean);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
//通过id选取对应的map中的value
    public Object getBean(String id) {
        Object object = beanMap.get(id);
        return object;
    }
}
