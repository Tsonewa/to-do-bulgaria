package com.example.todobulgaria.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfiguration {

        private String cloudName;
        private String apiKey;
        private String apiSecret;

        public String getCloudName() {
            return cloudName;
        }

        /**
         * Sets the cloud name associated with the cloudinary account.
         * @param cloudName the cloud name associated with the cloudinary account.
         * @return this
         */
        public CloudinaryConfiguration setCloudName(String cloudName) {
            this.cloudName = cloudName;
            return this;
        }

        public String getApiKey() {
            return apiKey;
        }

        public CloudinaryConfiguration setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public String getApiSecret() {
            return apiSecret;
        }

        public CloudinaryConfiguration setApiSecret(String apiSecret) {
            this.apiSecret = apiSecret;
            return this;
        }
}
