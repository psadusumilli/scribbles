Reference
http://blog.inflinx.com/2013/12/29/maven-bill-of-materials/
https://medium.com/java-user-group-malta/maven-s-bill-of-materials-bom-b430ede60599#.2ori07vww
https://books.sonatype.com/mvnref-book/reference/pom-relationships-sect-pom-syntax.html


CHAP 1 Dependency management
.......................................................................................................................................................................................................................................................
1 TRANSITIVE DEPENDENCIES
    This allows you to avoid needing to discover and specify the libraries that your own dependencies require, and including them automatically.
    But the following mechanisms help in avoiding cyclic patterns and depth of trees

    1.1 'Dependency Mediation'
          Nearest in depth is always picked when multiple versions of same dependency is present.
          A > B > C > D 2.0  &&  A  > X > D 1.0
          Here D 1.0 will be picked up.
    1.2 'Dependency Management'
          - to directly specify the versions of artifacts to be used when they are encountered in transitive dependencies
          - or in dependencies where no version has been specified
          - parent and child poms where children dont specify version explicitly.
          'dependency management takes precedence over dependency mediation'
    1.3 'Excluded dependencies'
          - If  X > Y > Z, then X can explicitly exclude Z, using the "exclusion" element.
    1.4 'Optional dependencies'
          - If Y > Z, the owner of project Y can mark project Z as an optional dependency, using the "optional" element.
          - When X > Y, X will depend only on Y and not on Y optional dependency Z.
          - X may then explicitly add a dependency on Z, at her option. (optional dependencies as "excluded by default.")
    'Scope'
        compile => required for compiling and used during execution (default)
        provided => will be provided by frameworks like servlet-api
        runtime => not required for compiling, but for execution
        test => only for testing
        system => similar to provided, but not from maven repo but a system path.
        import => used only within <dependencyManagement>, when done say A import B, all the dependencies in B <dependencyManagement> will be imported.
//................................................
<dependencyManagement>
1 'Parent /Child Poms'
    - X is parent of A & B
    - A > a 1.0 (exclude c) + b 1.0
    - B > c 1.0 + b 1.0
    - X parent-pom can have <dependencyManagement> [ a 1.0  (exclude c) + b 1.0 + c 1.0 ]
          A > a + b, B > c + b
2 'transitive version mgmt'
    - A > <dependencyManagement> [ a 1.2 + b 1.0 + c 1.0 + d 1.2 ]
      - B (child of A) > <dependencyManagement> [ d 1.0 ] + a 1.0 + c (no version)
      'result'
      B => [a 1.0 + b 1.0 + c 1.0 + d 1.0]
3 'importing poms'
    -  Z > X(pom) + Y(pom)
            All X+Y dependencyManagement dependencies would be imported
            If common found, X takes precedence because of declaration order
4 'BOM- Bill of Materials'
   4.1 create a BOM
    - Create a library of artifacts that can be used
          The root of the project is the BOM pom. It defines the versions of all the artifacts that will be created in the library.
            - artifact=bom | packaging=pom | dependencies=project1+project2 | modules=parent
                  - artifact=parent | parent=bom | dependencies=some open libs | modules=project1, project2
                        - artifact=project1 | parent=parent | dependencies=some open libs
                        - artifact=project2 | parent=parent | dependencies=some open libs
    4.2 use the BOM
          artifact=client | dependencyManagement=bom (import) | dependencies=project1,project2

   4.3 A better example, say you want same version of spring libraries, then import spring BOM
         http://mvnrepository.com/artifact/org.springframework/spring-framework-bom/4.2.4.RELEASE
            <dependencyManagement>
                <dependencies>
                    <dependency>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-framework-bom</artifactId>
                        <version>4.0.0.RC2</version>
                        <type>pom</type>
                        <scope>import</scope>
                    </dependency>
                </dependencies>
            </dependencyManagement>
            then all the spring dependencies like spring-orm and their transitive dependencies will be from the imported BOM
            <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
            </dependency>

CHAP 2 Versioning
......................................................................................................................................................................................................................................................
Standard scheme is
      '<major version>.<minor version>.<incremental version>-<qualifier>'
      1.3.4-alpha
qualifier is compared as String while the major/minor/incremental are compared as Integers
Active developement uses snapshots
      1.0-SNAPSHOT
      Maven would expand this version to UTC timezone “1.0-20080207-230803-1”
      if you were to deploy a release at 11:08 PM on February 7th, 2008 UTC

'versoning ranges'
(, ) =>Exclusive quantifiers
[, ] =>Inclusive quantifiers
samples
   [3.8,4.0) => 3.8 >= x < 4
   [,3.8.1]  =>  x < = 3.8.1
   [4.0,) => x >= 4.0
   [1.2]  => x = 1.2
   (,1.0],[1.2,) => x <= 1.0 or x >= 1.2. (multiple sets separated by comma here)
