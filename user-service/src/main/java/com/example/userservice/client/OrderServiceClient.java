package com.example.userservice.client;

import com.example.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="order-service") // 마이크로 서비스 name 입력
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders") // 실제 order-service의 controller api (end point) 주소 가져옴
    List<ResponseOrder> getOrders(@PathVariable String userId);

}
