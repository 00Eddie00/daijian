package shop.daijian.gateway.web.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.interfaces.AuthService;
import shop.daijian.common.wrapper.ResultWrapper;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

/**
 * 认证过滤器
 * 将请求头或者请求参数中的Token校验并提取成userId
 * 并将userId加到请求头中交给Controller
 *
 * @author qiyubing
 * @since 2019/07/19
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Reference
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 移除userId请求头，防止恶意登录
        exchange.getRequest().mutate().headers((headers) -> headers.remove("userId"));
        ServerHttpRequest request = exchange.getRequest();
        String headerToken = request.getHeaders().getFirst(AUTHORIZATION);
        String paramToken = request.getQueryParams().getFirst(AUTHORIZATION);
        String token;
        if (headerToken != null) {
            // token在请求头中
            token = headerToken;
        } else if (paramToken != null) {
            // token在请求参数中
            token = paramToken;
        } else {
            log.debug("无token");
            // 若没有token，则直接放行
            return chain.filter(exchange);
        }
        // TODO 上线删除
        if ("token".equals(token)) {
            exchange.getRequest().mutate().headers((headers -> headers.set("userId", "1")));
            log.debug("走后门进入");
            return chain.filter(exchange);
        }
        // 截取到有效Token
        token = StringUtils.substringAfter(token, "Bearer ");
        log.debug("token = {}", token);

        if (StringUtils.isBlank(token)) {
            return response(exchange.getResponse(), ResultWrapper.failure(BaseStatusEnum.TOKEN_ERROR));
        }
        String userId;
        try {
            // 验证token并提取用户ID
            userId = authService.verifyAndGetUserId(token);
            log.debug("用户ID = {} 进入", userId);
        } catch (Exception e) {
            return response(exchange.getResponse(), ResultWrapper.failure(BaseStatusEnum.TOKEN_ERROR));
        }
        //向请求头中添加用户ID
        exchange.getRequest().mutate().headers((headers -> headers.set("userId", userId)));
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private Mono<Void> response(ServerHttpResponse response, ResultWrapper resultWrapper) {
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONBytes(resultWrapper));
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return response.writeWith(Mono.just(buffer));
    }
}
