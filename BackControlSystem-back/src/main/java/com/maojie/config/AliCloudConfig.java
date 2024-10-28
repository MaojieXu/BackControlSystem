package com.maojie.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: Configuration class for AliCloud OSS properties. This class maps to properties prefixed with "aliyun.oss" in application configuration.
 * @Param: None
 * @return: None
 * @Author: Maojie Xu
 * @Date: 10/25/2024
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliCloudConfig {

    /**
     * @Description: The endpoint URL for the AliCloud OSS service
     * @Param: None
     * @return: String containing the endpoint URL
     */
    private String endpoint;

    /**
     * @Description: The access key ID for authentication with AliCloud OSS
     * @Param: None
     * @return: String containing the access key ID
     */
    private String accessKeyId;

    /**
     * @Description: The secret access key for authentication with AliCloud OSS
     * @Param: None
     * @return: String containing the access key secret
     */
    private String accessKeySecret;

    /**
     * @Description: The bucket name in AliCloud OSS where files will be stored
     * @Param: None
     * @return: String containing the bucket name
     */
    private String bucketName;

}
