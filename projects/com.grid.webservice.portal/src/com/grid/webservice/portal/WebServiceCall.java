package com.grid.webservice.portal;

import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
//import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPEnvelope;

public class WebServiceCall {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String url = "http://www50.brinkster.com/vbfacileinpt/np.asmx";         
        Service service = new Service();
        
        Call call = (Call)service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(url));
        call.setSOAPActionURI("http://microsoft.com/webservices/GetPrimeNumbers");
        call.setOperationName("GetPrimeNumbers");
        

        //String ret = (String)call.invoke(new Object[]{"100"});
        SOAPEnvelope ret = call.invoke(new Message("<soapenv:Envelope  xmlns:q0=\"http://microsoft.com/webservices/\"  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><q0:GetPrimeNumbers><q0:max>100</q0:max></q0:GetPrimeNumbers></soapenv:Body></soapenv:Envelope>"));
        System.out.println( ret);
        
        System.out.println("Over!");

	}

}
