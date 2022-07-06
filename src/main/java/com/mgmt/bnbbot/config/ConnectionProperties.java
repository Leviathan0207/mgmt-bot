package com.mgmt.bnbbot.config;

import com.mgmt.bnbbot.constants.Host;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "connection", ignoreInvalidFields = true)
public class ConnectionProperties {

	private SqlConnection mssql;

	private ServerConnection binance;

	@Data
	public static class ServerConnection {

		private String url;

		private Map<String, String> headers;

	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class BearerAuthConnection extends ServerConnection {

		private String audience;

	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class BasicAuthConnection extends ServerConnection {

		private String username;

		private String password;

	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class SqlConnection extends BasicAuthConnection {

		private String driverClass;

		private String serverDialect;

		private String validationQuery;

		private int removeAbandonedTimeout;

	}

	public ServerConnection getConnection(Host host) {
		switch (host) {
			case BINANCE:
				return this.binance;
			default:
				return null;
		}
	}

}
