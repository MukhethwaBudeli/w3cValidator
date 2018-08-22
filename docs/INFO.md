

### Purpose
At http://validator.w3.org/ the W3C offers an online validation service for checking html code to
be standard compliant. There is also an API available that is described at 
http://validator.w3.org/docs/api.html

This is a Java Library to call this API. The url of the service may be the official w3c 
validator at http://validator.w3.org/check or a copy of this service installed in your own environment.


### How to use
This java Library calls the W3C Validator according to
* http://validator.w3.org/docs/api.html

## Example:
  To check some html code with an unclosed div tag with the validator
  at w3.org:

```
  // url to use - modify if you have your own installation
  public static final String url="http://validator.w3.org/check";
  
  // html code to check
  String html="<!DOCTYPE html>\n"+
    "<html>\n"+
    "  <head>\n"+
    "    <title>test W3CChecker</title>\n"+
    "  </head>\n"+
    "  <body>\n"+
    "    <div>\n"+
    "  </body>\n"+
    "</html>";
    
  // API call 
	W3CValidator checkResult = W3CValidator.check(url, html);
```
  The returned W3CValidator object has the structure of the SOAP-Response as outlined in
  http://validator.w3.org/docs/api.html
  
  e.g. checkResult.body.response.errors.errorlist will contain a list of ValidationErrors
  with line col and message for each error.
  
### Example Output (from JUnit tests):
```	
Validation result for test 1:
	ValidationError line 10 col 9:'end tag for "DIV" omitted, but its declaration does not permit this'
	ValidationWarning line 0 col 0:'Using Direct Input mode: UTF-8 character encoding assumed'

Validation result for test 2:
	ValidationError line 1 col 82:'End tag for  body seen, but there were unclosed elements.'
	ValidationError line 1 col 75:'Unclosed element div.'
	ValidationWarning line 0 col 0:'No Character encoding declared at document level'
	ValidationWarning line 0 col 0:'Using Direct Input mode: UTF-8 character encoding assumed'
```
