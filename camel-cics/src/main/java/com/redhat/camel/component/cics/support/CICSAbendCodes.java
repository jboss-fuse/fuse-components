package com.redhat.camel.component.cics.support;

public interface CICSAbendCodes {

    /**
     * Either an incorrect response (other than PURGED) was returned from the suspend of the allocated task, or an incorrect response was returned from the resum
     * The transaction is abnormally terminated with a dump.
     * Check the return code from the resume or the suspend to determine the cause of the error.
     */
    String AAL2 = "AAL2";


    /**
     * The task has been purged before a GETMAIN request to the storage manager (SM) domain was able to complete successfully. The task that first detected the purged condition provides an exception trace.
     * The task is abnormally terminated with a CICS transaction dump.
     * It was purged either by the master terminal operator or as a result of a deadlock timeout. Investigate the reason why the task was purged.
     */
    String AAL3 = "AAL3";


    /**
     * An error has occurred on a call to the CICS/DB2 table manager DFHD2TM. The domain that detected the original error provides a trace entry and possibly a system dump.
     * The task is abnormally terminated with a CICS transaction dump.
     * Inform the system programmer. Examine the trace and the dump to identify the point of error.
     */
    String AALO = "AALO";


    /**
     * The text data overflow routines have been reentered while text overflow was in process. This condition occurs when the line requirements for the text header and/or trailer exceed the line capacity of the page for which data is being formatted.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Reduce the number of lines required for the header and/or trailer or increase the page size of the terminal.
     */
    String ABM9 = "ABM9";


    /**
     * A BMS text request specified a value for the JUSTIFY option which is zero or too large for the page being built.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Correct the application program that specified too large or zero value for the JUSTIFY option.
     */
    String ABM8 = "ABM8";


    /**
     * The trailer specified to be used while building pages of text data is longer than the page.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Correct the application program that issues the request with too long a trailer.
     */
    String ABM7 = "ABM7";


    /**
     * A BMS input or output request has been issued from a task that is not terminal-oriented.
     * The task is abnormally terminated with a CICS dump.
     * The task issuing a BMS input or output request must be attached to a terminal.
     */
    String ABM3 = "ABM3";


    /**
     * A basic mapping support (BMS) service is requested by a task associated with a terminal that is not supported by BMS. The request is not a routing request.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Do not use terminals not supported by BMS for applications using BMS services.
     */
    String ABM1 = "ABM1";


    /**
     * The map specified for a basic mapping support (BMS) request could not be located.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Check if the map has been defined. If it has, check that it has been specified correctly.
     */
    String ABM0 = "ABM0";


    /**
     * The user has specified a cursor position in the BMS output request. It is larger than the current screen size for the 3270 for which output is being built.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Correct the program that specified the incorrect cursor location.
     */
    String ABMB = "ABMB";


    /**
     * The user has supplied a terminal I/O area (TIOA) with an invalid data length that was either equal to zero or greater than the storage accounting length minus 12.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Correct the program that supplied the erroneous data length.
     */
    String ABMA = "ABMA";


    /**
     * The value specified for the length option of the basic mapping support (BMS) send map is greater than the length of the 'from' area.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Redefine the value for the length option.
     */
    String ABMF = "ABMF";


    /**
     * "A text string passed to BMS contained a set attribute order that was invalid for one of the following reasons:The set attribute sequence was less than three characters.The attribute type was invalid."
     * The task is abnormally terminated with a CICS transaction dump.
     * Correct the application program.
     */
    String ABMX = "ABMX";


    /**
     * The application program supplied an address that is not within region boundaries.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Correct the application program that is supplying the erroneous address.
     */
    String ABMU = "ABMU";


    /**
     * The map specified for a BMS output mapping request was not an output map.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Either define another output map or redefine the existing map.
     */
    String ABMO = "ABMO";


    /**
     * An invalid map was specified.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Use the supplied dump to diagnose the problem.
     */
    String ABMM = "ABMM";


    /**
     * The map specified for a BMS input mapping request was not an input map.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Either define another input map or redefine the existing map.
     */
    String ABMI = "ABMI";


    /**
     * The client code page which has been requested by the client is not one which CICS can support.
     * The transaction is abnormally terminated with a transaction dump.
     * Ensure that the Client codepage is valid.
     */
    String ABXV = "ABXV";


    /**
     * An error occurred when a SYNCPOINT request was issued by the bridge exit.
     * The task is abnormally terminated with a CICS transaction dump.
     * Check for other CICS messages and exception trace entries to investigate the cause of the SYNCPOINT error.
     */
    String ABXC = "ABXC";


    /**
     * An error occurred when a SYNCPOINT ROLLBACK request was issued by the bridge exit.
     * The task is abnormally terminated with a CICS transaction dump.
     * Check for other CICS messages and exception trace entries to investigate the cause of the SYNCPOINT ROLLBACK error.
     */
    String ABXD = "ABXD";


    /**
     * The user transaction's profile could not be found.
     * The task is abnormally terminated with a CICS transaction dump.
     * Check that the profile name in the user transaction definition is correct, and that this profile has been defined.
     */
    String ABRR = "ABRR";


    /**
     * An attempt to access a temporary storage queue failed.
     * The task is abnormally terminated with a CICS transaction dump.
     * Ensure that temporary storage is correctly generated.
     */
    String ABNC = "ABNC";


    /**
     * An error response was received from an invocation of a BMS TYPE=ROUTE or TYPE=STORE request.
     * The task is abnormally terminated with a CICS transaction dump.
     * Check that BMS was correctly generated.
     */
    String ABNE = "ABNE";


    /**
     * An attempt to ship data to the remote system failed.
     * The task is abnormally terminated with a CICS transaction dump.
     * Inform the system administrator.
     */
    String ABNH = "ABNH";


    /**
     * An APPC conversation failure occurred when an attach between CICS systems was issued.
     * The task is abnormally terminated with a CICS transaction dump.
     * Check the connection to the remote CICS system and try to reestablish it.
     */
    String ACSI = "ACSI";


    /**
     * An IPIC conversation failure occurred when an attach between CICS systems was issued.
     * The task is abnormally terminated with a CICS transaction dump.
     * Check the connection to the remote CICS system and try to reestablish it.
     */
    String ACSO = "ACSO";


    /**
     * The application program called an AMODE(64) command stub, but was not executing in AMODE(64).
     * The task is abnormally terminated with a CICS transaction dump.
     * Ensure that the application program is in the correct AMODE and is calling the correct stub program.
     */
    String AEE1 = "AEE1";


    /**
     * An application that is executing in AMODE(64) called CICS using a stub program that does not support AMODE(64).
     * The task is abnormally terminated with a CICS transaction dump.
     * Ensure that the application program calls the correct stub program in the correct AMODE.
     */
    String AEE0 = "AEE0";


    /**
     * ERROR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIA = "AEIA";


    /**
     * EOF
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEID = "AEID";


    /**
     * EODS
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIE = "AEIE";


    /**
     * INBFMH
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIG = "AEIG";


    /**
     * ENDINPT
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AIEH = "AIEH";


    /**
     * NONVAL
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEII = "AEII";


    /**
     * NOSTART
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIJ = "AEIJ";


    /**
     * TERMIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIK = "AEIK";


    /**
     * FILENOTFOUND
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIL = "AEIL";


    /**
     * NOTFND
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIM = "AEIM";


    /**
     * DUPREC
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIN = "AEIN";


    /**
     * DUPKEY
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIO = "AEIO";


    /**
     * INVREQ
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIP = "AEIP";


    /**
     * IOERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIQ = "AEIQ";


    /**
     * NOSPACE
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIR = "AEIR";


    /**
     * NOTOPEN
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIS = "AEIS";


    /**
     * ENDFILE
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIT = "AEIT";


    /**
     * ILLOGIC
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIU = "AEIU";


    /**
     * LENGERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIV = "AEIV";


    /**
     * QZERO
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIW = "AEIW";


    /**
     * ITEMERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEIZ = "AEIZ";


    /**
     * PGMIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEI0 = "AEI0";


    /**
     * TRANSIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEI1 = "AEI1";


    /**
     * ENDDATA
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEI2 = "AEI2";


    /**
     * INVTSREQ
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEI3 = "AEI3";


    /**
     * EXPIRED
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEI4 = "AEI4";


    /**
     * TSIOERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEI8 = "AEI8";


    /**
     * MAPFAIL
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEI9 = "AEI9";


    /**
     * RESIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXC = "AEXC";


    /**
     * ESCERROR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXF = "AEXF";


    /**
     * UOWLNOTFOUND
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXG = "AEXG";


    /**
     * TERMERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXI = "AEXI";


    /**
     * ROLLEDBACK
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXJ = "AEXJ";


    /**
     * END
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXK = "AEXK";


    /**
     * DISABLED
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXL = "AEXL";


    /**
     * NOTPOSS
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXU = "AEXU";


    /**
     * VOLIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXV = "AEXV";


    /**
     * TASKIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXW = "AEXW";


    /**
     * DSNNOTFOUND
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX1 = "AEX1";


    /**
     * LOADING
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX2 = "AEX2";


    /**
     * MODELIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX3 = "AEX3";


    /**
     * UOWNOTFOUND
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX4 = "AEX4";


    /**
     * PARTNERIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX5 = "AEX5";


    /**
     * PROFILEIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX6 = "AEX6";


    /**
     * NETNAMEIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX7 = "AEX7";


    /**
     * LOCKED
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX8 = "AEX8";


    /**
     * RECORDBUSY
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEX9 = "AEX9";


    /**
     * INVERRTERM
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYA = "AEYA";


    /**
     * INVMPSZ
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring, to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYB = "AEYB";


    /**
     * IGREQID
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYC = "AEYC";


    /**
     * INVLDC
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYE = "AEYE";


    /**
     * JIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYG = "AEYG";


    /**
     * QIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYH = "AEYH";


    /**
     * DSSTAT
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYJ = "AEYJ";


    /**
     * SELNERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYK = "AEYK";


    /**
     * FUNCERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYL = "AEYL";


    /**
     * UNEXPIN
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYM = "AEYM";


    /**
     * NOPASSBKRD
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYN = "AEYN";


    /**
     * NOPASSBKWR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYO = "AEYO";


    /**
     * SEGIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYP = "AEYP";


    /**
     * SYSIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYQ = "AEYQ";


    /**
     * ISCINVREQ
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYR = "AEYR";


    /**
     * ENVDEFERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYT = "AEYT";


    /**
     * IGREQCD
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYU = "AEYU";


    /**
     * SESSIONERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYV = "AEYV";


    /**
     * USERIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYX = "AEYX";


    /**
     * CBIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEYZ = "AEYZ";


    /**
     * INVEXITREQ
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEY0 = "AEY0";


    /**
     * INVPARTNSET
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEY1 = "AEY1";


    /**
     * INVPARTIN
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEY2 = "AEY2";


    /**
     * PARTNFALL
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEY3 = "AEY3";


    /**
     * NOTAUTH
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEY7 = "AEY7";


    /**
     * CHANGED
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZE = "AEZE";


    /**
     * PROCESSBUSY
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZF = "AEZF";


    /**
     * ACTIVITYBUSY
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZG = "AEZG";


    /**
     * PROCESSERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZH = "AEZH";


    /**
     * ACTIVITYERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZI = "AEZI";


    /**
     * CONTAINERERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZJ = "AEZJ";


    /**
     * EVENTERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZK = "AEZK";


    /**
     * TOKENERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZL = "AEZL";


    /**
     * NOTFINISHED
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZM = "AEZM";


    /**
     * POOLERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZN = "AEZN";


    /**
     * TIMERERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZO = "AEZO";


    /**
     * SYMBOLERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEXP = "AEXP";


    /**
     * TEMPLATERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZQ = "AEZQ";


    /**
     * NOTSUPERUSER
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZR = "AEZR";


    /**
     * CSDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZS = "AEZS";


    /**
     * DUPRES
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZT = "AEZT";


    /**
     * RESUNAVAIL
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZU = "AEZU";


    /**
     * CHANNELERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZV = "AEZV";


    /**
     * CCSIDERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZW = "AEZW";


    /**
     * TIMEDOUT
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZX = "AEZX";


    /**
     * CODEPAGEERR
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZY = "AEZY";


    /**
     * INCOMPLETE
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZZ = "AEZZ";


    /**
     * APPNOTFOUND
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZ1 = "AEZ1";


    /**
     * BUSY
     * The transaction is abnormally terminated with a CICS transaction dump.
     * Change the application program either to prevent the condition recurring,
	 * to check it by using the RESP option, or to handle the condition when it does occur.
     */
    String AEZ2 = "AEZ2";


    /**
     * The executing function has been purged before control could be returned.
     * The transaction is marked to be abnormally terminated with abend code AELA.
     * Investigate the reason the task was purged. It was purged either by the
	 * master terminal operator, or as a result of a deadlock timeout.
     */
    String AELA = "AELA";


    /**
     * A deadlock has been detected between two or more tasks issuing file control
	 * requests.
     * The task that would have entered deadlock is abended with a CICS transaction dump.
     * Examine this transaction and other transactions in the system that update
	 * the same files to find the cause of the deadlock, then correct the error.
     */
    String AFCF = "AFCF";


    /**
     * A GETMAIN for FFLE storage has failed.
     * The transaction is abnormally terminated with a transaction dump.
     * Retry the failed transaction.
     */
    String AFCE = "AFCE";


    /**
     * An attempt has been made to update a file after file control restart failed.
     * The transaction is abnormally terminated with a transaction dump.
     * Determine the cause of the failure in file control restart. Restart CICS.
     */
    String AFC0 = "AFC0";


    /**
     * The program issued a file control request against a file opened in RLS mode.
     * The task is abnormally terminated with a CICS transaction dump.
     * Retry the transaction when the server is available again
     */
    String AFCR = "AFCR";


    /**
     * The program issued a file control request against a file opened in RLS mode.
     * The task is abnormally terminated with a CICS transaction dump.
     * Retry the transaction.
     */
    String AFCW = "AFCW";


    /**
     * A request made against a file opened in RLS mode was unable to acquire a
	 * record lock.
     * The task is abnormally terminated with a CICS transaction dump.
     * Retry the transaction.
     */
    String AFCV = "AFCV";


    /**
     * The program has made a file control request against a file opened in RLS mode.
     * The task is abnormally terminated with a CICS transaction dump.
     * Resubmit the transaction.
     */
    String AFCT = "AFCT";


    /**
     * The program issued a file control request against a file opened in RLS mode.
     * The task is abnormally terminated with a CICS transaction dump.
     * Retry the transaction when the server is available again.
     */
    String AFCS = "AFCS";


    /**
     * The transaction was connected to another transaction in another CICS
	 * system via an IPIC link. This other transaction has abnormally terminated.
     * The task is abnormally terminated with a CICS transaction dump.
     * Correct the cause of the abend in the connected transaction.
     */
    String AIPM = "AIPM";


    /**
     * A deadlock timeout condition has been detected.
     * The transaction is abnormally terminated.
	 * The transaction should be reexecuted and the situation causing
	 * the SUSPEND to occur may clear itself.The AKCS abend is to be
	 * expected occasionally, unless DTIMOUT is set to zero. No special
	 * action is necessary.
     */
    String AKCS = "AKCS";

	/**
	 * CICS has determined that an OS/VS COBOL program is about to be executed.
	 * However CICS no longer supports such programs . CICS abnormally terminates
	 * the task and disables the program.CICS processing continues. Ensure that
	 * the program is recompiled against a level of COBOL compiler supported by
	 * CICS.
	*/
    String  ALIK = 	"ALIK";


    /**
     * A task has been purged due to lack of storage.
     * The task is abnormally terminated with a transaction dump.
     * Try the transaction again later.
     */
    String AMGB = "AMGB";


    /**
     * CICS encountered a GETMAIN failure during transaction initialization.
     * A severe error message and system dump should have preceeded this abend.
     * Try after sometime and use related diagnostics to determine the cause of
	 * the problem.
     */
    String APIS = "APIS";


    /**
     * A task issued a SPOOL command without the mandatory NOHANDLE operand.
     * CICS terminates the task abnormally with a dump.
     * Correct the syntax of the command, specifying NOHANDLE.
     */
    String APST = "APST";


    /**
     * The connection manager has insufficient authority.
     * The following message is issued: DFHRP1902.
     * See the user response for the message.
     */
    String ARPZ = "ARPZ";


    /**
     * An error was encountered when attempting to read from or write to
	 * temporary storage.
     * The task is abnormally terminated with a CICS transaction dump.
     * Determine the cause of the temporary storage problem and correct it.
     */
    String ARTE = "ARTE";


    /**
     * SEND DATA request with a data length greater than 65 528 bytes which is
	 * the maximum that it can process.
     * The transaction is abnormally terminated with a CICS transaction dump.
     * This is a CICS internal logic error.
     */
    String ATC6 = "ATC6";


    /**
     * An application program, has either issued more than one write request or
	 * issued a read request.
     * Correct the application program so that it will not issue more than one
	 * consecutive WRITE to a pipeline session terminal.
     */
    String ATCC = "ATCC";


    /**
     * A task has attempted to issue a WRITEQ TS request for a recoverable TS
	 * queue that has already been deleted in the same unit of work.
     * The task is abnormally terminated with a CICS transaction dump.
     * Correct the application to avoid issuing a WRITEQ TS request to a
	 * recoverable queue in a unit of work in which the queue has already been
	 * deleted.
     */
    String ATSP = "ATSP";


    /**
     * This is normal behavior when a user stage of the pipeline abends.
     * The task is abnormally terminated with a CICS transaction dump.
     * Correct the user abend.
     */
    String AWSQ = "AWSQ";


    /**
     * A task has been purged due to lack of storage in a dynamic storage area (DSA).
     * The task is abnormally terminated with a transaction dump.
     * Try the transaction again later.
     */
    String AXF0 = "AXF0";


    /**
     * A keyword such as TOKEN, CONSISTENT, REPEATABLE, UNCOMMITTED, or NOSUSPEND
	 * has been specified on a file control command for shipping to a system which
	 * does not support these functions.
     * The task is abnormally terminated with a CICS transaction dump.
     * Ensure that CICS in the file-owning region is at the correct level.
     */
    String AXF8 = "AXF8";


    /**
     * The data length for a WRITEQ TD or READQ TD is zero. The abend can also
	 * occur when determining the length for file control requests.
     * The task is abnormally terminated with a CICS transaction dump.
     * Notify the system programmer.
     */
    String AXFI = "AXFI";


    /**
     * The CICS command level interface imposes a maximum length of 32 767 for
	 * data. The length of the data just received exceeds this limit.
     * The task is abnormally terminated with a CICS transaction dump.
     * Notify the system programmer.
     */
    String AXFR = "AXFR";


    /**
     * An APPC conversation failure has occurred when an attach between CICS
	 * systems was issued.
     * The task is abnormally terminated with a transaction dump.
     * Check the connection to the remote CICS system and try to reestablish it.
     */
    String AXFY = "AXFY";


    /**
     * A distributed program link (DPL) request, specifying channel name
	 * DFHTRANSACTION, has also specified a COMMAREA that is too large for the
	 * terminal I/O area (TIOA).
     * The task is abnormally terminated with a transaction dump.
     * Check if the request is using the correct length for the COMMAREA. If a
	 * DFHTRANSACTION channel is specified the COMMAREA size should not exceed 24 KB.
     */
    String AXGE = "AXGE";


    /**
     * An APPC conversation failure occurred when an attach between CICS systems was issued.
     * The task is abnormally terminated with a CICS transaction dump.
     * Check the connection to the remote CICS system and try to reestablish it.
     */
    String AXTK = "AXTK";


    /**
     * The task is being routed back to the region from where it came.
     * CICS abnormally terminates the transaction with a transaction dump.
     * Correct the transaction definition.
     */
    String AZT3 = "AZT3";


}
