package com.mgmt.bnbbot.service;

import com.mgmt.bnbbot.constants.Host;
import com.mgmt.bnbbot.dto.binanceapidto.TestConnectivity;
import com.mgmt.bnbbot.util.ReactiveConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BinanceAPIServiceTest {

	private ReactiveConnector reactiveConnector;

	private BinanceAPIService binanceAPIService;

	@BeforeEach
	public void setup() {
		reactiveConnector = Mockito.mock(ReactiveConnector.class);
		binanceAPIService = new BinanceAPIServiceImpl(reactiveConnector);
	}

	@Test
	public void testConnectivity() {
		Mockito.when(reactiveConnector.reactiveGet(Mockito.eq(Host.BINANCE), Mockito.eq(TestConnectivity.URL),
				Mockito.anyMap(), Mockito.anyMap(), Mockito.eq(TestConnectivity.Response.class)))
				.thenReturn(Mono.just(TestConnectivity.Response.builder().build()));
		StepVerifier.create(binanceAPIService.testConnectivity()).expectNextMatches(result -> {
			Assertions.assertNotNull(result);
			Mockito.verify(reactiveConnector, Mockito.atLeastOnce()).reactiveGet(Mockito.eq(Host.BINANCE),
					Mockito.eq(TestConnectivity.URL), Mockito.anyMap(), Mockito.anyMap(),
					Mockito.eq(TestConnectivity.Response.class));
			return true;
		}).verifyComplete();
		Assertions.assertNotNull(binanceAPIService.testConnectivity());
	}

}
