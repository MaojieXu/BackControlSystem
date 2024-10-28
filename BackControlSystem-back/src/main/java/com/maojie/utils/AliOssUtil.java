package com.maojie.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.maojie.config.AliCloudConfig;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @Description: Utility class for uploading files to Aliyun OSS (Object Storage Service) using AliCloud configuration.
 */
@Component
public class AliOssUtil {

    private static AliCloudConfig aliCloudConfig;

    /**
     * @Description: Constructor that injects AliCloudConfig to provide configuration details for OSS operations.
     * @Param: aliCloudConfig - AliCloudConfig object containing endpoint, access keys, and bucket name
     */
    public AliOssUtil(AliCloudConfig aliCloudConfig) {
        AliOssUtil.aliCloudConfig = aliCloudConfig;
    }

    /**
     * @Description: Uploads a file to Aliyun OSS with a specified object name and input stream, and returns the URL of the uploaded file.
     * @Param: objectName - Name for the object to be stored in OSS; in - InputStream of the file to upload
     * @return: String - URL of the uploaded file
     * @throws Exception - Throws an exception if upload fails
     */
    public static String uploadFile(String objectName, InputStream in) throws Exception {
        // Retrieve configuration details from injected AliCloudConfig
        String endpoint = aliCloudConfig.getEndpoint();
        String accessKeyId = aliCloudConfig.getAccessKeyId();
        String accessKeySecret = aliCloudConfig.getAccessKeySecret();
        String bucketName = aliCloudConfig.getBucketName();

        // Initialize OSS client instance
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String url = "";

        try {
            // Create PutObjectRequest for uploading the file
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);

            // Perform the file upload and construct the file URL
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            url = "https://" + bucketName + "." + endpoint.substring(endpoint.lastIndexOf("/") + 1) + "/" + objectName;
        } catch (OSSException oe) {
            // Handle OSS-related exceptions
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            // Handle client-side exceptions
            System.out.println("Caught a ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            // Ensure OSS client is shut down after operation
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return url;
    }
}
