package com.yupi.ecsa.timesheet.xmlrpc;

import com.yupi.ecsa.timesheet.config.OdooProperties;
import com.yupi.ecsa.timesheet.domain.Timesheet;
import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TimesheetService implements XMLRPCCallback {
    private final OdooClient odooClient;
    private final OdooProperties odooProperties;

    private final Logger log = LoggerFactory.getLogger(TimesheetService.class);

    public TimesheetService(OdooClient odooClient, OdooProperties odooProperties) {
        this.odooClient = odooClient;
        this.odooProperties = odooProperties;
    }


    public void create(Timesheet timesheet) {
        try {
            odooClient.conexion();
            HashMap vals = timesheet.getVals();
            vals.put("user_id", odooProperties.getUserId());
            odooClient.crear("account.analytic_account",vals,this);
        } catch (XMLRPCException e) {
            log.error("Error al conectarse", e);
        }

    }

    @Override
    public void onResponse(long id, Object result) {

    }

    @Override
    public void onError(long id, XMLRPCException error) {

    }

    @Override
    public void onServerError(long id, XMLRPCServerException error) {

    }
}
