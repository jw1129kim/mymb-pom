package com.mymb.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymb.platform.api.model.nft.NftInfo;
import com.mymb.platform.api.model.nft.NftInfoResult;
import com.mymb.platform.api.model.project.ProjectInfo;
import com.mymb.platform.api.model.project.ProjectInfoResult;
import com.mymb.platform.api.repositories.ProjectsRepository;
import com.mymb.platform.core.context.GlobalContext;
import com.mymb.platform.core.context.property.MymbProperty;
import com.mymb.platform.core.context.property.model.ERC721Property;
import com.mymb.platform.pom.PomProcessor;
import com.mymb.platform.pom.Processor;
import com.mymb.platform.utils.Users;
import org.apache.coyote.Response;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/mymb/pom")
@SpringBootApplication
public class Application {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "false");
    }

    @Autowired
    private MymbProperty mymbProperty;

    @Autowired
    private ProjectsRepository projectsRepository;

    public static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static Gateway gateway;
    private static ObjectMapper mapper;

    @RequestMapping("/")
    String home() throws Exception {
        String pName = "";
        Users.addUser("minter" );
        pName = mymbProperty.getConnections().get(0).getCcpName();
        return "Hello Store2! - " + pName;
    }

    @RequestMapping(value = "/nft", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@RequestBody NftInfo nftInfo){
        String resultStr = "";
        NftInfoResult nftResult = null;
        ERC721Property erc721Property = GlobalContext.getERC721();

        try {
            byte[] result = getContract(erc721Property.getChannel(), erc721Property.getCcn()).submitTransaction("MintWithTokenURI", nftInfo.getId(), nftInfo.getUri());
            resultStr = new String(result, UTF_8);
            nftResult = getMapper().readValue(new String(result, UTF_8), NftInfoResult.class);

        }catch(Exception e){
            e.printStackTrace();;
        }

        return new ResponseEntity<>(nftResult, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<?> addProject(@RequestBody ProjectInfo projectInfo) {
        String resultStr = "";
        ProjectInfoResult result = null;

        projectsRepository.insert(projectInfo);

//        PomProcessor.getInstance()

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

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
