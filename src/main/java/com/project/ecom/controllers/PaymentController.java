package com.project.ecom.controllers;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.dtos.CreatePaymentLinkRequestDto;
import com.project.ecom.dtos.PaymentDto;
import com.project.ecom.models.Payment;
import com.project.ecom.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment_link")
    public ResponseEntity<String> createPaymentLink(@RequestBody CreatePaymentLinkRequestDto requestDto) {
        String paymentLink = this.paymentService.getPaymentLink(requestDto.getCustomerId(), requestDto.getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentLink);
    }

    @GetMapping("/confirmation")
    public ResponseEntity<PaymentDto> getPayment(@RequestParam(name = "session_id") String sessionId) {
        Payment payment = this.paymentService.getPayment(sessionId);
        PaymentDto paymentDto = Reusable.mapPaymentToPaymentDto(payment);
        return ResponseEntity.ok(paymentDto);
    }
}
