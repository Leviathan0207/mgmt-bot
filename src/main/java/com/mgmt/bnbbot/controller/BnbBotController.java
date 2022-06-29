package com.mgmt.bnbbot.controller;

import com.mgmt.bnbbot.service.BnbBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor
public class BnbBotController {

	private final BnbBotService bnbBotService;

	@PostMapping("/execute")
	public void execute() {
		bnbBotService.execute();
	}

}
