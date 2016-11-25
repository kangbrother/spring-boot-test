package test;

import org.junit.Test;

import com.lk.action.HelloWorld;

public class HelloWorldTest {
	@Test
	public void sayHello(){
		System.out.println(new HelloWorld().sayHello());
		
	}
}
