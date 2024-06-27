package com.hoa.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hoa.exception.OrderException;
import com.hoa.model.Address;
import com.hoa.model.Cart;
import com.hoa.model.CartItem;
import com.hoa.model.Order;
import com.hoa.model.OrderItem;
import com.hoa.model.User;
import com.hoa.repository.AddressRepository;
import com.hoa.repository.CartRepository;
import com.hoa.repository.OrderItemRepository;
import com.hoa.repository.OrderRepository;
import com.hoa.repository.UserRepository;

@Service
public class OrderServiceImplementation implements OrderService{
	
	private OrderRepository orderRepository;
	private CartService cartService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;
	
	public OrderServiceImplementation(OrderRepository orderRepository,
			CartService cartService,
			AddressRepository addressRepository,
			UserRepository userRepository,
			OrderItemService orderItemService,
			OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.cartService = cartService;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.orderItemService = orderItemService;
		this.orderItemRepository = orderItemRepository;
	}
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address = addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item:cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder = orderRepository.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt = orderRepository.findById(orderId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new OrderException("Order not exist with id - " + orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders = orderRepository.getUserOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
		
		return null;
	}

}
