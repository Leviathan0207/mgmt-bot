package com.mgmt.bnbbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BnbBotServiceImpl implements BnbBotService {

	@Override
	public void execute() {
		log.info("START :: executing...");
		this.read();
		this.process();
		this.write();
		log.info("END :: executing...");
	}

	@Override
	public void read() {
		log.info("START :: reading...");
		log.info("END :: reading...");
	}

	@Override
	public void process() {
		log.info("START :: processing...");
		log.info("END :: processing...");
	}

	@Override
	public void write() {
		log.info("START :: writing...");
		log.info("END :: writing...");
	}

	/*
	 * TODO: - Codes for read - Codes for process - Codes for write
	 */

}
