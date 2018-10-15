package com.yupi.ecsa.timesheet.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import com.yupi.ecsa.timesheet.config.OdooProperties;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * Created by fesqu on 17/07/2018.
 */
@Component
public class OdooClient {
    private static XMLRPCClient models;
    private static XMLRPCClient common;
    private final OdooProperties odooProperties;

    private final Logger log = LoggerFactory.getLogger(OdooClient.class);

    // private static final OdooClient ourInstance = new OdooClient();

    // public static OdooClient getInstance() {
    //     return ourInstance;
    // }

    public OdooClient(OdooProperties odooProperties) {
        this.odooProperties = odooProperties;
    }

    public void conexion() throws XMLRPCException {
        if (models != null || common != null) {
            try {
                models = new XMLRPCClient(new URL(String.format("%s/xmlrpc/2/object", odooProperties.getUrl())));
                common = new XMLRPCClient(new URL(String.format("%s/xmlrpc/2/common", odooProperties.getUrl())));
            } catch (MalformedURLException e) {
                log.error("Error al conectarse", e);
            }
        }
    }

//    public void comprobarAccesos()

    public void buscarLeer(List criteria, String clase, int limit, int offset, List atributos, XMLRPCCallback xmlrpcCallback, String orderBy) throws XMLRPCException {
        HashMap limites = new HashMap();
        if (limit != -1) {
            limites.put("limit", limit);
        }
        if (offset != -1) {
            limites.put("offset", offset);
        }
        if (atributos != null) {
            limites.put("fields", atributos);
        }
        if (orderBy != null) {
            limites.put("order", orderBy);
        }
        List search = asList(criteria);

        models.callAsync(xmlrpcCallback, "execute_kw",
            odooProperties.getDatabase(), CredentialStore.getUID(), CredentialStore.getPSW(),
            clase,
            "search_read",
            search,
            limites
        );
    }

    //
    public void crear(String clase, HashMap vals, XMLRPCCallback callback) {
        models.callAsync(callback, "execute_kw",
            odooProperties.getDatabase(), CredentialStore.getUID(), CredentialStore.getPSW(),
            clase, "create",
            Collections.singletonList(vals));
    }

    //
    public void iniciarSesion(String usuario, String password, XMLRPCCallback xmlrpcCallback) {
        common.callAsync(xmlrpcCallback, "authenticate", odooProperties.getDatabase(), usuario, password, emptyList());
    }

    //
    public void comprobarAccesos(List accesos, String odooClase, XMLRPCCallback callback) {
        models.callAsync(callback, "execute_kw",
            odooProperties.getDatabase(), CredentialStore.getUID(), CredentialStore.getPSW(),
            odooClase, "check_access_rights",
            accesos,
            new HashMap() {{
                put("raise_exception", false);
            }}
        );
    }
}
