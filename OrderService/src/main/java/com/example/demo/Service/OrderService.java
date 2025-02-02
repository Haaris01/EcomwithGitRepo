package com.example.demo.Service;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Models.Orders;
import com.example.demo.Repository.OrderRepo;
import com.example.demo.dmo.OrderDetails;
import com.example.demo.dmo.OrdersList;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;

@Service
public class OrderService {
	@Autowired
	OrderRepo orderrepository;
	
	

	public OrderResponse returnOrders(String userId)
	{
		Orders orders=orderrepository.findOrderByUserId(userId);
		OrderResponse response=OrderResponse.builder()
								.ordersList(orders.getOrdersList())
								.build();
		
		System.out.println("order fetched is "+ orders);
		return response;
	}
	
	public void createOrder(OrderRequest orderrequest )
	{
		System.out.println("orderequest "+orderrequest);
		Orders order1=orderrepository.findOrderByUserId(orderrequest.getUserId());
		if(order1==null)
		{
			System.out.println("Creating new order");
		
	        Orders order = Orders.builder()
	                            .userId(orderrequest.getUserId())
	                            .ordersList(new ArrayList<OrdersList> ())
	                            .build();
	//        
	//
	//        ArrayList<OrdersList> odl=new ArrayList<>();
	//        
	//        System.out.println("Date is "+(order.getOrdersList()));
	        
	        OrdersList ol=OrdersList.builder()
	        		      .datePlaced(orderrequest.getOrdersList().getDatePlaced())
	        		      .orderId(orderrequest.getOrdersList().getOrderId())
	        		      .orderDetails(new ArrayList<OrderDetails> ())
	        		      .build();
	        
	        
	        
	        ArrayList<OrderDetails> orderdetails=new ArrayList<>();
	        Iterator<OrderDetails> iter=orderrequest.getOrdersList().getOrderDetails().iterator();
	        while(iter.hasNext())
	        {
	        	OrderDetails orderdetails1=iter.next();
	        	OrderDetails od=OrderDetails.builder()
	        			        .productId(orderdetails1.getProductId())
	        			        .quantity(orderdetails1.getQuantity())
	        			        .deliveryOptionId(orderdetails1.getDeliveryOptionId())
	        			        .build();
	        	orderdetails.add(od);
	        	
	        }
	        ol.setOrderDetails(orderdetails);
	        order.getOrdersList().add(ol);
	//
	        orderrepository.save(order);
		}
		else
		{
			System.out.println("already exists");
//		
		}
	}
		
		public void updateOrder(@RequestBody OrderRequest orderrequest )
		{
			Orders order=orderrepository.findOrderByUserId(orderrequest.getUserId());
			Orders order1=order;
	        OrdersList ol=OrdersList.builder()
      		      .datePlaced(orderrequest.getOrdersList().getDatePlaced())
      		      .orderId(orderrequest.getOrdersList().getOrderId())
      		      .orderDetails(new ArrayList<OrderDetails> ())
      		      .build();
			
			
      
      
      
		      ArrayList<OrderDetails> orderdetails=new ArrayList<>();
		      Iterator<OrderDetails> iter=orderrequest.getOrdersList().getOrderDetails().iterator();
		      while(iter.hasNext())
		      {
		      	OrderDetails orderdetails1=iter.next();
		      	OrderDetails od=OrderDetails.builder()
		      			        .productId(orderdetails1.getProductId())
		      			        .quantity(orderdetails1.getQuantity())
		      			        .deliveryOptionId(orderdetails1.getDeliveryOptionId())
		      			        .build();
		      	orderdetails.add(od);
		      	
		      }
		      ol.setOrderDetails(orderdetails);
		      order1.getOrdersList().add(ol);
		      orderrepository.deleteByUserId(order.getUserId());;
		      orderrepository.save(order1);
		
    }
}
