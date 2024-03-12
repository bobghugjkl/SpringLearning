package org.example.proxy;

public class HourseCompany implements RentHouse{
    private owners owners;

    public HourseCompany(org.example.proxy.owners owners) {
        this.owners = owners;
    }

    public void torenthourse() {
        System.out.println("西野七濑");
        owners.torenthourse();
    }
}
