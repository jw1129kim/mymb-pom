package com.mymb.platform.pom;

public class PomProcessor implements Processor{

    private static Processor processor= null;

    public synchronized static Processor getInstance() {
        if (processor == null)
            processor = new PomProcessor();
        return processor;
    }

    @Override
    public void mintErc20Token(int numOfToken) {

    }

    @Override
    public int calculateTokenAmountToBeGenerated() {
        return 0;
    }
}
