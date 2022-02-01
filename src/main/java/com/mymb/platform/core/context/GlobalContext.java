package com.mymb.platform.core.context;

import com.mymb.platform.core.context.property.MymbProperty;
import com.mymb.platform.core.context.property.model.ConnectionProperty;
import com.mymb.platform.core.context.property.model.ERC20Property;
import com.mymb.platform.core.context.property.model.ERC721Property;
import com.mymb.platform.core.context.property.model.UsersProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class GlobalContext {

    @Autowired
    private MymbProperty beanProperty;
    public static MymbProperty mymbProperty;

    @PostConstruct
    private void initialize() {
        this.mymbProperty = beanProperty;
    }

    public static String getProperty(String prefix, String key) {

        String property = "";

//        try{
//
//            Class<?> cls = Class.forName("com.mymb.platform.core.context.property.MymbProperty");
//            Method method = cls.getMethod(getMethodName(prefix), (Class<?>[]) null );
//
//            Class<?> properties = (Class<?>) method.invoke(obj);
//            System.out.println(properties.getClass().getName());
//
//        }catch(NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e){
//            e.printStackTrace();
//        }

        return property;
    }

    public static ConnectionProperty getConnections(String ccpName){

        ConnectionProperty returnConnections = null;
        List<ConnectionProperty> list = mymbProperty.getConnections();

        for(ConnectionProperty conn : list){
            if(ccpName.equalsIgnoreCase(conn.getCcpName())){
                returnConnections = conn;
            }
        }

        return returnConnections;
    }

    public static UsersProperty getUser(String name) {

        UsersProperty returnUser = null;
        List<UsersProperty> list = mymbProperty.getUsers();

        for(UsersProperty user : list){
            if(name.equalsIgnoreCase(user.getName())){
                returnUser = user;
            }
        }

        return returnUser;
    }

    public static ERC721Property getERC721() {

        return mymbProperty.getErc721();
    }

    public static ERC20Property getERC20() {
        return mymbProperty.getErc20();
    }
}
