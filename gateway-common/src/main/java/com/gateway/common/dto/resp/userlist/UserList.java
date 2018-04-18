package com.gateway.common.dto.resp.userlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList {
    private List<Users> users;
}

@Data
class Users {

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "user_email")
    private String userEmail;

    @JsonProperty(value = "ad_domains")
    private AdDomains adDomains;

    private String id;
}

@Data
class AdDomains {

    @JsonProperty(value = "domain_name")
    private String domainName;

    @JsonProperty(value = "domain_type")
    private String domainType;
}

