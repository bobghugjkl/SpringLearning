package org.example.proxy;
//代理角色
public class MarryCompany implements Marry{
    //代理角色调目标角色的方法
    private Marry target;
//    通过带参构造传递对象
    public MarryCompany(Marry target) {
        this.target = target;
    }

    public void toMarry() {
        before();
        target.toMarry();
        after();//用户行为增强
    }

    private void before() {
        System.out.println("婚礼布置");
    }

    private void after() {
        System.out.println("百年好合");
    }
}
