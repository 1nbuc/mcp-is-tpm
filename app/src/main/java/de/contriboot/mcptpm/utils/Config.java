package de.contriboot.mcptpm.utils;

import com.figaf.integration.common.entity.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    public static RequestContext getRequestContextFromEnv() {
        RequestContext requestContext = new RequestContext();

        requestContext.setCloudPlatformType(CloudPlatformType.CLOUD_FOUNDRY);
        requestContext.setAuthenticationType(AuthenticationType.BASIC);
        requestContext.setPlatform(Platform.CPI);
        requestContext.setWebApiAccessMode(WebApiAccessMode.SAP_UNIVERSAL_ID);
        requestContext.setConnectionProperties(
                new ConnectionProperties(
                        Config.getEnvVar("CPI_URL"),
                        Config.getEnvVar("CPI_USER"),
                        Config.getEnvVar("CPI_PASSWORD")
                ));
        requestContext.setUniversalAuthAccountId(Config.getEnvVar("CPI_UNIVERSAL_AUTH_ACCOUNT_ID"));

        return requestContext;
    }

    public static String getEnvVar(String key) {
        Dotenv dotenv = Dotenv.load();
        String result = System.getenv(key);

        if (result == null || result.isEmpty()) {
            result = dotenv.get(key);
        }

        if (result == null || result.isEmpty()) {
            throw new RuntimeException("Environment variable '" + key + "' is not set.");
        }

        return result;
    }

    public static String getEnvPath() {
        String binPath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String[] split = binPath.split("/");
        String envPath = "";
        for (int i = 0; i < split.length - 1; i++) {
            envPath += split[i] + "/";
        }
        return envPath + "env";
    }
}