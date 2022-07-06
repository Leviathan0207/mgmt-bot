package com.mgmt.bnbbot.util;

import com.mgmt.bnbbot.config.ConnectionProperties;
import com.mgmt.bnbbot.constants.Host;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveConnectorImpl implements ReactiveConnector {

	private final ConnectionProperties props;

	private final WebClient webClient;

	@Override
	public <RES> Mono<RES> reactiveGet(Host host, String requestPath, Map<String, List<String>> queryParams,
			Map<String, String> pathParams, Class<RES> responseType) {
		return this.reactiveGet(host, requestPath, queryParams, pathParams, responseType, null);
	}

	@Override
	public <RES> Mono<RES> reactiveGet(Host host, String requestPath, Map<String, List<String>> queryParams,
			Map<String, String> pathParams, Class<RES> responseType, Consumer<HttpHeaders> postProcessHeader) {
		ConnectionProperties.ServerConnection connection = props.getConnection(host);
		return Mono.just(connection).map(h -> webClient.get()
				.uri(UriComponentsBuilder.fromUriString(props.getConnection(host) + requestPath)
						.queryParams(CollectionUtils.toMultiValueMap(queryParams)).buildAndExpand(pathParams).toUri())
				.headers(httpHeaders -> {
					if (null != postProcessHeader) {
						postProcessHeader.accept(httpHeaders);
					}
				})).flatMap(requestHeadersSpec -> processConnection(connection, requestHeadersSpec)
						.map(v -> requestHeadersSpec))
				.map(requestSpec -> requestSpec.headers(headers -> {
					if (null != postProcessHeader) {
						postProcessHeader.accept(headers);
					}
				})).flatMap((req -> req.retrieve().bodyToMono(responseType)));
	}

	private Mono<WebClient.RequestHeadersSpec<?>> processConnection(ConnectionProperties.ServerConnection connection,
			WebClient.RequestHeadersSpec<?> requestHeadersSpec) {
		return Mono.just(connection)
				.filter(c -> ConnectionProperties.BasicAuthConnection.class.isAssignableFrom(c.getClass()))
				.doOnNext(c -> {
					ConnectionProperties.BasicAuthConnection auth = (ConnectionProperties.BasicAuthConnection) connection;
					requestHeadersSpec.headers(hdrs -> hdrs.setBasicAuth(auth.getUsername(), auth.getPassword()));
				}).switchIfEmpty(Mono.just(connection))
				.filter(c -> ConnectionProperties.BearerAuthConnection.class.isAssignableFrom(c.getClass()))
				.flatMap(c -> {
					ConnectionProperties.BearerAuthConnection bearerAuth = (ConnectionProperties.BearerAuthConnection) c;
					ConnectionProperties.ServerConnection sConnection = new ConnectionProperties.ServerConnection();
					return Mono.just(sConnection);
				}).switchIfEmpty(Mono.just(connection)).doOnNext(c -> {
					requestHeadersSpec.headers(headers -> {
						if (null != c.getHeaders() && !c.getHeaders().isEmpty()) {
							c.getHeaders().forEach(headers::add);
						}
						if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
							headers.setContentType(MediaType.APPLICATION_JSON);
						}
						headers.setCacheControl(CacheControl.noCache());
					});
				}).map(v -> requestHeadersSpec);
	}

	@Override
	public <RES> Mono<RES> reactivePost(Host host, String requestPath, Object requestBody, Class<RES> responseType) {
		return this.reactivePost(host, requestPath, requestBody, responseType, null);
	}

	@Override
	public <RES> Mono<RES> reactivePost(Host host, String requestPath, Object requestBody, Class<RES> responseType,
			Consumer<HttpHeaders> postProcessHeader) {
		ConnectionProperties.ServerConnection connection = props.getConnection(host);
		return Mono.just(connection).map(c -> webClient.post().uri(connection.getUrl() + requestPath))
				.flatMap(requestSpec -> processConnection(connection, requestSpec).map(v -> requestSpec))
				.map(requestSpec -> requestSpec.headers(headers -> {
					if (postProcessHeader != null) {
						postProcessHeader.accept(headers);
					}
				})).flatMap(req -> req.bodyValue(requestBody).retrieve().bodyToMono(responseType));
	}

	@Override
	public <RES> Flux<RES> reactiveDownloadFile(String uri, MediaType fileType, Class<RES> objectClass) {
		return webClient.get().uri(uri).accept(fileType).retrieve().bodyToFlux(objectClass);
	}

}
