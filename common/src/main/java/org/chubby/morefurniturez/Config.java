package org.chubby.morefurniturez;

import com.mrcrayfish.framework.api.config.ConfigProperty;
import com.mrcrayfish.framework.api.config.ConfigType;
import com.mrcrayfish.framework.api.config.FrameworkConfig;

public class Config
{
    @FrameworkConfig(
            id = Constants.MOD_ID,
            name = "client",
            type = ConfigType.CLIENT

    )
    public static final Client CLIENT = new Client();

    @FrameworkConfig(
            id = Constants.MOD_ID,
            name = "server",
            type = ConfigType.SERVER_SYNC

    )
    public static final Server SERVER = new Server();

    public static class Client
    {

    }
    public static class Server
    {
        @ConfigProperty(
            name = "Electricity",
            comment = "Configure Electricity for "+ Constants.MOD_ID
        )
        public static final Electricity ELECTRICITY = new Electricity();


    }

    public static class Electricity
    {

    }
}
