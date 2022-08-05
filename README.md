# This is a short demo for the Bring framework.

## NOTE: THIS PROJECT IS WRITTEN ON JAVA 17

For start up this project, please, follow the instruction bellow:
> 1. Clone the project

> 2. Create in your **.m2** (Windows example of this folder *C:\Users\Pavlo_Khshanovskyi\.m2*) folder **setting.xml** file

> 3. Add to **setting.xml** ->
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

<activeProfiles>
    <activeProfile>github</activeProfile>
</activeProfiles>

<profiles>
    <profile>
        <id>github</id>
        <repositories>
            <repository>
                <id>central</id>
                <url>https://repo1.maven.org/maven2</url>
            </repository>
            <repository>
                <id>github</id>
                <url>https://maven.pkg.github.com/maingroon/svydovets-bring</url>
                <snapshots>
                    <enabled>true</enabled>
                </snapshots>
            </repository>
        </repositories>
    </profile>
</profiles>

<servers>
    <server>
        <id>github</id>
        <username>your_github_email</username>
        <password>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</password>
    </server>
</servers>
</settings>             
``` 
> 4. Replace *your_github_email* in this file on your email from the GitHub account

> 5. Build the project.

> 6. Setup Tomcat with version not less than 10
<details>
<summary> screenshots: </summary> 

> ![tom-1](https://user-images.githubusercontent.com/55089853/183022183-ce323574-7062-4298-8818-11deddf7c9ae.png)
> ![Screenshot 2022-08-05 100956](https://user-images.githubusercontent.com/55089853/183022290-e0447ccf-72b7-470a-975e-34fad2d6f07c.png)
</details>

> 7. Start server.
