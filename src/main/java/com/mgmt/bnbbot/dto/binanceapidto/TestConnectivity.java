package com.mgmt.bnbbot.dto.binanceapidto;

import lombok.Builder;
import lombok.Data;

public interface TestConnectivity {

	String URL = "/api/v3/ping";

	@Data
	@Builder
	class Response {

	}

}
