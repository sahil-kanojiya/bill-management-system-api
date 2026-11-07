package com.billing.system.service;


import com.billing.system.model.*;
import com.billing.system.model.dto.OrderRequestDTO;
import com.billing.system.repository.CustomerRepository;
import com.billing.system.repository.OrderRepository;
import com.billing.system.repository.ProductRepository;
import com.billing.system.sender.EmailService;
import com.billing.system.sender.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BillService billService;
    private final PaymentService paymentService;
    private final EmailService emailService;
    private final MessageService messageSenderService;

    @Transactional
    public Bill placeOrder(OrderRequestDTO orderRequest) throws Exception {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new Exception("Customer Not Found"));
        Product product=productRepository.findById(orderRequest.getProductId())
                .orElseThrow(() -> new Exception("Product Not Found"));
        if (product.getStock().getStockQuantity()<orderRequest.getQuantity()){
            throw new Exception("not sufficient quantity");
        }
        Order order=Order.builder()
                .customer(customer)
                .product(product)
                .odrerDate(LocalDate.now())
                .productCount(orderRequest.getQuantity())
                .build();
        if (orderRequest.getPaymentStatus()){
            Bill bill=billService.generateBill(order,orderRequest.getPaymentStatus());
            order.setBill(bill);
            order.setTotalAmount(bill.getNetAmount());
            Payment payment=paymentService.generatePaymentRecord(order,orderRequest.getPaymentStatus());
            order.setPayment(payment);
            customer.getOrders().add(order);
            product.getStock().setStockQuantity(product.getStock().getStockQuantity()-orderRequest.getQuantity());
            if (product.getStock().getStockQuantity()<product.getStock().getThresholdLevel()){
                emailService.sendLowStockAlertEmail(product);
            }
            orderRepository.save(order);
            emailService.sendOrderSuccessEmail(order);
//            messageSenderService.sendSms(order);
//            messageSenderService.sendWhatsAppMessage(order);
            return bill;
        }else {
            throw new Exception("payment is not done");
        }
    }

    @Transactional
    public void placeOrders(List<OrderRequestDTO> orders) throws Exception {
//        List<Order> orderList=new LinkedList<>();
//        try{
//            for (OrderRequestDTO orderRequest:orders){
//                //check for the stock
//                Customer customer = customerRepository.findById(orderRequest.getCustomerId())
//                        .orElseThrow(() -> new Exception("Customer Not Found"));
//                Product product=productRepository.findById(orderRequest.getProductId())
//                        .orElseThrow(() -> new Exception("Product Not Found"));
//                Order order=Order.builder()
//                        .customer(customer)
//                        .product(product)
//                        .odrerDate(LocalDate.now())
//                        .productCount(orderRequest.getQuantity())
//                        .build();
//                Bill bill=billService.generateBill(order,orderRequest.getPaymentStatus());
//                order.setBill(bill);
//                order.setTotalAmount(bill.getNetAmount());
//                Payment payment=paymentService.generatePaymentRecord(order,orderRequest.getPaymentStatus());
//                order.setPayment(payment);
//                payment.setOrder(order);
//                //        reduce stock
//            }
//        }catch (Exception e){
//            throw new Exception("order can not be placed");
//        }
//        orderRepository.saveAll(orderList);
    }

}
