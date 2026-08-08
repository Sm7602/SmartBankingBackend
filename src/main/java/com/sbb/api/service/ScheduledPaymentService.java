package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.ScheduledPaymentRepository;
import com.sbb.api.dto.scheduledpayment.ScheduledPaymentRequest;
import com.sbb.api.dto.scheduledpayment.ScheduledPaymentResponse;
import com.sbb.api.dto.scheduledpayment.ScheduledPaymentUpdateRequest;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.entity.ScheduledPayment;
import com.sbb.api.entity.Customer;

@Service
public class ScheduledPaymentService {

    @Autowired
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    private ScheduledPaymentResponse  convertToResponse(ScheduledPayment scheduledPayment) {

        return ScheduledPaymentResponse .builder()
        		     .id(scheduledPayment.getId())
        		     .paymentReference(scheduledPayment.getPaymentReference())
        		     .paymentTitle(scheduledPayment.getPaymentTitle())
        		     .amount(scheduledPayment.getAmount())
        		     .beneficiaryAccountNumber(scheduledPayment.getBeneficiaryAccountNumber())
        		     .ifscCode(scheduledPayment.getIfscCode())
        		     .nextPaymentDate(scheduledPayment.getNextPaymentDate())
        		     .frequency(scheduledPayment.getFrequency())
        		     .createdAt(scheduledPayment.getCreatedAt())
                 .updatedAt(scheduledPayment.getUpdatedAt())
                 .active(scheduledPayment.getActive())
                 .customer(scheduledPayment.getCustomer())
                 .build();

    }

    public ScheduledPaymentResponse  createScheduledPayment(ScheduledPaymentRequest request) {
        System.out.println("ScheduledPaymentService.createScheduledPayment()");
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() ->
                        new RuntimeException("Customer not found"));
        
        ScheduledPayment scheduledPayment=ScheduledPayment.builder()
        		 .paymentReference("SP" + System.currentTimeMillis())
   		     .paymentTitle(request.getPaymentTitle())
   		     .amount(request.getAmount())
   		     .beneficiaryAccountNumber(request.getBeneficiaryAccountNumber())
   		     .ifscCode(request.getIfscCode())
   		     .nextPaymentDate(request.getNextPaymentDate())
   		     .frequency(request.getFrequency())
   		     .createdAt(LocalDateTime.now())
             .updatedAt(LocalDateTime.now())
             .active(true)
             .customer(customer)
             .build();
        	

        scheduledPayment= scheduledPaymentRepository.save(scheduledPayment); 
        return convertToResponse(scheduledPayment);
    }

    public ScheduledPaymentResponse  getScheduledPaymentById(Long id) {
        System.out.println("ScheduledPaymentService.getScheduledPaymentById()");
        ScheduledPayment scheduledPayment= scheduledPaymentRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Scheduled Payment not found"));
        return convertToResponse(scheduledPayment);
    }

    public List<ScheduledPaymentResponse > getScheduledPaymentsByCustomerId(Long customerId) {
        System.out.println("ScheduledPaymentService.getScheduledPaymentsByUserId()");
        return scheduledPaymentRepository.findByCustomerId(customerId)
        		    .stream()
	            .map(this::convertToResponse)
	            .toList();
    }

    public ScheduledPaymentResponse  updateScheduledPayment(Long id,ScheduledPaymentUpdateRequest request) {
        System.out.println("ScheduledPaymentService.updateScheduledPayment()");
        ScheduledPayment existing =scheduledPaymentRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Scheduled Payment not found"));

        existing.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        existing.setAmount(request.getAmount());
        existing.setFrequency(request.getFrequency());
        existing.setNextPaymentDate(request.getNextPaymentDate());
        existing.setActive(request.getActive());
        existing.setUpdatedAt(LocalDateTime.now());

        existing= scheduledPaymentRepository.save(existing); 
        return convertToResponse(existing);
    }

    public ScheduledPaymentResponse  activateScheduledPayment(Long id) {
        System.out.println("ScheduledPaymentService.activateScheduledPayment()");
        ScheduledPayment payment = scheduledPaymentRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Scheduled Payment not found"));

        payment.setActive(true);
        payment.setUpdatedAt(LocalDateTime.now());
       
        
        payment= scheduledPaymentRepository.save(payment); 
        return convertToResponse(payment);
    }

    public ScheduledPaymentResponse  deactivateScheduledPayment(Long id) {
        System.out.println("ScheduledPaymentService.deactivateScheduledPayment()");
        ScheduledPayment payment = scheduledPaymentRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Scheduled Payment not found"));

        payment.setActive(false);
        payment.setUpdatedAt(LocalDateTime.now());
       
        
        payment= scheduledPaymentRepository.save(payment); 
        return convertToResponse(payment);
    }

    public void deleteScheduledPayment(Long id) {
        System.out.println("ScheduledPaymentService.deleteScheduledPayment()");
        ScheduledPayment payment =scheduledPaymentRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Scheduled Payment not found"));
        scheduledPaymentRepository.delete(payment);
    }
}