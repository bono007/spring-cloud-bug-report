# spring-cloud-bug-report

To reproduce the bug, execute

    mvn clean test
    
The test class `ERRORControllerTes` contains the tests (nested classes) that reproduce the error. Notice that the nested classes are only to ensure the order of the tests. The error can be reproduced as well if you execute all tests in expanded package following the order: FirstControllerTest, SecondControllerTest and ThirdControllerTest.

The test class `OKControllerTest` contains  version which does not fail

