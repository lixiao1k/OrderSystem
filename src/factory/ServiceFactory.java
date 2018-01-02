package factory;

import service.OrderManageService;
import service.impl.OrderManageServiceImpl;

public class ServiceFactory {
    public static OrderManageService getOrderManageService(){
        return OrderManageServiceImpl.getInstance();
    }
}
