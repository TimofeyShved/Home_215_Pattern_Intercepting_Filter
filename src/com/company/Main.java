package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static FilterManager filterManager = new FilterManager();

    public static void main(String[] args) {
	    // Паттерн (набор фильтров) иногда приходится проходить через несколько фильтров,
        // для этого мы создаем фильтры, класс объеденяющий фильтры
        // и сам менеджер через который мы работаем с фильтрами

        filterManager.setFilter(new AuthFilter());
        filterManager.setFilter(new IpFilter());
        filterManager.getFilterReqvest("home");
    }
}

// наши фильтры
interface Filter{
    void execute(String data);
}

class AuthFilter implements  Filter{
    @Override
    public void execute(String data) {
        System.out.println("auth filter data: " + data);
    }
}

class IpFilter implements  Filter{
    @Override
    public void execute(String data) {
        System.out.println("ip filter data: " + data);
    }
}

// класс объеденяющий фильтры
class FilterChain{
    List<Filter> filterList = new ArrayList<>();
    Target target = new Target();

    void add(Filter filter){
        filterList.add(filter);
    }

    void filter(String data){
        for (Filter f: filterList){
            f.execute(data);
        }
        target.doJob(data);
    }
}

// если фильтры пройдены успешно
class Target{
    void doJob(String data){
        System.out.println("data " + data + " in job");
    }
}

// менеджер через который мы работаем с фильтрами
class FilterManager{
    FilterChain filterChain = new FilterChain();

    public void getFilterReqvest(String reqvest) {
        filterChain.filter(reqvest);
    }

    public void setFilter(Filter filter) {
        filterChain.add(filter);
    }
}

