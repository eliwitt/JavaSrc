/**
 * StockQuoteSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package samples;

public class StockQuoteSoapBindingStub extends com.ibm.ws.webservices.engine.client.Stub implements samples.StockQuote {
    public StockQuoteSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws com.ibm.ws.webservices.engine.WebServicesFault {
        if (service == null) {
            super.service = new com.ibm.ws.webservices.engine.client.Service();
        }
        else {
            super.service = service;
        }
        super.engine = ((com.ibm.ws.webservices.engine.client.Service) super.service).getEngine();
        initTypeMapping();
        super.cachedEndpoint = endpointURL;
        super.connection = ((com.ibm.ws.webservices.engine.client.Service) super.service).getConnection(endpointURL);
        super.messageContexts = new com.ibm.ws.webservices.engine.MessageContext[1];
    }

    private void initTypeMapping() {
        javax.xml.rpc.encoding.TypeMapping _tm = super.getTypeMapping(com.ibm.ws.webservices.engine.Constants.URI_LITERAL_ENC);
        java.lang.Class _javaType = null;
        javax.xml.namespace.QName _xmlType = null;
        javax.xml.namespace.QName _compQName = null;
        javax.xml.namespace.QName _compTypeQName = null;
        com.ibm.ws.webservices.engine.encoding.SerializerFactory _sf = null;
        com.ibm.ws.webservices.engine.encoding.DeserializerFactory _df = null;
    }

    private static final com.ibm.ws.webservices.engine.description.OperationDesc _getQuoteOperation0;
    static {
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "symbol"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false, false, false, true, false), 
          };
        _params0[0].setOption("partQNameString","{http://www.w3.org/2001/XMLSchema}string");
        _params0[0].setOption("partName","string");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "getQuoteReturn"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "float"), float.class, true, false, false, false, true, false); 
        _returnDesc0.setOption("partQNameString","{http://www.w3.org/2001/XMLSchema}float");
        _returnDesc0.setOption("partName","float");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        _getQuoteOperation0 = new com.ibm.ws.webservices.engine.description.OperationDesc("getQuote", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://samples", "getQuote"), _params0, _returnDesc0, _faults0, "");
        _getQuoteOperation0.setOption("targetNamespace","http://samples");
        _getQuoteOperation0.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://samples", "StockQuote"));
        _getQuoteOperation0.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://samples", "getQuoteResponse"));
        _getQuoteOperation0.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://samples", "getQuoteRequest"));
        _getQuoteOperation0.setOption("outputName","getQuoteResponse");
        _getQuoteOperation0.setOption("inputName","getQuoteRequest");
        _getQuoteOperation0.setOption("buildNum","o0526.04");
        _getQuoteOperation0.setUse(com.ibm.ws.webservices.engine.enum.Use.LITERAL);
        _getQuoteOperation0.setStyle(com.ibm.ws.webservices.engine.enum.Style.WRAPPED);
    }

    private int _getQuoteIndex0 = 0;
    private synchronized com.ibm.ws.webservices.engine.client.Stub.Invoke _getgetQuoteInvoke0(Object[] _parameters) throws com.ibm.ws.webservices.engine.WebServicesFault  {
        com.ibm.ws.webservices.engine.MessageContext _mc = super.messageContexts[_getQuoteIndex0];
        if (_mc == null) {
            _mc = new com.ibm.ws.webservices.engine.MessageContext(super.engine);
            _mc.setOperation(StockQuoteSoapBindingStub._getQuoteOperation0);
            _mc.setUseSOAPAction(true);
            _mc.setSOAPActionURI("");
            _mc.setEncodingStyle(com.ibm.ws.webservices.engine.Constants.URI_LITERAL_ENC);
            _mc.setProperty(com.ibm.ws.webservices.engine.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
            _mc.setProperty(com.ibm.ws.webservices.engine.WebServicesEngine.PROP_DOMULTIREFS, Boolean.FALSE);
            super.primeMessageContext(_mc);
            super.messageContexts[_getQuoteIndex0] = _mc;
        }
        try {
            _mc = (com.ibm.ws.webservices.engine.MessageContext) _mc.clone();
        }
        catch (CloneNotSupportedException _cnse) {
            throw com.ibm.ws.webservices.engine.WebServicesFault.makeFault(_cnse);
        }
        return new com.ibm.ws.webservices.engine.client.Stub.Invoke(connection, _mc, _parameters);
    }

    public float getQuote(java.lang.String symbol) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new com.ibm.ws.webservices.engine.NoEndPointException();
        }
        java.util.Vector _resp = null;
        try {
            _resp = _getgetQuoteInvoke0(new java.lang.Object[] {symbol}).invoke();

        } catch (com.ibm.ws.webservices.engine.WebServicesFault _wsf) {
            Exception _e = _wsf.getUserException();
            throw _wsf;
        } 
        try {
            return ((java.lang.Float) ((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue()).floatValue();
        } catch (java.lang.Exception _exception) {
            return ((java.lang.Float) super.convert(((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue(), float.class)).floatValue();
        }
    }

}
