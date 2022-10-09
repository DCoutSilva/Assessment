package com.natixis.assessment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.natixis.assessment.model.Account;
import com.natixis.assessment.model.Payment;
import com.natixis.assessment.model.Product;
import com.natixis.assessment.repository.AccountRepository;
import com.natixis.assessment.repository.PaymentRepository;
import com.natixis.assessment.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/pay")
public class PaymentController {
	
	@Autowired
	AccountRepository accountRepository;
		
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	
	@PostMapping("/link")
	public ResponseEntity<String> getPaymentLink(@RequestBody Payment payment ){
		try{
			Optional<Account> account = accountRepository.findById(payment.getAccountId());
			if(!account.isPresent()){
				System.out.println("getPaymentLink error: Bad account ID");
				return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
			}
			
			List<Product> _products = new ArrayList<Product>();
			for (Product p: payment.getProducts()){
				Optional<Product> _p = productRepository.findById(p.getId());
				if (_p.isPresent()) _products.add(_p.get());
				else {
					System.out.println("getPaymentLink error: Bad product ID");
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			
			payment = paymentRepository.insert(payment);
			
			String link = "http://localhost:8080/pay/payment/?id=" + payment.getId();
			return new ResponseEntity<String>(link,HttpStatus.OK);
		}catch (Exception e){
			System.out.println("getPaymentLink exception: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/payment")
	public ResponseEntity<Payment> getPaymentDetails(@RequestParam(required = true) String id){
		if(id == null || id.equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<Payment> payment = paymentRepository.findById(id);
		if(payment.isPresent())
			return new ResponseEntity<Payment>(payment.get(),HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
		
}
