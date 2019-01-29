# Partner Center REST API Java8/Kotlin Client

[![CircleCI](https://circleci.com/gh/ClouDesire/partner-center-rest-api-client.svg?style=svg&circle-token=a5c07114285cefe886525a9194b8e20be32d28d5)](https://circleci.com/gh/ClouDesire/partner-center-rest-api-client)  [ ![Download](https://api.bintray.com/packages/cloudesire/maven-releases/partner-center-rest-api-client/images/download.svg) ](https://bintray.com/cloudesire/maven-releases/partner-center-rest-api-client/_latestVersion) 

This is a Kotlin (partial) implementation (usable also on Java 8 projects) of this [API](https://docs.microsoft.com/en-us/partner-center/develop/partner-center-rest-api-reference), based on the great [retrofit](https://github.com/square/retrofit) HTTP library.

Currently managed resources are:

* Customer
* Order
* Subscription

## Install on Maven

Add the package to your Maven project:

    <dependency>
      <groupId>com.cloudesire</groupId>
      <artifactId>partner-center-rest-api-client</artifactId>
      <version>1.0.0</version>
    </dependency>

Make sure that jcenter is enabled

    <repositories>
      <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com/</url>
      </repository>
    </repositories>

## Install on Gradle

Add dependency to your build.gradle

    compile 'com.cloudesire:partner-center-rest-api-client:1.0.0'

Make sure that jcenter is enabled

    repositories {
      jcenter()
    }
    
## Usage

### Authentication

openssl genrsa -out rsa_private.pem 2048
openssl rsa -in rsa_private.pem -pubout -out rsa_public.pem

```kotlin
MicrosoftPartnerCenterClient.Builder()
    .clientId("")
    .clientSecret("")
    .resellerDomain("")
    .build()
```

The `clientId` is the Azure AD client id of the calling web service. To find the calling application's client ID, in the Azure portal, click Azure Active Directory, click App registrations, click the application. The `clientId` is the Application ID.
  
The `clientSecret` is key registered for the calling web service or daemon application in Azure AD. To create a key, in the Azure portal, click Azure Active Directory, click App registrations, click the application, click Settings, click Keys, and add a Key.
  
The `resellerDomain` specifies your tenant ID, for example abcxyz.onmicrosoft.com or 8eaef023-2b34-4da1-9baa-8bc8c9d6a490 or common for tenant indipendent tokens.  


For usage examples please look into [tests](https://github.com/ClouDesire/partner-center-rest-api-client/tree/master/src/test/kotlin/com/cloudesire/partnercenter) folder.


# PS. The `clientSecret` string must be URL-encoded!!!