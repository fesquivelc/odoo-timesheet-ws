package com.yupi.ecsa.timesheet.xmlrpc;

import com.yupi.ecsa.timesheet.domain.Timesheet;
import de.timroes.axmlrpc.XMLRPCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TimesheetService {
    private final OdooClient odooClient;

    private final Logger log = LoggerFactory.getLogger(TimesheetService.class);

    public TimesheetService(OdooClient odooClient) {
        this.odooClient = odooClient;
    }


    public void create(Timesheet timesheet) {
        try {
            odooClient.conexion();
            odooClient.crear("account.analytic_account");
        } catch (XMLRPCException e) {
            log.error("Error al conectarse", e);
        }

    }
}
