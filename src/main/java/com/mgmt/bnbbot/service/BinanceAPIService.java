package com.mgmt.bnbbot.service;

import com.mgmt.bnbbot.dto.binanceapidto.TestConnectivity;
import reactor.core.publisher.Mono;

public interface BinanceAPIService {

	Mono<TestConnectivity.Response> testConnectivity();

}
