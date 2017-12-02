package com.app.services;

import com.app.interfaces.Bank;
import com.app.model.Conta;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface = "com.app.interfaces.Bank")
public class BankImpl implements Bank {

    @Resource
    WebServiceContext wsctx;

    public void init() throws Exception {
        MessageContext mctx = wsctx.getMessageContext();
        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> username = (List<String>) http_headers.get("username");
        List<String> password = (List<String>) http_headers.get("password");

        if (username == null && username.isEmpty() && password == null && password.isEmpty())
            throw new Exception("É necessário o login do cliente para acessar");

        String user = username.get(0);
        String pass = password.get(0);

        if(!user.equals("rodrigonunes") || !pass.equals("123")){
            throw new Exception("Login ou senha do cliente incorretos");
        }
    }

    @Override
    public BigDecimal recuperarSaldo(Conta conta) throws Exception {
        init();
        return conta.getCredito().subtract(conta.getDebito().setScale(BigDecimal.ROUND_HALF_EVEN));
    }

    @Override
    public Conta acessarConta(String username, String password) throws Exception {
        init();
        Conta conta = new Conta(23342423423423L, "0558-9", "10428-0");
        conta.setDebito(new BigDecimal("6519847.28"));
        conta.setCredito(new BigDecimal("66779847.22"));
        return conta;
    }
}
