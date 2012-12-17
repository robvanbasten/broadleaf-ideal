package com.bvb.ideal.service.payment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.bvb.ideal.service.payment.message.IdealRequest;
import com.bvb.ideal.service.payment.type.IdealMethodType;

import java.util.List;

import javax.annotation.Resource;
	 
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="IdealRequestGeneratorTest-context.xml")
public class IdealRequestGeneratorTest {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testMethod() {
		System.out.println("xxxxxxxxaaaaaaaaa");
		IdealRequestGenerator requestGenerator = applicationContext.getBean(IdealRequestGenerator.class);
		System.out.println("bbbbbbbaaaaaaaaa");
		IdealRequest idealRequest = new IdealRequest();
		System.out.println("aaaaaaaaa");
		idealRequest.setMethodType(IdealMethodType.ISSUER);
		String request = requestGenerator.buildRequest(idealRequest);
		System.out.println("Request: " + request);
	}
	
	
}
