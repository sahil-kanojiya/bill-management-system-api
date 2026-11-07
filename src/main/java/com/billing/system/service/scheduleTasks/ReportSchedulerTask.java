package com.billing.system.service.scheduleTasks;

import com.billing.system.model.Bill;
import com.billing.system.repository.BillRepository;
import com.billing.system.sender.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportSchedulerTask {


    private final BillRepository billRepository;

    private final EmailService emailService;

    @Value("${admin.email}")
    private String adminEmail;
    
//    @Scheduled(fixedRate = 60000) // Runs in every 60 sec
//    public void sendDailyReportToAdmin() {
//        LocalDate today = LocalDate.now();
//        List<Bill> todaysBills = billRepository.findByBillDate(today);
//        if (!todaysBills.isEmpty()) {
//            emailService.sendDailyReportEmail(todaysBills);
//        }
//    }

    @Scheduled(cron = "0 0 0 * * *") // Runs in every 24 hour
    public void sendDailyReportToAdmin() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Bill> yesterdayBills = billRepository.findByBillDate(yesterday);
        if (!yesterdayBills.isEmpty()) {
            emailService.sendDailyReportEmail(yesterdayBills);
        } else {
            log.info("No bills found for {}", yesterday);
        }
    }

}
