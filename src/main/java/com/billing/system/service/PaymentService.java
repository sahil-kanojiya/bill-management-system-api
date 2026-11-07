package com.billing.system.service;


import com.billing.system.model.Order;
import com.billing.system.model.Payment;
import com.billing.system.repository.OrderRepository;
import com.billing.system.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    public Payment generatePaymentRecord(Order order,Boolean paidOrNot) {
        return Payment.builder()
                .order(order)
                .paymentAmount(order.getTotalAmount()).paymentDate(LocalDate.now())
                .paymentConfirmation(paidOrNot)
                .build();
    }

    public void makePayment(Integer orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("order not found"));
        order.getPayment().setPaymentConfirmation(true);
        orderRepository.save(order);
    }

}
