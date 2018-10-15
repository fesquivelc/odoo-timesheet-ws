package com.yupi.ecsa.timesheet.xmlrpc;

public class CredentialStore {
    public enum NivelAcceso {
        COORDINADOR,
        ASISTENTE
    }

    private static Integer UID;
    private static String PSW;
    private static NivelAcceso nivelAcceso;

    public static Integer getUID() {
        return UID;
    }

    public static void setUID(Integer UID) {
        CredentialStore.UID = UID;
    }

    public static String getPSW() {
        return PSW;
    }

    public static void setPSW(String PSW) {
        CredentialStore.PSW = PSW;
    }

    public static NivelAcceso getNivelAcceso() {
        return nivelAcceso;
    }

    public static void setNivelAcceso(NivelAcceso nivelAcceso) {
        CredentialStore.nivelAcceso = nivelAcceso;
    }
}
