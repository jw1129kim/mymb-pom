package com.mymb.platform.utils;

import com.mymb.platform.core.context.GlobalContext;
import com.mymb.platform.core.context.property.model.UsersProperty;
import org.hyperledger.fabric.gateway.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Users {

    public static final Logger logger = LoggerFactory.getLogger(Users.class);

    public static void addUser(String identityLabel) throws Exception {

        try {
            // A wallet stores a collection of identities
            UsersProperty user = GlobalContext.getUser(identityLabel);
            Path credentialRoot = Paths.get(user.getCredentialRoot());
            logger.info("Credential Path: " + user.getCredentialRoot());

            Path walletRoot = Paths.get(user.getWalletRoot());
//            Path walletPath = walletRoot.resolve(Paths.get(user.getId()));
            Wallet wallet = Wallets.newFileSystemWallet(walletRoot);
            Identity existingUser = wallet.get(user.getId());

            if(existingUser == null){
                // Location of credentials to be stored in the wallet
                Path credentialPath = credentialRoot.resolve(Paths.get(user.getId(), "msp"));

                Path certificateRoot = credentialPath.resolve(Paths.get("signcerts"));
                Path certificatePem = null;
                if(Files.list(certificateRoot).count() == 1){
                    certificatePem = Files.list(certificateRoot).iterator().next();
                }

                Path privateKeyPath = credentialPath.resolve(Paths.get("keystore"));
                Path privateKey = null;
                if(Files.list(privateKeyPath).count() == 1) {
                    privateKey = Files.list(privateKeyPath).iterator().next();
                }

                // Load credentials into wallet
                X509Identity identity = Identities.newX509Identity(user.getMspid(), Identities.readX509Certificate(Files.newBufferedReader(certificatePem)), Identities.readPrivateKey(Files.newBufferedReader(privateKey)));
                wallet.put(user.getId(), identity);
            }
        } catch (Exception e) {
            System.err.println("Error adding to wallet");
            e.printStackTrace();
        }
    }

    public static Wallet getWalletByUser(String name){

        Wallet wallet = null;

        try {
            UsersProperty user = GlobalContext.getUser(name);
            Path walletRoot = Paths.get(user.getWalletRoot());
            wallet = Wallets.newFileSystemWallet(walletRoot);
            Identity existingUser = wallet.get(user.getId());

            if(existingUser == null){
                throw new Exception("There is no user : " + name);
            }
        } catch (Exception e) {
            System.err.println("Error get wallet");
            e.printStackTrace();
        }

        return wallet;
    }

    public static Identity getIdentityByUser(String name) {

        Identity identity = null;

        try {
            UsersProperty user = GlobalContext.getUser(name);
            Path walletRoot = Paths.get(user.getWalletRoot());
            Wallet wallet = Wallets.newFileSystemWallet(walletRoot);
            Identity existingUser = wallet.get(user.getId());

            if (existingUser == null) {
                throw new Exception("There is no user : " + name);
            }
        } catch (Exception e) {
            System.err.println("Error get wallet");
            e.printStackTrace();
        }

        return identity;
    }
}
