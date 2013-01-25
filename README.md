ngmon-pub-sub-benchmark
=======================

Installing Siena to local Maven repo:
1. Download Siena source package.
2. Add pom.xml with groupId "siena" and artifactId "siena" to the siena directory. For example:

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>siena</groupId>
	<artifactId>siena</artifactId>
	<version>2.0.3</version>
	<name>Siena</name>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>2.5.1</version>
				<configuration>
					<source>1.7</source>
					<target>1.7</target>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-resources-plugin</artifactId>
				<version>2.6</version>
				<configuration>
					<encoding>UTF-8</encoding>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>

3. Move src/siena directory to src/main/java/siena.
4. Run "maven package" + "maven install".
