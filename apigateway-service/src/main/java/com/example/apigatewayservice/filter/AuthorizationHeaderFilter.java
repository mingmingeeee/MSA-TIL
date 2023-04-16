package com.example.apigatewayservice.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    // login -> token 반환 받음
    // client -> users (with token) -> server: token 열고 맞는 지 아닌지 화인 (header에 token 있음)
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }
            
            String authorizationHeader = request.getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");
            
            if(!isJwtValid(jwt)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }
            
            

            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;


        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception ex) {
            returnValue = false;
        }

        if(subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

    // Mono, Flux -> Spring WebFlux (비동기 처리)
    // 단일 값: Mono, 다중 값: Flux
    // Spring MVC VS Flux => 나중에 찾아보기
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    public static class Config {

    }

}
