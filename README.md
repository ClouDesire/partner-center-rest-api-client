# Partner Center REST API Java8/Kotlin Client

[![CircleCI](https://circleci.com/gh/ClouDesire/partner-center-rest-api-client.svg?style=svg&circle-token=a5c07114285cefe886525a9194b8e20be32d28d5)](https://circleci.com/gh/ClouDesire/partner-center-rest-api-client)

This is a Kotlin (partial) implementation (usable also on Java 8 projects) of this [API](https://docs.microsoft.com/en-us/partner-center/develop/partner-center-rest-api-reference), based on the great [retrofit](https://github.com/square/retrofit) HTTP library.

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
    
## Examples

For usage examples please look into [tests](https://github.com/ClouDesire/partner-center-rest-api-client/tree/master/src/test/kotlin/com/cloudesire/partnercenter) folder.
