package com.known.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class MailConfig {
	@Value("${mail.sendUserName}")
	private String sendUserName;
	
	@Value("${mail.sendPassword}")
	private String sendPassword;

	@Value("${Glob_Real_Path}")
	private String Glob_Real_Path;

}
