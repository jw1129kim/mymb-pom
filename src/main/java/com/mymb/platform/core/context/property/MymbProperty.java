package com.mymb.platform.core.context.property;

import com.mymb.platform.core.context.property.model.ConnectionProperty;
import com.mymb.platform.core.context.property.model.ERC721Property;
import com.mymb.platform.core.context.property.model.UsersProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("core")
public class MymbProperty {

    private String name;
    private String environment;
    private boolean enabled;
    private List<String> servers = new ArrayList<>();
    private List<ConnectionProperty> connections = new ArrayList<>();
    private List<UsersProperty> users = new ArrayList<>();
    private ERC721Property erc721;
}
