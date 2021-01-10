package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;

import java.util.Scanner;

public class NassaMenu implements ApplicationMenu {
    private static NassaMenu instance;
    private NassaMenu(){
    }
    public static NassaMenu getInstance(){
        if (instance == null){
            instance = new NassaMenu();
        }
        return instance;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }

    @Override
    public Object printAvailableOptions() {
        System.out.println("Choose an action:");
        System.out.println("1. Get info\n2. Update info\n3. Create mission\n4. Choose missions for output");
        return null;
    }

    @Override
    public Object handleUserInput(Object o) {
        Scanner scanner = new Scanner(System.in);
        int operation = scanner.nextInt();
        if (operation == 1){
            System.out.println("suck");
        }
        return null;
    }
}
