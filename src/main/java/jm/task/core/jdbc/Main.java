package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        //userService.createUsersTable();
        userService.saveUser("Tom", "White", (byte)23);
        userService.saveUser("Jack", "Black", (byte)45);
        userService.saveUser("Nick", "Red", (byte)36);
        //userService.saveUser("Rob", "Green", (byte)28);
        //userService.removeUserById(2);
        //userService.cleanUsersTable();

        System.out.println(userService.getAllUsers());
        //userService.dropUsersTable();

    }
}
