package com.chuanqihou.powershop.filter;

import com.chuanqihou.powershop.constant.AuthConstants;
import com.chuanqihou.powershop.model.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 传奇后
 * @date 2023/6/19 18:55
 * @description token校验过滤器
 */
@Slf4j
@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {

    /**
     * redis操作模板
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 和前端约定好token放在header里面 Authorization:bearer token
     * @param exchange 当前请求的上下文
     * @param chain 过滤器链
     * @return Mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取到请求路径
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        log.info("请求路径：{}", path);
        //判断路径是否放行
        if (AuthConstants.ALLOW_URLS.contains(path)){
            // 如果为放行路径，直接放行
            return chain.filter(exchange);
        }
        //获取token
        String authorizationValue = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION);
        //判断token是否存在
        if (StringUtils.hasText(authorizationValue)) {
            String token = authorizationValue.replace(AuthConstants.BEARER, "");
            //使用redis效验token
            String redisKey = AuthConstants.TOKEN_REDIS_PREFIX + token;
            String tokenValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.hasText(tokenValue)) {
                //获取token过期时间
                Long expire = redisTemplate.getExpire(redisKey);
                // 判读token是否过期以及是否需要刷新token
                if (!ObjectUtils.isEmpty(expire) && expire != -2 && expire < AuthConstants.TOKEN_REFRESH_EXPIRE_TIME) {
                    //如果token存在,并且token过期时间小于5分钟，则刷新token时间
                    redisTemplate.expire(redisKey, AuthConstants.TOKEN_EXPIRE, AuthConstants.TOKEN_EXPIRE_TIME_UNIT);
                }
                //放行
                return chain.filter(exchange);
            }
        }
        //拦截
        //设置响应头
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        //设置响应状态码
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            //将对象转换为byte数组
            bytes = objectMapper.writeValueAsBytes(Result.fails(HttpStatus.UNAUTHORIZED.value(), "请先登录"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //将byte数组转换为响应对象
        DataBuffer data = response.bufferFactory().wrap(bytes);
        //响应数据
        return response.writeWith(Mono.just(data));
    }

    /**
     * 过滤器执行顺序
     * @return int
     */
    @Override
    public int getOrder() {
        //数字越小，优先级越高
        return 10;
    }
}
