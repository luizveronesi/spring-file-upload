# Spring File Upload

Java implementation to upload files to local server, ftp server, AWS S3 and Azure Blob Storage. It also gets filetype with Apache Tika and verifies it in configurable allow and block lists.

## Installation

```bash
# Clone the repository
git https://github.com/luizveronesi/spring-file-upload.git

# Navigate to the project directory
cd spring-file-upload

# Install dependencies
mvn install
```

```bash
# Docker installation
mvn clean package -f pom.xml -U
docker build . -t spring-file-upload-example:latest
docker create --name spring-file-upload-example --network your-network --ip x.x.x.x --restart unless-stopped spring-file-upload-example:latest bash
docker start spring-file-upload-example
```

## Usage

```bash
# Run the application
java -jar target/api.jar
```

Open Swagger: http://localhost:8080/swagger-ui/index.html

## Configuration

All configuration parameters must be set at file src/main/resources/application.yml.

### Local Storage

No configuration is needed.

### FTP

```bash
app:
  ftp:
    address: YOUR_FTP_ADDRESS
    # port: YOUR_CUSTOM_PORT (optional)
    username: YOUR_USERNAME
    password: YOUR_PASSWORD
```

### AWS S3

```bash
spring:
  cloud:
    aws:
      region:
        static: us-east-2 # must set a valid default value, even if not using azure
      credentials:
        access-key: YOUR_ACCESS_KEY
        secret-key: YOUR_SECRET_KEY
```

### Azure Blob Storage

```bash
spring:
  cloud:
    azure:
      storage:
        blob:
          account-name: YOUR_ACCOUNT_NAME
          account-key: YOUR_KEY
          container-name: YOUR_CONTAINER
```

### Allow and Block Lists

The lists are set at file src/main/resources/application.yml using the properties.

The implementation checks if the file type detected by Apache Tika corresponds to any of the informed mime types in these two variables.

The values must be comma separated.

```bash
app:
  upload:
    blocklist: application, font, text/html, text/x-php
    allowlist: application/xml, application/pdf, application/x-elc, application/vnd.openxmlformats-officedocument.wordprocessingml.document
```

## Endpoint

### POST /

Upload a file and extract the pages.

|          Parameter |     Type      | Description                                                                                                                                                               |
| -----------------: | :-----------: | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|           `folder` |    string     | The folder to which the file is going to be uploaded. It may be the full path for local storage or relative path for the others. Required only for local storage and FTP. |
|             `file` | MultipartFile | The file itself.                                                                                                                                                          |
|             `type` |    option     | Select the engine to make the upload. Four options available: LOCAL, FTP, AWS, AZURE.                                                                                     |
| `keepOriginalName` |    boolean    | If false, an unique identifier is generated and used at the file name. Otherwise the original name is kept. Default: false.                                               |
|           `bucket` |    string     | The bucket name to be used at AWS S3. Required when using AWS S3.                                                                                                         |

## Next steps

Implement unit tests.
