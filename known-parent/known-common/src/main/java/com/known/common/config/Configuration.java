package com.known.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Configuration {
	@Value("${known.httpSolrUrl}")
	private String httpSolrUrl;

	@Value("${app_id_qq}")
	private String appIdQq;

	@Value("${app_key_qq}")
	private String appKeyQq;

	@Value("${redirect_url_qq}")
	private String redirectUrlQq;

	@Value("${scope_qq}")
	private String scopeQq;

	@Value("${getUserInfoURL_qq}")
	private String getUserInfoURLQq;

	@Value("${accessTokenURL_qq}")
	private String accessTokenURLQq;

	@Value("${getOpenIDURL_qq}")
	private String getOpenIDURLQq;

	@Value("${authorizeURL_qq}")
	private String authorizeURLQq;

}
