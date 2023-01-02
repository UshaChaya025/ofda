package com.cg.ofda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cg.ofda.repository.ICustomerRepository;
import com.cg.ofda.repository.IOrderRepository;
import com.cg.ofda.repository.IRestaurantRepository;
import com.cg.ofda.dto.ItemDto;
import com.cg.ofda.dto.OrderDetailsDto;
import com.cg.ofda.dto.RestaurantDto;
import com.cg.ofda.exception.CustomerException;
import com.cg.ofda.exception.OrderDetailsException;
import com.cg.ofda.exception.RestaurantException;

@Service
public class IOrderDetailsServiceImpl implements IOrderDetailsService {
	
		@Autowired
		IOrderRepository orderDetailsRepository;
		@Autowired
		ICustomerRepository customerRepository;
		@Autowired
		IRestaurantRepository restaurantRepository;
		
		/* @author : Satya 
	     * @return : orderDetailsDto
	     * @description : This method adds orderDetails in the repository and returns order details
	     */

		@Override
		public OrderDetailsDto addOrder(OrderDetailsDto orderdetails) throws OrderDetailsException {
			if(orderDetailsRepository.existsById(orderdetails.getOrderId())) {
				throw new OrderDetailsException("Order already exists");
			}
			return orderDetailsRepository.save(orderdetails);
		}
		
		/* @author : Satya 
	     * @return : orderDetailsDto
	     * @description : This method updates orderDetails in the repository and returns order details
	     */

		@Override
		public OrderDetailsDto updateOrder(OrderDetailsDto orderdetails) {
			  orderDetailsRepository.save(orderdetails);
				return orderdetails;
		}
		
		/* @author : Satya 
	     * @return : orderDetails 
	     * @description : This method removes orderDetails in the repository and returns order details
	     */
		
		@Override
		public OrderDetailsDto removeOrder(String ordId)  {
			OrderDetailsDto order = orderDetailsRepository.findById(ordId).get();
			orderDetailsRepository.delete(order);
			return order;
		}
		
		/* @author : Satya 
	     * @return : orderDetailsDto
	     * @description : This method retrieves the orderDetails by orderId
	     */

		
		@Override
		public OrderDetailsDto viewOrder(String ordId) {
			Optional<OrderDetailsDto> findById = orderDetailsRepository.findById(ordId);
			return findById.orElseThrow(() -> new OrderDetailsException("There are no orderdetails having id:" + ordId));
		}
		
		
		public List<OrderDetailsDto> viewAllOrders(){
			return orderDetailsRepository.findAll();
		}
		
		/* @author : Satya 
	     * @return : List<orderDetailsDto>
	     * @description : This method views all orderDetails by the customerId
	     */
		
		@Override
		public List<OrderDetailsDto> viewAllOrdersByCustomer(String custId) throws CustomerException {
			List<OrderDetailsDto> ord=orderDetailsRepository.findAll();
			List<OrderDetailsDto> customerorders=new ArrayList<>();
			if(customerRepository.existsById(custId)) {
			for(OrderDetailsDto o:ord)
			{
				String customerid =o.getCart().getCustomer().getCustomerId();
				
				if(customerid.equals(custId))
				{
					customerorders.add(o);
				}
			}
			return customerorders;
			
		}
			else 
				throw new CustomerException("Customer Id not found");
		}
		
		/* @author : Satya 
	     * @return : List<orderDetailsDto>
	     * @description : This method retrieves list of orderDetails by restaurantId
	     */
		
		public List<OrderDetailsDto> viewAllOrdersByRestaurant(String restId) throws RestaurantException{
			if(restaurantRepository.existsById(restId)) {
			List<OrderDetailsDto> ord=orderDetailsRepository.findAll();
			List<OrderDetailsDto> orders=new ArrayList<>();
			for(OrderDetailsDto o:ord)
			{
				List<ItemDto> items=o.getCart().getItemList();
				for(ItemDto item:items)
				{
					List<RestaurantDto> restaurants=item.getRestaurants();
					for(RestaurantDto restaurant:restaurants)
					{
						if(restId.equals(restaurant.getRestaurantId()))
								{
								orders.add(o);
								}
					}
				}
			}
			return orders;
			}
			else throw new RestaurantException("Restaurant Id not found");
		}	
}

