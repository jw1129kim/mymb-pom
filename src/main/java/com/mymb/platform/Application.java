package com.mymb.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymb.platform.api.model.mymb.MymbToken;
import com.mymb.platform.api.model.mymb.MymbTokenResult;
import com.mymb.platform.api.model.policy.MymbPolicy;
import com.mymb.platform.api.model.project.ProjectInfo;
import com.mymb.platform.api.model.project.ProjectInfoResult;
import com.mymb.platform.api.model.user.UserInfo;
import com.mymb.platform.api.repositories.PolicyRepository;
import com.mymb.platform.api.repositories.ProjectsRepository;
import com.mymb.platform.core.context.property.MymbProperty;
import com.mymb.platform.pom.PomProcessor;
import com.mymb.platform.utils.Users;
import org.hyperledger.fabric.gateway.Gateway;
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

import java.util.List;

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

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PomProcessor pomProcessor;

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

//    @RequestMapping(value = "/nft", method = RequestMethod.POST)
//    public ResponseEntity<?> createProduct(@RequestBody NftInfo nftInfo){
//        String resultStr = "";
//        NftInfoResult nftResult = null;
//        ERC721Property erc721Property = GlobalContext.getERC721();
//
//        try {
//            byte[] result = getContract(erc721Property.getChannel(), erc721Property.getCcn()).submitTransaction("MintWithTokenURI", nftInfo.getId(), nftInfo.getUri());
//            resultStr = new String(result, UTF_8);
//            nftResult = getMapper().readValue(new String(result, UTF_8), NftInfoResult.class);
//
//        }catch(Exception e){
//            e.printStackTrace();;
//        }
//
//        return new ResponseEntity<>(nftResult, HttpStatus.CREATED);
//    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> mintERC20(@RequestBody MymbToken mymbToken) throws Exception {

        MymbTokenResult result = null;

        result = pomProcessor.mintErc20Token(mymbToken.getAmountOfToken());

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/token/all", method = RequestMethod.GET)
    public ResponseEntity<?> getTotalSupplyERC20() throws Exception {

        int result = 0;

        result = pomProcessor.totalSupplyOfErc20();

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<?> addProject(@RequestBody ProjectInfo projectInfo) throws Exception {
        String resultStr = "";
        ProjectInfoResult result = null;

        pomProcessor.addProject(projectInfo);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<?> getProjects() {
        String resultStr = "";
        List projectList = null;

        projectList = pomProcessor.getProjects();

        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/amount", method = RequestMethod.GET)
    public ResponseEntity<?> getTotalAmountOfProjecs(){

        Long amount = pomProcessor.totalAmountOfProjects();

        return new ResponseEntity<>(amount, HttpStatus.OK);

    }

    @RequestMapping(value = "/policy", method = RequestMethod.POST)
    public ResponseEntity<?> createMymbPomPolicy(@RequestBody MymbPolicy mymbPolicy){

//        MymbPolicy policy = null;
//        MymbPomPolicy pomPolicy = null;
//        ObjectMapper mapper = new ObjectMapper();
//
//        policy = MymbPolicy.builder().build();
//        policy.setName("projectPolicy");
//        pomPolicy = MymbPomPolicy.builder().build();
//
//        pomPolicy.setName("projectPolicy");
//        pomPolicy.setIssueConditionByProjects(100000000);
//        pomPolicy.setNumOfTokenByProjects(10000);
//        pomPolicy.setIssueConditionByUsers(10000);
//        pomPolicy.setNumOfTokenByUsers(1000);
//        try {
//            policy.setContent(mapper.writeValueAsString(pomPolicy));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//        policyRepository.save(policy);

//        return new ResponseEntity<>(policy, HttpStatus.OK);

        policyRepository.save(mymbPolicy);

        return new ResponseEntity<>(mymbPolicy, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody UserInfo userInfo) throws Exception {

        pomProcessor.addUser(userInfo);

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
