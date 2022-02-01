package com.mymb.platform.pom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymb.platform.api.model.mymb.MymbTokenResult;
import com.mymb.platform.api.model.policy.MymbPolicy;
import com.mymb.platform.api.model.project.ProjectInfo;
import com.mymb.platform.api.model.project.ProjectStatus;
import com.mymb.platform.api.model.user.UserInfo;
import com.mymb.platform.api.model.user.UserStatus;
import com.mymb.platform.api.repositories.*;
import com.mymb.platform.core.context.GlobalContext;
import com.mymb.platform.pom.policy.MymbPomPolicy;
import com.mymb.platform.utils.Users;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Scope("singleton")
public class PomProcessor implements Processor{

    private static Processor processor= null;
    private static Gateway gateway;
    private static ObjectMapper mapper;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    public static final Logger logger = LoggerFactory.getLogger(PomProcessor.class);

    public synchronized static Processor getInstance() {
        if (processor == null)
            processor = new PomProcessor();
        return processor;
    }

    @Override
    public MymbTokenResult mintErc20Token(int numOfToken) throws Exception {

        MymbTokenResult tokenResult = null;

        byte[] result = getContract(GlobalContext.getERC20().getChannel(), GlobalContext.getERC20().getCcn()).submitTransaction("Mint", String.valueOf(numOfToken));
        logger.info(new String(result));

        return tokenResult;
    }

    @Override
    public int totalSupplyOfErc20() throws Exception {

        int totalSupply = 0;

        byte[] result = getContract(GlobalContext.getERC20().getChannel(), GlobalContext.getERC20().getCcn()).submitTransaction("TotalSupply");
        logger.info(new String(result));
        totalSupply = Integer.parseInt(new String(result, UTF_8));

        return totalSupply;
    }

    @Override
    public <T> int calculateTokenAmountToBeGenerated(T t) throws Exception {

        MymbPolicy policy = null;
        MymbPomPolicy pomPolicy = null;
        int numOfToken = 0;

        policy = policyRepository.findByName("projectPolicy");
        pomPolicy = getMapper().readValue(policy.getContent(), MymbPomPolicy.class);

        if (t instanceof ProjectStatus){
           ProjectStatus projectStatus = (ProjectStatus) t;
           if(projectStatus.getDelta() >= pomPolicy.getIssueConditionByProjects()){
               projectStatus.setDelta(projectStatus.getDelta() - pomPolicy.getIssueConditionByProjects());
               projectStatusRepository.save(projectStatus);
               numOfToken = pomPolicy.getNumOfTokenByProjects();
           }
        }else if(t instanceof UserStatus){
            UserStatus userStatus = (UserStatus) t;
            if(userStatus.getDelta() >= pomPolicy.getIssueConditionByUsers()){
                userStatus.setDelta(userStatus.getDelta() - pomPolicy.getIssueConditionByUsers());
                userStatusRepository.save(userStatus);
                numOfToken = pomPolicy.getNumOfTokenByUsers();
            }

        }
        return numOfToken;
    }

    @Override
    public void addProject(ProjectInfo project) throws Exception {

        int numOfToken = 0;
        ProjectStatus prjStatus = null;

        projectsRepository.insert(project);
        prjStatus = projectStatusRepository.findByName("projectAmountStatus");
        if(prjStatus == null){
            prjStatus = ProjectStatus.builder().build();
            prjStatus.setName("projectAmountStatus");
            prjStatus.setTotalAmount(totalAmountOfProjects());
            prjStatus.setDelta(Long.parseLong(project.getAmount()));
            projectStatusRepository.insert(prjStatus);
        }else{
            prjStatus.setTotalAmount(totalAmountOfProjects());
            prjStatus.setDelta(prjStatus.getDelta() + Long.parseLong(project.getAmount()));
            projectStatusRepository.save(prjStatus);
        }

        numOfToken = calculateTokenAmountToBeGenerated(prjStatus);
        if(numOfToken > 0)
            mintErc20Token(numOfToken);
    }

    @Override
    public List<ProjectInfo> getProjects() {
        List<ProjectInfo> projects = null;

        projects = projectsRepository.findAll();

        return projects;
    }

    @Override
    public long totalAmountOfProjects() {

        List<ProjectInfo> projects = null;
        long amount = 0;

        projects = getProjects();
        for(ProjectInfo project : projects){
            amount = amount + Long.parseLong(project.getAmount());
        }

        return amount;
    }

    @Override
    public void addUser(UserInfo user) throws Exception {

        int numOfToken = 0;
        UserStatus userStatus = null;

        userRepository.insert(user);

        userStatus = userStatusRepository.findByName("userAmountStatus");
        if(userStatus == null){
            userStatus = UserStatus.builder().build();
            userStatus.setName("userAmountStatus");
            userStatus.setTotalUser(totalAmountOfUsers());
            userStatus.setDelta(userStatus.getDelta() + 1);
            userStatusRepository.insert(userStatus);
        }else{
            userStatus.setTotalUser(totalAmountOfUsers());
            userStatus.setDelta(userStatus.getDelta() + 1);
            userStatusRepository.save(userStatus);
        }

        numOfToken = calculateTokenAmountToBeGenerated(userStatus);
        if(numOfToken > 0)
            mintErc20Token(numOfToken);

    }

    @Override
    public long totalAmountOfUsers() {

        long amount = 0;
        amount = userRepository.count();

        return amount;
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

}
