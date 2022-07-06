package com.mgmt.bnbbot.controller;

import com.mgmt.bnbbot.dto.binanceapidto.TestConnectivity;
import com.mgmt.bnbbot.service.BinanceAPIService;
import com.mgmt.bnbbot.service.BnbBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor
public class BnbBotController {

	private final BnbBotService bnbBotService;

	private final BinanceAPIService binanceAPIService;

	@PostMapping("/execute")
	public void execute() {
		bnbBotService.execute();
	}

	@PostMapping
	public Mono<TestConnectivity.Response> testConnectivity() {
		return binanceAPIService.testConnectivity();
	}

}
