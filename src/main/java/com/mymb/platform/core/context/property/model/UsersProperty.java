package com.mymb.platform.core.context.property.model;

import lombok.Data;

@Data
public class UsersProperty {

    private String name;
    private String organization;
    private String mspid;
    private String id;
    private String role;
    private String credentialRoot;
    private String walletRoot;
}
