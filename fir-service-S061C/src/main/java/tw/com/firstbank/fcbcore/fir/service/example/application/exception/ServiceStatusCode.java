package tw.com.firstbank.fcbcore.fir.service.example.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServiceStatusCode {

	INVALID_USER_NAME("0001"),
	INVALID_USER_CREDIT("0002"),
	INVALID_USER_EMAIL("0003"),
	INVALID_USER_DATA("0004"),
	INVALID_HOST_TRANSACTION("0005"),

	INVALID_VERSION("9999");

	private final String code;

}
