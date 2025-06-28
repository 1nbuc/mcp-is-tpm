package de.contriboot.mcptpm.utils;

import com.figaf.integration.common.entity.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    public static RequestContext getRequestContextFromEnv() {
        RequestContext requestContext = new RequestContext();

        requestContext.setCloudPlatformType(CloudPlatformType.CLOUD_FOUNDRY);
        requestContext.setAuthenticationType(AuthenticationType.BASIC);
        requestContext.setPlatform(Platform.CPI);

        if (Config.getEnvVar("CPI_URL") == null || Config.getEnvVar("CPI_USER") == null || Config.getEnvVar("CPI_PASSWORD") == null) {
            throw new RuntimeException("Missing environment variables for CPI connection. Please provide at least CPI_URL, CPI_USER and CPI_PASSWORD");
        }

        if (Config.getEnvVar("CPI_UNIVERSAL_MAIL") == null || Config.getEnvVar("CPI_UNIVERSAL_MAIL").isEmpty()) {
            requestContext.setWebApiAccessMode(WebApiAccessMode.S_USER);
            requestContext.setConnectionProperties(
                    new ConnectionProperties(
                            Config.getEnvVar("CPI_URL"),
                            Config.getEnvVar("CPI_USER"),
                            Config.getEnvVar("CPI_PASSWORD")
                    ));
        } else {
            requestContext.setWebApiAccessMode(WebApiAccessMode.SAP_UNIVERSAL_ID);
            requestContext.setUniversalAuthAccountId(Config.getEnvVar("CPI_USER"));
            requestContext.setConnectionProperties(
                    new ConnectionProperties(
                            Config.getEnvVar("CPI_URL"),
                            Config.getEnvVar("CPI_UNIVERSAL_MAIL"),
                            Config.getEnvVar("CPI_PASSWORD")
                    ));
        }



        return requestContext;
    }

    public static String getEnvVar(String key) {
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.load();
        } catch (Exception e) {

        }

        String result = System.getenv(key);

        if ((result == null || result.isEmpty()) && dotenv == null) {
            return null;
        }

        if (result == null || result.isEmpty()) {
            result = dotenv.get(key);
        }

        return result;
    }

}