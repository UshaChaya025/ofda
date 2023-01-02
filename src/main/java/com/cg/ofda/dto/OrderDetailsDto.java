package com.cg.ofda.dto;


import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name = "OrderDetails")
public class OrderDetailsDto {
	
		@Id
		@Column(name = "Order_ID",length =3)
		@NotBlank(message = "order cannot be null")
		String orderId;
	 
		LocalDateTime orderDate;
		@Column( name = "Order_Status",length = 20)
		String orderStatus;
		
		@OneToOne(cascade=CascadeType.ALL)
		@JoinColumn(name = "cart_Id")
		FoodCartDto cart;

		public OrderDetailsDto() {
			super();

		}

		public OrderDetailsDto(String orderId, LocalDateTime localDateTime,FoodCartDto cart, String orderStatus) {
			super();
			this.orderId = orderId;
			this.orderDate = localDateTime;
			this.orderStatus = orderStatus;
			this.cart = cart;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public LocalDateTime getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(LocalDateTime orderDate) {
			this.orderDate = orderDate;
		}

		public FoodCartDto getCart() {
			return cart;
		}

		public void setCart(FoodCartDto cart) {
			this.cart = cart;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		@Override
		public String toString() {
			return "OrderDetails [orderId=" + orderId + ", orderDate=" + orderDate + ", orderStatus=" + orderStatus
					+ ", cart=" + cart + "]";
		}



}

