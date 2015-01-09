package samples;

public class StockQuoteProxy implements samples.StockQuote {
  private boolean _useJNDI = true;
  private String _endpoint = null;
  private samples.StockQuote __stockQuote = null;
  
  public StockQuoteProxy() {
    _initStockQuoteProxy();
  }
  
  private void _initStockQuoteProxy() {
  
  if (_useJNDI) {
    try{
      javax.naming.InitialContext ctx = new javax.naming.InitialContext();
      __stockQuote = ((samples.StockQuoteService)ctx.lookup("java:comp/env/service/StockQuoteService")).getStockQuote();
      }
    catch (javax.naming.NamingException namingException) {}
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  if (__stockQuote == null) {
    try{
      __stockQuote = (new samples.StockQuoteServiceLocator()).getStockQuote();
      }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  if (__stockQuote != null) {
    if (_endpoint != null)
      ((javax.xml.rpc.Stub)__stockQuote)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    else
      _endpoint = (String)((javax.xml.rpc.Stub)__stockQuote)._getProperty("javax.xml.rpc.service.endpoint.address");
  }
  
}


public void useJNDI(boolean useJNDI) {
  _useJNDI = useJNDI;
  __stockQuote = null;
  
}

public String getEndpoint() {
  return _endpoint;
}

public void setEndpoint(String endpoint) {
  _endpoint = endpoint;
  if (__stockQuote != null)
    ((javax.xml.rpc.Stub)__stockQuote)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
  
}

public samples.StockQuote getStockQuote() {
  if (__stockQuote == null)
    _initStockQuoteProxy();
  return __stockQuote;
}

public float getQuote(java.lang.String symbol) throws java.rmi.RemoteException{
  if (__stockQuote == null)
    _initStockQuoteProxy();
  return __stockQuote.getQuote(symbol);
}


}