/*
 *
 * JHelloWorld
 *
 */

import com.ms.com.*;
import com.ms.asp.*;

class JHelloWorld 
{

	public String Greeting = "";

	IResponse m_iResponse = null;
	IRequest m_iRequest = null;

	public void OnStartPage(IScriptingContext objContext) {

		m_iResponse = objContext.getResponse();
		m_iRequest = objContext.getRequest();

	}

	public void OnEndPage() {

		m_iResponse = null;
		m_iRequest = null;

	}

	public void HelloWorld() { 

		IRequestDictionary serverVariables = m_iRequest.getServerVariables();
		Variant hname = new Variant();
		hname.putString("REMOTE_HOST");
		Variant v = serverVariables.getItem(hname);
		hname.putString(Greeting + "<p>Welcome from " + v.toString());
		m_iResponse.Write(hname);

	}

}

