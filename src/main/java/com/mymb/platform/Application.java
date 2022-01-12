package com.mymb.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymb.platform.core.context.GlobalContext;
import com.mymb.platform.core.context.property.MymbProperty;
import com.mymb.platform.utils.Users;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class Application {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "false");
    }

    @Autowired
    private MymbProperty mymbProperty;

    public static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static Gateway gateway;
    private static ObjectMapper mapper;

    private Contract getContract(String channelId, String chaincodeId) throws Exception {

        getGateway();
        Network network = gateway.getNetwork(channelId);
        Contract contract = network.getContract(chaincodeId);

        return contract;
    }

    private Gateway getGateway() throws Exception {
        if(gateway != null){
            return gateway;
        }

        return connectToBlockchain();
    }

    private ObjectMapper getMapper(){

        if(mapper == null){
            mapper = new ObjectMapper();
        }

        return mapper;
    }

    public Gateway connectToBlockchain() throws Exception {

        String userName = "minter";

        logger.info("Start to connect Gateway");
        Path ccpPath = Paths.get(GlobalContext.getConnections("org1ccp").getCcpPath());

        Wallet wallet = Users.getWalletByUser(userName);
        logger.info("Config Path : " + ccpPath.toString());

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, GlobalContext.getUser(userName).getId()).networkConfig(ccpPath).discovery(true);

        logger.info("builder connect");
        gateway = builder.connect();

        return gateway;
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
