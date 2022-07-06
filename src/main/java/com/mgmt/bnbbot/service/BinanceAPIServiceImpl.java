package com.mgmt.bnbbot.service;

import com.mgmt.bnbbot.constants.Host;
import com.mgmt.bnbbot.dto.binanceapidto.TestConnectivity;
import com.mgmt.bnbbot.util.ReactiveConnector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@AllArgsConstructor
@Service
public class BinanceAPIServiceImpl implements BinanceAPIService {

	private final ReactiveConnector reactiveConnector;

	@Override
	public Mono<TestConnectivity.Response> testConnectivity() {
		return reactiveConnector.reactiveGet(Host.BINANCE, TestConnectivity.URL, Collections.emptyMap(),
				Collections.emptyMap(), TestConnectivity.Response.class);
	}

}
