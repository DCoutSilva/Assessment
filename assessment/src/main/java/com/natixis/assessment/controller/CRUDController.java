package com.natixis.assessment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.natixis.assessment.model.Account;
import com.natixis.assessment.model.Product;
import com.natixis.assessment.model.User;
import com.natixis.assessment.repository.AccountRepository;
import com.natixis.assessment.repository.ProductRepository;
import com.natixis.assessment.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CRUDController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
		try{
			List<User> users = new ArrayList<User>();
			if (name == null)
				userRepository.findAll().forEach(users::add);
			else userRepository.findByNameContaining(name).forEach(users::add);
			
			if (users.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<> (users, HttpStatus.OK);
	
		}catch(Exception e){
			System.out.println("Exception getAllUsers: "+e.getMessage());
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(required = false) String name) {
		try{
			List<Account> accounts = new ArrayList<Account>();
			if (name == null)
				accountRepository.findAll().forEach(accounts::add);
			else accountRepository.findByAccountNameContaining(name).forEach(accounts::add);
			
			if (accounts.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<> (accounts, HttpStatus.OK);
	
		}catch(Exception e){
			System.out.println("Exception getAllAccounts: "+e.getMessage());
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) {
		try{
			List<Product> products = new ArrayList<Product>();
			if (name == null)
				productRepository.findAll().forEach(products::add);
			else productRepository.findByNameContaining(name).forEach(products::add);
			
			if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<> (products, HttpStatus.OK);
	
		}catch(Exception e){
			System.out.println("Exception getAllAccounts: "+e.getMessage());
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())
			return new ResponseEntity<User>(user.get(),HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent())
			return new ResponseEntity<Product>(product.get(),HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/accounts/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable("id") String id) {
		Optional<Account> account = accountRepository.findById(id);
		if(account.isPresent())
			return new ResponseEntity<Account>(account.get(),HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user){
		if(!User.mailValidation(user.getEmail())){
			System.out.println("createUser error: Invalid email adress");
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User> (userRepository.insert(user),HttpStatus.OK);
	}
	
	@PostMapping("/accounts")
	public ResponseEntity<Account> createAccount(@RequestBody Account account){
		if(!Account.ibanValidation(account.getIban())){
			System.out.println("createAccount error: Invalid IBAN");
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Account> (accountRepository.insert(account),HttpStatus.OK);
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		if(product.getPrice()<= 0.0){
			System.out.println("createProduct error: invalid price");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Product> (productRepository.insert(product), HttpStatus.OK);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") String id){
		if(!User.mailValidation(user.getEmail())){
			System.out.println("updateUser error: Invalid email adress");
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		 Optional<User> userData = userRepository.findById(id);
		 if (userData.isPresent()) {
		   User _user = userData.get();
		   _user.setName(user.getName());
		   _user.setEmail(user.getEmail());
		   _user.setAccounts(user.getAccounts());
		   return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
		 } else {
		   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") String id){
		if(product.getPrice()<= 0.0){
			System.out.println("updateProduct error: invalid price");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
        Optional<Product> productData = productRepository.findById(id);
	    if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setName(product.getName());
            _product.setPrice(product.getPrice());
            _product.setAccount(product.getAccount());
		    return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
		} else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/accounts/{id}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable("id") String id){
		if(!Account.ibanValidation(account.getIban())){
			System.out.println("updateAccount error: Invalid IBAN");
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		 Optional<Account> accountData = accountRepository.findById(id);
		 if (accountData.isPresent()) {
		    Account _account = accountData.get();
		    _account.setAccountName(account.getAccountName());
		    _account.setIban(account.getIban());
		    return new ResponseEntity<>(accountRepository.save(_account), HttpStatus.OK);
		 } else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id){
		try{
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			System.out.println("Exception deleteUser: "+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/users")
	public ResponseEntity<HttpStatus> deleteAllUsers(){
		try{
			userRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e){
			System.out.println("Exception deleteAllUsers: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") String id){
		try{
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			System.out.println("Exception deleteProduct: "+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/products")
	public ResponseEntity<HttpStatus> deleteAllProducts(){
		try{
			productRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e){
			System.out.println("Exception deleteAllProducts: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/accounts/{id}")
	public ResponseEntity<HttpStatus> deleteAccount(@PathVariable("id") String id){
		try{
			accountRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			System.out.println("Exception deleteAccount: "+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/accounts")
	public ResponseEntity<HttpStatus> deleteAllAccounts(){
		try{
			accountRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (Exception e){
			System.out.println("Exception deleteAllAccounts: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
