package com.zq.seller.task;

import com.zq.seller.enums.ChanEnum;
import com.zq.seller.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时对账任务
 */
@Component
public class VerifyTask {
    @Autowired
    private VerifyService verifyService;

    @Scheduled(cron = "0 0 1,3,5 * * ? ")
    public void makeVerificationFile(){
        Date yesterday = new Date(System.currentTimeMillis() - 24*60*60*1000);
        for (ChanEnum chanEnum : ChanEnum.values()) {
            verifyService.makeOrderFile(chanEnum.getChanId(), yesterday);
        }
        for (ChanEnum chanEnum : ChanEnum.values()) {
            verifyService.saveChanOrders(chanEnum.getChanId(), yesterday);
        }

    }

    @Scheduled(cron = "0 0 2,4,6 * * ? ")
    public void verify(){
        Date yesterday = new Date(System.currentTimeMillis() - 24*60*60*1000);
        for (ChanEnum chanEnum : ChanEnum.values()) {
            verifyService.verifyOrder(chanEnum.getChanId(), yesterday);
        }
    }
}
