package com.platform.authentication.security;

public class OAuthClientProperties {
	private String clientId;
    private String clientSecret;
    private String[] scopes;
    private String authorizedGrandTypes;

    public String getClientId () {
        return clientId;
    }
    public void setClientId ( String clientId ) {
        this.clientId = clientId;
    }
    public String getClientSecret () {
        return clientSecret;
    }
    public void setClientSecret ( String clientSecret ) {
        this.clientSecret = clientSecret;
    }
    public String[] getScopes () {
        return scopes;
    }
    public void setScopes ( String[]  scopes ) {
        this.scopes = scopes;
    }
    public String getAuthorizedGrandTypes () {
        return authorizedGrandTypes;
    }
    public void setAuthorizedGrandTypes ( String authorizedGrandTypes ) {
        this.authorizedGrandTypes = authorizedGrandTypes;
    }

}
