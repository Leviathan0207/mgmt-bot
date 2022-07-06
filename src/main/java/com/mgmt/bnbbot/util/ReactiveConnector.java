package com.mgmt.bnbbot.util;

import com.mgmt.bnbbot.config.ConnectionProperties;
import com.mgmt.bnbbot.constants.Host;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface ReactiveConnector {

	<RES> Mono<RES> reactiveGet(Host host, String requestPath, Map<String, List<String>> queryParams,
			Map<String, String> pathParams, Class<RES> responseType);

	<RES> Mono<RES> reactiveGet(Host host, String requestPath, Map<String, List<String>> queryParams,
			Map<String, String> pathParams, Class<RES> responseType, Consumer<HttpHeaders> postProcessHeader);

	<RES> Mono<RES> reactivePost(Host host, String requestPath, Object requestBody, Class<RES> responseType);

	<RES> Mono<RES> reactivePost(Host host, String requestPath, Object requestBody, Class<RES> responseType,
			Consumer<HttpHeaders> postProcessHeader);

	<RES> Flux<RES> reactiveDownloadFile(String uri, MediaType fileType, Class<RES> objectClass);

}
