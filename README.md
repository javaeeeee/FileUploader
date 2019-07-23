# FileUploader
A Spring Boot application to manage files in cloud storage

## General description
This application provide three endpoints: to upload a file, to download a file, and return
the list of metadata of all stored files such as original name, size, and content type.

```
POST localhost:8080/upload
GET localhost:8080/download/<filename>
GET localhost:8080/
```

## Connecting to AWS S3 bucket
In order to connect the application to an AWS S3 bucket one should set the following environmental
variables.

```bash
export CLOUD_AWS_CREDENTIALS_ACCESSKEY=<access key>
export CLOUD_AWS_CREDENTIALS_SECRETKEY=<secret>
export CLOUD_AWS_REGION_STATIC=<region>
export AWS_S3_BUCKET_NAME=<bucket name>
```

## Running the application

The application requires Java of at least version 8 to be installed. To run this application type the following command from the project's folder.
```bash
mvn spring-boot:run
```

## General design considerations

This application uses a relational database to store file metadata. For simplicity, an in-memory database
is used, but but simply changing connection URL one can connect to a RDMS such as MySQL or Postgres deployed
locally or in the cloud.

A database is used to translate unique filename used for storage to original file names and also
has better search capabilities than e.g. code relying on obtaining list of objects stored in the bucket.

Files are stored in an AWS S3 bucket. The original name of the file is returned using Content Disposition header.
To save a file one can use the following `cUrl` command.

```bash
curl -O -L -J localhost:8080/download/<filename>
```