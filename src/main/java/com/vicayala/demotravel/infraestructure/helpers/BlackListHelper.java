package com.vicayala.demotravel.infraestructure.helpers;

import com.vicayala.demotravel.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {

    public void isInCustomerBlackList(String customerId){
        if(customerId.equals("BBMB771012HMCRR022")){
            throw new ForbiddenCustomerException();
        }
    }
}
