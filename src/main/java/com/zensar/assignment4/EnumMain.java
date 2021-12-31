package com.zensar.assignment4;

import com.zensar.assignment4.models.Order;
import com.zensar.assignment4.models.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnumMain {

    private static List<Order> orderList;

    public static void main(String[] args) {
        fillOrders();
        changeStatus(1, 3);
        changeStatus(2, 4);
        changeStatus(3, 2);
    }

    private static void changeStatus(int id, int status) {
        Optional<Order> order = orderList.stream().filter(order1 -> order1.getId() == id).findFirst();
        if (order.isPresent()) {
            Order tempOrder = order.get();
            tempOrder.setStatus(Status.getStatus(status));
            orderList.set(id - 1, tempOrder);
        }
        System.out.println("\n\n\nOrder List after update");
        orderList.forEach(System.out::println);
    }

    private static void fillOrders() {
        orderList = new ArrayList<>();
        orderList.add(new Order(1, "order 1", 5, 5000, Status.NEW));
        orderList.add(new Order(2, "order 2", 10, 3200, Status.ACCEPTED));
        orderList.add(new Order(3, "order 3", 20, 4500, Status.NEW));
        orderList.add(new Order(4, "order 4", 6, 7800, Status.COMPLETED));
        orderList.add(new Order(5, "order 5", 12, 7400, Status.COMPLETED));

        System.out.println("Initial Order List");
        orderList.forEach(System.out::println);
    }


}
