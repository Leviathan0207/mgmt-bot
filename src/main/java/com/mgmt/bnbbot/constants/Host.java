package com.mgmt.bnbbot.constants;

import java.util.Optional;
import java.util.stream.Stream;

public enum Host {

	BINANCE;

	public static Optional<Host> lookup(String host) {
		return Stream.of(Host.values()).filter(h -> h.name().toLowerCase().equals(host)).findFirst();
	}

}
