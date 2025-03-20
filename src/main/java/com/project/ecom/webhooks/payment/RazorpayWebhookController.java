package com.project.ecom.webhooks.payment;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.dtos.PaymentDto;
import com.project.ecom.models.Payment;
import com.project.ecom.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook/razorpay")
public class RazorpayWebhookController {
    private IPaymentService paymentService;

    @Autowired
    public RazorpayWebhookController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment/confirmation")
    public ResponseEntity<PaymentDto> handlePaymentLinkPaid(@RequestParam(name = "razorpay_payment_id") String paymentId,
                                                            @RequestParam(name = "razorpay_payment_link_id") String paymentLinkId,
                                                            @RequestParam(name = "razorpay_payment_link_reference_id") String paymentLinkRefId,
                                                            @RequestParam(name = "razorpay_payment_link_status") String paymentLinkStatus,
                                                            @RequestParam(name = "razorpay_signature") String razorpaySignature) {
        Payment payment = this.paymentService.getPayment(paymentLinkId);
        PaymentDto paymentDto = Reusable.mapPaymentToPaymentDto(payment);
        return ResponseEntity.ok(paymentDto);
    }
}
