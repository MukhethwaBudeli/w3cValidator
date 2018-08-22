/**
 * Copyright (c) 2018 BITPlan GmbH
 *
 * http://www.bitplan.com
 *
 * This file is part of the Opensource project at:
 * https://github.com/WolfgangFahl/w3cValidator
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * see
 * https://github.com/WolfgangFahl/w3cValidator/blob/master/LICENSE
 */
package com.bitplan.w3ccheck;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;
import com.bitplan.w3ccheck.W3CValidator.Body.ValidationResponse.Errors.ValidationError;
import com.bitplan.w3ccheck.W3CValidator.Body.ValidationResponse.Warnings.ValidationWarning;

/**
 * test case for W3CValidator W3C Markup Validation service Java adapter
 * @author wf
 *
 */
public class TestW3CValidator {
	
	/**
	 * test the w3cValidator interface with some html code
	 * @throws Exception
	 */
	@Test
	public void testW3CValidator() throws Exception {
		String preamble="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" + 
				"   \"http://www.w3.org/TR/html4/loose.dtd\">\n"+
				"<html>\n"+
				"  <head>\n"+
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"+
				"    <title>test</title>\n"+
				"  </head>\n"+
				"  <body>\n";
		String footer="  </body>\n"+
				"</html>\n";
		String[] htmls = {
				preamble+
				"    <div>\n"+
				footer,
				// "<!DOCTYPE html><html><head><title>test W3CChecker</title></head><body><div></body></html>",
				preamble+footer+"\u0000",
				preamble+
				"<a href='http://www.bitplan.com'This is not an anchor</a>"
				+footer
		};
		int[] expectedErrs={1,0,5,0};
		int[] expectedWarnings={1,1,2,0};
		int index=0;
		System.out.println("Testing "+htmls.length+" html messages via "+W3CValidator.url);
		for (String html : htmls) {
			W3CValidator checkResult = W3CValidator.check(html);
			assertNotNull("checkResult should not be null",checkResult);
			List<ValidationError> errlist = checkResult.body.response.errors.errorlist;
			List<ValidationWarning> warnlist = checkResult.body.response.warnings.warninglist;
			if (errlist.size()>0) {
				Object first = errlist.get(0);
				assertTrue("if first is a string, than moxy is not activated",first instanceof ValidationError);
			}
			//System.out.println(first.getClass().getName());
			//System.out.println(first);
			System.out.println("Validation result for test "+(index+1)+":");
			for (ValidationError err:errlist) {
				System.out.println("\t"+err.toString());
			}
			for (ValidationWarning warn:warnlist) {
				System.out.println("\t"+warn.toString());
			}
			System.out.println();
			assertTrue(errlist.size()>=expectedErrs[index]);
			assertTrue(warnlist.size()>=expectedWarnings[index]);
			index++;
		}
	} // testW3CValidator

} // TestW3CValidator
