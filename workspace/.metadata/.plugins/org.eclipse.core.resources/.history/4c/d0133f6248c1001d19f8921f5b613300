/**
 * StockQuoteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package samples;

public class StockQuoteServiceLocator extends com.ibm.ws.webservices.multiprotocol.AgnosticService implements com.ibm.ws.webservices.multiprotocol.GeneratedService, samples.StockQuoteService {

    public StockQuoteServiceLocator() {
        super(com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
           "http://samples",
           "StockQuoteService"));

        context.setLocatorName("samples.StockQuoteServiceLocator");
    }

    public StockQuoteServiceLocator(com.ibm.ws.webservices.multiprotocol.ServiceContext ctx) {
        super(ctx);
        context.setLocatorName("samples.StockQuoteServiceLocator");
    }

    // Use to get a proxy class for stockQuote
    private final java.lang.String stockQuote_address = "http://localhost:9080/StockQuote/services/StockQuote";

    public java.lang.String getStockQuoteAddress() {
        if (context.getOverriddingEndpointURIs() == null) {
            return stockQuote_address;
        }
        String overriddingEndpoint = (String) context.getOverriddingEndpointURIs().get("StockQuote");
        if (overriddingEndpoint != null) {
            return overriddingEndpoint;
        }
        else {
            return stockQuote_address;
        }
    }

    private java.lang.String stockQuotePortName = "StockQuote";

    // The WSDD port name defaults to the port name.
    private java.lang.String stockQuoteWSDDPortName = "StockQuote";

    public java.lang.String getStockQuoteWSDDPortName() {
        return stockQuoteWSDDPortName;
    }

    public void setStockQuoteWSDDPortName(java.lang.String name) {
        stockQuoteWSDDPortName = name;
    }

    public samples.StockQuote getStockQuote() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getStockQuoteAddress());
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getStockQuote(endpoint);
    }

    public samples.StockQuote getStockQuote(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        samples.StockQuote _stub =
            (samples.StockQuote) getStub(
                stockQuotePortName,
                (String) getPort2NamespaceMap().get(stockQuotePortName),
                samples.StockQuote.class,
                "samples.StockQuoteSoapBindingStub",
                portAddress.toString());
        if (_stub instanceof com.ibm.ws.webservices.engine.client.Stub) {
            ((com.ibm.ws.webservices.engine.client.Stub) _stub).setPortName(stockQuoteWSDDPortName);
        }
        return _stub;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (samples.StockQuote.class.isAssignableFrom(serviceEndpointInterface)) {
                return getStockQuote();
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("WSWS3273E: Error: There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        String inputPortName = portName.getLocalPart();
        if ("StockQuote".equals(inputPortName)) {
            return getStockQuote();
        }
        else  {
            throw new javax.xml.rpc.ServiceException();
        }
    }

    public void setPortNamePrefix(java.lang.String prefix) {
        stockQuoteWSDDPortName = prefix + "/" + stockQuotePortName;
    }

    public javax.xml.namespace.QName getServiceName() {
        return com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://samples", "StockQuoteService");
    }

    private java.util.Map port2NamespaceMap = null;

    protected synchronized java.util.Map getPort2NamespaceMap() {
        if (port2NamespaceMap == null) {
            port2NamespaceMap = new java.util.HashMap();
            port2NamespaceMap.put(
               "StockQuote",
               "http://schemas.xmlsoap.org/wsdl/soap/");
        }
        return port2NamespaceMap;
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            String serviceNamespace = getServiceName().getNamespaceURI();
            for (java.util.Iterator i = getPort2NamespaceMap().keySet().iterator(); i.hasNext(); ) {
                ports.add(
                    com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                        serviceNamespace,
                        (String) i.next()));
            }
        }
        return ports.iterator();
    }

    public javax.xml.rpc.Call[] getCalls(javax.xml.namespace.QName portName) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
        if  (portName.getLocalPart().equals("StockQuote")) {
            return new javax.xml.rpc.Call[] {
                createCall(portName, "getQuote", "getQuoteRequest"),
            };
        }
        else {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
    }
}
