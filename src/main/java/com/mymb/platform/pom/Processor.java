package com.mymb.platform.pom;

import com.mymb.platform.api.model.mymb.MymbTokenResult;
import com.mymb.platform.api.model.project.ProjectInfo;
import com.mymb.platform.api.model.user.UserInfo;

import java.util.List;

public interface Processor {
    public MymbTokenResult mintErc20Token(int numOfToken) throws Exception;
    public int totalSupplyOfErc20() throws Exception;
    public <T> int calculateTokenAmountToBeGenerated(T t) throws Exception;

    public void addProject(ProjectInfo project) throws Exception;
    public List<ProjectInfo> getProjects();
    public long totalAmountOfProjects();

    public void addUser(UserInfo user) throws Exception;
    public long totalAmountOfUsers();
}