package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.context.impl.NassaMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {
    static ApplicationMenu start() throws InvalidStateException {
        ApplicationMenu applicationMenu = NassaMenu.getInstance();
        final Supplier<ApplicationContext> applicationContextSupplier = applicationMenu::getApplicationContext; // todo
        NassaContext nassaContext = NassaContext.getInstance();
        nassaContext.init();
        applicationMenu.printAvailableOptions();
        return applicationContextSupplier::get;
    }
}
