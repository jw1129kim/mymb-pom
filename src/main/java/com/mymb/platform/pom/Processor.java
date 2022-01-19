package com.mymb.platform.pom;

public interface Processor {
    public void mintErc20Token(int numOfToken);
    public int calculateTokenAmountToBeGenerated();
}