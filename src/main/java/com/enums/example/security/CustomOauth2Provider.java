package com.enums.example.security;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOauth2Provider {
   KAKAO {
      @Override
		public Builder getBuilder(String registrationId) {
			ClientRegistration.Builder builder = getBuilder(registrationId,
					ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
			builder.scope("profile_nickname", "profile_image", "account_email");
			builder.authorizationUri("https://kauth.kakao.com/oauth/authorize");
			builder.tokenUri("https://kauth.kakao.com/oauth/token");
			builder.userInfoUri("https://kapi.kakao.com/v2/user/me");
			builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
         builder.userNameAttributeName("id");
			builder.clientName("Kakao");
			return builder;
		}
   };

   private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}";

	protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method,
			String redirectUri) {
		ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
		builder.clientAuthenticationMethod(method);
		builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
		builder.redirectUri(redirectUri);
		return builder;
	}

	public abstract ClientRegistration.Builder getBuilder(String registrationId);
}
