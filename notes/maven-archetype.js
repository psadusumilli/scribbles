step#1
  create the sample code base sample-job
//.................................................................................................................
step#2
  mvn archetype:create-from-project
//.................................................................................................................
step#3
  cp -r sample-job/target/generated-sources/archetype <home>/archetype
//.................................................................................................................
step#4
  vim <home>/archetype/pom.xml

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.equifax.cse.dep</groupId>
      <artifactId>build-batch-job-archetype</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <packaging>maven-archetype</packaging>
      <name>build-batch-job-archetype</name>
      <build>
        <extensions>
          <extension>
            <groupId>org.apache.maven.archetype</groupId>
            <artifactId>archetype-packaging</artifactId>
            <version>2.4</version>
          </extension>
        </extensions>
        <pluginManagement>
          <plugins>
            <plugin>
              <artifactId>maven-archetype-plugin</artifactId>
              <version>2.4</version>
            </plugin>
          </plugins>
        </pluginManagement>
      </build>
      <description>job-archetype 1.0.0-SNAPSHOT</description>
    </project>
//.................................................................................................................
step#5
vim <home>/archetype/src/main/resources/META-INF/maven/archetype-metadata.xml
add the required properties

<?xml version="1.0" encoding="UTF-8"?>
<archetype-descriptor
        xsi:schemaLocation="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0 http://maven.apache.org/xsd/archetype-descriptor-1.0.0.xsd"
        name="job-archetype"
        xmlns="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <requiredProperties>
        <requiredProperty key="groupId">
            <defaultValue>com.equifax.dep</defaultValue>
        </requiredProperty>
        <requiredProperty key="artifactId">
            <defaultValue>build-batch-job</defaultValue>
        </requiredProperty>
        <requiredProperty key="version">
            <defaultValue>1.0.0-SNAPSHOT</defaultValue>
        </requiredProperty>
        <requiredProperty key="package">
            <defaultValue>com.equifax.dep</defaultValue>
        </requiredProperty>
    </requiredProperties>
    <fileSets>
        <fileSet filtered="true" packaged="true" encoding="UTF-8">
            <directory>src/main/java</directory>
            <includes>
                // <include>**/*.java</include>
            </includes>
        </fileSet>
        <fileSet encoding="UTF-8">
            <directory></directory>
            <includes>
                <include>job-archetype.iml</include>
            </includes>
        </fileSet>
    </fileSets>
</archetype-descriptor>


//.................................................................................................................
step#6 - add custom properties
vim <home>/archetypesrc/test/resources/projects/basic/archetype.properties

Wed Jun 29 20:07:57 EDT 2016
package=com.equifax.dep
version=1.0.0-SNAPSHOT
groupId=com.equifax.dep
artifactId=build-batch-job
~
//.................................................................................................................
step#7 - install the archetype to local .m2
cd <home>/archetype/
mvn install

NFO] Parameter: groupId, Value: com.equifax.dep
[INFO] Parameter: artifactId, Value: build-batch-job
[INFO] Parameter: version, Value: 1.0.0-SNAPSHOT
[INFO] Parameter: package, Value: com.equifax.dep
[INFO] Parameter: packageInPathFormat, Value: com/equifax/dep
[INFO] Parameter: version, Value: 1.0.0-SNAPSHOT
[INFO] Parameter: package, Value: com.equifax.dep
[INFO] Parameter: groupId, Value: com.equifax.dep
[INFO] Parameter: artifactId, Value: build-batch-job
[INFO] project created from Archetype in dir: /Users/vxr63/code/dep/personal/vijay/archetype/target/test-classes/projects/basic/project/build-batch-job
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ build-batch-job-archetype ---
[INFO] Installing /Users/vxr63/code/dep/personal/vijay/archetype/target/build-batch-job-archetype-1.0.0-SNAPSHOT.jar to /Users/vxr63/.m2/repository/com/equifax/cse/dep/build-batch-job-archetype/1.0.0-SNAPSHOT/build-batch-job-archetype-1.0.0-SNAPSHOT.jar
[INFO] Installing /Users/vxr63/code/dep/personal/vijay/archetype/pom.xml to /Users/vxr63/.m2/repository/com/equifax/cse/dep/build-batch-job-archetype/1.0.0-SNAPSHOT/build-batch-job-archetype-1.0.0-SNAPSHOT.pom
[INFO]
[INFO] --- maven-archetype-plugin:2.4:update-local-catalog (default-update-local-catalog) @ build-batch-job-archetype ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
//.................................................................................................................
step#8
mkdir <home>/test-artifact/
cd <home>/test-artifact/

mvn archetype:generate -DarchetypeCatalog=local
Choose archetype:
1: local -> com.equifax.cse.dep:job-archetype-archetype (job-archetype 1.0.0-SNAPSHOT)
2: local -> com.equifax.cse.dep:build-batch-job-archetype (job-archetype 1.0.0-SNAPSHOT)
Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): : 2
