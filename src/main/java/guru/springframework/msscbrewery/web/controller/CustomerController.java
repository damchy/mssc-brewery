package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"/{customerId}"})
    public ResponseEntity<CustomerDto> getBeer(@PathVariable("customerId") UUID customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity handlePost(@RequestBody CustomerDto customerDto){
        CustomerDto savedDto = customerService.saveNewCustomer(customerDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/customer/" + savedDto.getId().toString());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleUpdate(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto){
        customerService.updateCustomer(customerId, customerDto);
    }

    @DeleteMapping("/{customerId}")
    public void deleteById(@PathVariable("customerId")  UUID customerId){
        customerService.deleteById(customerId);
    }
}
