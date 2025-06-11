package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
//@SelectClasses({createjobstest.class,assesmentcriteriatest.class ,copyjobbeneittest.class, copyjobfromclienttest.class,copyjobquestiontest.class, copyjobtest.class, jobapplytest.class, marketinsighttest.class, teamsandroletest.class, interviewtest.class })
@SelectClasses({qrcodetest.class})
public class AllTestsTest {
	
}