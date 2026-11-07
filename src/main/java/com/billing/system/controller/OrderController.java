package com.billing.system.controller;
import com.billing.system.model.Bill;
import com.billing.system.model.dto.OrderRequestDTO;
import com.billing.system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO order) {
        try {
            Bill bill = orderService.placeOrder(order);
            return ResponseEntity.accepted().body(bill);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error placing order: " + e.getMessage());
        }
    }

    @PostMapping("/place-all")
    public ResponseEntity<?> placeOrder(@RequestBody List<OrderRequestDTO> orders) {
        try {
            orderService.placeOrders(orders);
            return ResponseEntity.ok("All Orders Placed Successfully");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error placing order: " + e.getMessage());
        }
    }

}
